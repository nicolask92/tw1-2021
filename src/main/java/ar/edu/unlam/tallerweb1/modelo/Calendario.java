package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.List;

// TODO ver que hacemos con esto, estaba en el diagrama, pero no me cierra como implementarlo
enum Mes {
    ENERO,
    FEBRERO,
    MARZO,
    ABRIL,
    MAYO,
    JUNIO,
    JULIO,
    AGOSTO,
    SEPTIEMBRE,
    OCTUBRE,
    NOVIEMBRE,
    DICIEMBRE
}

@Entity
public class Calendario {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany
    List<Actividad> actividades;

    public Calendario(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public Calendario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
