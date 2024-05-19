<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Listar Equipos</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h1>Listar Equipos</h1>
        
        <!-- Eliminado el bloque que muestra el mensaje -->
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID Equipo</th>
                        <th>Nombre</th>
                        <th>Características</th>
                        <th>Aula</th>
                        <th>Tipo</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="equipo" items="${equipos}">
                        <tr>
                            <td>${equipo.idEquipo}</td>
                            <td>${equipo.nombreEquipo}</td>
                            <td>${equipo.caracteristicas}</td>
                            <td>${equipo.aula}</td>
                            <td>${equipo.tipo}</td>
                        </tr>
                    </c:forEach>
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
