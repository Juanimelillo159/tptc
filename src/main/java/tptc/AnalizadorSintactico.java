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
    
    // ===================== CLASE ERROR SINTÁCTICO =====================
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
    
    // ===================== LISTENER DE ERRORES =====================
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
                
                if (recognizer instanceof Parser) {
                    Parser parser = (Parser) recognizer;
                    contexto = obtenerContexto(parser, e);
                }
            }
            
            ErrorSintactico error = new ErrorSintactico(
                line,
                charPositionInLine + 1,
                msg,
                tokenOfensivo,
                contexto
            );
            errores.add(error);
        }
        
        private String obtenerContexto(Parser parser, RecognitionException e) {
            if (e != null && e.getCtx() != null) {
                return e.getCtx().getClass().getSimpleName().replace("Context", "");
            }
            
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
    
    // ===================== RESULTADO DEL ANÁLISIS =====================
    public static class ResultadoAnalisisSintactico {
        private ParseTree arbolSintactico;
        private List<ErrorSintactico> errores;
        private boolean exitoso;
        private long tiempoAnalisis;
        private int numeroNodos;
        private int profundidadMaxima;

        // Opcional: guardar el código intermedio / optimizado si querés
        private List<String> codigoIntermedioGenerado;
        private List<String> codigoOptimizadoGenerado;
        
        public ResultadoAnalisisSintactico(ParseTree arbol,
                                           List<ErrorSintactico> errores, 
                                           long tiempoAnalisis) {
            this.arbolSintactico = arbol;
            this.errores = errores;
            this.exitoso = errores.isEmpty();
            this.tiempoAnalisis = tiempoAnalisis;
            this.numeroNodos = contarNodos(arbol);
            this.profundidadMaxima = calcularProfundidad(arbol);
            this.codigoIntermedioGenerado = new ArrayList<>();
            this.codigoOptimizadoGenerado = new ArrayList<>();
        }

        // --------- FASE 5 + 6: CÓDIGO INTERMEDIO Y OPTIMIZACIÓN ---------

        /**
         * Genera el código intermedio y el código optimizado a partir del
         * árbol sintáctico, mostrando la salida por consola y exportando
         * ambos a archivos .txt usando el nombre base indicado.
         *
         * Ejemplo: baseNombreArchivo = "ejemplo_correcto"
         * -> "ejemplo_correcto_codigo_intermedio.txt"
         * -> "ejemplo_correcto_codigo_optimizado.txt"
         */
        public void generarCodigoIntermedioYOptimizado(String baseNombreArchivo) {
            if (!exitoso || arbolSintactico == null) {
                System.out.println(ColoresConsole.rojo(
                    "❌ No se puede generar código intermedio: el análisis sintáctico tuvo errores."));
                return;
            }

            // ===== 5. GENERACIÓN DE CÓDIGO INTERMEDIO =====
            GeneradorCodigoIntermedio generador = new GeneradorCodigoIntermedio();
            generador.visit(arbolSintactico);

            // Mostrar por consola (con el formato tipo ejemplo)
            generador.mostrarCodigo();

            // Exportar a archivo
            String archivoIntermedio = baseNombreArchivo + "_codigo_intermedio.txt";
            generador.exportarComoTxt(archivoIntermedio);

            this.codigoIntermedioGenerado = generador.getCodigoIntermedio();

            // ===== 6. OPTIMIZACIÓN DE CÓDIGO =====
            OptimizadorCodigoIntermedio optimizador =
                    new OptimizadorCodigoIntermedio(this.codigoIntermedioGenerado);

            optimizador.optimizar();
            optimizador.mostrarResumen();
            optimizador.mostrarCodigoOptimizadoEnConsola();

            String archivoOptimizado = baseNombreArchivo + "_codigo_optimizado.txt";
            optimizador.exportarComoTxt(archivoOptimizado);

            this.codigoOptimizadoGenerado = optimizador.getCodigoOptimizado();
        }

        // --------- helpers internos para estadísticas del árbol ---------

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
        
        // --------- Getters ---------
        public ParseTree getArbolSintactico() { return arbolSintactico; }
        public List<ErrorSintactico> getErrores() { return errores; }
        public boolean fueExitoso() { return exitoso; }
        public long getTiempoAnalisis() { return tiempoAnalisis; }
        public int getNumeroNodos() { return numeroNodos; }
        public int getProfundidadMaxima() { return profundidadMaxima; }

        public List<String> getCodigoIntermedioGenerado() {
            return new ArrayList<>(codigoIntermedioGenerado);
        }

        public List<String> getCodigoOptimizadoGenerado() {
            return new ArrayList<>(codigoOptimizadoGenerado);
        }
    }
    
    // ===================== MÉTODOS PÚBLICOS DE ANÁLISIS =====================

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
            
            // Estrategia de recuperación de errores
            parser.setErrorHandler(new DefaultErrorStrategy());
            
            // Analizar desde la regla inicial (programa)
            ParseTree arbol = parser.programa();
            
            long tiempoFin = System.currentTimeMillis();
            long tiempoAnalisis = tiempoFin - tiempoInicio;
            
            return new ResultadoAnalisisSintactico(
                    arbol,
                    manejadorErrores.getErrores(),
                    tiempoAnalisis
            );
            
        } catch (Exception e) {
            List<ErrorSintactico> errores = new ArrayList<>();
            errores.add(new ErrorSintactico(
                    0, 0,
                    "Error catastrófico: " + e.getMessage(),
                    "",
                    "programa"
            ));
            
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
            
            if (trazaDetallada) {
                lexer.removeErrorListeners();
                lexer.addErrorListener(ConsoleErrorListener.INSTANCE);
            }
            
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            compiladorParser parser = new compiladorParser(tokens);
            
            ManejadorErroresSintacticos manejadorErrores = new ManejadorErroresSintacticos();
            parser.removeErrorListeners();
            parser.addErrorListener(manejadorErrores);
            
            if (modoRecuperacion) {
                parser.setErrorHandler(new DefaultErrorStrategy());
            } else {
                parser.setErrorHandler(new BailErrorStrategy()); // Fallar rápido
            }
            
            if (trazaDetallada) {
                parser.setTrace(true);
            }
            
            ParseTree arbol = parser.programa();
            
            long tiempoFin = System.currentTimeMillis();
            return new ResultadoAnalisisSintactico(
                    arbol,
                    manejadorErrores.getErrores(), 
                    tiempoFin - tiempoInicio
            );
            
        } catch (Exception e) {
            List<ErrorSintactico> errores = new ArrayList<>();
            errores.add(new ErrorSintactico(
                    0, 0,
                    "Error: " + e.getMessage(),
                    "",
                    "programa"
            ));
            return new ResultadoAnalisisSintactico(
                    null,
                    errores,
                    System.currentTimeMillis() - tiempoInicio
            );
        }
    }
    
    // ===================== UTILIDADES =====================

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
