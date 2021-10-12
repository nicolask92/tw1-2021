package ar.edu.unlam.tallerweb1.viewBuilders;

import ar.edu.unlam.tallerweb1.modelo.Clase;

import java.util.List;

public class FechaYClases {
    int dia;
    List<Clase> clases;

    public FechaYClases(int dia, List<Clase> clases) {
        this.dia = dia;
        this.clases = clases;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }
}