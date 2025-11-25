package tptc;

import java.util.*;

/**
 * Reportador de resultados del análisis sintáctico CON COLORES
 */
public class ReportadorSintactico {

    /**
     * Muestra el reporte completo del análisis sintáctico CON COLORES
     */
    public static void mostrarReporteCompleto(String nombreArchivo,
            AnalizadorSintactico.ResultadoAnalisisSintactico resultado) {
        mostrarEncabezado(nombreArchivo);
        mostrarResumenAnalisis(resultado);

        if (!resultado.fueExitoso()) {
            mostrarErroresSintacticos(resultado.getErrores());
        }

        mostrarAnalisisDetallado(resultado);
        
        // Mostrar código intermedio optimizado si está disponible
        if (resultado.fueExitoso() && resultado.getCodigoIntermedio() != null) {
            mostrarCodigoIntermedio(resultado.getCodigoIntermedio());
        }
    }

    /**
     * Muestra reporte resumido CON COLORES
     */
    public static void mostrarReporteResumido(AnalizadorSintactico.ResultadoAnalisisSintactico resultado) {
        System.out.println(ColoresConsole.azul("📋 RESUMEN DEL ANÁLISIS SINTÁCTICO:"));
        System.out.println(ColoresConsole.cyan("─".repeat(40)));

        if (resultado.fueExitoso()) {
            System.out.println(ColoresConsole.verde("🟢 Estado: EXITOSO"));
        } else {
            System.out.println(ColoresConsole.rojo("🔴 Estado: CON ERRORES"));
            System.out.println(ColoresConsole.rojo("❌ Errores sintácticos: " + resultado.getErrores().size()));
        }

        System.out.println(ColoresConsole.cyan("🌳 Nodos en AST: " + resultado.getNumeroNodos()));
        System.out.println(ColoresConsole.cyan("📏 Profundidad máxima: " + resultado.getProfundidadMaxima()));
        System.out.println(ColoresConsole.cyan("⏱️  Tiempo: " + resultado.getTiempoAnalisis() + " ms"));
    }

    private static void mostrarEncabezado(String nombreArchivo) {
        System.out.println(ColoresConsole.AZUL + "╔══════════════════════════════════════════════════════════════╗" + ColoresConsole.RESET);
        System.out.println(ColoresConsole.AZUL + "║                   ANÁLISIS SINTÁCTICO C++                   ║" + ColoresConsole.RESET);
        System.out.println(ColoresConsole.AZUL + "║                   Técnicas de Compilación                   ║" + ColoresConsole.RESET);
        System.out.println(ColoresConsole.AZUL + "╚══════════════════════════════════════════════════════════════╝" + ColoresConsole.RESET);
        System.out.println();
        System.out.println(ColoresConsole.cyan("📂 Archivo analizado: " + nombreArchivo));
        System.out.println();
    }

    private static void mostrarResumenAnalisis(AnalizadorSintactico.ResultadoAnalisisSintactico resultado) {
        System.out.println(ColoresConsole.azul("📊 RESUMEN DEL ANÁLISIS:"));
        System.out.println(ColoresConsole.cyan("═".repeat(50)));

        if (resultado.fueExitoso()) {
            System.out.println(ColoresConsole.verde("🎉 ¡ANÁLISIS SINTÁCTICO EXITOSO!"));
            System.out.println(ColoresConsole.verde("✅ El programa tiene una estructura sintáctica válida"));
        } else {
            System.out.println(ColoresConsole.rojo("❌ ERRORES SINTÁCTICOS DETECTADOS"));
            System.out.println(ColoresConsole.rojo("🚫 El programa contiene errores de sintaxis"));
        }

        System.out.println();
        System.out.println(ColoresConsole.cyan("═".repeat(50)));
        System.out.println();
    }

