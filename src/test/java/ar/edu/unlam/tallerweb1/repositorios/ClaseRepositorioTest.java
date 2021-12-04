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

    private Clase givenUnaClaseConLugarDisponible(String nombreActividad, int mes) throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoValidoYHorarioValido(nombreActividad, mes);
        return new Clase(LocalDateTime.of(2021, mes, 1, 20, 0), actividad, Modalidad.PRESENCIAL);
    }

    private Actividad givenUnaActividadConPeriodoValidoYHorarioValido(String nombreActividad, int mes) throws Exception {
        LocalDateTime principioAnio = LocalDateTime.of(2021, 1, 15, 20, 0).minusDays(2);
        LocalDateTime finDeAnio = LocalDateTime.of(2021, 12, 20, 20, 0);
        Periodo periodo = new Periodo(principioAnio, finDeAnio);

        return new Actividad(nombreActividad, Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo);
    }

    private List<Clase> givenTresClasesParaElMesActualYUnaElSiguiente() throws Exception {
        List<Clase> clasesEnEsteMes = crearClasesPorCriterios(3, 11);
        Clase claseDelMesSiguiente = crearClasesPorCriterios(1, 12).get(0);

        assert clasesEnEsteMes.size() == 3;

        List<Clase> clasesTotales = new ArrayList<>(clasesEnEsteMes);
        clasesTotales.add(claseDelMesSiguiente);

        return clasesTotales;
    }

    private List<Clase> crearClasesPorCriterios(int cantitdadClases, int mes) throws Exception {
        List<Clase> clases = new ArrayList<>();
        for (int i = 0; i < cantitdadClases; i++) {
            Clase clase = givenUnaClaseConLugarDisponible("clase"+i, mes);
            session().save(clase);
            clases.add(clase);
        }
        return clases;
    }

    private List<Clase> whenBuscoLaDemesSiguiente() {
        Optional<Mes> mesSiguiente = Optional.ofNullable(Mes.values[11]);
        return claseRepositorio.getClases(mesSiguiente);
    }

    private List<Clase> whenBuscoLaDemesActual() {
        return claseRepositorio.getClases(Optional.of(Mes.NOVIEMBRE));
    }

    private void thenLaCantidadDeClasesConseguidasEsCorrecta(List<Clase> clases, int cantClasesQueDeberianSer) {
        Assert.assertEquals(clases.size(), cantClasesQueDeberianSer);
    }
}