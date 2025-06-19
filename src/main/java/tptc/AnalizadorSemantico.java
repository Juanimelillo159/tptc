package tptc;

import tptc.AnalizadorSintactico.ResultadoAnalisisSintactico;
import tptc.compiladorParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;
import java.util.*;

/**
 * Analizador Semántico para el compilador C++
 * Responsabilidad: Verificar coherencia semántica del programa
 */
public class AnalizadorSemantico {

    // Clase para almacenar errores semánticos (similar a ErrorSintactico)
    public static class ErrorSemantico {
        private int linea;
        private int columna;
        private String mensaje;
        private String contexto;
        private TipoError tipo; // ERROR o WARNING

        public enum TipoError { ERROR, WARNING }

        public ErrorSemantico(int linea, int columna, String mensaje, String contexto, TipoError tipo) {
            this.linea = linea;
            this.columna = columna;
            this.mensaje = mensaje;
            this.contexto = contexto;
            this.tipo = tipo;
        }

        // Getters
        public int getLinea() { return linea; }
        public int getColumna() { return columna; }
        public String getMensaje() { return mensaje; }
        public String getContexto() { return contexto; }
        public TipoError getTipo() { return tipo; }
        public boolean esError() { return tipo == TipoError.ERROR; }
        public boolean esWarning() { return tipo == TipoError.WARNING; }

        @Override
        public String toString() {
            return String.format("%s en Línea %d, Col %d [%s]: %s", 
                               tipo.toString(), linea, columna, contexto, mensaje);
        }
    }

    // Clase para representar símbolos en la tabla de símbolos
    public static class Simbolo {
        private String nombre;
        private String tipo;
        private int ambito;
        private int lineaDeclaracion;
        private int columnaDeclaracion;

        public Simbolo(String nombre, String tipo, int ambito, int linea, int columna) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.ambito = ambito;
            this.lineaDeclaracion = linea;
            this.columnaDeclaracion = columna;
        }

