package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TurnoRepositorioImpl implements TurnoRepositorio {

    private final SessionFactory sessionFactory;

    @Autowired
    public TurnoRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Turno> getTurnosPorId(Usuario user) {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Turno.class)
                .add(Restrictions.eq("usuario", user))
                .list();
    }

    @Override
    public List<Clase> getTurnosDeEsteMes() {
        return null;
    }

    @Override
    public void guardarTurno(Cliente cliente, Clase clase) {
        Turno turno = new Turno( cliente,clase, LocalDate.now());
        sessionFactory.getCurrentSession().save(turno);
    }

}
