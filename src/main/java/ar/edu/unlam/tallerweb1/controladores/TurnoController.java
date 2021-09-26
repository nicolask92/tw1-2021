package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.servicios.ClaseService;
import ar.edu.unlam.tallerweb1.servicios.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.unlam.tallerweb1.modelo.Turno;

import java.util.List;
import java.util.Optional;

@Controller
public class TurnoController {

    ClaseService claseService;
    TurnoService turnoService;
    @Autowired
    TurnoController(ClaseService claseService,TurnoService turnoService ) {
        this.claseService = claseService;
        this.turnoService = turnoService;

    }



    @RequestMapping({"/mostrar-clases/{mes}", "/mostrar-clases"})
    public ModelAndView mostrarClases(@PathVariable Optional<Mes> mes) {

        List<Clase> clases = claseService.getClases(mes);

        ModelMap model = new ModelMap();

        return new ModelAndView("Clases", model);
    }
    @RequestMapping({"/mostrar-turnos/{id}"})
    public ModelAndView mostrarTurnosPorUsuario(@PathVariable Long id) {

        List<Turno> turnos = turnoService.getTurnosPorId(id);

        ModelMap model = new ModelMap();

        return new ModelAndView("ClasesPorUsuario", model);
    }
}
