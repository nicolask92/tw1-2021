package ar.edu.unlam.tallerweb1.viewBuilders;

import ar.edu.unlam.tallerweb1.helpers.CalculadoraImportes;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.Plan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanesViewModel {

    static final List<Plan> listaDePlanesValidosParaSuscribirse = List.of(
            Plan.BASICO,
            Plan.ESTANDAR,
            Plan.PREMIUM
    );

    Optional<BigDecimal> importeAPagar;
    Optional<Boolean> conDebitoActivado;
    Plan plan;
    Boolean activo;
    Boolean puedeContratar;

    public PlanesViewModel(Plan plan, Optional<Boolean> conDebitoActivado, Boolean activo, Optional<BigDecimal> importeAPagar, Boolean puedeContratar) {
        this.plan = plan;
        this.conDebitoActivado = conDebitoActivado;
        this.activo = activo;
        this.importeAPagar = importeAPagar;
        this.puedeContratar = puedeContratar;
    }

    static public List<PlanesViewModel> getListadoDePlanesActual(Pago ultimoPago) {
        final Plan planDelUltimoPago = ultimoPago != null ? ultimoPago.getPlan() : null;
        LocalDate hoy = LocalDate.now();
        List<PlanesViewModel> planesAContratar = new ArrayList<>();
        if (planDelUltimoPago == Plan.NINGUNO || planDelUltimoPago == null) {
            planesAContratar.add(new PlanesViewModel(Plan.BASICO, Optional.empty(), false, Optional.empty(), true));
            planesAContratar.add(new PlanesViewModel(Plan.ESTANDAR, Optional.empty(), false, Optional.empty(), true));
            planesAContratar.add(new PlanesViewModel(Plan.PREMIUM, Optional.empty(), false, Optional.empty(), true));
        } else if (planDelUltimoPago.equals(Plan.BASICO)) {
            planesAContratar.add(new PlanesViewModel(Plan.BASICO, Optional.of(ultimoPago.getDebitoAutomatico()), true, Optional.empty(), false));
            planesAContratar.add(new PlanesViewModel(Plan.ESTANDAR, Optional.empty(), false, Optional.of(CalculadoraImportes.calcularSaldoAPagar(Plan.BASICO, Plan.ESTANDAR, hoy)), true));
            planesAContratar.add(new PlanesViewModel(Plan.PREMIUM, Optional.empty(), false, Optional.of(CalculadoraImportes.calcularSaldoAPagar(Plan.BASICO, Plan.PREMIUM, hoy)), true));
        } else if (planDelUltimoPago.equals(Plan.ESTANDAR)) {
            planesAContratar.add(new PlanesViewModel(Plan.BASICO, Optional.empty(), false, Optional.empty(), false));
            planesAContratar.add(new PlanesViewModel(Plan.ESTANDAR, Optional.of(ultimoPago.getDebitoAutomatico()), true, Optional.empty(), false));
            planesAContratar.add(new PlanesViewModel(Plan.PREMIUM, Optional.empty(), false, Optional.of(CalculadoraImportes.calcularSaldoAPagar(Plan.ESTANDAR, Plan.PREMIUM, hoy)), true));
        } else if (planDelUltimoPago.equals(Plan.PREMIUM)) {
            planesAContratar.add(new PlanesViewModel(Plan.BASICO, Optional.empty(), false, Optional.empty(), false));
            planesAContratar.add(new PlanesViewModel(Plan.ESTANDAR, Optional.empty(), false, Optional.empty(), false));
            planesAContratar.add(new PlanesViewModel(Plan.PREMIUM, Optional.of(ultimoPago.getDebitoAutomatico()), true, Optional.empty(), false));
        }
        return planesAContratar;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Optional<Boolean> getConDebitoActivado() {
        return conDebitoActivado;
    }

    public void setConDebitoActivado(Optional<Boolean> conDebitoActivado) {
        this.conDebitoActivado = conDebitoActivado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Optional<BigDecimal> getImporteAPagar() {
        return importeAPagar;
    }

    public void setImporteAPagar(Optional<BigDecimal> importeAPagar) {
        this.importeAPagar = importeAPagar;
    }

    public Boolean getPuedeContratar() {
        return puedeContratar;
    }
}
