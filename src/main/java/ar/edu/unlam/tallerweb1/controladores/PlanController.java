package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.servicios.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PlanController {

    PlanService planService;

    @Autowired
    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/planes")
    public ModelAndView getPlanes() {

        List<Plan> planesDisponibles = Arrays
                .stream(Plan.values())
                .filter( plan -> plan != Plan.NINGUNO )
                .collect(Collectors.toList());

        ModelMap modelMap = new ModelMap();

        modelMap.put("planes", planesDisponibles);

        return new ModelAndView("/planes", modelMap);
    }
}
