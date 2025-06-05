package tptc;

import org.antlr.v4.runtime.tree.*;
import java.io.*;
import java.util.*;

/**
 * Visualizador del Árbol de Sintaxis Abstracta (AST)
 * Responsabilidad: Mostrar el árbol sintáctico de forma legible
 */
public class VisualizadorAST {
    
    // Caracteres para dibujar el árbol
    private static final String RAMA_VERTICAL = "│   ";
    private static final String RAMA_INTERMEDIA = "├── ";
    private static final String RAMA_FINAL = "└── ";
    private static final String ESPACIO = "    ";
    
    // Colores ANSI para terminal (opcional)
    private static final String RESET = "\u001B[0m";
    private static final String VERDE = "\u001B[32m";
    private static final String AZUL = "\u001B[34m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String MORADO = "\u001B[35m";
    
    /**
     * Muestra el árbol sintáctico completo con formato visual
     */
    public static void mostrarArbolCompleto(ParseTree arbol) {
        if (arbol == null) {
            System.out.println("❌ No se puede mostrar el árbol: árbol nulo");
            return;
        }
        
        System.out.println("🌳 ÁRBOL DE SINTAXIS ABSTRACTA (AST):");
        System.out.println("═".repeat(60));
        mostrarNodo(arbol, "", true, new HashSet<>());
        System.out.println("═".repeat(60));
    }
    
    /**
     * Muestra el árbol de forma compacta
     */
    public static void mostrarArbolCompacto(ParseTree arbol) {
        if (arbol == null) {
            System.out.println("❌ Árbol no disponible");
            return;
        }
        
        System.out.println("🌳 AST (Compacto):");
        System.out.println("-".repeat(40));
        mostrarNodoCompacto(arbol, 0);
        System.out.println("-".repeat(40));
    }
    
    /**
     * Exporta el árbol a un archivo de texto
     */
    public static void exportarArbol(ParseTree arbol, String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println("ÁRBOL DE SINTAXIS ABSTRACTA");
            writer.println("Generado automáticamente");
            writer.println("=" .repeat(50));
            writer.println();
            
            if (arbol != null) {
                exportarNodo(arbol, "", true, writer);
            } else {
                writer.println("Árbol no disponible (errores sintácticos)");
            }
            
            writer.println();
            writer.println("=" .repeat(50));
            writer.println("Fin del árbol");
            
            System.out.println("✅ Árbol exportado a: " + nombreArchivo);
            
        } catch (IOException e) {
            System.err.println("❌ Error al exportar árbol: " + e.getMessage());
        }
    }
    
