package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class PagoRepositorioTest extends SpringTest {

    @Autowired
    PagoRepositorio pagoRepositorio;

    @Transactional
    @Rollback
    @Test
    public void testQueHabiandoVariosPagosMeDevuelvaElUltimo() {
        Cliente cliente = givenUnCliente();
        givenTresPagosEnElMes(cliente);
        Pago pagoBuscado = whenBuscoElUltimoPago(cliente);
        thenConsigoElPago(pagoBuscado);
    }

    private void thenConsigoElPago(Pago pagoBuscado) {
        assert pagoBuscado.getPlan().equals(Plan.PREMIUM);
    }

    private Pago whenBuscoElUltimoPago(Cliente cliente) {
        return pagoRepositorio.getUltimoPagoContratadoParaEsteMesYActivo(cliente);
    }

    private Cliente givenUnCliente() {
        Cliente cliente = new Cliente("Arturo" + LocalDateTime.now(), "Frondizi", "arturitoElMasCapo@gmail.com", Collections.EMPTY_LIST);
        cliente.getContrataciones().stream().findFirst().get().cancelarPlan();
        session().save(cliente);
        return cliente;
    }

    private List<Pago> givenTresPagosEnElMes(Cliente cliente) {
        LocalDate hoy = LocalDate.now();
        Pago pagoEstandar = new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.ESTANDAR);
        Pago pagoBasico = new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.BASICO);
        Pago pagoPremium = new Pago(cliente, hoy.getMonth(), hoy.getYear(), Plan.PREMIUM);

        pagoEstandar.cancelarPlan();
        pagoBasico.cancelarPlan();

        session().save(pagoEstandar);
        session().save(pagoBasico);
        session().save(pagoPremium);

        return List.of(pagoBasico, pagoEstandar, pagoPremium);
    }
}