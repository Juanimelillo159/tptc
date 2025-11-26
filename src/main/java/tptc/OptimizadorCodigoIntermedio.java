package tptc;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Optimizador de código de tres direcciones.
 * 
 * Recibe una lista de instrucciones y aplica optimizaciones simples.
 */
public class OptimizadorCodigoIntermedio {

    private List<String> original;
    private List<String> optimizado;

    private int instruccionesOriginales;
    private int instruccionesOptimizadas;
    private int instruccionesEliminadas;
    private double reduccionPorcentual;

    public OptimizadorCodigoIntermedio(List<String> codigo) {
        this.original = new ArrayList<>(codigo);
        this.optimizado = new ArrayList<>();
    }

    /**
     * Aplica optimizaciones sobre el código original.
     */
    public void optimizar() {
        // Por ahora hacemos optimizaciones muy básicas.
        // Puedes mejorar esto luego.

        List<String> temp = new ArrayList<>();

        for (String linea : original) {
            String trimmed = linea.trim();

            // 1) Eliminar líneas completamente vacías
            if (trimmed.isEmpty()) continue;

            // 2) Eliminar asignaciones triviales del estilo "x = x"
            if (trimmed.matches("^[a-zA-Z_][a-zA-Z0-9_]* = \\1$")) {
                continue;
            }

            // 3) (Lugar para más reglas, por ejemplo constante folding a nivel textual)

            temp.add(linea);
        }

        this.optimizado = temp;

        this.instruccionesOriginales = original.size();
        this.instruccionesOptimizadas = optimizado.size();
        this.instruccionesEliminadas = instruccionesOriginales - instruccionesOptimizadas;

        if (instruccionesOriginales > 0) {
            this.reduccionPorcentual =
                    (instruccionesEliminadas * 100.0) / instruccionesOriginales;
        } else {
            this.reduccionPorcentual = 0.0;
        }
    }

    public List<String> getCodigoOptimizado() {
        return optimizado;
    }

    public int getInstruccionesOriginales() {
        return instruccionesOriginales;
    }

    public int getInstruccionesOptimizadas() {
        return instruccionesOptimizadas;
    }

    public int getInstruccionesEliminadas() {
        return instruccionesEliminadas;
    }

    public double getReduccionPorcentual() {
        return reduccionPorcentual;
    }

    /**
     * Exporta el código optimizado a un archivo de texto.
     */
    public void exportarComoTxt(String rutaArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (int i = 0; i < optimizado.size(); i++) {
                writer.printf("%3d: %s%n", i, optimizado.get(i));
            }
            System.out.println(ColoresConsole.verde(
                    "✅ Código optimizado guardado en: " + rutaArchivo));
        } catch (IOException e) {
            System.err.println(ColoresConsole.rojo(
                    "❌ Error al guardar código optimizado: " + e.getMessage()));
        }
    }

    /**
     * Muestra el resumen de optimización por consola.
     */
    public void mostrarResumen() {
        System.out.println("=== 6. OPTIMIZACIÓN DE CÓDIGO ===");
        System.out.println("   🔧 Aplicando optimizaciones al código intermedio...");

        System.out.println(ColoresConsole.verde("✅ Optimización completada:"));
        System.out.println("   📊 Instrucciones originales: " + instruccionesOriginales);
        System.out.println("   📊 Instrucciones optimizadas: " + instruccionesOptimizadas);
        System.out.println("   📊 Instrucciones eliminadas: " + instruccionesEliminadas);
        System.out.printf("   📊 Reducción de código: %.2f%%%n", reduccionPorcentual);
        System.out.println();
    }

    /**
     * Muestra el código optimizado en consola con numeración como en el ejemplo.
     */
    public void mostrarCodigoOptimizadoEnConsola() {
        System.out.println("   📝 Código optimizado:\n");
        for (int i = 0; i < optimizado.size(); i++) {
            System.out.printf(" %2d: %s%n", i, optimizado.get(i));
        }
        System.out.println();
    }
}
