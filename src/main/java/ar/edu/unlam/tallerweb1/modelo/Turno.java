package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Clase clase;

    @Column(name="fecha_y_hora_de_reserva")
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

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }
}
