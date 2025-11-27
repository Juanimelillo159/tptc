package tptc;

import java.util.*;

/**
 * Reportador de resultados del análisis semántico
 */
public class ReportadorSemantico {

    /**
     * Muestra el reporte completo del análisis semántico
     * Formato alineado al ejemplo esperado:
     *
     * === 4. ANÁLISIS SEMÁNTICO === (esto lo imprime App)
     * 📋 Tabla de símbolos construida:
     *
     * === TABLA DE SÍMBOLOS ===
     * ...
     *
     * ❌ ERRORES SEMÁNTICOS:
     * ❌ Error: ... (línea X, columna Y)
     *
     * ⚠️ WARNINGS SEMÁNTICOS:
     * ⚠️ Warning: ... (línea X, columna Y)
     * ⚠️ El código tiene warnings, pero se puede continuar.
     */
    public static void mostrarReporteCompleto(String nombreArchivo,
            AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {

        // 1) Mensaje de tabla construida
        System.out.println("   📋 Tabla de símbolos construida:\n");

        // 2) Tabla de símbolos SIEMPRE primero
        mostrarTablaSimbolos(resultado.getTablaSimbolos());

        // 3) Errores semánticos (si hay)
        if (!resultado.getErrores().isEmpty()) {
            mostrarErroresSemanticos(resultado.getErrores());
        }

        // 4) Warnings (si hay)
        if (!resultado.getWarnings().isEmpty()) {
            mostrarWarningsSemanticos(resultado.getWarnings());
        }
    }

    /**
     * Muestra solo errores y warnings CON COLORES
     */
    public static void mostrarSoloErrores(AnalizadorSemantico.ResultadoAnalisisSemantico resultado) {
        if (!resultado.getErrores().isEmpty()) {
            mostrarErroresSemanticos(resultado.getErrores());
        }

        if (!resultado.getWarnings().isEmpty()) {
            mostrarWarningsSemanticos(resultado.getWarnings());
        }

        if (resultado.getErrores().isEmpty() && resultado.getWarnings().isEmpty()) {
            System.out.println(ColoresConsole.verde("✅ No se encontraron errores ni warnings semánticos"));
        }
    }

    /**
     * Reporte resumido (lo dejo por si lo usás en modo compacto)
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

        // Estadísticas básicas de la tabla de símbolos
        Map<String, Integer> stats = resultado.getTablaSimbolos().getEstadisticas();
        System.out.println(ColoresConsole.cyan("📊 Variables: " + stats.get("totalVariables")));
        System.out.println(ColoresConsole.cyan("🔧 Funciones: " + stats.get("totalFunciones")));
    }

    // ================== SECCIONES DE REPORTE (FORMATO ESPERADO) ==================

    private static void mostrarErroresSemanticos(List<ErrorSemantico> errores) {
        System.out.println(ColoresConsole.rojo("❌ ERRORES SEMÁNTICOS:"));

        for (ErrorSemantico error : errores) {
            System.out.printf(
                    ColoresConsole.rojo("   ❌ Error: %s (línea %d, columna %d)%n"),
                    error.getMensaje(),
                    error.getLinea(),
                    error.getColumna());
        }
        System.out.println();
    }

    private static void mostrarWarningsSemanticos(List<ErrorSemantico> warnings) {
        System.out.println(ColoresConsole.amarillo("⚠️ WARNINGS SEMÁNTICOS:"));

        for (ErrorSemantico warning : warnings) {
            System.out.printf(
                    ColoresConsole.amarillo("   ⚠️ Warning: %s (línea %d, columna %d)%n"),
                    warning.getMensaje(),
                    warning.getLinea(),
                    warning.getColumna());
        }

        // Mensaje final como en el ejemplo
        System.out.println(ColoresConsole.amarillo("   ⚠️ El código tiene warnings, pero se puede continuar."));
        System.out.println();
    }

    /**
     * Muestra la tabla de símbolos con el formato:
     * NOMBRE TIPO CATEGORÍA LÍNEA COLUMNA ÁMBITO DETALLES
     */
    private static void mostrarTablaSimbolos(TablaSimbolos tabla) {
        System.out.println(ColoresConsole.azul("=== TABLA DE SÍMBOLOS ==="));
        System.out.printf(ColoresConsole.negrita("%-15s %-10s %-13s %-10s %-10s %-15s %s%n"),
                "NOMBRE", "TIPO", "CATEGORÍA", "LÍNEA", "COLUMNA", "ÁMBITO", "DETALLES");
        System.out.println(ColoresConsole
                .cyan("--------------------------------------------------------------------------------------------"));

        // Lista combinada de símbolos (funciones + variables/parámetros)
        List<Simbolo> simbolos = new ArrayList<>();
        simbolos.addAll(tabla.getTodasLasFunciones());
        simbolos.addAll(tabla.getTodasLasVariables());

        // Ordenar por aparición en el código (línea, columna)
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
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < f.getParametros().size(); i++) {
            if (i > 0)
                sb.append(", ");
            sb.append(f.getParametros().get(i).getTipoDato());
        }
        sb.append("]");
        return sb.toString();
    }

    // ================== EXPORTAR A ARCHIVO (SIN COLORES) ==================

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
                writer.println("ERRORES SEMÁNTICOS:");
                writer.println("-".repeat(40));
                for (ErrorSemantico error : resultado.getErrores()) {
                    writer.printf("• %s (línea %d, columna %d)%n",
                            error.getMensaje(), error.getLinea(), error.getColumna());
                    writer.println();
                }
            }

            // Warnings
            if (!resultado.getWarnings().isEmpty()) {
                writer.println("WARNINGS SEMÁNTICOS:");
                writer.println("-".repeat(40));
                for (ErrorSemantico warning : resultado.getWarnings()) {
                    writer.printf("• %s (línea %d, columna %d)%n",
                            warning.getMensaje(), warning.getLinea(), warning.getColumna());
                    writer.println();
                }
                writer.println("⚠️ El código tiene warnings, pero se puede continuar.");
            }

            // Tabla de símbolos
            writer.println();
            writer.println("TABLA DE SÍMBOLOS:");
            writer.println("NOMBRE          TIPO       CATEGORÍA       LÍNEA      COLUMNA    ÁMBITO          DETALLES");
            writer.println(
                    "--------------------------------------------------------------------------------------------");

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
            writer.println("=".repeat(60));
            writer.println("Fin del reporte");

            System.out.println(ColoresConsole.verde("✅ Reporte semántico exportado a: " + rutaExportacion));

        } catch (java.io.IOException e) {
            System.err.println(ColoresConsole.rojo("❌ Error al exportar reporte semántico: " + e.getMessage()));
        }
    }
}
