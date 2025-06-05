package tptc;

import java.util.Map;

/**
 * Responsabilidad: Formatear y mostrar los resultados del análisis léxico
 */
public class ReportadorResultados {

    /**
     * Muestra el reporte completo del análisis léxico
     */
    public static void mostrarReporteCompleto(String nombreArchivo, String contenidoArchivo,
            AnalizadorLexico.ResultadoAnalisis resultado) {
        mostrarEncabezado(nombreArchivo);
        mostrarContenidoArchivo(contenidoArchivo);
        mostrarTablaTokens(resultado);
        mostrarEstadisticas(resultado);
        mostrarPiePagina();
    }

    /**
     * Muestra solo la tabla de tokens (para reportes simples)
     */
    public static void mostrarSoloTabla(AnalizadorLexico.ResultadoAnalisis resultado) {
        mostrarTablaTokens(resultado);
        mostrarResumenEjecutivo(resultado);
    }

    private static void mostrarEncabezado(String nombreArchivo) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ANALIZADOR LÉXICO C++                    ║");
        System.out.println("║                   Técnicas de Compilación                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("📂 Archivo analizado: " + nombreArchivo);
        System.out.println();
    }

    private static void mostrarContenidoArchivo(String contenido) {
        System.out.println("📄 CONTENIDO DEL ARCHIVO:");
        System.out.println("─".repeat(60));
        System.out.println(contenido.trim());
        System.out.println("─".repeat(60));
        System.out.println();
    }

    private static void mostrarTablaTokens(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println("📋 TABLA DE TOKENS:");
        System.out.println("═".repeat(85));
        System.out.printf("│ %-3s │ %-15s │ %-15s │ %-5s │ %-5s │ %-10s │%n",
                "Nº", "LEXEMA", "TIPO", "LÍN", "COL", "ESTADO");
        System.out.println("═".repeat(85));

        for (AnalizadorLexico.TokenInfo token : resultado.getTokens()) {
            String estado = token.esError() ? "❌ ERROR" : "✅ OK";
            String lexemaCorto = AnalizadorLexico.truncarTexto(token.getLexema(), 14);

            System.out.printf("│ %-3d │ %-15s │ %-15s │ %-5d │ %-5d │ %-10s │%n",
                    token.getNumero(),
                    lexemaCorto,
                    token.getTipo(),
                    token.getLinea(),
                    token.getColumna(),
                    estado);
        }
        System.out.println("═".repeat(85));
    }

    private static void mostrarEstadisticas(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println();
        System.out.println("📊 ESTADÍSTICAS DETALLADAS:");
        System.out.println("─".repeat(50));
        System.out.println("🔢 Total de tokens analizados: " + resultado.getTotalTokens());
        System.out.println("✅ Tokens válidos: " + resultado.getTokensValidos());
        System.out.println("❌ Tokens con error: " + resultado.getTokensConError());
        System.out.printf("📈 Porcentaje de éxito: %.1f%%%n", resultado.getPorcentajeExito());

        // Mostrar resultado final
        System.out.println();
        if (resultado.fueExitoso()) {
            System.out.println("🎉 ¡ANÁLISIS LÉXICO COMPLETADO EXITOSAMENTE!");
            System.out.println("   ✓ No se encontraron errores léxicos");
            System.out.println("   ✓ Todos los tokens fueron reconocidos correctamente");
        } else {
            System.out.println("⚠️  SE ENCONTRARON ERRORES EN EL ANÁLISIS LÉXICO");
            System.out.println("   ❌ Hay tokens que no pudieron ser reconocidos");
            System.out.println("   💡 Revisa los tokens marcados como ERROR en la tabla");
        }

        // Mostrar distribución de tipos
        mostrarDistribucionTipos(resultado.getDistribucionTipos());
    }

    private static void mostrarDistribucionTipos(Map<String, Integer> distribucion) {
        System.out.println();
        System.out.println("📋 DISTRIBUCIÓN DE TIPOS DE TOKENS:");
        System.out.println("─".repeat(40));

        distribucion.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> {
                    String barra = "█".repeat(Math.min(entry.getValue(), 25));
                    System.out.printf("%-15s: %2d %s%n",
                            entry.getKey(),
                            entry.getValue(),
                            barra);
                });
    }

    private static void mostrarResumenEjecutivo(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println();
        System.out.println("📝 RESUMEN EJECUTIVO:");
        System.out.println("─".repeat(30));
        System.out.printf("Tokens: %d | Válidos: %d | Errores: %d | Éxito: %.1f%%%n",
                resultado.getTotalTokens(),
                resultado.getTokensValidos(),
                resultado.getTokensConError(),
                resultado.getPorcentajeExito());

        if (resultado.fueExitoso()) {
            System.out.println("🟢 Estado: EXITOSO");
        } else {
            System.out.println("🔴 Estado: CON ERRORES");
        }
    }

    private static void mostrarPiePagina() {
        System.out.println();
        System.out.println("═".repeat(60));
        System.out.println("💡 Para analizar otro archivo, cambia la ruta en App.java");
        System.out.println("🔄 Para re-analizar, ejecuta nuevamente el programa");
        System.out.println("═".repeat(60));
    }
}