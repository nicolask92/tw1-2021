package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Exceptiones.ElClienteNoCorrespondeAlTurnoException;
import ar.edu.unlam.tallerweb1.Exceptiones.LaClaseEsDeUnaFechaAnterioALaActualException;
import ar.edu.unlam.tallerweb1.Exceptiones.TurnoExpiroException;
import ar.edu.unlam.tallerweb1.Exceptiones.YaHayTurnoDeLaMismaClaseException;
import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.TurnoRepositorio;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void testQueSiGuardeTurnoSeAgregue1ClienteALaClase() throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException {
        Cliente cliente = givenUnClienteActivo();
        Clase clase = givenClaseConLugar();
        whenGuardoTurno(clase.getId(), cliente.getId(), cliente, clase, new Turno());
        thenSeIncrementaEn1LaCantidadDeClientesEnLaClase(clase);

    }

    @Test(expected = Exception.class)
    public void testQueLaClaseNoSeEncontro() throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException {

        Cliente cliente = givenUnClienteActivo();
        givenLaClaseNoExiste();
        whenGuardoTurnoIncorrectamente(cliente);
        thenNoSeGuarda();
    }

    @Test
    public void QueSePuedeBorrarTurno() throws Exception, ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException {
        Cliente cliente = givenUnClienteActivo();
        Turno turno = givenTurno(cliente);
        whenBorroTurno(turno, cliente);
        thenSeBorraElTurno(turno.getId(), cliente.getId());

    }

    @Test(expected = ElClienteNoCorrespondeAlTurnoException.class)
    public void QueNoSePuedaBorrarTurnoConUsuarioDistintoAlUsurioDelTurno() throws ElClienteNoCorrespondeAlTurnoException, Exception, TurnoExpiroException {
        Turno turno = givenTurnoConCliente();
        Cliente cliente = givenUnClienteActivo();
        whenBorroTurno(turno, cliente);
        thenNoSeBorraElTurno(turno);
    }

    @Test(expected = LaClaseEsDeUnaFechaAnterioALaActualException.class)
    public void queNoSePuedaReservarTurnoDespuesDeLaFechaDeLaClase() throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException {
        Cliente cliente = givenUnClienteActivo();
        Clase clase =givenClaseConFechaAnterioAlDiaDeHoy();
        whenReservoTurno(cliente, clase);
        thenElTurnoNoSeReserva();
    }

    @Test
    public void testQueSeDevuelvanLosTurnosDelDiaLoHagaCorrectamente() {
        Cliente cliente = givenUnClienteActivo();
        List<Turno> turnos = givenTurnos(true);
        List<Turno> turnosDevueltosPorRepo = whenBuscoLosTurnosDelClienteParaElDiaDeHoy(cliente, turnos, true);
        thenTraeLosTurnosDelDiaDeHoy(turnosDevueltosPorRepo, turnos);
    }

    @Test
    public void testQueNoHayanTurnosParaHoyNoDevuelveNada() {
        Cliente cliente = givenUnClienteActivo();
        List<Turno> turnosDeAyer = givenTurnos(false);
        List<Turno> turnosDevueltosPorRepo = whenBuscoLosTurnosDelClienteParaElDiaDeHoy(cliente, turnosDeAyer, false);
        thenElListadoDeTurnosDelDiaDeHoyEsVacio(turnosDevueltosPorRepo);
    }

    @Test(expected = TurnoExpiroException.class)
    public void noSePuedeBorrarTurnoAnteriorALaFechaActual() throws ElClienteNoCorrespondeAlTurnoException, Exception, TurnoExpiroException {
        Cliente cliente = givenUnClienteActivo();
        Turno turno = givenHayUnTurnoDeAyer(cliente);
        whenBorroTurno(turno, cliente);
    }

    @Test(expected = YaHayTurnoDeLaMismaClaseException.class)
    public void noSePuedaSacarTurno2VecesDeLaMismaClase() throws YaHayTurnoDeLaMismaClaseException, LaClaseEsDeUnaFechaAnterioALaActualException, Exception {
        Cliente cliente = givenUnClienteActivo();
        Turno turno = givenTurno(cliente);
        whenGuardoTurno(turno.getClase().getId(), cliente.getId(), cliente, turno.getClase(), turno);
    }

    private Turno givenHayUnTurnoDeAyer(Cliente cliente) {
        Clase clase = new Clase();
        clase.setDiaClase(LocalDateTime.now().minusDays(1));
        return new Turno(cliente, clase , LocalDate.now().minusDays(5));
    }

    private List<Turno> givenTurnos(boolean paraHoy) {
        Turno turno = new Turno();
        Turno turno2 = new Turno();

        turno.setFechaYHoraDeReserva(paraHoy ? LocalDate.now() : LocalDate.now().minusDays(1));
        turno2.setFechaYHoraDeReserva(paraHoy ? LocalDate.now() : LocalDate.now().minusDays(1));

        return List.of(turno, turno2);
    }

    private List<Turno> whenBuscoLosTurnosDelClienteParaElDiaDeHoy(Cliente cliente, List<Turno> turnos, boolean turnosDeHoy) {
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        if (turnosDeHoy) {
            when(turnoRepositorio.getTurnosParaHoy(cliente)).thenReturn(turnos);
        } else {
            when(turnoRepositorio.getTurnosParaHoy(cliente)).thenReturn(null);
        }
        return turnoService.getTurnosParaHoy(cliente.getId());
    }

    private Clase givenClaseConFechaAnterioAlDiaDeHoy() throws Exception {
        Clase clase =new Clase(LocalDateTime.now().minusDays(1), new Actividad(), Modalidad.PRESENCIAL);
        clase.setId(1L);
        return clase;
    }

    private Turno givenTurnoConCliente() {
        return new Turno (new Cliente() , new Clase(), LocalDate.now());
    }

    private Turno givenTurno(Cliente cliente) throws Exception {
        Clase clase = new Clase(LocalDateTime.now().plusDays(2), new Actividad(), Modalidad.PRESENCIAL);
        clase.setId(1L);
        Turno turno = new Turno(cliente , clase, LocalDate.now());
        turno.setId(1L);
        return turno;
    }

    private void givenLaClaseNoExiste() throws Exception {
        when(claseRepositorio.getById(IDCLASE)).thenReturn(null);
    }

    private Cliente givenUnClienteActivo() {
        Cliente cliente = new Cliente( "Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com");
        cliente.setId(1L);
        return cliente;
    }

    private Clase givenClaseConLugar() throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoYHorarioValido();
        return new Clase(LocalDateTime.now().plusHours(4), actividad, Modalidad.PRESENCIAL);
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

    private void whenGuardoTurno(Long idClase, Long idUsuario, Cliente cliente, Clase clase, Turno turno) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException {
        when(claseRepositorio.getById(idClase)).thenReturn(clase);
        when(clienteRepositorio.getById(idUsuario)).thenReturn(cliente);
        when(turnoRepositorio.getTurnosByIdCliente(cliente)).thenReturn(List.of(turno));
        doNothing().when(turnoRepositorio).guardarTurno(cliente,clase);
        turnoService.guardarTurno(idClase, idUsuario);

    }

    private void whenGuardoTurnoIncorrectamente(Cliente cliente) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException {
       when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
       turnoService.guardarTurno(IDCLASE, cliente.getId());
    }

    private void whenBorroTurno(Turno turno, Cliente cliente) throws Exception, ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException {
        when(turnoRepositorio.getTurnoById(turno.getId())).thenReturn(turno);
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        turnoService.borrarTurno(turno.getId(), cliente.getId());

    }

    private void whenReservoTurno(Cliente cliente, Clase clase) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException {
        when(claseRepositorio.getById(clase.getId())).thenReturn(clase);
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        turnoService.guardarTurno(cliente.getId(), clase.getId());
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
    private void thenNoSeBorraElTurno(Turno turno) {
        verify(turnoRepositorio, never()).borrarTurno(turno);
    }

    private void thenSeBorraElTurno(Long idTurno, Long idCliente) {
        verify(turnoRepositorio, times(1)).getTurnoById(idTurno);
    }

    private void thenTraeLosTurnosDelDiaDeHoy(List<Turno> turnosDevueltosPorRepo, List<Turno> turnosArmados) {
        Assert.assertEquals(turnosDevueltosPorRepo, turnosArmados);
    }

    private void thenElTurnoNoSeReserva() {
    }

    private void thenElListadoDeTurnosDelDiaDeHoyEsVacio(List<Turno> turnosDevueltosPorRepo) {
        assertThat(turnosDevueltosPorRepo == null);
    }

}
