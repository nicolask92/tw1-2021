package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepositorio clienteRepositorio;

    @Autowired
    public ClienteServiceImpl(ClienteRepositorio clienteRepositorio){
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public Cliente getCliente(Long idUsuario) {
        return clienteRepositorio.getById(idUsuario);
    }

    @Override
    public Pago getPlanActivo(Cliente cliente) {
        return clienteRepositorio.getPlanActivo(cliente);
    }

    @Override
    public List<Pago> getPagos(Long idUsuario) {

        Cliente cliente = clienteRepositorio.getById(idUsuario);
        return clienteRepositorio.getPagos(cliente);
    }
}
