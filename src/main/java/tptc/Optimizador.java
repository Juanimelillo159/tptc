package tptc;

import java.util.*;
import java.util.regex.*;

/**
 * Optimizador de código intermedio con las 3 técnicas requeridas:
 * 1. Propagación de constantes
 * 2. Eliminación de código muerto  
 * 3. Simplificación de expresiones
 */
public class Optimizador {
    private List<String> codigoOriginal;
    private List<String> codigoOptimizado;
    private Map<String, String> constantes;
    private Set<String> variablesUtilizadas;
    private int optimizacionesAplicadas;
    private List<String> reporteOptimizaciones;

    // Patrones para análisis de instrucciones
    private static final Pattern PATRON_ASIGNACION = 
        Pattern.compile("^(\\w+)\\s*=\\s*(.+)$");
    private static final Pattern PATRON_OPERACION = 
        Pattern.compile("^(\\w+)\\s*=\\s*(\\w+)\\s*([+\\-*/])\\s*(\\w+)$");
    private static final Pattern PATRON_CONSTANTE = 
        Pattern.compile("^\\d+(\\.\\d+)?$");
    private static final Pattern PATRON_VARIABLE = 
        Pattern.compile("^[a-zA-Z_]\\w*$");

    public Optimizador(List<String> codigoIntermedio) {
        this.codigoOriginal = new ArrayList<>(codigoIntermedio);
        this.codigoOptimizado = new ArrayList<>();
        this.constantes = new HashMap<>();
        this.variablesUtilizadas = new HashSet<>();
        this.optimizacionesAplicadas = 0;
        this.reporteOptimizaciones = new ArrayList<>();
    }

    /**
     * Aplica todas las optimizaciones en secuencia
     */
    public List<String> optimizar() {
        if (codigoOriginal.isEmpty()) {
            reporteOptimizaciones.add("⚠️  No hay código para optimizar");
            return codigoOptimizado;
        }

        reporteOptimizaciones.add("🚀 INICIANDO OPTIMIZACIÓN DE CÓDIGO");
        reporteOptimizaciones.add("Código original: " + codigoOriginal.size() + " instrucciones");

        // Fase 1: Análisis de uso de variables
        analizarUsoVariables();

        // Fase 2: Aplicar optimizaciones en orden
        List<String> codigoPaso1 = propagacionConstantes();
        List<String> codigoPaso2 = simplificacionExpresiones(codigoPaso1);
        List<String> codigoPaso3 = eliminarAsignacionesRedundantes(codigoPaso2);
        List<String> codigoPaso4 = eliminacionCodigoMuerto(codigoPaso3);

        codigoOptimizado = codigoPaso4;

        reporteOptimizaciones.add("✅ OPTIMIZACIÓN COMPLETADA");
        reporteOptimizaciones.add("Optimizaciones aplicadas: " + optimizacionesAplicadas);
        reporteOptimizaciones.add("Reducción: " + 
            String.format("%.1f%%", ((1 - (double)codigoOptimizado.size() / codigoOriginal.size())) * 100));

        return codigoOptimizado;
    }

    public List<String> getReporteOptimizaciones() {
        return new ArrayList<>(reporteOptimizaciones);
    }

