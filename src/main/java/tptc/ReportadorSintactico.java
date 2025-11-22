package tptc;

import java.util.*;

/**
 * Reportador de resultados del análisis sintáctico
 * Responsabilidad: Formatear y mostrar resultados del análisis sintáctico
 */
public class ReportadorSintactico {

    /**
     * Muestra el reporte completo del análisis sintáctico
     */
    public static void mostrarReporteCompleto(String nombreArchivo,
            AnalizadorSintactico.ResultadoAnalisisSintactico resultado) {
        mostrarEncabezadoSintactico(nombreArchivo);
        mostrarResumenAnalisis(resultado);

        if (resultado.fueExitoso()) {
            mostrarEstadisticasDetalladas(resultado);
            mostrarCodigoIntermedio(resultado.getCodigoIntermedio());
        } else {
            mostrarErroresSintacticos(resultado.getErrores());
        }

    
    }

    /**
     * Muestra solo los errores sintácticos (para reportes de error)
     */
    public static void mostrarSoloErrores(AnalizadorSintactico.ResultadoAnalisisSintactico resultado) {
        if (!resultado.fueExitoso()) {
            System.out.println("❌ ERRORES SINTÁCTICOS ENCONTRADOS:");
            System.out.println("═".repeat(60));
            mostrarErroresSintacticos(resultado.getErrores());
        } else {
            System.out.println("✅ No se encontraron errores sintácticos");
        }
    }

    /**
     * Muestra reporte resumido del análisis
     */
    public static void mostrarReporteResumido(AnalizadorSintactico.ResultadoAnalisisSintactico resultado) {
        System.out.println("📋 RESUMEN DEL ANÁLISIS SINTÁCTICO:");
        System.out.println("─".repeat(40));

        if (resultado.fueExitoso()) {
            System.out.println("🟢 Estado: EXITOSO");
            System.out.println("🌳 Nodos en AST: " + resultado.getNumeroNodos());
            System.out.println("📏 Profundidad: " + resultado.getProfundidadMaxima());
            System.out.println("⏱️  Tiempo: " + resultado.getTiempoAnalisis() + " ms");
        } else {
            System.out.println("🔴 Estado: CON ERRORES");
            System.out.println("❌ Errores encontrados: " + resultado.getErrores().size());
            System.out.println("⏱️  Tiempo: " + resultado.getTiempoAnalisis() + " ms");
        }
    }

