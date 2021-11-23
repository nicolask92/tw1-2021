package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
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

    boolean activo;
    LocalDate fechaCancelacion;
    LocalDate fechaDeFinalizacion;

    @Enumerated(EnumType.STRING)
    Plan plan;

    public Pago(Cliente cliente, Month mes, Integer anio, Plan plan) {
        this.cliente = cliente;
        this.mes = mes;
        this.anio = anio;
        this.plan = plan;
        activo = true;
        Calendar calendar = Calendar.getInstance();
        calendar.set(anio, mes.getValue() - 1, 1);
        this.fechaDeFinalizacion = LocalDate.of(anio, mes.getValue(), calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
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

    public boolean esActivo() {
        return activo;
    }

    public Pago cancelarPlan() {
        this.fechaCancelacion = LocalDate.now();
        this.activo = false;
        return this;
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
