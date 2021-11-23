package ar.edu.unlam.tallerweb1.modelo;

import ar.edu.unlam.tallerweb1.exceptiones.YaTienePagoRegistradoParaMismoMes;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class Cliente extends Usuario {

    //   Esto creo que no deberia ir, ya que el cliente tiene turnos..
    @ManyToMany(mappedBy = "clientes")
    List<Clase> clases;

    @OneToMany(mappedBy = "cliente")
    List<Pago> contrataciones;

    public Cliente(String nombre, String apellido, String email, Plan plan, List<Clase> clases) {
        super(nombre, apellido, email);
        this.clases = clases;
    }

    public Cliente(String nombre, String apellido, String email) {
        this.clases = Collections.emptyList();
    }

    public Cliente() {
    }

    public List<Pago> agregarPago(Pago pago) throws YaTienePagoRegistradoParaMismoMes {
        if (contrataciones.contains(pago)) {
            throw new YaTienePagoRegistradoParaMismoMes();
        }
        this.contrataciones.add(pago);
        return this.contrataciones;
    }

    public List<Pago> getContrataciones() {
        return contrataciones;
    }
}
