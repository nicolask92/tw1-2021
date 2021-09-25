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
}
