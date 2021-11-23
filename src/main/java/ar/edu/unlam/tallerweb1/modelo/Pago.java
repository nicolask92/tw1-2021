package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.time.Month;
import java.util.Objects;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Cliente cliente;

    Month mes;

    Integer anio;

    @Enumerated(EnumType.STRING)
    Plan plan;

    public Pago(Cliente cliente, Month mes, Integer anio, Plan plan) {
        this.cliente = cliente;
        this.mes = mes;
        this.anio = anio;
        this.plan = plan;
    }

    public Pago() {
    }

    public Integer getAnio() {
        return anio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Month getMes() {
        return mes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Plan getPlan() {
        return plan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pago pago = (Pago) o;
        return cliente.equals(pago.cliente) && mes == pago.mes && anio.equals(pago.anio) && plan == pago.plan;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, mes, anio, plan);
    }
}
