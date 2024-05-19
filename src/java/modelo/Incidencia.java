package modelo;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Incidencia {
    private int idIncidencia;
    private int idUsuario;
    private int idEquipo;
    private String estado;
    private String descripcion;
    private String prioridad;
    private Date fechaInicio;
    private Date fechaFin;
    
 
    
     public Incidencia() {
        this.fechaInicio = new Date(); 
    }

    public Incidencia(int idIncidencia, int idUsuario, int idEquipo, String estado, String descripcion, String prioridad, Date fechaInicio, Date fechaFin) {
        this.idIncidencia = idIncidencia;
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
        this.estado = estado;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
   
    
    

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }


 
    
  public void setFechaInicioString(String fechaInicioString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.fechaInicio = dateFormat.parse(fechaInicioString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setFechaFinString(String fechaFinString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.fechaFin = dateFormat.parse(fechaFinString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
   


}