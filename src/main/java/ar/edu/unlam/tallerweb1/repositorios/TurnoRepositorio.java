package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

import java.util.List;

public interface TurnoRepositorio {
    List<Turno> getTurnosByIdCliente(Cliente cliente);

    List<Clase> getTurnosDeEsteMes();

    void guardarTurno(Cliente cliente, Clase clase);

    Turno getTurnoById(Long idTurno);

    void borrarTurno(Turno turno);
}
