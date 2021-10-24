package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormatSymbols;
import java.time.*;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Repository("claseRepositorio")
public class ClaseRepositorioImpl implements ClaseRepositorio {

    private final SessionFactory sessionFactory;

    @Autowired
    public ClaseRepositorioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    // TODO tengo que mejorar un poco el codigo aca, es un asco
    public List<Clase> getClases(Optional<Mes> mes) {

        final Session session = sessionFactory.getCurrentSession();

        if (mes.isPresent()) {

            int numeroDeMes = mes.get().getNumeroDelMes();
            LocalDateTime primeroDiaDelMes = LocalDateTime.of(Year.now().getValue(), Month.of(numeroDeMes), 1, 0, 0, 0);
            Calendar cal = Calendar.getInstance();
            // Month.of(numeroDeMes).getValue()
            cal.set(Year.now().getValue(), numeroDeMes+1, 1);
            int ultimoDiaDelMes = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            LocalDateTime fechaCompletaUltimoDia = LocalDateTime.of(Year.now().getValue(), Month.of(numeroDeMes), ultimoDiaDelMes - 1, 23, 59, 59);
            return session.createCriteria(Clase.class)
                .add(Restrictions.between("diaClase", primeroDiaDelMes, fechaCompletaUltimoDia))
                .list();
        } else {
            Calendar cal = Calendar.getInstance();
            int numeroDeMes = LocalDate.now().getMonth().getValue();
            LocalDateTime primeroDiaDelMes = LocalDateTime.of(Year.now().getValue(), Month.of(numeroDeMes), 1, 0, 0, 0);
            int ultimoDiaDelMes = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            LocalDateTime fechaCompletaUltimoDia = LocalDateTime.of(Year.now().getValue(), Month.of(numeroDeMes), ultimoDiaDelMes, 23, 59, 59);
            return session.createCriteria(Clase.class)
                .add(Restrictions.between("diaClase", primeroDiaDelMes, fechaCompletaUltimoDia))
                .list();
        }
    }

    @Override
    public Clase getById(Long id) throws Exception {
        final Session session = sessionFactory.getCurrentSession();
        Clase clase = (Clase) session.createCriteria(Clase.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();

        if (clase == null) throw new Exception("No existe la clase");

        return clase;
    }
}
