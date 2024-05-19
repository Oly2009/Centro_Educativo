<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulario de Registro de Usuario</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container">
        <h2>Registro de Nuevo Usuario</h2>

        <c:if test="${not empty mensajeError}">
            <p class="error">${mensajeError}</p>
        </c:if>

        <c:if test="${not empty mensajeExito}">
            <p class="success">${mensajeExito}</p>
        </c:if>

        <form class="user-form" action="Controlador" method="post">
            <input type="hidden" name="accion" value="Agregar">
            <div class="form-group">
                <input class="form-input" type="text" name="nombre" id="nombre" placeholder="Nombre de usuario" />
            </div>
            <div class="form-group">
                <input class="form-input" type="text" name="apellidos" id="apellidos" placeholder="Apellidos de usuario" />
            </div>
            <div class="form-group">
                <input class="form-input" type="email" name="email" id="email" placeholder="Email de usuario" />
            </div>
            <div class="form-group">
                <input class="form-input" type="password" name="password" id="password" placeholder="Contraseña" />
            </div>
            <div class="form-group">
                <label>Roles:</label><br>
                <input type="checkbox" name="roles" id="profesor" value="profesor">
                <label for="profesor">Profesor</label><br>
                <input type="checkbox" name="roles" id="alumno" value="alumno">
                <label for="alumno">Alumno</label><br>
                <input type="checkbox" name="roles" id="tic" value="tic">
                <label for="tic">TIC</label><br>
            </div>
            <div class="form-actions">
                <input class="btn" type="submit" value="Agregar" name="submit" />
               
            </div>
        </form>
        <form action="Controlador" method="post" class="btn-group">
            <input type="hidden" name="submit" value="Volver">
            <input type="submit" value="Volver" class="btn">
        </form>
    </div>
</body>
</html>
