package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.servicios.PlanService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.mockito.Mockito.mock;

public class PlanControllerTest {

    PlanService planService = mock(PlanService.class);
    PlanController planController = new PlanController(planService);

    @Test
    public void irALaPaginaDePlanesMeMuestraLosPlanesDisponibles() {
        givenClienteLogueadoYSinPlan();
        ModelAndView mv = whenVeLaVistaDePlanes();
        thenSeMandaLosPlanesALaVista(mv);
    }

    private void thenSeMandaLosPlanesALaVista(ModelAndView mv) {
        Assert.assertEquals(mv.getViewName(), "/planes");
        Assert.assertEquals(mv.getModel().get("planes"), List.of(Plan.BASICO, Plan.ESTANDAR, Plan.PREMIUM));
    }

    private ModelAndView whenVeLaVistaDePlanes() {
        return planController.getPlanes();
    }

    private void givenClienteLogueadoYSinPlan() {
    }
}