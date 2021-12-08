package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.common.Modalidad;
import ar.edu.unlam.tallerweb1.common.Tipo;
import ar.edu.unlam.tallerweb1.exceptiones.NoTienePlanParaVerLasClasesException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import org.hibernate.SessionFactory;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClaseServiceImplTest {

    SessionFactory sessionFactory = mock(SessionFactory.class);
    ClaseRepositorio claseRepositorio = mock(ClaseRepositorio.class);
    ClienteRepositorio clienteRepositorio = mock(ClienteRepositorio.class);

    private final ClaseService claseService = new ClaseServiceImpl(sessionFactory, claseRepositorio, clienteRepositorio);

    @Test
    public void getClasesDevuelveLasClasesDelMesSiTienePlan() throws Exception, YaTienePagoRegistradoParaMismoMes, NoTienePlanParaVerLasClasesException {
        Cliente cliente = givenUnClienteConPlanActivo();
        List<Clase> clasesDelMes = givenDosClasesParaElMes();
        List<Clase> clasesDevueltas = whenLePasoMesQueYaTengoContrado(cliente, clasesDelMes);
        thenMeDevuelveLasClasesQueHayEnEseMes(clasesDevueltas, clasesDelMes);
    }

    @Test(expected=NoTienePlanParaVerLasClasesException.class)
    public void getClasesNoDevuelveLasClasesDelMesSiNoTienePlan() throws YaTienePagoRegistradoParaMismoMes, NoTienePlanParaVerLasClasesException {
        Cliente cliente = givenUnClienteConPlanActivo();
        whenIntentaAccederALasClasesDisponiblesEnMesQueNoTienePlan(cliente);
    }

    private Cliente givenUnClienteConPlanActivo() throws YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = new Cliente( "Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com", Collections.EMPTY_LIST);
        cliente.setId(1L);
        LocalDate fecha = LocalDate.of(2021, 3, 1);
        Pago pago = new Pago(cliente, fecha.getMonth(), fecha.getYear(), Plan.ESTANDAR);
        pago.setDebitoAutomatico(true);
        cliente.agregarPago(pago);
        return cliente;
    }

    private List<Clase> givenDosClasesParaElMes() throws Exception {
        return List.of(givenClaseConLugar("Una clase"), givenClaseConLugar("otraClase"));
    }

    private Clase givenClaseConLugar(String nombre) throws Exception {
        Actividad actividad = givenUnaActividadConPeriodoYHorarioValido(nombre);
        return new Clase(LocalDateTime.of(2021, 3, 1, 20,0), actividad, Modalidad.PRESENCIAL);
    }

    private Actividad givenUnaActividadConPeriodoYHorarioValido(String nombre) {
        return new Actividad(nombre, Tipo.CROSSFIT, 4000f);
    }

    private void whenIntentaAccederALasClasesDisponiblesEnMesQueNoTienePlan(Cliente cliente) throws NoTienePlanParaVerLasClasesException, YaTienePagoRegistradoParaMismoMes {
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        claseService.getClases(Optional.of(Mes.ABRIL), Optional.of(LocalDate.now().getYear()), cliente.getId(), false);
    }

    private List<Clase> whenLePasoMesQueYaTengoContrado(Cliente cliente, List<Clase> clasesDelMes) throws NoTienePlanParaVerLasClasesException, YaTienePagoRegistradoParaMismoMes {
        when(clienteRepositorio.getById(cliente.getId())).thenReturn(cliente);
        when(claseRepositorio.getClases(Optional.of(Mes.MARZO))).thenReturn(clasesDelMes);
        return claseService.getClases(Optional.of(Mes.MARZO), Optional.of(LocalDate.now().getYear()), cliente.getId(), false);
    }

    private void thenMeDevuelveLasClasesQueHayEnEseMes(List<Clase> clasesDevueltas, List<Clase> clasesPersistidas) {
        assertThat(clasesDevueltas.size()).isEqualTo(2);
        assertThat(clasesPersistidas).isEqualTo(clasesDevueltas);
    }
}