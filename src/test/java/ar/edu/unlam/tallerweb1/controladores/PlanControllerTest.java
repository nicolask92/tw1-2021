package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.servicios.PlanService;
import org.junit.Assert;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.mockito.Mockito.*;

public class PlanControllerTest {

    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);
    PlanService planService = mock(PlanService.class);
    PlanController planController = new PlanController(planService, clienteRepositorio);
    HttpSession mockSession = mock(HttpSession.class);

    @Test
    public void irALaPaginaDePlanesMeMuestraLosPlanesDisponibles() {
        givenClienteLogueadoYSinPlan();
        ModelAndView mv = whenVeLaVistaDePlanes();
        thenSeMandaLosPlanesALaVista(mv);
    }

    @Test
    public void queSePuedaContratarPlan() throws PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        ModelAndView mv = whenContratoPlan(mockSession, cliente, "Basico");
        thenElUsuarioTienePlanYSeLoRedirigeASacarTurnos(mv);
    }

    @Test
    public void noSePuedeContratarPlan() throws PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        ModelAndView mv = whenContratoPlanNoExistente(mockSession, cliente, "Invalido");
        thenElUsuarioNoPuedoContratarPlan(mv, cliente);
    }

    @Test
    public void sePuedeCancelarLaSuscripcionDeUnPlan() throws PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoYConPlan();
        ModelAndView mv = whenCanceloSuscripcionDelPlanActual(mockSession, cliente, "Basico");
        thenElUsuarioNoTienePlan(mv, cliente);
    }

    @Test
    public void noSePuedeCancelarLaSuscripcionDeUnPlan() throws PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoYConPlan();
        ModelAndView mv = whenCanceloSuscripcionDelPlanActual(mockSession, cliente, "Invalido");
        thenElUsuarioNoTienePlan(mv, cliente);
    }

    private Cliente givenClienteLogueadoYConPlan() {
        Cliente cliente = new Cliente();
        cliente.setPlan(Plan.ESTANDAR);
        return cliente;
    }

    private Cliente givenClienteLogueadoYSinPlan() {
        Cliente cliente = new Cliente();
        cliente.setPlan(Plan.NINGUNO);
        return cliente;
    }

    private ModelAndView whenVeLaVistaDePlanes() {
        return planController.getPlanes();
    }

    private ModelAndView whenContratoPlan(HttpSession session, Cliente cliente, String plan) throws PlanNoExisteException {
        when(session.getAttribute("usuarioId")).thenReturn(cliente.getId());
        when(planService.contratarPlan(cliente.getId(), plan )).thenReturn(Plan.BASICO);
        return planController.contratarPlan(plan, session);
    }
    private ModelAndView whenContratoPlanNoExistente(HttpSession session, Cliente cliente, String plan) throws PlanNoExisteException {
        when(session.getAttribute("usuarioId")).thenReturn(cliente.getId());
//        when(planService.contratarPlan(cliente.getId(), plan )).thenReturn(Plan.BASICO);
        doThrow(PlanNoExisteException.class).when(planService).contratarPlan(cliente.getId(), plan);
        return planController.contratarPlan(plan, session);
    }

    private ModelAndView whenCanceloSuscripcionDelPlanActual(HttpSession session, Cliente cliente, String plan) throws PlanNoExisteException {
        when(session.getAttribute("usuarioId")).thenReturn(cliente.getId());
        when(planService.cancelarPlan(cliente.getId(), "Basico")).thenReturn(cliente.setPlan(Plan.NINGUNO));
        return planController.cancelarPlan(plan, session);
    }

    private void thenSeMandaLosPlanesALaVista(ModelAndView mv) {
        Assert.assertEquals(mv.getViewName(), "/planes");
        Assert.assertEquals(mv.getModel().get("planes"), List.of(Plan.BASICO, Plan.ESTANDAR, Plan.PREMIUM));
    }

    private void thenElUsuarioNoPuedoContratarPlan(ModelAndView mv, Cliente cliente) {
        assertThat(cliente.getPlan()).isEqualTo(Plan.NINGUNO);
        assertThat(mv.getModel().get("noExistePlan")).isEqualTo("El plan que quiere contratar no existe");
        assertThat(mv.getViewName()).isEqualTo("/planes");
    }

    private void thenElUsuarioTienePlanYSeLoRedirigeASacarTurnos(ModelAndView mv) {
        assertThat(mv.getModel().get("contracionExitosa")).isEqualTo("El Plan se contrato correctamente");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-clases");
    }

    private void thenElUsuarioNoTienePlan(ModelAndView mv, Cliente cliente) {
        assertThat(mv.getModel().get("msg")).isEqualTo("El Plan se cancelo correctamente");
        assertThat(cliente.getPlan()).isEqualTo(Plan.NINGUNO);
    }
}