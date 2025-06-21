package tptc;

import org.antlr.v4.runtime.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Analizador Léxico Mejorado para el compilador C++
 * Responsabilidad: Procesar código fuente y detectar errores léxicos
 * específicos
 */
public class AnalizadorLexico {

    // Patrones para detectar errores léxicos comunes
    private static final Pattern IDENTIFICADOR_INVALIDO_PATTERN = Pattern.compile("\\d+[a-zA-Z_][a-zA-Z0-9_]*");
    private static final Pattern DECIMAL_INVALIDO_PATTERN = Pattern.compile("\\d+\\.|\\.|\\d+\\.\\d*\\.\\d*");
    private static final Pattern CARACTER_INVALIDO_PATTERN = Pattern.compile("'([^'\\r\\n]|\\\\.)*('')?|'");

    // Clase para almacenar información de cada token
    public static class TokenInfo {
        private int numero;
        private String lexema;
        private String tipo;
        private int linea;
        private int columna;
        private boolean esError;
        private String mensajeError;

        public TokenInfo(int numero, String lexema, String tipo, int linea, int columna, boolean esError) {
            this.numero = numero;
            this.lexema = lexema;
            this.tipo = tipo;
            this.linea = linea;
            this.columna = columna;
            this.esError = esError;
            this.mensajeError = esError ? generarMensajeError(lexema, tipo) : null;
        }

        private String generarMensajeError(String lexema, String tipo) {
            switch (tipo) {
                case "IDENTIFICADOR_INVALIDO":
                    return "Identificador inválido: no puede comenzar con dígitos";
                case "DECIMAL_INVALIDO":
                    return "Número decimal mal formado";
                case "CARACTER_INVALIDO":
                    return "Literal de carácter mal formado";
                case "ERROR_LEXICO":
                    return "Secuencia de caracteres no reconocida";
                default:
                    return "Token no válido";
            }
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

        public String getMensajeError() {
            return mensajeError;
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
        private List<String> tiposErrores;

        public ResultadoAnalisis(List<TokenInfo> tokens, Map<String, Integer> distribucionTipos) {
            this.tokens = tokens;
            this.distribucionTipos = distribucionTipos;
            this.totalTokens = tokens.size();
            this.tokensConError = (int) tokens.stream().mapToInt(t -> t.esError() ? 1 : 0).sum();
            this.tokensValidos = totalTokens - tokensConError;
            this.exitoso = tokensConError == 0;
            this.tiposErrores = calcularTiposErrores();
        }

        private List<String> calcularTiposErrores() {
            Set<String> tipos = new HashSet<>();
            for (TokenInfo token : tokens) {
                if (token.esError()) {
                    tipos.add(token.getMensajeError());
                }
            }
            return new ArrayList<>(tipos);
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

        public List<String> getTiposErrores() {
            return tiposErrores;
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
     * Procesa los tokens del código fuente con detección mejorada de errores
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

            // Determinar si es un error léxico usando el tipo del token y validaciones
            // adicionales
            boolean esError = esTokenError(lexema, tipoToken) || validarErroresEspecificos(lexema);

            // Crear información del token
            TokenInfo info = new TokenInfo(numeroToken, lexema, tipoToken, linea, columna, esError);
            tokens.add(info);

            // Contar tipos de tokens
            String tipoConteo = esError ? "ERROR_" + tipoToken : tipoToken;
            conteoTipos.put(tipoConteo, conteoTipos.getOrDefault(tipoConteo, 0) + 1);

            numeroToken++;
        }

        return new ResultadoAnalisis(tokens, conteoTipos);
    }

    /**
     * Validaciones específicas para errores léxicos comunes
     */
    private static boolean validarErroresEspecificos(String lexema) {
        // Validar identificadores que empiezan con número
        if (IDENTIFICADOR_INVALIDO_PATTERN.matcher(lexema).matches()) {
            return true;
        }

        // Validar decimales mal formados
        if (DECIMAL_INVALIDO_PATTERN.matcher(lexema).matches()) {
            return true;
        }

        // Validar caracteres mal formados
        if (CARACTER_INVALIDO_PATTERN.matcher(lexema).matches()) {
            return true;
        }

        return false;
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
        // Tipos específicos de error definidos en la gramática
        Set<String> tiposError = Set.of(
                "IDENTIFICADOR_INVALIDO",
                "DECIMAL_INVALIDO",
                "CARACTER_INVALIDO",
                "ERROR_LEXICO",
                "DESCONOCIDO");

        return tiposError.contains(tipo) ||
                tipo.equals("DESCONOCIDO") ||
                lexema.matches(".*[@#$^~`].*"); // Caracteres claramente inválidos
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