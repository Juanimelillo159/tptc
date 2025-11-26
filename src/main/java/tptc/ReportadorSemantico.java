package tptc;

import java.util.*;

/**
 * Reportador de resultados del análisis semántico
 */
public class ReportadorSemantico {

    /**
     * Muestra el reporte completo del análisis semántico
     */
    public static void mostrarReporteCompleto(String nombreArchivo,
                                              AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        mostrarEncabezado(nombreArchivo);
        mostrarResumenGeneral(resultado);

        if (!resultado.fueExitoso()) {
            mostrarErroresCriticos(resultado.getErrores());
        }

        if (!resultado.getWarnings().isEmpty()) {
            mostrarWarnings(resultado.getWarnings());
        }

        mostrarTablaSimbolos(resultado.getTablaSimbolos());

        // Mostrar sugerencias de mejora
        mostrarSugerenciasMejora(resultado);
    }

    /**
     * Muestra solo errores y warnings CON COLORES
     */
    public static void mostrarSoloErrores(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        if (!resultado.fueExitoso()) {
            System.out.println(ColoresConsole.rojo("❌ ERRORES SEMÁNTICOS CRÍTICOS:"));
            System.out.println(ColoresConsole.cyan("═".repeat(60)));
            mostrarErroresCriticos(resultado.getErrores());
        }

        if (!resultado.getWarnings().isEmpty()) {
            System.out.println(ColoresConsole.amarillo("\n⚠️  WARNINGS (NO CRÍTICOS):"));
            System.out.println(ColoresConsole.cyan("═".repeat(60)));
            mostrarWarnings(resultado.getWarnings());
        }

        if (resultado.fueExitoso() && resultado.getWarnings().isEmpty()) {
            System.out.println(ColoresConsole.verde("✅ No se encontraron errores semánticos"));
        }
    }

    /**
     * Muestra reporte resumido CON COLORES
     */
    public static void mostrarReporteResumido(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println(ColoresConsole.azul("📋 RESUMEN DEL ANÁLISIS SEMÁNTICO:"));
        System.out.println(ColoresConsole.cyan("─".repeat(40)));

        if (resultado.fueExitoso()) {
            System.out.println(ColoresConsole.verde("🟢 Estado: EXITOSO"));
        } else {
            System.out.println(ColoresConsole.rojo("🔴 Estado: CON ERRORES"));
            System.out.println(ColoresConsole.rojo("❌ Errores críticos: " + resultado.getNumeroErrores()));
        }

        System.out.println(ColoresConsole.amarillo("⚠️  Warnings: " + resultado.getNumeroWarnings()));
        System.out.println(ColoresConsole.cyan("⏱️  Tiempo: " + resultado.getTiempoAnalisis() + " ms"));

        // Mostrar estadísticas básicas de la tabla de símbolos
        Map<String, Integer> stats = resultado.getTablaSimbolos().getEstadisticas();
        System.out.println(ColoresConsole.cyan("📊 Variables: " + stats.get("totalVariables")));
        System.out.println(ColoresConsole.cyan("🔧 Funciones: " + stats.get("totalFunciones")));
    }

    private static void mostrarEncabezado(String nombreArchivo) {
        System.out.println(ColoresConsole.AZUL + "╔══════════════════════════════════════════════════════════════╗" + ColoresConsole.RESET);
        System.out.println(ColoresConsole.AZUL + "║                   ANÁLISIS SEMÁNTICO C++                    ║" + ColoresConsole.RESET);
        System.out.println(ColoresConsole.AZUL + "║                   Técnicas de Compilación                   ║" + ColoresConsole.RESET);
        System.out.println(ColoresConsole.AZUL + "╚══════════════════════════════════════════════════════════════╝" + ColoresConsole.RESET);
        System.out.println();
        System.out.println(ColoresConsole.cyan("📂 Archivo analizado: " + nombreArchivo));
        System.out.println();
    }

