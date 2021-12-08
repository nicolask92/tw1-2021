package ar.edu.unlam.tallerweb1.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
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

    Boolean debitoAutomatico;

    BigDecimal importePagado;

    Boolean activo;
    LocalDate fechaCancelacion;
    LocalDate fechaDeFinalizacion;

    @Column()
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

    public void setImportePagado(BigDecimal importePagado) {
        this.importePagado = importePagado;
    }

    public Boolean getDebitoAutomatico() {
        return debitoAutomatico != null ? debitoAutomatico : false;
    }

    public void setDebitoAutomatico(Boolean debitoAutomatico) {
        this.debitoAutomatico = debitoAutomatico;
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

    public String getFechaCancelacion() {
        return fechaCancelacion.toString();
    }

    public String getFechaDeFinalizacion() {
        return fechaDeFinalizacion.toString();
    }

    public LocalDate getFechaDeFinalizacionEnLocalDate() {
        return fechaDeFinalizacion;
    }

    public Boolean esActivo() {
        return activo;
    }

    public Pago cancelarPlan() {
        this.fechaCancelacion = LocalDate.now();
        this.activo = false;
        return this;
    }

    public Pago activarPlan() {
        this.activo = true;
        return this;
    }

    public Pago cancelarSuscripcion() {
        debitoAutomatico = false;
        return this;
    }

    public BigDecimal getImportePagado() {
        return importePagado;
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
