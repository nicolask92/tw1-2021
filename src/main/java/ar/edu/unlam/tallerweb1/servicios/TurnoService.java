package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Exceptiones.ElClienteNoCorrespondeAlTurnoException;
import ar.edu.unlam.tallerweb1.Exceptiones.LaClaseEsDeUnaFechaAnterioALaActualException;
import ar.edu.unlam.tallerweb1.Exceptiones.TurnoExpiroException;
import ar.edu.unlam.tallerweb1.Exceptiones.YaHayTurnoDeLaMismaClaseException;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Turno;

import java.util.List;


public interface TurnoService {
    List<Turno> getTurnosDeEsteMes();

    void guardarTurno(Long idClase, Long idUsuario) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException;

    List<Turno> getTurnosByIdCliente(Long id) throws Exception;

    void borrarTurno(Long idTurno, Long idCliente) throws ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException;

    List<Turno> getTurnosParaHoy(Long idCliente);
}
