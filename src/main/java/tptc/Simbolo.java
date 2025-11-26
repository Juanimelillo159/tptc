package tptc;

/**
 * Clase base para representar símbolos en la tabla de símbolos
 */
public abstract class Simbolo {
    protected String nombre;
    protected TipoSimbolo tipo;
    protected int linea;
    protected int columna;
    protected boolean utilizado;
    protected boolean inicializado;

    // Ámbito lógico: "global", "main", "sumar", etc.
    protected String ambito;

    public enum TipoSimbolo {
        VARIABLE,
        FUNCION,
        PARAMETRO
    }

    public Simbolo(String nombre, TipoSimbolo tipo, int linea, int columna) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
        this.utilizado = false;
        this.inicializado = false;
        this.ambito = "global"; // por defecto, luego se ajusta en el analizador
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public TipoSimbolo getTipo() {
        return tipo;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }

    public boolean isUtilizado() {
        return utilizado;
    }

    public boolean isInicializado() {
        return inicializado;
    }

    public boolean isInicializada() {
        return inicializado;
    } // Alias para compatibilidad

    public void setUtilizado(boolean utilizado) {
        this.utilizado = utilizado;
    }

    public void setInicializado(boolean inicializado) {
        this.inicializado = inicializado;
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public abstract String getTipoDato();

    public abstract String getDescripcion();

    @Override
    public String toString() {
        return String.format("%s '%s' (%s) en línea %d",
                tipo.toString().toLowerCase(), nombre, getTipoDato(), linea);
    }
}
