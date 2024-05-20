<%@ page import="java.util.List" %>
<%@ page import="modelo.Usuario" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modificar Usuarios</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Modificar Usuarios</h1>
        
        <c:if test="${not empty mensaje}">
            <p class="${mensaje.startsWith('Error') ? 'error' : 'success'}">${mensaje}</p>
        </c:if>
        
        <c:if test="${not empty usuarios}">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Apellidos</th>
                            <th>Email</th>
                            <th>Contraseña</th>
                            <th>Rol</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${usuarios}" var="usuario">
                            <tr>
                                <form id="form${usuario.id}" action="Controlador" method="post">
                                    <td>
                                        <input type="hidden" name="idUsuario" value="${usuario.id}" />
                                        ${usuario.id}
                                    </td>
                                    <td><input type="text" name="nombre" value="${usuario.nombre}" required /></td>
                                    <td><input type="text" name="apellidos" value="${usuario.apellidos}" required /></td>
                                    <td><input type="email" name="email" value="${usuario.email}" required /></td>
                                    <td>
                                        <input type="password" name="password" placeholder="********" />
                                        <input type="hidden" name="currentPassword" value="${usuario.password}" />
                                    </td>
                                    <td>
                                        <c:forEach var="rol" items="${usuario.roles}">
                                            <input type="checkbox" name="roles" value="${rol}" 
                                                   <c:if test="${usuario.roles.contains(rol)}">checked</c:if>>${rol}<br>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <input type="hidden" name="submit" value="ModificarUsuario">
                                        <button type="submit" class="btn">Modificar</button>
                                    </td>
                                </form>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        
        <form action="Controlador" method="post" class="btn-group">
            <input type="hidden" name="submit" value="Volver">
            <input type="submit" value="Volver" class="btn">
        </form>
       
    </div>
</body>
</html>
