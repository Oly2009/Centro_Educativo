package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncidenciaDAO {
    private ConexionBD conexionBD;

    public IncidenciaDAO(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }

    public List<Equipo> listarEquipos() throws SQLException {
        List<Equipo> equipos = new ArrayList<>();
        String query = "SELECT * FROM equipos";

        try (PreparedStatement statement = conexionBD.getConnection().prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Equipo equipo = new Equipo();
                equipo.setIdEquipo(resultSet.getInt("id_equipo"));
                equipo.setNombreEquipo(resultSet.getString("nombre_equipo"));
                equipo.setCaracteristicas(resultSet.getString("caracteristicas"));
                equipo.setAula(resultSet.getString("aula"));
                equipo.setTipo(resultSet.getString("tipo"));
                equipos.add(equipo);
            }
        }

        return equipos;
    }

   
 
public int obtenerIdEquipoPorNombre(String nombreEquipo) throws SQLException {
    int idEquipo = -1; // Valor predeterminado en caso de que no se encuentre ningún equipo

    String sql = "SELECT id_equipo FROM equipos WHERE nombre_equipo = ?";
    try (PreparedStatement stmt = conexionBD.getConnection().prepareStatement(sql)) {
        stmt.setString(1, nombreEquipo);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                idEquipo = rs.getInt("id_equipo");
            }
        }
    }

    return idEquipo;
}




 public boolean agregarIncidencia(Incidencia incidencia, String email, String rol) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = conexionBD.getConnection();

            // Verificar primero si el usuario existe y tiene el rol adecuado para agregar incidencias
      
   PreparedStatement psRol = con.prepareStatement("SELECT u.id_usuario FROM usuarios u " +
    "JOIN usuarios_rol ur ON u.id_usuario = ur.id_usuario " +
    "JOIN roles r ON ur.id_rol = r.id_rol " +
    "WHERE u.email = ? AND r.nombre_rol = ?");
    psRol.setString(1, email);
    psRol.setString(2, rol);
    ResultSet rsRol = psRol.executeQuery();
            if (rsRol.next()) {
                int userId = rsRol.getInt("id_usuario");

                // Si el usuario es válido y tiene el rol adecuado, procedemos a agregar la incidencia
                String sql = "INSERT INTO incidencias (id_usuario, id_equipo, estado, descripcion, prioridad, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?, ?, ?, ?)";
                ps = con.prepareStatement(sql);
                ps.setInt(1, userId);
                ps.setInt(2, incidencia.getIdEquipo());
                ps.setString(3, incidencia.getEstado());
                ps.setString(4, incidencia.getDescripcion());
                ps.setString(5, incidencia.getPrioridad());
                ps.setDate(6, new java.sql.Date(incidencia.getFechaInicio().getTime()));
                if (incidencia.getFechaFin() != null) {
                    ps.setDate(7, new java.sql.Date(incidencia.getFechaFin().getTime()));
                } else {
                    ps.setNull(7, Types.DATE);
                }

                int result = ps.executeUpdate();
                return result > 0;
            } else {
                System.out.println("Usuario no autorizado o inexistente para esta acción.");
                return false;  // Usuario no encontrado o no tiene el rol adecuado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al agregar incidencia: " + e.getMessage(), e);
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

  public String obtenerNombreEquipo(int idEquipo) throws SQLException {
        String nombreEquipo = null;

        String sql = "SELECT nombre_equipo FROM equipos WHERE id_equipo = ?";
        try (Connection con = conexionBD.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idEquipo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nombreEquipo = rs.getString("nombre_equipo");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener el nombre del equipo: " + e.getMessage(), e);
        }

        return nombreEquipo;
    }
public List<Incidencia> listarIncidenciasPorUsuario(String email, String rol) throws SQLException {
    List<Incidencia> incidencias = new ArrayList<>();
    Connection con = conexionBD.getConnection();
    
    // Comprobar si el usuario con este email tiene el rol proporcionado
    PreparedStatement psRol = con.prepareStatement("SELECT COUNT(*) FROM usuarios u " +
        "JOIN usuarios_rol ur ON u.id_usuario = ur.id_usuario " +
        "JOIN roles r ON ur.id_rol = r.id_rol " +
        "WHERE u.email = ? AND r.nombre_rol = ?");
    psRol.setString(1, email);
    psRol.setString(2, rol);
    ResultSet rsRol = psRol.executeQuery();
    
    if (rsRol.next() && rsRol.getInt(1) > 0) {
        PreparedStatement ps;
        if ("tic".equals(rol)) {
            ps = con.prepareStatement("SELECT * FROM incidencias");
        } else {
            ps = con.prepareStatement("SELECT * FROM incidencias WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE email = ?)");
            ps.setString(1, email);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Incidencia incidencia = new Incidencia(
                rs.getInt("id_incidencia"),
                rs.getInt("id_usuario"),
                rs.getInt("id_equipo"),
                rs.getString("estado"),
                rs.getString("descripcion"),
                rs.getString("prioridad"),
                rs.getDate("fecha_inicio"),
                rs.getDate("fecha_fin")
            );
            incidencias.add(incidencia);
        }
        rs.close();
        ps.close();
    }
    rsRol.close();
    psRol.close();
    con.close();
    return incidencias;
}
public List<Equipo> listarEquiposConIncidencias(String email, String rol) throws SQLException {
    List<Equipo> equiposConIncidencias = new ArrayList<>();

    // Consulta SQL para obtener los equipos que tienen incidencias asociadas
    String sql;
    if ("tic".equals(rol)) {
        sql = "SELECT e.id_equipo, e.nombre_equipo, i.id_incidencia, i.estado, i.descripcion, i.prioridad, i.fecha_inicio, i.fecha_fin " +
             "FROM equipos e INNER JOIN incidencias i ON e.id_equipo = i.id_equipo";
    } else {
        sql = "SELECT e.id_equipo, e.nombre_equipo, i.id_incidencia, i.estado, i.descripcion, i.prioridad, i.fecha_inicio, i.fecha_fin " +
             "FROM equipos e INNER JOIN incidencias i ON e.id_equipo = i.id_equipo " +
             "WHERE i.id_usuario = (SELECT id_usuario FROM usuarios WHERE email = ?)";
    }

    try (Connection con = conexionBD.getConnection();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        if (!"tic".equals(rol)) {
            stmt.setString(1, email);
        }
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int idEquipo = rs.getInt("id_equipo");
                Equipo equipo = null;
                // Buscar si el equipo ya está en la lista
                for (Equipo eq : equiposConIncidencias) {
                    if (eq.getIdEquipo() == idEquipo) {
                        equipo = eq;
                        break;
                    }
                }
                // Si el equipo no está en la lista, agregarlo
                if (equipo == null) {
                    equipo = new Equipo();
                    equipo.setIdEquipo(idEquipo);
                    equipo.setNombreEquipo(rs.getString("nombre_equipo"));
                    equipo.setIncidencias(new ArrayList<>());
                    equiposConIncidencias.add(equipo);
                }
                // Crear un objeto Incidencia y agregarlo al equipo
                Incidencia incidencia = new Incidencia();
                incidencia.setIdIncidencia(rs.getInt("id_incidencia"));
                incidencia.setEstado(rs.getString("estado"));
                incidencia.setDescripcion(rs.getString("descripcion"));
                incidencia.setPrioridad(rs.getString("prioridad"));
                incidencia.setFechaInicio(rs.getDate("fecha_inicio"));
                incidencia.setFechaFin(rs.getDate("fecha_fin"));
                equipo.getIncidencias().add(incidencia);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error al obtener equipos con incidencias: " + e.getMessage(), e);
    }

    return equiposConIncidencias;
}

public boolean borrarIncidenciasEquipo(int idEquipo, List<Integer> idIncidencias, String email, String rol) throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;
    try {
        con = conexionBD.getConnection();

        // Verificar primero si el usuario existe y tiene el rol adecuado para borrar incidencias
        PreparedStatement psRol = con.prepareStatement("SELECT u.id_usuario FROM usuarios u " +
                "JOIN usuarios_rol ur ON u.id_usuario = ur.id_usuario " +
                "JOIN roles r ON ur.id_rol = r.id_rol " +
                "WHERE u.email = ? AND r.nombre_rol = ?");
        psRol.setString(1, email);
        psRol.setString(2, rol);
        ResultSet rsRol = psRol.executeQuery();

        if (rsRol.next()) {
          
            // Borrar las incidencias del equipo
            String sql = "DELETE FROM incidencias WHERE id_incidencia IN (";
            for (int i = 0; i < idIncidencias.size(); i++) {
                if (i > 0) {
                    sql += ",";
                }
                sql += "?";
            }
            sql += ")";

            ps = con.prepareStatement(sql);
            int parameterIndex = 1;
            for (int idIncidencia : idIncidencias) {
                ps.setInt(parameterIndex++, idIncidencia);
            }

            int result = ps.executeUpdate();
            if (result <= 0) {
                return false; // Al menos una incidencia no se pudo borrar
            }
            return true; // Todas las incidencias se borraron exitosamente
        } else {
            System.out.println("Usuario no autorizado o inexistente para esta acción.");
            return false;  // Usuario no encontrado o no tiene el rol adecuado
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new SQLException("Error al borrar incidencias del equipo: " + e.getMessage(), e);
    } finally {
        if (ps != null) {
            ps.close();
        }
        if (con != null) {
            con.close();
        }
    }
}
        
 
public boolean modificarIncidencias(List<Incidencia> incidencias, String email, String rol) throws SQLException {
    Connection conn = null;
    PreparedStatement stmtUpdateIncidencia = null;
    try {
        conn = conexionBD.getConnection();
        conn.setAutoCommit(false);

        // Verificar el usuario y rol antes de modificar la incidencia
        String sqlVerifyUser = "SELECT COUNT(*) FROM usuarios u " +
                               "JOIN usuarios_rol ur ON u.id_usuario = ur.id_usuario " +
                               "JOIN roles r ON ur.id_rol = r.id_rol " +
                               "WHERE u.email = ? AND r.nombre_rol = ?";
        try (PreparedStatement stmtVerifyUser = conn.prepareStatement(sqlVerifyUser)) {
            stmtVerifyUser.setString(1, email);
            stmtVerifyUser.setString(2, rol);
            try (ResultSet rsVerifyUser = stmtVerifyUser.executeQuery()) {
                if (rsVerifyUser.next() && rsVerifyUser.getInt(1) > 0) {
                    // Usuario y rol verificados
                } else {
                    conn.rollback();
                    return false; // Usuario no verificado
                }
            }
        }

        String sqlUpdateIncidencia = "UPDATE incidencias SET estado = ?, prioridad = ?, fecha_fin = CASE WHEN estado = 'reparado' THEN ? WHEN estado = 'pendiente' THEN NULL ELSE fecha_fin END WHERE id_incidencia = ?";
stmtUpdateIncidencia = conn.prepareStatement(sqlUpdateIncidencia);

        for (Incidencia incidencia : incidencias) {
            stmtUpdateIncidencia.setString(1, incidencia.getEstado());
            stmtUpdateIncidencia.setString(2, incidencia.getPrioridad());
            if ("reparado".equals(incidencia.getEstado())) {
                // Si el estado es "reparado", actualizar la fecha fin a la fecha actual
                stmtUpdateIncidencia.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
            } else {
                // Si no, mantener la fecha fin como está (usar el valor existente)
                stmtUpdateIncidencia.setNull(3, Types.DATE);
            }
            stmtUpdateIncidencia.setInt(4, incidencia.getIdIncidencia());
            stmtUpdateIncidencia.addBatch(); // Agregar la consulta al lote
        }

        // Ejecutar el lote de actualizaciones
        int[] updateCounts = stmtUpdateIncidencia.executeBatch();

        // Comprobar si se actualizaron todas las incidencias
        for (int updateCount : updateCounts) {
            if (updateCount == 0) {
                conn.rollback();
                return false; // Al menos una incidencia no se pudo actualizar
            }
        }

        conn.commit();
        return true; // Todas las incidencias se actualizaron correctamente
    } catch (SQLException e) {
        if (conn != null) {
            conn.rollback();
        }
        throw e;
    } finally {
        // Cerrar conexiones y liberar recursos
        if (stmtUpdateIncidencia != null) {
            stmtUpdateIncidencia.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}


}