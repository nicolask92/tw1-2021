package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.Entity;

@Entity
public class Administrador extends Usuario {

    public Administrador(String nombre, String apellido, String email) {
        super(nombre, apellido, email);
    }

    public Administrador() {
    }
}