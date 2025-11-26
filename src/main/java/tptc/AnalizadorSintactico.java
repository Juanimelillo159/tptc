package tptc;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
import java.util.*;

/**
 * Analizador Sintáctico para el compilador C++
 * Responsabilidad: Procesar tokens y construir árbol de sintaxis
 */
public class AnalizadorSintactico {
    
    // Clase para almacenar errores sintácticos
    public static class ErrorSintactico {
        private int linea;
        private int columna;
        private String mensaje;
        private String tokenOfensivo;
        private String contexto;
        
        public ErrorSintactico(int linea, int columna, String mensaje, String tokenOfensivo, String contexto) {
            this.linea = linea;
            this.columna = columna;
            this.mensaje = mensaje;
            this.tokenOfensivo = tokenOfensivo;
            this.contexto = contexto;
        }
        
        // Getters
        public int getLinea() { return linea; }
        public int getColumna() { return columna; }
        public String getMensaje() { return mensaje; }
        public String getTokenOfensivo() { return tokenOfensivo; }
        public String getContexto() { return contexto; }
        
        @Override
        public String toString() {
            return String.format("Línea %d, Col %d: %s (Token: '%s')", 
                               linea, columna, mensaje, tokenOfensivo);
        }
    }
    
    // Listener personalizado para capturar errores sintácticos
    public static class ManejadorErroresSintacticos extends BaseErrorListener {
        private List<ErrorSintactico> errores = new ArrayList<>();
        
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                               int line, int charPositionInLine, String msg,
                               RecognitionException e) {
            
            String tokenOfensivo = "EOF";
            String contexto = "desconocido";
            
            if (offendingSymbol instanceof Token) {
                Token token = (Token) offendingSymbol;
                tokenOfensivo = token.getText();
                
                // Obtener contexto del parser si es posible
                if (recognizer instanceof Parser) {
                    Parser parser = (Parser) recognizer;
                    contexto = obtenerContexto(parser, e);
                }
            }
            
            ErrorSintactico error = new ErrorSintactico(
                line, charPositionInLine + 1, msg, tokenOfensivo, contexto);
            errores.add(error);
        }
        
        private String obtenerContexto(Parser parser, RecognitionException e) {
            if (e != null && e.getCtx() != null) {
                return e.getCtx().getClass().getSimpleName().replace("Context", "");
            }
            
            // Intentar obtener el contexto actual del parser
            RuleContext ctx = parser.getContext();
            if (ctx != null) {
                return ctx.getClass().getSimpleName().replace("Context", "");
            }
            
            return "programa";
        }
        
