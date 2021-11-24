package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import org.springframework.stereotype.Service;

public interface ClienteService {
    Cliente getCliente(Long idUsuario);

    Pago getPlanActivo(Cliente cliente);
}
