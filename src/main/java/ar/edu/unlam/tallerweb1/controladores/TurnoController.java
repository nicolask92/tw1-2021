package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Turno;
import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import ar.edu.unlam.tallerweb1.servicios.TurnoService;
import ar.edu.unlam.tallerweb1.viewBuilders.CalendarioDeActividades;
import ar.edu.unlam.tallerweb1.viewBuilders.ClasesViewModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    ClasesViewModelBuilder clasesViewModelBuilder;

    private ClaseService claseService;
    private TurnoService turnoService;

    @Autowired
    TurnoController(ClaseService claseService, TurnoService turnoService) {
        this.claseService = claseService;
        this.turnoService = turnoService;
    }

    @RequestMapping({"/mostrar-clases/{mes}", "/mostrar-clases"})
    public ModelAndView mostrarClasesParaSacarTurnos(@PathVariable Optional<Mes> mes) throws Exception {

        List<Clase> clases = claseService.getClases(mes);

        CalendarioDeActividades calendarioYActividades = clasesViewModelBuilder.getCalendarioCompleto(clases, mes);

        ModelMap model = new ModelMap();
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
            model.put("msg", "Se guardo turno correctamente");
            return new ModelAndView("redirect:/mostrar-turno", model);
        } catch (Exception e) {
            model.put("msg", "Cupo máximo alcanzado");
            return new ModelAndView("clases-para-turnos", model);
        }

    }

    @RequestMapping(method = RequestMethod.GET, path = "/borrar-turno/{idTurno}")
    public ModelAndView borrarTurno(@PathVariable("idTurno") Long idTurno, HttpServletRequest sesion){
        Long idUsuario = (Long)sesion.getSession().getAttribute("usuarioId");
        ModelMap model = new ModelMap();
        turnoService.borrarTurno(idTurno, idUsuario);
        model.put("msgBorrado","Se borro turno correctamente");
        return new ModelAndView("redirect:/mostrar-turno", model);
    }

}
