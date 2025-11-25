package tptc;

/**
 * Utilidades para colores en la consola usando códigos ANSI
 */
public class ColoresConsole {
    // Códigos ANSI para colores
    public static final String RESET = "\u001B[0m";
    public static final String NEGRO = "\u001B[30m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BLANCO = "\u001B[37m";
    
    // Colores de fondo
    public static final String FONDO_ROJO = "\u001B[41m";
    public static final String FONDO_VERDE = "\u001B[42m";
    public static final String FONDO_AMARILLO = "\u001B[43m";
    public static final String FONDO_AZUL = "\u001B[44m";
    
    // Estilos de texto
    public static final String NEGRITA = "\u001B[1m";
    public static final String SUBRAYADO = "\u001B[4m";

    /**
     * Aplica color verde a un texto (éxito)
     */
    public static String verde(String texto) {
        return VERDE + texto + RESET;
    }
    
    /**
     * Aplica color rojo a un texto (error)
     */
    public static String rojo(String texto) {
        return ROJO + texto + RESET;
    }
    
    /**
     * Aplica color amarillo a un texto (warning)
     */
    public static String amarillo(String texto) {
        return AMARILLO + texto + RESET;
    }
    
    /**
     * Aplica color azul a un texto (información)
     */
    public static String azul(String texto) {
        return AZUL + texto + RESET;
    }

    /**
     * Aplica color magenta a un texto
     */
    public static String magenta(String texto) {
    return MAGENTA + texto + RESET;
    }
    
    /**
     * Aplica color cyan a un texto (detalles)
     */
    public static String cyan(String texto) {
        return CYAN + texto + RESET;
    }
    
    /**
     * Aplica negrita a un texto
     */
    public static String negrita(String texto) {
        return NEGRITA + texto + RESET;
    }
    
    /**
     * Texto de éxito con icono
     */
    public static String exito(String texto) {
        return VERDE + "✅ " + texto + RESET;
    }
    
    /**
     * Texto de error con icono
     */
    public static String error(String texto) {
        return ROJO + "❌ " + texto + RESET;
    }
    
    /**
     * Texto de warning con icono
     */
    public static String warning(String texto) {
        return AMARILLO + "⚠️ " + texto + RESET;
    }
    
    /**
     * Texto de información con icono
     */
    public static String info(String texto) {
        return AZUL + "ℹ️ " + texto + RESET;
    }
    
    /**
     * Verifica si la consola soporta colores ANSI
     */
    public static boolean soportaColores() {
        return System.console() != null || 
               System.getenv("TERM") != null ||
               System.getProperty("os.name").toLowerCase().contains("win");
    }
}