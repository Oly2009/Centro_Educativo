<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listado de Equipos con Incidencias</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h1>Listado de Equipos con Incidencias</h1>
        
     
        
        <c:if test="${not empty equiposConIncidencias}">
            <form action="Controlador" method="post" class="form-container">
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
                                <th>Seleccionar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="equipo" items="${equiposConIncidencias}">
                                <c:forEach var="incidencia" items="${equipo.incidencias}">
                                    <tr>
                                        <td>${equipo.idEquipo}</td>
                                        <td>${equipo.nombreEquipo}</td>
                                        <td>${incidencia.estado}</td>
                                        <td>${incidencia.descripcion}</td>
                                        <td>${incidencia.prioridad}</td>
                                        <td>${incidencia.fechaInicio}</td>
                                        <td>${incidencia.fechaFin}</td>
                                        <td>
                                            <input type="checkbox" name="idIncidenciaSeleccionada" value="${incidencia.idIncidencia}">
                                            <!-- Agregar campo oculto para el ID del equipo -->
                                            <input type="hidden" name="idEquipo" value="${equipo.idEquipo}">
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="form-actions">
                    <input type="hidden" name="submit" value="BorrarIncidenciasConfirmar">
                    <button type="submit" class="btn">Borrar Incidencia</button>
                </div>
            </form>
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
