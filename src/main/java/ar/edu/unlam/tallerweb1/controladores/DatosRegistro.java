package ar.edu.unlam.tallerweb1.controladores;

public class DatosRegistro {
    private String nombre;
    private String apellido;
    private String email;
    private String clave;
    private String repiteClave;

    public DatosRegistro(){}

    public DatosRegistro(String nombre, String apellido, String email, String clave, String repiteClave) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.repiteClave = repiteClave;
    }

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}

    public void setApellido(String apellido) {this.apellido = apellido;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRepiteClave() {
        return repiteClave;
    }

    public void setRepiteClave(String repiteClave) {
        this.repiteClave = repiteClave;
    }
}
