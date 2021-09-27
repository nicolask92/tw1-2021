package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Clase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarioDeActividades {
    private Map<Integer, List<Clase>> clases = new HashMap<>();

    public CalendarioDeActividades(Map<Integer, List<Clase>> clases) {
        this.clases = clases;
    }

    public Map<Integer, List<Clase>> getClases() {
        return clases;
    }
}
