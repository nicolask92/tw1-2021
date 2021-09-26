package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Tipo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String descripcion;

    @OneToOne
    Calendario calendario;

    Tipo tipo;

    Float precio;

    @Transient
    List<DiaYHorario> diaYHorario;

    @OneToMany
    List<Clase> clases;

    @Transient
    Frecuencia frecuencia;

    @Transient
    Periodo periodo;

    @Transient
    Horario horario;

    public Actividad(String descripcion, Tipo tipo, Float precio, Frecuencia frecuencia, Periodo periodo, Horario horario) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.frecuencia = Frecuencia.CON_INICIO_Y_FIN;
        this.periodo = periodo;
        this.horario = horario;
    }

    public Actividad(String descripcion, Tipo tipo, Float precio, Horario horario) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.horario = horario;
        this.frecuencia = Frecuencia.REPETITIVA;
    }

    public Actividad() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Calendario getCalendario() {
        return calendario;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public List<DiaYHorario> getDiaYHorario() {
        return diaYHorario;
    }

    public void setDiaYHorario(List<DiaYHorario> diaYHorario) {
        this.diaYHorario = diaYHorario;
    }

    public List<Clase> getClases() {
        return clases;
    }

    public void setClases(List<Clase> clases) {
        this.clases = clases;
    }

    public Frecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
