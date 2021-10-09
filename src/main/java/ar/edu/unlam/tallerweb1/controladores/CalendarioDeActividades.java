package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Clase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarioDeActividades {
    private Map<Integer, List<Clase>> fechasYSusClases = new HashMap<>();
    private List<String> conjuntoDias;

    public CalendarioDeActividades(Map<Integer, List<Clase>> fechasYSusClases) {
        this.fechasYSusClases = fechasYSusClases;
    }

    public Map<Integer, List<Clase>> getFechasYSusClases() {
        return fechasYSusClases;
    }

    public void setConjuntoDias(List<String> conjuntoDias) {
        this.conjuntoDias = conjuntoDias;
    }

    public List<String> getConjuntoDias() {
        return conjuntoDias;
    }
}
