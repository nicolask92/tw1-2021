package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptiones.PlanNoExisteException;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.PlanRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Objects;

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
    public Plan contratarPlan(Long idCliente, String plan) throws PlanNoExisteException {

        Cliente cliente = clienteRepositorio.getById(idCliente);

        if(plan.equals("Basico") || plan.equals("Estandar") || plan.equals("Premium")){
            return cliente.setPlan(Plan.valueOf(plan.toUpperCase()));
        }else
            throw new PlanNoExisteException();

    }
}
