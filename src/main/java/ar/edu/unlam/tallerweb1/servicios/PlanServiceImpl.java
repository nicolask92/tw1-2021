package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.controladores.DatosPlan;
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

import java.time.Month;

@Service
public class PlanServiceImpl implements PlanService {

    private PagoRepositorio pagoRepositorio;
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    public PlanServiceImpl(PagoRepositorio pagoRepositorio, ClienteRepositorio clienteRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
        this.clienteRepositorio= clienteRepositorio;
    }


    @Override
    @Transactional
    public Plan contratarPlan(Long idCliente, Month mes, Integer anio, DatosPlan plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = clienteRepositorio.getById(idCliente);

        if (plan.getNombre().equals("Basico") || plan.getNombre().equals("Estandar") || plan.getNombre().equals("Premium")) {
            Pago pago = clienteRepositorio.getPagoActivo(cliente);
            Pago nuevoPago = new Pago(cliente, mes, anio, Plan.valueOf(plan.getNombre().toUpperCase()));
            nuevoPago.setDebitoAutomatico(plan.getConDebito());
            cliente.agregarPago(nuevoPago);
            if (pago != null) {
                pago.cancelarPlan();
                pagoRepositorio.actualizar(pago);
            }
            pagoRepositorio.guardar(nuevoPago);
            clienteRepositorio.actualizarCliente(cliente);
            return nuevoPago.getPlan();
        } else
            throw new PlanNoExisteException();
    }

    @Override
    @Transactional
    public Pago cancelarPlan(Long idCliente, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes {
        Cliente cliente = clienteRepositorio.getById(idCliente);
        if (plan.equals("Basico") || plan.equals("Estandar") || plan.equals("Premium")) {
            Pago pago = clienteRepositorio.getPagoActivo(cliente);
            pago.cancelarPlan();
            Pago pagoNinguno = clienteRepositorio.getPlanNinguno(cliente);
            pagoNinguno.activarPlan();
            pagoRepositorio.actualizar(pago);
            pagoRepositorio.actualizar(pagoNinguno);
            return pago;
        }else
            throw new PlanNoExisteException();
    }

    @Override
    public Pago getUltimoPagoContratadoParaEsteMesYActivo(Long idCliente) {
        Cliente cliente = clienteRepositorio.getById(idCliente);
        return pagoRepositorio.getUltimoPagoContratadoParaEsteMesYActivo(cliente);
    }
}