    private static void mostrarResumenGeneral(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println(ColoresConsole.azul("📊 RESUMEN GENERAL:"));
        System.out.println(ColoresConsole.cyan("═".repeat(50)));

        if (resultado.fueExitoso()) {
            System.out.println(ColoresConsole.verde("🎉 ¡ANÁLISIS SEMÁNTICO EXITOSO!"));
            System.out.println(ColoresConsole.verde("✅ El programa es semánticamente correcto"));
        } else {
            System.out.println(ColoresConsole.rojo("❌ ERRORES SEMÁNTICOS DETECTADOS"));
            System.out.println(ColoresConsole.rojo("🚫 El programa contiene errores que impiden la compilación"));
        }

        System.out.println();
        System.out.printf(ColoresConsole.rojo("• Errores críticos: %d%n"), resultado.getNumeroErrores());
        System.out.printf(ColoresConsole.amarillo("• Warnings: %d%n"), resultado.getNumeroWarnings());
        System.out.printf(ColoresConsole.cyan("• Tiempo de análisis: %d ms%n"), resultado.getTiempoAnalisis());
        System.out.println();
        System.out.println(ColoresConsole.cyan("═".repeat(50)));
        System.out.println();
    }

    private static void mostrarErroresCriticos(List<ErrorSemantico> errores) {
        System.out.println(ColoresConsole.rojo("🚨 ERRORES CRÍTICOS:"));
        System.out.println(ColoresConsole.cyan("═".repeat(60)));

        // Agrupar errores por línea
        Map<Integer, List<ErrorSemantico>> erroresPorLinea = new TreeMap<>();
        for (ErrorSemantico error : errores) {
            erroresPorLinea.computeIfAbsent(error.getLinea(), k -> new ArrayList<>()).add(error);
        }

        for (Map.Entry<Integer, List<ErrorSemantico>> entry : erroresPorLinea.entrySet()) {
            int linea = entry.getKey();
            List<ErrorSemantico> erroresLinea = entry.getValue();

            System.out.printf(ColoresConsole.rojo("📍 LÍNEA %d:%n"), linea);
            for (int i = 0; i < erroresLinea.size(); i++) {
                ErrorSemantico error = erroresLinea.get(i);
                System.out.printf(ColoresConsole.rojo("   %d. %s%n"), i + 1, error.toString());
                System.out.printf(ColoresConsole.amarillo("      💡 %s%n"), error.getSugerencia());
                if (i < erroresLinea.size() - 1) {
                    System.out.println();
                }
            }
            System.out.println();
        }

        System.out.println(ColoresConsole.cyan("═".repeat(60)));

        // Mostrar resumen de tipos de errores
        mostrarResumenTiposErrores(errores, true);
    }

    private static void mostrarWarnings(List<ErrorSemantico> warnings) {
        System.out.println(ColoresConsole.amarillo("⚠️  WARNINGS (RECOMENDACIONES):"));
        System.out.println(ColoresConsole.cyan("═".repeat(60)));

        // Agrupar warnings por tipo
        Map<ErrorSemantico.TipoError, List<ErrorSemantico>> warningsPorTipo = new HashMap<>();
        for (ErrorSemantico warning : warnings) {
            warningsPorTipo.computeIfAbsent(warning.getTipo(), k -> new ArrayList<>()).add(warning);
        }

        for (Map.Entry<ErrorSemantico.TipoError, List<ErrorSemantico>> entry : warningsPorTipo.entrySet()) {
            List<ErrorSemantico> warningsDelTipo = entry.getValue();

            System.out.printf(ColoresConsole.amarillo("🔸 %s (%d ocurrencias):%n"),
                    warningsDelTipo.get(0).getDescripcionTipo(), warningsDelTipo.size());

            for (ErrorSemantico warning : warningsDelTipo) {
                System.out.printf(ColoresConsole.amarillo("   • Línea %d, Col %d: %s%n"),
                        warning.getLinea(), warning.getColumna(), warning.getMensaje());
            }
            System.out.printf(ColoresConsole.cyan("   💡 %s%n%n"), warningsDelTipo.get(0).getSugerencia());
        }

        System.out.println(ColoresConsole.cyan("═".repeat(60)));
    }

