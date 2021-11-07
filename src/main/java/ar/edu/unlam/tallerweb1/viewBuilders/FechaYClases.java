package ar.edu.unlam.tallerweb1.viewBuilders;

import ar.edu.unlam.tallerweb1.modelo.Clase;

import java.util.List;

public class FechaYClases {
    private int dia;
    private boolean esDomingo;
    private List<Clase> clases;

    public FechaYClases(int dia, boolean esDomingo, List<Clase> clases) {
        this.esDomingo = esDomingo;
        this.dia = dia;
        this.clases = clases;
    }

    public boolean isEsDomingo() {
        return esDomingo;
    }

    public void setEsDomingo(boolean esDomingo) {
        this.esDomingo = esDomingo;
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
