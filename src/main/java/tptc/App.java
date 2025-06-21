package tptc;

import java.io.*;

/**
 * Punto de entrada principal del compilador C++ con detección mejorada de
 * errores léxicos
 * Integra análisis léxico y sintáctico con mejor control de flujo
 */
public class App {

    // ⭐ CONFIGURACIÓN PRINCIPAL ⭐
    private static final String ARCHIVO_A_ANALIZAR = "input/programa.txt";

    // Configuración de análisis
    private static final boolean EJECUTAR_ANALISIS_LEXICO = true;
    private static final boolean EJECUTAR_ANALISIS_SINTACTICO = true;
    private static final boolean MOSTRAR_CONTENIDO_ARCHIVO = true;
    private static final boolean EXPORTAR_RESULTADOS = false;

    // Configuración de presentación
    private static final boolean MODO_DETALLADO = true;
    private static final boolean MOSTRAR_ESTADISTICAS = true;

    // ⭐ NUEVA CONFIGURACIÓN ⭐
    private static final boolean DETENER_EN_ERRORES_LEXICOS = true; // Si debe parar en errores léxicos

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                COMPILADOR C++ MEJORADO - TPTC               ║");
        System.out.println("║          Análisis Léxico y Sintáctico con Detección         ║");
        System.out.println("║               Mejorada de Errores Léxicos                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        try {
            // Leer el archivo fuente
            String contenidoArchivo = leerArchivo(ARCHIVO_A_ANALIZAR);

            if (MOSTRAR_CONTENIDO_ARCHIVO) {
                mostrarContenidoFuente(contenidoArchivo);
            }

            boolean exitoTotal = true;

            // === FASE 1: ANÁLISIS LÉXICO ===
            AnalizadorLexico.ResultadoAnalisis resultadoLexico = null;
            if (EJECUTAR_ANALISIS_LEXICO) {
                System.out.println("🔍 INICIANDO ANÁLISIS LÉXICO MEJORADO...");
                System.out.println("═".repeat(60));

                resultadoLexico = AnalizadorLexico.analizarCodigo(contenidoArchivo);

                if (MODO_DETALLADO) {
                    ReportadorResultados.mostrarReporteCompleto(ARCHIVO_A_ANALIZAR, contenidoArchivo, resultadoLexico);
                } else {
                    ReportadorResultados.mostrarSoloTabla(resultadoLexico);
                }

                if (!resultadoLexico.fueExitoso()) {
                    exitoTotal = false;
                    System.out
                            .println("⚠️  Se encontraron " + resultadoLexico.getTokensConError() + " errores léxicos.");

                    if (DETENER_EN_ERRORES_LEXICOS) {
                        System.out.println("🛑 DETENIENDO COMPILACIÓN por errores léxicos.");
                        System.out.println("💡 Corrija los errores léxicos antes de continuar.");
                        System.out.println();
                        mostrarResumenFinal(resultadoLexico, null, false);
                        System.exit(1);
                        return;
                    } else {
                        System.out
                                .println("⚡ Continuando con análisis sintáctico (puede generar errores adicionales).");
                        System.out.println();
                    }
                } else {
                    System.out.println("✅ Análisis léxico completado sin errores. Procediendo...");
                    System.out.println();
                }
            }

            // === FASE 2: ANÁLISIS SINTÁCTICO ===
            AnalizadorSintactico.ResultadoAnalisisSintactico resultadoSintactico = null;
            if (EJECUTAR_ANALISIS_SINTACTICO) {
                System.out.println("🌳 INICIANDO ANÁLISIS SINTÁCTICO...");
                System.out.println("═".repeat(60));

                resultadoSintactico = AnalizadorSintactico.analizarCodigo(contenidoArchivo);

                if (MODO_DETALLADO) {
                    ReportadorSintactico.mostrarReporteCompleto(ARCHIVO_A_ANALIZAR, resultadoSintactico);
                } else {
                    ReportadorSintactico.mostrarReporteResumido(resultadoSintactico);
                }

                if (!resultadoSintactico.fueExitoso()) {
                    exitoTotal = false;
                }
            }

            // === RESUMEN FINAL ===
            mostrarResumenFinal(resultadoLexico, resultadoSintactico, exitoTotal);

            // === EXPORTAR RESULTADOS (OPCIONAL) ===
            if (EXPORTAR_RESULTADOS) {
                exportarResultados(resultadoLexico, resultadoSintactico);
            }

            // Código de salida
            System.exit(exitoTotal ? 0 : 1);

        } catch (Exception e) {
            manejarErrorGeneral(e);
        }
    }

    /**
     * Lee el archivo fuente
     */
    private static String leerArchivo(String rutaArchivo) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }
        return contenido.toString();
    }

    /**
     * Muestra el contenido del archivo fuente
     */
    private static void mostrarContenidoFuente(String contenido) {
        System.out.println("📄 CÓDIGO FUENTE A ANALIZAR:");
        System.out.println("═".repeat(60));

        String[] lineas = contenido.split("\n");
        for (int i = 0; i < lineas.length; i++) {
            System.out.printf("%3d │ %s%n", i + 1, lineas[i]);
        }

        System.out.println("═".repeat(60));
        System.out.println("📊 Archivo: " + ARCHIVO_A_ANALIZAR + " (" + lineas.length + " líneas)");
        System.out.println();
    }

    /**
     * Muestra resumen final de ambos análisis con información mejorada
     */
    private static void mostrarResumenFinal(AnalizadorLexico.ResultadoAnalisis resultadoLexico,
            AnalizadorSintactico.ResultadoAnalisisSintactico resultadoSintactico,
            boolean exitoTotal) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      RESUMEN FINAL                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // Resumen por fases
        System.out.println("📋 RESULTADOS POR FASE:");
        System.out.println("─".repeat(40));

        if (resultadoLexico != null) {
            String estadoLexico = resultadoLexico.fueExitoso() ? "✅ EXITOSO" : "❌ CON ERRORES";
            System.out.printf("🔍 Análisis Léxico:    %s%n", estadoLexico);
            if (MOSTRAR_ESTADISTICAS) {
                System.out.printf("   └─ Tokens: %d | Errores: %d | Éxito: %.1f%%%n",
                        resultadoLexico.getTotalTokens(),
                        resultadoLexico.getTokensConError(),
                        resultadoLexico.getPorcentajeExito());

                if (!resultadoLexico.fueExitoso()) {
                    System.out.println("   └─ Tipos de errores: " + resultadoLexico.getTiposErrores().size());
                }
            }
        }

        if (resultadoSintactico != null) {
            String estadoSintactico = resultadoSintactico.fueExitoso() ? "✅ EXITOSO" : "❌ CON ERRORES";
            System.out.printf("🌳 Análisis Sintáctico: %s%n", estadoSintactico);
            if (MOSTRAR_ESTADISTICAS) {
                System.out.printf("   └─ Nodos: %d | Profundidad: %d | Tiempo: %d ms%n",
                        resultadoSintactico.getNumeroNodos(),
                        resultadoSintactico.getProfundidadMaxima(),
                        resultadoSintactico.getTiempoAnalisis());
            }
        } else if (resultadoLexico != null && !resultadoLexico.fueExitoso() && DETENER_EN_ERRORES_LEXICOS) {
            System.out.printf("🌳 Análisis Sintáctico: ⏸️  NO EJECUTADO%n");
            System.out.printf("   └─ Motivo: Errores léxicos detectados%n");
        }

        System.out.println();

        // Estado general
        if (exitoTotal) {
            System.out.println("🎉 ¡COMPILACIÓN EXITOSA!");
            System.out.println("✅ El programa pasó todas las fases de análisis");
            System.out.println("🚀 Listo para las siguientes fases (semántico, generación de código)");
        } else {
            System.out.println("💥 COMPILACIÓN FALLIDA");

            if (resultadoLexico != null && !resultadoLexico.fueExitoso()) {
                System.out.println("❌ Se encontraron errores léxicos que impiden continuar");
                System.out.println("🔧 Corrija los errores léxicos antes de proceder");
            } else if (resultadoSintactico != null && !resultadoSintactico.fueExitoso()) {
                System.out.println("❌ Se encontraron errores sintácticos");
                System.out.println("🔧 Corrija los errores sintácticos antes de proceder");
            }
        }

        // Recomendaciones
        if (!exitoTotal) {
            System.out.println();
            System.out.println("💡 RECOMENDACIONES:");
            if (resultadoLexico != null && !resultadoLexico.fueExitoso()) {
                System.out.println("   1. Revise los identificadores que comienzan con números");
                System.out.println("   2. Verifique la sintaxis de números decimales");
                System.out.println("   3. Asegúrese de que los caracteres estén bien formados");
            }
            if (resultadoSintactico != null && !resultadoSintactico.fueExitoso()) {
                System.out.println("   4. Revise la estructura del programa (llaves, paréntesis, puntos y coma)");
                System.out.println("   5. Verifique la sintaxis de bucles for");
            }
        }

        System.out.println();
    }

    /**
     * Exporta los resultados a archivos
     */
    private static void exportarResultados(AnalizadorLexico.ResultadoAnalisis resultadoLexico,
            AnalizadorSintactico.ResultadoAnalisisSintactico resultadoSintactico) {
        try {
            String baseNombre = ARCHIVO_A_ANALIZAR.replace(".txt", "").replace("/", "_");

            if (resultadoLexico != null) {
                String archivoLexico = "reportes/" + baseNombre + "_lexico.txt";
                exportarReporteLexico(ARCHIVO_A_ANALIZAR, resultadoLexico, archivoLexico);
            }

            if (resultadoSintactico != null) {
                String archivoReporte = "reportes/" + baseNombre + "_sintactico.txt";
                ReportadorSintactico.exportarReporte(ARCHIVO_A_ANALIZAR, resultadoSintactico, archivoReporte);
            }

        } catch (Exception e) {
            System.err.println("⚠️  Error al exportar resultados: " + e.getMessage());
        }
    }

    /**
     * Exporta reporte léxico a archivo
     */
    private static void exportarReporteLexico(String nombreArchivo,
            AnalizadorLexico.ResultadoAnalisis resultado, String rutaExportacion) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(rutaExportacion))) {
            writer.println("REPORTE DE ANÁLISIS LÉXICO MEJORADO");
            writer.println("Archivo analizado: " + nombreArchivo);
            writer.println("Fecha: " + new java.util.Date());
            writer.println("=".repeat(60));
            writer.println();

            writer.println("RESUMEN:");
            writer.println("Estado: " + (resultado.fueExitoso() ? "EXITOSO" : "CON ERRORES"));
            writer.println("Total de tokens: " + resultado.getTotalTokens());
            writer.println("Tokens válidos: " + resultado.getTokensValidos());
            writer.println("Tokens con error: " + resultado.getTokensConError());
            writer.println("Porcentaje de éxito: " + String.format("%.1f%%", resultado.getPorcentajeExito()));
            writer.println();

            if (!resultado.fueExitoso()) {
                writer.println("ERRORES ENCONTRADOS:");
                writer.println("-".repeat(40));
                for (AnalizadorLexico.TokenInfo token : resultado.getTokens()) {
                    if (token.esError()) {
                        writer.printf("Línea %d, Col %d: '%s' - %s%n",
                                token.getLinea(), token.getColumna(), token.getLexema(), token.getMensajeError());
                    }
                }
                writer.println();
            }

            writer.println("DETALLE DE TOKENS:");
            writer.println("-".repeat(40));
            for (AnalizadorLexico.TokenInfo token : resultado.getTokens()) {
                String estado = token.esError() ? "ERROR" : "OK";
                writer.printf("%d. '%s' (%s) - Línea %d, Col %d [%s]%n",
                        token.getNumero(), token.getLexema(), token.getTipo(),
                        token.getLinea(), token.getColumna(), estado);
            }

            System.out.println("✅ Reporte léxico exportado a: " + rutaExportacion);

        } catch (java.io.IOException e) {
            System.err.println("❌ Error al exportar reporte léxico: " + e.getMessage());
        }
    }

    /**
     * Maneja errores generales
     */
    private static void manejarErrorGeneral(Exception e) {
        System.err.println("╔══════════════════════════════════════════════════════════════╗");
        System.err.println("║                   ❌ ERROR INESPERADO                       ║");
        System.err.println("╚══════════════════════════════════════════════════════════════╝");
        System.err.println();
        System.err.println("💥 Error inesperado: " + e.getClass().getSimpleName());
        System.err.println("📝 Mensaje: " + e.getMessage());
        System.err.println();
        System.err.println("🔍 Posibles causas:");
        System.err.println("   • Archivos ANTLR no compilados correctamente");
        System.err.println("   • Dependencias faltantes");
        System.err.println("   • Archivo de entrada corrupto o no encontrado");

        if (e.getMessage() != null && e.getMessage().contains("ClassNotFoundException")) {
            System.err.println("   • Ejecuta: javac -cp \".;antlr-4.13.1-complete.jar\" tptc\\*.java");
        }

        if (e instanceof FileNotFoundException) {
            System.err.println("   • Verifica que el archivo '" + ARCHIVO_A_ANALIZAR + "' existe");
            System.err.println("   • Crea el directorio 'input/' si no existe");
        }

        System.err.println();
        e.printStackTrace();
        System.exit(3);
    }
}