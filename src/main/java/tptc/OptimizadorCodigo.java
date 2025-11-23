package tptc;

import java.util.*;

/**
 * Optimizador de Código Intermedio (3-Address Code)
 * Implementa técnicas de optimización:
 * 1. Propagación de Constantes / Folding
 * 2. Eliminación de Código Muerto
 * 3. Simplificación Algebraica
 */
public class OptimizadorCodigo {

    public static List<String> optimizar(List<String> codigoOriginal) {
        List<String> codigo = new ArrayList<>(codigoOriginal);
        boolean cambios;
        int pasadas = 0;

        System.out.println("🔄 Iniciando optimización...");

        do {
            cambios = false;
            pasadas++;
            int tamanioInicial = codigo.size();

            // Técnica 1: Propagación de Constantes y Folding
            if (propagacionConstantes(codigo)) cambios = true;

            // Técnica 2: Simplificación Algebraica
            if (simplificacionAlgebraica(codigo)) cambios = true;

            // Técnica 3: Eliminación de Código Muerto
            if (eliminacionCodigoMuerto(codigo)) cambios = true;

            if (cambios) {
                System.out.println("   ✓ Pasada " + pasadas + ": Reducción de instrucciones de " + tamanioInicial + " a " + codigo.size());
            }

        } while (cambios && pasadas < 10); // Límite de pasadas para evitar bucles infinitos

        System.out.println("✅ Optimización completada en " + pasadas + " pasadas.");
        return codigo;
    }

    /**
     * Propagación de Constantes y Constant Folding
     * Ejemplo: x = 5 + 3 -> x = 8
     *          y = x + 2 -> y = 8 + 2 -> y = 10
     */
    private static boolean propagacionConstantes(List<String> codigo) {
        boolean cambios = false;
        Map<String, String> constantes = new HashMap<>();

        for (int i = 0; i < codigo.size(); i++) {
            String linea = codigo.get(i);
            String[] partes = linea.split(" ");

            // Detección de asignación simple: x = 5
            if (partes.length == 3 && partes[1].equals("=") && esNumero(partes[2])) {
                constantes.put(partes[0], partes[2]);
            }
            // Detección de asignación simple con variable: x = y (y y es constante)
            else if (partes.length == 3 && partes[1].equals("=") && constantes.containsKey(partes[2])) {
                String nuevoValor = constantes.get(partes[2]);
                codigo.set(i, partes[0] + " = " + nuevoValor);
                constantes.put(partes[0], nuevoValor);
                cambios = true;
            }
            // Constant Folding: x = 5 + 3
            else if (partes.length == 5 && partes[1].equals("=") && esNumero(partes[2]) && esNumero(partes[4])) {
                try {
                    double val1 = Double.parseDouble(partes[2]);
                    double val2 = Double.parseDouble(partes[4]);
                    String op = partes[3];
                    double res = 0;
                    boolean operacionValida = true;

                    switch (op) {
                        case "+": res = val1 + val2; break;
                        case "-": res = val1 - val2; break;
                        case "*": res = val1 * val2; break;
                        case "/":
                            if (val2 != 0) res = val1 / val2;
                            else operacionValida = false;
                            break;
                        default: operacionValida = false;
                    }

                    if (operacionValida) {
                        String resultadoStr;
                        if (res == (long) res) {
                            resultadoStr = String.valueOf((long) res);
                        } else {
                            resultadoStr = String.valueOf(res);
                        }
                        codigo.set(i, partes[0] + " = " + resultadoStr);
                        constantes.put(partes[0], resultadoStr);
                        cambios = true;
                    }
                } catch (NumberFormatException e) {
                    // Ignorar errores de parseo
                }
            }
            // Propagación en operaciones: x = y + 5 (si y es constante)
            else if (partes.length == 5 && partes[1].equals("=")) {
                String op1 = partes[2];
                String op = partes[3];
                String op2 = partes[4];
                boolean localChange = false;

                if (constantes.containsKey(op1)) {
                    op1 = constantes.get(op1);
                    localChange = true;
                }
                if (constantes.containsKey(op2)) {
                    op2 = constantes.get(op2);
                    localChange = true;
                }

                if (localChange) {
                    codigo.set(i, partes[0] + " = " + op1 + " " + op + " " + op2);
                    cambios = true;
                    // Si ahora ambos son números, el siguiente paso de folding lo resolverá
                }
            }

            // Resetear constantes si hay etiquetas o saltos (análisis local básico)
            // En un compilador real se usaría análisis de flujo de datos
            if (linea.endsWith(":") || linea.startsWith("goto") || linea.startsWith("if") || linea.startsWith("CALL")) {
                constantes.clear();
            }
            // Si se reasigna una variable, eliminarla de constantes (si no es constante ahora)
            if (partes.length >= 3 && partes[1].equals("=") && !esNumero(partes[2]) && !constantes.containsKey(partes[2])) {
                 if (partes.length == 3) { // Asignación simple no constante
                     constantes.remove(partes[0]);
                 }
            }
        }
        return cambios;
    }

