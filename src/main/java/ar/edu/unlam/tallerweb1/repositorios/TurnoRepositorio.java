package ar.edu.unlam.tallerweb1.repositorios;


import ar.edu.unlam.tallerweb1.modelo.Cliente;

import ar.edu.unlam.tallerweb1.modelo.Turno;

import java.util.List;

public interface TurnoRepositorio {


    List<Turno> getTurnosPorId(Cliente cliente);

}
