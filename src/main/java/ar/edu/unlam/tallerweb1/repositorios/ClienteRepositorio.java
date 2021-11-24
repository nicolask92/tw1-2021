package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;

import java.util.List;


public interface ClienteRepositorio {

    Cliente getById(Long idUsuario);
    void actualizarCliente(Cliente cliente);
    Pago getPlanActivo(Cliente cliente);

    List<Pago> getPagos(Cliente cliente);
}
