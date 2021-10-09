package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import ar.edu.unlam.tallerweb1.servicios.TurnoService;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;

public class TurnoControllerTest {

    ClaseService claseService = mock(ClaseService.class);
    TurnoService turnoService = mock(TurnoService.class);
    TurnoController turnoController = new TurnoController(claseService, turnoService);
    HttpSession mockDeHttpSession = mock(HttpSession.class);


    @Test
    public void testQueSeMuestrenLosTurnosDeUnUsuarioEspecifico() {
        Turno turno = givenHayUnTurno();
        ModelAndView mv = whenConsultoElTurnoConUnUsuario(turno);
        thenMuestroLosTurnosDelUsuario(mv, turno);
    }

    @Test
    public void testQueSePuedaReservarTurno() throws Exception {
        Clase clase = givenQueLaClaseTengaLugar();
        Cliente cliente = new Cliente();
        ModelAndView mv = whenReservoTurno(clase.getId(), mockDeHttpSession, cliente.getId());
        thenReservoElTurnoCorrectamente(mv);
    }

    @Test
    public void testQueNoSePuedaReservarTurno() throws Exception {
        Clase clase = givenQueLaClaseNoTengaLugar();
        Cliente cliente = givenUnClienteActivo();
        ModelAndView mv = whenReservoTurnoSinLugar(clase.getId(), mockDeHttpSession, cliente.getId());
        thenNoPuedoReservarTurno(mv);

    }

    private void thenMuestroLosTurnosDelUsuario(ModelAndView mv, Turno turno) {
        assertThat(mv.getViewName()).isEqualTo("Turnos");
        assertThat(mv.getModel().get("turnos")).isEqualTo(List.of(turno));
    }

    private ModelAndView whenConsultoElTurnoConUnUsuario(Turno turno) {
        List<Turno> turnoCliente = List.of(turno);
        Cliente cliente = mock(Cliente.class);
        when(turnoService.getTurnosPorId(cliente.getId())).thenReturn(turnoCliente);
        ModelAndView mv = turnoController.mostrarTurnoPorId(cliente.getId());
        return mv;
    }

    private Turno givenHayUnTurno() {
        return new Turno();
    }

    private Cliente givenUnClienteActivo() {
        return new Cliente("Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com");
    }

    private Clase givenQueLaClaseTengaLugar() throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoYHorarioValido();
        return new Clase(LocalDateTime.now(), actividad, Modalidad.PRESENCIAL);
    }

    private Clase givenQueLaClaseNoTengaLugar() throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoYHorarioValido();
        Clase clase = new Clase(LocalDateTime.now(), actividad, Modalidad.PRESENCIAL);
        //clase.setCantidadMaxima(0);
        return clase;
    }

    private Actividad givenUnaActividadConPeriodoYHorarioValido() throws Exception {
        LocalDateTime antesDeAyer = LocalDateTime.now().minusDays(2);
        LocalDateTime pasadoManiana = LocalDateTime.now().plusDays(2);
        Periodo periodo = new Periodo(antesDeAyer, pasadoManiana);

        LocalTime ahora = LocalTime.now();
        LocalTime enUnRato = LocalTime.now().plusHours(2);
        Horario horario = new Horario(ahora, enUnRato);

        return new Actividad("Actividad de alto impacto", Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo, horario);
    }

    private ModelAndView whenReservoTurno(Long id, HttpSession session, Long idUsuario) throws Exception {
        when(session.getAttribute("usuarioId")).thenReturn(idUsuario);
        doNothing().when(turnoService).guardarTurno(id, idUsuario);
        return turnoController.reservarTurno(id, session);
    }

    private ModelAndView whenReservoTurnoSinLugar(Long id, HttpSession session, Long idUsuario) throws Exception {
        when(session.getAttribute("usuarioId")).thenReturn(idUsuario);
        doThrow(Exception.class).when(turnoService).guardarTurno(id, idUsuario);
        return turnoController.reservarTurno(id, session);
    }

    private void thenReservoElTurnoCorrectamente(ModelAndView mv) {
        assertThat(mv.getModel().get("msg")).isEqualTo("Se guardo turno correctamente");
        assertThat(mv.getViewName()).isEqualTo("redirect:/home");
    }

    private void thenNoPuedoReservarTurno(ModelAndView mv) {
        assertThat(mv.getModel().get("msg")).isEqualTo("Cupo máximo alcanzado");
        assertThat(mv.getViewName()).isEqualTo("clases-para-turnos");
    }
}