package ar.edu.unlam.tallerweb1.helpers;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class CalculadoraImportesTest {

    @Test
    public void calcularDeUnPlanBasicoAUnoEstandarMeDaCorrectamente() {
        BigDecimal diferencia = CalculadoraImportes.calcularSaldoAPagar(Plan.BASICO, Plan.ESTANDAR, LocalDate.of(2021, 1, 12));
        assertEquals(BigDecimal.valueOf(3645.16), diferencia);
    }

    @Test
    public void calcularDeUnPlanBasicoAUnoPremiumMeDaCorrectamente() {
        BigDecimal diferencia = CalculadoraImportes.calcularSaldoAPagar(Plan.BASICO, Plan.PREMIUM, LocalDate.of(2021, 1, 12));
        assertEquals(BigDecimal.valueOf(5645.16), diferencia);
    }

    @Test
    public void calcularDeUnPlanEstandarAUnoPremiumMeDaCorrectamente() {
        BigDecimal diferencia = CalculadoraImportes.calcularSaldoAPagar(Plan.ESTANDAR, Plan.PREMIUM, LocalDate.of(2021, 1, 12));
        assertEquals(BigDecimal.valueOf(5064.52), diferencia);
    }

    @Test
    public void calcularDeUnPlanSuperiorAUnoInferiorMeDaCeroPorqueNoSePuede() {
        BigDecimal diferencia = CalculadoraImportes.calcularSaldoAPagar(Plan.PREMIUM, Plan.ESTANDAR, LocalDate.of(2021, 1, 12));
        assertEquals(BigDecimal.valueOf(0), diferencia);
    }
}