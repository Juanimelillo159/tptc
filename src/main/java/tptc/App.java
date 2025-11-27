package tptc;

import java.io.*;
import java.util.*;

/**
 * Punto de entrada principal del compilador C++ con análisis léxico, sintáctico
 * semántico, generación de código intermedio y optimización.
 */
public class App {

    // CONFIGURACIÓN PRINCIPAL
    private static final String ARCHIVO_A_ANALIZAR = "input/Ejemplo-Final-Correcto.txt";
    /* private static final String ARCHIVO_A_ANALIZAR = "input/Ejemplo-Final-Errores.txt"; */

    // Configuración de análisis
    private static final boolean EJECUTAR_ANALISIS_LEXICO = true;
    private static final boolean EJECUTAR_ANALISIS_SINTACTICO = true;
    private static final boolean EJECUTAR_ANALISIS_SEMANTICO = true;
    private static final boolean MOSTRAR_CONTENIDO_ARCHIVO = true;
    private static final boolean EXPORTAR_RESULTADOS = false;

    // Configuración de presentación
    private static final boolean MODO_DETALLADO = true;
    private static final boolean MOSTRAR_ESTADISTICAS = true;
    private static final boolean MOSTRAR_TABLA_SIMBOLOS = true;

    // CONFIGURACIÓN SEMÁNTICA
    private static final boolean DETENER_EN_ERRORES_LEXICOS = true;
    private static final boolean DETENER_EN_ERRORES_SINTACTICOS = true;
    private static final boolean MOSTRAR_WARNINGS = true;