    /**
     * Propagación de constantes
     * Reemplaza variables con valores constantes conocidos
     */
    private List<String> propagacionConstantes() {
        List<String> resultado = new ArrayList<>();
        constantes.clear();
        int optimizaciones = 0;

        reporteOptimizaciones.add("\n🔧 PROPAGACIÓN DE CONSTANTES:");

        for (String instruccion : codigoOriginal) {
            String instruccionOptimizada = instruccion;

            // Detectar asignaciones de constantes
            Matcher matcherAsignacion = PATRON_ASIGNACION.matcher(instruccion);
            if (matcherAsignacion.matches()) {
                String variable = matcherAsignacion.group(1);
                String valor = matcherAsignacion.group(2);

                // Si es una constante literal
                if (esConstante(valor)) {
                    constantes.put(variable, valor);
                    resultado.add(instruccion);
                    continue;
                }

                // Si el valor es una variable que conocemos como constante
                if (constantes.containsKey(valor)) {
                    String nuevoValor = constantes.get(valor);
                    instruccionOptimizada = variable + " = " + nuevoValor;
                    constantes.put(variable, nuevoValor);
                    optimizaciones++;
                    reporteOptimizaciones.add("   📌 " + instruccion + " → " + instruccionOptimizada);
                }
            }

            // Reemplazar usos de variables constantes en la instrucción
            for (Map.Entry<String, String> entry : constantes.entrySet()) {
                String variableConst = entry.getKey();
                String valorConst = entry.getValue();
                
                // Reemplazar solo si la variable aparece como operando (no como destino)
                if (!instruccionOptimizada.startsWith(variableConst + " =") && 
                    instruccionOptimizada.contains(variableConst)) {
                    String original = instruccionOptimizada;
                    instruccionOptimizada = instruccionOptimizada.replace(variableConst, valorConst);
                    if (!original.equals(instruccionOptimizada)) {
                        optimizaciones++;
                        reporteOptimizaciones.add("   🔄 " + original + " → " + instruccionOptimizada);
                    }
                }
            }

            resultado.add(instruccionOptimizada);
        }

        optimizacionesAplicadas += optimizaciones;
        reporteOptimizaciones.add("   ✅ Optimizaciones aplicadas: " + optimizaciones);
        return resultado;
    }

    /**
     * Eliminación de código muerto
     * Elimina asignaciones a variables que no se usan después
     */
    private List<String> eliminacionCodigoMuerto(List<String> codigo) {
        List<String> resultado = new ArrayList<>();
        Set<String> asignacionesEliminadas = new HashSet<>();
        int optimizaciones = 0;

        reporteOptimizaciones.add("\n🗑️  ELIMINACIÓN DE CÓDIGO MUERTO:");

        // Identificar qué variables se usan realmente
        Set<String> variablesNecesarias = identificarVariablesNecesarias(codigo);

        for (String instruccion : codigo) {
            boolean mantenerInstruccion = true;

            // Verificar si es una asignación a variable no utilizada
            Matcher matcherAsignacion = PATRON_ASIGNACION.matcher(instruccion);
            if (matcherAsignacion.matches()) {
                String variable = matcherAsignacion.group(1);

                // No mantener si:
                // 1. Es una variable temporal (t0, t1, etc.) Y no es necesaria
                // 2. No es una instrucción especial (DECLARAR, CALL, etc.)
                if (esVariableTemporal(variable) && !variablesNecesarias.contains(variable) &&
                    !instruccion.contains("DECLARAR") && !instruccion.contains("CALL") &&
                    !instruccion.contains("return") && !instruccion.contains("goto") &&
                    !instruccion.contains("ifFalse")) {
                    
                    mantenerInstruccion = false;
                    asignacionesEliminadas.add(variable);
                    optimizaciones++;
                    reporteOptimizaciones.add("   🗑️  Eliminada: " + instruccion);
                }
            }

            if (mantenerInstruccion) {
                resultado.add(instruccion);
            }
        }

        optimizacionesAplicadas += optimizaciones;
        reporteOptimizaciones.add("   ✅ Asignaciones eliminadas: " + optimizaciones);
        return resultado;
    }

