package tptc;

/**
 * Representa una variable en la tabla de símbolos
 */
public class SimboloVariable extends Simbolo {
    private String tipoDato;
    private Object valor;
    private boolean esParametro;
    
    public SimboloVariable(String nombre, String tipoDato, int linea, int columna, boolean esParametro) {
        super(nombre, esParametro ? TipoSimbolo.PARAMETRO : TipoSimbolo.VARIABLE, linea, columna);
        this.tipoDato = tipoDato;
        this.valor = null;
        this.esParametro = esParametro;
        // Los parámetros se consideran inicializados por defecto
        if (esParametro) {
            this.inicializado = true;
        }
    }
    
    public SimboloVariable(String nombre, String tipoDato, int linea, int columna) {
        this(nombre, tipoDato, linea, columna, false);
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