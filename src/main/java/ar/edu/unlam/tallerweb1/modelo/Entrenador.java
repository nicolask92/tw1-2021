package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Entrenador extends Usuario {

    @ManyToMany(mappedBy = "profesores")
    List<Clase> dictadas;

    public Entrenador(String nombre, String apellido, String email, List<Clase> dictadas) {
        super(nombre, apellido, email);
        this.dictadas = dictadas;
    }

    public Entrenador(String nombre, String apellido, String email) {
        super(nombre, apellido, email);
    }

    public Entrenador(List<Clase> dictadas) {
        this.dictadas = dictadas;
    }

    public Entrenador() {
    }
}