    private static void mostrarErroresSintacticos(List<AnalizadorSintactico.ErrorSintactico> errores) {
        System.out.println(ColoresConsole.rojo("🚨 ERRORES SINTÁCTICOS:"));
        System.out.println(ColoresConsole.cyan("═".repeat(70)));

        // Agrupar errores por línea
        Map<Integer, List<AnalizadorSintactico.ErrorSintactico>> erroresPorLinea = new TreeMap<>();
        for (AnalizadorSintactico.ErrorSintactico error : errores) {
            erroresPorLinea.computeIfAbsent(error.getLinea(), k -> new ArrayList<>()).add(error);
        }

        for (Map.Entry<Integer, List<AnalizadorSintactico.ErrorSintactico>> entry : erroresPorLinea.entrySet()) {
            int linea = entry.getKey();
            List<AnalizadorSintactico.ErrorSintactico> erroresLinea = entry.getValue();

            System.out.printf(ColoresConsole.rojo("📍 LÍNEA %d:%n"), linea);
            for (int i = 0; i < erroresLinea.size(); i++) {
                AnalizadorSintactico.ErrorSintactico error = erroresLinea.get(i);
                System.out.printf(ColoresConsole.rojo("   %d. %s%n"), i + 1, error.toString());
                System.out.printf(ColoresConsole.amarillo("      Token: '%s' | Contexto: %s%n"), 
                    error.getTokenOfensivo(), error.getContexto());
                if (i < erroresLinea.size() - 1) {
                    System.out.println();
                }
            }
            System.out.println();
        }

        System.out.println(ColoresConsole.cyan("═".repeat(70)));
        System.out.println();
    }

    private static void mostrarAnalisisDetallado(AnalizadorSintactico.ResultadoAnalisisSintactico resultado) {
        System.out.println(ColoresConsole.azul("🔍 ANÁLISIS DETALLADO DEL PROGRAMA:"));
        System.out.println(ColoresConsole.cyan("═".repeat(50)));

        if (resultado.getArbolSintactico() != null) {
            Map<String, Integer> estadisticas = AnalizadorSintactico.obtenerEstadisticasArbol(resultado.getArbolSintactico());
            
            System.out.println(ColoresConsole.cyan("🌿 CONSTRUCCIONES DETECTADAS:"));
            System.out.println(ColoresConsole.cyan("─".repeat(35)));

            // Mostrar las construcciones más importantes
            mostrarConstruccion(estadisticas, "Expresion", "Expresiones", "🧮");
            mostrarConstruccion(estadisticas, "Declaracion", "Declaraciones", "📝");
            mostrarConstruccion(estadisticas, "Funcion", "Funciones", "🔧");
            mostrarConstruccion(estadisticas, "Si", "Condicionales if", "🔀");
            mostrarConstruccion(estadisticas, "Mientras", "Bucles while", "🔄");
            mostrarConstruccion(estadisticas, "Para", "Bucles for", "⚡");

            System.out.println();
            System.out.printf(ColoresConsole.cyan("   📊 Nodos por nivel: %d%n"), resultado.getProfundidadMaxima());
        } else {
            System.out.println(ColoresConsole.amarillo("   ⚠️  No se pudo generar el árbol sintáctico"));
        }

        System.out.println(ColoresConsole.cyan("═".repeat(50)));
        System.out.println();
    }

    private static void mostrarConstruccion(Map<String, Integer> estadisticas, String clave, String descripcion, String emoji) {
        int cantidad = estadisticas.getOrDefault(clave, 0);
        if (cantidad > 0) {
            System.out.printf(ColoresConsole.cyan("   %s %-20s: %d%n"), emoji, descripcion, cantidad);
        }
    }

