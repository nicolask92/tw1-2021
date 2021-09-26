package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Clase;

import java.util.List;

public interface TurnoRepositorio {
    List<Clase> getTurnosDeEsteMes();
    void guardarTurno(Long id);
}
