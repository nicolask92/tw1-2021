package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.*;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@Transactional
public class TurnoServiceImpl implements TurnoService {

    private final TurnoRepositorio turnoRepositorio;
    private final ClaseRepositorio claseRepositorio;
    private final ClienteRepositorio clienteRepositorio;

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
    public void guardarTurno(Long idClase, Long idUsuario) throws Exception, LaClaseEsDeUnaFechaAnterioALaActualException, YaHayTurnoDeLaMismaClaseException, SuPlanNoPermiteMasInscripcionesPorDiaException, SinPlanException, SuPlanNoPermiteMasInscripcionesPorSemanaException {
        Clase clase = claseRepositorio.getById(idClase);
        Cliente cliente = clienteRepositorio.getById(idUsuario);
        List<Turno> turnosDelCliente = turnoRepositorio.getTurnosByIdCliente(cliente);
        if (clase == null)
            throw new Exception();

        long cantidadDeClasesEnElDia = turnosDelCliente
            .stream()
            .filter( turno ->
                turno.getClase().getDiaClase().toLocalDate().equals(clase.getDiaClase().toLocalDate())
            )
            .count();

        long cantidadDeClasesEnLaSemana = getCantidadTurnosPorSemana(clase, turnosDelCliente);

        switch (cliente.getPlan()) {
            case NINGUNO:
                throw new SinPlanException();
            case BASICO:
                if (cantidadDeClasesEnElDia >= 1) {
                    throw new SuPlanNoPermiteMasInscripcionesPorDiaException();
                }
                if (cantidadDeClasesEnLaSemana >= 3) {
                    throw new SuPlanNoPermiteMasInscripcionesPorSemanaException();
                }
                break;
            case ESTANDAR:
                if (cantidadDeClasesEnElDia >= 3) {
                    throw new SuPlanNoPermiteMasInscripcionesPorDiaException();
                }
                if (cantidadDeClasesEnLaSemana >= 6) {
                    throw new SuPlanNoPermiteMasInscripcionesPorSemanaException();
                }
                break;
            case PREMIUM:
        }

        if (clase.getDiaClase().isBefore(LocalDateTime.now()))
            throw new LaClaseEsDeUnaFechaAnterioALaActualException();

        for (Turno t : turnosDelCliente) {
            if(t.getClase().getId().equals(clase.getId()))
                throw new YaHayTurnoDeLaMismaClaseException();
        }
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
    public void borrarTurno(Long idTurno, Long idCliente) throws ElClienteNoCorrespondeAlTurnoException, TurnoExpiroException {
        Turno turno = turnoRepositorio.getTurnoById(idTurno);
        Cliente cliente = clienteRepositorio.getById(idCliente);

        if (!(Objects.equals(turno.getCliente().getId(), cliente.getId())))
          throw new ElClienteNoCorrespondeAlTurnoException();

        if(turno.getClase().getDiaClase().isBefore(LocalDateTime.now()))
            throw new TurnoExpiroException();

        turnoRepositorio.borrarTurno(turno);

    }

    @Override
    public List<Turno> getTurnosParaHoy(Long idCliente) {
        Cliente cliente = clienteRepositorio.getById(idCliente);
        return turnoRepositorio.getTurnosParaHoy(cliente);
    }

    @Override
    public List<Clase> buscarClase(String claseABuscar) throws NoSeEncontroClaseConEseNombreException {

        List<Clase> clasesBuscadas = turnoRepositorio.buscarClases(claseABuscar.toUpperCase());

        if(clasesBuscadas.size() == 0)
            throw new NoSeEncontroClaseConEseNombreException();

        return clasesBuscadas;
    }


    private long getCantidadTurnosPorSemana(Clase clase, List<Turno> turnos) {
        final int diaDeLaSemanaDeLaClase = clase.getDiaClase().getDayOfWeek().getValue();

        final LocalDate finalPrimerDiaDeLaSemana = clase.getDiaClase().toLocalDate().minusDays(diaDeLaSemanaDeLaClase - 1);
        final LocalDate finalUltimoDiaDeLaSemana = clase.getDiaClase().toLocalDate().plusDays(7 - diaDeLaSemanaDeLaClase);

        return turnos
            .stream()
            .filter( turno ->
                turno.getClase().getDiaClase().toLocalDate().isAfter(finalPrimerDiaDeLaSemana.minusDays(1)) &&
                    turno.getClase().getDiaClase().toLocalDate().isBefore(finalUltimoDiaDeLaSemana.plusDays(1))
            )
            .count();
    }

}
