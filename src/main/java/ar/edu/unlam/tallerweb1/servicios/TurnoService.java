package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.*;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Turno;

import java.util.List;


public interface TurnoService {

    void guardarTurno(Long idClase, Long idUsuario) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException, YaVencioElMesException;

    List<Turno> getTurnosByIdCliente(Long id) throws Exception;

    void borrarTurno(Long idTurno, Long idCliente) throws ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException;

    List<Turno> getTurnosParaHoy(Long idCliente);

    List<Clase> buscarClase(String claseABuscar) throws NoSeEncontroClaseConEseNombreException;
}
