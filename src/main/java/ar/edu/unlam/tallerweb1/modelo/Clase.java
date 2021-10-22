package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "clientes_clases",
            joinColumns = @JoinColumn(name = "clase_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id")
    )
    List<Cliente> clientes;

    @ManyToMany
    @JoinTable(
            name = "clases_profesores",
            joinColumns = @JoinColumn(name = "clase_id"),
            inverseJoinColumns = @JoinColumn(name = "entrenador_id")
    )
    List<Entrenador> profesores;

    @ManyToOne
    Actividad actividad;

    @Enumerated(EnumType.STRING)
    Modalidad modalidad;

    @OneToOne
    Horario horario;

    Integer cupoMaximo = 20;

    LocalDateTime diaClase;

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

    public List<Cliente> agregarCliente(Cliente cliente) throws Exception {
        if (modalidad == Modalidad.PRESENCIAL && clientes.size() == cupoMaximo) {
            throw new Exception("Ya se excedio la cantidad maxima de personas que pueden asistir a este lugar");
        }
        this.clientes.add(cliente);
        return clientes;
    }

    public String getHorarioString(int dia) {
        String comienzo = this.horario.getHoraInicio().format(DateTimeFormatter.ISO_LOCAL_TIME);
        String fin = this.horario.getHoraFin().format(DateTimeFormatter.ISO_LOCAL_TIME);

        return "(" + comienzo + " - " + fin + ")";
    }

    public String getActividadString() {
        return this.actividad.descripcion;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Entrenador> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Entrenador> profesores) {
        this.profesores = profesores;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
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

    public LocalDateTime getDiaClase() {
        return diaClase;
    }
}
