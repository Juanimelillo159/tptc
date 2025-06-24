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
    }

    /**
     * Muestra solo errores y warnings
     */
    public static void mostrarSoloErrores(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        if (!resultado.fueExitoso()) {
            System.out.println("❌ ERRORES SEMÁNTICOS CRÍTICOS:");
            System.out.println("═".repeat(60));
            mostrarErroresCriticos(resultado.getErrores());
        }

        if (!resultado.getWarnings().isEmpty()) {
            System.out.println("\n⚠️  WARNINGS (NO CRÍTICOS):");
            System.out.println("═".repeat(60));
            mostrarWarnings(resultado.getWarnings());
        }

        if (resultado.fueExitoso() && resultado.getWarnings().isEmpty()) {
            System.out.println("✅ No se encontraron errores semánticos");
        }
    }

    /**
     * Muestra reporte resumido
     */
    public static void mostrarReporteResumido(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println("📋 RESUMEN DEL ANÁLISIS SEMÁNTICO:");
        System.out.println("─".repeat(40));

        if (resultado.fueExitoso()) {
            System.out.println("🟢 Estado: EXITOSO");
        } else {
            System.out.println("🔴 Estado: CON ERRORES");
            System.out.println("❌ Errores críticos: " + resultado.getNumeroErrores());
        }

        System.out.println("⚠️  Warnings: " + resultado.getNumeroWarnings());
        System.out.println("⏱️  Tiempo: " + resultado.getTiempoAnalisis() + " ms");

        // Mostrar estadísticas básicas de la tabla de símbolos
        Map<String, Integer> stats = resultado.getTablaSimbolos().getEstadisticas();
        System.out.println("📊 Variables: " + stats.get("totalVariables"));
        System.out.println("🔧 Funciones: " + stats.get("totalFunciones"));
    }

    private static void mostrarEncabezado(String nombreArchivo) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   ANÁLISIS SEMÁNTICO C++                    ║");
        System.out.println("║                   Técnicas de Compilación                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("📂 Archivo analizado: " + nombreArchivo);
        System.out.println();
    }

    private static void mostrarResumenGeneral(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println("📊 RESUMEN GENERAL:");
        System.out.println("═".repeat(50));

        if (resultado.fueExitoso()) {
            System.out.println("🎉 ¡ANÁLISIS SEMÁNTICO EXITOSO!");
            System.out.println("✅ El programa es semánticamente correcto");
        } else {
            System.out.println("❌ ERRORES SEMÁNTICOS DETECTADOS");
            System.out.println("🚫 El programa contiene errores que impiden la compilación");
        }

        System.out.println();
        System.out.printf("• Errores críticos: %d%n", resultado.getNumeroErrores());
        System.out.printf("• Warnings: %d%n", resultado.getNumeroWarnings());
        System.out.printf("• Tiempo de análisis: %d ms%n", resultado.getTiempoAnalisis());
        System.out.println();
        System.out.println("═".repeat(50));
        System.out.println();
    }

    private static void mostrarErroresCriticos(List<ErrorSemantico> errores) {
        System.out.println("🚨 ERRORES CRÍTICOS:");
        System.out.println("═".repeat(60));

        // Agrupar errores por línea
        Map<Integer, List<ErrorSemantico>> erroresPorLinea = new TreeMap<>();
        for (ErrorSemantico error : errores) {
            erroresPorLinea.computeIfAbsent(error.getLinea(), k -> new ArrayList<>()).add(error);
        }

        for (Map.Entry<Integer, List<ErrorSemantico>> entry : erroresPorLinea.entrySet()) {
            int linea = entry.getKey();
            List<ErrorSemantico> erroresLinea = entry.getValue();

            System.out.printf("📍 LÍNEA %d:%n", linea);
            for (int i = 0; i < erroresLinea.size(); i++) {
                ErrorSemantico error = erroresLinea.get(i);
                System.out.printf("   %d. %s%n", i + 1, error.toString());
                System.out.printf("      💡 %s%n", error.getSugerencia());
                if (i < erroresLinea.size() - 1) {
                    System.out.println();
                }
            }
            System.out.println();
        }

        System.out.println("═".repeat(60));

        // Mostrar resumen de tipos de errores
        mostrarResumenTiposErrores(errores, true);
    }

    private static void mostrarWarnings(List<ErrorSemantico> warnings) {
        System.out.println("⚠️  WARNINGS (RECOMENDACIONES):");
        System.out.println("═".repeat(60));

        // Agrupar warnings por tipo
        Map<ErrorSemantico.TipoError, List<ErrorSemantico>> warningsPorTipo = new HashMap<>();
        for (ErrorSemantico warning : warnings) {
            warningsPorTipo.computeIfAbsent(warning.getTipo(), k -> new ArrayList<>()).add(warning);
        }

        for (Map.Entry<ErrorSemantico.TipoError, List<ErrorSemantico>> entry : warningsPorTipo.entrySet()) {
            ErrorSemantico.TipoError tipo = entry.getKey();
            List<ErrorSemantico> warningsDelTipo = entry.getValue();

            System.out.printf("🔸 %s (%d ocurrencias):%n",
                    warningsDelTipo.get(0).getDescripcionTipo(), warningsDelTipo.size());

            for (ErrorSemantico warning : warningsDelTipo) {
                System.out.printf("   • Línea %d, Col %d: %s%n",
                        warning.getLinea(), warning.getColumna(), warning.getMensaje());
            }
            System.out.printf("   💡 %s%n%n", warningsDelTipo.get(0).getSugerencia());
        }

        System.out.println("═".repeat(60));
    }

    private static void mostrarTablaSimbolos(TablaSimbolos tabla) {
        System.out.println("📋 TABLA DE SÍMBOLOS:");
        System.out.println("═".repeat(80));

        // Mostrar funciones
        Collection<SimboloFuncion> funciones = tabla.getTodasLasFunciones();
        if (!funciones.isEmpty()) {
            System.out.println("🔧 FUNCIONES:");
            System.out.println("-".repeat(40));
            for (SimboloFuncion funcion : funciones) {
                String estado = funcion.isUtilizado() ? "✅" : "⚠️ ";
                String tieneReturn = funcion.tieneReturn() ? "✓" : "✗";
                System.out.printf("   %s %s (línea %d)%n", estado, funcion.getSignatura(), funcion.getLinea());
                System.out.printf("      Return: %s | Parámetros: %d%n",
                        tieneReturn, funcion.getNumeroParametros());
            }
            System.out.println();
        }

        // Mostrar variables
        List<SimboloVariable> variables = tabla.getTodasLasVariables();
        if (!variables.isEmpty()) {
            System.out.println("📊 VARIABLES:");
            System.out.println("-".repeat(60));
            System.out.printf("%-15s %-8s %-5s %-10s %-10s%n",
                    "NOMBRE", "TIPO", "LÍNEA", "INICIALIZADA", "UTILIZADA");
            System.out.println("-".repeat(60));

            for (SimboloVariable variable : variables) {
                String inicializada = variable.isInicializada() ? "✓" : "✗";
                String utilizada = variable.isUtilizado() ? "✓" : "✗";
                String tipoDesc = variable.esParametro() ? variable.getTipoDato() + "*" : variable.getTipoDato();

                System.out.printf("%-15s %-8s %-5d %-10s %-10s%n",
                        variable.getNombre(),
                        tipoDesc,
                        variable.getLinea(),
                        inicializada,
                        utilizada);
            }
            System.out.println("* = parámetro");
            System.out.println();
        }

        System.out.println("═".repeat(80));
    }


    private static void mostrarResumenTiposErrores(List<ErrorSemantico> errores, boolean sonCriticos) {
        Map<ErrorSemantico.TipoError, Integer> conteoTipos = new HashMap<>();

        for (ErrorSemantico error : errores) {
            conteoTipos.put(error.getTipo(), conteoTipos.getOrDefault(error.getTipo(), 0) + 1);
        }

        if (conteoTipos.size() > 1) {
            String titulo = sonCriticos ? "TIPOS DE ERRORES MÁS FRECUENTES" : "TIPOS DE WARNINGS MÁS FRECUENTES";
            System.out.println("📊 " + titulo + ":");
            System.out.println("-".repeat(40));

            conteoTipos.entrySet().stream()
                    .sorted(Map.Entry.<ErrorSemantico.TipoError, Integer>comparingByValue().reversed())
                    .forEach(entry -> {
                        ErrorSemantico ejemplo = errores.stream()
                                .filter(e -> e.getTipo() == entry.getKey())
                                .findFirst().orElse(null);

                        if (ejemplo != null) {
                            System.out.printf("   %-30s: %d%n",
                                    ejemplo.getDescripcionTipo(), entry.getValue());
                        }
                    });
            System.out.println();
        }
    }

    private static void mostrarDistribucionErrores(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println("🔍 DISTRIBUCIÓN DE PROBLEMAS:");
        System.out.println("-".repeat(30));

        Map<String, Integer> distribucion = new HashMap<>();

        // Contar errores por categoría
        for (ErrorSemantico error : resultado.getErrores()) {
            String categoria = categorizarError(error.getTipo());
            distribucion.put(categoria, distribucion.getOrDefault(categoria, 0) + 1);
        }

        // Contar warnings por categoría
        for (ErrorSemantico warning : resultado.getWarnings()) {
            String categoria = "Warning: " + categorizarError(warning.getTipo());
            distribucion.put(categoria, distribucion.getOrDefault(categoria, 0) + 1);
        }

        distribucion.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    String icono = entry.getKey().startsWith("Warning") ? "⚠️ " : "❌";
                    System.out.printf("   %s %-25s: %d%n",
                            icono, entry.getKey(), entry.getValue());
                });

        System.out.println();
    }

    private static String categorizarError(ErrorSemantico.TipoError tipo) {
        switch (tipo) {
            case VARIABLE_NO_DECLARADA:
            case FUNCION_NO_DECLARADA:
                return "Declaraciones";

            case REDEFINICION_VARIABLE:
            case REDEFINICION_FUNCION:
                return "Redefiniciones";

            case TIPOS_INCOMPATIBLES:
            case CONVERSION_TIPO_IMPLICITA:
            case TIPO_RETORNO_INCORRECTO:
                return "Tipos";

            case VARIABLE_NO_INICIALIZADA:
            case VARIABLE_NO_UTILIZADA:
            case PARAMETRO_NO_UTILIZADO:
                return "Variables";

            case FUNCION_SIN_RETURN:
            case RETURN_FUERA_DE_FUNCION:
            case FUNCION_NO_UTILIZADA:
                return "Funciones";

            case BREAK_CONTINUE_FUERA_DE_BUCLE:
                return "Control de flujo";

            case NUMERO_ARGUMENTOS_INCORRECTO:
                return "Llamadas";

            default:
                return "Otros";
        }
    }

    private static double calcularEficienciaSemantica(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        // Fórmula de eficiencia semántica basada en varios factores
        double penalizacionErrores = resultado.getNumeroErrores() * 10.0;
        double penalizacionWarnings = resultado.getNumeroWarnings() * 2.0;

        Map<String, Integer> stats = resultado.getTablaSimbolos().getEstadisticas();
        int totalVars = stats.get("totalVariables");

        double bonusUtilizacion = 0;
        if (totalVars > 0) {
            double porcentajeUtilizadas = (stats.get("variablesUtilizadas") * 100.0) / totalVars;
            bonusUtilizacion = porcentajeUtilizadas * 0.5;
        }

        double eficiencia = 100.0 - penalizacionErrores - penalizacionWarnings + bonusUtilizacion;
        return Math.max(0.0, Math.min(100.0, eficiencia));
    }

    /**
     * Exporta el reporte a archivo
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

            System.out.println("✅ Reporte semántico exportado a: " + rutaExportacion);

        } catch (java.io.IOException e) {
            System.err.println("❌ Error al exportar reporte semántico: " + e.getMessage());
        }
    }

    /**
     * Muestra sugerencias de mejora basadas en el análisis
     */
    public static void mostrarSugerenciasMejora(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        System.out.println("💡 SUGERENCIAS DE MEJORA:");
        System.out.println("═".repeat(50));

        Map<String, Integer> stats = resultado.getTablaSimbolos().getEstadisticas();
        boolean haySugerencias = false;

        // Sugerencias basadas en variables no utilizadas
        int varsNoUtilizadas = stats.get("totalVariables") - stats.get("variablesUtilizadas");
        if (varsNoUtilizadas > 0) {
            System.out.printf("🗑️  Elimine %d variable(s) que no se utilizan para limpiar el código%n",
                    varsNoUtilizadas);
            haySugerencias = true;
        }

        // Sugerencias basadas en inicialización
        int varsNoInicializadas = stats.get("totalVariables") - stats.get("variablesInicializadas");
        if (varsNoInicializadas > 0) {
            System.out.printf("🔧 Inicialice %d variable(s) al declararlas para evitar errores%n", varsNoInicializadas);
            haySugerencias = true;
        }

        // Sugerencias basadas en warnings
        if (resultado.getNumeroWarnings() > 0) {
            System.out.printf("⚠️  Revise los %d warnings para mejorar la calidad del código%n",
                    resultado.getNumeroWarnings());
            haySugerencias = true;
        }

        // Sugerencias basadas en complejidad
        if (stats.get("nivelMaximoAmbito") > 3) {
            System.out.println("📐 Considere simplificar la estructura del código (muchos niveles de anidamiento)");
            haySugerencias = true;
        }

        if (!haySugerencias) {
            System.out.println("🎉 ¡Excelente! No hay sugerencias específicas de mejora.");
            System.out.println("    Su código tiene una buena estructura semántica.");
        }

        System.out.println("═".repeat(50));
    }
}