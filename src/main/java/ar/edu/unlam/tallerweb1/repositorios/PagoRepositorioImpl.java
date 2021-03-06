package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository("pagoRepositorio")
@Transactional
public class PagoRepositorioImpl implements PagoRepositorio {

    private final SessionFactory sessionFactory;

    @Autowired
    public PagoRepositorioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardar(Pago pago) {
        sessionFactory.getCurrentSession().save(pago);
    }

    @Override
    public void actualizar(Pago pago) {
        sessionFactory.getCurrentSession().update(pago);
    }

    @Override
    public Pago getUltimoPagoContratadoParaEsteMesYActivo(Cliente cliente) {
        LocalDate hoy = LocalDate.now();
        return (Pago) sessionFactory.getCurrentSession()
            .createQuery("select distinct p from Pago p where mes =: mes and anio =: anio and activo =: activo and cliente =: cliente")
            .setParameter("mes", hoy.getMonth())
            .setParameter("anio", hoy.getYear())
            .setParameter("activo", true)
            .setParameter("cliente", cliente)
            .uniqueResult();
    }
}