    private static void mostrarCodigoIntermedio(List<String> codigoIntermedio) {
        System.out.println(ColoresConsole.azul("🔄 CÓDIGO INTERMEDIO GENERADO (3AC):"));
        System.out.println(ColoresConsole.cyan("═".repeat(60)));

        if (codigoIntermedio != null && !codigoIntermedio.isEmpty()) {
            for (String linea : codigoIntermedio) {
                // Colorear diferentes tipos de instrucciones
                String lineaColoreada = colorearLineaCodigo(linea);
                System.out.println("  " + lineaColoreada);
            }
        } else {
            System.out.println(ColoresConsole.amarillo("   ⚠️  No se generó código intermedio"));
        }

        System.out.println(ColoresConsole.cyan("═".repeat(60)));
        System.out.println();
    }

    private static String colorearLineaCodigo(String linea) {
        if (linea.startsWith("INICIO") || linea.startsWith("FIN")) {
            return ColoresConsole.verde(linea);
        } else if (linea.startsWith("FUNCION")) {
            return ColoresConsole.azul(linea);
        } else if (linea.startsWith("DECLARAR")) {
            return ColoresConsole.cyan(linea);
        } else if (linea.startsWith("return")) {
            return ColoresConsole.magenta(linea);
        } else if (linea.contains("=")) {
            return ColoresConsole.amarillo(linea);
        } else if (linea.startsWith("if") || linea.startsWith("goto")) {
            return ColoresConsole.ROJO + linea + ColoresConsole.RESET;
        }
        return linea;
    }

    /**
     * Exporta el reporte a archivo (sin colores para archivo de texto)
     */
    public static void exportarReporte(String nombreArchivo,
            AnalizadorSintactico.ResultadoAnalisisSintactico resultado,
            String rutaExportacion) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(rutaExportacion))) {
            writer.println("REPORTE DE ANÁLISIS SINTÁCTICO");
            writer.println("Archivo analizado: " + nombreArchivo);
            writer.println("Fecha: " + new Date());
            writer.println("=".repeat(60));
            writer.println();

            // Resumen
            writer.println("RESUMEN:");
            writer.println("Estado: " + (resultado.fueExitoso() ? "EXITOSO" : "CON ERRORES"));
            writer.println("Nodos en AST: " + resultado.getNumeroNodos());
            writer.println("Profundidad máxima: " + resultado.getProfundidadMaxima());
            writer.println("Tiempo de análisis: " + resultado.getTiempoAnalisis() + " ms");
            writer.println("Errores sintácticos: " + resultado.getErrores().size());
            writer.println();

            // Errores
            if (!resultado.getErrores().isEmpty()) {
                writer.println("ERRORES SINTÁCTICOS:");
                writer.println("-".repeat(40));
                for (AnalizadorSintactico.ErrorSintactico error : resultado.getErrores()) {
                    writer.println("• " + error.toString());
                    writer.println("  Token: '" + error.getTokenOfensivo() + "' | Contexto: " + error.getContexto());
                    writer.println();
                }
            }

            // Estadísticas del árbol
            if (resultado.getArbolSintactico() != null) {
                writer.println("ESTADÍSTICAS DEL ÁRBOL SINTÁCTICO:");
                writer.println("-".repeat(40));
                Map<String, Integer> estadisticas = AnalizadorSintactico.obtenerEstadisticasArbol(resultado.getArbolSintactico());
                for (Map.Entry<String, Integer> entry : estadisticas.entrySet()) {
                    writer.println(entry.getKey() + ": " + entry.getValue());
                }
                writer.println();
            }

            // Código intermedio
            if (resultado.getCodigoIntermedio() != null && !resultado.getCodigoIntermedio().isEmpty()) {
                writer.println("CÓDIGO INTERMEDIO GENERADO:");
                writer.println("-".repeat(40));
                for (String linea : resultado.getCodigoIntermedio()) {
                    writer.println(linea);
                }
            }

            writer.println();
            writer.println("=".repeat(60));
            writer.println("Fin del reporte");

            System.out.println(ColoresConsole.verde("✅ Reporte sintáctico exportado a: " + rutaExportacion));

        } catch (java.io.IOException e) {
            System.err.println(ColoresConsole.rojo("❌ Error al exportar reporte sintáctico: " + e.getMessage()));
        }
    }
}