    /**
     * Genera representación en formato DOT (para Graphviz)
     */
    public static void exportarADot(ParseTree arbol, String nombreArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println("digraph AST {");
            writer.println("    node [shape=box, style=rounded];");
            writer.println("    rankdir=TB;");
            writer.println();
            
            if (arbol != null) {
                Map<ParseTree, String> nodoIds = new HashMap<>();
                generarNodosDot(arbol, writer, nodoIds, new int[]{0});
                writer.println();
                generarConexionesDot(arbol, writer, nodoIds);
            }
            
            writer.println("}");
            
            System.out.println("✅ Archivo DOT generado: " + nombreArchivo);
            System.out.println("💡 Visualiza con: dot -Tpng " + nombreArchivo + " -o arbol.png");
            
        } catch (IOException e) {
            System.err.println("❌ Error al generar DOT: " + e.getMessage());
        }
    }
    
    /**
     * Muestra estadísticas del árbol
     */
    public static void mostrarEstadisticasArbol(ParseTree arbol) {
        if (arbol == null) {
            System.out.println("❌ No hay árbol para analizar");
            return;
        }
        
        EstadisticasArbol stats = calcularEstadisticas(arbol);
        
        System.out.println("📊 ESTADÍSTICAS DEL ÁRBOL:");
        System.out.println("─".repeat(30));
        System.out.println("🔢 Total de nodos: " + stats.totalNodos);
        System.out.println("📏 Profundidad máxima: " + stats.profundidadMaxima);
        System.out.println("🍃 Nodos hoja: " + stats.nodosHoja);
        System.out.println("🌿 Nodos internos: " + stats.nodosInternos);
        System.out.println("📋 Tipos de nodos únicos: " + stats.tiposNodos.size());
        
        System.out.println("\n📈 Distribución por tipo:");
        stats.tiposNodos.entrySet()
                       .stream()
                       .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                       .limit(10) // Mostrar solo los 10 más frecuentes
                       .forEach(entry -> {
                           String barra = "█".repeat(Math.min(entry.getValue(), 20));
                           System.out.printf("   %-15s: %2d %s%n", 
                                           entry.getKey(), entry.getValue(), barra);
                       });
    }
    
    // Métodos privados para la implementación
    
    private static void mostrarNodo(ParseTree nodo, String prefijo, boolean esUltimo, Set<ParseTree> visitados) {
        if (nodo == null || visitados.contains(nodo)) return;
        visitados.add(nodo);
        
        String nombreNodo = obtenerNombreNodo(nodo);
        String simbolo = esUltimo ? RAMA_FINAL : RAMA_INTERMEDIA;
        
        System.out.println(prefijo + simbolo + colorearNodo(nombreNodo, nodo));
        
        String nuevoPrefijo = prefijo + (esUltimo ? ESPACIO : RAMA_VERTICAL);
        
        for (int i = 0; i < nodo.getChildCount(); i++) {
            boolean esUltimoHijo = (i == nodo.getChildCount() - 1);
            mostrarNodo(nodo.getChild(i), nuevoPrefijo, esUltimoHijo, visitados);
        }
    }
    
    private static void mostrarNodoCompacto(ParseTree nodo, int nivel) {
        if (nodo == null) return;
        
        String indentacion = "  ".repeat(nivel);
        String nombreNodo = obtenerNombreNodo(nodo);
        
        System.out.println(indentacion + "• " + nombreNodo);
        
        for (int i = 0; i < nodo.getChildCount(); i++) {
            mostrarNodoCompacto(nodo.getChild(i), nivel + 1);
        }
    }
    
    private static void exportarNodo(ParseTree nodo, String prefijo, boolean esUltimo, PrintWriter writer) {
        if (nodo == null) return;
        
        String nombreNodo = obtenerNombreNodo(nodo);
        String simbolo = esUltimo ? RAMA_FINAL : RAMA_INTERMEDIA;
        
        writer.println(prefijo + simbolo + nombreNodo);
        
        String nuevoPrefijo = prefijo + (esUltimo ? ESPACIO : RAMA_VERTICAL);
        
        for (int i = 0; i < nodo.getChildCount(); i++) {
            boolean esUltimoHijo = (i == nodo.getChildCount() - 1);
            exportarNodo(nodo.getChild(i), nuevoPrefijo, esUltimoHijo, writer);
        }
    }
    
    private static void generarNodosDot(ParseTree nodo, PrintWriter writer, 
                                      Map<ParseTree, String> nodoIds, int[] contador) {
        if (nodo == null) return;
        
        String nodeId = "node" + contador[0]++;
        nodoIds.put(nodo, nodeId);
        
        String nombreNodo = obtenerNombreNodo(nodo).replace("\"", "\\\"");
        String color = obtenerColorNodo(nodo);
        
        writer.printf("    %s [label=\"%s\", fillcolor=\"%s\", style=\"filled,rounded\"];%n", 
                     nodeId, nombreNodo, color);
        
        for (int i = 0; i < nodo.getChildCount(); i++) {
            generarNodosDot(nodo.getChild(i), writer, nodoIds, contador);
        }
    }
    
    private static void generarConexionesDot(ParseTree nodo, PrintWriter writer, 
                                           Map<ParseTree, String> nodoIds) {
        if (nodo == null) return;
        
        String nodoId = nodoIds.get(nodo);
        
        for (int i = 0; i < nodo.getChildCount(); i++) {
            ParseTree hijo = nodo.getChild(i);
            String hijoId = nodoIds.get(hijo);
            writer.printf("    %s -> %s;%n", nodoId, hijoId);
            generarConexionesDot(hijo, writer, nodoIds);
        }
    }
    
    private static String obtenerNombreNodo(ParseTree nodo) {
        if (nodo instanceof TerminalNode) {
            TerminalNode terminal = (TerminalNode) nodo;
            String texto = terminal.getText();
            return String.format("'%s'", texto.length() > 20 ? texto.substring(0, 17) + "..." : texto);
        } else {
            String nombreClase = nodo.getClass().getSimpleName();
            return nombreClase.replace("Context", "");
        }
    }
    
    private static String colorearNodo(String nombre, ParseTree nodo) {
        // Colorear según el tipo de nodo (solo si el terminal soporta colores)
        if (nodo instanceof TerminalNode) {
            return VERDE + nombre + RESET;
        } else if (nombre.contains("declaracion") || nombre.contains("Declaracion")) {
            return AZUL + nombre + RESET;
        } else if (nombre.contains("asignacion") || nombre.contains("Asignacion")) {
            return AMARILLO + nombre + RESET;
        } else if (nombre.contains("if") || nombre.contains("while") || nombre.contains("for")) {
            return MORADO + nombre + RESET;
        } else {
            return nombre;
        }
    }
    
    private static String obtenerColorNodo(ParseTree nodo) {
        if (nodo instanceof TerminalNode) {
            return "lightgreen";
        } else {
            String nombre = obtenerNombreNodo(nodo).toLowerCase();
            if (nombre.contains("declaracion")) return "lightblue";
            if (nombre.contains("asignacion")) return "lightyellow";
            if (nombre.contains("if") || nombre.contains("while") || nombre.contains("for")) return "lightcoral";
            return "lightgray";
        }
    }
    
    private static EstadisticasArbol calcularEstadisticas(ParseTree nodo) {
        EstadisticasArbol stats = new EstadisticasArbol();
        calcularEstadisticasRecursivo(nodo, stats, 1);
        return stats;
    }
    
    private static void calcularEstadisticasRecursivo(ParseTree nodo, EstadisticasArbol stats, int profundidad) {
        if (nodo == null) return;
        
        stats.totalNodos++;
        stats.profundidadMaxima = Math.max(stats.profundidadMaxima, profundidad);
        
        String tipoNodo = obtenerNombreNodo(nodo);
        stats.tiposNodos.put(tipoNodo, stats.tiposNodos.getOrDefault(tipoNodo, 0) + 1);
        
        if (nodo.getChildCount() == 0) {
            stats.nodosHoja++;
        } else {
            stats.nodosInternos++;
        }
        
        for (int i = 0; i < nodo.getChildCount(); i++) {
            calcularEstadisticasRecursivo(nodo.getChild(i), stats, profundidad + 1);
        }
    }
    
    // Clase auxiliar para estadísticas
    private static class EstadisticasArbol {
        int totalNodos = 0;
        int profundidadMaxima = 0;
        int nodosHoja = 0;
        int nodosInternos = 0;
        Map<String, Integer> tiposNodos = new HashMap<>();
    }
}