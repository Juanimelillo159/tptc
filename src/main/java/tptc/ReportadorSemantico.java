package tptc;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Reportador de resultados del análisis semántico
 * Responsabilidad: Formatear y mostrar resultados del análisis semántico
 */
public class ReportadorSemantico {

    /**
     * Muestra el reporte completo del análisis semántico
     */
    public static void mostrarReporteCompleto(String nombreArchivo,
            AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        mostrarEncabezadoSemantico(nombreArchivo);
        mostrarResumenAnalisis(resultado);

        if (resultado.fueExitoso()) {
            mostrarTablaSimbolos(resultado.getTablaSimbolos());
            mostrarEstadisticasDetalladas(resultado);
        } else {
            mostrarErroresSemanticos(resultado.getErrores());
        }

        mostrarPieReporte();
    }

    /**
     * Muestra solo los errores semánticos (para reportes de error)
     */
    public static void mostrarSoloErrores(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        if (!resultado.fueExitoso()) {
            System.out.println("❌ ERRORES SEMÁNTICOS ENCONTRADOS:");
            System.out.println("═".repeat(60));
            mostrarErroresSemanticos(resultado.getErrores());
        } else {
            System.out.println("✅ No se encontraron errores semánticos");
        }
    }

    /**
     * Muestra reporte resumido del análisis semántico
     */
    public static void mostrarReporteResumido(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println("📋 RESUMEN DEL ANÁLISIS SEMÁNTICO:");
        System.out.println("─".repeat(40));

        if (resultado.fueExitoso()) {
            System.out.println("🟢 Estado: EXITOSO");
            System.out.println("📊 Variables declaradas: " + resultado.getTotalVariables());
            System.out.println("⚙️  Funciones declaradas: " + resultado.getTotalFunciones());
            System.out.println("⏱️  Tiempo: " + resultado.getTiempoAnalisis() + " ms");
        } else {
            System.out.println("🔴 Estado: CON ERRORES");
            System.out.println("❌ Errores críticos: " + resultado.getTotalErrores());
            System.out.println("⚠️  Advertencias: " + resultado.getTotalWarnings());
            System.out.println("⏱️  Tiempo: " + resultado.getTiempoAnalisis() + " ms");
        }
    }

