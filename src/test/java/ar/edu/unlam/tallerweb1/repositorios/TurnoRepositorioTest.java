package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDate;

public class TurnoRepositorioTest extends SpringTest {

    @Autowired
    TurnoRepositorio turnoRepositorio;
    @Autowired
    ClaseRepositorio claseRepositorio;

    @Test
    @Rollback
    @Transactional
    public void queSePuedaEliminarUnTurno() throws Exception {
        Turno turno = givenQueHayUnTurno();
        whenQuieroEliminarElTurno(turno);
        thenElTurnoYaNoExiste(turno);
    }

    private void thenElTurnoYaNoExiste(Turno turno) throws Exception {
        Assert.assertNotNull(claseRepositorio.getById(turno.getClase().getId()));

        Assert.assertNull(turnoRepositorio.getTurnoById(turno.getId()));
    }

    private void whenQuieroEliminarElTurno(Turno turno) {
        turnoRepositorio.borrarTurno(turno);
    }

    private Turno givenQueHayUnTurno() {
        Cliente cliente = new Cliente();
        cliente.setApellido("Locaso");
        cliente.setNombre("Jorge");
        session().save(cliente);

        Clase clase = new Clase();
        session().save(clase);

        Turno turno = new Turno(cliente, clase, LocalDate.now());
        session().save(turno);
        return turno;
    }

}