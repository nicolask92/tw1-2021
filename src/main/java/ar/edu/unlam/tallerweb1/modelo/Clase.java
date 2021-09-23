package ar.edu.unlam.tallerweb1.modelo;

import com.sun.istack.NotNull;

import javax.persistence.*;
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

    @NotNull
    Modalidad modalidad;

    Integer cupoMaximo = 20;

    public Clase(Periodo periodo, Actividad actividad, Modalidad modalidad) {
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
