package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.exceptiones.NoTienePlanParaVerLasClasesException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Clase;

import java.util.List;
import java.util.Optional;

public interface ClaseService {
    List<Clase> getClases(Optional<Mes> mes, Optional<Integer> anio, Long idUsuario, boolean habilitarPruebaDebito) throws NoTienePlanParaVerLasClasesException, YaTienePagoRegistradoParaMismoMes;

    Clase buscarClasePorId(Long id);
}
