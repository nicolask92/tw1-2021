package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Clase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarioDeActividades {
    private Map<Integer, List<Clase>> clases = new HashMap<>();
    private List<String> conjuntoDias;

    public CalendarioDeActividades(Map<Integer, List<Clase>> clases) {
        this.clases = clases;
    }

    public Map<Integer, List<Clase>> getClases() {
        return clases;
    }

    public void setConjuntoDias(List<String> conjuntoDias) {
        this.conjuntoDias = conjuntoDias;
    }

    public List<String> getConjuntoDias() {
        return conjuntoDias;
    }
}
