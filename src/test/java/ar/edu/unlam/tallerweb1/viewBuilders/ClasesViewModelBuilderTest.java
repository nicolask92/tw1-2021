package ar.edu.unlam.tallerweb1.viewBuilders;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.Actividad;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Horario;
import ar.edu.unlam.tallerweb1.modelo.Periodo;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
        
    }

    @Test
    public void dosClasesEnDiferenteDiaSeRenderizaCorrectamente() {

    }

    private void givenCeroClasesEnElMes() {
    }

    private void thenElListadoDeClasesSeHaceCorrectamente(CalendarioDeActividades calendario) {
        int tamanioDeClase = calcularClasesEnCalendario(calendario);
        int hoy = LocalDateTime.now().getDayOfMonth();
        assertEquals(2, tamanioDeClase);
        assertEquals(Calendar.getInstance().getMaximum(Calendar.DAY_OF_MONTH), calendario.getFechasYSusClases().size());
        FechaYClases fechaHoyYSusClases = calendario.getFechasYSusClases()
                .stream()
                .filter(fechaYClase -> fechaYClase.getDia() == hoy)
                .findFirst()
                .orElse(null);
        assertEquals(fechaHoyYSusClases.getClases().size(), 2);
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
        LocalDateTime pasadoManiana = mesActual ? LocalDateTime.now().plusDays(2) : LocalDateTime.now().plusMonths(1);
        Periodo periodo = new Periodo(antesDeAyer, pasadoManiana);

        LocalTime ahora = LocalTime.now();
        LocalTime enUnRato = LocalTime.now().plusHours(2);
        Horario horario = new Horario(ahora, enUnRato);

        return new Actividad(nombreActividad, Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo);
    }


    private CalendarioDeActividades whenRenderizoElMes(List<Clase> clases) throws Exception {
        return clasesBuilder.getCalendarioCompleto(clases, Optional.empty());
    }

    private void thenLaCantidadDeClasesADevolverEsCero(CalendarioDeActividades calendario) {
        int tamanioDeClase = calcularClasesEnCalendario(calendario);
        assertEquals(tamanioDeClase, 0);
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