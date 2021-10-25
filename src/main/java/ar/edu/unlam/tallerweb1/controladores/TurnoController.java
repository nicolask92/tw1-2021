package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.Exceptiones.ElClienteNoCorrespondeAlTurnoException;
import ar.edu.unlam.tallerweb1.Exceptiones.LaClaseEsDeUnaFechaAnterioALaActualException;
import ar.edu.unlam.tallerweb1.Exceptiones.TurnoExpiroException;
import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @RequestMapping({"/mostrar-clases/{mes}", "/mostrar-clases"})
    public ModelAndView mostrarClasesParaSacarTurnos(@PathVariable Optional<Mes> mes, HttpSession httpSession) throws Exception {

        Long idUsuario = (Long) httpSession.getAttribute("usuarioId");

        List<Clase> clases = claseService.getClases(mes);
        List<Turno> turnosDelDia = turnoService.getTurnosParaHoy(idUsuario);

        CalendarioDeActividades calendarioYActividades = clasesViewModelBuilder.getCalendarioCompleto(clases, mes);

        ModelMap model = new ModelMap();
        model.put("turnosDelDia", turnosDelDia);
        model.put("mes", mes);
        model.put("calendario", calendarioYActividades);

        return new ModelAndView("clases-para-turnos", model);
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

        ModelMap model = new ModelMap();
        try {
            turnoService.guardarTurno(idClase, idUsuario);
            model.put("msgGuardado", "Se guardo turno correctamente");
            return new ModelAndView("redirect:/mostrar-turno", model);
        } catch (Exception e) {
            model.put("msg", "Cupo máximo alcanzado");
            return new ModelAndView("redirect:/mostrar-clases", model);
        }catch(LaClaseEsDeUnaFechaAnterioALaActualException e){
            model.put("msg", "La clase ya expiro");
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

}
