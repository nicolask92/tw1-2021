package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.servicios.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(method = RequestMethod.POST, path = "/contratar-plan")
    public ModelAndView contratarPlan(HttpSession sesion) {
        Long idUsuario = (Long)sesion.getAttribute("usuarioId");
        Cliente cliente = clienteRepositorio.getById(idUsuario);
        planService.cambiarPlan(cliente);
        return new ModelAndView("forward:/mostrar-clase");
    }
}
