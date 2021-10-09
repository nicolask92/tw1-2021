package ar.edu.unlam.tallerweb1.viewBuilders;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.controladores.CalendarioDeActividades;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ClasesViewModelBuilder {

    final static Calendar cal = Calendar.getInstance();

    ClasesViewModelBuilder() {}

    // TODO encapsular funcionamiento
    public CalendarioDeActividades getCalendarioCompleto(List<Clase> clases, Optional<Mes> mes) throws Exception {
        int numeroDeMes = mes.map(Enum::ordinal).orElseGet(() -> cal.get(Calendar.MONTH));

        // arreglar el numero de mes, usar la constante de Calender.
        int anio = cal.get(Calendar.YEAR);
        cal.set(anio, numeroDeMes, 1);

        Map<Integer, List<Clase>> diaYClases = new HashMap<>();

        for (int i = 0; i < cal.getMaximum(Calendar.DAY_OF_MONTH); i++) {
            int finalI = i;
            List<Clase> clasesParaEsteDia = clases.stream()
                    .filter(clase -> clase.getDiaClase().getDayOfMonth() == finalI )
                    .collect(Collectors.toList());
            diaYClases.put(i, clasesParaEsteDia);
        }
        CalendarioDeActividades calendarioYClases = new CalendarioDeActividades(diaYClases);
        calendarioYClases.setConjuntoDias(this.generarListaDeDias());
        return calendarioYClases;
    }

    private List<String> generarListaDeDias() throws Exception {
        List<String> dias = new ArrayList<>();
        Date date = cal.getTime();
        String diaEnString = new SimpleDateFormat("EEEE").format(date);
        if (diaEnString.equals("lunes")) {
            dias.add("Lunes");
            dias.add("Martes");
            dias.add("Miercoles");
            dias.add("Jueves");
            dias.add("Viernes");
            dias.add("Sabado");
            dias.add("Domingo");
        } else if (diaEnString.equals("martes")) {
            dias.add("Martes");
            dias.add("Miercoles");
            dias.add("Jueves");
            dias.add("Viernes");
            dias.add("Sabado");
            dias.add("Domingo");
            dias.add("Lunes");
        } else if (diaEnString.equals("miércoles")) {
            dias.add("Miercoles");
            dias.add("Jueves");
            dias.add("Viernes");
            dias.add("Sabado");
            dias.add("Domingo");
            dias.add("Lunes");
            dias.add("Martes");
        } else if (diaEnString.equals("jueves")) {
            dias.add("Jueves");
            dias.add("Viernes");
            dias.add("Sabado");
            dias.add("Domingo");
            dias.add("Lunes");
            dias.add("Martes");
            dias.add("Miercoles");
        } else if (diaEnString.equals("viernes")) {
            dias.add("Viernes");
            dias.add("Sabado");
            dias.add("Domingo");
            dias.add("Lunes");
            dias.add("Martes");
            dias.add("Miercoles");
            dias.add("Jueves");
        } else if (diaEnString.equals("sabado")) {
            dias.add("Sabado");
            dias.add("Domingo");
            dias.add("Lunes");
            dias.add("Martes");
            dias.add("Miercoles");
            dias.add("Jueves");
            dias.add("Viernes");
        } else if (diaEnString.equals("domingo")) {
            dias.add("Domingo");
            dias.add("Lunes");
            dias.add("Martes");
            dias.add("Miercoles");
            dias.add("Jueves");
            dias.add("Viernes");
            dias.add("Sabado");
        } else {
            throw new Exception("Sos un asco programando amigo, mira lo que hiciste ahi arriba, el dia es=" + diaEnString);
        }
        return dias;
    }
}
