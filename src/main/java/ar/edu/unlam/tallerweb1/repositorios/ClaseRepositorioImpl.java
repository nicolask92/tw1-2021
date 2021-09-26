package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClaseRepositorioImpl implements ClaseRepositorio {

    private final SessionFactory sessionFactory;

    @Autowired
    public ClaseRepositorioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<Clase> getClases(Optional<Mes> mes) {
        final Session session = sessionFactory.getCurrentSession();

        if (mes.isPresent()) {
            return null;//(List<Clase>) session.createCriteria(Clase.class)
                    //.add(equals());
        } else {
            return null; // (List<Clase>) session.createCriteria(Clase.class);
        }
    }
}
