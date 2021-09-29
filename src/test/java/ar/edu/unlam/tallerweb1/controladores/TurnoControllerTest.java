package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import ar.edu.unlam.tallerweb1.servicios.TurnoService;
import org.dom4j.rule.Mode;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TurnoControllerTest {

    ClaseService claseService = mock(ClaseService.class);
    TurnoService turnoService = mock(TurnoService.class);
    TurnoController turnoController = new TurnoController(claseService, turnoService);
    HttpSession mockDeHttpSession = mock(HttpSession.class);


    @Test
    public void testQueSeMuestrenLosTurnosDeUnUsuarioEspecifico(){
     Turno turno = givenHayUnTurno();
     ModelAndView mv = whenConsultoElTurnoConUnUsuario(turno);
     thenMuestroLosTurnosDelUsuario(mv, turno);
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

    @Test
    public void testQueSePuedaReservarTurno() throws Exception {
        Clase clase = givenQueLaClaseTengaLugar();
        ModelAndView mv = whenReservoTurno(clase.getId(), mockDeHttpSession);
        thenReservoElTurno(mv);
    }

    @Test
    public void testQueNoSePuedaReservarTurno() throws Exception {
      Clase clase = givenQueLaClaseNoTengaLugar();
      ModelAndView mv = whenReservoTurno(clase.getId(), mockDeHttpSession);
      thenNoPuedoReservarTurno(mv);

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
        clase.setCantidadMaxima(0);
        Cliente cliente = givenUnClienteActivo();

        doThrow(Exception.class).when(turnoService).guardarTurno(clase.getId(),cliente.getId());

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

    private ModelAndView whenReservoTurno(Long id, HttpSession session) throws Exception {

        return turnoController.reservarTurno(id, session);
    }

    private void thenReservoElTurno(ModelAndView mv) {
        //doNothing().when(turnoService).guardarTurno(anyLong(), anyLong());
        //Assert.assertEquals(mv.getView(), "clases-para-turnos");
        assertThat(mv.getModel().get("msg")).isEqualTo("Se guardo turno correctamente");
        assertThat(mv.getViewName()).isEqualTo("redirect:/home");
    }

    private void thenNoPuedoReservarTurno(ModelAndView mv) {
        assertThat(mv.getModel().get("msg")).isEqualTo("Cupo máximo alcanzado");
        assertThat(mv.getViewName()).isEqualTo("clases-para-turnos");
    }
}