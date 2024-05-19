
package modelo;


public class Rol {
    private int id;
    private String nombreRol;

    public Rol(int id, String nombreRol) {
        this.id = id;
        this.nombreRol = nombreRol;
    }
      public Rol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
  
}