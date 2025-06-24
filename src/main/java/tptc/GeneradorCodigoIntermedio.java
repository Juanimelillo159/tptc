package tptc;

import org.antlr.v4.runtime.tree.ParseTree;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    public String visitAsignacion(compiladorParser.AsignacionContext ctx) {
        String id = ctx.IDENTIFICADOR().getText();
        String expr = visit(ctx.expresion());
        
        if (ctx.op.getType() == compiladorParser.IGU) {
            codigo.add(id + " = " + expr);
        } else if (ctx.op.getType() == compiladorParser.SUMA_ASIG) {
            String temp = nuevaTemp();
            codigo.add(temp + " = " + id + " + " + expr);
            codigo.add(id + " = " + temp);
        }
        return null;
    }

    @Override
    public String visitExpresion(compiladorParser.ExpresionContext ctx) {
        if (ctx.getChildCount() == 1) {
            return visit(ctx.getChild(0));
        }

        if (ctx.getChildCount() == 3) {
            String left = visit(ctx.getChild(0));
            String right = visit(ctx.getChild(2));
            String op = ctx.getChild(1).getText();

            if (ctx.getChild(1) instanceof TerminalNode) {
                String temp = nuevaTemp();
                codigo.add(temp + " = " + left + " " + op + " " + right);
                return temp;
            }
        }
        return visitChildren(ctx);
    }

    @Override
    public String visitSi(compiladorParser.SiContext ctx) {
        String labelElse = nuevaLabel();
        String labelFin = nuevaLabel();

        String condicion = visit(ctx.expresion());
        codigo.add("if " + condicion + " goto " + labelElse);

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
        String condicion = visit(ctx.expresion());
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
        } else if (ctx.asignacion(0) != null) {
            visit(ctx.asignacion(0));
        }

        codigo.add(labelInicio + ":");

        // Condición
        if (ctx.expresion() != null) {
            String condicion = visit(ctx.expresion());
            codigo.add("ifFalse " + condicion + " goto " + labelFin);
        }

        // Cuerpo
        visit(ctx.instruccion());

        // Incremento
        if (ctx.asignacion().size() > (ctx.declaracion_variable() != null ? 0 : 1)) {
            visit(ctx.asignacion(ctx.asignacion().size() - 1));
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