package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoServiceImpl implements TurnoService {

    private TurnoRepositorio turnoRepositorio;
    private ClaseRepositorio claseRepositorio;

    @Autowired
    public TurnoServiceImpl (TurnoRepositorio turnoRepositorio, ClaseRepositorio claseRepositorio){
        this.turnoRepositorio = turnoRepositorio;
        this.claseRepositorio = claseRepositorio;
    }

    @Override
    public List<Turno> getTurnosDeEsteMes() {
        return null;
    }

    @Override
    public void guardarTurno(Long id) {
        Clase clase = claseRepositorio.getById(id);
    //    turnoRepositorio.guardarTurno(clase, cliente);
    }

    @Override
    public List<Turno> getTurnosPorId(Long id) {
        return null;
    }
}
