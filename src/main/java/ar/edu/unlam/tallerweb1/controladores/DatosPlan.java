package ar.edu.unlam.tallerweb1.controladores;

public class DatosPlan {
    private String nombre;
    private Boolean conDebito;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getConDebito() {
        return conDebito;
    }

    public void setConDebito(Boolean conDebito) {
        this.conDebito = conDebito;
    }
}
