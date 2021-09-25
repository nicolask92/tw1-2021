package ar.edu.unlam.tallerweb1.modelo;

import java.time.LocalTime;

public class Horario {
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Horario(LocalTime horaInicio, LocalTime horaFin) throws Exception {
        if (horaFin.isBefore(horaInicio)) throw new Exception("La hora de fin no puede ser anterior a la hora de fin");

        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}
