package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
public class Cliente extends Usuario {

    //   Esto creo que no deberia ir, ya que el cliente tiene turnos..
    @ManyToMany(mappedBy = "clientes")
    List<Clase> clases;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Pago> contrataciones= new ArrayList<>();

    // TODO cambiar constructor. Sacar plan
    public Cliente(String nombre, String apellido, String email, Plan plan, List<Clase> clases) {
        super(nombre, apellido, email);
        this.clases = clases;
        LocalDate hoy = LocalDate.now();
        contrataciones.add(new Pago(this, hoy.getMonth(), hoy.getYear(), Plan.NINGUNO));
    }

    public Cliente(String nombre, String apellido, String email, List<Clase> clases) {
        super(nombre, apellido, email);
        this.clases = clases;
        LocalDate hoy = LocalDate.now();
        contrataciones.add(new Pago(this, hoy.getMonth(), hoy.getYear(), Plan.NINGUNO));
    }

    public Cliente() {
    }

    public String getNombreCompleto (){
        return this.nombre + " " + this.apellido;
    }

    public List<Pago> agregarPago(Pago pago) throws YaTienePagoRegistradoParaMismoMes {
        if (contrataciones.contains(pago)) {
            throw new YaTienePagoRegistradoParaMismoMes();
        }
        this.contrataciones.add(pago);
        return this.contrataciones;
    }

    public Pago getUltimoPagoRealizado() {
        return contrataciones.stream()
            .max(Comparator.comparing(Pago::getMes))
            .stream()
            .max(Comparator.comparing(Pago::getAnio))
            .stream()
            .max(Comparator.comparing(Pago::getId))
            .get();
    }

    public Plan getUltimoPlanContrado() {
        Optional<Pago> pagoObtenido = contrataciones.stream()
            .filter( pago -> pago.activo )
            .max(Comparator.comparing(Pago::getMes))
            .stream()
                .max(Comparator.comparing(Pago::getAnio));
        return pagoObtenido.isEmpty() ? null : pagoObtenido.get().getPlan();
    }

    public List<Pago> getContrataciones() {
        return contrataciones;
    }
}