    /**
     * Exporta reporte a archivo de texto
     */
    public static void exportarReporte(String nombreArchivo,
            AnalizadorSemantico.ResultadoAnalisisSemantico resultado,
            String rutaExportacion) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(rutaExportacion))) {
            writer.println("REPORTE DE ANÁLISIS SEMÁNTICO");
            writer.println("Archivo analizado: " + nombreArchivo);
            writer.println("Fecha: " + new Date());
            writer.println("=".repeat(60));
            writer.println();

            // Resumen
            writer.println("RESUMEN:");
            writer.println("Estado: " + (resultado.fueExitoso() ? "EXITOSO" : "CON ERRORES"));
            writer.println("Variables declaradas: " + resultado.getTotalVariables());
            writer.println("Funciones declaradas: " + resultado.getTotalFunciones());
            writer.println("Tiempo de análisis: " + resultado.getTiempoAnalisis() + " ms");
            writer.println("Errores encontrados: " + resultado.getTotalErrores());
            writer.println("Advertencias: " + resultado.getTotalWarnings());
            writer.println();

            if (!resultado.fueExitoso()) {
                writer.println("ERRORES SEMÁNTICOS:");
                writer.println("-".repeat(40));
                for (AnalizadorSemantico.ErrorSemantico error : resultado.getErrores()) {
                    writer.println("• " + error);
                    writer.println();
                }
            }

            // Tabla de símbolos
            writer.println("TABLA DE SÍMBOLOS:");
            writer.println("-".repeat(40));
            for (AnalizadorSemantico.Simbolo simbolo : resultado.getTablaSimbolos()) {
                writer.printf("• %-15s (%-10s) | Línea: %-4d | Ámbito: %d%n",
                        simbolo.getNombre(),
                        simbolo.getTipo(),
                        simbolo.getLineaDeclaracion(),
                        simbolo.getAmbito());
            }

            writer.println();
            writer.println("=".repeat(60));
            writer.println("Fin del reporte");

            System.out.println("✅ Reporte semántico exportado a: " + rutaExportacion);

        } catch (java.io.IOException e) {
            System.err.println("❌ Error al exportar reporte semántico: " + e.getMessage());
        }
    }

    private static void mostrarEncabezadoSemantico(String nombreArchivo) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ANÁLISIS SEMÁNTICO C++                    ║");
        System.out.println("║                   Técnicas de Compilación                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("📂 Archivo analizado: " + nombreArchivo);
        System.out.println();
    }

    private static void mostrarResumenAnalisis(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println("📊 RESUMEN DEL ANÁLISIS:");
        System.out.println("═".repeat(50));

        if (resultado.fueExitoso()) {
            System.out.println("🎉 ¡ANÁLISIS SEMÁNTICO EXITOSO!");
            System.out.println("✅ El programa es semánticamente correcto");
            System.out.println();
        } else {
            System.out.println("❌ PROBLEMAS SEMÁNTICOS DETECTADOS");
            System.out.println("🚫 El programa contiene errores semánticos");
            System.out.println();

            // Mostrar distribución de errores por tipo
            Map<AnalizadorSemantico.ErrorSemantico.TipoError, Long> conteoErrores = resultado.getErrores().stream()
                .collect(Collectors.groupingBy(
                    AnalizadorSemantico.ErrorSemantico::getTipo,
                    Collectors.counting()
                ));

            System.out.println("   📍 Errores críticos: " + conteoErrores.getOrDefault(
                AnalizadorSemantico.ErrorSemantico.TipoError.ERROR, 0L));
            System.out.println("   ⚠️  Advertencias: " + conteoErrores.getOrDefault(
                AnalizadorSemantico.ErrorSemantico.TipoError.WARNING, 0L));
        }

        System.out.println("═".repeat(50));
        System.out.println();
    }

    private static void mostrarErroresSemanticos(List<AnalizadorSemantico.ErrorSemantico> errores) {
        System.out.println("🚨 ERRORES SEMÁNTICOS DETALLADOS:");
        System.out.println("═".repeat(60));

        // Separar errores y warnings
        List<AnalizadorSemantico.ErrorSemantico> erroresCriticos = errores.stream()
            .filter(AnalizadorSemantico.ErrorSemantico::esError)
            .sorted(Comparator.comparingInt(AnalizadorSemantico.ErrorSemantico::getLinea))
            .collect(Collectors.toList());

        List<AnalizadorSemantico.ErrorSemantico> advertencias = errores.stream()
            .filter(AnalizadorSemantico.ErrorSemantico::esWarning)
            .sorted(Comparator.comparingInt(AnalizadorSemantico.ErrorSemantico::getLinea))
            .collect(Collectors.toList());

        // Mostrar errores críticos primero
        if (!erroresCriticos.isEmpty()) {
            System.out.println("🔴 ERRORES CRÍTICOS:");
            System.out.println("-".repeat(30));
            mostrarListaErrores(erroresCriticos);
            System.out.println();
        }

        // Luego mostrar advertencias
        if (!advertencias.isEmpty()) {
            System.out.println("🟠 ADVERTENCIAS:");
            System.out.println("-".repeat(30));
            mostrarListaErrores(advertencias);
            System.out.println();
        }

        System.out.println("═".repeat(60));

        // Mostrar resumen de tipos de errores
        mostrarResumenTiposErrores(errores);
    }

    private static void mostrarListaErrores(List<AnalizadorSemantico.ErrorSemantico> errores) {
        // Agrupar errores por línea para mejor presentación
        Map<Integer, List<AnalizadorSemantico.ErrorSemantico>> erroresPorLinea = new TreeMap<>();
        for (AnalizadorSemantico.ErrorSemantico error : errores) {
            erroresPorLinea.computeIfAbsent(error.getLinea(), _ -> new ArrayList<>()).add(error);
        }

        // Mostrar errores ordenados por línea
        erroresPorLinea.forEach((linea, erroresLinea) -> {
            System.out.println("📍 LÍNEA " + linea + ":");
            System.out.println("-".repeat(30));

            for (int i = 0; i < erroresLinea.size(); i++) {
                AnalizadorSemantico.ErrorSemantico error = erroresLinea.get(i);
                System.out.printf("   %d. Columna %d: %s%n",
                        i + 1, error.getColumna(), error.getMensaje());
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
    }

    private static void mostrarTablaSimbolos(List<AnalizadorSemantico.Simbolo> tablaSimbolos) {
        System.out.println("📚 TABLA DE SÍMBOLOS:");
        System.out.println("═".repeat(80));
        System.out.printf("%-20s %-15s %-10s %-10s %-15s%n", 
                "Nombre", "Tipo", "Ámbito", "Línea", "Columna");
        System.out.println("-".repeat(80));

        if (tablaSimbolos.isEmpty()) {
            System.out.println("   (No se encontraron símbolos declarados)");
        } else {
            // Ordenar por ámbito y luego por nombre
            tablaSimbolos.stream()
                .sorted(Comparator.comparingInt(AnalizadorSemantico.Simbolo::getAmbito)
                .forEach(simbolo -> {
                    System.out.printf("%-20s %-15s %-10d %-10d %-15d%n",
                            simbolo.getNombre(),
                            simbolo.getTipo(),
                            simbolo.getAmbito(),
                            simbolo.getLineaDeclaracion(),
                            simbolo.getColumnaDeclaracion());
                });
        }

        System.out.println("═".repeat(80));
        System.out.println();
    }

    private static void mostrarEstadisticasDetalladas(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println("📈 ESTADÍSTICAS SEMÁNTICAS:");
        System.out.println("═".repeat(50));

        // Agrupar símbolos por tipo
        Map<String, Long> simbolosPorTipo = resultado.getTablaSimbolos().stream()
            .collect(Collectors.groupingBy(
                AnalizadorSemantico.Simbolo::getTipo,
                Collectors.counting()
            ));

        System.out.println("📦 DISTRIBUCIÓN DE SÍMBOLOS:");
        System.out.println("-".repeat(35));

        simbolosPorTipo.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(entry -> {
                String icono = obtenerIconoTipo(entry.getKey());
                System.out.printf("   %s %-15s: %d%n", icono, entry.getKey(), entry.getValue());
            });

        System.out.println();
        System.out.println("═".repeat(50));
    }

    private static String generarSugerencia(AnalizadorSemantico.ErrorSemantico error) {
        String mensaje = error.getMensaje().toLowerCase();

        if (mensaje.contains("ya declarada")) {
            return "Considera cambiar el nombre de la variable o eliminar la declaración duplicada";
        }
        if (mensaje.contains("no declarada")) {
            return "Verifica el nombre o declara la variable/función antes de usarla";
        }
        if (mensaje.contains("tipos incompatibles")) {
            return "Asegúrate que los tipos de datos sean compatibles en esta operación";
        }
        if (mensaje.contains("ámbito")) {
            return "La variable no es accesible en este contexto. Declárala en el ámbito correcto";
        }
        if (mensaje.contains("función")) {
            return "Verifica el nombre y los parámetros de la función";
        }

        return ""; // Sin sugerencia específica
    }

    private static void mostrarResumenTiposErrores(List<AnalizadorSemantico.ErrorSemantico> errores) {
        Map<String, Integer> tiposErrores = new HashMap<>();

        for (AnalizadorSemantico.ErrorSemantico error : errores) {
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

    private static String categorizarError(AnalizadorSemantico.ErrorSemantico error) {
        String mensaje = error.getMensaje().toLowerCase();

        if (mensaje.contains("declarada")) {
            return "Declaraciones duplicadas";
        }
        if (mensaje.contains("no declarada")) {
            return "Símbolos no declarados";
        }
        if (mensaje.contains("tipos incompatibles")) {
            return "Incompatibilidad de tipos";
        }
        if (mensaje.contains("ámbito")) {
            return "Problemas de ámbito";
        }
        if (mensaje.contains("función")) {
            return "Errores en funciones";
        }

        return "Otros errores semánticos";
    }

    private static String obtenerIconoTipo(String tipo) {
        switch (tipo.toLowerCase()) {
            case "variable":
                return "📌";
            case "funcion":
                return "⚙️";
            case "parametro":
                return "🔘";
            case "constante":
                return "🔒";
            default:
                return "🔹";
        }
    }

    private static void mostrarPieReporte() {
        System.out.println();
        System.out.println("═".repeat(60));
        System.out.println("💡 Opciones adicionales:");
        System.out.println("   • Exportar tabla de símbolos: exportarReporte()");
        System.out.println("   • Ver análisis completo: ejecutar con modo detallado");
        System.out.println("═".repeat(60));
    }
}