package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.servicios.ClienteService;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;


public class PerfilControllerTest {

    HttpSession mockSession = mock(HttpSession.class);
    ClienteService clienteService = mock(ClienteService.class);
    PerfilController perfilController = new PerfilController(clienteService);

    @Test
    public void seMuestraElPerfilDelCliente() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenClienteLogueadoYConPlan();
        ModelAndView mv = whenElClienteConsultaElPerfil(cliente);
        thenSeMuestraElPerfilDelCliente(mv);
    }

    @Test
    public void historialPagosSeMuestraCorrectamente() {

    }

    private Cliente givenClienteLogueadoYConPlan() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente();
        LocalDate hoy = LocalDate.now();
        cliente.agregarPago(new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.ESTANDAR));
        return cliente;
    }

    private ModelAndView whenElClienteConsultaElPerfil(Cliente cliente) {
        when(clienteService.getCliente(cliente.getId())).thenReturn(cliente);
        return perfilController.getPerfil(mockSession);
    }

    private void thenSeMuestraElPerfilDelCliente(ModelAndView mv) {
        assertThat(mv.getViewName()).isEqualTo("perfil");
    }
}
