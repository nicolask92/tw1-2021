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
    public void testQueSePuedaContratarPlan() throws PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        ModelAndView mv = whenContratoPlan(mockSession, cliente);
        thenElUsuarioTienePlanYSeLoRedirigeASacarTurnos(mv);
    }

    private Cliente givenClienteLogueadoYSinPlan() {
        Cliente cliente = new Cliente();
        cliente.setPlan(Plan.NINGUNO);
        return cliente;
    }

    private ModelAndView whenVeLaVistaDePlanes() {
        return planController.getPlanes();
    }

    private ModelAndView whenContratoPlan(HttpSession session, Cliente cliente) throws PlanNoExisteException {
        when(session.getAttribute("usuarioId")).thenReturn(cliente.getId());
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        when(planService.contratarPlan(cliente.getId(), "Basico" )).thenReturn(Plan.BASICO);
        return planController.contratarPlan("Basico",session);
    }

    private void thenSeMandaLosPlanesALaVista(ModelAndView mv) {
        Assert.assertEquals(mv.getViewName(), "/planes");
        Assert.assertEquals(mv.getModel().get("planes"), List.of(Plan.BASICO, Plan.ESTANDAR, Plan.PREMIUM));
    }

    private void thenElUsuarioTienePlanYSeLoRedirigeASacarTurnos(ModelAndView mv) {
        assertThat(mv.getModel().get("contracionExitosa")).isEqualTo("El Plan se contrato correctamente");
        assertThat(mv.getViewName()).isEqualTo("forward:/mostrar-clase");
    }
}