<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modificar Incidencias por Equipo</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h1>Modificar Incidencias por Equipo</h1>
        
        <!-- Mostrar mensaje de éxito o error -->
        <% 
        String mensaje = (String) request.getSession().getAttribute("mensaje");
        if (mensaje != null && !mensaje.isEmpty()) {
            out.println("<p class='error'>" + mensaje + "</p>");
            request.getSession().removeAttribute("mensaje");
        }
        %>
        
        <c:if test="${not empty equiposConIncidencias}">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>ID Equipo</th>
                            <th>Nombre Equipo</th>
                            <th>Estado</th>
                            <th>Descripción</th>
                            <th>Prioridad</th>
                            <th>Fecha de Inicio</th>
                            <th>Fecha de Fin</th>
                            <th>Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="equipo" items="${equiposConIncidencias}">
                            <c:forEach var="incidencia" items="${equipo.incidencias}">
                                <tr>
                                    <form action="Controlador" method="post">
                                        <td>
                                            <input type="hidden" name="idEquipo" value="${equipo.idEquipo}"/>
                                            <input type="hidden" name="idIncid" value="${incidencia.idIncidencia}"/>
                                            ${equipo.idEquipo}
                                        </td>
                                        <td>${equipo.nombreEquipo}</td>
                                        <td>
                                            <select name="estado${incidencia.idIncidencia}" class="form-input">
                                                <option value="pendiente" ${incidencia.estado == 'pendiente' ? 'selected' : ''}>Pendiente</option>
                                                <option value="reparado" ${incidencia.estado == 'reparado' ? 'selected' : ''}>Reparado</option>
                                            </select>
                                        </td>
                                        <td>${incidencia.descripcion}</td>
                                        <td>
                                            <select name="prioridad${incidencia.idIncidencia}" class="form-input">
                                                <option value="baja" ${incidencia.prioridad == 'baja' ? 'selected' : ''}>Baja</option>
                                                <option value="media" ${incidencia.prioridad == 'media' ? 'selected' : ''}>Media</option>
                                                <option value="alta" ${incidencia.prioridad == 'alta' ? 'selected' : ''}>Alta</option>
                                            </select>
                                        </td>
                                        <td>${incidencia.fechaInicio}</td>
                                        <td>${incidencia.fechaFin}</td>
                                        <td>
                                            <input type="hidden" name="submit" value="ModificarIncidencias"/>
                                            <button type="submit" class="btn">Modificar</button>
                                        </td>
                                    </form>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>

        <form action="Controlador" method="post" class="form-container">
            <input type="hidden" name="submit" value="Volver">
            <button type="submit" class="btn">Volver</button>
        </form>
        
        <c:if test="${empty equiposConIncidencias}">
            <p>No hay equipos con incidencias.</p>
        </c:if>
    </div>
</body>
</html>
