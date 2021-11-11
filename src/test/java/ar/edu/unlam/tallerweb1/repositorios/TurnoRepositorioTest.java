package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.*;
import org.junit.Assert;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TurnoRepositorioTest extends SpringTest {

    @Autowired
    TurnoRepositorio turnoRepositorio;
    @Autowired
    ClaseRepositorio claseRepositorio;

    @Test
    @Rollback
    @Transactional
    public void queSePuedaEliminarUnTurno() throws Exception {
        Turno turno = givenQueHayUnTurno(1, true);
        whenQuieroEliminarElTurno(turno);
        thenElTurnoYaNoExiste(turno);
    }

    @Test
    @Rollback
    @Transactional
    public void queSiHayDosTurnosHoyDevuelveLosTurnosDelClienteQueEstoyConsultando() {
        List<Turno> turnosParaHoy = givenTurnos(2, true);
        List<Turno> turnosDeAyer = givenTurnos(2, false);
        Cliente clienteAConsultarTurnos = turnosParaHoy.stream().findFirst().get().getCliente();

        List<Turno> turnosConseguidos = whenConsultoTurnosParaElCliente(clienteAConsultarTurnos);
        thenDevuelveLosTurnosParaHoy(turnosParaHoy, turnosConseguidos, turnosDeAyer);
    }

    @Test
    @Rollback
    @Transactional
    public void testBuscarClasesYQueSeEncuentren() throws Exception {
        List<Clase> clases = givenClases(2, true);
        List<Clase> clasesEncontradas = whenBuscoClases();
        thenEncuntroLasClases(clases, clasesEncontradas);
    }

    private void thenEncuntroLasClases(List<Clase> clases, List<Clase> clasesEncontradas) {
        assertThat(clases.size()).isEqualTo(clasesEncontradas.size());
    }

    private List<Clase> whenBuscoClases() {
        return turnoRepositorio.buscarClases("ossf");
    }

    private List<Clase> givenClases(int cantitdadClases, boolean mesActual) throws Exception {
        List<Clase> clases = new ArrayList<>();
        for (int i = 0; i < cantitdadClases; i++) {
            Clase clase = givenUnaClaseConLugarDisponible("Crossfit", mesActual);
            session().save(clase);
            clases.add(clase);
        }
        return clases;
    }
    private Clase givenUnaClaseConLugarDisponible(String nombreActividad, boolean mesActual) throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoValidoYHorarioValido(nombreActividad, mesActual);
        return new Clase(mesActual ? LocalDateTime.now().plusHours(5) : LocalDateTime.now().plusMonths(1), actividad, Modalidad.PRESENCIAL);
    }
    private Actividad givenUnaActividadConPeriodoValidoYHorarioValido(String nombreActividad, boolean mesActual) throws Exception {
        LocalDateTime antesDeAyer = LocalDateTime.now().minusDays(2);
        LocalDateTime pasadoManiana = mesActual ? LocalDateTime.now().plusDays(2) : LocalDateTime.now().plusMonths(1);
        Periodo periodo = new Periodo(antesDeAyer, pasadoManiana);

        return new Actividad(nombreActividad, Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo);
    }

    private void thenDevuelveLosTurnosParaHoy(List<Turno> turnosParaHoy, List<Turno> turnosConseguidos, List<Turno> turnosdeAyer) {
        assert turnosConseguidos.size() == 1;
        Assert.assertTrue(turnosParaHoy.containsAll(turnosConseguidos));
        Assert.assertFalse(turnosdeAyer.containsAll(turnosConseguidos));
    }

    private List<Turno> whenConsultoTurnosParaElCliente(Cliente cliente) {
        return turnoRepositorio.getTurnosParaHoy(cliente);
    }

    private List<Turno> givenTurnos(int cantidadTurnosACrear, boolean paraHoy) {
        List<Turno> turnos = new ArrayList<>();
        for (int i = 0; i < cantidadTurnosACrear; i++) {
            turnos.add(givenQueHayUnTurno(i, paraHoy));
        }
        return turnos;
    }

    private Turno givenQueHayUnTurno(int iteracion, boolean paraHoy) {
        Cliente cliente = new Cliente();
        cliente.setApellido("Locaso" + iteracion);
        cliente.setNombre("Jorge");
        session().save(cliente);

        Clase clase = new Clase();
        clase.setDiaClase(paraHoy ? LocalDateTime.now() : LocalDateTime.now().minusDays(1));
        session().save(clase);

        Turno turno = new Turno(cliente, clase, LocalDate.now().minusDays(1));
        session().save(turno);
        return turno;
    }

    private void whenQuieroEliminarElTurno(Turno turno) {
        turnoRepositorio.borrarTurno(turno);
    }

    private void thenElTurnoYaNoExiste(Turno turno) throws Exception {
        Assert.assertNotNull(claseRepositorio.getById(turno.getClase().getId()));

        Assert.assertNull(turnoRepositorio.getTurnoById(turno.getId()));
    }

}