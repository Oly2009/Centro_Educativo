<%-- 
    Document   : exito
    Created on : 20-mar-2024, 17:48:55
    Author     : Oly
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menú para Alumnos</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h1>Menú para Alumnos</h1>
        
        <div class="section">
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="CrearIncidencia">
                <button type="submit" class="btn">Crear Incidencia</button>
            </form>
            <br>
            <form action="index.jsp">
                <button type="submit" class="btn logout-btn">Salir</button>
            </form>
        </div>
    </div>
</body>
</html>