        public List<ErrorSintactico> getErrores() { return new ArrayList<>(errores); }
        public boolean tieneErrores() { return !errores.isEmpty(); }
        public int getNumeroErrores() { return errores.size(); }
    }
    
    // Clase para almacenar resultados del análisis sintáctico
    public static class ResultadoAnalisisSintactico {
        private ParseTree arbolSintactico;
        private List<ErrorSintactico> errores;
        private boolean exitoso;
        private long tiempoAnalisis;
        private int numeroNodos;
        private int profundidadMaxima;
         private List<String> codigoIntermedio;
         private List<String> reporteOptimizacion;
        
        public ResultadoAnalisisSintactico(ParseTree arbol, List<ErrorSintactico> errores, 
                                         long tiempoAnalisis) {
            this.arbolSintactico = arbol;
            this.errores = errores;
            this.exitoso = errores.isEmpty();
            this.tiempoAnalisis = tiempoAnalisis;
            this.numeroNodos = contarNodos(arbol);
            this.profundidadMaxima = calcularProfundidad(arbol);
             this.reporteOptimizacion = new ArrayList<>();
             this.codigoIntermedio = generarCodigoIntermedio(arbol);
        }

        private List<String> generarCodigoIntermedio(ParseTree arbol) {
        if (!exitoso) return new ArrayList<>();
        GeneradorCodigoIntermedio generador = new GeneradorCodigoIntermedio();
            generador.visit(arbol);

            List<String> codigoIntermedio = generador.getCodigoIntermedio();

            Optimizador optimizador = new Optimizador(codigoIntermedio);
            List<String> codigoOptimizado = optimizador.optimizar();

            reporteOptimizacion = optimizador.getReporteOptimizaciones();


            optimizador.mostrarReporteOptimizacion();
            optimizador.mostrarCodigoOptimizado();
    
            return codigoOptimizado;
        }

        public List<String> getCodigoIntermedio() {
            return codigoIntermedio;
        }

        public List<String> getReporteOptimizacion() {
            return reporteOptimizacion;
        }
        
        private int contarNodos(ParseTree nodo) {
            if (nodo == null) return 0;
            int count = 1;
            for (int i = 0; i < nodo.getChildCount(); i++) {
                count += contarNodos(nodo.getChild(i));
            }
            return count;
        }
        
        private int calcularProfundidad(ParseTree nodo) {
            if (nodo == null || nodo.getChildCount() == 0) return 1;
            int maxProfundidad = 0;
            for (int i = 0; i < nodo.getChildCount(); i++) {
                maxProfundidad = Math.max(maxProfundidad, calcularProfundidad(nodo.getChild(i)));
            }
            return 1 + maxProfundidad;
        }
        
        // Getters
        public ParseTree getArbolSintactico() { return arbolSintactico; }
        public List<ErrorSintactico> getErrores() { return errores; }
        public boolean fueExitoso() { return exitoso; }
        public long getTiempoAnalisis() { return tiempoAnalisis; }
        public int getNumeroNodos() { return numeroNodos; }
        public int getProfundidadMaxima() { return profundidadMaxima; }
    }
    
    /**
     * Analiza un archivo y retorna el resultado del análisis sintáctico
     */
    public static ResultadoAnalisisSintactico analizarArchivo(String rutaArchivo) throws IOException {
        String contenido = leerArchivo(rutaArchivo);
        return analizarCodigo(contenido);
    }
    
    /**
     * Analiza código fuente directamente
     */
    public static ResultadoAnalisisSintactico analizarCodigo(String codigoFuente) {
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            // Crear el stream de entrada
            CharStream input = CharStreams.fromString(codigoFuente);
            
            // Crear lexer
            compiladorLexer lexer = new compiladorLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            
            // Crear parser
            compiladorParser parser = new compiladorParser(tokens);
            
            // Configurar manejo de errores
            ManejadorErroresSintacticos manejadorErrores = new ManejadorErroresSintacticos();
            parser.removeErrorListeners(); // Quitar el listener por defecto
            parser.addErrorListener(manejadorErrores);
            
            // Configurar estrategia de recuperación de errores
            parser.setErrorHandler(new DefaultErrorStrategy());
            
            // Analizar desde la regla inicial (programa)
            ParseTree arbol = parser.programa();
            
            long tiempoFin = System.currentTimeMillis();
            long tiempoAnalisis = tiempoFin - tiempoInicio;
            
            return new ResultadoAnalisisSintactico(arbol, manejadorErrores.getErrores(), tiempoAnalisis);
            
        } catch (Exception e) {
            // En caso de error catastrófico
            List<ErrorSintactico> errores = new ArrayList<>();
            errores.add(new ErrorSintactico(0, 0, "Error catastrófico: " + e.getMessage(), "", "programa"));
            
            long tiempoFin = System.currentTimeMillis();
            return new ResultadoAnalisisSintactico(null, errores, tiempoFin - tiempoInicio);
        }
    }
    
    /**
     * Realiza análisis sintáctico con opciones avanzadas
     */
    public static ResultadoAnalisisSintactico analizarConOpciones(String codigoFuente, 
                                                                boolean modoRecuperacion,
                                                                boolean trazaDetallada) {
        long tiempoInicio = System.currentTimeMillis();
        
        try {
            CharStream input = CharStreams.fromString(codigoFuente);
            compiladorLexer lexer = new compiladorLexer(input);
            
            // Configurar traza si se solicita
            if (trazaDetallada) {
                lexer.removeErrorListeners();
                lexer.addErrorListener(ConsoleErrorListener.INSTANCE);
            }
            
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            compiladorParser parser = new compiladorParser(tokens);
            
            ManejadorErroresSintacticos manejadorErrores = new ManejadorErroresSintacticos();
            parser.removeErrorListeners();
            parser.addErrorListener(manejadorErrores);
            
            // Configurar estrategia de recuperación
            if (modoRecuperacion) {
                parser.setErrorHandler(new DefaultErrorStrategy());
            } else {
                parser.setErrorHandler(new BailErrorStrategy()); // Fallar rápido
            }
            
            // Traza detallada si se solicita
            if (trazaDetallada) {
                parser.setTrace(true);
            }
            
            ParseTree arbol = parser.programa();
            
            long tiempoFin = System.currentTimeMillis();
            return new ResultadoAnalisisSintactico(arbol, manejadorErrores.getErrores(), 
                                                 tiempoFin - tiempoInicio);
            
        } catch (Exception e) {
            List<ErrorSintactico> errores = new ArrayList<>();
            errores.add(new ErrorSintactico(0, 0, "Error: " + e.getMessage(), "", "programa"));
            return new ResultadoAnalisisSintactico(null, errores, System.currentTimeMillis() - tiempoInicio);
        }
    }
    
    /**
     * Obtiene información estadística del árbol sintáctico
     */
    public static Map<String, Integer> obtenerEstadisticasArbol(ParseTree arbol) {
        Map<String, Integer> estadisticas = new HashMap<>();
        
        if (arbol != null) {
            contarTiposNodos(arbol, estadisticas);
        }
        
        return estadisticas;
    }
    
    private static void contarTiposNodos(ParseTree nodo, Map<String, Integer> conteo) {
        if (nodo == null) return;
        
        String tipoNodo = nodo.getClass().getSimpleName().replace("Context", "");
        conteo.put(tipoNodo, conteo.getOrDefault(tipoNodo, 0) + 1);
        
        for (int i = 0; i < nodo.getChildCount(); i++) {
            contarTiposNodos(nodo.getChild(i), conteo);
        }
    }
    
    /**
     * Lee un archivo y retorna su contenido
     */
    private static String leerArchivo(String rutaArchivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        return contenido.toString();
    }
    
}