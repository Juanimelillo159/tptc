package tptc;

/**
 * Reportador de resultados mejorado del análisis léxico CON COLORES
 */
public class ReportadorResultados {

    /**
     * Muestra el reporte completo del análisis léxico CON COLORES
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
     * Muestra solo la tabla de tokens (para reportes simples) CON COLORES
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
        System.out.println(ColoresConsole.AZUL + "╔══════════════════════════════════════════════════════════════╗" + ColoresConsole.RESET);
        System.out.println(ColoresConsole.AZUL + "║                ANALIZADOR LÉXICO C++ MEJORADO               ║" + ColoresConsole.RESET);
        System.out.println(ColoresConsole.AZUL + "║                  Técnicas de Compilación                    ║" + ColoresConsole.RESET);
        System.out.println(ColoresConsole.AZUL + "╚══════════════════════════════════════════════════════════════╝" + ColoresConsole.RESET);
        System.out.println();
        System.out.println(ColoresConsole.cyan("📂 Archivo analizado: " + nombreArchivo));
        System.out.println();
    }

    private static void mostrarContenidoArchivo(String contenido) {
        System.out.println(ColoresConsole.azul("📄 CONTENIDO DEL ARCHIVO:"));
        System.out.println(ColoresConsole.cyan("─".repeat(60)));
        String[] lineas = contenido.split("\n");
        for (int i = 0; i < lineas.length; i++) {
            System.out.printf(ColoresConsole.cyan("%3d │ ") + "%s%n", i + 1, lineas[i]);
        }
        System.out.println(ColoresConsole.cyan("─".repeat(60)));
        System.out.println();
    }

    private static void mostrarTablaTokens(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println(ColoresConsole.azul("📋 TABLA DE TOKENS:"));
        System.out.println(ColoresConsole.cyan("═".repeat(85)));
        System.out.printf(ColoresConsole.negrita("│ %-3s │ %-15s │ %-15s │ %-5s │ %-5s │ %-10s │%n"),
                "Nº", "LEXEMA", "TIPO", "LÍN", "COL", "ESTADO");
        System.out.println(ColoresConsole.cyan("═".repeat(85)));

        for (AnalizadorLexico.TokenInfo token : resultado.getTokens()) {
            String estado = ColoresConsole.verde("✅ OK");
            String lexemaCorto = AnalizadorLexico.truncarTexto(token.getLexema(), 14);

            System.out.printf("│ %-3d │ %-15s │ %-15s │ %-5d │ %-5d │ %-10s │%n",
                    token.getNumero(),
                    lexemaCorto,
                    token.getTipo(),
                    token.getLinea(),
                    token.getColumna(),
                    estado);
        }
        System.out.println(ColoresConsole.cyan("═".repeat(85)));
    }

    private static void mostrarTablaTokensConErrores(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println(ColoresConsole.azul("📋 TABLA DE TOKENS (CON ERRORES DETECTADOS):"));
        System.out.println(ColoresConsole.cyan("═".repeat(100)));
        System.out.printf(ColoresConsole.negrita("│ %-3s │ %-15s │ %-15s │ %-5s │ %-5s │ %-10s │ %-25s │%n"),
                "Nº", "LEXEMA", "TIPO", "LÍN", "COL", "ESTADO", "ERROR");
        System.out.println(ColoresConsole.cyan("═".repeat(100)));

        for (AnalizadorLexico.TokenInfo token : resultado.getTokens()) {
            String estado = token.esError() ? ColoresConsole.rojo("❌ ERROR") : ColoresConsole.verde("✅ OK");
            String error = token.esError() ? ColoresConsole.rojo(AnalizadorLexico.truncarTexto(token.getMensajeError(), 24)) : "";
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
        System.out.println(ColoresConsole.cyan("═".repeat(100)));
    }

    private static void mostrarErroresDetallados(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println();
        System.out.println(ColoresConsole.rojo("🚨 ERRORES LÉXICOS DETALLADOS:"));
        System.out.println(ColoresConsole.cyan("═".repeat(80)));

        boolean hayErrores = false;
        for (AnalizadorLexico.TokenInfo token : resultado.getTokens()) {
            if (token.esError()) {
                hayErrores = true;
                System.out.printf(ColoresConsole.rojo("⚠️  Línea %d, Columna %d: '%s'%n"),
                        token.getLinea(), token.getColumna(), token.getLexema());
                System.out.printf(ColoresConsole.amarillo("   Tipo detectado: %s%n"), token.getTipo());
                System.out.printf(ColoresConsole.rojo("   Error: %s%n"), token.getMensajeError());
                System.out.println(ColoresConsole.cyan("   Sugerencia: " + generarSugerencia(token)));
                System.out.println();
            }
        }

        if (!hayErrores) {
            System.out.println(ColoresConsole.verde("   ✅ No se encontraron errores léxicos"));
        }

        System.out.println(ColoresConsole.cyan("═".repeat(80)));
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
        System.out.println(ColoresConsole.azul("📊 ESTADÍSTICAS DETALLADAS:"));
        System.out.println(ColoresConsole.cyan("─".repeat(50)));
        System.out.println(ColoresConsole.cyan("🔢 Total de tokens analizados: " + resultado.getTotalTokens()));
        System.out.println(ColoresConsole.verde("✅ Tokens válidos: " + resultado.getTokensValidos()));
        System.out.println(ColoresConsole.rojo("❌ Tokens con error: " + resultado.getTokensConError()));

        if (!resultado.getTiposErrores().isEmpty()) {
            System.out.println();
            System.out.println(ColoresConsole.amarillo("🔍 Tipos de errores encontrados:"));
            for (String tipoError : resultado.getTiposErrores()) {
                System.out.println(ColoresConsole.amarillo("   • " + tipoError));
            }
        }

        System.out.println();
        if (resultado.fueExitoso()) {
            System.out.println(ColoresConsole.verde("🎉 ¡ANÁLISIS LÉXICO COMPLETADO EXITOSAMENTE!"));
            System.out.println(ColoresConsole.verde("   ✓ No se encontraron errores léxicos"));
            System.out.println(ColoresConsole.verde("   ✓ Todos los tokens fueron reconocidos correctamente"));
            System.out.println(ColoresConsole.verde("   ✓ El código puede proceder al análisis sintáctico"));
        } else {
            System.out.println(ColoresConsole.rojo("⚠️  SE ENCONTRARON ERRORES EN EL ANÁLISIS LÉXICO"));
            System.out.println(ColoresConsole.rojo("   ❌ Hay tokens que no pudieron ser reconocidos correctamente"));
            System.out.println(ColoresConsole.amarillo("   💡 Revise los tokens marcados como ERROR en la tabla"));
            System.out.println(ColoresConsole.amarillo("   ⚡ Se recomienda corregir errores léxicos antes del análisis sintáctico"));
        }
    }

    private static void mostrarResumenEjecutivo(AnalizadorLexico.ResultadoAnalisis resultado) {
        System.out.println();
        System.out.println(ColoresConsole.azul("📝 RESUMEN EJECUTIVO:"));
        System.out.println(ColoresConsole.cyan("─".repeat(30)));
        
        System.out.printf(ColoresConsole.cyan("Tokens: %d | ") + 
                         ColoresConsole.verde("Válidos: %d | ") + 
                         ColoresConsole.rojo("Errores: %d | ") + 
                         ColoresConsole.azul("Éxito: %.1f%%%n"),
                resultado.getTotalTokens(),
                resultado.getTokensValidos(),
                resultado.getTokensConError(),
                resultado.getPorcentajeExito());

        if (resultado.fueExitoso()) {
            System.out.println(ColoresConsole.verde("🟢 Estado: EXITOSO"));
        } else {
            System.out.println(ColoresConsole.rojo("🔴 Estado: CON ERRORES LÉXICOS"));
        }
    }
}