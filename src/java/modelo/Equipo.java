
package modelo;
import java.util.List;
import modelo.Incidencia;

public class Equipo {
    private int idEquipo;
    private String nombreEquipo;
    private String caracteristicas;
    private String aula;
    private String tipo;
    private List<Incidencia> incidencias; // Lista de incidencias asociadas al equipo

    // Constructor vacío
    public Equipo() {
    }

    // Constructor completo sin listas
    public Equipo(int idEquipo, String nombreEquipo, String caracteristicas, String aula, String tipo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.caracteristicas = caracteristicas;
        this.aula = aula;
        this.tipo = tipo;
    }

    // Métodos getters y setters
    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Métodos getters y setters para la lista de incidencias
    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }
}
