
package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private String apellidos;
    private String email;
    private List<String> roles;

    public Usuario(int id, String nombre, String apellidos, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.roles = new ArrayList<>();
    }

    public Usuario() {
    }

    public Usuario(int id, String nombre, String apellidos, String email, List<String> roles) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.roles = roles;
    }

 

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public void addRol(String rol) {
        roles.add(rol);
    }
}