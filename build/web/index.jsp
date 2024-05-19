<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h1>Iniciar Sesión</h1>

        <%-- Mostrar mensaje de error si existe uno --%>
        <%
            Boolean isError = (Boolean) request.getAttribute("error");
            String errorMessage = (String) request.getAttribute("message");
            if (isError != null && isError) {
        %>
            <p class="error"><%= errorMessage %></p>
        <% } %>

        <form action="Controlador" method="post" class="user-form">
            <div class="form-group">
                <label for="email">Correo Electrónico:</label><br>
                <input type="email" id="email" name="email" class="form-input" required><br>
            </div>
            <div class="form-group">
                <label for="password">Contraseña:</label><br>
                <input type="password" id="password" name="password" class="form-input" required><br>
            </div>
            <div class="form-group">
                <label for="rol">Seleccione Rol:</label><br>
                <select id="rol" name="rol" class="form-input" required>
                    <option value="profesor">Profesor</option>
                    <option value="alumno">Alumno</option>
                    <option value="tic">TIC</option>
                </select><br><br>
            </div>
            <div class="form-actions">
                <input type="submit" value="Login" name="submit" class="btn"><br>
            </div>
            <div class="form-actions">
                <input type="reset" value="Limpiar" class="btn">
            </div>
        </form>
    </div>
</body>
</html>
