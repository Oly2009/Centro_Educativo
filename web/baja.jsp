<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Usuario" %>
<%@ page import="modelo.UsuarioDAO" %>
<%@ page import="modelo.ConexionBD" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dar de Baja Usuarios</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h1>Dar de Baja Usuarios</h1>
        
        <% 
        // Establecer la conexión con la base de datos
        ConexionBD conexionBD = new ConexionBD("localhost", "centro", "oly", "");
        UsuarioDAO usuarioDAO = new UsuarioDAO(conexionBD);
        List<Usuario> usuariosConRoles = usuarioDAO.listarUsuariosConRoles();
        request.setAttribute("usuarios", usuariosConRoles);
        %>
        
        <%-- Mostrar mensaje de éxito o error desde la sesión --%>
        <% 
        String mensaje = (String) session.getAttribute("mensaje");
        if (mensaje != null && !mensaje.isEmpty()) {
        %>
            <p class="error"><%= mensaje %></p>
        <% 
            session.removeAttribute("mensaje");
        }
        %>
        
        <div class="table-container">
            <form action="Controlador" method="post">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Email</th>
                            <th>Rol</th>
                            <th>Dar de Baja</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                        if (usuariosConRoles != null && !usuariosConRoles.isEmpty()) {
                            for (Usuario usuario : usuariosConRoles) {
                        %>
                        <tr>
                            <td><%= usuario.getId() %></td>
                            <td><%= usuario.getNombre() %></td>
                            <td><%= usuario.getApellidos() %></td>
                            <td><%= usuario.getEmail() %></td>
                            <td>
                                <% 
                                List<String> roles = usuario.getRoles();
                                if (roles != null && !roles.isEmpty()) {
                                    for (String rol : roles) {
                                        out.print(rol + "<br>");
                                    }
                                }
                                %>
                            </td>
                            <td><input type="checkbox" name="usuariosSeleccionados" value="<%= usuario.getId() %>"></td>
                        </tr>
                        <% 
                            }
                        } else {
                        %>
                        <tr>
                            <td colspan="6">No se encontraron usuarios con roles</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
                <br>
                <input type="hidden" name="submit" value="BajaUsuarios">
                <button type="submit" class="btn">Dar de Baja</button>
            </form>
        </div>
        
        <form action="Controlador" method="post">
            <input type="hidden" name="submit" value="Volver">
            <button type="submit" class="btn">Volver</button>
        </form>
    </div>

    <% conexionBD.cerrarConexion(); %> 
</body>
</html>
