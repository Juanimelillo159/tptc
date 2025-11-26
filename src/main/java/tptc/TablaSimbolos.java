package tptc;

import java.util.*;

/**
 * Tabla de símbolos con soporte para múltiples ámbitos (scopes)
 */
public class TablaSimbolos {
    private List<Map<String, Simbolo>> pilaAmbitos;
    private int nivelActual;
    private Map<String, SimboloFuncion> funciones;
    private SimboloFuncion funcionActual;

    // NUEVO: lista global con TODAS las variables que se han declarado,
    // sin importar si el ámbito ya se cerró.
    private List<SimboloVariable> todasLasVariables;

    public TablaSimbolos() {
        this.pilaAmbitos = new ArrayList<>();
        this.nivelActual = -1;
        this.funciones = new HashMap<>();
        this.funcionActual = null;
        this.todasLasVariables = new ArrayList<>();

        // Crear ámbito global
        abrirAmbito();
    }

    /**
     * Abre un nuevo ámbito
     */
    public void abrirAmbito() {
        pilaAmbitos.add(new HashMap<>());
        nivelActual++;
    }

    /**
     * Cierra el ámbito actual (para búsquedas futuras)
     * Las variables se mantienen en 'todasLasVariables' para reportes.
     */
    public void cerrarAmbito() {
        if (nivelActual > 0) { // nunca quitamos el global
            pilaAmbitos.remove(nivelActual);
            nivelActual--;
        }
    }

    /**
     * Inserta un símbolo en el ámbito actual
     */
    public boolean insertar(Simbolo simbolo) {
        if (nivelActual < 0) {
            return false;
        }

        Map<String, Simbolo> ambitoActual = pilaAmbitos.get(nivelActual);

        // Verificar si ya existe en el ámbito actual
        if (ambitoActual.containsKey(simbolo.getNombre())) {
            return false; // Ya existe
        }

        ambitoActual.put(simbolo.getNombre(), simbolo);

        // Si es una función, también agregarla al mapa de funciones
        if (simbolo instanceof SimboloFuncion) {
            funciones.put(simbolo.getNombre(), (SimboloFuncion) simbolo);
        }

        // NUEVO: si es variable (local, global o parámetro), guárdala en la lista global
        if (simbolo instanceof SimboloVariable) {
            todasLasVariables.add((SimboloVariable) simbolo);
        }

        return true;
    }

    /**
     * Busca un símbolo en todos los ámbitos (desde el actual hacia el global)
     */
    public Simbolo buscar(String nombre) {
        // Buscar desde el ámbito actual hacia el global
        for (int i = nivelActual; i >= 0; i--) {
            Map<String, Simbolo> ambito = pilaAmbitos.get(i);
            if (ambito.containsKey(nombre)) {
                return ambito.get(nombre);
            }
        }
        return null;
    }

    /**
     * Busca un símbolo solo en el ámbito actual
     */
    public Simbolo buscarEnAmbitoActual(String nombre) {
        if (nivelActual < 0) {
            return null;
        }

        Map<String, Simbolo> ambitoActual = pilaAmbitos.get(nivelActual);
        return ambitoActual.get(nombre);
    }

    /**
     * Busca una función específicamente
     */
    public SimboloFuncion buscarFuncion(String nombre) {
        return funciones.get(nombre);
    }

    /**
     * Establece la función actual (para análisis semántico)
     */
    public void setFuncionActual(SimboloFuncion funcion) {
        this.funcionActual = funcion;
    }

    /**
     * Obtiene la función actual
     */
    public SimboloFuncion getFuncionActual() {
        return funcionActual;
    }

    /**
     * Obtiene el nivel de ámbito actual
     */
    public int getNivelActual() {
        return nivelActual;
    }

    /**
     * Obtiene todas las variables del ámbito actual
     */
    public List<SimboloVariable> getVariablesAmbitoActual() {
        List<SimboloVariable> variables = new ArrayList<>();

        if (nivelActual >= 0) {
            Map<String, Simbolo> ambitoActual = pilaAmbitos.get(nivelActual);
            for (Simbolo simbolo : ambitoActual.values()) {
                if (simbolo instanceof SimboloVariable) {
                    variables.add((SimboloVariable) simbolo);
                }
            }
        }

        return variables;
    }

    /**
     * Obtiene TODAS las variables declaradas en cualquier ámbito
     * (globales, locales y parámetros), incluso si el ámbito ya se cerró.
     */
    public List<SimboloVariable> getTodasLasVariables() {
        return new ArrayList<>(todasLasVariables);
    }

    /**
     * Obtiene todas las funciones
     */
    public Collection<SimboloFuncion> getTodasLasFunciones() {
        return funciones.values();
    }

    /**
     * Verifica si existe la función main
     */
    public boolean existeFuncionMain() {
        SimboloFuncion main = funciones.get("main");
        return main != null && main.getTipoRetorno().equals("int");
    }

    /**
     * Marca un símbolo como utilizado
     */
    public void marcarComoUtilizado(String nombre) {
        Simbolo simbolo = buscar(nombre);
        if (simbolo != null) {
            simbolo.setUtilizado(true);
        }
    }

    /**
     * Obtiene estadísticas de la tabla de símbolos
     */
    public Map<String, Integer> getEstadisticas() {
        Map<String, Integer> stats = new HashMap<>();

        int totalVariables = 0;
        int variablesUtilizadas = 0;
        int variablesInicializadas = 0;

        for (SimboloVariable var : getTodasLasVariables()) {
            totalVariables++;
            if (var.isUtilizado()) variablesUtilizadas++;
            if (var.isInicializado()) variablesInicializadas++;
        }

        stats.put("totalVariables", totalVariables);
        stats.put("variablesUtilizadas", variablesUtilizadas);
        stats.put("variablesInicializadas", variablesInicializadas);
        stats.put("totalFunciones", funciones.size());
        stats.put("nivelMaximoAmbito", nivelActual);

        return stats;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== TABLA DE SÍMBOLOS (debug interno) ===\n");

        for (int i = 0; i <= nivelActual; i++) {
            sb.append(String.format("Ámbito nivel %d:\n", i));
            Map<String, Simbolo> ambito = pilaAmbitos.get(i);

            if (ambito.isEmpty()) {
                sb.append("  (vacío)\n");
            } else {
                for (Simbolo simbolo : ambito.values()) {
                    sb.append(String.format("  %s\n", simbolo.toString()));
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
