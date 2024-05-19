<%@page import="java.util.List"%>
<%@page import="modelo.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listar Usuarios</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h1>Listar Usuarios</h1>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Apellidos</th>
                        <th>Email</th>
                        <th>Roles</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                    // Obtener la lista de usuarios con roles del objeto request
                    List<Usuario> usuariosConRoles = (List<Usuario>) request.getAttribute("usuarios");
                    // Verificar si la lista no está vacía
                    if (usuariosConRoles != null && !usuariosConRoles.isEmpty()) {
                        // Iterar sobre la lista de usuarios con roles
                        for (Usuario usuario : usuariosConRoles) {
                    %>
                    <tr>
                        <td><%= usuario.getId() %></td>
                        <td><%= usuario.getNombre() %></td>
                        <td><%= usuario.getApellidos() %></td>
                        <td><%= usuario.getEmail() %></td>
                        <td>
                            <% 
                            // Obtener la lista de roles del usuario
                            List<String> roles = usuario.getRoles();
                            // Verificar si la lista de roles no está vacía
                            if (roles != null && !roles.isEmpty()) {
                                // Iterar sobre la lista de roles
                                for (String rol : roles) {
                            %>
                            <%= rol %><br>
                            <% 
                                }
                            }
                            %>
                        </td>
                    </tr>
                    <% 
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="5">No se encontraron usuarios con roles.</td>
                    </tr>
                    <% 
                    }
                    %>
                </tbody>
            </table>
        </div>
        
        <form action="Controlador" method="post">
            <input type="hidden" name="submit" value="Volver">
            <button type="submit" class="btn">Volver</button>
        </form>
    </div>
</body>
</html>
