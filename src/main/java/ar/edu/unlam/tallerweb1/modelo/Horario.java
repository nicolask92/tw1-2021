package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Horario {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="hora_inicio")
    private LocalTime horaInicio;
    @Column(name="hora_fin")
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

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}
