package ar.edu.unlam.tallerweb1.servicios;


import ar.edu.unlam.tallerweb1.modelo.Turno;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.repositorios.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TurnoServiceImpl implements TurnoService {

    TurnoRepositorio turnoRepositorio;

    @Autowired
    public TurnoServiceImpl (TurnoRepositorio turnoRepositorio){
        this.turnoRepositorio = turnoRepositorio;
    }

    @Override
    public List<Turno> getTurnosPorId(Long id) {
        Cliente cliente = new Cliente();

        return turnoRepositorio.getTurnosPorId(cliente);
    }


}
