package tptc;

import org.antlr.v4.runtime.tree.*;
import java.util.*;

public class GeneradorCodigoIntermedio extends compiladorBaseVisitor<String> {
    private List<String> codigo = new ArrayList<>();
    public int tempCount = 0;
    public int labelCount = 0;
    private Stack<String> labelsSalida = new Stack<>();
    private Stack<String> labelsEntrada = new Stack<>();

    public List<String> getCodigoIntermedio() {
        return codigo;
    }

    public void mostrarCodigo() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                CÓDIGO INTERMEDIO GENERADO                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        for (int i = 0; i < codigo.size(); i++) {
            System.out.printf("%3d │ %s%n", i + 1, codigo.get(i));
        }
    }

    private String nuevaTemp() {
        return "t" + (tempCount++);
    }

    private String nuevaLabel() {
        return "L" + (labelCount++);
    }

    @Override
    public String visitPrograma(compiladorParser.ProgramaContext ctx) {
        codigo.add("INICIO_PROGRAMA");
        visitChildren(ctx);
        codigo.add("FIN_PROGRAMA");
        return null;
    }

    @Override
    public String visitDefinicion_funcion_main(compiladorParser.Definicion_funcion_mainContext ctx) {
        codigo.add("FUNCION main:");
        visit(ctx.bloque());
        codigo.add("FIN_FUNCION main");
        return null;
    }

    @Override
    public String visitDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx) {
        String nombreFuncion = ctx.IDENTIFICADOR().getText();
        codigo.add("FUNCION " + nombreFuncion + ":");
        
        if (ctx.parametros() != null) {
            visit(ctx.parametros());
        }
        
        visit(ctx.bloque());
        codigo.add("FIN_FUNCION " + nombreFuncion);
        return null;
    }

    @Override
    public String visitParametro(compiladorParser.ParametroContext ctx) {
        String nombreParam = ctx.IDENTIFICADOR().getText();
        String tipo = ctx.tipo().getText();
        codigo.add("PARAM " + tipo + " " + nombreParam);
        return null;
    }

    @Override
    public String visitDeclaracion_variable(compiladorParser.Declaracion_variableContext ctx) {
        String nombreVariable = ctx.IDENTIFICADOR().getText();
        String tipo = ctx.tipo().getText();
        
        codigo.add("DECLARAR " + tipo + " " + nombreVariable);
        
        if (ctx.expresion() != null) {
            String valor = visit(ctx.expresion());
            codigo.add(nombreVariable + " = " + valor);
        }
        
        return null;
    }

    @Override
    public String visitAsignacion_simple(compiladorParser.Asignacion_simpleContext ctx) {
        String id = ctx.IDENTIFICADOR().getText();
        String expr = visit(ctx.expresion());
        codigo.add(id + " = " + expr);
        return null;
    }

    @Override
    public String visitAsignacion_suma(compiladorParser.Asignacion_sumaContext ctx) {
        String id = ctx.IDENTIFICADOR().getText();
        String expr = visit(ctx.expresion());
        String temp = nuevaTemp();
        codigo.add(temp + " = " + id + " + " + expr);
        codigo.add(id + " = " + temp);
        return null;
    }

    @Override
    public String visitExpresion(compiladorParser.ExpresionContext ctx) {
        if (ctx.getChildCount() == 1) {
            ParseTree hijo = ctx.getChild(0);
            if (hijo instanceof TerminalNode) {
                return hijo.getText();
            }
            return visit(hijo);
        }

        if (ctx.getChildCount() == 3) {
            String left = visit(ctx.getChild(0));
            String op = ctx.getChild(1).getText();
            String right = visit(ctx.getChild(2));
            
            String temp = nuevaTemp();
            codigo.add(temp + " = " + left + " " + op + " " + right);
            return temp;
        }

        if (ctx.PA() != null && ctx.PC() != null) {
            return visit(ctx.expresion(0));
        }

        if (ctx.IDENTIFICADOR() != null && ctx.argumentos() != null) {
            return procesarLlamadaFuncion(ctx);
        }

        if ((ctx.SUMA() != null || ctx.RESTA() != null || ctx.NOT() != null) && 
            ctx.expresion() != null && ctx.expresion().size() == 1) {
            String expr = visit(ctx.expresion(0));
            String op = ctx.getChild(0).getText();
            String temp = nuevaTemp();
            
            if (op.equals("!")) {
                codigo.add(temp + " = " + op + " " + expr);
            } else {
                codigo.add(temp + " = " + op + expr);
            }
            return temp;
        }

        return visitChildren(ctx);
    }

    private String procesarLlamadaFuncion(compiladorParser.ExpresionContext ctx) {
        String nombreFuncion = ctx.IDENTIFICADOR().getText();
        StringBuilder llamada = new StringBuilder("CALL " + nombreFuncion);
        
        if (ctx.argumentos() != null && ctx.argumentos().expresion() != null) {
            for (compiladorParser.ExpresionContext arg : ctx.argumentos().expresion()) {
                String argValor = visit(arg);
                llamada.append(" ").append(argValor);
            }
        }
        
        if (!nombreFuncion.equals("main")) {
            String temp = nuevaTemp();
            codigo.add(temp + " = " + llamada.toString());
            return temp;
        } else {
            codigo.add(llamada.toString());
            return "";
        }
    }

    @Override
    public String visitInstruccion(compiladorParser.InstruccionContext ctx) {
        // Manejar break
        if (ctx.BREAK() != null) {
            if (!labelsSalida.isEmpty()) {
                codigo.add("goto " + labelsSalida.peek());
            }
            return null;
        }
        
        // Manejar continue
        if (ctx.CONTINUE() != null) {
            if (!labelsEntrada.isEmpty()) {
                codigo.add("goto " + labelsEntrada.peek());
            }
            return null;
        }
        
        // Para otros tipos de instrucciones, procesar normalmente
        return visitChildren(ctx);
    }

    @Override
    public String visitSi(compiladorParser.SiContext ctx) {
        String labelElse = nuevaLabel();
        String labelFin = nuevaLabel();

        String condicion = visit(ctx.expresion());
        codigo.add("ifFalse " + condicion + " goto " + labelElse);

        visit(ctx.instruccion(0));

        if (ctx.ELSE() != null) {
            codigo.add("goto " + labelFin);
            codigo.add(labelElse + ":");
            visit(ctx.instruccion(1));
            codigo.add(labelFin + ":");
        } else {
            codigo.add(labelElse + ":");
        }
        return null;
    }

    @Override
    public String visitMientras(compiladorParser.MientrasContext ctx) {
        String labelInicio = nuevaLabel();
        String labelFin = nuevaLabel();

        labelsEntrada.push(labelInicio);
        labelsSalida.push(labelFin);

        codigo.add(labelInicio + ":");
        String condicion = visit(ctx.expresion());
        codigo.add("ifFalse " + condicion + " goto " + labelFin);

        visit(ctx.instruccion());

        codigo.add("goto " + labelInicio);
        codigo.add(labelFin + ":");

        labelsEntrada.pop();
        labelsSalida.pop();
        return null;
    }

    @Override
    public String visitPara(compiladorParser.ParaContext ctx) {
        String labelInicio = nuevaLabel();
        String labelFin = nuevaLabel();

        labelsEntrada.push(labelInicio);
        labelsSalida.push(labelFin);

        if (ctx.declaracion_variable() != null) {
            visit(ctx.declaracion_variable());
        } else if (ctx.asignacion_simple() != null && !ctx.asignacion_simple().isEmpty()) {
            visit(ctx.asignacion_simple(0));
        }

        codigo.add(labelInicio + ":");

        if (ctx.expresion() != null && !ctx.expresion().isEmpty()) {
            String condicion = visit(ctx.expresion(0));
            codigo.add("ifFalse " + condicion + " goto " + labelFin);
        }

        visit(ctx.instruccion());

        if (ctx.asignacion_simple() != null && ctx.asignacion_simple().size() > 0) {
            int index = ctx.declaracion_variable() != null ? 1 : 0;
            if (ctx.asignacion_simple().size() > index) {
                visit(ctx.asignacion_simple(index));
            }
        }

        codigo.add("goto " + labelInicio);
        codigo.add(labelFin + ":");

        labelsEntrada.pop();
        labelsSalida.pop();
        return null;
    }

    @Override
    public String visitRetorno(compiladorParser.RetornoContext ctx) {
        if (ctx.expresion() != null) {
            String expr = visit(ctx.expresion());
            codigo.add("return " + expr);
        } else {
            codigo.add("return");
        }
        return null;
    }

    @Override
    public String visitBloque(compiladorParser.BloqueContext ctx) {
        if (ctx.instruccion() != null) {
            for (compiladorParser.InstruccionContext instr : ctx.instruccion()) {
                visit(instr);
            }
        }
        return null;
    }
}