    // Configuración Código Intermedio
    private static final boolean GENERAR_CODIGO_INTERMEDIO = true;
    private static final boolean MOSTRAR_CODIGO_INTERMEDIO = true;

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║            COMPILADOR C++ COMPLETO - TPTC v2.0              ║");
        System.out.println("║     Análisis Léxico, Sintáctico y Semántico Integrado      ║");
        System.out.println("║      con Código Intermedio, Optimización y Símbolos        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        try {
            // Leer el archivo fuente
            String contenidoArchivo = leerArchivo(ARCHIVO_A_ANALIZAR);

            if (MOSTRAR_CONTENIDO_ARCHIVO) {
                mostrarContenidoFuente(contenidoArchivo);
            }

            boolean exitoTotal = true;

            // Resultados por fase
            AnalizadorLexico.ResultadoAnalisis resultadoLexico = null;
            AnalizadorSintactico.ResultadoAnalisisSintactico resultadoSintactico = null;
            AnalizadorSemantico.ResultadoAnalisisSemantico resultadoSemantico = null;

            // === 1. ANÁLISIS LÉXICO ===
            if (EJECUTAR_ANALISIS_LEXICO) {
                System.out.println("=== 1. ANÁLISIS LÉXICO ===");
                System.out.println("═".repeat(60));

                resultadoLexico = AnalizadorLexico.analizarCodigo(contenidoArchivo);

                if (MODO_DETALLADO) {
                    ReportadorResultados.mostrarReporteCompleto(ARCHIVO_A_ANALIZAR, contenidoArchivo, resultadoLexico);
                } else {
                    ReportadorResultados.mostrarSoloTabla(resultadoLexico);
                }

                if (!resultadoLexico.fueExitoso()) {
                    exitoTotal = false;
                    System.out.println("⚠️  Se encontraron " + resultadoLexico.getTokensConError() + " errores léxicos.");

                    if (DETENER_EN_ERRORES_LEXICOS) {
                        System.out.println("🛑 DETENIENDO COMPILACIÓN por errores léxicos.");
                        mostrarResumenFinal(resultadoLexico, null, null, false);
                        System.exit(1);
                        return;
                    }
                } else {
                    System.out.println("✅ Análisis léxico completado sin errores. Procediendo...");
                }
                System.out.println();
            }

            // === 2. ANÁLISIS SINTÁCTICO ===
            if (EJECUTAR_ANALISIS_SINTACTICO) {
                System.out.println("=== 2. ANÁLISIS SINTÁCTICO ===");
                System.out.println("═".repeat(60));

                resultadoSintactico = AnalizadorSintactico.analizarCodigo(contenidoArchivo);

                if (MODO_DETALLADO) {
                    ReportadorSintactico.mostrarReporteCompleto(ARCHIVO_A_ANALIZAR, resultadoSintactico);
                } else {
                    ReportadorSintactico.mostrarReporteResumido(resultadoSintactico);
                }

                if (!resultadoSintactico.fueExitoso()) {
                    exitoTotal = false;
                    System.out.println(
                            "⚠️  Se encontraron " + resultadoSintactico.getErrores().size() + " errores sintácticos.");

                    if (DETENER_EN_ERRORES_SINTACTICOS) {
                        System.out.println("🛑 DETENIENDO COMPILACIÓN por errores sintácticos.");
                        mostrarResumenFinal(resultadoLexico, resultadoSintactico, null, false);
                        System.exit(1);
                        return;
                    }
                } else {
                    System.out.println("✅ Análisis sintáctico completado sin errores. Procediendo...");
                }
                System.out.println();
            }

            // === 3. (Opcional) VISUALIZACIÓN DEL AST ===
            // Aquí podrías enganchar una visualización gráfica del AST si quisieras.

            // === 4. ANÁLISIS SEMÁNTICO ===
            if (EJECUTAR_ANALISIS_SEMANTICO && resultadoSintactico != null && resultadoSintactico.fueExitoso()) {
                System.out.println("=== 4. ANÁLISIS SEMÁNTICO ===");
                System.out.println("═".repeat(60));

                resultadoSemantico = AnalizadorSemantico.analizar(resultadoSintactico.getArbolSintactico());

                if (MODO_DETALLADO) {
                    ReportadorSemantico.mostrarReporteCompleto(ARCHIVO_A_ANALIZAR, resultadoSemantico);
                } else {
                    ReportadorSemantico.mostrarReporteResumido(resultadoSemantico);
                }

                if (!resultadoSemantico.fueExitoso()) {
                    exitoTotal = false;
                    System.out.println(
                            "⚠️  Se encontraron " + resultadoSemantico.getNumeroErrores() + " errores semánticos.");
                } else {
                    System.out.println("✅ Análisis semántico completado sin errores.");

                    if (resultadoSemantico.getNumeroWarnings() > 0 && MOSTRAR_WARNINGS) {
                        System.out.println("⚠️  Se encontraron " + resultadoSemantico.getNumeroWarnings()
                                + " warnings (no críticos).");
                    }
                }
                System.out.println();

            } else if (EJECUTAR_ANALISIS_SEMANTICO) {
                System.out.println("⏸️  ANÁLISIS SEMÁNTICO OMITIDO");
                System.out.println("   Motivo: Errores en fases anteriores impiden el análisis semántico");
                System.out.println();
            }

            // === 5 y 6: CÓDIGO INTERMEDIO + OPTIMIZACIÓN ===
            if (GENERAR_CODIGO_INTERMEDIO
                    && resultadoSintactico != null && resultadoSintactico.fueExitoso()
                    && resultadoSemantico != null && resultadoSemantico.fueExitoso()) {

                String baseNombre = obtenerNombreBase(ARCHIVO_A_ANALIZAR);

                // === 5. GENERACIÓN DE CÓDIGO INTERMEDIO ===
                System.out.println("=== 5. GENERACIÓN DE CÓDIGO INTERMEDIO ===");
                System.out.println("   🎯 Iniciando recorrido del AST con GeneradorCodigoIntermedio...");
                System.out.println("═".repeat(60));

                GeneradorCodigoIntermedio generador = new GeneradorCodigoIntermedio();
                generador.visit(resultadoSintactico.getArbolSintactico());
                List<String> codigoIntermedio = generador.getCodigoIntermedio();

                if (MOSTRAR_CODIGO_INTERMEDIO) {
                    System.out.println("   📝 Código de tres direcciones generado:\n");
                    generador.mostrarCodigo(); // método alias que ya definiste/marcamos antes
                }

                String archivoIntermedio = baseNombre + "_codigo_intermedio.txt";
                generarArchivoTexto(archivoIntermedio, codigoIntermedio);
                System.out.println("\n✅ Código intermedio guardado en: " + archivoIntermedio);
                System.out.println();

                // === 6. OPTIMIZACIÓN DE CÓDIGO ===
                System.out.println("=== 6. OPTIMIZACIÓN DE CÓDIGO ===");
                System.out.println("   🔧 Aplicando optimizaciones al código intermedio...");
                System.out.println("═".repeat(60));

                Optimizador optimizador = new Optimizador(codigoIntermedio);
                List<String> codigoOptimizado = optimizador.optimizar();

                optimizador.mostrarReporteOptimizacion();
                System.out.println();
                System.out.println("   📝 Código optimizado:\n");
                optimizador.mostrarCodigoOptimizado();

                String archivoOptimizado = baseNombre + "_codigo_optimizado.txt";
                generarArchivoTexto(archivoOptimizado, codigoOptimizado);
                System.out.println("\n✅ Código optimizado guardado en: " + archivoOptimizado);
                System.out.println();

            } else if (GENERAR_CODIGO_INTERMEDIO) {
                System.out.println("⏸️  GENERACIÓN DE CÓDIGO INTERMEDIO/OPTIMIZACIÓN OMITIDA");
                System.out.println("   Motivo: Errores en fases anteriores impiden la generación");
                System.out.println();
            }

            // === 7. RESUMEN FINAL ===
            mostrarResumenFinal(resultadoLexico, resultadoSintactico, resultadoSemantico, exitoTotal);

            // === EXPORTAR RESULTADOS (OPCIONAL) ===
            if (EXPORTAR_RESULTADOS) {
                exportarResultados(resultadoLexico, resultadoSintactico, resultadoSemantico);
            }

            System.exit(exitoTotal ? 0 : 1);

        } catch (Exception e) {
            manejarErrorGeneral(e);
        }
    }

    // ========================= HELPERS =========================

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
     * Nombre base sin ruta ni extensión, en minúsculas.
     * Ej: "input/ejemplo_correcto.cpp" -> "ejemplo_correcto"
     */
    private static String obtenerNombreBase(String ruta) {
        String nombre = ruta;

        int slash = Math.max(nombre.lastIndexOf('/'), nombre.lastIndexOf('\\'));
        if (slash >= 0) {
            nombre = nombre.substring(slash + 1);
        }

        int punto = nombre.lastIndexOf('.');
        if (punto > 0) {
            nombre = nombre.substring(0, punto);
        }

        return nombre.toLowerCase();
    }

    /**
     * Genera un archivo de texto simple con una lista de líneas.
     */
    private static void generarArchivoTexto(String ruta, List<String> lineas) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ruta))) {
            for (String linea : lineas) {
                writer.println(linea);
            }
        } catch (IOException e) {
            System.err.println("❌ Error al guardar archivo " + ruta + ": " + e.getMessage());
        }
    }

    /**
     * Muestra resumen final mejorado con información de todas las fases
     */
    private static void mostrarResumenFinal(AnalizadorLexico.ResultadoAnalisis resultadoLexico,
            AnalizadorSintactico.ResultadoAnalisisSintactico resultadoSintactico,
            AnalizadorSemantico.ResultadoAnalisisSemantico resultadoSemantico,
            boolean exitoTotal) {

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                      RESUMEN FINAL                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");

        // Resumen por fases
        System.out.println("📋 RESULTADOS POR FASE:");
        System.out.println("─".repeat(50));

        // Análisis Léxico
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

        // Análisis Sintáctico
        if (resultadoSintactico != null) {
            String estadoSintactico = resultadoSintactico.fueExitoso() ? "✅ EXITOSO" : "❌ CON ERRORES";
            System.out.printf("🌳 Análisis Sintáctico: %s%n", estadoSintactico);
            if (MOSTRAR_ESTADISTICAS) {
                System.out.printf("   └─ Nodos: %d | Errores: %d | Tiempo: %d ms%n",
                        resultadoSintactico.getNumeroNodos(),
                        resultadoSintactico.getErrores().size(),
                        resultadoSintactico.getTiempoAnalisis());
            }
        }

        // Análisis Semántico
        if (resultadoSemantico != null) {
            String estadoSemantico = resultadoSemantico.fueExitoso() ? "✅ EXITOSO" : "❌ CON ERRORES";
            System.out.printf("🧠 Análisis Semántico:  %s%n", estadoSemantico);
            if (MOSTRAR_ESTADISTICAS) {
                System.out.printf("   └─ Errores: %d | Warnings: %d | Tiempo: %d ms%n",
                        resultadoSemantico.getNumeroErrores(),
                        resultadoSemantico.getNumeroWarnings(),
                        resultadoSemantico.getTiempoAnalisis());
            }
        } else if (EJECUTAR_ANALISIS_SEMANTICO) {
            System.out.printf("🧠 Análisis Semántico:  ⏸️  NO EJECUTADO%n");
            System.out.printf("   └─ Motivo: Errores en fases anteriores%n");
        }

        System.out.println();

        // Estado general
        if (exitoTotal) {
            System.out.println("🎉 ¡COMPILACIÓN EXITOSA!");
            System.out.println("✅ El programa pasó todas las fases de análisis");

            if (resultadoSemantico != null && resultadoSemantico.getNumeroWarnings() > 0) {
                System.out.println("⚠️  Se encontraron " + resultadoSemantico.getNumeroWarnings() +
                        " warnings (recomendaciones de mejora)");
            }

        } else {
            System.out.println("💥 COMPILACIÓN FALLIDA");

            if (resultadoLexico != null && !resultadoLexico.fueExitoso()) {
                System.out.println("❌ Errores léxicos detectados");
            }
            if (resultadoSintactico != null && !resultadoSintactico.fueExitoso()) {
                System.out.println("❌ Errores sintácticos detectados");
            }
            if (resultadoSemantico != null && !resultadoSemantico.fueExitoso()) {
                System.out.println("❌ Errores semánticos detectados");
                // Línea específica pedida en el resultado esperado
                System.out.println("❌ Compilación detenida debido a errores semánticos.");
            }
        }

        if (resultadoSemantico != null && MOSTRAR_TABLA_SIMBOLOS) {
            var stats = resultadoSemantico.getTablaSimbolos().getEstadisticas();
            System.out.println();
            System.out.println("📊 TABLA DE SÍMBOLOS:");
            System.out.printf("   Variables: %d | Funciones: %d | Ámbitos: %d%n",
                    stats.get("totalVariables"),
                    stats.get("totalFunciones"),
                    stats.get("nivelMaximoAmbito") + 1);
        }

        System.out.println();
    }

    /**
     * Exporta los resultados a archivos
     */
    private static void exportarResultados(AnalizadorLexico.ResultadoAnalisis resultadoLexico,
            AnalizadorSintactico.ResultadoAnalisisSintactico resultadoSintactico,
            AnalizadorSemantico.ResultadoAnalisisSemantico resultadoSemantico) {
        try {
            new File("reportes").mkdirs();

            String baseNombre = ARCHIVO_A_ANALIZAR.replace(".txt", "").replace("/", "_");
            String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());

            if (resultadoLexico != null) {
                String archivoLexico = String.format("reportes/%s_lexico_%s.txt", baseNombre, timestamp);
                exportarReporteLexico(ARCHIVO_A_ANALIZAR, resultadoLexico, archivoLexico);
            }

            if (resultadoSintactico != null) {
                String archivoSintactico = String.format("reportes/%s_sintactico_%s.txt", baseNombre, timestamp);
                ReportadorSintactico.exportarReporte(ARCHIVO_A_ANALIZAR, resultadoSintactico, archivoSintactico);
            }

            if (resultadoSemantico != null) {
                String archivoSemantico = String.format("reportes/%s_semantico_%s.txt", baseNombre, timestamp);
                ReportadorSemantico.exportarReporte(ARCHIVO_A_ANALIZAR, resultadoSemantico, archivoSemantico);
            }

            String archivoConsolidado = String.format("reportes/%s_completo_%s.txt", baseNombre, timestamp);
            exportarReporteConsolidado(ARCHIVO_A_ANALIZAR, resultadoLexico, resultadoSintactico,
                    resultadoSemantico, archivoConsolidado);

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
     * Exporta un reporte consolidado con todas las fases
     */
    private static void exportarReporteConsolidado(String nombreArchivo,
            AnalizadorLexico.ResultadoAnalisis resultadoLexico,
            AnalizadorSintactico.ResultadoAnalisisSintactico resultadoSintactico,
            AnalizadorSemantico.ResultadoAnalisisSemantico resultadoSemantico,
            String rutaExportacion) {

        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(rutaExportacion))) {
            writer.println("╔══════════════════════════════════════════════════════════════╗");
            writer.println("║                  REPORTE CONSOLIDADO                        ║");
            writer.println("║              COMPILADOR C++ COMPLETO - TPTC                 ║");
            writer.println("╚══════════════════════════════════════════════════════════════╝");
            writer.println();
            writer.println("Archivo analizado: " + nombreArchivo);
            writer.println("Fecha y hora: " + new java.util.Date());
            writer.println("Versión del compilador: TPTC v2.0");
            writer.println("=".repeat(70));
            writer.println();

            boolean exitoTotal = true;
            if (resultadoLexico != null) {
                exitoTotal &= resultadoLexico.fueExitoso();
                writer.println("Análisis Léxico: " + (resultadoLexico.fueExitoso() ? "✓ EXITOSO" : "✗ FALLIDO"));
            }

            if (resultadoSintactico != null) {
                exitoTotal &= resultadoSintactico.fueExitoso();
                writer.println(
                        "Análisis Sintáctico: " + (resultadoSintactico.fueExitoso() ? "✓ EXITOSO" : "✗ FALLIDO"));
            }

            if (resultadoSemantico != null) {
                exitoTotal &= resultadoSemantico.fueExitoso();
                writer.println("Análisis Semántico: " + (resultadoSemantico.fueExitoso() ? "✓ EXITOSO" : "✗ FALLIDO"));
            }

            writer.println();
            writer.println("ESTADO GENERAL: " + (exitoTotal ? "✓ COMPILACIÓN EXITOSA" : "✗ COMPILACIÓN FALLIDA"));
            writer.println();

            if (resultadoLexico != null) {
                writer.println("=".repeat(70));
                writer.println("FASE 1: ANÁLISIS LÉXICO");
                writer.println("=".repeat(70));
                writer.printf("Tokens totales: %d%n", resultadoLexico.getTotalTokens());
                writer.printf("Tokens válidos: %d%n", resultadoLexico.getTokensValidos());
                writer.printf("Errores léxicos: %d%n", resultadoLexico.getTokensConError());
                writer.printf("Porcentaje de éxito: %.1f%%%n", resultadoLexico.getPorcentajeExito());
                writer.println();
            }

            if (resultadoSintactico != null) {
                writer.println("=".repeat(70));
                writer.println("FASE 2: ANÁLISIS SINTÁCTICO");
                writer.println("=".repeat(70));
                writer.printf("Nodos en AST: %d%n", resultadoSintactico.getNumeroNodos());
                writer.printf("Profundidad máxima: %d%n", resultadoSintactico.getProfundidadMaxima());
                writer.printf("Errores sintácticos: %d%n", resultadoSintactico.getErrores().size());
                writer.printf("Tiempo de análisis: %d ms%n", resultadoSintactico.getTiempoAnalisis());
                writer.println();
            }

            if (resultadoSemantico != null) {
                writer.println("=".repeat(70));
                writer.println("FASE 3: ANÁLISIS SEMÁNTICO");
                writer.println("=".repeat(70));
                writer.printf("Errores críticos: %d%n", resultadoSemantico.getNumeroErrores());
                writer.printf("Warnings: %d%n", resultadoSemantico.getNumeroWarnings());
                writer.printf("Tiempo de análisis: %d ms%n", resultadoSemantico.getTiempoAnalisis());

                var stats = resultadoSemantico.getTablaSimbolos().getEstadisticas();
                writer.printf("Variables declaradas: %d%n", stats.get("totalVariables"));
                writer.printf("Funciones declaradas: %d%n", stats.get("totalFunciones"));
                writer.printf("Máximo nivel de ámbito: %d%n", stats.get("nivelMaximoAmbito"));
                writer.println();

                writer.println("TABLA DE SÍMBOLOS (RESUMEN):");
                writer.println("-".repeat(40));

                var funciones = resultadoSemantico.getTablaSimbolos().getTodasLasFunciones();
                if (!funciones.isEmpty()) {
                    writer.println("Funciones:");
                    for (var funcion : funciones) {
                        writer.printf("  - %s (línea %d)%n", funcion.getSignatura(), funcion.getLinea());
                    }
                    writer.println();
                }

                var variables = resultadoSemantico.getTablaSimbolos().getTodasLasVariables();
                if (!variables.isEmpty()) {
                    writer.println("Variables:");
                    for (var variable : variables) {
                        if (!variable.esParametro()) {
                            String estado = variable.isUtilizado() ? "usada" : "no usada";
                            writer.printf("  - %s %s (línea %d) - %s%n",
                                    variable.getTipoDato(), variable.getNombre(),
                                    variable.getLinea(), estado);
                        }
                    }
                    writer.println();
                }
            }

            boolean hayProblemas = false;

            if (resultadoSemantico != null && !resultadoSemantico.getErrores().isEmpty()) {
                writer.println("=".repeat(70));
                writer.println("ERRORES CRÍTICOS ENCONTRADOS:");
                writer.println("=".repeat(70));
                for (var error : resultadoSemantico.getErrores()) {
                    writer.println("• " + error.toString());
                    writer.println("  Sugerencia: " + error.getSugerencia());
                    writer.println();
                }
                hayProblemas = true;
            }

            if (resultadoSemantico != null && !resultadoSemantico.getWarnings().isEmpty()) {
                writer.println("=".repeat(70));
                writer.println("WARNINGS (RECOMENDACIONES):");
                writer.println("=".repeat(70));
                for (var warning : resultadoSemantico.getWarnings()) {
                    writer.println("• " + warning.toString());
                    writer.println("  Sugerencia: " + warning.getSugerencia());
                    writer.println();
                }
                hayProblemas = true;
            }

            if (!hayProblemas && exitoTotal) {
                writer.println("=".repeat(70));
                writer.println("¡FELICIDADES!");
                writer.println("El código no presenta errores críticos ni warnings.");
                writer.println("El programa está listo para las siguientes fases de compilación.");
                writer.println("=".repeat(70));
            }

            writer.println();
            writer.println("=".repeat(70));
            writer.println("FIN DEL REPORTE");
            writer.println("=".repeat(70));

            System.out.println("✅ Reporte consolidado exportado a: " + rutaExportacion);

        } catch (java.io.IOException e) {
            System.err.println("❌ Error al exportar reporte consolidado: " + e.getMessage());
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
        System.err.println("   • Error en el análisis semántico");

        if (e.getMessage() != null && e.getMessage().contains("ClassNotFoundException")) {
            System.err.println("   • Ejecuta: javac -cp \".;antlr-4.13.1-complete.jar\" tptc\\*.java");
        }

        if (e instanceof FileNotFoundException) {
            System.err.println("   • Verifica que el archivo '" + ARCHIVO_A_ANALIZAR + "' existe");
            System.err.println("   • Crea el directorio 'input/' si no existe");
        }

        System.err.println();
        System.err.println("📚 Para más información sobre el compilador TPTC:");
        System.err.println("   • Verifica que todas las clases estén compiladas");
        System.err.println("   • Asegúrate de que la gramática ANTLR esté actualizada");
        System.err.println("   • Revisa que el archivo de entrada tenga sintaxis C++ válida");
        System.err.println();
        e.printStackTrace();
        System.exit(3);
    }
}