    /**
     * Muestra la tabla de símbolos con el formato:
     * NOMBRE TIPO CATEGORÍA LÍNEA COLUMNA ÁMBITO DETALLES
     */
    private static void mostrarTablaSimbolos(TablaSimbolos tabla) {
        System.out.println(ColoresConsole.azul("=== TABLA DE SÍMBOLOS ==="));
        System.out.printf(ColoresConsole.negrita("%-15s %-10s %-13s %-10s %-10s %-15s %s%n"),
                "NOMBRE", "TIPO", "CATEGORÍA", "LÍNEA", "COLUMNA", "ÁMBITO", "DETALLES");
        System.out.println(ColoresConsole.cyan("--------------------------------------------------------------------------------------------"));

        // Construimos una lista combinada de símbolos (funciones + variables/parámetros)
        List<Simbolo> simbolos = new ArrayList<>();

        simbolos.addAll(tabla.getTodasLasFunciones());
        simbolos.addAll(tabla.getTodasLasVariables());

        // Ordenar por línea y columna para que quede “de arriba a abajo”
        simbolos.sort(Comparator
                .comparingInt(Simbolo::getLinea)
                .thenComparingInt(Simbolo::getColumna));

        for (Simbolo s : simbolos) {
            String nombre = s.getNombre();
            String tipoDato = s.getTipoDato();
            String categoria;
            String ambito = s.getAmbito() == null ? "global" : s.getAmbito();
            String detalles = "";

            if (s instanceof SimboloFuncion) {
                SimboloFuncion f = (SimboloFuncion) s;
                categoria = "funcion";
                detalles = "[private] " + formatearListaParametros(f);
            } else if (s instanceof SimboloVariable) {
                SimboloVariable v = (SimboloVariable) s;
                categoria = v.esParametro() ? "parametro" : "variable";

                if (v.esArreglo()) {
                    detalles += "[arr:" + v.getTamanioArreglo() + "] ";
                }
                detalles += "[private]";
            } else {
                categoria = s.getTipo().toString().toLowerCase();
                detalles = "";
            }

            System.out.printf("%-15s %-10s %-13s %-10d %-10d %-15s %s%n",
                    nombre,
                    tipoDato,
                    categoria,
                    s.getLinea(),
                    s.getColumna(),
                    ambito,
                    detalles);
        }

        System.out.println();
    }

    private static String formatearListaParametros(SimboloFuncion f) {
        if (f.getParametros().isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < f.getParametros().size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(f.getParametros().get(i).getTipoDato());
        }
        sb.append("]");
        return sb.toString();
    }

    private static void mostrarResumenTiposErrores(List<ErrorSemantico> errores, boolean sonCriticos) {
        Map<ErrorSemantico.TipoError, Integer> conteoTipos = new HashMap<>();

        for (ErrorSemantico error : errores) {
            conteoTipos.put(error.getTipo(), conteoTipos.getOrDefault(error.getTipo(), 0) + 1);
        }

        if (conteoTipos.size() > 1) {
            String titulo = sonCriticos ? "TIPOS DE ERRORES MÁS FRECUENTES" : "TIPOS DE WARNINGS MÁS FRECUENTES";
            System.out.println(ColoresConsole.azul("📊 " + titulo + ":"));
            System.out.println(ColoresConsole.cyan("-".repeat(40)));

            conteoTipos.entrySet().stream()
                    .sorted(Map.Entry.<ErrorSemantico.TipoError, Integer>comparingByValue().reversed())
                    .forEach(entry -> {
                        ErrorSemantico ejemplo = errores.stream()
                                .filter(e -> e.getTipo() == entry.getKey())
                                .findFirst().orElse(null);

                        if (ejemplo != null) {
                            System.out.printf(ColoresConsole.cyan("   %-30s: %d%n"),
                                    ejemplo.getDescripcionTipo(), entry.getValue());
                        }
                    });
            System.out.println();
        }
    }

    /**
     * Muestra sugerencias de mejora basadas en el análisis CON COLORES
     */
    public static void mostrarSugerenciasMejora(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println(ColoresConsole.azul("💡 SUGERENCIAS DE MEJORA:"));
        System.out.println(ColoresConsole.cyan("═".repeat(50)));

        Map<String, Integer> stats = resultado.getTablaSimbolos().getEstadisticas();
        boolean haySugerencias = false;

        // Sugerencias basadas en variables no utilizadas
        int varsNoUtilizadas = stats.get("totalVariables") - stats.get("variablesUtilizadas");
        if (varsNoUtilizadas > 0) {
            System.out.printf(ColoresConsole.amarillo("🗑️  Elimine %d variable(s) que no se utilizan para limpiar el código%n"),
                    varsNoUtilizadas);
            haySugerencias = true;
        }

        // Sugerencias basadas en inicialización
        int varsNoInicializadas = stats.get("totalVariables") - stats.get("variablesInicializadas");
        if (varsNoInicializadas > 0) {
            System.out.printf(ColoresConsole.amarillo("🔧 Inicialice %d variable(s) al declararlas para evitar errores%n"), varsNoInicializadas);
            haySugerencias = true;
        }

        // Sugerencias basadas en warnings
        if (resultado.getNumeroWarnings() > 0) {
            System.out.printf(ColoresConsole.amarillo("⚠️  Revise los %d warnings para mejorar la calidad del código%n"),
                    resultado.getNumeroWarnings());
            haySugerencias = true;
        }

        // Sugerencias basadas en complejidad
        if (stats.get("nivelMaximoAmbito") > 3) {
            System.out.println(ColoresConsole.amarillo("📐 Considere simplificar la estructura del código (muchos niveles de anidamiento)"));
            haySugerencias = true;
        }

        if (!haySugerencias) {
            System.out.println(ColoresConsole.verde("🎉 ¡Excelente! No hay sugerencias específicas de mejora."));
            System.out.println(ColoresConsole.verde("    Su código tiene una buena estructura semántica."));
        }

        System.out.println(ColoresConsole.cyan("═".repeat(50)));
    }

