package ar.edu.unlam.tallerweb1.viewBuilders;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ClasesViewModelBuilder {

    ClasesViewModelBuilder() {}

    public CalendarioDeActividades getCalendarioCompleto(List<Clase> clases, Optional<Mes> mes) throws Exception {

        Calendar calendario = setearCalendario(mes);

        return armarCalendario(calendario, clases);
    }

    private CalendarioDeActividades armarCalendario(Calendar cal, List<Clase> clases) throws Exception {
        List<FechaYClases> fechaYClases = new ArrayList<>();

        for (int numeroDelDiaDelMes = 0; numeroDelDiaDelMes < cal.getActualMaximum(Calendar.DAY_OF_MONTH); numeroDelDiaDelMes++) {
            int finalNumeroDelDiaDelMes = numeroDelDiaDelMes;
            List<Clase> clasesParaEsteDia = clases.stream()
                    .filter(clase -> (clase.getDiaClase().getDayOfMonth()-1) == finalNumeroDelDiaDelMes )
                    .collect(Collectors.toList());

            fechaYClases.add(new FechaYClases(numeroDelDiaDelMes + 1, esDomingo(numeroDelDiaDelMes, cal), clasesParaEsteDia));
        }

        CalendarioDeActividades calendarioYClases = new CalendarioDeActividades(fechaYClases);
        calendarioYClases.setConjuntoDias(this.generarListaDeDias(cal));
        return calendarioYClases;
    }

    private boolean esDomingo(int diaDelMes, Calendar calendario) {
        LocalDate fecha = LocalDate.of(calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH) + 1, diaDelMes+1);
        return fecha.getDayOfWeek().getValue() == 7;
    }

    private Calendar setearCalendario(Optional<Mes> mes) {
        Calendar calendario = Calendar.getInstance();
        int numeroDeMes = getNumeroDeMes(mes, calendario);
        int anioActual = calendario.get(Calendar.YEAR);
        if (mes.isPresent()) {
            calendario.set(anioActual, numeroDeMes - 1, 1);
        } else {
            calendario.set(anioActual, numeroDeMes, 1);
        }
        return calendario;
    }

    private int getNumeroDeMes(Optional<Mes> mes, Calendar calendario) {
        if (mes.isPresent()) {
            return mes.get().getNumeroDelMes();
        } else {
            return calendario.get(Calendar.MONTH);
        }
    }

    private List<String> generarListaDeDias(Calendar calendario) throws Exception {
        List<String> dias = new ArrayList<>();
        Date diaYFecha = calendario.getTime();
        LocalDate diaYFechaEnLocalDate = LocalDate.ofInstant(diaYFecha.toInstant(), ZoneId.systemDefault());
        int primerDiaDelMes = diaYFechaEnLocalDate.getDayOfWeek().ordinal() + 1;
        switch (primerDiaDelMes) {
            case 1:
                dias.add("Lunes");
                dias.add("Martes");
                dias.add("Miercoles");
                dias.add("Jueves");
                dias.add("Viernes");
                dias.add("Sabado");
                dias.add("Domingo");
                break;
            case 2:
                dias.add("Martes");
                dias.add("Miercoles");
                dias.add("Jueves");
                dias.add("Viernes");
                dias.add("Sabado");
                dias.add("Domingo");
                dias.add("Lunes");
                break;
            case 3:
                dias.add("Miercoles");
                dias.add("Jueves");
                dias.add("Viernes");
                dias.add("Sabado");
                dias.add("Domingo");
                dias.add("Lunes");
                dias.add("Martes");
                break;
            case 4:
                dias.add("Jueves");
                dias.add("Viernes");
                dias.add("Sabado");
                dias.add("Domingo");
                dias.add("Lunes");
                dias.add("Martes");
                dias.add("Miercoles");
                break;
            case 5:
                dias.add("Viernes");
                dias.add("Sabado");
                dias.add("Domingo");
                dias.add("Lunes");
                dias.add("Martes");
                dias.add("Miercoles");
                dias.add("Jueves");
                break;
            case 6:
                dias.add("Sabado");
                dias.add("Domingo");
                dias.add("Lunes");
                dias.add("Martes");
                dias.add("Miercoles");
                dias.add("Jueves");
                dias.add("Viernes");
                break;
            case 7:
                dias.add("Domingo");
                dias.add("Lunes");
                dias.add("Martes");
                dias.add("Miercoles");
                dias.add("Jueves");
                dias.add("Viernes");
                dias.add("Sabado");
                break;
            default:
                throw new Exception("No hay dia con ese nombre, dia=");
        }
        return dias;
    }
}
