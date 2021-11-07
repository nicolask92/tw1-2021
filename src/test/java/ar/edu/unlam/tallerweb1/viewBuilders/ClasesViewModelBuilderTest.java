package ar.edu.unlam.tallerweb1.viewBuilders;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.Actividad;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Periodo;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ClasesViewModelBuilderTest {

    ClasesViewModelBuilder clasesBuilder = new ClasesViewModelBuilder();

    @Test
    public void habiendoDosClasesMeRenderizaCorrectamente() throws Exception {
        List<Clase> clases = givenClases(2, true);
        CalendarioDeActividades calendario = whenRenderizoElMes(clases);
        thenElListadoDeClasesSeHaceCorrectamente(calendario);
    }

    @Test
    public void siNoHayClasesElTotalDeClasesADevolverDentroDelBuilderEsCero() throws Exception {
        givenCeroClasesEnElMes();
        CalendarioDeActividades calendario = whenRenderizoElMes(Collections.emptyList());
        thenLaCantidadDeClasesADevolverEsCero(calendario);
    }

    @Test
    public void siHayClasesParaElMesQueVieneNoSeMuestraNadaEnElMesActual() throws Exception {
        List<Clase> clases = givenClases(2, false);
        CalendarioDeActividades calendario = whenRenderizoElMes(clases);
        thenElListadoDeClasesEsDelMesQueViene(calendario);
    }

    boolean estaDentroDelRango(LocalDateTime dateTime, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return !(dateTime.isBefore(fechaInicio) || dateTime.isAfter(fechaFin));
    }

    @Test
    public void dosClasesEnDiferenteDiaSeRenderizaCorrectamente() throws Exception {
        List<Clase> clases = givenDasClasesEnDiferentesDias();
        CalendarioDeActividades calendario = whenRenderizoElMes(clases);
        thenElListadoDeClasesSeHaceCorrectamenteEnDiasDiferentes(calendario);
    }

    private List<Clase> givenDasClasesEnDiferentesDias() throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoValidoYHorarioValido("actividad", true);
        Clase clase1 = new Clase(LocalDateTime.now().minusDays(1), actividad, Modalidad.PRESENCIAL);
        Clase clase2 = new Clase(LocalDateTime.now().plusDays(1), actividad, Modalidad.PRESENCIAL);
        return List.of(clase1, clase2);
    }

    private void givenCeroClasesEnElMes() {
    }

    private List<Clase> givenClases(int i, boolean mesActual) throws Exception {
        return crearClasesPorCriterios(i, mesActual);
    }

    private Clase givenUnaClaseConLugarDisponible(String nombreActividad, boolean mesActual) throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoValidoYHorarioValido(nombreActividad, mesActual);
        return new Clase(mesActual ? LocalDateTime.now() : LocalDateTime.now().plusMonths(1), actividad, Modalidad.PRESENCIAL);
    }

    private Actividad givenUnaActividadConPeriodoValidoYHorarioValido(String nombreActividad, boolean mesActual) throws Exception {
        LocalDateTime antesDeAyer = LocalDateTime.now().minusDays(2);
        LocalDateTime pasadoManiana = mesActual ? LocalDateTime.now().plusDays(2) : LocalDateTime.now().plusMonths(2);
        Periodo periodo = new Periodo(antesDeAyer, pasadoManiana);

        return new Actividad(nombreActividad, Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo);
    }


    private CalendarioDeActividades whenRenderizoElMes(List<Clase> clases) throws Exception {
        return clasesBuilder.getCalendarioCompleto(clases, Optional.empty());
    }

    private void thenLaCantidadDeClasesADevolverEsCero(CalendarioDeActividades calendario) {
        int tamanioDeClase = calcularClasesEnCalendario(calendario);
        assertEquals(tamanioDeClase, 0);
    }

    private void thenElListadoDeClasesSeHaceCorrectamenteEnDiasDiferentes(CalendarioDeActividades calendario) {
        int tamanioDeClasesEnCalendario = calcularClasesEnCalendario(calendario);
        assertEquals(2, tamanioDeClasesEnCalendario);

        int hoy = LocalDateTime.now().getDayOfMonth();

        FechaYClases fechaHoyYSusClasesAyer = calendario.getFechasYSusClases()
                .stream()
                .filter(fechaYClase -> fechaYClase.getDia() == (hoy - 1))
                .findFirst()
                .orElse(null);
        assert fechaHoyYSusClasesAyer != null;
        assertEquals(fechaHoyYSusClasesAyer.getClases().size(), 1);

        FechaYClases fechaHoyYSusClasesMañana = calendario.getFechasYSusClases()
                .stream()
                .filter(fechaYClase -> fechaYClase.getDia() == (hoy + 1))
                .findFirst()
                .orElse(null);
        assert fechaHoyYSusClasesMañana != null;
        assertEquals(1, fechaHoyYSusClasesMañana.getClases().size());
    }

    private void thenElListadoDeClasesSeHaceCorrectamente(CalendarioDeActividades calendario) {
        int tamanioDeClase = calcularClasesEnCalendario(calendario);
        int hoy = LocalDateTime.now().getDayOfMonth();
        assertEquals(2, tamanioDeClase);
        assertEquals(Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH), calendario.getFechasYSusClases().size());
        FechaYClases fechaHoyYSusClases = calendario.getFechasYSusClases()
                .stream()
                .filter(fechaYClase -> fechaYClase.getDia() == hoy)
                .findFirst()
                .orElse(null);
        assertEquals(fechaHoyYSusClases.getClases().size(), 2);
    }

    private void thenElListadoDeClasesEsDelMesQueViene(CalendarioDeActividades calendario) {
        int tamanioDeClasesMes = calcularClasesEnCalendario(calendario);
        assertEquals(2, tamanioDeClasesMes);

        int ultimoDiaDeEsteMes = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime finDeMes = LocalDateTime.now().withDayOfMonth(ultimoDiaDeEsteMes);

        boolean hayClasesEnEsteMes = calendario.getFechasYSusClases()
                .stream()
                .anyMatch(fechaYClase -> fechaYClase.getClases().stream().anyMatch( clase -> estaDentroDelRango(clase.getDiaClase(), inicioMes, finDeMes)
                ));
        assertFalse(hayClasesEnEsteMes);
    }

    private List<Clase> crearClasesPorCriterios(int cantitdadClases, boolean mesActual) throws Exception {
        List<Clase> clases = new ArrayList<>();
        for (int i = 0; i < cantitdadClases; i++) {
            Clase clase = givenUnaClaseConLugarDisponible("clase"+i, mesActual);
            clases.add(clase);
        }
        return clases;
    }

    private int calcularClasesEnCalendario(CalendarioDeActividades calendarioDeActividades) {
        return (int) calendarioDeActividades.getFechasYSusClases()
                .stream()
                .map( FechaYClases::getClases )
                .collect(Collectors.toList())
                .stream().flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .count();
    }
}