package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.servicios.PlanService;
import org.junit.Assert;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    public void testQueSePuedaContratarPlan(){
        Cliente cliente = givenClienteLogueadoYSinPlan();
        ModelAndView mv = whenContratoPlan(mockSession, cliente);
        thenElUsuarioTienePlan(mv);
    }

    private void thenElUsuarioTienePlan(ModelAndView mv) {
        assertThat(mv.getViewName()).isEqualTo("forward:/mostrar-clase");
    }

    private ModelAndView whenContratoPlan(HttpSession session, Cliente cliente) {
        when(session.getAttribute("usuarioId")).thenReturn(cliente.getId());
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        doNothing().when(planService).cambiarPlan(cliente);
        return planController.contratarPlan(session);
    }

    private void thenSeMandaLosPlanesALaVista(ModelAndView mv) {
        Assert.assertEquals(mv.getViewName(), "/planes");
        Assert.assertEquals(mv.getModel().get("planes"), List.of(Plan.BASICO, Plan.ESTANDAR, Plan.PREMIUM));
    }

    private ModelAndView whenVeLaVistaDePlanes() {
        return planController.getPlanes();
    }

    private Cliente givenClienteLogueadoYSinPlan() {
        Cliente cliente = new Cliente();
        cliente.setPlan(Plan.NINGUNO);
        return cliente;
    }
}