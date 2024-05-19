package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexionBD {
    private Connection conexion = null;
    private String servidor;
    private String database;
    private String usuario;
    private String password;
    private String url;

    public ConexionBD(String servidor, String database, String usuario, String password) {
        try {
            this.servidor = servidor;
            this.database = database;
            this.usuario = usuario;
            this.password = password;
            this.url = "jdbc:mysql://" + servidor + "/" + database;
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, password);
            System.out.println("Conexion con la base de datos " + url + "...Ok");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return conexion;
    }

    public Connection cerrarConexion() {
        try {
            conexion.close();
            System.out.println("Cerrando conexion");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion = null;
        return conexion;
    } 
}
