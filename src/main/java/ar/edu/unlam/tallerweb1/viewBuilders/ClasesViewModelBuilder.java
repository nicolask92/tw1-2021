package ar.edu.unlam.tallerweb1.viewBuilders;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.controladores.CalendarioDeActividades;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.*;

@Component
public class ClasesViewModelBuilder {

    ClasesViewModelBuilder() {}

    public CalendarioDeActividades getCalendarioCompleto(List<Clase> clases, Optional<Mes> mes) {
        int cantMaxFechasMes = this.getMaximaFechaDelMes(mes);

        Map<Integer, List<Clase>> diaYClases = new HashMap<>();

        for (int i = 0; i < cantMaxFechasMes; i++) {
            int finalI = i;
            List<Clase> clasesParaEsteDia = (List<Clase>) clases.stream()
                    .filter(clase -> clase.getDiaClase().getDayOfMonth() == finalI )
                    .findAny()
                    .orElse(null);
            diaYClases.put(i, clasesParaEsteDia);
        }

        return new CalendarioDeActividades(diaYClases);
    }

    private int getMaximaFechaDelMes(Optional<Mes> mes) {
        if (mes.isPresent()) {
            int numeroDeMes = mes.get().ordinal();
            Calendar cal = Calendar.getInstance();
            cal.set(Year.now().getValue(), Month.of(numeroDeMes).getValue(), 1);
            return cal.getMaximum(Calendar.DAY_OF_MONTH);
        } else {
            Calendar cal = Calendar.getInstance();
            int numeroDeMes = LocalDate.now().getMonth().getValue();
            LocalDateTime primeroDiaDelMes = LocalDateTime.of(Year.now().getValue(), Month.of(numeroDeMes), 1, 0, 0, 0);
            return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }
}
