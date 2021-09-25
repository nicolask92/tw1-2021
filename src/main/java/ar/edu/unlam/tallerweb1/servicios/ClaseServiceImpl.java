package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClaseServiceImpl implements ClaseService {

    ClaseRepositorio claseRepositorio;

    @Autowired
    public ClaseServiceImpl(ClaseRepositorio claseRepositorio){
        this.claseRepositorio = claseRepositorio;
    }

    public List<Clase> getClases(Optional<Mes> mes) {
        return claseRepositorio.getClases(mes);
    }
}
