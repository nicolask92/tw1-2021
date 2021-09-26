package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClaseServiceImpl implements ClaseService {

    private SessionFactory sessionFactory;
    private ClaseRepositorio claseRepositorio;

    @Autowired
    public ClaseServiceImpl(SessionFactory sessionFactory, ClaseRepositorio claseRepositorio) {
        this.sessionFactory = sessionFactory;
        this.claseRepositorio = claseRepositorio;
    }


    public List<Clase> getClases(Optional<Mes> mes) {
        return claseRepositorio.getClases(mes);
    }

    @Override
    public Clase buscarClasePorId(Long id) {

        return (Clase) sessionFactory.getCurrentSession().createCriteria(Clase.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
