package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.*;
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
import java.util.ArrayList;
import java.util.Collections;
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
    public void testQueSiGuardeTurnoSeAgregue1ClienteALaClase() throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        Clase clase = givenClaseConLugar(LocalDateTime.now());
        Turno turno = givenTurnoDeOtraClase();
        whenGuardoTurno(clase.getId(), cliente.getId(), cliente, clase, turno);
        thenSeIncrementaEn1LaCantidadDeClientesEnLaClase(clase);
    }

    @Test(expected = Exception.class)
    public void testQueLaClaseNoSeEncontro() throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        givenLaClaseNoExiste();
        whenGuardoTurnoIncorrectamente(cliente);
        thenNoSeGuarda();
    }

    @Test
    public void QueSePuedeBorrarTurno() throws Exception, ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        Turno turno = givenTurno(cliente, false);
        whenBorroTurno(turno, cliente);
        thenSeBorraElTurno(turno.getId(), cliente.getId());

    }

    @Test(expected = ElClienteNoCorrespondeAlTurnoException.class)
    public void QueNoSePuedaBorrarTurnoConUsuarioDistintoAlUsurioDelTurno() throws ElClienteNoCorrespondeAlTurnoException, Exception, TurnoExpiroException, YaTienePagoRegistradoParaMismoMes {
        Turno turno = givenTurnoConCliente();
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        whenBorroTurno(turno, cliente);
        thenNoSeBorraElTurno(turno);
    }

    @Test(expected = LaClaseEsDeUnaFechaAnterioALaActualException.class)
    public void queNoSePuedaReservarTurnoDespuesDeLaFechaDeLaClase() throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        Clase clase = givenClaseConFechaAnterioAlDiaDeHoy();
        whenReservoTurno(cliente, clase);
        thenElTurnoNoSeReserva();
    }

    @Test
    public void testQueSeDevuelvanLosTurnosDelDiaLoHagaCorrectamente() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        List<Turno> turnos = givenTurnos(true);
        List<Turno> turnosDevueltosPorRepo = whenBuscoLosTurnosDelClienteParaElDiaDeHoy(cliente, turnos, true);
        thenTraeLosTurnosDelDiaDeHoy(turnosDevueltosPorRepo, turnos);
    }

    @Test
    public void testQueNoHayanTurnosParaHoyNoDevuelveNada() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        List<Turno> turnosDeAyer = givenTurnos(false);
        List<Turno> turnosDevueltosPorRepo = whenBuscoLosTurnosDelClienteParaElDiaDeHoy(cliente, turnosDeAyer, false);
        thenElListadoDeTurnosDelDiaDeHoyEsVacio(turnosDevueltosPorRepo);
    }

    @Test(expected = TurnoExpiroException.class)
    public void noSePuedeBorrarTurnoAnteriorALaFechaActual() throws ElClienteNoCorrespondeAlTurnoException, Exception, TurnoExpiroException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        Turno turno = givenHayUnTurnoDeAyer(cliente, LocalDate.now());
        whenBorroTurno(turno, cliente);
    }

    @Test(expected = YaHayTurnoDeLaMismaClaseException.class)
    public void noSePuedaSacarTurno2VecesDeLaMismaClase() throws YaHayTurnoDeLaMismaClaseException, LaClaseEsDeUnaFechaAnterioALaActualException, Exception, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.PREMIUM);
        Turno turno = givenTurno(cliente, false);
        whenGuardoTurno(turno.getClase().getId(), cliente.getId(), cliente, turno.getClase(), turno);
    }

    @Test(expected = SuPlanNoPermiteMasInscripcionesPorDiaException.class)
    public void testQueSiTieneElPlanBasicoYTieneUnTurnoSacadoNoDejaSacarOtroTurnoMasEnElMismoDia() throws Exception, YaHayTurnoDeLaMismaClaseException, LaClaseEsDeUnaFechaAnterioALaActualException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        Turno turno = givenTurno(cliente, false);
        Turno turno2 = givenTurno(cliente, false);
        whenIntentoReservar2DoTurnoSaltaExcepcionDeTurnosPorDia(turno, cliente, turno2.getClase());
    }

    @Test(expected = SuPlanNoPermiteMasInscripcionesPorDiaException.class)
    public void testQueSiTieneElPlanEstadarYTiene3TurnosSacadosNoDejaSacarOtroTurnoMasEnElMismoDia() throws Exception, YaHayTurnoDeLaMismaClaseException, LaClaseEsDeUnaFechaAnterioALaActualException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.ESTANDAR);
        Turno turno = givenTurno(cliente, false);
        Turno turno2 = givenTurno(cliente, false);
        Turno turno3 = givenTurno(cliente, false);
        Clase clase = givenClaseConLugarDentroDe2Dias();
        List<Turno> turnos = List.of(turno, turno2, turno3);
        whenIntentoReservar4ToTurnoSaltaExcepcionDeTurnosPorDia(turnos, cliente, clase);
    }

    @Test
    public void testQueSiTieneElPlanEPremiumPuedaSacarMasDe3TurnosPorDia() throws Exception, YaHayTurnoDeLaMismaClaseException, LaClaseEsDeUnaFechaAnterioALaActualException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.PREMIUM);
        Turno turno = givenTurno(cliente, false);
        Turno turno2 = givenTurno(cliente, false);
        Turno turno3 = givenTurno(cliente, false);
        Clase clase = givenClaseConLugarDentroDe2Dias();
        List<Turno> turnos = List.of(turno, turno2, turno3);
        whenIntentoReservar4ToTurnoSaltaExcepcionDeTurnosPorDia(turnos, cliente, clase);
        thenReservo4Turno();
    }

    @Test
    public void testBuscarClasesYQueSeEncuentren() throws Exception, NoSeEncontroClaseConEseNombreException {
        String claseBuscada = givenClaseABuscada("CROSSFIT");
        Clase clase = givenClaseConLugar(LocalDateTime.now());
        List<Clase> clasesEncontradas = whenBuscoClases(claseBuscada, clase);
        thenEncuntroLasClases(clasesEncontradas);
    }

    @Test(expected = NoSeEncontroClaseConEseNombreException.class)
    public void testBuscarClaseYQueNoSeEncuentre() throws NoSeEncontroClaseConEseNombreException, Exception {
        String claseBuscada = givenClaseABuscada("asd");
        Clase clase = givenClaseConLugar(LocalDateTime.now());
        List<Clase> clases = whenBuscoClasesNoLaEncuentra(claseBuscada, clase);
    }

    @Test(expected = SuPlanNoPermiteMasInscripcionesPorSemanaException.class)
    public void testSiTienePlanBasicoNoPuedeSacarMasDeTresTurnosPorSemana() throws Exception, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.BASICO);
        Clase clase = givenClaseConLugar(LocalDateTime.of(2021,12,15, 14, 2));
        List<Turno> turnosYaSacados = givenTurnosSacadosParaEstaSemana(cliente, 3, LocalDateTime.of(2021,12,14, 14, 2));
        whenIntentoReservarTurnoEnLaSemanaFalla(cliente, clase, turnosYaSacados);
    }

    @Test(expected = SuPlanNoPermiteMasInscripcionesPorSemanaException.class)
    public void testSiTienePlanEstandarNoPuedeSacarMasDeSeisTurnosPorSemana() throws Exception, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(Plan.ESTANDAR);
        Clase clase = givenClaseConLugar(LocalDateTime.of(2021,12,15, 14, 2));
        List<Turno> turnosYaSacados = givenTurnosSacadosParaEstaSemana(cliente, 6, LocalDateTime.of(2021,12,14, 14, 2));
        whenIntentoReservarTurnoEnLaSemanaFalla(cliente, clase, turnosYaSacados);
    }

    private List<Turno> givenTurnosSacadosParaEstaSemana(Cliente cliente, int cantidadTurnos, LocalDateTime fechaYHora) throws Exception {
        List<Turno> turnos = new ArrayList<>();
        for (int i = 0; i < cantidadTurnos; i++) {
            turnos.add(givenHayUnTurnoDeAyer(cliente, fechaYHora.toLocalDate()));
        }
        return turnos;
    }

    private String givenClaseABuscada(String claseBuscada) {
        return claseBuscada;
    }

    private Turno givenTurnoDeOtraClase() throws Exception {
        Clase clase = givenClaseConFechaAnterioAlDiaDeHoy();
        Turno turno = new Turno();
        turno.setClase(clase);
        return turno;
    }

    private Turno givenHayUnTurnoDeAyer(Cliente cliente, LocalDate fecha) {
        Clase clase = new Clase();
        clase.setId(1L);
        clase.setDiaClase(LocalDateTime.of(fecha, LocalTime.of(10,0)).minusDays(1));
        return new Turno(cliente, clase ,fecha.minusDays(5));
    }

    private List<Turno> givenTurnos(boolean paraHoy) {
        Turno turno = new Turno();
        Turno turno2 = new Turno();

        turno.setFechaYHoraDeReserva(paraHoy ? LocalDate.now() : LocalDate.now().minusDays(1));
        turno2.setFechaYHoraDeReserva(paraHoy ? LocalDate.now() : LocalDate.now().minusDays(1));

        return List.of(turno, turno2);
    }

    private Clase givenClaseConLugarDentroDe2Dias() throws Exception {
        return new Clase(LocalDateTime.now().plusDays(2), new Actividad(), Modalidad.PRESENCIAL);
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

    private Turno givenTurno(Cliente cliente, boolean paraHoy) throws Exception {
        Clase clase = new Clase(paraHoy ? LocalDateTime.now() : LocalDateTime.now().plusDays(2), new Actividad(), Modalidad.PRESENCIAL);
        clase.setId(1L);
        Turno turno = new Turno(cliente , clase, LocalDate.now());
        turno.setId(1L);
        return turno;
    }

    private void givenLaClaseNoExiste() throws Exception {
        when(claseRepositorio.getById(IDCLASE)).thenReturn(null);
    }

    private Cliente givenUnClienteActivo(Plan tipoPlan) throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente( "Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com", Collections.EMPTY_LIST);
        cliente.setId(1L);
        LocalDate hoy = LocalDate.now();
        cliente.agregarPago(new Pago(cliente, hoy.getMonth(), hoy.getYear(), tipoPlan));
        return cliente;
    }

    private Clase givenClaseConLugar(LocalDateTime fechaYHora) throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoYHorarioValido(fechaYHora);
        return new Clase(fechaYHora.plusHours(4), actividad, Modalidad.PRESENCIAL);
    }

    private Actividad givenUnaActividadConPeriodoYHorarioValido(LocalDateTime fechaYHora) throws Exception {
        LocalDateTime antesDeAyer = fechaYHora.minusDays(2);
        LocalDateTime pasadoManiana = fechaYHora.plusDays(2);
        Periodo periodo = new Periodo(antesDeAyer, pasadoManiana);

        return new Actividad("Actividad de alto impacto", Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo);
    }

    private void whenGuardoTurno(Long idClase, Long idUsuario, Cliente cliente, Clase clase, Turno turno) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException {
        when(claseRepositorio.getById(idClase)).thenReturn(clase);
        when(clienteRepositorio.getById(idUsuario)).thenReturn(cliente);
        when(turnoRepositorio.getTurnosByIdCliente(cliente)).thenReturn(List.of(turno));
        doNothing().when(turnoRepositorio).guardarTurno(cliente,clase);
        turnoService.guardarTurno(idClase, idUsuario);
    }

    private void whenGuardoTurnoIncorrectamente(Cliente cliente) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException {
       when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
       turnoService.guardarTurno(IDCLASE, cliente.getId());
    }

    private void whenBorroTurno(Turno turno, Cliente cliente) throws Exception, ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException {
        when(turnoRepositorio.getTurnoById(turno.getId())).thenReturn(turno);
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        turnoService.borrarTurno(turno.getId(), cliente.getId());

    }

    private void whenReservoTurno(Cliente cliente, Clase clase) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException {
        when(claseRepositorio.getById(clase.getId())).thenReturn(clase);
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        turnoService.guardarTurno(cliente.getId(), clase.getId());
    }

    private void whenIntentoReservar2DoTurnoSaltaExcepcionDeTurnosPorDia(Turno turno, Cliente cliente, Clase clase) throws Exception, YaHayTurnoDeLaMismaClaseException, LaClaseEsDeUnaFechaAnterioALaActualException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException {
        when(claseRepositorio.getById(clase.getId())).thenReturn(clase);
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        when(turnoRepositorio.getTurnosByIdCliente(cliente)).thenReturn(List.of(turno));
        turnoService.guardarTurno(clase.getId(), cliente.getId());
    }

    private void whenIntentoReservar4ToTurnoSaltaExcepcionDeTurnosPorDia(List<Turno> turno, Cliente cliente, Clase clase) throws Exception, YaHayTurnoDeLaMismaClaseException, LaClaseEsDeUnaFechaAnterioALaActualException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException {
        when(claseRepositorio.getById(clase.getId())).thenReturn(clase);
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        when(turnoRepositorio.getTurnosByIdCliente(cliente)).thenReturn(turno);
        turnoService.guardarTurno(clase.getId(), cliente.getId());
    }

    private List<Clase> whenBuscoClases(String claseBuscada, Clase clase) throws NoSeEncontroClaseConEseNombreException {
        when(turnoRepositorio.buscarClases(claseBuscada)).thenReturn(List.of(clase));
        return turnoService.buscarClase(claseBuscada);
    }

    private List<Clase> whenBuscoClasesNoLaEncuentra(String claseBuscada, Clase clase) throws NoSeEncontroClaseConEseNombreException {
        when(turnoRepositorio.buscarClases(claseBuscada)).thenReturn(List.of(clase));
        return turnoService.buscarClase(claseBuscada);
    }

    private void whenIntentoReservarTurnoEnLaSemanaFalla(Cliente cliente, Clase clase, List<Turno> turnosYaSacados) throws YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, Exception, SuPlanNoPermiteMasInscripcionesPorSemanaException {
        when(claseRepositorio.getById(clase.getId())).thenReturn(clase);
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        when(turnoRepositorio.getTurnosByIdCliente(cliente)).thenReturn(turnosYaSacados);
        turnoService.guardarTurno(clase.getId() ,cliente.getId());
    }

    private void thenSeIncrementaEn1LaCantidadDeClientesEnLaClase(Clase clase) throws Exception {
        assertThat(clase.getClientes().size()).isEqualTo(1);
        verify(turnoRepositorio, times(1)).guardarTurno(any(),any());
        verify(claseRepositorio,times(1)).getById(clase.getId());
    }
    private void thenNoSeGuarda() {
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

    private void thenEncuntroLasClases(List<Clase> clasesEncontradas) {
        assertThat(clasesEncontradas.size()).isEqualTo(1);
        verify(turnoRepositorio, times(1)).buscarClases(anyString());
    }

    private void thenReservo4Turno() {
        verify(turnoRepositorio, times(1)).guardarTurno(any(),any());
    }

}
