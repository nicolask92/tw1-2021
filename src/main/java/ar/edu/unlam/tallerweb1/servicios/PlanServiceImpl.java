package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;

@Service
@Transactional
public class PlanServiceImpl implements PlanService {

    private PagoRepositorio pagoRepositorio;
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    public PlanServiceImpl(PagoRepositorio pagoRepositorio, ClienteRepositorio clienteRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
        this.clienteRepositorio= clienteRepositorio;
    }


    @Override
    public Plan contratarPlan(Long idCliente, Month mes, Integer anio, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = clienteRepositorio.getById(idCliente);

        if (plan.equals("Basico") || plan.equals("Estandar") || plan.equals("Premium")) {
            Pago pago = clienteRepositorio.getPlanActivo(cliente);
            if (pago != null) {
                pago.cancelarPlan();
                pagoRepositorio.actualizar(pago);
            }
            Pago nuevoPago = new Pago(cliente, mes, anio, Plan.valueOf(plan.toUpperCase()));
            pagoRepositorio.guardar(nuevoPago);
            cliente.agregarPago(nuevoPago);
            clienteRepositorio.actualizarCliente(cliente);
            return nuevoPago.getPlan();
        } else
            throw new PlanNoExisteException();
    }

    @Override
    public Pago cancelarPlan(Long idCliente, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = clienteRepositorio.getById(idCliente);
        if (plan.equals("Basico") || plan.equals("Estandar") || plan.equals("Premium")) {
            Pago pago = clienteRepositorio.getPlanActivo(cliente);
            Pago pagoNinguno = clienteRepositorio.getPlanNinguno(cliente);
            pago.cancelarPlan();
            pagoNinguno.activarPlan();
            pagoRepositorio.actualizar(pago);
            pagoRepositorio.actualizar(pagoNinguno);
            return pago;
        }else
            throw new PlanNoExisteException();
    }
}
