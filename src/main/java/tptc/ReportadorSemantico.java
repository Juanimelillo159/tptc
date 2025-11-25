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

    private static void mostrarTablaSimbolos(TablaSimbolos tabla) {
        System.out.println(ColoresConsole.azul("📋 TABLA DE SÍMBOLOS:"));
        System.out.println(ColoresConsole.cyan("═".repeat(80)));

        // Mostrar funciones
        Collection<SimboloFuncion> funciones = tabla.getTodasLasFunciones();
        if (!funciones.isEmpty()) {
            System.out.println(ColoresConsole.cyan("🔧 FUNCIONES:"));
            System.out.println(ColoresConsole.cyan("-".repeat(40)));
            for (SimboloFuncion funcion : funciones) {
                String estado = funcion.isUtilizado() ? ColoresConsole.verde("✅") : ColoresConsole.amarillo("⚠️ ");
                String tieneReturn = funcion.tieneReturn() ? ColoresConsole.verde("✓") : ColoresConsole.rojo("✗");
                System.out.printf(ColoresConsole.cyan("   %s %s (línea %d)%n"), estado, funcion.getSignatura(), funcion.getLinea());
                System.out.printf(ColoresConsole.cyan("      Return: %s | Parámetros: %d%n"),
                        tieneReturn, funcion.getNumeroParametros());
            }
            System.out.println();
        }

        // Mostrar variables
        List<SimboloVariable> variables = tabla.getTodasLasVariables();
        if (!variables.isEmpty()) {
            System.out.println(ColoresConsole.cyan("📊 VARIABLES:"));
            System.out.println(ColoresConsole.cyan("-".repeat(60)));
            System.out.printf(ColoresConsole.negrita("%-15s %-8s %-5s %-10s %-10s%n"),
                    "NOMBRE", "TIPO", "LÍNEA", "INICIALIZADA", "UTILIZADA");
            System.out.println(ColoresConsole.cyan("-".repeat(60)));

            for (SimboloVariable variable : variables) {
                String inicializada = variable.isInicializada() ? ColoresConsole.verde("✓") : ColoresConsole.rojo("✗");
                String utilizada = variable.isUtilizado() ? ColoresConsole.verde("✓") : ColoresConsole.rojo("✗");
                String tipoDesc = variable.esParametro() ? variable.getTipoDato() + "*" : variable.getTipoDato();

                System.out.printf("%-15s %-8s %-5d %-10s %-10s%n",
                        variable.getNombre(),
                        tipoDesc,
                        variable.getLinea(),
                        inicializada,
                        utilizada);
            }
            System.out.println(ColoresConsole.cyan("* = parámetro"));
            System.out.println();
        }

        System.out.println(ColoresConsole.cyan("═".repeat(80)));
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

            // Tabla de símbolos
            writer.println("TABLA DE SÍMBOLOS:");
            writer.println("-".repeat(40));
            writer.println(resultado.getTablaSimbolos().toString());

            // Estadísticas
            Map<String, Integer> stats = resultado.getTablaSimbolos().getEstadisticas();
            writer.println("ESTADÍSTICAS:");
            writer.println("-".repeat(40));
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