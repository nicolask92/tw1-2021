package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TurnoRepositorioImpl implements TurnoRepositorio {

    private final SessionFactory sessionFactory;

    @Autowired
    public TurnoRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Clase> getTurnosDeEsteMes() {
        return null;
    }

    @Override
    public void guardarTurno(Long id) {

        Turno turno = new Turno();
        sessionFactory.getCurrentSession().save(turno);

    }
}
