package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class TurnoController {

    ClaseService claseService;

    @Autowired
    TurnoController(ClaseService claseService) {
        this.claseService = claseService;
    }

    @RequestMapping({"/mostrar-turnos/{mes}", "/mostrar-turnos"})
    public ModelAndView mostrarTurnos(@PathVariable Optional<Mes> mes) {

        List<Clase> clases = claseService.getClases(mes);

        ModelMap model = new ModelMap();

        return new ModelAndView("turnos", model);
    }

    @RequestMapping("/mostrar-clase/{id}")
    public ModelAndView mostrarClase(@PathVariable("id") Long id){
        Clase clase = claseService.buscarClasePorId(id);
        ModelMap model = new ModelMap();
        model.put("clase", clase);
        return new ModelAndView("clase", model);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/reservar-Turno/{id}")
    public ModelAndView reservarTurno(@PathVariable("id") Integer id){
        return new ModelAndView("redirect:turnos");
    }
}