    /**
     * Simplificación Algebraica
     * x = y + 0 -> x = y
     * x = y * 1 -> x = y
     * x = y * 0 -> x = 0
     */
    private static boolean simplificacionAlgebraica(List<String> codigo) {
        boolean cambios = false;

        for (int i = 0; i < codigo.size(); i++) {
            String linea = codigo.get(i);
            String[] partes = linea.split(" ");

            if (partes.length == 5 && partes[1].equals("=")) {
                String res = partes[0];
                String op1 = partes[2];
                String op = partes[3];
                String op2 = partes[4];

                // x + 0
                if (op.equals("+") && op2.equals("0")) {
                    codigo.set(i, res + " = " + op1);
                    cambios = true;
                }
                // 0 + x
                else if (op.equals("+") && op1.equals("0")) {
                    codigo.set(i, res + " = " + op2);
                    cambios = true;
                }
                // x - 0
                else if (op.equals("-") && op2.equals("0")) {
                    codigo.set(i, res + " = " + op1);
                    cambios = true;
                }
                // x * 1
                else if (op.equals("*") && op2.equals("1")) {
                    codigo.set(i, res + " = " + op1);
                    cambios = true;
                }
                // 1 * x
                else if (op.equals("*") && op1.equals("1")) {
                    codigo.set(i, res + " = " + op2);
                    cambios = true;
                }
                // x * 0
                else if (op.equals("*") && op2.equals("0")) {
                    codigo.set(i, res + " = 0");
                    cambios = true;
                }
                // 0 * x
                else if (op.equals("*") && op1.equals("0")) {
                    codigo.set(i, res + " = 0");
                    cambios = true;
                }
            }
        }
        return cambios;
    }

    /**
     * Eliminación de Código Muerto
     * Elimina instrucciones después de un return o goto incondicional hasta la siguiente etiqueta
     */
    private static boolean eliminacionCodigoMuerto(List<String> codigo) {
        boolean cambios = false;
        boolean codigoMuerto = false;
        Iterator<String> it = codigo.iterator();

        while (it.hasNext()) {
            String linea = it.next();

            // Si encontramos una etiqueta, el código deja de ser muerto (es alcanzable por salto)
            if (linea.endsWith(":") || linea.startsWith("FIN_FUNCION")) {
                codigoMuerto = false;
            }

            if (codigoMuerto) {
                it.remove();
                cambios = true;
                continue;
            }

            // Instrucciones que causan código muerto subsiguiente
            if (linea.startsWith("goto") || linea.startsWith("return")) {
                // Verificar que no sea un salto condicional (ifFalse usa goto pero no bloquea)
                // Ojo: ifFalse ... goto ... NO hace muerto el código siguiente
                // Pero "goto L1" incondicional SÍ
                if (!linea.startsWith("ifFalse")) {
                    codigoMuerto = true;
                }
            }
        }
        return cambios;
    }

    private static boolean esNumero(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}