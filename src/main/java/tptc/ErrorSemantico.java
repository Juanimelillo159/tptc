package tptc;

/**
 * Representa un error semántico o warning
 */
public class ErrorSemantico {
    public enum TipoError {
        // Errores críticos
        VARIABLE_NO_DECLARADA,
        FUNCION_NO_DECLARADA,
        REDEFINICION_VARIABLE,
        REDEFINICION_FUNCION,
        TIPOS_INCOMPATIBLES,
        VARIABLE_NO_INICIALIZADA,
        NUMERO_ARGUMENTOS_INCORRECTO,
        TIPO_RETORNO_INCORRECTO,
        FUNCION_SIN_RETURN,
        FUNCION_MAIN_FALTANTE,
        RETURN_FUERA_DE_FUNCION,
        BREAK_CONTINUE_FUERA_DE_BUCLE,

        // Warnings (no críticos)
        VARIABLE_NO_UTILIZADA,
        FUNCION_NO_UTILIZADA,
        CONVERSION_TIPO_IMPLICITA,
        PARAMETRO_NO_UTILIZADO,
        CODIGO_INALCANZABLE,
        ASIGNACION_INNECESARIA
    }

    private TipoError tipo;
    private String mensaje;
    private int linea;
    private int columna;
    private String contexto;
    private boolean esCritico;

    public ErrorSemantico(TipoError tipo, String mensaje, int linea, int columna, String contexto) {
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.linea = linea;
        this.columna = columna;
        this.contexto = contexto != null ? contexto : "";
        this.esCritico = determinarSiEsCritico(tipo);
    }

    public ErrorSemantico(TipoError tipo, String mensaje, int linea, int columna) {
        this(tipo, mensaje, linea, columna, null);
    }

    private boolean determinarSiEsCritico(TipoError tipo) {
        switch (tipo) {
            case VARIABLE_NO_UTILIZADA:
            case FUNCION_NO_UTILIZADA:
            case CONVERSION_TIPO_IMPLICITA:
            case PARAMETRO_NO_UTILIZADO:
            case CODIGO_INALCANZABLE:
            case ASIGNACION_INNECESARIA:
                return false; // Warnings
            default:
                return true; // Errores críticos
        }
    }

    // Getters
    public TipoError getTipo() {
        return tipo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public String getContexto() {
        return contexto;
    }

    public boolean esCritico() {
        return esCritico;
    }

    public boolean esWarning() {
        return !esCritico;
    }

    public String getTipoString() {
        return esCritico ? "ERROR" : "WARNING";
    }

    public String getDescripcionTipo() {
        switch (tipo) {
            case VARIABLE_NO_DECLARADA:
                return "Variable no declarada";
            case FUNCION_NO_DECLARADA:
                return "Función no declarada";
            case REDEFINICION_VARIABLE:
                return "Redefinición de variable";
            case REDEFINICION_FUNCION:
                return "Redefinición de función";
            case TIPOS_INCOMPATIBLES:
                return "Tipos incompatibles";
            case VARIABLE_NO_INICIALIZADA:
                return "Variable no inicializada";
            case NUMERO_ARGUMENTOS_INCORRECTO:
                return "Número incorrecto de argumentos";
            case TIPO_RETORNO_INCORRECTO:
                return "Tipo de retorno incorrecto";
            case FUNCION_SIN_RETURN:
                return "Función sin declaración return";
            case FUNCION_MAIN_FALTANTE:
                return "Función main faltante";
            case RETURN_FUERA_DE_FUNCION:
                return "Return fuera de función";
            case BREAK_CONTINUE_FUERA_DE_BUCLE:
                return "Break/Continue fuera de bucle";
            case VARIABLE_NO_UTILIZADA:
                return "Variable declarada pero no utilizada";
            case FUNCION_NO_UTILIZADA:
                return "Función declarada pero no utilizada";
            case CONVERSION_TIPO_IMPLICITA:
                return "Conversión implícita de tipos";
            case PARAMETRO_NO_UTILIZADO:
                return "Parámetro no utilizado";
            case CODIGO_INALCANZABLE:
                return "Código inalcanzable";
            case ASIGNACION_INNECESARIA:
                return "Asignación innecesaria";
            default:
                return "Error desconocido";
        }
    }

    public String getSugerencia() {
        switch (tipo) {
            case VARIABLE_NO_DECLARADA:
                return "Declare la variable antes de usarla o verifique la ortografía";
            case FUNCION_NO_DECLARADA:
                return "Declare la función antes de usarla o verifique la ortografía";
            case REDEFINICION_VARIABLE:
                return "Use un nombre diferente para la variable o elimine una de las declaraciones";
            case REDEFINICION_FUNCION:
                return "Use un nombre diferente para la función o elimine una de las declaraciones";
            case TIPOS_INCOMPATIBLES:
                return "Asegúrese de que los tipos sean compatibles o use conversión explícita";
            case VARIABLE_NO_INICIALIZADA:
                return "Inicialice la variable antes de usarla";
            case NUMERO_ARGUMENTOS_INCORRECTO:
                return "Verifique la signatura de la función y proporcione el número correcto de argumentos";
            case TIPO_RETORNO_INCORRECTO:
                return "Asegúrese de que el tipo de retorno coincida con la declaración de la función";
            case FUNCION_SIN_RETURN:
                return "Agregue una declaración return al final de la función";
            case VARIABLE_NO_UTILIZADA:
                return "Elimine la variable si no la necesita o úsela en su código";
            case FUNCION_NO_UTILIZADA:
                return "Elimine la función si no la necesita o úsela en su código";
            case CONVERSION_TIPO_IMPLICITA:
                return "Use conversión explícita para evitar pérdida de precisión";
            default:
                return "Revise la documentación del lenguaje";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Formato: [TIPO] Línea X, Col Y: Descripción - Mensaje
        sb.append(String.format("[%s] Línea %d, Col %d: %s",
                getTipoString(), linea, columna, getDescripcionTipo()));

        if (!mensaje.isEmpty()) {
            sb.append(" - ").append(mensaje);
        }

        if (!contexto.isEmpty()) {
            sb.append(String.format(" (en contexto: %s)", contexto));
        }

        return sb.toString();
    }

    public String toStringDetallado() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString()).append("\n");
        sb.append("   Sugerencia: ").append(getSugerencia());
        return sb.toString();
    }
}