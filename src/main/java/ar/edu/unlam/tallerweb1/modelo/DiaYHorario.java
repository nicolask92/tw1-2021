package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Dia;

import javax.persistence.*;

@Entity
public class DiaYHorario {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Horario horario;

    @ManyToOne
    Actividad actividad;

    @Enumerated(EnumType.STRING)
    private Dia dia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiaYHorario(Horario horario, Dia dia) {
        this.horario = horario;
        this.dia = dia;
    }

    public DiaYHorario() {
    }
}
