package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Gimnasio {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    String nombre;
    String direccion;

    @OneToMany
    List<Usuario> usuarios;

    @OneToOne
    Calendario calendario;

    Integer cantidadMaxima = 100;

    public Gimnasio() {
    }

    public Gimnasio(String nombre, String direccion, Integer cantidadMaxima) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.cantidadMaxima = cantidadMaxima;
    }

    public Gimnasio(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
