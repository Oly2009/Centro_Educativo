<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menú para Profesores</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h1>Menú para Profesores</h1>
        
        <div class="section">
            <h2>Gestión de Incidencias:</h2>
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="CrearIncidencia">
                <button type="submit" class="btn">Crear Incidencia</button>
            </form>
            <br>
             <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="ListarIncidencias">
                <button type="submit" class="btn">Listar Incidencias</button>
            </form>
            <br>
             <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="ListarIncidenciasEquipo">
                <button type="submit" class="btn">Borrar Incidencia</button>
            </form>
            <br>
            <form action="index.jsp">
                <button type="submit" class="btn logout-btn">Salir</button>
            </form>
        </div>
    </div>
</body>
</html>
