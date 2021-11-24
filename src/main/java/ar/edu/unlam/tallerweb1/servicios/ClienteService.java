package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import org.springframework.stereotype.Service;

public interface ClienteService {
    Cliente getCliente(Long idUsuario);
}
