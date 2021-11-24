package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.servicios.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class PerfilController {

    private final ClienteService clienteService;

    @Autowired
    public PerfilController(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/perfil")
    public ModelAndView getPerfil(HttpSession sesion){
        Long idUsuario = (Long)sesion.getAttribute("usuarioId");
        ModelMap model = new ModelMap();

        Cliente cliente = clienteService.getCliente(idUsuario);
        Pago pago = clienteService.getPlanActivo(cliente);
        model.put("clienteDatos", cliente);
        model.put("ultimoPago", pago);
        return new ModelAndView("perfil", model);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/historial-pagos")
    public ModelAndView getHistorialPagos(HttpSession sesion){
        Long idUsuario = (Long)sesion.getAttribute("usuarioId");
        ModelMap model = new ModelMap();

        List<Pago> pagos = clienteService.getPagos(idUsuario);
        model.put("pagos", pagos);
        return new ModelAndView("historial-pagos", model);
    }

}
