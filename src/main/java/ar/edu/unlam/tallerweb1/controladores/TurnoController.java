package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.exceptiones.*;
import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import ar.edu.unlam.tallerweb1.servicios.TurnoService;
import ar.edu.unlam.tallerweb1.viewBuilders.CalendarioDeActividades;
import ar.edu.unlam.tallerweb1.viewBuilders.ClasesViewModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class TurnoController {

    private ClasesViewModelBuilder clasesViewModelBuilder;
    private ClaseService claseService;
    private TurnoService turnoService;

    @Autowired
    TurnoController(ClaseService claseService, TurnoService turnoService, ClasesViewModelBuilder clasesViewModelBuilder) {
        this.claseService = claseService;
        this.turnoService = turnoService;
        this.clasesViewModelBuilder = clasesViewModelBuilder;
    }

    @RequestMapping({"/mostrar-clases/{mes}", "/mostrar-clases", "/mostrar-clases/{mes}/{anio}", "/mostrar-clases/{mes}/{anio}/{debito}"})
    public ModelAndView mostrarClasesParaSacarTurnos(
        @PathVariable Optional<Mes> mes,
        @PathVariable Optional<Integer> anio,
        @PathVariable Optional<String> debito,
        HttpSession httpSession) throws Exception {

        Long idUsuario = (Long) httpSession.getAttribute("usuarioId");
        LocalDate hoy = LocalDate.now();
        ModelMap model = new ModelMap();
        model.put("mes", mes.orElseGet(() -> Mes.values()[hoy.getMonth().getValue() - 1]));
        model.put("anio", anio.orElse(hoy.getYear()));
        List<Clase> clases;

        try {
            clases = claseService.getClases(mes, anio, idUsuario, debito.isPresent());
            List<Turno> turnosDelDia = turnoService.getTurnosParaHoy(idUsuario);

            CalendarioDeActividades calendarioYActividades = clasesViewModelBuilder.getCalendarioCompleto(clases, mes, anio);

            model.put("turnosDelDia", turnosDelDia);
            model.put("calendario", calendarioYActividades);

            return new ModelAndView("clases-para-turnos", model);
        } catch (NoTienePlanParaVerLasClasesException | YaTienePagoRegistradoParaMismoMes e) {
            model.put("turnosDelDia", List.of());
            CalendarioDeActividades calendarioYActividades = clasesViewModelBuilder.getCalendarioCompleto(List.of(), mes, anio);

            model.put("calendario", calendarioYActividades);

            model.put("msgError", "Para poder ver las clases de este mes debe contratar un plan.");
            return new ModelAndView("clases-para-turnos", model);
        }
    }

    @RequestMapping("/mostrar-clase/{id}")
    public ModelAndView mostrarClase(@PathVariable("id") Long id){
        Clase clase = claseService.buscarClasePorId(id);
        ModelMap model = new ModelMap();
        model.put("clase", clase);
        return new ModelAndView("clase", model);
    }

    @RequestMapping("/mostrar-turno")
    public ModelAndView mostrarTurnoPorId(HttpServletRequest sesion) throws Exception {

        Long idUsuario = (Long)sesion.getSession().getAttribute("usuarioId");
        ModelMap model = new ModelMap();

        try{
            List<Turno> turnos = turnoService.getTurnosByIdCliente(idUsuario);
            model.put("turnos", turnos);
            model.put("usuarioId", idUsuario);
            return new ModelAndView("Turnos", model);
        } catch (Exception e){
            model.put("msg", "No hay turnos disponibles");
            return new ModelAndView("Turnos", model);
        }

    }

    @RequestMapping(method = RequestMethod.GET, path = "/reservar-Turno/{idClase}")
    public ModelAndView reservarTurno(@PathVariable("idClase") Long idClase, HttpServletRequest sesion) throws Exception {
        Long idUsuario = (Long)sesion.getSession().getAttribute("usuarioId");
        Plan plan = (Plan) sesion.getSession().getAttribute("plan");
        ModelMap model = new ModelMap();

        if (plan == null || plan == Plan.NINGUNO) {
            model.put("msg", "No tenes plan, por lo tanto no podes reservar turnos");
            return new ModelAndView("redirect:/planes", model);
        }

        try {
            turnoService.guardarTurno(idClase, idUsuario);
            model.put("msgGuardado", "Se guardo turno correctamente");
            return new ModelAndView("redirect:/mostrar-turno", model);
        } catch (Exception e) {
            model.put("msg", "Cupo máximo alcanzado");
            return new ModelAndView("redirect:/mostrar-clases", model);
        } catch (LaClaseEsDeUnaFechaAnterioALaActualException e){
            model.put("msg", "La clase ya expiro");
            return new ModelAndView("redirect:/mostrar-clases", model);
        } catch (YaHayTurnoDeLaMismaClaseException e){
            model.put("msgTurnoExistente","Ya reservaste turno para esta clase");
            return new ModelAndView("redirect:/mostrar-clases", model);
        } catch (SinPlanException e) {
            model.put("msgTurnoExistente","No puede reservar turnos porque no tiene Plan");
            return new ModelAndView("redirect:/planes", model);
        } catch (SuPlanNoPermiteMasInscripcionesPorDiaException e) {
            model.put("msg", "Su plan no permite inscribirse a mas clases");
            return new ModelAndView("redirect:/mostrar-clases", model);
        } catch (SuPlanNoPermiteMasInscripcionesPorSemanaException e) {
            model.put("msg", "Su plan no permite sacar mas clases por semana");
            return new ModelAndView("redirect:/mostrar-clases", model);
        } catch (YaVencioElMesException e) {
            model.put("msg", "No se puede sacar clases de meses anteriores");
            return new ModelAndView("redirect:/mostrar-clases", model);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/borrar-turno/{idTurno}")
    public ModelAndView borrarTurno(@PathVariable("idTurno") Long idTurno, HttpServletRequest sesion) throws ElClienteNoCorrespondeAlTurnoException {
        Long idUsuario = (Long)sesion.getSession().getAttribute("usuarioId");
        ModelMap model = new ModelMap();

        try {
            turnoService.borrarTurno(idTurno, idUsuario);
            model.put("msgBorrado","Se borro turno correctamente");
            return new ModelAndView("redirect:/mostrar-turno", model);
        }catch (ElClienteNoCorrespondeAlTurnoException e){
            model.put("msgUsuarioNoValido", "El turno no corresponde al usuario");
            return new ModelAndView("redirect:/mostrar-turno",model);
        }catch (TurnoExpiroException e){
            model.put("msgTurnoExpiro", "No se puede borrar turno porque es de una fecha anterior a la actual");
            return new ModelAndView("redirect:/mostrar-turno",model);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/buscar-clase")
    public ModelAndView buscarClase(@RequestParam ("claseABuscar") String claseABuscar) {

        ModelMap model = new ModelMap();
        try {
            List<Clase> clasesBuscadas = turnoService.buscarClase(claseABuscar);
            model.put("clasesBuscadas", clasesBuscadas);
            return new ModelAndView("clase-buscada", model);
        } catch (IllegalArgumentException | NoSeEncontroClaseConEseNombreException e){
            model.put("claseNoEncontrada", "No hay clases con ese Nombre");
            return new ModelAndView("clase-buscada", model);
        }
    }
}
