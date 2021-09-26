package ar.edu.unlam.tallerweb1.modelo;

import java.time.LocalDateTime;

public class Periodo {
    private LocalDateTime inicio;
    private LocalDateTime fin;

    public Periodo(LocalDateTime inicio, LocalDateTime fin) throws Exception {
        if (fin.isBefore(inicio)) throw new Exception("El fin no puede ser antes que el inicio");
        this.inicio = inicio;
        this.fin = fin;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }
}