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

import java.util.List;
import java.util.Optional;

@Controller
public class TurnoController {

    ClaseService claseService;
    TurnoService turnoService;
    @Autowired
    TurnoController(ClaseService claseService, TurnoService turnoService) {
        this.claseService = claseService;
        this.turnoService = turnoService;
    }

    @RequestMapping({"/mostrar-clases/{mes}", "/mostrar-clases"})
    public ModelAndView mostrarClasesParaSacarTurnos(@PathVariable Optional<Mes> mes) {

        List<Clase> clases = claseService.getClases(mes);

        ModelMap model = new ModelMap();

        return new ModelAndView("clases-para-turnos", model);
    }

    @RequestMapping("/mostrar-clase/{id}")
    public ModelAndView mostrarClase(@PathVariable("id") Long id){
        Clase clase = claseService.buscarClasePorId(id);
        ModelMap model = new ModelMap();
        model.put("clase", clase);
        return new ModelAndView("clase", model);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/reservar-Turno/{idClase}")
    public ModelAndView reservarTurno(@PathVariable("idClase") Long id){
        turnoService.guardarTurno(id);
        return new ModelAndView("clases-para-turnos");
    }
}
