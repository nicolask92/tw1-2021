package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.servicios.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PlanController {

    private PlanService planService;
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    public PlanController(PlanService planService, ClienteRepositorio clienteRepositorio) {
        this.planService = planService;
        this.clienteRepositorio = clienteRepositorio;

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

    @RequestMapping(method = RequestMethod.GET, path = "/contratar-plan/{plan}")
    public ModelAndView contratarPlan(@PathVariable("plan") String plan, HttpSession sesion) throws PlanNoExisteException {
        Long idUsuario = (Long)sesion.getAttribute("usuarioId");
        ModelMap model = new ModelMap();

        try {
            planService.contratarPlan(idUsuario, plan);
            model.put("contracionExitosa", "El Plan se contrato correctamente");
            return new ModelAndView("redirect:/mostrar-clases", model);
        } catch (PlanNoExisteException e){
            model.put("noExistePlan", "El plan que quiere contratar no existe");
            return  new ModelAndView("/planes", model);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/contratar-plan/{plan}")
    public ModelAndView cancelarPlan(@PathVariable("plan") String plan, HttpSession sesion) throws PlanNoExisteException {
        Long idUsuario = (Long)sesion.getAttribute("usuarioId");
        ModelMap model = new ModelMap();

        try{
            planService.cancelarPlan(idUsuario, plan);
            model.put("msg", "El Plan se cancelo correctamente");
            return new ModelAndView("redirect:/planes", model);
        }catch (PlanNoExisteException e){
            model.put("noExistePlan", "El plan que quiere cancelar no existe");
            return  new ModelAndView("/planes", model);
        }

    }
}
