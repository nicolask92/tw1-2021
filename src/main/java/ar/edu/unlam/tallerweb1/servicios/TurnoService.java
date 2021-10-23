package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.Exceptiones.ElClienteDelNoCorrespondeAlTurnoException;
import ar.edu.unlam.tallerweb1.Exceptiones.LaClaseEsDeUnaFechaAnterioALaActualException;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TurnoService {
    List<Turno> getTurnosDeEsteMes();

    void guardarTurno(Long idClase, Long idUsuario) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException;

    List<Turno> getTurnosByIdCliente(Long id) throws Exception;

    void borrarTurno(Long idTurno, Long idCliente) throws ElClienteDelNoCorrespondeAlTurnoException;
}
