package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ClienteRepositorioTest extends SpringTest {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Test
    @Rollback @Transactional
    public void seObtienePagoActivoDelCliente() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteConPlanActivo();
        Pago pagoActivo = whenBuscoElPagoActivo(cliente);
        thenSeEncuentraElPagoActivoDelCliente(pagoActivo, cliente);
    }

    @Test
    @Rollback @Transactional
    public void seObtieneClientePorId() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteConPlanActivo();
        Cliente clienteBuscado = whenBuscoElCliente(cliente);
        thenEncuntroElClienteBuscado(cliente, clienteBuscado);
    }

    @Test
    @Rollback @Transactional
    public void seObtieneElPlanNingunoDelCliente() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteConPlanActivo();
        Pago pago = whenBuscoElPlanNingunoDelCliente(cliente);
        thenEncuentroElPlanNinguno(pago, cliente);
    }

    private Pago givenPago(Cliente cliente){
        LocalDate hoy = LocalDate.now();
        return new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.BASICO);
    }

    private Cliente givenClienteConPlanActivo() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente("Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com", Collections.EMPTY_LIST);
        cliente.setId(1L);
        cliente.getUltimoPagoRealizado().cancelarPlan();
        Pago pago = givenPago(cliente);
        cliente.agregarPago(pago);
        session().save(cliente);
        return cliente;
    }

    private Pago whenBuscoElPagoActivo(Cliente cliente) {
        return clienteRepositorio.getPagoActivo(cliente);
    }

    private Cliente whenBuscoElCliente(Cliente cliente) {
        return clienteRepositorio.getById(cliente.getId());
    }

    private Pago whenBuscoElPlanNingunoDelCliente(Cliente cliente) {
        return clienteRepositorio.getPlanNinguno(cliente);
    }

    private void thenSeEncuentraElPagoActivoDelCliente(Pago pagoActivo, Cliente cliente) {
        assertThat(pagoActivo.getCliente().getId()).isEqualTo(cliente.getId());
    }

    private void thenEncuntroElClienteBuscado(Cliente cliente, Cliente clienteBuscado) {
        assertThat(cliente).isEqualTo(clienteBuscado);
    }

    private void thenEncuentroElPlanNinguno(Pago pago, Cliente cliente) {
        assertThat(cliente.getId()).isEqualTo(pago.getCliente().getId());
    }
}
