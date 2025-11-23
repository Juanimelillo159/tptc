package tptc;

public class ANSI {
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // Colores brillantes / Bold
    public static final String RED_BOLD = "\u001B[1;31m";
    public static final String GREEN_BOLD = "\u001B[1;32m";
    public static final String YELLOW_BOLD = "\u001B[1;33m";

    public static String color(String texto, String color) {
        return color + texto + RESET;
    }

    public static String error(String texto) {
        return color(texto, RED_BOLD);
    }

    public static String success(String texto) {
        return color(texto, GREEN_BOLD);
    }

    public static String warning(String texto) {
        return color(texto, YELLOW_BOLD);
    }
}