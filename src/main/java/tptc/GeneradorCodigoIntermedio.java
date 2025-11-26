package tptc;

import org.antlr.v4.runtime.tree.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Generador de código intermedio (tres direcciones) a partir del AST.
 */
public class GeneradorCodigoIntermedio extends compiladorBaseVisitor<String> {

    private final List<String> codigo = new ArrayList<>();
    private int tempCount = 0;
    private int labelCount = 0;
    private final Stack<String> labelsSalida = new Stack<>();
    private final Stack<String> labelsEntrada = new Stack<>();

    public GeneradorCodigoIntermedio() {
        // Primera línea como en el ejemplo esperado
        codigo.add("// Código de tres direcciones generado");
    }

    /** Devuelve la lista de instrucciones de código intermedio generadas. */
    public List<String> getCodigoIntermedio() {
        return codigo;
    }

    /**
     * Muestra el código intermedio por consola, numerado, para que App pueda
     * usarlo después de imprimir sus encabezados.
     */
    public void mostrarCodigo() {
        for (int i = 0; i < codigo.size(); i++) {
            System.out.printf(" %2d: %s%n", i, codigo.get(i));
        }
    }

    /**
     * Exporta el código intermedio a un archivo de texto, con numeración.
     */
    public void exportarComoTxt(String rutaArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (int i = 0; i < codigo.size(); i++) {
                writer.printf("%3d: %s%n", i, codigo.get(i));
            }
            System.out.println(ColoresConsole.verde(
                    "✅ Código intermedio guardado en: " + rutaArchivo));
        } catch (IOException e) {
            System.err.println(ColoresConsole.rojo(
                    "❌ Error al guardar código intermedio: " + e.getMessage()));
        }
    }

    // ===================== Helpers internos =====================

    private String nuevaTemp() {
        return "t" + (tempCount++);
    }

    private String nuevaLabel() {
        // Solo el número, para poder armar THEN_1 / END_IF_1 si quisieras
        return String.valueOf(labelCount++);
    }

    // ===================== Visitas del AST ======================

    @Override
    public String visitPrograma(compiladorParser.ProgramaContext ctx) {
        codigo.add("PROGRAMA_INICIO:");
        // Comentario general de declaraciones globales (aunque no haya ninguna)
        codigo.add("// Declaración de variables globales");
        visitChildren(ctx);
        codigo.add("PROGRAMA_FIN:");
        return null;
    }

    @Override
    public String visitDefinicion_funcion_main(compiladorParser.Definicion_funcion_mainContext ctx) {
        codigo.add("func_main:");
        // El cuerpo y los returns se generan en visitBloque / visitRetorno
        visit(ctx.bloque());
        return null;
    }

    @Override
    public String visitDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx) {
        String nombreFuncion = ctx.IDENTIFICADOR().getText();
        codigo.add("func_" + nombreFuncion + ":");

        if (ctx.parametros() != null) {
            visit(ctx.parametros());
        }

        visit(ctx.bloque());
        return null;
    }

    @Override
    public String visitParametro(compiladorParser.ParametroContext ctx) {
        String nombreParam = ctx.IDENTIFICADOR().getText();
        String tipo = ctx.tipo().getText();
        // Formato: PARAM a int (nombre, tipo)
        codigo.add("PARAM " + nombreParam + " " + tipo);
        return null;
    }

    // ===== Declaraciones =====

    @Override
    public String visitDeclaracion_global(compiladorParser.Declaracion_globalContext ctx) {
        String tipo = ctx.tipo().getText();
        for (compiladorParser.DeclaradorContext d : ctx.declarador()) {
            String nombre = d.IDENTIFICADOR().getText();
            if (d.ENTERO() != null) {
                // Arreglo global: numeros[3] int
                codigo.add("DECLARE " + nombre + "[" + d.ENTERO().getText() + "] " + tipo);
            } else {
                // Variable global simple: contadorGlobal int
                codigo.add("DECLARE " + nombre + " " + tipo);
            }
        }
        return null;
    }

    @Override
    public String visitDeclaracion_variable(compiladorParser.Declaracion_variableContext ctx) {
        String tipo = ctx.tipo().getText();
        for (compiladorParser.DeclaradorContext d : ctx.declarador()) {
            String nombre = d.IDENTIFICADOR().getText();
            if (d.ENTERO() != null) {
                codigo.add("DECLARE " + nombre + "[" + d.ENTERO().getText() + "] " + tipo);
            } else {
                codigo.add("DECLARE " + nombre + " " + tipo);
            }
        }
        return null;
    }

    // ===== Asignaciones =====

    @Override
    public String visitAsignacion_simple(compiladorParser.Asignacion_simpleContext ctx) {
        String lhs = visit(ctx.referencia());        // lado izquierdo (id o arreglo)
        String rhs = visit(ctx.expresion());         // lado derecho
        codigo.add(lhs + " = " + rhs);
        return null;
    }

    @Override
    public String visitAsignacion_suma(compiladorParser.Asignacion_sumaContext ctx) {
        String lhs = visit(ctx.referencia());
        String rhs = visit(ctx.expresion());
        String temp = nuevaTemp();
        codigo.add(temp + " = " + lhs + " + " + rhs);
        codigo.add(lhs + " = " + temp);
        return null;
    }

    // ===== Referencias (id o arreglo) =====

    @Override
    public String visitReferencia(compiladorParser.ReferenciaContext ctx) {
        if (ctx.expresion() == null) {
            return ctx.IDENTIFICADOR().getText();
        } else {
            String idx = visit(ctx.expresion());
            return ctx.IDENTIFICADOR().getText() + "[" + idx + "]";
        }
    }

    // ===== Expresiones =====

    @Override
    public String visitExpresion(compiladorParser.ExpresionContext ctx) {
        // 1) Llamada a función: IDENTIFICADOR PA argumentos? PC
        if (ctx.IDENTIFICADOR() != null && ctx.argumentos() != null) {
            return procesarLlamadaFuncion(ctx);
        }

        // 2) Referencia (variable o arreglo)
        if (ctx.referencia() != null) {
            return visit(ctx.referencia());
        }

        // 3) Caso simple: literal o identificador suelto
        if (ctx.getChildCount() == 1) {
            ParseTree hijo = ctx.getChild(0);
            if (hijo instanceof TerminalNode) {
                return hijo.getText();
            }
            return visit(hijo);
        }

        // 4) Paréntesis: (exp)
        if (ctx.PA() != null && ctx.PC() != null && ctx.expresion().size() == 1) {
            return visit(ctx.expresion(0));
        }

        // 5) Expresión binaria: E op E
        if (ctx.getChildCount() == 3 && ctx.expresion().size() == 2) {
            String left = visit(ctx.expresion(0));
            String op = ctx.getChild(1).getText();
            String right = visit(ctx.expresion(1));

            String t = nuevaTemp();
            codigo.add(t + " = " + left + " " + op + " " + right);
            return t;
        }

        // 6) Expresión unaria: op E
        if (ctx.getChildCount() == 2 && ctx.expresion().size() == 1) {
            String op = ctx.getChild(0).getText();
            String e = visit(ctx.expresion(0));
            String t = nuevaTemp();
            codigo.add(t + " = " + op + " " + e);
            return t;
        }

        return visitChildren(ctx);
    }

    private String procesarLlamadaFuncion(compiladorParser.ExpresionContext ctx) {
        String nombreFuncion = ctx.IDENTIFICADOR().getText();
        StringBuilder call = new StringBuilder("CALL func_" + nombreFuncion);

        if (ctx.argumentos() != null && ctx.argumentos().expresion() != null) {
            for (compiladorParser.ExpresionContext arg : ctx.argumentos().expresion()) {
                String val = visit(arg);
                call.append(", ").append(val);
            }
        }

        // Igual que en el ejemplo: se asume RETURN_VALUE y lo copiamos a un temporal
        codigo.add(call.toString());
        String t = nuevaTemp();
        codigo.add(t + " = RETURN_VALUE");
        return t;
    }

    // ===== Estructuras de control =====

    @Override
    public String visitSi(compiladorParser.SiContext ctx) {
        String cond = visit(ctx.expresion());
        String n = nuevaLabel(); // número para etiquetar THEN_n / END_IF_n
        String thenLabel = "THEN_" + n;
        String endLabel = "END_IF_" + n;

        // Modelo: if cond goto THEN_n ; goto END_IF_n ; THEN_n: ... END_IF_n:
        codigo.add("t" + nuevaTemp() + " = " + cond); // si querés se puede simplificar
        String lastTemp = "t" + (tempCount - 1);

        codigo.add("if " + lastTemp + " goto " + thenLabel);
        codigo.add("goto " + endLabel);
        codigo.add(thenLabel + ":");
        visit(ctx.instruccion(0));
        // (si quisieras manejar else, acá habría que extender)
        codigo.add(endLabel + ":");
        return null;
    }

    @Override
    public String visitMientras(compiladorParser.MientrasContext ctx) {
        String start = "L" + nuevaLabel();
        String end = "L" + nuevaLabel();

        labelsEntrada.push(start);
        labelsSalida.push(end);

        codigo.add(start + ":");
        String cond = visit(ctx.expresion());
        codigo.add("ifFalse " + cond + " goto " + end);
        visit(ctx.instruccion());
        codigo.add("goto " + start);
        codigo.add(end + ":");

        labelsEntrada.pop();
        labelsSalida.pop();
        return null;
    }

    @Override
    public String visitPara(compiladorParser.ParaContext ctx) {
        String start = "L" + nuevaLabel();
        String end = "L" + nuevaLabel();

        labelsEntrada.push(start);
        labelsSalida.push(end);

        // Inicialización
        if (ctx.declaracion_variable() != null) {
            visit(ctx.declaracion_variable());
        } else if (ctx.asignacion_simple() != null && !ctx.asignacion_simple().isEmpty()) {
            visit(ctx.asignacion_simple(0));
        }

        codigo.add(start + ":");

        // Condición
        if (ctx.expresion() != null && !ctx.expresion().isEmpty()) {
            String cond = visit(ctx.expresion(0));
            codigo.add("ifFalse " + cond + " goto " + end);
        }

        // Cuerpo
        visit(ctx.instruccion());

        // Actualización
        if (ctx.asignacion_simple() != null && ctx.asignacion_simple().size() > 0) {
            int index = ctx.declaracion_variable() != null ? 1 : 0;
            if (ctx.asignacion_simple().size() > index) {
                visit(ctx.asignacion_simple(index));
            }
        }

        codigo.add("goto " + start);
        codigo.add(end + ":");

        labelsEntrada.pop();
        labelsSalida.pop();
        return null;
    }

    // ===== Instrucciones (break / continue) =====

    @Override
    public String visitInstruccion(compiladorParser.InstruccionContext ctx) {
        if (ctx.BREAK() != null) {
            if (!labelsSalida.isEmpty()) {
                codigo.add("goto " + labelsSalida.peek());
            }
            return null;
        }

        if (ctx.CONTINUE() != null) {
            if (!labelsEntrada.isEmpty()) {
                codigo.add("goto " + labelsEntrada.peek());
            }
            return null;
        }

        return visitChildren(ctx);
    }

    // ===== Return =====

    @Override
    public String visitRetorno(compiladorParser.RetornoContext ctx) {
        if (ctx.expresion() != null) {
            String val = visit(ctx.expresion());
            codigo.add("return " + val);
        } else {
            codigo.add("return");
        }
        return null;
    }

    // ===== Bloques =====

    @Override
    public String visitBloque(compiladorParser.BloqueContext ctx) {
        for (compiladorParser.InstruccionContext instr : ctx.instruccion()) {
            visit(instr);
        }
        return null;
    }
}
