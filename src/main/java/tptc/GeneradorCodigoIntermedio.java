package tptc;

import org.antlr.v4.runtime.tree.*;
import java.util.*;

public class GeneradorCodigoIntermedio extends compiladorBaseVisitor<String> {
    private List<String> codigo = new ArrayList<>();
    private int tempCount = 0;
    private int labelCount = 0;
    private Stack<String> labelsSalida = new Stack<>();
    private Stack<String> labelsEntrada = new Stack<>();

    public List<String> getCodigoIntermedio() {
        return codigo;
    }

    private String nuevaTemp() {
        return "t" + (tempCount++);
    }

    private String nuevaLabel() {
        return "L" + (labelCount++);
    }

    // Método auxiliar para manejar expresiones de condición
    private String visitExpresionCondicional(List<compiladorParser.ExpresionContext> expresiones) {
    if (expresiones == null || expresiones.isEmpty()) {
        throw new RuntimeException("Expresión condicional vacía");
    }
    
    // Si solo hay una expresión
    if (expresiones.size() == 1) {
        return visit(expresiones.get(0));
    }
    
    // Si hay múltiples expresiones, las combinamos con AND lógico
    String resultado = visit(expresiones.get(0));
    for (int i = 1; i < expresiones.size(); i++) {
        String temp = nuevaTemp();
        String right = visit(expresiones.get(i));
        codigo.add(temp + " = " + resultado + " && " + right);
        resultado = temp;
    }
    return resultado;
}

    private String procesarCondicion(ParseTree condicionNode) { 
        if (condicionNode instanceof compiladorParser.ExpresionContext) {
            return visit((compiladorParser.ExpresionContext) condicionNode);
        } else if (condicionNode instanceof TerminalNode) {
            return condicionNode.getText();
        }
        throw new RuntimeException("Tipo de condición no soportada: " + condicionNode.getClass().getSimpleName());
    }

    @Override
    public String visitPrograma(compiladorParser.ProgramaContext ctx) {
        codigo.add("INICIO_PROGRAMA");
        visitChildren(ctx);
        codigo.add("FIN_PROGRAMA");
        return null;
    }

    @Override
    public String visitDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx) {
        String nombreFuncion = ctx.IDENTIFICADOR().getText();
        codigo.add("FUNCION " + nombreFuncion + ":");
        visit(ctx.bloque());
        codigo.add("FIN_FUNCION " + nombreFuncion);
        return null;
    }

    @Override
    public String visitAsignacionSimple(compiladorParser.AsignacionSimpleContext ctx) {
        String id = ctx.IDENTIFICADOR().getText();
        String expr = visit(ctx.expresion());
        codigo.add(id + " = " + expr);
        return null;
    }

    @Override
    public String visitAsignacionSuma(compiladorParser.AsignacionSumaContext ctx) {
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
            // Manejo de expresiones simples (variables, literales)
            ParseTree hijo = ctx.getChild(0);
            if (hijo instanceof TerminalNode) {
                return hijo.getText();
            }
            return visit(hijo);
        }

        // Manejo de operaciones binarias
        if (ctx.getChildCount() == 3) {
            String left = visit(ctx.getChild(0));
            String right = visit(ctx.getChild(2));
            String op = ctx.getChild(1).getText();

            String temp = nuevaTemp();
            codigo.add(temp + " = " + left + " " + op + " " + right);
            return temp;
        }
        
        // Manejo de paréntesis
        if (ctx.PA() != null && ctx.PC() != null) {
            return visit(ctx.expresion(0));
        }

        return visitChildren(ctx);
    }

    @Override
    public String visitSi(compiladorParser.SiContext ctx) {
        String labelElse = nuevaLabel();
        String labelFin = nuevaLabel();

        // Versión segura para cualquier tipo de gramática
        String condicion = procesarCondicion(ctx.expresion());
        codigo.add("ifFalse " + condicion + " goto " + labelElse);

        visit(ctx.instruccion(0)); // Bloque if

        if (ctx.ELSE() != null) {
            codigo.add("goto " + labelFin);
            codigo.add(labelElse + ":");
        visit(ctx.instruccion(1)); // Bloque else
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
        String condicion = procesarCondicion(ctx.expresion());
        codigo.add("ifFalse " + condicion + " goto " + labelFin);

        visit(ctx.instruccion()); // Cuerpo del while

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

        // Inicialización
        if (ctx.declaracion_variable() != null) {
            visit(ctx.declaracion_variable());
        } else if (!ctx.asignacion().isEmpty()) {
            visit(ctx.asignacion(0));
        }

        codigo.add(labelInicio + ":");

        // Condición (manejo seguro de expresión nula)
        if (ctx.expresion() != null) {
            String condicion = visitExpresionCondicional(ctx.expresion());
            codigo.add("ifFalse " + condicion + " goto " + labelFin);
        }

        // Cuerpo
        visit(ctx.instruccion());

        // Incremento (manejo seguro de índices)
        if (ctx.asignacion().size() > (ctx.declaracion_variable() != null ? 1 : 0)) {
            visit(ctx.asignacion(ctx.declaracion_variable() != null ? 1 : 0));
        }

        codigo.add("goto " + labelInicio);
        codigo.add(labelFin + ":");
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
}