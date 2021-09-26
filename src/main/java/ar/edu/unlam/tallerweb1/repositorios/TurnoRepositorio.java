package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

import java.util.List;

public interface TurnoRepositorio {
    List<Turno> getTurnosPorId(Usuario user);

    List<Clase> getTurnosDeEsteMes();
    void guardarTurno(Long id);
}
