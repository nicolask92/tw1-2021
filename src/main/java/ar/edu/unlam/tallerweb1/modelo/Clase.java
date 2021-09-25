package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

enum Modalidad {
    ONLINE,
    PRESENCIAL
}

@Entity
public class Clase {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany
    List<Cliente> clientes;

    @Transient
    Periodo periodo;

    @OneToMany
    List<Entrenador> profesores;

    @Transient
    Actividad actividad;

    @Column(nullable = false)
    Modalidad modalidad;

    Integer cupoMaximo = 20;

    public Clase(LocalDate dia, Actividad actividad, Modalidad modalidad) {
        if (actividad.frecuencia == Frecuencia.CON_INICIO_Y_FIN) {
        //    if (dia.isAfter(actividad.))
        }
        this.periodo = periodo;
        this.actividad = actividad;
        this.modalidad = modalidad;
    }

    public Clase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
