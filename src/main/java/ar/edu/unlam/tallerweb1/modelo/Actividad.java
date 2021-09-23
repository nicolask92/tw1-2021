package ar.edu.unlam.tallerweb1.modelo;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

enum Tipo {
    CROSSFIT,
    NATACION,
    FUNCIONAL,
    BOXEO,
    RUNNING,
    MUSCULACION
}

enum Dia {
    LUNES,
    MARTES,
    MIERCOLES,
    JUEVES,
    VIERNES,
    SABADO,
    DOMINGO
}

enum Frecuencia {
    REPETITIVA,
    TEMPORADA,
    CON_INICIO_Y_FIN
}

@Entity
public class Actividad {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    String descripcion;

    @OneToOne
    Calendario calendario;

    @NotNull
    Tipo tipo;

    @NotNull
    Float precio;

    @Transient
    List<Dia> diasConActividad;

    @NotNull
    @OneToOne
    Clase clase;

    Frecuencia frecuencia;

    @Transient
    Periodo horario;

    public Actividad(String descripcion, Tipo tipo, Float precio, Clase clase, Frecuencia frecuencia, Periodo horario) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.clase = clase;
        this.frecuencia = frecuencia;
        this.horario = horario;
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
