package ar.edu.unlam.tallerweb1.modelo;

public enum Plan {
    BASICO("Basico" , "Permite 1 turno por dia y hasta 3 por semana", 3500f),
    ESTANDAR("Estandar" , "Permite 3 turno por dia y hasta 6 por semana", 5000f),
    PREMIUM("Premium" , "No tiene limites para sacar turnos", 7000f),
    NINGUNO("Ninguno" , "Sin beneficios", 0f);

    private String nombre;
    private String descripcion;
    private Float precio;

    Plan(String nombre, String descripcion, Float precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
}
