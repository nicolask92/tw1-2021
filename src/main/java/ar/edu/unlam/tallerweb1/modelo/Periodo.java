package ar.edu.unlam.tallerweb1.modelo;

import java.time.LocalDate;

public class Periodo {
    LocalDate inicio;
    LocalDate fin;

    public Periodo(LocalDate inicio, LocalDate fin) {
        this.inicio = inicio;
        this.fin = fin;
    }
}
