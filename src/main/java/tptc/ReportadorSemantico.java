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
        if (tabla == null) {
            System.out.println("Tabla de símbolos no disponible");
            return;
        }

        System.out.println("=== TABLA DE SÍMBOLOS ===");
        System.out.printf("%-15s %-10s %-15s %-10s %-10s %-15s %-20s%n",
                "NOMBRE", "TIPO", "CATEGORÍA", "LÍNEA", "COLUMNA", "ÁMBITO", "DETALLES");
        System.out.println("-".repeat(92));

        List<FilaSimbolo> filas = new ArrayList<>();

        for (SimboloVariable variable : tabla.getTodasLasVariables()) {
            String categoria = variable.esParametro() ? "parametro" : "variable";
            StringBuilder detalles = new StringBuilder();
            if (variable.esArreglo()) {
                detalles.append(String.format("[arr:%d] ", variable.getTamanioArreglo()));
            }
            detalles.append("[private]");

            filas.add(new FilaSimbolo(variable.getNombre(), variable.getTipoDato(), categoria,
                    variable.getLinea(), variable.getColumna(), variable.getAmbito(), detalles.toString().trim()));
        }

        for (SimboloFuncion funcion : tabla.getTodasLasFunciones()) {
            String detallePrivacidad = "[private]";
            String parametros = "";
            if (!funcion.getParametros().isEmpty()) {
                parametros = " [" + funcion.getParametros().stream()
                        .map(SimboloVariable::getTipoDato)
                        .reduce((a, b) -> a + ", " + b).orElse("") + "]";
            }

            filas.add(new FilaSimbolo(funcion.getNombre(), funcion.getTipoRetorno(), "funcion",
                    funcion.getLinea(), funcion.getColumna(), "global", detallePrivacidad + parametros));
        }

        filas.sort((a, b) -> {
            int cmp = Integer.compare(a.linea, b.linea);
            if (cmp == 0) {
                cmp = a.nombre.compareTo(b.nombre);
            }
            return cmp;
        });

        for (FilaSimbolo fila : filas) {
            System.out.printf("%-15s %-10s %-15s %-10d %-10d %-15s %-20s%n",
                    fila.nombre,
                    fila.tipo,
                    fila.categoria,
                    fila.linea,
                    fila.columna,
                    fila.ambito,
                    fila.detalles);
        }
    }

    private static class FilaSimbolo {
        String nombre;
        String tipo;
        String categoria;
        int linea;
        int columna;
        String ambito;
        String detalles;

        FilaSimbolo(String nombre, String tipo, String categoria, int linea, int columna, String ambito,
                String detalles) {
            this.nombre = nombre;
            this.tipo = tipo;
            this.categoria = categoria;
            this.linea = linea;
            this.columna = columna;
            this.ambito = ambito;
            this.detalles = detalles;
        }
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