package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;

import ar.edu.unlam.tallerweb1.modelo.Cliente;

import ar.edu.unlam.tallerweb1.modelo.Turno;

import java.util.List;

    @Repository
    public class TurnoRepositorioImpl implements TurnoRepositorio {
    private final SessionFactory sessionFactory;

    @Autowired
    public TurnoRepositorioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Turno> getTurnosPorId(Cliente usuario) {

        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Turno.class)
                .add(Restrictions.eq("cliente", usuario))
                .list();
    }

}
