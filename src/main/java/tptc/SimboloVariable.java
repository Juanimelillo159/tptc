package tptc;

/**
 * Representa una variable en la tabla de símbolos
 */
public class SimboloVariable extends Simbolo {
    private String tipoDato;
    private Object valor;
    private boolean esParametro;
    private boolean esArreglo;
    private int tamanioArreglo;

    public SimboloVariable(String nombre, String tipoDato, int linea, int columna, boolean esParametro,
            String ambito, boolean esArreglo, int tamanioArreglo) {
        super(nombre, esParametro ? TipoSimbolo.PARAMETRO : TipoSimbolo.VARIABLE, linea, columna, ambito);
        this.tipoDato = tipoDato;
        this.valor = null;
        this.esParametro = esParametro;
        this.esArreglo = esArreglo;
        this.tamanioArreglo = Math.max(tamanioArreglo, 0);
        // Los parámetros se consideran inicializados por defecto
        if (esParametro) {
            this.inicializado = true;
        }
    }

    public SimboloVariable(String nombre, String tipoDato, int linea, int columna) {
        this(nombre, tipoDato, linea, columna, false, "global", false, 0);
    }

    public SimboloVariable(String nombre, String tipoDato, int linea, int columna, boolean esParametro, String ambito) {
        this(nombre, tipoDato, linea, columna, esParametro, ambito, false, 0);
    }

    public static SimboloVariable crearArreglo(String nombre, String tipoDato, int linea, int columna, String ambito,
            int tamanio) {
        return new SimboloVariable(nombre, tipoDato, linea, columna, false, ambito, true, tamanio);
    }
    
    @Override
    public String getTipoDato() {
        return tipoDato;
    }
    
    public Object getValor() {
        return valor;
    }
    
    public void setValor(Object valor) {
        this.valor = valor;
        this.inicializado = true;
    }
    
    public boolean esParametro() {
        return esParametro;
    }

    public boolean esArreglo() {
        return esArreglo;
    }

    public int getTamanioArreglo() {
        return tamanioArreglo;
    }
    
    @Override
    public String getDescripcion() {
        String desc = String.format("Variable %s de tipo %s", nombre, tipoDato);
        if (esParametro) {
            desc = "Parámetro " + desc.toLowerCase();
        }
        return desc;
    }
    
    /**
     * Verifica si el tipo es compatible para asignación
     */
    public boolean esCompatibleCon(String otroTipo) {
        if (tipoDato.equals(otroTipo)) {
            return true;
        }
        
        // Reglas de compatibilidad básicas para C++
        if (tipoDato.equals("double")) {
            return otroTipo.equals("int"); // int puede asignarse a double
        }
        
        if (tipoDato.equals("char")) {
            return otroTipo.equals("int") && valor != null; // int puede asignarse a char si está en rango
        }
        
        return false;
    }
}