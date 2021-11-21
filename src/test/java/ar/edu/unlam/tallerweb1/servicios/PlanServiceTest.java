package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.PlanRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.TurnoRepositorio;
import org.junit.Test;

public class PlanServiceTest {

    PlanRepositorio planRepositorio = mock(PlanRepositorio.class);
    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);

    private PlanService planService = new PlanServiceImpl(planRepositorio, clienteRepositorio);

    @Test
    public void elClienteContrataPlanBasico() throws PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        whenClienteContrataPlan(cliente, "Basico");
        thenElClienteTienePlan(cliente);
    }

    @Test(expected = PlanNoExisteException.class)
    public void elClienteContrataPlanConNombreInvalido() throws PlanNoExisteException {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        whenClienteContrataPlan(cliente, "Invalido");
    }

    private Cliente givenClienteLogueadoYSinPlan() {
        Cliente cliente = new Cliente();
        cliente.setPlan(Plan.NINGUNO);
        return cliente;
    }

    private void whenClienteContrataPlan(Cliente cliente, String plan) throws PlanNoExisteException {
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        planService.contratarPlan(cliente.getId(), plan );
    }

    private void thenElClienteTienePlan(Cliente cliente) {
        assertThat(cliente.getPlan()).isEqualTo(Plan.BASICO);
    }
}
