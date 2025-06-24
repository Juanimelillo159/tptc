package tptc;

import java.util.List;
import java.util.ArrayList;

/**
 * Representa una función en la tabla de símbolos
 */
public class SimboloFuncion extends Simbolo {
    private String tipoRetorno;
    private List<SimboloVariable> parametros;
    private boolean tieneReturn;
    private boolean esMain;

    public SimboloFuncion(String nombre, String tipoRetorno, int linea, int columna) {
        super(nombre, TipoSimbolo.FUNCION, linea, columna);
        this.tipoRetorno = tipoRetorno;
        this.parametros = new ArrayList<>();
        this.tieneReturn = false;
        this.esMain = nombre.equals("main");
        this.inicializado = true; // Las funciones se consideran "inicializadas" al declararse
    }

    @Override
    public String getTipoDato() {
        return tipoRetorno;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public List<SimboloVariable> getParametros() {
        return new ArrayList<>(parametros);
    }

    public void agregarParametro(SimboloVariable parametro) {
        parametros.add(parametro);
    }

    public boolean tieneReturn() {
        return tieneReturn;
    }

    public void setTieneReturn(boolean tieneReturn) {
        this.tieneReturn = tieneReturn;
    }

    public boolean esMain() {
        return esMain;
    }

    public int getNumeroParametros() {
        return parametros.size();
    }

    /**
     * Verifica si los tipos de argumentos coinciden con los parámetros
     */
    public boolean coincideSignatura(List<String> tiposArgumentos) {
        if (tiposArgumentos.size() != parametros.size()) {
            return false;
        }

        for (int i = 0; i < parametros.size(); i++) {
            String tipoParametro = parametros.get(i).getTipoDato();
            String tipoArgumento = tiposArgumentos.get(i);

            if (!sonTiposCompatibles(tipoParametro, tipoArgumento)) {
                return false;
            }
        }

        return true;
    }

    private boolean sonTiposCompatibles(String tipoParametro, String tipoArgumento) {
        if (tipoParametro.equals(tipoArgumento)) {
            return true;
        }

        // Reglas de compatibilidad para parámetros
        if (tipoParametro.equals("double") && tipoArgumento.equals("int")) {
            return true;
        }

        return false;
    }

    @Override
    public String getDescripcion() {
        StringBuilder desc = new StringBuilder();
        desc.append(String.format("Función %s que retorna %s", nombre, tipoRetorno));

        if (!parametros.isEmpty()) {
            desc.append(" con parámetros: ");
            for (int i = 0; i < parametros.size(); i++) {
                if (i > 0)
                    desc.append(", ");
                SimboloVariable param = parametros.get(i);
                desc.append(param.getTipoDato()).append(" ").append(param.getNombre());
            }
        } else {
            desc.append(" sin parámetros");
        }

        return desc.toString();
    }

    public String getSignatura() {
        StringBuilder sig = new StringBuilder();
        sig.append(tipoRetorno).append(" ").append(nombre).append("(");

        for (int i = 0; i < parametros.size(); i++) {
            if (i > 0)
                sig.append(", ");
            sig.append(parametros.get(i).getTipoDato());
        }

        sig.append(")");
        return sig.toString();
    }
}