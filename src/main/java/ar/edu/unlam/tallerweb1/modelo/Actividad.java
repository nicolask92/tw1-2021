package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Tipo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Actividad {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    String descripcion;

    @OneToOne
    Calendario calendario;

    Tipo tipo;

    Float precio;

    @Transient
    List<DiaYHorario> diaYHorario;

    @OneToMany
    List<Clase> clases;

    @Transient
    Frecuencia frecuencia;

    @Transient
    Periodo periodo;

    @Transient
    Horario horario;

    public Actividad(String descripcion, Tipo tipo, Float precio, Frecuencia frecuencia, Periodo horario) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.frecuencia = Frecuencia.CON_INICIO_Y_FIN;
    }

    public Actividad(String descripcion, Tipo tipo, Float precio) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.frecuencia = Frecuencia.REPETITIVA;
    }

    public Actividad() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
