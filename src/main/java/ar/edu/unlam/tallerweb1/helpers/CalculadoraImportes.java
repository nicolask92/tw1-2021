package ar.edu.unlam.tallerweb1.helpers;

import ar.edu.unlam.tallerweb1.modelo.Plan;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;

public class CalculadoraImportes {

    private static final MathContext m = new MathContext(6);

    public static BigDecimal calcularSaldoAPagar(Plan planOrigen, Plan planAUpgradear, LocalDate fechaAPartirDeCualCalcular) {

        BigDecimal valorAPagar = new BigDecimal(0);

        if ( planOrigen.getNombre().equals("Basico") && planAUpgradear.getNombre().equals("Estandar") ||
                planOrigen.getNombre().equals("Basico") && planAUpgradear.getNombre().equals("Premium") ||
                planOrigen.getNombre().equals("Estandar") && planAUpgradear.getNombre().equals("Premium")
            ) {
            // primero saco cuando le sobra para este mes;
            BigDecimal valorYaConsumido = BigDecimal.valueOf((planOrigen.getPrecio() / fechaAPartirDeCualCalcular.lengthOfMonth()) * fechaAPartirDeCualCalcular.getDayOfMonth());
            // hago la diferencia del nuevo Plan con lo que ya pago del plan anterior;
            valorAPagar = BigDecimal.valueOf(planAUpgradear.getPrecio()).subtract(valorYaConsumido);
        }

        return valorAPagar.round(m);
    }
}
