package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.PlanRepositorio;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;

public class PlanServiceTest {

    PlanRepositorio planRepositorio = mock(PlanRepositorio.class);
    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);

    private PlanService planService = new PlanServiceImpl(planRepositorio, clienteRepositorio);

    @Test
    public void elClienteContrataPlanBasico() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        whenClienteContrataPlan(cliente, "Basico");
        thenElClienteTienePlan(cliente);
    }

    @Test(expected = PlanNoExisteException.class)
    public void elClienteContrataPlanConNombreInvalido() throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoYSinPlan();
        whenClienteContrataPlan(cliente, "Invalido");
    }

    private Cliente givenClienteLogueadoYSinPlan() {
        return new Cliente();
    }

    private void whenClienteContrataPlan(Cliente cliente, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        planService.contratarPlan(cliente.getId(), LocalDate.now().getMonth(), LocalDate.now().getYear(), plan );
    }

    private void thenElClienteTienePlan(Cliente cliente) {
        assertThat(cliente.getContrataciones()).isEqualTo(Collections.emptyList());
    }
}
