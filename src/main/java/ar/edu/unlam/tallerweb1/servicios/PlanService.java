package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
public interface PlanService {
    Plan contratarPlan(Long idCliente, Month mes, Integer anio, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes;

    Pago cancelarPlan(Long id, String plan) throws PlanNoExisteException, YaTienePagoRegistradoParaMismoMes;

    Pago getUltimoPagoContratadoParaEsteMesYActivo(Long idUsuario);
}