    /**
     * Exporta reporte a archivo de texto
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
            writer.println("Errores encontrados: " + resultado.getErrores().size());
            writer.println();

            if (!resultado.fueExitoso()) {
                writer.println("ERRORES SINTÁCTICOS:");
                writer.println("-".repeat(40));
                for (AnalizadorSintactico.ErrorSintactico error : resultado.getErrores()) {
                    writer.println("• " + error.toString());
                    writer.println("  Contexto: " + error.getContexto());
                    writer.println();
                }
            }

            writer.println();
            writer.println("=".repeat(60));
            writer.println("Fin del reporte");

            System.out.println("✅ Reporte exportado a: " + rutaExportacion);

        } catch (java.io.IOException e) {
            System.err.println("❌ Error al exportar reporte: " + e.getMessage());
        }
    }

    private static void mostrarEncabezadoSintactico(String nombreArchivo) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ANÁLISIS SINTÁCTICO C++                   ║");
        System.out.println("║                   Técnicas de Compilación                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("📂 Archivo analizado: " + nombreArchivo);
        System.out.println();
    }

    private static void mostrarResumenAnalisis(AnalizadorSintactico.ResultadoAnalisisSintactico resultado) {
        System.out.println("📊 RESUMEN DEL ANÁLISIS:");
        System.out.println("═".repeat(50));

        if (resultado.fueExitoso()) {
            System.out.println("🎉 ¡ANÁLISIS SINTÁCTICO EXITOSO!");
            System.out.println("✅ El programa tiene una estructura sintáctica válida");
            System.out.println();
        } else {
            System.out.println("❌ ERRORES SINTÁCTICOS DETECTADOS");
            System.out.println("🚫 El programa contiene errores de sintaxis");
            System.out.println();

            // Mostrar distribución de errores por línea
            Map<Integer, Integer> erroresPorLinea = new HashMap<>();
            for (AnalizadorSintactico.ErrorSintactico error : resultado.getErrores()) {
                erroresPorLinea.put(error.getLinea(),
                        erroresPorLinea.getOrDefault(error.getLinea(), 0) + 1);
            }

            if (erroresPorLinea.size() > 1) {
                System.out.println("   📍 Líneas afectadas: " + erroresPorLinea.size());
            }
        }

        System.out.println("═".repeat(50));
        System.out.println();
    }


    private static void mostrarErroresSintacticos(List<AnalizadorSintactico.ErrorSintactico> errores) {
        System.out.println("🚨 ERRORES SINTÁCTICOS DETALLADOS:");
        System.out.println("═".repeat(60));

        // Agrupar errores por línea para mejor presentación
        Map<Integer, List<AnalizadorSintactico.ErrorSintactico>> erroresPorLinea = new HashMap<>();
        for (AnalizadorSintactico.ErrorSintactico error : errores) {
            erroresPorLinea.computeIfAbsent(error.getLinea(), k -> new ArrayList<>()).add(error);
        }

        // Mostrar errores ordenados por línea
        erroresPorLinea.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    int linea = entry.getKey();
                    List<AnalizadorSintactico.ErrorSintactico> erroresLinea = entry.getValue();

                    System.out.println("📍 LÍNEA " + linea + ":");
                    System.out.println("-".repeat(30));

                    for (int i = 0; i < erroresLinea.size(); i++) {
                        AnalizadorSintactico.ErrorSintactico error = erroresLinea.get(i);
                        System.out.printf("   %d. Columna %d: %s%n",
                                i + 1, error.getColumna(), error.getMensaje());
                        System.out.println("      Token problemático: '" + error.getTokenOfensivo() + "'");
                        System.out.println("      Contexto: " + error.getContexto());

                        // Sugerir posibles soluciones
                        String sugerencia = generarSugerencia(error);
                        if (!sugerencia.isEmpty()) {
                            System.out.println("      💡 Sugerencia: " + sugerencia);
                        }

                        if (i < erroresLinea.size() - 1) {
                            System.out.println();
                        }
                    }
                    System.out.println();
                });

        System.out.println("═".repeat(60));

        // Mostrar resumen de tipos de errores más comunes
        mostrarResumenTiposErrores(errores);
    }

    private static void mostrarCodigoIntermedio(List<String> codigo) {
        if (codigo == null || codigo.isEmpty()) return;
    
        System.out.println("\n🔄 CÓDIGO INTERMEDIO GENERADO (3AC):");
        System.out.println("═".repeat(60));
        codigo.forEach(linea -> System.out.println("  " + linea));
        System.out.println("═".repeat(60));
    }

    private static void mostrarEstadisticasDetalladas(AnalizadorSintactico.ResultadoAnalisisSintactico resultado) {
        System.out.println("📈 ANÁLISIS DETALLADO DEL PROGRAMA:");
        System.out.println("═".repeat(50));

        // Obtener estadísticas del árbol
        Map<String, Integer> estadisticasArbol = AnalizadorSintactico
                .obtenerEstadisticasArbol(resultado.getArbolSintactico());

        // Categorizar los nodos por tipo de construcción
        Map<String, Integer> construcciones = categorizarConstrucciones(estadisticasArbol);

        System.out.println("🏗️  CONSTRUCCIONES DETECTADAS:");
        System.out.println("-".repeat(35));

        if (construcciones.isEmpty()) {
            System.out.println("   (No se detectaron construcciones específicas)");
        } else {
            construcciones.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEach(entry -> {
                        String construccion = entry.getKey();
                        int cantidad = entry.getValue();
                        String icono = obtenerIconoConstruccion(construccion);
                        System.out.printf("   %s %-20s: %d%n", icono, construccion, cantidad);
                    });
        }

        System.out.println();
        System.out.println("   Nodos por nivel: " + (resultado.getNumeroNodos() / resultado.getProfundidadMaxima()));
        System.out.println("═".repeat(50));
    }

    private static String generarSugerencia(AnalizadorSintactico.ErrorSintactico error) {
        String mensaje = error.getMensaje().toLowerCase();
        String token = error.getTokenOfensivo();

        if (mensaje.contains("missing") && mensaje.contains("';'")) {
            return "Agrega un punto y coma (;) al final de la declaración";
        }
        if (mensaje.contains("missing") && mensaje.contains("'}'")) {
            return "Falta una llave de cierre (})";
        }
        if (mensaje.contains("missing") && mensaje.contains("')'")) {
            return "Falta un paréntesis de cierre ())";
        }
        if (mensaje.contains("extraneous")) {
            return "Elimina el token '" + token + "' que está de más";
        }
        if (mensaje.contains("expecting")) {
            return "Revisa la sintaxis, se esperaba otro token";
        }
        if (token.equals("=") && error.getContexto().contains("declaracion")) {
            return "Verifica la sintaxis de declaración e inicialización";
        }

        return ""; // Sin sugerencia específica
    }

    private static void mostrarResumenTiposErrores(List<AnalizadorSintactico.ErrorSintactico> errores) {
        Map<String, Integer> tiposErrores = new HashMap<>();

        for (AnalizadorSintactico.ErrorSintactico error : errores) {
            String tipo = categorizarError(error);
            tiposErrores.put(tipo, tiposErrores.getOrDefault(tipo, 0) + 1);
        }

        if (tiposErrores.size() > 1) {
            System.out.println("📊 TIPOS DE ERRORES MÁS FRECUENTES:");
            System.out.println("-".repeat(40));

            tiposErrores.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .forEach(entry -> {
                        System.out.printf("   %-25s: %d%n", entry.getKey(), entry.getValue());
                    });
            System.out.println();
        }
    }

    private static String categorizarError(AnalizadorSintactico.ErrorSintactico error) {
        String mensaje = error.getMensaje().toLowerCase();

        if (mensaje.contains("missing"))
            return "Símbolos faltantes";
        if (mensaje.contains("extraneous"))
            return "Símbolos extra";
        if (mensaje.contains("expecting"))
            return "Tokens inesperados";
        if (mensaje.contains("no viable"))
            return "Secuencias inválidas";

        return "Otros errores";
    }

    private static Map<String, Integer> categorizarConstrucciones(Map<String, Integer> estadisticas) {
        Map<String, Integer> construcciones = new HashMap<>();

        // Categorizar según los nombres de las reglas de tu gramática
        for (Map.Entry<String, Integer> entry : estadisticas.entrySet()) {
            String nodo = entry.getKey().toLowerCase();
            int cantidad = entry.getValue();

            if (nodo.contains("declaracion")) {
                construcciones.put("Declaraciones", construcciones.getOrDefault("Declaraciones", 0) + cantidad);
            } else if (nodo.contains("asignacion")) {
                construcciones.put("Asignaciones", construcciones.getOrDefault("Asignaciones", 0) + cantidad);
            } else if (nodo.contains("if")) {
                construcciones.put("Condicionales", construcciones.getOrDefault("Condicionales", 0) + cantidad);
            } else if (nodo.contains("while")) {
                construcciones.put("Bucles while", construcciones.getOrDefault("Bucles while", 0) + cantidad);
            } else if (nodo.contains("for")) {
                construcciones.put("Bucles for", construcciones.getOrDefault("Bucles for", 0) + cantidad);
            } else if (nodo.contains("funcion")) {
                construcciones.put("Funciones", construcciones.getOrDefault("Funciones", 0) + cantidad);
            } else if (nodo.contains("expresion") || nodo.contains("factor") || nodo.contains("term")) {
                construcciones.put("Expresiones", construcciones.getOrDefault("Expresiones", 0) + cantidad);
            }
        }

        return construcciones;
    }

    private static String obtenerIconoConstruccion(String construccion) {
        switch (construccion) {
            case "Declaraciones":
                return "📝";
            case "Asignaciones":
                return "➡️ ";
            case "Condicionales":
                return "🔀";
            case "Bucles while":
                return "🔄";
            case "Bucles for":
                return "🔁";
            case "Funciones":
                return "⚙️ ";
            case "Expresiones":
                return "🧮";
            default:
                return "🔹";
        }
    }
}