package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Periodo {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime inicio;
    private LocalDateTime fin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Periodo(LocalDateTime inicio, LocalDateTime fin) throws Exception {
        if (fin.isBefore(inicio)) throw new Exception("El fin no puede ser antes que el inicio");
        this.inicio = inicio;
        this.fin = fin;
    }

    public Periodo() {
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }
}