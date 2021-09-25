package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;

import java.util.List;
import java.util.Optional;

public interface ClaseRepositorio {
    List<Clase> getClases(Optional<Mes> mes);
}