    /**
     * Simplificación de expresiones
     * Simplifica operaciones con constantes y expresiones redundantes
     */
    private List<String> simplificacionExpresiones(List<String> codigo) {
        List<String> resultado = new ArrayList<>();
        int optimizaciones = 0;

        reporteOptimizaciones.add("\n🧮 SIMPLIFICACIÓN DE EXPRESIONES:");

        for (String instruccion : codigo) {
            String instruccionOptimizada = instruccion;

            // Simplificar operaciones aritméticas con constantes
            Matcher matcherOperacion = PATRON_OPERACION.matcher(instruccion);
            if (matcherOperacion.matches()) {
                String destino = matcherOperacion.group(1);
                String op1 = matcherOperacion.group(2);
                String operador = matcherOperacion.group(3);
                String op2 = matcherOperacion.group(4);

                // Si ambos operandos son constantes, calcular resultado
                if (esConstante(op1) && esConstante(op2)) {
                    String resultadoCalculado = calcularOperacion(op1, operador, op2);
                    if (resultadoCalculado != null) {
                        instruccionOptimizada = destino + " = " + resultadoCalculado;
                        optimizaciones++;
                        reporteOptimizaciones.add("   🧠 " + instruccion + " → " + instruccionOptimizada);
                    }
                }
                // Simplificar operaciones con identidades
                else if (esConstante(op1) || esConstante(op2)) {
                    String simplificada = simplificarIdentidad(destino, op1, operador, op2);
                    if (simplificada != null) {
                        instruccionOptimizada = simplificada;
                        optimizaciones++;
                        reporteOptimizaciones.add("   ⚡ " + instruccion + " → " + instruccionOptimizada);
                    }
                }

                // Simplificar x = t0 donde t0 es una constante
                if (constantes.containsKey(op1) && operador.equals("=")) {
                    String valorConstante = constantes.get(op1);
                    instruccionOptimizada = destino + " = " + valorConstante;
                    optimizaciones++;
                    reporteOptimizaciones.add("   📦 " + instruccion + " → " + instruccionOptimizada);
                }
            }

            // Eliminar asignaciones redundantes (a = a)
            Matcher matcherAsignacion = PATRON_ASIGNACION.matcher(instruccionOptimizada);
            if (matcherAsignacion.matches()) {
                String variable = matcherAsignacion.group(1);
                String valor = matcherAsignacion.group(2);
                
                if (variable.equals(valor) && esVariableTemporal(variable)) {
                    // No agregar esta instrucción (es redundante)
                    optimizaciones++;
                    reporteOptimizaciones.add("   🔄 Eliminada asignación redundante: " + instruccionOptimizada);
                    continue;
                }
            }

            resultado.add(instruccionOptimizada);
        }

        optimizacionesAplicadas += optimizaciones;
        reporteOptimizaciones.add("   ✅ Simplificaciones aplicadas: " + optimizaciones);
        return resultado;
    }

    /**
    * Eliminación de asignaciones redundantes
    */
    private List<String> eliminarAsignacionesRedundantes(List<String> codigo) {
        List<String> resultado = new ArrayList<>();
        int optimizaciones = 0;

        reporteOptimizaciones.add("\n🔄 ELIMINACIÓN DE ASIGNACIONES REDUNDANTES:");

        for (int i = 0; i < codigo.size(); i++) {
            String instruccion = codigo.get(i);
            boolean mantener = true;

            Matcher matcherAsignacion = PATRON_ASIGNACION.matcher(instruccion);
            if (matcherAsignacion.matches()) {
                String destino = matcherAsignacion.group(1);
                String valor = matcherAsignacion.group(2);

                // Eliminar t2 = y si luego se hace z = t2
                if (esVariableTemporal(destino) && i + 1 < codigo.size()) {
                    String siguiente = codigo.get(i + 1);
                    Matcher matcherSiguiente = PATRON_ASIGNACION.matcher(siguiente);
                    if (matcherSiguiente.matches()) {
                        String siguienteDestino = matcherSiguiente.group(1);
                        String siguienteValor = matcherSiguiente.group(2);
                    
                        if (siguienteValor.equals(destino)) {
                            // Reemplazar la siguiente instrucción
                            resultado.add(siguienteDestino + " = " + valor);
                            i++; // Saltar la siguiente instrucción
                            optimizaciones++;
                            reporteOptimizaciones.add("   🔄 " + instruccion + " + " + siguiente + " → " + siguienteDestino + " = " + valor);
                            mantener = false;
                        }
                    }
                }
            }

            if (mantener) {
                resultado.add(instruccion);
            }
        }

        optimizacionesAplicadas += optimizaciones;
        reporteOptimizaciones.add("   ✅ Optimizaciones aplicadas: " + optimizaciones);
        return resultado;
    }

    /**
     * Métodos auxiliares
     */
    private void analizarUsoVariables() {
        // Seguir variables usadas en return, CALL, condiciones, etc.
        for (String instruccion : codigoOriginal) {
            // Buscar variables en instrucciones
            String[] partes = instruccion.split("\\s+");
            for (String parte : partes) {
                if (esVariable(parte) && !parte.equals("=") && !esPalabraClave(parte)) {
                    variablesUtilizadas.add(parte);
                }
            }
        }
    }

