package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.PlanRepositorio;
import org.junit.Test;

import java.time.LocalDate;

public class PlanServiceTest {

    PlanRepositorio planRepositorio = mock(PlanRepositorio.class);
    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);

    private PlanService planService = new PlanServiceImpl(planRepositorio, clienteRepositorio);

    @Test
    public void elClienteContrataPlanBasico() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoConPlanNinguno();
        whenClienteContrataPlan(cliente, "Basico");
        thenElClienteTienePlan(cliente);
    }

    @Test(expected = PlanNoExisteException.class)
    public void elClienteContrataPlanConNombreInvalido() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoConPlanNinguno();
        whenClienteContrataPlan(cliente, "Invalido");
    }

    @Test
    public void clienteConPlanContratadoPuedeCancelarLaSuscripcion() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoConPlan();
        whenClienteCancelaElPlan(cliente, "Basico");
        thenElClienteNoTienePlan(cliente);
    }

    @Test(expected = PlanNoExisteException.class)
    public void clienteConPlanContratadoNoPuedeCancelarLaSuscripcion() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoConPlan();
        whenClienteCancelaElPlan(cliente, "Invalido");
    }

    private Cliente givenClienteLogueadoConPlan() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente();
        LocalDate hoy = LocalDate.now();
        cliente.agregarPago(new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.BASICO));
        return cliente;
    }

    private Cliente givenClienteLogueadoConPlanNinguno() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente();
        LocalDate hoy = LocalDate.now();
        cliente.agregarPago(new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.NINGUNO));
        return cliente;
    }

    private void whenClienteContrataPlan(Cliente cliente, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        planService.contratarPlan(cliente.getId(), LocalDate.now().getMonth(), LocalDate.now().getYear(), plan);
    }

    private void whenClienteCancelaElPlan(Cliente cliente, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        doNothing().when(clienteRepositorio).actualizarCliente(any());
        planService.cancelarPlan(cliente.getId(), plan);
    }

    private void thenElClienteTienePlan(Cliente cliente) {
        assertThat(cliente.getUltimoPlanContrado()).isEqualTo(Plan.BASICO);
    }

    private void thenElClienteNoTienePlan(Cliente cliente) {
        Pago pago = cliente.getContrataciones().get(0);
        assertThat(pago.esActivo()).isEqualTo(false);
    }
}
