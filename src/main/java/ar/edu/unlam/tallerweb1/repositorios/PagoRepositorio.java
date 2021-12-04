package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;

public interface PagoRepositorio {
    void guardar(Pago pago);
    void actualizar(Pago pago);
    Pago getUltimoPagoContratadoParaEsteMesYActivo(Cliente cliente);
}
