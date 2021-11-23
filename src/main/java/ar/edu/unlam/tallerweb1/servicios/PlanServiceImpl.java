package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.PlanRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.List;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private PlanRepositorio planRepositorio;
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    public PlanServiceImpl(PlanRepositorio planRepositorio, ClienteRepositorio clienteRepositorio) {
        this.planRepositorio = planRepositorio;
        this.clienteRepositorio= clienteRepositorio;
    }


    @Override
    public List<Pago> contratarPlan(Long idCliente, Month mes, Integer anio, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {

        Cliente cliente = clienteRepositorio.getById(idCliente);

        if (plan.equals("Basico") || plan.equals("Estandar") || plan.equals("Premium")) {
            cliente.agregarPago(new Pago(cliente, mes, anio, Plan.valueOf(plan)));
            clienteRepositorio.actualizarCliente(cliente);
            return cliente.getContrataciones();
        } else
            throw new PlanNoExisteException();
    }
}
