package tptc;

import org.antlr.v4.runtime.*;
import java.io.*;
import java.util.*;

/**
 * Analizador Léxico para el compilador C++
 * Responsabilidad: Procesar código fuente y generar tabla de tokens
 */
public class AnalizadorLexico {

    // Clase para almacenar información de cada token
    public static class TokenInfo {
        private int numero;
        private String lexema;
        private String tipo;
        private int linea;
        private int columna;
        private boolean esError;

        public TokenInfo(int numero, String lexema, String tipo, int linea, int columna, boolean esError) {
            this.numero = numero;
            this.lexema = lexema;
            this.tipo = tipo;
            this.linea = linea;
            this.columna = columna;
            this.esError = esError;
        }

        // Getters
        public int getNumero() {
            return numero;
        }

        public String getLexema() {
            return lexema;
        }

        public String getTipo() {
            return tipo;
        }

        public int getLinea() {
            return linea;
        }

        public int getColumna() {
            return columna;
        }

        public boolean esError() {
            return esError;
        }
    }

    // Clase para almacenar resultados del análisis
    public static class ResultadoAnalisis {
        private List<TokenInfo> tokens;
        private int totalTokens;
        private int tokensValidos;
        private int tokensConError;
        private Map<String, Integer> distribucionTipos;
        private boolean exitoso;

        public ResultadoAnalisis(List<TokenInfo> tokens, Map<String, Integer> distribucionTipos) {
            this.tokens = tokens;
            this.distribucionTipos = distribucionTipos;
            this.totalTokens = tokens.size();
            this.tokensConError = (int) tokens.stream().mapToInt(t -> t.esError() ? 1 : 0).sum();
            this.tokensValidos = totalTokens - tokensConError;
            this.exitoso = tokensConError == 0;
        }

        // Getters
        public List<TokenInfo> getTokens() {
            return tokens;
        }

        public int getTotalTokens() {
            return totalTokens;
        }

        public int getTokensValidos() {
            return tokensValidos;
        }

        public int getTokensConError() {
            return tokensConError;
        }

        public Map<String, Integer> getDistribucionTipos() {
            return distribucionTipos;
        }

        public boolean fueExitoso() {
            return exitoso;
        }

        public double getPorcentajeExito() {
            return totalTokens > 0 ? ((double) tokensValidos / totalTokens) * 100 : 0;
        }
    }

    /**
     * Analiza el contenido de un archivo y retorna los resultados
     */
    public static ResultadoAnalisis analizarArchivo(String rutaArchivo) throws IOException {
        String contenido = leerArchivo(rutaArchivo);
        return analizarCodigo(contenido);
    }

    /**
     * Analiza código fuente directamente y retorna los resultados
     */
    public static ResultadoAnalisis analizarCodigo(String codigoFuente) {
        CharStream input = CharStreams.fromString(codigoFuente);
        return procesarTokens(input);
    }

    /**
     * Lee un archivo y retorna su contenido como String
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

    /**
     * Procesa los tokens del código fuente
     */
    private static ResultadoAnalisis procesarTokens(CharStream input) {
        compiladorLexer lexer = new compiladorLexer(input);

        List<TokenInfo> tokens = new ArrayList<>();
        Map<String, Integer> conteoTipos = new HashMap<>();

        Token token;
        int numeroToken = 1;

        // Procesar todos los tokens hasta EOF
        while ((token = lexer.nextToken()).getType() != Token.EOF) {
            String lexema = token.getText();
            String tipoToken = obtenerNombreToken(lexer, token.getType());
            int linea = token.getLine();
            int columna = token.getCharPositionInLine() + 1;

            // Determinar si es un error léxico
            boolean esError = esTokenError(lexema, tipoToken);

            // Crear información del token
            TokenInfo info = new TokenInfo(numeroToken, lexema, tipoToken, linea, columna, esError);
            tokens.add(info);

            // Contar tipos de tokens
            conteoTipos.put(tipoToken, conteoTipos.getOrDefault(tipoToken, 0) + 1);

            numeroToken++;
        }

        return new ResultadoAnalisis(tokens, conteoTipos);
    }

    /**
     * Obtiene el nombre del token desde el vocabulario del lexer
     */
    private static String obtenerNombreToken(compiladorLexer lexer, int tipoToken) {
        String nombre = lexer.getVocabulary().getSymbolicName(tipoToken);

        if (nombre == null) {
            String literal = lexer.getVocabulary().getLiteralName(tipoToken);
            if (literal != null) {
                nombre = literal.replace("'", "");
            } else {
                nombre = "DESCONOCIDO";
            }
        }

        return nombre;
    }

    /**
     * Determina si un token es un error léxico
     */
    private static boolean esTokenError(String lexema, String tipo) {
        // Considerar como error si:
        // 1. El tipo es desconocido
        // 2. Contiene caracteres claramente inválidos
        return tipo.equals("DESCONOCIDO") ||
                lexema.matches(".*[@#$^~`].*"); // Caracteres no válidos en la gramática
    }

    /**
     * Utilidad para truncar texto largo
     */
    public static String truncarTexto(String texto, int maxLongitud) {
        if (texto.length() <= maxLongitud) {
            return texto;
        }
        return texto.substring(0, maxLongitud - 3) + "...";
    }
}