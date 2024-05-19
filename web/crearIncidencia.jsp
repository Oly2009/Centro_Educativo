<%@page import="java.util.List"%>
<%@page import="modelo.Equipo"%>
<%@page import="modelo.Incidencia"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Incidencia</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h1>Crear Incidencia</h1>

        <%-- Mostrar mensaje de error si existe uno --%>
        <%
            Boolean isError = (Boolean) request.getAttribute("error");
            String errorMessage = (String) request.getAttribute("message");
            if (isError != null && isError) {
        %>
            <p class="error"><%= errorMessage %></p>
        <% } %>

        <%-- Mostrar mensaje de éxito si la incidencia fue agregada --%>
        <%
            Boolean agregado = (Boolean) request.getAttribute("agregado");
            if (agregado != null && agregado) {
        %>
            <p class="success">Incidencia agregada exitosamente.</p>
        <% } %>

        <form action="Controlador" method="post" class="user-form">
            <input type="hidden" name="submit" value="AgregarIncidencias">

            <div class="form-group">
                <label for="idEquipo">Equipo:</label>
                <select id="idEquipo" name="idEquipo" class="form-input">
                    <option value="">Selecciona un equipo</option>
                    <% 
                    List<Equipo> equipos = (List<Equipo>) request.getAttribute("equipos");
                    if (equipos != null) {
                        for (Equipo equipo : equipos) {
                    %>
                    <option value="<%= equipo.getNombreEquipo() %>"><%= equipo.getNombreEquipo() %></option>
                    <% 
                        }
                    }
                    %>
                </select>
            </div>

            <div class="form-group">
                <label for="estado">Estado:</label>
                <input type="text" id="estado" name="estado" value="Pendiente" readonly class="form-input">
            </div>

            <div class="form-group">
                <label for="descripcion">Descripción:</label>
                <textarea id="descripcion" name="descripcion" rows="4" cols="50" class="form-input"></textarea>
            </div>

            <div class="form-group">
                <label for="prioridad">Prioridad:</label>
                <select id="prioridad" name="prioridad" class="form-input" required>
                    <option value="Alta">Alta</option>
                    <option value="Media">Media</option>
                    <option value="Baja">Baja</option>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn">Agregar Incidencia</button>
               
            </div>
        </form>
                 <form action="Controlador" method="post">
                    <input type="hidden" name="submit" value="Volver">
                    <button type="submit" class="btn">Volver</button>
                </form>
    </div>
</body>
</html>
