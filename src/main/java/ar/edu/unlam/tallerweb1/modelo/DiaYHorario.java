package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Dia;

public class DiaYHorario {
    private Horario horario;
    private Dia dia;

    public DiaYHorario(Horario horario, Dia dia) {
        this.horario = horario;
        this.dia = dia;
    }
}
