package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TurnoRepositorioImpl implements TurnoRepositorio {

    @Override
    public List<Clase> getTurnosDeEsteMes() {
        return null;
    }
}
