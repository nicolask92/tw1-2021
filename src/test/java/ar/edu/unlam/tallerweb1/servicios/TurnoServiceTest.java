package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.TurnoRepositorio;
import org.junit.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

public class TurnoServiceTest {


    private static final Long IDCLASE = 20L ;
    TurnoRepositorio turnoRepositorio = mock(TurnoRepositorio.class);
    ClaseRepositorio claseRepositorio = mock(ClaseRepositorio.class);
    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);
    private TurnoService turnoService = new TurnoServiceImpl(turnoRepositorio, claseRepositorio, clienteRepositorio);


    @Test
    public void testQueSiGuardeTurnoSeAgregue1ClienteALaClase() throws Exception {
        Cliente cliente = givenUnClienteActivo();
        Clase clase = givenClaseConLugar();
        whenGuardoTurno(clase.getId(), cliente.getId(), cliente, clase);
        thenSeIncrementaEn1LaCantidadDeClientesEnLaClase(clase);

    }

    @Test(expected = Exception.class)
    public void testQueLaClaseNoSeEncontro() throws Exception {

        Cliente cliente = givenUnClienteActivo();
        givenLaClaseNoExiste();
        whenGuardoTurnoIncorrectamente(cliente);
        thenNoSeGuarda();
    }

    private void givenLaClaseNoExiste() throws Exception {
        when(claseRepositorio.getById(IDCLASE)).thenReturn(null);
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

        return new Actividad("Actividad de alto impacto", Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo);
    }
    private void whenGuardoTurno(Long idClase, Long idUsuario, Cliente cliente, Clase clase) throws Exception {
        when(claseRepositorio.getById(idClase)).thenReturn(clase);
        when(clienteRepositorio.getById(idUsuario)).thenReturn(cliente);
        doNothing().when(turnoRepositorio).guardarTurno(cliente,clase);
        turnoService.guardarTurno(idClase, idUsuario);

    }
    private void whenGuardoTurnoIncorrectamente(Cliente cliente) throws Exception {
       when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
       turnoService.guardarTurno(IDCLASE, cliente.getId());
    }

    private void thenSeIncrementaEn1LaCantidadDeClientesEnLaClase(Clase clase) throws Exception {
        assertThat(clase.getClientes().size()).isEqualTo(1);
        verify(turnoRepositorio, times(1)).guardarTurno(any(),any());
        verify(claseRepositorio,times(1)).getById(clase.getId());
    }

    private void thenNoSeGuarda() {
        //verify(clase ,never()).agregarCliente(cliente);
        verify(turnoRepositorio, never()).guardarTurno(any(), any());
    }

}
