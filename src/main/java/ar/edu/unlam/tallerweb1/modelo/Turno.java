package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Cliente cliente;

    @OneToOne
    private Clase clase;

    private LocalDate fechaYHoraDeReserva;

    private boolean asisitio = false;

    public Turno(Cliente cliente, Clase clase, LocalDate fechaYHoraDeReserva) {
        this.cliente = cliente;
        this.clase = clase;
        this.fechaYHoraDeReserva = fechaYHoraDeReserva;
    }

    public Turno() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
