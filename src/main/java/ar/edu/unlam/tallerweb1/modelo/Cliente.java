package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

enum Plan {
    PREMIUM,
    BASICO,
    NINGUNO
}

@Entity
@Table(name="usuario")
public class Cliente extends Usuario{

    @Enumerated(EnumType.STRING)
    Plan plan;

    @OneToMany
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
