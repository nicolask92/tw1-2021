package ar.edu.unlam.tallerweb1.viewBuilders;

import org.springframework.util.Assert;

import java.util.List;


public class CalendarioDeActividades {
    private List<FechaYClases> fechaYClases;
    private List<String> conjuntoDias;

    public CalendarioDeActividades(List<FechaYClases> fechaYClases) {
        int cantidadDiasOriginal = (int) fechaYClases.stream().map(FechaYClases::getDia).count();
        int cantidadDiasUnique = (int) fechaYClases.stream().map(FechaYClases::getDia).distinct().count();
        Assert.isTrue(cantidadDiasOriginal == cantidadDiasUnique, "No se pueden tener dias repetidos");
        this.fechaYClases = fechaYClases;
    }

    public List<FechaYClases> getFechasYSusClases() {
        return fechaYClases;
    }

    public void setConjuntoDias(List<String> conjuntoDias) {
        this.conjuntoDias = conjuntoDias;
    }

    public List<String> getConjuntoDias() {
        return conjuntoDias;
    }
}
