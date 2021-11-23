package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("clienteRepositorio")
public class ClienteRepositorioImpl implements ClienteRepositorio {

    private final SessionFactory sessionFactory;

    @Autowired
    public ClienteRepositorioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Cliente getById(Long idUsuario) {
        final Session session = sessionFactory.getCurrentSession();
        return (Cliente) session.createCriteria(Cliente.class)
                .add(Restrictions.eq("id",idUsuario))
                .uniqueResult();
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(cliente);
    }
}
