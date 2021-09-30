package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.TurnoRepositorio;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class TurnoServiceTest {


    TurnoRepositorio turnoRepositorio = mock(TurnoRepositorio.class);
    ClaseRepositorio claseRepositorio = mock(ClaseRepositorio.class);
    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);
    private TurnoService turnoService = new TurnoServiceImpl(turnoRepositorio, claseRepositorio, clienteRepositorio);


    @Test
    public void testQueSeGuardeTurnoSeAgregue1ClienteALaClase() throws Exception {
        Cliente cliente = givenUnClienteActivo();
        Clase clase = givenClaseConLugar();
        whenGuardoTurno(clase.getId(), cliente.getId(), cliente, clase);
        thenSeIncrementaEn1LaCantidadDeClientesEnLaClase(clase);

    }

    @Test
    public void testQueLaClaseNoSeEncontro() throws Exception {

        Cliente cliente = givenUnClienteActivo();
        Clase clase = givenLaClaseNoExiste();
        whenGuardoTurnoIncorrectamente(clase.getId(), cliente.getId(), cliente, clase);
        thenNoSeGuarda();
    }

    private Clase givenLaClaseNoExiste() {
        return new Clase();
    }


    private Cliente givenUnClienteActivo() {
        return new Cliente("Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com");
    }

    private Clase givenClaseConLugar() throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoYHorarioValido();
        return new Clase(LocalDateTime.now(), actividad, Modalidad.PRESENCIAL);
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
    private void whenGuardoTurno(Long idClase, Long idUsuario, Cliente cliente, Clase clase) throws Exception {
        when(claseRepositorio.getById(idClase)).thenReturn(clase);
        when(clienteRepositorio.getById(idUsuario)).thenReturn(cliente);
        turnoService.guardarTurno(idClase, idUsuario);

    }
    private void whenGuardoTurnoIncorrectamente(Long idClase, Long idUsuario, Cliente cliente, Clase clase) throws Exception {
        when(clienteRepositorio.getById(idUsuario)).thenReturn(cliente);
        doThrow(Exception.class).when(claseRepositorio).getById(clase.getId());
        turnoService.guardarTurno(idClase, idUsuario);
    }

    private void thenSeIncrementaEn1LaCantidadDeClientesEnLaClase(Clase clase) {
        assertThat(clase.getClientes().size()).isEqualTo(1);
        verify(turnoRepositorio, times(1)).guardarTurno(any(),any());
    }

    private void thenNoSeGuarda(){
        //verify(clase ,never()).agregarCliente(cliente);
        verify(turnoRepositorio, never()).guardarTurno(any(), any());
    }

}
