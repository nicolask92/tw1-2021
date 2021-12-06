package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("clienteRepositorio")
@Transactional
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

    @Override
    public Pago getPagoActivo(Cliente cliente) {
        final Session session = sessionFactory.getCurrentSession();
        return (Pago) session.createQuery("select p from Pago p where p.activo= true and p.cliente=:cliente")
                .setParameter("cliente", cliente)
                .uniqueResult();
    }

    @Override
    public List<Pago> getPagos(Cliente cliente) {
        final Session session = sessionFactory.getCurrentSession();
        return  session.createQuery("select p from Pago p where p.cliente =:cliente AND p.plan != 'NINGUNO' order by p.id desc")
                .setParameter("cliente", cliente)
                .list();
    }

    @Override
    public Pago getPlanNinguno(Cliente cliente) {
        final Session session = sessionFactory.getCurrentSession();
        return (Pago) session.createQuery("select p from Pago p where p.activo= false and p.cliente=:cliente and p.plan = 'NINGUNO'")
                .setParameter("cliente", cliente)
                .uniqueResult();
    }
}
