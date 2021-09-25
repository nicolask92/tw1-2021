package ar.edu.unlam.tallerweb1.modelo;

import java.time.LocalDate;

public class Periodo {
    private LocalDate inicio;
    private LocalDate fin;

    public Periodo(LocalDate inicio, LocalDate fin) throws Exception {
        if (fin.isBefore(inicio)) throw new Exception("El fin no puede ser antes que el inicio");
        this.inicio = inicio;
        this.fin = fin;
    }
}
