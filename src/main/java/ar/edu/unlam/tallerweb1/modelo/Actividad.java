package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Tipo;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String descripcion;

    @OneToOne
    Calendario calendario;

    @Enumerated(EnumType.STRING)
    Tipo tipo;

    Float precio;

    @OneToMany
    List<DiaYHorario> diaYHorario;

    @OneToMany
    List<Clase> clases;

    @Enumerated(EnumType.STRING)
    Frecuencia frecuencia;

    @OneToOne
    Periodo periodo;

    public Actividad(String descripcion, Tipo tipo, Float precio, Frecuencia frecuencia, Periodo periodo) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.frecuencia = Frecuencia.CON_INICIO_Y_FIN;
        this.periodo = periodo;
    }

    public Actividad(String descripcion, Tipo tipo, Float precio) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
