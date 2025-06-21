package tptc;

/**
 * Reportador de resultados mejorado del análisis léxico
 * Responsabilidad: Formatear y mostrar los resultados con mejor detección de
 * errores
 */
public class ReportadorResultados {

    /**
     * Muestra el reporte completo del análisis léxico
     */
    public static void mostrarReporteCompleto(String nombreArchivo, String contenidoArchivo,
            AnalizadorLexico.ResultadoAnalisis resultado) {
        mostrarEncabezado(nombreArchivo);
        mostrarContenidoArchivo(contenidoArchivo);

        if (resultado.fueExitoso()) {
            mostrarTablaTokens(resultado);
            mostrarEstadisticas(resultado);
        } else {
            mostrarTablaTokensConErrores(resultado);
            mostrarErroresDetallados(resultado);
            mostrarEstadisticas(resultado);
        }
    }

    /**
     * Muestra solo la tabla de tokens (para reportes simples)
     */
    public static void mostrarSoloTabla(AnalizadorLexico.ResultadoAnalisis resultado) {
        if (resultado.fueExitoso()) {
            mostrarTablaTokens(resultado);
        } else {
            mostrarTablaTokensConErrores(resultado);
        }
        mostrarResumenEjecutivo(resultado);
    }

    private static void mostrarEncabezado(String nombreArchivo) {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                ANALIZADOR LÉXICO C++ MEJORADO               ║");
        System.out.println("║                  Técnicas de Compilación                    ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("📂 Archivo analizado: " + nombreArchivo);
        System.out.println();
    }

    private static void mostrarContenidoArchivo(String contenido) {
        System.out.println("📄 CONTENIDO DEL ARCHIVO:");
        System.out.println("─".repeat(60));
        String[] lineas = contenido.split("\n");
        for (int i = 0; i < lineas.length; i++) {
            System.out.printf("%3d │ %s%n", i + 1, lineas[i]);
        }
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
            String estado = "✅ OK";
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

    private static void mostrarTablaTokensConErrores(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println("📋 TABLA DE TOKENS (CON ERRORES DETECTADOS):");
        System.out.println("═".repeat(100));
        System.out.printf("│ %-3s │ %-15s │ %-15s │ %-5s │ %-5s │ %-10s │ %-25s │%n",
                "Nº", "LEXEMA", "TIPO", "LÍN", "COL", "ESTADO", "ERROR");
        System.out.println("═".repeat(100));

        for (AnalizadorLexico.TokenInfo token : resultado.getTokens()) {
            String estado = token.esError() ? "❌ ERROR" : "✅ OK";
            String error = token.esError() ? AnalizadorLexico.truncarTexto(token.getMensajeError(), 24) : "";
            String lexemaCorto = AnalizadorLexico.truncarTexto(token.getLexema(), 14);

            System.out.printf("│ %-3d │ %-15s │ %-15s │ %-5d │ %-5d │ %-10s │ %-25s │%n",
                    token.getNumero(),
                    lexemaCorto,
                    token.getTipo(),
                    token.getLinea(),
                    token.getColumna(),
                    estado,
                    error);
        }
        System.out.println("═".repeat(100));
    }

    private static void mostrarErroresDetallados(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println();
        System.out.println("🚨 ERRORES LÉXICOS DETALLADOS:");
        System.out.println("═".repeat(80));

        boolean hayErrores = false;
        for (AnalizadorLexico.TokenInfo token : resultado.getTokens()) {
            if (token.esError()) {
                hayErrores = true;
                System.out.printf("⚠️  Línea %d, Columna %d: '%s'%n",
                        token.getLinea(), token.getColumna(), token.getLexema());
                System.out.printf("   Tipo detectado: %s%n", token.getTipo());
                System.out.printf("   Error: %s%n", token.getMensajeError());
                System.out.println("   Sugerencia: " + generarSugerencia(token));
                System.out.println();
            }
        }

        if (!hayErrores) {
            System.out.println("   ✅ No se encontraron errores léxicos");
        }

        System.out.println("═".repeat(80));
    }

    private static String generarSugerencia(AnalizadorLexico.TokenInfo token) {
        String lexema = token.getLexema();
        String tipo = token.getTipo();

        switch (tipo) {
            case "IDENTIFICADOR_INVALIDO":
                return "Los identificadores deben comenzar con una letra. Ejemplo: 'a" + lexema.substring(1) + "'";
            case "DECIMAL_INVALIDO":
                if (lexema.endsWith(".")) {
                    return "Agregue dígitos después del punto decimal. Ejemplo: '" + lexema + "0'";
                } else if (lexema.startsWith(".")) {
                    return "Agregue dígitos antes del punto decimal. Ejemplo: '0" + lexema + "'";
                } else {
                    return "Formato correcto de decimal: 'numero.numero'";
                }
            case "CARACTER_INVALIDO":
                return "Los caracteres deben estar entre comillas simples. Ejemplo: 'a'";
            case "ERROR_LEXICO":
                return "Secuencia no válida. Revise la sintaxis del lenguaje";
            default:
                return "Revise la documentación del lenguaje";
        }
    }

    private static void mostrarEstadisticas(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println();
        System.out.println("📊 ESTADÍSTICAS DETALLADAS:");
        System.out.println("─".repeat(50));
        System.out.println("🔢 Total de tokens analizados: " + resultado.getTotalTokens());
        System.out.println("✅ Tokens válidos: " + resultado.getTokensValidos());
        System.out.println("❌ Tokens con error: " + resultado.getTokensConError());

        if (!resultado.getTiposErrores().isEmpty()) {
            System.out.println();
            System.out.println("🔍 Tipos de errores encontrados:");
            for (String tipoError : resultado.getTiposErrores()) {
                System.out.println("   • " + tipoError);
            }
        }

        System.out.println();
        if (resultado.fueExitoso()) {
            System.out.println("🎉 ¡ANÁLISIS LÉXICO COMPLETADO EXITOSAMENTE!");
            System.out.println("   ✓ No se encontraron errores léxicos");
            System.out.println("   ✓ Todos los tokens fueron reconocidos correctamente");
            System.out.println("   ✓ El código puede proceder al análisis sintáctico");
        } else {
            System.out.println("⚠️  SE ENCONTRARON ERRORES EN EL ANÁLISIS LÉXICO");
            System.out.println("   ❌ Hay tokens que no pudieron ser reconocidos correctamente");
            System.out.println("   💡 Revise los tokens marcados como ERROR en la tabla");
            System.out.println("   ⚡ Se recomienda corregir errores léxicos antes del análisis sintáctico");
        }
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
            System.out.println("🔴 Estado: CON ERRORES LÉXICOS");
        }
    }
}