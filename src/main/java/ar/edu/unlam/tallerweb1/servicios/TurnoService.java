package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Turno;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TurnoService {
    List<Turno> getTurnosDeEsteMes();

    void guardarTurno(Long id, Long idUsuario);

    List<Turno> getTurnosPorId(Long id);
}