    /**
     * Exporta el reporte a archivo (sin colores para archivo de texto)
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
            writer.println("Errores críticos: " + resultado.getNumeroErrores());
            writer.println("Warnings: " + resultado.getNumeroWarnings());
            writer.println("Tiempo de análisis: " + resultado.getTiempoAnalisis() + " ms");
            writer.println();

            // Errores
            if (!resultado.getErrores().isEmpty()) {
                writer.println("ERRORES CRÍTICOS:");
                writer.println("-".repeat(40));
                for (ErrorSemantico error : resultado.getErrores()) {
                    writer.println("• " + error.toStringDetallado());
                    writer.println();
                }
            }

            // Warnings
            if (!resultado.getWarnings().isEmpty()) {
                writer.println("WARNINGS:");
                writer.println("-".repeat(40));
                for (ErrorSemantico warning : resultado.getWarnings()) {
                    writer.println("• " + warning.toStringDetallado());
                    writer.println();
                }
            }

            // Tabla de símbolos (MISMO FORMATO PERO SIN COLORES)
            writer.println("TABLA DE SÍMBOLOS:");
            writer.println("NOMBRE          TIPO       CATEGORÍA       LÍNEA      COLUMNA    ÁMBITO          DETALLES");
            writer.println("--------------------------------------------------------------------------------------------");

            List<Simbolo> simbolos = new ArrayList<>();
            simbolos.addAll(resultado.getTablaSimbolos().getTodasLasFunciones());
            simbolos.addAll(resultado.getTablaSimbolos().getTodasLasVariables());

            simbolos.sort(Comparator
                    .comparingInt(Simbolo::getLinea)
                    .thenComparingInt(Simbolo::getColumna));

            for (Simbolo s : simbolos) {
                String nombre = s.getNombre();
                String tipoDato = s.getTipoDato();
                String categoria;
                String ambito = s.getAmbito() == null ? "global" : s.getAmbito();
                String detalles = "";

                if (s instanceof SimboloFuncion) {
                    SimboloFuncion f = (SimboloFuncion) s;
                    categoria = "funcion";
                    detalles = "[private] " + formatearListaParametros(f);
                } else if (s instanceof SimboloVariable) {
                    SimboloVariable v = (SimboloVariable) s;
                    categoria = v.esParametro() ? "parametro" : "variable";
                    if (v.esArreglo()) {
                        detalles += "[arr:" + v.getTamanioArreglo() + "] ";
                    }
                    detalles += "[private]";
                } else {
                    categoria = s.getTipo().toString().toLowerCase();
                }

                writer.printf("%-15s %-10s %-13s %-10d %-10d %-15s %s%n",
                        nombre, tipoDato, categoria, s.getLinea(), s.getColumna(), ambito, detalles);
            }

            writer.println();
            writer.println("ESTADÍSTICAS:");
            writer.println("-".repeat(40));
            Map<String, Integer> stats = resultado.getTablaSimbolos().getEstadisticas();
            stats.forEach((key, value) -> writer.println(key + ": " + value));

            writer.println();
            writer.println("=".repeat(60));
            writer.println("Fin del reporte");

            System.out.println(ColoresConsole.verde("✅ Reporte semántico exportado a: " + rutaExportacion));

        } catch (java.io.IOException e) {
            System.err.println(ColoresConsole.rojo("❌ Error al exportar reporte semántico: " + e.getMessage()));
        }
    }
}
