package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {

    private TurnoRepositorio turnoRepositorio;
    private ClaseRepositorio claseRepositorio;
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    public TurnoServiceImpl(TurnoRepositorio turnoRepositorio, ClaseRepositorio claseRepositorio, ClienteRepositorio clienteRepositorio){
        this.turnoRepositorio = turnoRepositorio;
        this.claseRepositorio = claseRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public List<Turno> getTurnosDeEsteMes() {
        return null;
    }

    @Override
    public void guardarTurno(Long idClase, Long idUsuario) throws Exception {
        Clase clase = claseRepositorio.getById(idClase);
        Cliente cliente = clienteRepositorio.getById(idUsuario);
        if (clase == null)
            throw new Exception();
        clase.agregarCliente(cliente); //exepcion de si tiene cupo disponible
        turnoRepositorio.guardarTurno(cliente, clase);

    }

    @Override
    public List<Turno> getTurnosByIdCliente(Long id) throws Exception {
        Cliente cliente = clienteRepositorio.getById(id);
        List<Turno> turnos = turnoRepositorio.getTurnosByIdCliente(cliente);
        if(turnos == null)
            throw new Exception();
        return turnos;
    }

    @Override
    public void borrarTurno(Long idTurno, Long idCliente) {
        Turno turno = turnoRepositorio.getTurnoById(idTurno);
        Cliente cliente = clienteRepositorio.getById(idCliente);
        if (Objects.equals(turno.getCliente().getId(), cliente.getId()))
        turnoRepositorio.borrarTurno(turno);
    }
}
