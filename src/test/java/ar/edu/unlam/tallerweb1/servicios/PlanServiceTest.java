package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import org.junit.Test;

public class PlanServiceTest {
    private PlanService planService = new PlanServiceImpl();

    @Test
    public void testElClienteContrataPlan(){
        Cliente cliente = givenClienteLogueadoYSinPlan();
        whenClienteContrataPlan(cliente);
        thenElClienteTienePlan(cliente);
    }

    private void thenElClienteTienePlan(Cliente cliente) {

    }

    private void whenClienteContrataPlan(Cliente cliente) {
        planService.cambiarPlan(cliente);
    }

    private Cliente givenClienteLogueadoYSinPlan() {
        Cliente cliente = new Cliente();
        cliente.setPlan(Plan.NINGUNO);
        return cliente;
    }
}
