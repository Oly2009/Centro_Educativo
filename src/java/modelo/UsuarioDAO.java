


package modelo;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class UsuarioDAO {
    private ConexionBD conexionBD;

    public UsuarioDAO(ConexionBD conexionBD) {
        this.conexionBD = conexionBD;
    }
    
    
public void registrarUsuario(String nombre, String apellidos, String email, String password, String[] roles) throws SQLException {
    Connection conn = null;
    PreparedStatement stmtInsertUsuario = null;
    PreparedStatement stmtInsertUsuarioRol = null;
    PreparedStatement stmtSelectRol = null;
    ResultSet resultSet = null;

    try {
        conn = conexionBD.getConnection();
        conn.setAutoCommit(false);

        // Cifrar la contraseña antes de guardarla
        String hashedPassword = hashPassword(password);

        String sqlInsertUsuario = "INSERT INTO usuarios (nombre, apellidos, password, email) VALUES (?, ?, ?, ?)";
        stmtInsertUsuario = conn.prepareStatement(sqlInsertUsuario, Statement.RETURN_GENERATED_KEYS);
        stmtInsertUsuario.setString(1, nombre);
        stmtInsertUsuario.setString(2, apellidos);
        stmtInsertUsuario.setString(3, hashedPassword);
        stmtInsertUsuario.setString(4, email);
        stmtInsertUsuario.executeUpdate();

        // Obtener el ID del usuario 
        int idUsuario;
        try (ResultSet generatedKeys = stmtInsertUsuario.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                idUsuario = generatedKeys.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID del usuario insertado.");
            }
        }

        // Insertar roles del usuario en la tabla usuarios_rol
        if (roles != null && idUsuario != -1) {
            String sqlInsertUsuarioRol = "INSERT INTO usuarios_rol (id_usuario, id_rol) VALUES (?, (SELECT id_rol FROM roles WHERE nombre_rol = ?))";
            stmtInsertUsuarioRol = conn.prepareStatement(sqlInsertUsuarioRol);
            for (String rol : roles) {
                // Verificar si el usuario ya tiene el rol
                String sqlSelectRol = "SELECT COUNT(*) AS count FROM usuarios_rol ur JOIN roles r ON ur.id_rol = r.id_rol WHERE ur.id_usuario = ? AND r.nombre_rol = ?";
                stmtSelectRol = conn.prepareStatement(sqlSelectRol);
                stmtSelectRol.setInt(1, idUsuario);
                stmtSelectRol.setString(2, rol);
                resultSet = stmtSelectRol.executeQuery();
                resultSet.next();
                int count = resultSet.getInt("count");
                if (count == 0) {
                    
                    // Inserta el rol solo si el usuario no lo tiene
                    stmtInsertUsuarioRol.setInt(1, idUsuario);
                    stmtInsertUsuarioRol.setString(2, rol);
                    stmtInsertUsuarioRol.executeUpdate();
                } else {
                    System.out.println("El usuario ya tiene el rol '" + rol + "'. No se insertará nuevamente.");
                }
                
                resultSet.close();
                stmtSelectRol.close();
            }
        }

        conn.commit();
    } catch (SQLException e) {
        if (conn != null) {
            conn.rollback();
        }
        e.printStackTrace();
        throw new SQLException("Error al registrar el usuario: " + e.getMessage(), e);
    } finally {
        // Cerrar recursos en el bloque finally
        if (stmtInsertUsuarioRol != null) {
            stmtInsertUsuarioRol.close();
        }
        if (stmtInsertUsuario != null) {
            stmtInsertUsuario.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
public boolean existeUsuarioConRol(String email, String[] roles) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;


        conn = conexionBD.getConnection();

        //  verificar si el usuario tiene alguno de los roles seleccionados
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS count ");
        sql.append("FROM usuarios u ");
        sql.append("INNER JOIN usuarios_rol ur ON u.id_usuario = ur.id_usuario ");
        sql.append("INNER JOIN roles r ON ur.id_rol = r.id_rol ");
        sql.append("WHERE u.email = ? AND r.nombre_rol IN (");

        // Agregar los roles seleccionados a la consulta sql
        for (int i = 0; i < roles.length; i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("?");
        }
        sql.append(")");

        stmt = conn.prepareStatement(sql.toString());
        stmt.setString(1, email);
        
        // Establecer los roles seleccionados en la consulta SQL
        for (int i = 0; i < roles.length; i++) {
            stmt.setString(i + 2, roles[i]);
        }

        rs = stmt.executeQuery();

        if (rs.next()) {
            int count = rs.getInt("count");
            return count > 0;
        } else {
            return false;
        }
    
    }



 
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            
            return null;
        }
    }

 public String iniciarSesion(String email, String password, String rol) {
    try (Connection conn = conexionBD.getConnection()) {
        String sql = "SELECT COUNT(*) AS count FROM usuarios u " +
                     "INNER JOIN usuarios_rol ur ON u.id_usuario = ur.id_usuario " +
                     "INNER JOIN roles r ON ur.id_rol = r.id_rol " +
                     "WHERE u.email = ? AND r.nombre_rol = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, rol);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    if (count > 0) {
                        // si el usuario tiene el rol especificado, ahora comprobamos la contraseña
                        String sqlPassword = "SELECT password FROM usuarios WHERE email = ?";
                        try (PreparedStatement passwordStmt = conn.prepareStatement(sqlPassword)) {
                            passwordStmt.setString(1, email);
                            try (ResultSet passwordRs = passwordStmt.executeQuery()) {
                                if (passwordRs.next()) {
                                    String hashedPassword = passwordRs.getString("password");
                                    if (verifyPassword(password, hashedPassword)) {
                                        return null; // Inicio de sesión exitoso
                                    } else {
                                        return "Contraseña incorrecta";
                                    }
                                }
                            }
                        }
                    } else {
                        return "El usuario no tiene el rol especificado";
                    }
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return "Error de conexión con la base de datos";
    }
    return "Usuario no encontrado";
}


    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            // Hash de la contraseña 
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(plainPassword.getBytes());
            String hashedPlainPassword = Base64.getEncoder().encodeToString(hashedBytes);

            // Comparar con la contraseña almacenada
            return hashedPlainPassword.equals(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int obtenerIdUsuario(String email) throws SQLException {
        int idUsuario = -1; 

        String sql = "SELECT id_usuario FROM usuarios WHERE email = ?";
        try (Connection conn = conexionBD.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idUsuario = rs.getInt("id_usuario");
                }
            }
        }

        return idUsuario;
    }

    public List<Usuario> listarUsuariosConRoles() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = conexionBD.getConnection()) {
            String sql = "SELECT u.id_usuario, u.nombre, u.apellidos, u.email, r.nombre_rol " +
                         "FROM usuarios u " +
                         "JOIN usuarios_rol ur ON u.id_usuario = ur.id_usuario " +
                         "JOIN roles r ON ur.id_rol = r.id_rol";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int idUsuario = rs.getInt("id_usuario");
                        String nombre = rs.getString("nombre");
                        String apellidos = rs.getString("apellidos");
                        String email = rs.getString("email");
                        String rol = rs.getString("nombre_rol");

                        // Buscar si el usuario  existe en la lista
                        Usuario usuario = null;
                        for (Usuario u : usuarios) {
                            if (u.getId() == idUsuario) {
                                usuario = u;
                                break;
                            }
                        }
                        // Si el usuario no existe en la lista, crear uno nuevo
                        if (usuario == null) {
                            usuario = new Usuario(idUsuario, nombre, apellidos, email);
                            usuario.setRoles(new ArrayList<>()); 
                            usuarios.add(usuario);
                        }
                        // Agregar el rol al usuario
                        usuario.getRoles().add(rol);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        }
        return usuarios;
    }

    public void darDeBajaUsuario(int idUsuario) {
        Connection conn = null;
        try {
            conn = conexionBD.getConnection();
            conn.setAutoCommit(false);

            String sqlDeleteUsuarioRol = "DELETE FROM usuarios_rol WHERE id_usuario = ?";
            try (PreparedStatement stmtDeleteUsuarioRol = conn.prepareStatement(sqlDeleteUsuarioRol)) {
                stmtDeleteUsuarioRol.setInt(1, idUsuario);
                stmtDeleteUsuarioRol.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public int obtenerUsuarioIdPorEmail(String email, String rol) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = conexionBD.getConnection();
            String sql = "SELECT u.id_usuario FROM usuarios u " +
                         "INNER JOIN usuarios_rol ur ON u.id_usuario = ur.id_usuario " +
                         "INNER JOIN roles r ON ur.id_rol = r.id_rol " +
                         "WHERE u.email = ? AND r.nombre_rol = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, rol);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_usuario");
            } else {
                return -1; // Usuario no encontrado
            }
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
    }

    public boolean modificarUsuario(int idUsuario, String nombre, String apellidos, String email, String password, String[] roles) throws SQLException {
        Connection conn = null;
        PreparedStatement stmtUpdateUsuario = null;
        PreparedStatement stmtDeleteRolesUsuario = null;
        PreparedStatement stmtInsertRolesUsuario = null;
        try {
            conn = conexionBD.getConnection();
            conn.setAutoCommit(false);

           
            String hashedPassword = hashPassword(password);

            // Actualiza los datos  del usuario
            String sqlUpdateUsuario = "UPDATE usuarios SET nombre = ?, apellidos = ?, email = ?, password = ? WHERE id_usuario = ?";
            stmtUpdateUsuario = conn.prepareStatement(sqlUpdateUsuario);
            stmtUpdateUsuario.setString(1, nombre);
            stmtUpdateUsuario.setString(2, apellidos);
            stmtUpdateUsuario.setString(3, email);
            stmtUpdateUsuario.setString(4, hashedPassword); // Guardar la contraseña hasheada
            stmtUpdateUsuario.setInt(5, idUsuario);
            int rowsUpdated = stmtUpdateUsuario.executeUpdate();

            if (rowsUpdated == 0) {
                conn.rollback();
                return false; 
            }

            // Elimina roles anteriores del usuario
            String sqlDeleteRolesUsuario = "DELETE FROM usuarios_rol WHERE id_usuario = ?";
            stmtDeleteRolesUsuario = conn.prepareStatement(sqlDeleteRolesUsuario);
            stmtDeleteRolesUsuario.setInt(1, idUsuario);
            stmtDeleteRolesUsuario.executeUpdate();

            // Insertar nuevos roles del usuario
            if (roles != null) {
                String sqlInsertRolesUsuario = "INSERT INTO usuarios_rol (id_usuario, id_rol) VALUES (?, (SELECT id_rol FROM roles WHERE nombre_rol = ?))";
                stmtInsertRolesUsuario = conn.prepareStatement(sqlInsertRolesUsuario);
                for (String rol : roles) {
                    stmtInsertRolesUsuario.setInt(1, idUsuario);
                    stmtInsertRolesUsuario.setString(2, rol);
                    stmtInsertRolesUsuario.executeUpdate();
                }
            }

            conn.commit();
            return true; // Modificación exitosa
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
         
            if (stmtUpdateUsuario != null) {
                stmtUpdateUsuario.close();
            }
            if (stmtDeleteRolesUsuario != null) {
                stmtDeleteRolesUsuario.close();
            }
            if (stmtInsertRolesUsuario != null) {
                stmtInsertRolesUsuario.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
