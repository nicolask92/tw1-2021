package ar.edu.unlam.tallerweb1.modelo;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Horario {
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Horario(LocalTime horaInicio, LocalTime horaFin) throws Exception {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}