        // Getters
        public String getNombre() { return nombre; }
        public String getTipo() { return tipo; }
        public int getAmbito() { return ambito; }
        public int getLineaDeclaracion() { return lineaDeclaracion; }
        public int getColumnaDeclaracion() { return columnaDeclaracion; }
    }

    // Clase para almacenar resultados del análisis semántico
    public static class ResultadoAnalisisSemantico {
        private List<ErrorSemantico> errores;
        private List<Simbolo> tablaSimbolos;
        private boolean exitoso;
        private long tiempoAnalisis;
        private int totalVariables;
        private int totalFunciones;

        public ResultadoAnalisisSemantico(List<ErrorSemantico> errores, List<Simbolo> tablaSimbolos, 
                                        long tiempoAnalisis) {
            this.errores = errores;
            this.tablaSimbolos = tablaSimbolos;
            this.tiempoAnalisis = tiempoAnalisis;
            this.exitoso = errores.stream().noneMatch(ErrorSemantico::esError);
            
            this.totalVariables = (int) tablaSimbolos.stream()
                .filter(s -> s.getTipo().equals("variable")).count();
            this.totalFunciones = (int) tablaSimbolos.stream()
                .filter(s -> s.getTipo().equals("funcion")).count();
        }

        // Getters
        public List<ErrorSemantico> getErrores() { return new ArrayList<>(errores); }
        public List<Simbolo> getTablaSimbolos() { return new ArrayList<>(tablaSimbolos); }
        public boolean fueExitoso() { return exitoso; }
        public long getTiempoAnalisis() { return tiempoAnalisis; }
        public int getTotalVariables() { return totalVariables; }
        public int getTotalFunciones() { return totalFunciones; }
        public int getTotalErrores() { 
            return (int) errores.stream().filter(ErrorSemantico::esError).count(); 
        }
        public int getTotalWarnings() { 
            return (int) errores.stream().filter(ErrorSemantico::esWarning).count(); 
        }
    }

    // Visitor para recorrer el AST y realizar el análisis semántico
    private static class VisitorSemantico extends compiladorBaseVisitor<Void> {
        private List<ErrorSemantico> errores = new ArrayList<>();
        private List<Simbolo> tablaSimbolos = new ArrayList<>();
        private Stack<Integer> ambitos = new Stack<>();
        private int contadorAmbito = 0;

        public VisitorSemantico() {
            ambitos.push(contadorAmbito++); // Ámbito global
        }

        @Override 
        public Void visitDeclaracion_variable(compiladorParser.Declaracion_variableContext ctx) {

            compiladorParser.VariableContext varCtx = ctx.lista_variables().variable(0);

            
            String nombreVariable = ctx.lista_variables().variable(0).IDENTIFICADOR().getText();
            String tipoVariable = ctx.tipo().getText();

            // Verificar si la variable ya existe en el ámbito actual
            if (existeEnAmbitoActual(nombreVariable)) {
                agregarError(ctx, "Variable '" + nombreVariable + "' ya declarada en este ámbito", 
                           ErrorSemantico.TipoError.ERROR);
            } else {
                // Agregar a la tabla de símbolos
                tablaSimbolos.add(new Simbolo(
                    nombreVariable, 
                    "variable", 
                    ambitos.peek(),
                    ctx.start.getLine(),
                    ctx.start.getCharPositionInLine()
                ));
            }

            // Verificar inicialización si existe (CORRECCIÓN PRINCIPAL)
            if (varCtx.IGU() != null) {  // Si hay signo '='
            visit(varCtx.expresion());  // Visitar la expresión de inicialización
        
            // Verificación de tipos (ejemplo básico)
            if (!tipoVariable.equals(inferirTipo(varCtx.expresion()))) {
                agregarError(ctx, "Tipo incompatible en inicialización", 
                       ErrorSemantico.TipoError.ERROR);
            }
    }

            return null;
        }

        @Override
        public Void visitBloque(compiladorParser.BloqueContext ctx) {
            ambitos.push(contadorAmbito++); // Nuevo ámbito
            super.visitBloque(ctx);
            ambitos.pop(); // Salir del ámbito
            return null;
        }

        private boolean existeEnAmbitoActual(String nombre) {
            return tablaSimbolos.stream()
                .filter(s -> s.getNombre().equals(nombre))
                .anyMatch(s -> s.getAmbito() == ambitos.peek());
        }

        private void agregarError(ParserRuleContext ctx, String mensaje, ErrorSemantico.TipoError tipo) {
            errores.add(new ErrorSemantico(
                ctx.start.getLine(),
                ctx.start.getCharPositionInLine(),
                mensaje,
                ctx.getClass().getSimpleName().replace("Context", ""),
                tipo
            ));
        }

        public List<ErrorSemantico> getErrores() { return errores; }
        public List<Simbolo> getTablaSimbolos() { return tablaSimbolos; }
    }

    /**
     * Analiza un árbol sintáctico y retorna el resultado del análisis semántico
     */
    public static ResultadoAnalisisSemantico analizarArbol(ParseTree arbol) {
        long tiempoInicio = System.currentTimeMillis();

        try {
            VisitorSemantico visitor = new VisitorSemantico();
            visitor.visit(arbol);

            long tiempoFin = System.currentTimeMillis();
            return new ResultadoAnalisisSemantico(
                visitor.getErrores(),
                visitor.getTablaSimbolos(),
                tiempoFin - tiempoInicio
            );
        } catch (Exception e) {
            List<ErrorSemantico> errores = new ArrayList<>();
            errores.add(new ErrorSemantico(
                0, 0, 
                "Error catastrófico: " + e.getMessage(), 
                "programa", 
                ErrorSemantico.TipoError.ERROR
            ));
            return new ResultadoAnalisisSemantico(
                errores, 
                Collections.emptyList(), 
                System.currentTimeMillis() - tiempoInicio
            );
        }
    }

    /**
     * Analiza código fuente directamente (usa el analizador sintáctico internamente)
     */
    public static ResultadoAnalisisSemantico analizarCodigo(String codigoFuente) {
        ResultadoAnalisisSintactico resultadoSintactico = AnalizadorSintactico.analizarCodigo(codigoFuente);
        if (!resultadoSintactico.fueExitoso()) {
            List<ErrorSemantico> errores = new ArrayList<>();
            errores.add(new ErrorSemantico(
                0, 0,
                "No se puede realizar análisis semántico debido a errores sintácticos",
                "programa",
                ErrorSemantico.TipoError.ERROR
            ));
            return new ResultadoAnalisisSemantico(
                errores,
                Collections.emptyList(),
                0
            );
        }
        return analizarArbol(resultadoSintactico.getArbolSintactico());
    }

    /**
     * Analiza un archivo (usa analizador sintáctico internamente)
     */
    public static ResultadoAnalisisSemantico analizarArchivo(String rutaArchivo) throws IOException {
        String contenido = AnalizadorSintactico.leerArchivo(rutaArchivo);
        return analizarCodigo(contenido);
    }
}