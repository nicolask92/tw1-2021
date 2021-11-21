package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import org.springframework.stereotype.Service;

@Service
public interface PlanService {
    Plan contratarPlan(Long idCliente, String plan) throws PlanNoExisteException;

    Plan cancelarPlan(Long id, String plan) throws PlanNoExisteException;
}
