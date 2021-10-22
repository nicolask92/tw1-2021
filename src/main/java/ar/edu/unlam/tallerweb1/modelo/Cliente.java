package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class Cliente extends Usuario {

    @Enumerated(EnumType.STRING)
    Plan plan;

    @ManyToMany(mappedBy = "clientes")
    List<Clase> clases;

    public Cliente(String nombre, String apellido, String email, Plan plan, List<Clase> clases) {
        super(nombre, apellido, email);
        this.plan = plan;
        this.clases = clases;
    }

    public Cliente(String nombre, String apellido, String email) {
        this.plan = Plan.NINGUNO;
        this.clases = Collections.emptyList();
    }

    public Cliente() {
    }
}
