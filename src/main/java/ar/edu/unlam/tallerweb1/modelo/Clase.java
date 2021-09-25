package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Frecuencia;

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

    @OneToMany
    List<Entrenador> profesores;

    @Transient
    Actividad actividad;

    @Column(nullable = false)
    Modalidad modalidad;

    LocalDate diaClase;

    Integer cupoMaximo = 20;

    public Clase(LocalDate diaClase, Actividad actividad, Modalidad modalidad) throws Exception {
        if (actividad.frecuencia == Frecuencia.CON_INICIO_Y_FIN) {
            if (diaClase.isBefore(actividad.periodo.getInicio()) || diaClase.isAfter(actividad.periodo.getFin())) {
                throw new Exception("La creacion de esta clase es anterior o posterior al periodo en el cual se realiza");
            }
        }

        if (modalidad == Modalidad.PRESENCIAL && clientes.size() == cupoMaximo) {
            throw new Exception("Ya se excedio la cantidad maxima de personas que pueden asistir a este lugar");
        }
        this.diaClase = diaClase;
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
