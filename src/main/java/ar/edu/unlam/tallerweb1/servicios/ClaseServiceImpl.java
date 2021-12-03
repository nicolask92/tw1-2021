package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.exceptiones.NoTienePlanParaVerLasClasesException;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClaseServiceImpl implements ClaseService {

    private SessionFactory sessionFactory;
    private ClaseRepositorio claseRepositorio;
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    public ClaseServiceImpl(SessionFactory sessionFactory, ClaseRepositorio claseRepositorio, ClienteRepositorio clienteRepositorio) {
        this.sessionFactory = sessionFactory;
        this.claseRepositorio = claseRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    // TODO falta agregar restriccion de año
    @Override
    public List<Clase> getClases(Optional<Mes> mes, Long idUsuario) throws NoTienePlanParaVerLasClasesException {
        final Cliente cliente = clienteRepositorio.getById(idUsuario);

        boolean tienePlanParaElMesAConsultar = cliente.getContrataciones()
            .stream()
            .anyMatch( plan ->
                plan.getMes().ordinal() + 1 == (mes.isPresent() ? mes.get().getNumeroDelMes() : LocalDate.now().getMonth().getValue()) &&
            );

        if (!tienePlanParaElMesAConsultar)
            throw new NoTienePlanParaVerLasClasesException();

        return claseRepositorio.getClases(mes);
    }

    @Override
    public Clase buscarClasePorId(Long id) {
        return (Clase) sessionFactory.getCurrentSession().createCriteria(Clase.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