    private Set<String> identificarVariablesNecesarias(List<String> codigo) {
        Set<String> necesarias = new HashSet<>(variablesUtilizadas);
        
        // También considerar variables usadas en condiciones y llamadas
        for (String instruccion : codigo) {
            if (instruccion.contains("ifFalse") || instruccion.contains("CALL")) {
                // Extraer todas las variables de estas instrucciones
                String[] tokens = instruccion.split("\\s+");
                for (String token : tokens) {
                    if (esVariable(token)) {
                        necesarias.add(token);
                    }
                }
            }
        }
        
        return necesarias;
    }

    private boolean esConstante(String valor) {
        return PATRON_CONSTANTE.matcher(valor).matches() || constantes.containsKey(valor);
    }

    private boolean esVariable(String token) {
        return PATRON_VARIABLE.matcher(token).matches() && 
               !token.equals("ifFalse") && !token.equals("goto") && 
               !token.equals("CALL") && !token.equals("return");
    }

    private boolean esVariableTemporal(String variable) {
        return variable.startsWith("t") && variable.length() > 1 && 
               Character.isDigit(variable.charAt(1));
    }

    private boolean esPalabraClave(String token) {
        return token.equals("DECLARAR") || token.equals("FUNCION") || 
               token.equals("FIN_FUNCION") || token.equals("PARAM") ||
               token.equals("INICIO_PROGRAMA") || token.equals("FIN_PROGRAMA");
    }

    private String calcularOperacion(String op1, String operador, String op2) {
        try {
            double num1 = Double.parseDouble(op1);
            double num2 = Double.parseDouble(op2);
            double resultado = 0;

            switch (operador) {
                case "+": resultado = num1 + num2; break;
                case "-": resultado = num1 - num2; break;
                case "*": resultado = num1 * num2; break;
                case "/": 
                    if (num2 != 0) resultado = num1 / num2; 
                    else return null;
                    break;
                default: return null;
            }

            // Si es entero, mostrar sin decimal
            if (resultado == (int) resultado) {
                return String.valueOf((int) resultado);
            } else {
                return String.valueOf(resultado);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String simplificarIdentidad(String destino, String op1, String operador, String op2) {
        // x + 0 → x
        if (operador.equals("+") && esCero(op2)) {
            return destino + " = " + op1;
        }
        // x - 0 → x  
        if (operador.equals("-") && esCero(op2)) {
            return destino + " = " + op1;
        }
        // x * 1 → x
        if (operador.equals("*") && esUno(op2)) {
            return destino + " = " + op1;
        }
        // x / 1 → x
        if (operador.equals("/") && esUno(op2)) {
            return destino + " = " + op1;
        }
        // 0 + x → x
        if (operador.equals("+") && esCero(op1)) {
            return destino + " = " + op2;
        }
        // 1 * x → x
        if (operador.equals("*") && esUno(op1)) {
            return destino + " = " + op2;
        }
        return null;
    }

    private boolean esCero(String valor) {
        return valor.equals("0") || valor.equals("0.0");
    }

    private boolean esUno(String valor) {
        return valor.equals("1") || valor.equals("1.0");
    }

    /**
     * Métodos de acceso y reporte
     */
    public List<String> getCodigoOptimizado() {
        return new ArrayList<>(codigoOptimizado);
    }

    public int getOptimizacionesAplicadas() {
        return optimizacionesAplicadas;
    }

    public List<String> getReporteOptimizaciones() {
        return new ArrayList<>(reporteOptimizaciones);
    }

    public void mostrarReporteOptimizacion() {
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   REPORTE DE OPTIMIZACIÓN                   ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        
        for (String linea : reporteOptimizaciones) {
            System.out.println(linea);
        }

        System.out.println("\n📊 RESUMEN FINAL:");
        System.out.println("─".repeat(40));
        System.out.println("Instrucciones originales: " + codigoOriginal.size());
        System.out.println("Instrucciones optimizadas: " + codigoOptimizado.size());
        System.out.println("Total optimizaciones: " + optimizacionesAplicadas);
        System.out.printf("Reducción: %.1f%%\n", 
            ((1 - (double)codigoOptimizado.size() / codigoOriginal.size())) * 100);
    }

    public void mostrarCodigoOptimizado() {
        System.out.println("\n🔄 CÓDIGO OPTIMIZADO:");
        System.out.println("═".repeat(60));
        for (int i = 0; i < codigoOptimizado.size(); i++) {
            System.out.printf("%3d │ %s%n", i + 1, codigoOptimizado.get(i));
        }
    }
}