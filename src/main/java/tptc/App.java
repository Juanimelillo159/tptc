package tptc;

import java.io.*;

/**
 * Punto de entrada principal del compilador C++
 * Integra análisis léxico y sintáctico
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

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    COMPILADOR C++ - TPTC                    ║");
        System.out.println("║              Análisis Léxico y Sintáctico                   ║");
        System.out.println("║                 Técnicas de Compilación                     ║");
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
                System.out.println("🔍 INICIANDO ANÁLISIS LÉXICO...");
                System.out.println("═".repeat(60));

                resultadoLexico = AnalizadorLexico.analizarCodigo(contenidoArchivo);

                if (MODO_DETALLADO) {
                    ReportadorResultados.mostrarReporteCompleto(ARCHIVO_A_ANALIZAR, contenidoArchivo, resultadoLexico);
                } else {
                    ReportadorResultados.mostrarSoloTabla(resultadoLexico);
                }

                if (!resultadoLexico.fueExitoso()) {
                    exitoTotal = false;
                    System.out.println("⚠️  Se encontraron errores léxicos. El análisis sintáctico puede fallar.");
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
     * Muestra resumen final de ambos análisis
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
        }

        System.out.println();

        // Estado general
        if (exitoTotal) {
            System.out.println("🎉 ¡COMPILACIÓN EXITOSA!");
            System.out.println("✅ El programa pasó todas las fases de análisis");
            System.out.println("🚀 Listo para las siguientes fases (semántico, generación de código)");
        } else {
            System.out.println("💥 COMPILACIÓN FALLIDA");
            System.out.println("❌ Se encontraron errores que impiden continuar");
            System.out.println("🔧 Corrige los errores antes de proceder");
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
                // Aquí podrías exportar resultados léxicos si implementas el método
                System.out.println("📁 Exportando resultados léxicos...");
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
     * Maneja errores de archivo
     */

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
        System.err.println("   • Archivo de entrada corrupto");

        if (e.getMessage() != null && e.getMessage().contains("ClassNotFoundException")) {
            System.err.println("   • Ejecuta: javac -cp \".;antlr-4.13.1-complete.jar\" tptc\\*.java");
        }

        System.err.println();
        e.printStackTrace();
        System.exit(3);
    }
}