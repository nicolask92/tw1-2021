package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.servicios.PlanService;
import ar.edu.unlam.tallerweb1.viewBuilders.PlanesViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

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
    public ModelAndView getPlanes(HttpSession sesion) {

        Long idUsuario = (Long)sesion.getAttribute("usuarioId");
        final Pago ultimoPagoEsteMes = planService.getUltimoPagoContratadoParaEsteMesYActivo(idUsuario);

        List<PlanesViewModel> planesAMostrar = PlanesViewModel.getListadoDePlanesActual(ultimoPagoEsteMes);

        ModelMap modelMap = new ModelMap();
        modelMap.put("datosPlan", new DatosPlan());
        modelMap.put("planes", planesAMostrar);

        return new ModelAndView("/planes", modelMap);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/contratar-plan")
    public ModelAndView contratarPlan(@ModelAttribute("datosPlan") DatosPlan datosPlan, HttpSession sesion) throws PlanNoExisteException {

        Long idUsuario = (Long)sesion.getAttribute("usuarioId");
        ModelMap model = new ModelMap();
        LocalDate hoy = LocalDate.now();

        try {
            Plan ultimoPlan = planService.contratarPlan(idUsuario, hoy.getMonth(), hoy.getYear(), datosPlan);
            model.put("contracionExitosa", "El Plan se contrato correctamente");
            sesion.setAttribute("plan", null);
            sesion.setAttribute("plan", ultimoPlan);
            return new ModelAndView("redirect:/mostrar-clases", model);
        } catch (PlanNoExisteException e){
            model.put("noExistePlan", "El plan que quiere contratar no existe");
            return new ModelAndView("redirect:/planes", model);
        } catch (YaTienePagoRegistradoParaMismoMes e) {
            model.put("yaTuvoPlan", "Ya tiene registrado este plan para este mes.");
            return new ModelAndView("/planes", model);
        }
    }

    // Ahora no se usa mas
    @RequestMapping(method = RequestMethod.GET, path = "/cancelar-plan/{plan}")
    public ModelAndView cancelarPlan(@PathVariable("plan") String plan, HttpSession sesion) throws PlanNoExisteException {
        Long idUsuario = (Long)sesion.getAttribute("usuarioId");
        ModelMap model = new ModelMap();

        try {
            planService.cancelarPlan(idUsuario, plan);
            model.put("msg", "El Plan se cancelo correctamente");
            return new ModelAndView("redirect:/planes", model);
        } catch (PlanNoExisteException | YaTienePagoRegistradoParaMismoMes e){
            model.put("noExistePlan", "El plan que quiere cancelar no existe");
            return  new ModelAndView("redirect:/planes", model);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/cancelar-suscripcion/{plan}")
    public ModelAndView cancelarSuscripcion(@PathVariable("plan") String plan, HttpSession sesion) {
        Long idUsuario = (Long)sesion.getAttribute("usuarioId");
        ModelMap model = new ModelMap();

        try {
            planService.cancelarSuscripcion(idUsuario, plan);
            model.put("msg", "Te desuscribiste del plan correctamente");
            return new ModelAndView("redirect:/planes", model);
        } catch (PlanNoExisteException e) {
            model.put("noExistePlan", "El plan que quiere desuscribirse no existe");
            return  new ModelAndView("redirect:/planes", model);
        }
    }
}
