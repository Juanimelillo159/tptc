package tptc;

/**
 * Representa un error semántico encontrado durante el análisis semántico.
 * Incluye diferenciación entre errores críticos y advertencias (warnings).
 */
public class ErrorSemantico {
    private final int linea;
    private final int columna;
    private final String mensaje;
    private final String contexto;
    private final TipoError tipo;
    private final String simboloInvolucrado;
    private final String tipoDatoEsperado;
    private final String tipoDatoEncontrado;

    public enum TipoError {
        ERROR("Error Crítico"),
        WARNING("Advertencia");

        private final String nombre;

        TipoError(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }
    }

    /**
     * Constructor completo para errores semánticos
     */
    public ErrorSemantico(int linea, int columna, String mensaje, String contexto,
                        TipoError tipo, String simboloInvolucrado, 
                        String tipoDatoEsperado, String tipoDatoEncontrado) {
        this.linea = linea;
        this.columna = columna;
        this.mensaje = mensaje;
        this.contexto = contexto;
        this.tipo = tipo;
        this.simboloInvolucrado = simboloInvolucrado;
        this.tipoDatoEsperado = tipoDatoEsperado;
        this.tipoDatoEncontrado = tipoDatoEncontrado;
    }

    /**
     * Constructor simplificado para errores sin información de tipos
     */
    public ErrorSemantico(int linea, int columna, String mensaje, 
                        String contexto, TipoError tipo) {
        this(linea, columna, mensaje, contexto, tipo, null, null, null);
    }

    // Getters
    public int getLinea() { return linea; }
    public int getColumna() { return columna; }
    public String getMensaje() { return mensaje; }
    public String getContexto() { return contexto; }
    public TipoError getTipo() { return tipo; }
    public String getSimboloInvolucrado() { return simboloInvolucrado; }
    public String getTipoDatoEsperado() { return tipoDatoEsperado; }
    public String getTipoDatoEncontrado() { return tipoDatoEncontrado; }
    public boolean esError() { return tipo == TipoError.ERROR; }
    public boolean esWarning() { return tipo == TipoError.WARNING; }

    /**
     * Representación detallada del error
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append(tipo.getNombre()).append(" Semántico");
        sb.append(" en Línea ").append(linea);
        sb.append(", Col ").append(columna);
        sb.append(" [").append(contexto).append("]: ");
        sb.append(mensaje);
        
        if (simboloInvolucrado != null) {
            sb.append("\n   Símbolo: ").append(simboloInvolucrado);
        }
        
        if (tipoDatoEsperado != null && tipoDatoEncontrado != null) {
            sb.append("\n   Tipos: Esperado=").append(tipoDatoEsperado);
            sb.append(", Encontrado=").append(tipoDatoEncontrado);
        }
        
        return sb.toString();
    }

    /**
     * Versión compacta para listados
     */
    public String toCompactString() {
        return String.format("%s|L%d:C%d|%s|%s", 
            tipo.name(), linea, columna, contexto, mensaje);
    }

    /**
     * Factory method para errores de variable no declarada
     */
    public static ErrorSemantico crearErrorVariableNoDeclarada(int linea, int columna, 
            String nombreVariable, String contexto) {
        return new ErrorSemantico(
            linea, columna,
            "Variable '" + nombreVariable + "' no declarada en este ámbito",
            contexto,
            TipoError.ERROR,
            nombreVariable,
            null, null
        );
    }

    /**
     * Factory method para errores de tipo incompatible
     */
    public static ErrorSemantico crearErrorTipoIncompatible(int linea, int columna,
            String operacion, String tipoEsperado, String tipoEncontrado, 
            String contexto) {
        return new ErrorSemantico(
            linea, columna,
            "Tipo incompatible en " + operacion,
            contexto,
            TipoError.ERROR,
            null,
            tipoEsperado, tipoEncontrado
        );
    }

    /**
     * Factory method para advertencias de conversión implícita
     */
    public static ErrorSemantico crearWarningConversionImplicita(int linea, int columna,
            String tipoOrigen, String tipoDestino, String contexto) {
        return new ErrorSemantico(
            linea, columna,
            "Conversión implícita de " + tipoOrigen + " a " + tipoDestino,
            contexto,
            TipoError.WARNING,
            null,
            tipoDestino, tipoOrigen
        );
    }
}