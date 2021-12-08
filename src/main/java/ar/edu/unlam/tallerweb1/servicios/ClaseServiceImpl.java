package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.common.Mes;
import ar.edu.unlam.tallerweb1.exceptiones.NoTienePlanParaVerLasClasesException;
import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;
import ar.edu.unlam.tallerweb1.modelo.Clase;
import ar.edu.unlam.tallerweb1.modelo.Cliente;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.repositorios.ClaseRepositorio;
import ar.edu.unlam.tallerweb1.repositorios.ClienteRepositorio;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClaseServiceImpl implements ClaseService {

    private final SessionFactory sessionFactory;
    private final ClaseRepositorio claseRepositorio;
    private final ClienteRepositorio clienteRepositorio;

    @Autowired
    public ClaseServiceImpl(SessionFactory sessionFactory, ClaseRepositorio claseRepositorio, ClienteRepositorio clienteRepositorio) {
        this.sessionFactory = sessionFactory;
        this.claseRepositorio = claseRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public List<Clase> getClases(Optional<Mes> mes, Optional<Integer> anio, Long idUsuario, boolean habilitarPruebaDebito) throws NoTienePlanParaVerLasClasesException, YaTienePagoRegistradoParaMismoMes {
        final Cliente cliente = clienteRepositorio.getById(idUsuario);

        LocalDate hoy;
        int mesDefinido;
        int anioDefinido;

        if (habilitarPruebaDebito) {
            hoy = LocalDate.of(2022, 1, 1);
            mesDefinido = hoy.getMonth().getValue();
            anioDefinido = hoy.getYear();
        } else {
            hoy = LocalDate.now();
            mesDefinido = mes.isPresent() ? mes.get().getNumeroDelMes() : hoy.getMonth().getValue();
            anioDefinido = anio.orElseGet(hoy::getYear);
        }

        boolean tienePlanParaElMesAConsultar = cliente.getUltimoPagoEn(mesDefinido, anioDefinido, false) != null;

        boolean tieneCapacidadDeGenerarPagoEnElMes = cliente.getUltimoPagoRealizado() != null ? cliente.getUltimoPagoRealizado().getDebitoAutomatico() : false;

        if (!tienePlanParaElMesAConsultar) {
            if (tieneCapacidadDeGenerarPagoEnElMes && hoy.getMonth().getValue() == mesDefinido && hoy.getYear() == anioDefinido) {
                Pago pagoACancelar = cliente.getUltimoPagoRealizado();
                Pago pagoParaEsteMes = new Pago(cliente, hoy.getMonth(), anioDefinido, cliente.getUltimoPlanContrado());
                pagoACancelar.cancelarPlan();
                pagoParaEsteMes.setDebitoAutomatico(true);
                pagoParaEsteMes.setImportePagado(BigDecimal.valueOf(pagoACancelar.getPlan().getPrecio()));
                cliente.agregarPago(pagoParaEsteMes);
                clienteRepositorio.actualizarCliente(cliente);
            } else {
                throw new NoTienePlanParaVerLasClasesException();
            }
        }
        return claseRepositorio.getClases(mes);
    }

    @Override
    public Clase buscarClasePorId(Long id) {
        return (Clase) sessionFactory.getCurrentSession().createCriteria(Clase.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
