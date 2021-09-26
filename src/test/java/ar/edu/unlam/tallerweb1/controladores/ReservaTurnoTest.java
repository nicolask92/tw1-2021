package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.modelo.*;

import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import ar.edu.unlam.tallerweb1.servicios.TurnoService;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.mock;

public class ReservaTurnoTest {

    ClaseService claseService = mock(ClaseService.class);
    TurnoService turnoService = mock(TurnoService.class);
    TurnoController turnoController = new TurnoController(claseService, turnoService );


    @Test
    public void testQueSePuedaReservarTurno() throws Exception {
       Clase clase = givenQueLaClaseTengaLugar();
       Cliente cliente = givenUnClienteActivo();
       whenReservoTurno(clase);

    }

    private Cliente givenUnClienteActivo() {
        return new Cliente("Arturo" + LocalDateTime.now().toString(),
                "Frondizi", "arturitoElMasCapo@gmail.com");

    }

    private Clase givenQueLaClaseTengaLugar() throws Exception {
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

    private void whenReservoTurno(Clase clase) {
        turnoController.reservarTurno(clase.getId());

    }


}
