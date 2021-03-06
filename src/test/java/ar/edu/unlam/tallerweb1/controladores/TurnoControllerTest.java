package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.exceptiones.*;
import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import ar.edu.unlam.tallerweb1.servicios.TurnoService;
import ar.edu.unlam.tallerweb1.viewBuilders.CalendarioDeActividades;
import ar.edu.unlam.tallerweb1.viewBuilders.ClasesViewModelBuilder;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TurnoControllerTest {

    ClaseService claseService = mock(ClaseService.class);
    TurnoService turnoService = mock(TurnoService.class);
    HttpServletRequest mockDeHttpServletSession = mock(HttpServletRequest.class);
    HttpSession mockSession = mock(HttpSession.class);
    ClasesViewModelBuilder clasesViewModelBuilder = mock(ClasesViewModelBuilder.class);

    TurnoController turnoController = new TurnoController(claseService, turnoService, clasesViewModelBuilder);

    @Test
    public void testQueSeMuestrenLosTurnosDeUnUsuarioEspecifico() throws Exception {
        Turno turno = givenHayUnTurno();
        Cliente cliente = givenUnUsuarioSinTurnos();
        ModelAndView mv = whenConsultoElTurnoConUnUsuario(turno, mockDeHttpServletSession, cliente.getId());
        thenMuestroLosTurnosDelUsuario(mv, turno);
    }

    @Test
    public void testUsuarioNoTieneTurnosParaConsultar() throws Exception {
        Cliente cliente = givenUnUsuarioSinTurnos();
        ModelAndView mv = whenConsultoUsuarioSinTurnos(cliente.getId(), mockDeHttpServletSession);
        thenMuestroQueNoTieneTurnos(mv);
    }

    @Test
    public void testQueSePuedaReservarTurno() throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaVencioElMesException {
        Clase clase = givenQueLaClaseTengaLugar();
        Cliente cliente = new Cliente();
        ModelAndView mv = whenReservoTurno(clase.getId(), mockDeHttpServletSession, cliente.getId());
        thenReservoElTurnoCorrectamente(mv);
    }

    @Test
    public void testQueNoSePuedaReservarTurno() throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes, YaVencioElMesException {
        Clase clase = givenQueLaClaseNoTengaLugar();
        Cliente cliente = givenUnClienteActivo(true, Plan.BASICO);
        ModelAndView mv = whenReservoTurnoSinLugar(clase.getId(), mockDeHttpServletSession, cliente.getId());
        thenNoPuedoReservarTurno(mv);
    }

    @Test
    public void TestQueSeBorreReservaDeTurno() throws Exception, ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(true, Plan.BASICO);
        Turno turno = givenUnClienteConUnTurno(cliente);
        ModelAndView mv = whenBorroTurnoExistente(turno, cliente, mockDeHttpServletSession);
        thenBorroTurno(mv);
    }

    @Test
    public void queNoSePuedaReservarTurnoDespuesDeLaFechaDeLaClase() throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes, YaVencioElMesException {
        Cliente cliente =givenUnClienteActivo(true, Plan.BASICO);
        Clase clase = givenClaseConFechaAnterioAlDiaDeHoy();
        ModelAndView mv = whenReservoTurnoConFechaAnteriorALaActual(clase.getId(), mockDeHttpServletSession, cliente.getId());
        thenElTurnoNoSeReseva(mv);
    }

    @Test
    public void QueNoSePuedaBorrarTurnoConUsuarioDistintoAlUsurioDelTurno() throws ElClienteNoCorrespondeAlTurnoException, Exception, TurnoExpiroException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente =givenUnClienteActivo(true, Plan.BASICO);
        Cliente cliente2 = givenUnClienteActivo(true , Plan.BASICO);
        Turno turno = givenUnClienteConUnTurno(cliente);
        ModelAndView mv = whenBorroTurnoConUsuarioDistintoAlDelTurno(turno, cliente2, mockDeHttpServletSession);
        thenElTurnoNoSeBorraYRedirigeALaViewTurnos(mv);
    }

    @Test
    public void testSiHayTurnosParaElDiaDeHoySeLoRenderice() throws Exception {
        Turno turno = givenHayUnTurnoParaHoy();
        ModelAndView mv = whenMuestroLosTurnos(turno);
        thenElTurnoSeMandaALaVista(mv, turno);
    }

    @Test
    public void noSePuedeBorrarTurnoAnteriorALaFechaActual() throws ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(true, Plan.BASICO);
        Turno turno = givenHayUnTurnoDeAyer(cliente);
        ModelAndView mv = whenBorroTurnoDeAyer(turno, cliente, mockDeHttpServletSession);
        thenElTurnoNoSeBorraYSaleAdvertencia(mv);
    }

    @Test
    public void noSePuedaSacarTurno2VecesDeLaMismaClase() throws LaClaseEsDeUnaFechaAnterioALaActualException, Exception, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes, YaVencioElMesException {
        Cliente cliente = givenUnClienteActivo(true, Plan.BASICO);
        Turno turno = givenHayUnTurnoParaHoy();
        turno.setCliente(cliente);
        ModelAndView mv = whenReservoTurnoDeLaMismaClase(turno.getClase().getId(), mockDeHttpServletSession, cliente.getId());
        thenNoPuedoReservarTurnoDeLaMismaClase(mv);
    }

    @Test
    public void testQueNoSePuedaReservarTurnoSiElTipoNoTienePlan() throws Exception, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = givenUnClienteActivo(false , Plan.BASICO);
        Clase clase = givenClaseConFechaAnterioAlDiaDeHoy();
        ModelAndView mv = whenReservoTurnoSinPlan(clase.getId(), mockDeHttpServletSession, cliente.getId());
        thenRedirigeALaVistaDePlanes(mv);
    }

    @Test
    public void testBuscarClaseYQueSeEncuntre() throws Exception, NoSeEncontroClaseConEseNombreException {
        String claseABuscar = givenClaseABuscar("CROSSFIT");
        Clase clase = givenQueLaClaseTengaLugar();
        ModelAndView mv = whenBuscoLaClase(claseABuscar, clase);
        thenEncuentroLaClaseBuscada(mv, clase);
    }

    @Test
    public void testBuscarClaseYNoSeEncuentra() throws NoSeEncontroClaseConEseNombreException, Exception {
        String claseABuscar = givenClaseABuscar("asd");
        Clase clase = givenQueLaClaseTengaLugar();
        ModelAndView mv  = whenBuscoLaClase(claseABuscar, clase);
        thenNoEncuentroLaClaseBuscada(mv);
    }

    @Test
    public void ConPlanBasicoNoSePuedeSacarMasDe1TurnoPorDia() throws Exception, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes, YaVencioElMesException {
        Cliente clienteConPlanBasico = givenUnClienteActivo(true, Plan.BASICO);
        ModelAndView mv = whenReservoMasDe1TurnoConPlanBasico(clienteConPlanBasico, mockDeHttpServletSession);
        thenReservoTurnoSaleExpecionDeCantidadMaximaDeTurnosPorDia(mv);
    }

    @Test
    public void ConPlanEstandarNoSePuedeSacarMasDe3TurnoPorDia() throws Exception, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes, YaVencioElMesException {
        Cliente cliente = givenUnClienteActivo(true, Plan.ESTANDAR);
        ModelAndView mv = whenReservoMasDe1TurnoConPlanBasico(cliente, mockDeHttpServletSession);
        thenReservoTurnoSaleExpecionDeCantidadMaximaDeTurnosPorDia(mv);
    }

    @Test
    public void ConPlanBasicoNoSePuedeSacarMasDe3TurnosPorSemana() throws Exception, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes, YaVencioElMesException {
        Cliente cliente = givenUnClienteActivo(true, Plan.ESTANDAR);
        ModelAndView mv = whenIntentoReservarMasDe3TurnosPorSemana(cliente, mockDeHttpServletSession);
        thenReservoTurnoTiraExcepcionDeCantidadMaximaPorSemana(mv);
    }

    @Test
    public void ConPlanEstandarNoSePuedeSacarMasDe6TurnosPorSemana() throws Exception, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaTienePagoRegistradoParaMismoMes, YaVencioElMesException {
        Cliente cliente = givenUnClienteActivo(true, Plan.ESTANDAR);
        ModelAndView mv = whenIntentoReservarMasDe6TurnosPorSemana(cliente, mockDeHttpServletSession);
        thenReservoTurnoTiraExcepcionDeCantidadMaximaPorSemana(mv);
    }

    @Test
    public void siEntraAVerUnMesQueNoTienePlanFallar() throws YaTienePagoRegistradoParaMismoMes, Exception, NoTienePlanParaVerLasClasesException {
        Cliente cliente = givenUnClienteActivoEnUnMesYNoEnElSiguiente();
        ModelAndView mv = whenIntentaVerMesDondeNoTieneUnPlanAsociado(cliente);
        thenNoMuestraLosMesesYAvisaQueNecesitaContratarUnPlanParaPoderVer(mv);
    }

    private Cliente givenUnClienteActivoEnUnMesYNoEnElSiguiente() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente("Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com", Collections.EMPTY_LIST);
        LocalDate mesMarzo = LocalDate.of(2021, 3, 1);
        cliente.agregarPago(new Pago(cliente, mesMarzo.getMonth(), mesMarzo.getYear(), Plan.BASICO));
        return cliente;
    }

    private String givenClaseABuscar(String claseABuscar) {
        return claseABuscar;
    }

    private Turno givenHayUnTurnoDeAyer(Cliente cliente) {
        Clase clase = new Clase();
        clase.setDiaClase(LocalDateTime.now().minusDays(1));
        return new Turno(cliente, clase , LocalDate.now().minusDays(5));
    }

    private Turno givenHayUnTurnoParaHoy() {
        Turno turno = givenHayUnTurno();
        Clase clase = new Clase();
        clase.setDiaClase(LocalDateTime.now());
        turno.setClase(clase);
        return turno;
    }

    private Turno givenUnClienteConUnTurno(Cliente cliente) {
        return new Turno(cliente, new Clase(), LocalDate.now());
    }

    private Cliente givenUnUsuarioSinTurnos() {
        return new Cliente();
    }

    private Turno givenHayUnTurno() {
        return new Turno();
    }

    private Cliente givenUnClienteActivo(boolean conPlan, Plan tipoPlan) throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente("Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com", Collections.EMPTY_LIST);
        if (conPlan) {
            LocalDate hoy = LocalDate.now();
            cliente.agregarPago(new Pago(cliente, hoy.getMonth(), hoy.getYear(), tipoPlan));
        }
        return cliente;
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

        return new Actividad("Actividad de alto impacto", Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo);
    }

    private Clase givenClaseConFechaAnterioAlDiaDeHoy() throws Exception {
        Clase clase =new Clase(LocalDateTime.now().minusDays(1), new Actividad(), Modalidad.PRESENCIAL);
        clase.setId(1L);
        return clase;
    }

    private ModelAndView whenReservoTurno(Long id,HttpServletRequest session, Long idUsuario) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaVencioElMesException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(idUsuario);
        when(session.getSession().getAttribute("plan")).thenReturn(Plan.BASICO);
        doNothing().when(turnoService).guardarTurno(id, idUsuario);
        return turnoController.reservarTurno(id, session);
    }

    private ModelAndView whenReservoTurnoSinLugar(Long id, HttpServletRequest session, Long idUsuario) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaVencioElMesException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(idUsuario);
        when(session.getSession().getAttribute("plan")).thenReturn(Plan.BASICO);
        doThrow(Exception.class).when(turnoService).guardarTurno(id, idUsuario);
        return turnoController.reservarTurno(id, session);
    }

    private ModelAndView whenBorroTurnoExistente(Turno turno, Cliente cliente, HttpServletRequest session) throws Exception, ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(cliente.getId());
        doNothing().when(turnoService).borrarTurno(turno.getId(),cliente.getId());
        return turnoController.borrarTurno(turno.getId(), session);
    }

    private ModelAndView whenConsultoElTurnoConUnUsuario(Turno turno, HttpServletRequest session,Long usuarioId) throws Exception {
        List<Turno> turnoCliente = List.of(turno);
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(usuarioId);
        when(turnoService.getTurnosByIdCliente(usuarioId)).thenReturn(turnoCliente);
        return turnoController.mostrarTurnoPorId(session);
    }

    private ModelAndView whenConsultoUsuarioSinTurnos(Long usuarioId, HttpServletRequest session) throws Exception {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(usuarioId);
        when(turnoService.getTurnosByIdCliente(usuarioId)).thenReturn(null);
        doThrow(Exception.class).when(turnoService).getTurnosByIdCliente(usuarioId);
        return turnoController.mostrarTurnoPorId(session);
    }

    private ModelAndView whenReservoTurnoConFechaAnteriorALaActual(Long idClase, HttpServletRequest session, Long idUsuario) throws LaClaseEsDeUnaFechaAnterioALaActualException, Exception, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaVencioElMesException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(idUsuario);
        when(session.getSession().getAttribute("plan")).thenReturn(Plan.BASICO);
        doThrow(LaClaseEsDeUnaFechaAnterioALaActualException.class).when(turnoService).guardarTurno(idClase, idUsuario);
        return turnoController.reservarTurno(idClase, session);
    }

    private ModelAndView whenBorroTurnoConUsuarioDistintoAlDelTurno(Turno turno, Cliente cliente2, HttpServletRequest session) throws ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(turno.getCliente().getId());
        doThrow(ElClienteNoCorrespondeAlTurnoException.class).when(turnoService).borrarTurno(turno.getId(), cliente2.getId());
        return turnoController.borrarTurno(turno.getId(), session);
    }

    private ModelAndView whenMuestroLosTurnos(Turno turno) throws Exception {
        when(clasesViewModelBuilder.getCalendarioCompleto(anyList(), any(), any())).thenReturn(new CalendarioDeActividades());
        when(turnoService.getTurnosParaHoy(anyLong())).thenReturn(List.of(turno));
        return turnoController.mostrarClasesParaSacarTurnos(Optional.empty(), Optional.empty(), Optional.empty(), mockSession);
    }

    private ModelAndView whenBorroTurnoDeAyer(Turno turno, Cliente cliente, HttpServletRequest session ) throws ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(cliente.getId());
        doThrow(TurnoExpiroException.class).when(turnoService).borrarTurno(turno.getId(),cliente.getId());

        return turnoController.borrarTurno(turno.getId(), session);
    }

    private ModelAndView whenReservoTurnoDeLaMismaClase(Long idTurno, HttpServletRequest session, Long idUsuario) throws LaClaseEsDeUnaFechaAnterioALaActualException, Exception, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaVencioElMesException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(idUsuario);
        when(session.getSession().getAttribute("plan")).thenReturn(Plan.BASICO);
        doThrow(YaHayTurnoDeLaMismaClaseException.class).when(turnoService).guardarTurno(idTurno, idUsuario);
        return turnoController.reservarTurno(idTurno, session);
    }

    private ModelAndView whenReservoTurnoSinPlan(Long idClase, HttpServletRequest session, Long idUsuario) throws Exception {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(idUsuario);
        when(session.getSession().getAttribute("plan")).thenReturn(Plan.NINGUNO);
        return turnoController.reservarTurno(idClase, session);
    }

    private ModelAndView whenBuscoLaClase(String claseABuscar, Clase clase) throws NoSeEncontroClaseConEseNombreException {
        when(turnoService.buscarClase(claseABuscar)).thenReturn(List.of(clase));
        doThrow(NoSeEncontroClaseConEseNombreException.class).when(turnoService).buscarClase(claseABuscar);
        return turnoController.buscarClase(claseABuscar);
    }

    private ModelAndView whenReservoMasDe1TurnoConPlanBasico(Cliente clienteConPlanBasico, HttpServletRequest session) throws YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, Exception, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaVencioElMesException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(clienteConPlanBasico.getId());
        when(session.getSession().getAttribute("plan")).thenReturn(Plan.BASICO);
        doThrow(SuPlanNoPermiteMasInscripcionesPorDiaException.class).when(turnoService).guardarTurno(1L, clienteConPlanBasico.getId());
        return turnoController.reservarTurno(1L, session);
    }

    private ModelAndView whenIntentoReservarMasDe6TurnosPorSemana(Cliente clienteConPlanBasico, HttpServletRequest session) throws YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, Exception, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaVencioElMesException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(clienteConPlanBasico.getId());
        when(session.getSession().getAttribute("plan")).thenReturn(Plan.ESTANDAR);
        doThrow(SuPlanNoPermiteMasInscripcionesPorSemanaException.class).when(turnoService).guardarTurno(1L, clienteConPlanBasico.getId());
        return turnoController.reservarTurno(1L, session);
    }

    private ModelAndView whenIntentoReservarMasDe3TurnosPorSemana(Cliente clienteConPlanBasico, HttpServletRequest session) throws YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, LaClaseEsDeUnaFechaAnterioALaActualException, SinPlanException, Exception, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaVencioElMesException {
        when(session.getSession()).thenReturn(mockSession);
        when(session.getSession().getAttribute("usuarioId")).thenReturn(clienteConPlanBasico.getId());
        when(session.getSession().getAttribute("plan")).thenReturn(Plan.BASICO);
        doThrow(SuPlanNoPermiteMasInscripcionesPorSemanaException.class).when(turnoService).guardarTurno(1L, clienteConPlanBasico.getId());
        return turnoController.reservarTurno(1L, session);
    }

    private ModelAndView whenIntentaVerMesDondeNoTieneUnPlanAsociado(Cliente cliente) throws Exception, NoTienePlanParaVerLasClasesException, YaTienePagoRegistradoParaMismoMes {
        when(mockDeHttpServletSession.getSession()).thenReturn(mockSession);
        when(mockDeHttpServletSession.getSession().getAttribute("usuarioId")).thenReturn(cliente.getId());
        doThrow(NoTienePlanParaVerLasClasesException.class).when(claseService).getClases(Optional.of(Mes.ABRIL), Optional.empty(), cliente.getId(), false);
        return turnoController.mostrarClasesParaSacarTurnos(Optional.of(Mes.ABRIL), Optional.empty(), Optional.empty(), mockSession);
    }

    private void thenReservoElTurnoCorrectamente(ModelAndView mv) {
        assertThat(mv.getModel().get("msgGuardado")).isEqualTo("Se guardo turno correctamente");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-turno");
    }

    private void thenNoPuedoReservarTurno(ModelAndView mv) {
        assertThat(mv.getModel().get("msg")).isEqualTo("Cupo m??ximo alcanzado");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-clases");
    }

    private void thenBorroTurno(ModelAndView mv) {
        assertThat(mv.getModel().get("msgBorrado")).isEqualTo("Se borro turno correctamente");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-turno");
    }

    private void thenMuestroLosTurnosDelUsuario(ModelAndView mv, Turno turno) {
        assertThat(mv.getViewName()).isEqualTo("Turnos");
        assertThat(mv.getModel().get("turnos")).isEqualTo(List.of(turno));
    }

    private void thenMuestroQueNoTieneTurnos(ModelAndView mv) {
        assertThat(mv.getModel().get("turnos")).isEqualTo(null);
        assertThat(mv.getModel().get("msg")).isEqualTo("No hay turnos disponibles");
    }

    private void thenElTurnoNoSeReseva(ModelAndView mv) {
        assertThat(mv.getModel().get("msg")).isEqualTo("La clase ya expiro");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-clases");
    }

    private void thenElTurnoNoSeBorraYRedirigeALaViewTurnos(ModelAndView mv) {
        assertThat(mv.getModel().get("msgUsuarioNoValido")).isEqualTo("El turno no corresponde al usuario");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-turno");
    }

    private void thenElTurnoSeMandaALaVista(ModelAndView mv, Turno turno) {
        assertThat(mv.getModel().get("turnosDelDia") == List.of(turno));
        assertThat(mv.getViewName().equals("clases-para-turnos"));
    }

    private void thenElTurnoNoSeBorraYSaleAdvertencia(ModelAndView mv) {
        assertThat(mv.getModel().get("msgTurnoExpiro")).isEqualTo("No se puede borrar turno porque es de una fecha anterior a la actual");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-turno");
    }

    private void thenNoPuedoReservarTurnoDeLaMismaClase(ModelAndView mv) {
        assertThat(mv.getModel().get("msgTurnoExistente")).isEqualTo("Ya reservaste turno para esta clase");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-clases");
    }

    private void thenRedirigeALaVistaDePlanes(ModelAndView mv) {
        assertThat(mv.getModel().get("msg")).isEqualTo("No tenes plan, por lo tanto no podes reservar turnos");
        assertThat(mv.getViewName()).isEqualTo("redirect:/planes");
    }

    private void thenEncuentroLaClaseBuscada(ModelAndView mv, Clase clase) {
        assertThat(mv.getModel().get("clasesBuscadas") == List.of(clase));
        assertThat(mv.getViewName()).isEqualTo("clase-buscada");
    }

    private void thenNoEncuentroLaClaseBuscada(ModelAndView mv) {
        assertThat(mv.getModel().get("claseNoEncontrada")).isEqualTo("No hay clases con ese Nombre");
        assertThat(mv.getViewName()).isEqualTo("clase-buscada");
    }

    private void thenReservoTurnoSaleExpecionDeCantidadMaximaDeTurnosPorDia(ModelAndView mv) {
        assertThat(mv.getModel().get("msg")).isEqualTo("Su plan no permite inscribirse a mas clases");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-clases");
    }

    private void thenReservoTurnoTiraExcepcionDeCantidadMaximaPorSemana(ModelAndView mv) {
        assertThat(mv.getModel().get("msg")).isEqualTo("Su plan no permite sacar mas clases por semana");
        assertThat(mv.getViewName()).isEqualTo("redirect:/mostrar-clases");
    }

    private void thenNoMuestraLosMesesYAvisaQueNecesitaContratarUnPlanParaPoderVer(ModelAndView mv) {
        assertThat(mv.getModel().get("msgError")).isEqualTo("Para poder ver las clases de este mes debe contratar un plan.");
        assertThat(mv.getModel().get("calendario")).isEqualTo(null);
    }
}