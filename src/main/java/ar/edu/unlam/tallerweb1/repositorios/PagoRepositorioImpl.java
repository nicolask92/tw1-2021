package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("pagoRepositorio")
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
}
