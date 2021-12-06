package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.servicios.PlanService;
import ar.edu.unlam.tallerweb1.viewBuilders.PlanesViewModel;
import org.junit.Assert;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

public class PlanControllerTest {

    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);
    PlanService planService = mock(PlanService.class);
    PlanController planController = new PlanController(planService, clienteRepositorio);
    HttpSession mockSession = mock(HttpSession.class);

    @Test
    public void irALaPaginaDePlanesMeMuestraLosPlanesDisponibles() {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        ModelAndView mv = whenVeLaVistaDePlanes(cliente);
        thenSeMandaLosPlanesALaVista(mv);
    }

    @Test
    public void testQueSePuedaContratarPlan() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        ModelAndView mv = whenContratoPlan(mockSession, cliente, "Basico", false);
        thenElUsuarioTienePlanYSeLoRedirigeASacarTurnos(mv);
    }

    @Test
    public void noSePuedeContratarPlanAlMandarPlanInvalido() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        ModelAndView mv = whenContratoPlanNoExistente(mockSession, cliente, "Invalido", false);
        thenElUsuarioNoPuedoContratarPlan(mv, cliente);
    }

    @Test
    public void sePuedeCancelarUnPlan() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoYConPlan();
        ModelAndView mv = whenCanceloPlanActual(mockSession, cliente, "Basico");
        thenElUsuarioNoTienePlan(mv, cliente);
    }

    @Test
    public void noSePuedeCancelarUnPlan() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoYConPlan();
        ModelAndView mv = whenCanceloPlanActual(mockSession, cliente, "Invalido");
        thenElUsuarioNoTienePlan(mv, cliente);
    }

    @Test
    public void testSePuedeDesuscribirDeUnPlan() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoYConPlan();
        ModelAndView mv = whenCanceloSuscripcionPlanActual(cliente, "Invalido");
        thenSeDesuscribeAlPlan(mv, cliente);
    }

    @Test
    public void siYaTieneContratadoUnPlanNoDejaContratarElMismo() throws YaTienePagoRegistradoParaMismoMes, PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoYConPlan();
        ModelAndView mv = whenContratoPlanQueYaTengoContrado(cliente, "Estandar", false);
        thenElClienteEsRedirigidoYLeAvisaQueYaLoTieneContrado(mv);
    }

    @Test
    public void seMuestraLaVigenciaDelPlanQueTieneContrado() {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        Pago pago = givenUnPagoDeEsteMes(cliente);
        ModelAndView mv = whenVeoLosPlanesQueTengoContratado(cliente, pago);
        thenMeMuestraLaVigenciaDelPlanQueTengoContratadoSobreEseMes(mv);
    }

    private Pago givenUnPagoDeEsteMes(Cliente cliente) {
        Pago pago = new Pago(cliente, LocalDate.now().getMonth(), LocalDate.now().getYear(), Plan.ESTANDAR);
        pago.setDebitoAutomatico(true);
        return pago;
    }

    private Cliente givenClienteLogueadoYSinPlan() {
        return new Cliente();
    }

    private Cliente givenClienteLogueadoYConPlan() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente();
        LocalDate hoy = LocalDate.now();
        cliente.agregarPago(new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.ESTANDAR));
        return cliente;
    }

    private ModelAndView whenVeoLosPlanesQueTengoContratado(Cliente cliente, Pago pago) {
        when(mockSession.getAttribute("usuarioId")).thenReturn(cliente.getId());
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        when(planService.getUltimoPagoContratadoParaEsteMesYActivo(cliente.getId())).thenReturn(pago);
        return planController.getPlanes(mockSession);
    }

    private ModelAndView whenCanceloSuscripcionPlanActual(Cliente cliente, String plan) throws PlanNoExisteException {
        when(mockSession.getAttribute("usuarioId")).thenReturn(cliente.getId());
        doNothing().when(planService).cancelarSuscripcion(cliente.getId(), "Basico");
        return planController.cancelarSuscripcion(plan, mockSession);
    }

    private ModelAndView whenContratoPlanQueYaTengoContrado(Cliente cliente, String plan, Boolean conDebito) throws YaTienePagoRegistradoParaMismoMes, PlanNoExisteException {
        DatosPlan datosPlan = new DatosPlan();
        datosPlan.setNombre(plan);
        datosPlan.setConDebito(conDebito);
        when(mockSession.getAttribute("usuarioId")).thenReturn(cliente.getId());
        doThrow(YaTienePagoRegistradoParaMismoMes.class).when(planService).contratarPlan(cliente.getId(), LocalDate.now().getMonth(), LocalDate.now().getYear(), datosPlan);
        return planController.contratarPlan(datosPlan, mockSession);
    }

    private ModelAndView whenVeLaVistaDePlanes(Cliente cliente) {
        when(mockSession.getAttribute("usuarioId")).thenReturn(cliente.getId());
        when(planService.getUltimoPagoContratadoParaEsteMesYActivo(cliente.getId())).thenReturn(null);
        return planController.getPlanes(mockSession);
    }

    private ModelAndView whenContratoPlan(HttpSession session, Cliente cliente, String plan, Boolean conDebito) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        DatosPlan datosPlan = new DatosPlan();
        datosPlan.setNombre(plan);
        datosPlan.setConDebito(conDebito);
        when(session.getAttribute("usuarioId")).thenReturn(cliente.getId());
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        when(planService.contratarPlan(cliente.getId(), LocalDate.now().getMonth(), LocalDate.now().getYear(), datosPlan)).thenReturn(Plan.BASICO);

        return planController.contratarPlan(datosPlan, session);
    }

    private ModelAndView whenContratoPlanNoExistente(HttpSession session, Cliente cliente, String plan, Boolean conDebito) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        DatosPlan datosPlan = new DatosPlan();
        datosPlan.setNombre(plan);
        datosPlan.setConDebito(conDebito);
        when(session.getAttribute("usuarioId")).thenReturn(cliente.getId());
        doThrow(PlanNoExisteException.class).when(planService).contratarPlan(cliente.getId(), LocalDate.now().getMonth(), LocalDate.now().getYear(), datosPlan);
        return planController.contratarPlan(datosPlan, session);
    }

    private ModelAndView whenCanceloPlanActual(HttpSession session, Cliente cliente, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Pago pago = cliente.getContrataciones().get(0);
        when(session.getAttribute("usuarioId")).thenReturn(cliente.getId());
        when(planService.cancelarPlan(cliente.getId(), "Basico")).thenReturn(pago);
        return planController.cancelarPlan(plan, session);
    }

    private void thenSeMandaLosPlanesALaVista(ModelAndView mv) {
        List<PlanesViewModel> listaDePlanes = (List<PlanesViewModel>) mv.getModel().get("planes");
        PlanesViewModel primerPlan = listaDePlanes.get(0);
        PlanesViewModel segundoPlan = listaDePlanes.get(1);
        PlanesViewModel tercerPlan = listaDePlanes.get(2);

        Assert.assertEquals(mv.getViewName(), "/planes");
        Assert.assertEquals(listaDePlanes.size(), 3);
        Assert.assertEquals(primerPlan.getPlan(), Plan.BASICO);
        Assert.assertEquals(segundoPlan.getPlan(), Plan.ESTANDAR);
        Assert.assertEquals(tercerPlan.getPlan(), Plan.PREMIUM);

        Assert.assertEquals(primerPlan.getActivo(), false);
        Assert.assertEquals(segundoPlan.getActivo(), false);
        Assert.assertEquals(tercerPlan.getActivo(), false);

        Assert.assertFalse(primerPlan.getConDebitoActivado().isPresent());
        Assert.assertFalse(segundoPlan.getConDebitoActivado().isPresent());
        Assert.assertFalse(tercerPlan.getConDebitoActivado().isPresent());
    }

    private void thenElUsuarioNoPuedoContratarPlan(ModelAndView mv, Cliente cliente) {
        assertThat(mv.getModel().get("noExistePlan")).isEqualTo("El plan que quiere contratar no existe");
        assertThat(mv.getViewName()).isEqualTo("redirect:/planes");
    }

    private void thenElUsuarioTienePlanYSeLoRedirigeASacarTurnos(ModelAndView mv) {
        assertThat(mv.getModel().get("contracionExitosa")).isEqualTo("El Plan se contrato correctamente");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-clases");
    }

    private void thenElUsuarioNoTienePlan(ModelAndView mv, Cliente cliente) {
        assertThat(mv.getModel().get("msg")).isEqualTo("El Plan se cancelo correctamente");
        assertThat(mv.getViewName()).isEqualTo("redirect:/planes");
    }

    private void thenElClienteEsRedirigidoYLeAvisaQueYaLoTieneContrado(ModelAndView mv) {
        assertThat(mv.getModel().get("yaTuvoPlan")).isEqualTo("Ya tiene registrado este plan para este mes.");
        assertThat(mv.getViewName()).isEqualTo("/planes");
    }

    private void thenMeMuestraLaVigenciaDelPlanQueTengoContratadoSobreEseMes(ModelAndView mv) {
        List<PlanesViewModel> listaDePlanes = (List<PlanesViewModel>) mv.getModel().get("planes");
        PlanesViewModel primerPlan = listaDePlanes.get(0);
        PlanesViewModel segundoPlan = listaDePlanes.get(1);
        PlanesViewModel tercerPlan = listaDePlanes.get(2);

        Assert.assertEquals(mv.getViewName(), "/planes");
        Assert.assertEquals(listaDePlanes.size(), 3);
        Assert.assertEquals(primerPlan.getPlan(), Plan.BASICO);
        Assert.assertEquals(segundoPlan.getPlan(), Plan.ESTANDAR);
        Assert.assertEquals(tercerPlan.getPlan(), Plan.PREMIUM);

        Assert.assertEquals(primerPlan.getActivo(), false);
        Assert.assertEquals(segundoPlan.getActivo(), true);
        Assert.assertEquals(tercerPlan.getActivo(), false);

        Assert.assertFalse(primerPlan.getConDebitoActivado().isPresent());
        Assert.assertEquals(segundoPlan.getConDebitoActivado().get(), true);
        Assert.assertFalse(tercerPlan.getConDebitoActivado().isPresent());
    }

    private void thenSeDesuscribeAlPlan(ModelAndView mv, Cliente cliente) {
        assertThat(mv.getModel().get("msg")).isEqualTo("Te desuscribiste del plan correctamente");
        assertThat(mv.getViewName()).isEqualTo("redirect:/planes");
    }
}