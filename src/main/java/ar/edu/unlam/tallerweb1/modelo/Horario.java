package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalTime;

@Entity
public class Horario {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Horario(LocalTime horaInicio, LocalTime horaFin) throws Exception {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Horario() {
    }
}
