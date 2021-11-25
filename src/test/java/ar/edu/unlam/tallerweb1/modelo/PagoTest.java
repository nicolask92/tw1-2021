package ar.edu.unlam.tallerweb1.modelo;

import org.junit.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.*;

public class PagoTest {

    @Test
    public void queDosPagosConIgualClienteMesAnioYPlanSonIguales() {
        Cliente cliente = givenUnCliente();
        Pago pago = givenUnPago(cliente);
        Pago pago2 = givenUnPago(cliente);
        boolean sonIguales = whenComparoLosDosPagos(pago, pago2);
        thenLosPagosSonIguales(sonIguales);
    }

    @Test
    public void queSeCreeUnPagoSeteaBienLaFechaDeFinalizacion() {
        Cliente cliente = givenUnCliente();
        Pago pago = givenUnPagoConMesYAñoEspecifico(cliente);
        thenElPagoTieneFechaCorrecta(pago);
    }

    private void thenElPagoTieneFechaCorrecta(Pago pago) {
        assertEquals(LocalDate.of(2021, 12, 31), pago.getFechaDeFinalizacionEnLocalDate());
    }

    private Pago givenUnPagoConMesYAñoEspecifico(Cliente cliente) {
        final LocalDate hoy = LocalDate.of(2021, 12, 1);
        return new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.BASICO);
    }

    private Pago givenUnPago(Cliente cliente) {
        final LocalDate hoy = LocalDate.now();
        return new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.BASICO);
    }

    private Cliente givenUnCliente() {
        Cliente cliente = new Cliente("Amigdala", "Perez", "cluas@gmail.com", Collections.emptyList());
        cliente.setId(1L);
        return cliente;
    }

    private boolean whenComparoLosDosPagos(Pago pago, Pago pago2) {
        return pago.equals(pago2);
    }

    private void thenLosPagosSonIguales(boolean sonIguales) {
        assertTrue(sonIguales);
    }
}