package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.common.Tipo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClaseRepositorioTest extends SpringTest {

    @Autowired
    private ClaseRepositorio claseRepositorio;

    @Test
    @Rollback @Transactional
    public void getClasesDelMesActualTraeLasClasesDeEseMes() throws Exception {
        givenTresClasesParaElMesActualYUnaElSiguiente();
        List<Clase> clasesEncontradas = whenBuscoLaDemesActual();
        thenLaCantidadDeClasesConseguidasEsCorrecta(clasesEncontradas, 3);
    }

    @Test
    @Rollback @Transactional
    public void getClasesDeUnMesParticularTraeLasClasesDeEseMes() throws Exception {
        givenTresClasesParaElMesActualYUnaElSiguiente();
        List<Clase> clasesEncontradas = whenBuscoLaDemesSiguiente();
        thenLaCantidadDeClasesConseguidasEsCorrecta(clasesEncontradas, 1);
    }

    private Clase givenUnaClaseConLugarDisponible(String nombreActividad, boolean mesActual) throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoValidoYHorarioValido(nombreActividad, mesActual);
        return new Clase(mesActual ? LocalDateTime.now() : LocalDateTime.now().plusMonths(1), actividad, Modalidad.PRESENCIAL);
    }

    private Actividad givenUnaActividadConPeriodoValidoYHorarioValido(String nombreActividad, boolean mesActual) throws Exception {
        LocalDateTime antesDeAyer = LocalDateTime.now().minusDays(2);
        LocalDateTime pasadoManiana = mesActual ? LocalDateTime.now().plusDays(2) : LocalDateTime.now().plusMonths(1);
        Periodo periodo = new Periodo(antesDeAyer, pasadoManiana);

        return new Actividad(nombreActividad, Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo);
    }

    private List<Clase> givenTresClasesParaElMesActualYUnaElSiguiente() throws Exception {
        List<Clase> clasesEnEsteMes = crearClasesPorCriterios(3, true);
        Clase claseDelMesSiguiente = crearClasesPorCriterios(1, false).get(0);

        assert clasesEnEsteMes.size() == 3;

        List<Clase> clasesTotales = new ArrayList<>(clasesEnEsteMes);
        clasesTotales.add(claseDelMesSiguiente);

        return clasesTotales;
    }

    private List<Clase> crearClasesPorCriterios(int cantitdadClases, boolean mesActual) throws Exception {
        List<Clase> clases = new ArrayList<>();
        for (int i = 0; i < cantitdadClases; i++) {
            Clase clase = givenUnaClaseConLugarDisponible("clase"+i, mesActual);
            session().save(clase);
            clases.add(clase);
        }
        return clases;
    }

    private List<Clase> whenBuscoLaDemesSiguiente() {
        Optional<Mes> mesSiguiente = Optional.ofNullable(Mes.values[LocalDate.now().getMonth().getValue() + 1]);
        return claseRepositorio.getClases(mesSiguiente);
    }

    private List<Clase> whenBuscoLaDemesActual() {
        return claseRepositorio.getClases(java.util.Optional.empty());
    }

    private void thenLaCantidadDeClasesConseguidasEsCorrecta(List<Clase> clases, int cantClasesQueDeberianSer) {
        Assert.assertEquals(clases.size(), cantClasesQueDeberianSer);
    }
}