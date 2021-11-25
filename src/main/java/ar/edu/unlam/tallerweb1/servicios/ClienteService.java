package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClienteService {
    Cliente getCliente(Long idUsuario);

    Pago getPagoActivo(Cliente cliente);

    List<Pago> getPagos(Long idUsuario);
}
