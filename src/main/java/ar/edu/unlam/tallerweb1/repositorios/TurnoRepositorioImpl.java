package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.common.Tipo;
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
        return session.createCriteria(Turno.class)
                .add(Restrictions.eq("cliente", cliente))
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
        return session.createCriteria(Turno.class)
                .createAlias("clase", "c")
                .add(Restrictions.eq("cliente", cliente))
                .add(Restrictions.between("c.diaClase", diaYHoraInicial, diaYHoraFinal))
                .list();
    }

    @Override
    public List<Clase> buscarClases(String claseBuscada) {
        final Session session = sessionFactory.getCurrentSession();
        Tipo clasetipo = tipoFromString(claseBuscada);

        return session.createQuery("from Clase where actividad.tipo=:tipo_actividad AND diaClase >=:fecha_actual")
                .setParameter("tipo_actividad", clasetipo)
                .setParameter("fecha_actual", LocalDateTime.now())
                .list();
    }

    private Tipo tipoFromString (String clase){
        return Tipo.valueOf(clase);
    }
}
