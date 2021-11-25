package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);
    private ClienteService clienteService = new ClienteServiceImpl(clienteRepositorio);

    @Test
    public void seObtieneclientePorIdUsurio() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteConPlanActivo();
        whenBuscoElCliente(cliente);
        thenEncuntraCliente(cliente);
    }

    @Test
    public void seObtienePlanActivoDelCliente() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteConPlanActivo();
        whenBuscoPlanActivoDelCliente(cliente);
        thenSeObtieneElPlanActivoDelCliente(cliente);
    }

    private Pago givenPago(Cliente cliente){
        LocalDate hoy = LocalDate.now();
        return new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.BASICO);
    }

    private Cliente givenClienteConPlanActivo() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente("Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com", Collections.EMPTY_LIST);
        cliente.setId(1L);
        Pago pago = givenPago(cliente);
        cliente.agregarPago(pago);
        return cliente;
    }

    private void whenBuscoElCliente(Cliente cliente) {
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        clienteService.getCliente(cliente.getId());
    }

    private void whenBuscoPlanActivoDelCliente(Cliente cliente) {
        when(clienteRepositorio.getPagoActivo(cliente)).thenReturn(cliente.getUltimoPagoRealizado());
        clienteService.getPagoActivo(cliente);
    }

    private void thenEncuntraCliente(Cliente cliente) {
        verify(clienteRepositorio, times(1)).getById(cliente.getId());
    }

    private void thenSeObtieneElPlanActivoDelCliente(Cliente cliente) {
        verify(clienteRepositorio, times(1)).getPagoActivo(cliente);
    }

}
