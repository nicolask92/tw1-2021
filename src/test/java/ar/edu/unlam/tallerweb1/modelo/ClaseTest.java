package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.common.Frecuencia;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import org.junit.Test;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ClaseTest {

    @Test
    public void crearClaseConActividadConFrencuenciaRepitivaLoCreaCorrectamente() throws Exception {
        Actividad actividad = mock(Actividad.class);

        Clase clase = new Clase(LocalDateTime.now(), actividad, Modalidad.ONLINE);

        Assert.notNull(clase);
    }

    @Test
    public void crearClaseConActividadConFrecuenciaInicialYFinalPoniendoUnaFechaValidaCreaLaClaseCorrectamente() throws Exception {
        Actividad actividadValida = givenUnaActividadConPeriodoValidoYHorarioValido();

        Clase clase = whenCreoLaClaseConActividad(actividadValida);

        thenLaClaseSeCreaComoDiosManda(clase);
    }

    @Test
    public void agregarClienteAClaseSiHayLugarLoHaceCorrectamente() throws Exception {
        Clase clase = givenUnaClaseConLugarDisponible();
        Cliente cliente = givenUnaCantidadDeClientesComunesYCorrientes(1).get(0);
        whenAgregoAClienteAClaseSeSumaALosClientes(clase, cliente);
        thenLaListaDeClientesAumentaEnUno(clase, cliente);
    }

    @Test (expected = Exception.class)
    public void agregarAClienteAClaseCompletaSaltaExcepcion() throws Exception {
        Clase clase = givenUnaClaseSinLugarDisponible();
        Cliente cliente = givenUnaCantidadDeClientesComunesYCorrientes(1).get(0);
        whenAgregoClienteAClaseCompletaExplota(clase, cliente);
    }

    @Test(expected = Exception.class)
    public void crearClaseConActividadConFrencuenciaInicialYFinalPoniendoFechaFueraDeDondeSeRealizaLaActividadYSiendoPresencialFalla() throws Exception {
        Actividad actividadInvalida = givenUnaActividadConPeriodoValidoYHorarioValido();

        whenCreoLaClaseConActividadConHorarioInvalido(actividadInvalida);
    }

    private void whenAgregoClienteAClaseCompletaExplota(Clase clase, Cliente cliente) throws Exception {
        clase.agregarCliente(cliente);
    }

    private Clase givenUnaClaseSinLugarDisponible() throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoValidoYHorarioValido();
        Clase clase = new Clase(LocalDateTime.now(), actividad, Modalidad.PRESENCIAL);
        clase.setCantidadMaxima(1);

        Cliente cliente = givenUnaCantidadDeClientesComunesYCorrientes(1).get(0);
        clase.agregarCliente(cliente);

        return clase;
    }

    private void thenLaListaDeClientesAumentaEnUno(Clase clase, Cliente cliente) {
        assertThat(cliente).isIn(clase.getClientes());
    }

    private void whenAgregoAClienteAClaseSeSumaALosClientes(Clase clase, Cliente cliente) throws Exception {
        clase.agregarCliente(cliente);
    }

    private List<Cliente> givenUnaCantidadDeClientesComunesYCorrientes(int cantidadClientes) {
        List<Cliente> clientes = new ArrayList<>();
        for (int i = 0; i < cantidadClientes; i++) {
            clientes.add(new Cliente("Arturo" + LocalDateTime.now().toString(), "Frondizi", "arturitoElMasCapo@gmail.com", Collections.EMPTY_LIST));
        }
        return clientes;
    }

    private Clase givenUnaClaseConLugarDisponible() throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoValidoYHorarioValido();
        return new Clase(LocalDateTime.now(), actividad, Modalidad.PRESENCIAL);
    }

    private void whenCreoLaClaseConActividadConHorarioInvalido(Actividad actividadInvalida) throws Exception {
        new Clase(LocalDateTime.now().plusDays(3), actividadInvalida, Modalidad.PRESENCIAL);
    }

    private Actividad givenUnaActividadConPeriodoValidoYHorarioValido() throws Exception {
        LocalDateTime antesDeAyer = LocalDateTime.now().minusDays(2);
        LocalDateTime pasadoManiana = LocalDateTime.now().plusDays(2);
        Periodo periodo = new Periodo(antesDeAyer, pasadoManiana);

        LocalTime ahora = LocalTime.now();
        LocalTime enUnRato = LocalTime.now().plusHours(2);
    //    Horario horario = new Horario(ahora, enUnRato);

        return new Actividad("Actividad de alto impacto", Tipo.CROSSFIT, 4000f, Frecuencia.CON_INICIO_Y_FIN, periodo);
    }

    private Clase whenCreoLaClaseConActividad(Actividad actividadValida) throws Exception {
        return new Clase(LocalDateTime.now(), actividadValida, Modalidad.ONLINE);
    }

    private void thenLaClaseSeCreaComoDiosManda(Clase clase) {
        assertThat(clase).isNotNull();
    }
}