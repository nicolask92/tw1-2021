package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    List<Cliente> clientes;

    @OneToMany
    List<Entrenador> profesores;

    @Transient
    Actividad actividad;

    @Column(nullable = false)
    Modalidad modalidad;

    LocalDateTime diaClase;

    Integer cupoMaximo = 20;

    public Clase(LocalDateTime diaClase, Actividad actividad, Modalidad modalidad) throws Exception {
        if (actividad.frecuencia == Frecuencia.CON_INICIO_Y_FIN) {
            if (diaClase.isBefore(actividad.periodo.getInicio()) || diaClase.isAfter(actividad.periodo.getFin())) {
                throw new Exception("La creacion de esta clase es anterior o posterior al periodo en el cual se realiza");
            }
        }
        this.diaClase = diaClase;
        this.actividad = actividad;
        this.modalidad = modalidad;
        this.clientes = new ArrayList<>();
    }

    public Clase() {
    }

    List<Cliente> agregarCliente(Cliente cliente) throws Exception {
        if (modalidad == Modalidad.PRESENCIAL && clientes.size() == cupoMaximo) {
            throw new Exception("Ya se excedio la cantidad maxima de personas que pueden asistir a este lugar");
        }
        this.clientes.add(cliente);
        return clientes;
    }

    public LocalDateTime getDiaClase() {
        return diaClase;
    }

    public void setDiaClase(LocalDateTime diaClase) {
        this.diaClase = diaClase;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCantidadMaxima(int nuevaCantidadMaxima) {
        this.cupoMaximo = nuevaCantidadMaxima;
    }
}
