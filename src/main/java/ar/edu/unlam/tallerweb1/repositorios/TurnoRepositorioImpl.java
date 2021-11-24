package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class TurnoRepositorioImpl implements TurnoRepositorio {

    private final SessionFactory sessionFactory;

    @Autowired
    public TurnoRepositorioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Turno> getTurnosByIdCliente(Cliente cliente) {
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select distinct t from Turno t where cliente =:cliente")
                .setParameter("cliente", cliente)
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

    @Override
    public Turno getTurnoById(Long idTurno) {
        final Session session = sessionFactory.getCurrentSession();
        return (Turno)session.createCriteria(Turno.class)
                .add(Restrictions.eq("id", idTurno))
                .uniqueResult();
    }

    @Override
    public void borrarTurno(Turno turno) {
        sessionFactory.getCurrentSession().delete(turno);
    }

    @Override
    public List<Turno> getTurnosParaHoy(Cliente cliente) {
        final Session session = sessionFactory.getCurrentSession();
        final LocalDate hoy = LocalDate.now();
        final LocalDateTime diaYHoraInicial = LocalDateTime.of(hoy, LocalTime.of(0,0));
        final LocalDateTime diaYHoraFinal = LocalDateTime.of(hoy, LocalTime.of(23, 59, 59));
        return session.createQuery("select distinct t from Turno t where cliente =:cliente and clase.diaClase between :diaYHoraInicial and :diaYHoraFinal")
                .setParameter("cliente", cliente)
                .setParameter("diaYHoraInicial", diaYHoraInicial)
                .setParameter("diaYHoraFinal", diaYHoraFinal)
                .list();
    }

    @Override
    public List<Clase> buscarClases(String claseBuscada) {
        final Session session = sessionFactory.getCurrentSession();

        return session.createQuery("from Clase where actividad.descripcion like :descripcion AND diaClase >=:fecha_actual")
                .setParameter("descripcion", "%" + claseBuscada + "%")
                .setParameter("fecha_actual", LocalDateTime.now())
                .list();
    }
}
