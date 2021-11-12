package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Cliente;
import org.springframework.stereotype.Service;

@Service
public interface PlanService {

    void cambiarPlan(Cliente cliente);
}
