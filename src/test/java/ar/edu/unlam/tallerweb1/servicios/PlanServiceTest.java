package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.controladores.DatosPlan;
import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.PagoRepositorio;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class PlanServiceTest {

    PagoRepositorio pagoRepositorio = mock(PagoRepositorio.class);
    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);

    private PlanService planService = new PlanServiceImpl(pagoRepositorio, clienteRepositorio);

    @Test
    public void elClienteContrataPlanBasico() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoConPlanNinguno();
        whenClienteContrataPlan(cliente, "Basico", Plan.NINGUNO, false);
        thenElClienteTienePlan(cliente);
    }

    @Test(expected = PlanNoExisteException.class)
    public void elClienteContrataPlanConNombreInvalido() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoConPlanNinguno();
        whenClienteContrataPlan(cliente, "Invalido", Plan.NINGUNO, false);
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

    @Test(expected = YaTienePagoRegistradoParaMismoMes.class)
    public void siYaTieneContratadoUnPlanNoDejaContratarElMismo() throws YaTienePagoRegistradoParaMismoMes, PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoConPlan();
        whenClienteContrataPlan(cliente, "Basico", Plan.BASICO, false);
    }

    @Test
    public void contratarPlanDejaElPagoAnteriorCancelado() throws YaTienePagoRegistradoParaMismoMes, PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoConPlan();
        whenClienteContrataPlan(cliente, "Estandar", Plan.ESTANDAR, false);
        thenElClienteTieneDosPagosSiendoElContradoActivoYElAnteriorCancelado(cliente);
    }

    @Test
    public void getUltimoPagoLoHaceCorrectamente() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoConPlan();
        Pago pago = whenBuscoUltimoPago(cliente);
        thenDevuelveUltimoPago(pago);
    }

    @Test
    public void contratarPlanConDebitoAutomaticoDejaElPagoConDebitoAutomatico() throws YaTienePagoRegistradoParaMismoMes, PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoConPlanNinguno();
        whenClienteContrataPlan(cliente, "Estandar", Plan.ESTANDAR, false);

    }

    private Pago whenBuscoUltimoPago(Cliente cliente) {
        when(pagoRepositorio.getUltimoPagoContratadoParaEsteMesYActivo(cliente)).thenReturn(cliente.getUltimoPagoRealizado());
        return planService.getUltimoPagoContratadoParaEsteMesYActivo(cliente.getId());
    }

    private void thenElClienteTieneDosPagosSiendoElContradoActivoYElAnteriorCancelado(Cliente cliente) {
        List<Pago> listaDePagosDelCliente = cliente.getContrataciones();
        Pago pagoBasico = listaDePagosDelCliente.stream().filter( pago ->
                pago.getPlan() == Plan.BASICO
        ).findFirst().get();

        Pago pagoEstandar = listaDePagosDelCliente.stream().filter( pago ->
                pago.getPlan() == Plan.ESTANDAR
        ).findFirst().get();

        assertThat(listaDePagosDelCliente.size()).isEqualTo(2);

        assertThat(false).isEqualTo(pagoBasico.esActivo());
        assertThat(true).isEqualTo(pagoEstandar.esActivo());
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

    private void whenClienteContrataPlan(Cliente cliente, String plan, Plan planADevolver, Boolean conDebito) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        LocalDate hoy = LocalDate.now();
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        cliente.getUltimoPagoRealizado().cancelarPlan();
        Pago pagoNuevo = new Pago(cliente, hoy.getMonth(), hoy.getYear(), planADevolver);
        when(clienteRepositorio.getPagoActivo(cliente)).thenReturn(pagoNuevo);
        DatosPlan datosPlan = new DatosPlan();
        datosPlan.setNombre(plan);
        datosPlan.setConDebito(conDebito);
        planService.contratarPlan(cliente.getId(), LocalDate.now().getMonth(), LocalDate.now().getYear(), datosPlan);
    }

    private void whenClienteCancelaElPlan(Cliente cliente, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        LocalDate hoy = LocalDate.now();
        cliente.getUltimoPagoRealizado().cancelarPlan();
        when(clienteRepositorio.getPagoActivo(cliente)).thenReturn(new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.BASICO));
        when(clienteRepositorio.getPlanNinguno(cliente)).thenReturn(new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.NINGUNO));
        planService.cancelarPlan(cliente.getId(), plan);
    }

    private void thenElClienteTienePlan(Cliente cliente) {
        Pago ultimoPago = cliente.getContrataciones().stream().filter( pago ->
                pago.getPlan() == Plan.BASICO
        ).findFirst().get();

        assertThat(ultimoPago.getPlan()).isEqualTo(Plan.BASICO);
    }

    private void thenElClienteNoTienePlan(Cliente cliente) {
        Pago pago = cliente.getContrataciones().get(0);
        assertThat(pago.esActivo()).isEqualTo(false);
    }

    private void thenDevuelveUltimoPago(Pago pago) {
        assertThat(pago).isNotNull();
        assertThat(pago.esActivo()).isTrue();
        assertThat(pago.getPlan()).isEqualTo(Plan.BASICO);
    }
}
