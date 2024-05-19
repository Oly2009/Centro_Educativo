<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Menú para TIC</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <div class="main-container menu-container">
        <h1>Menú para TIC</h1>
        
        <div class="section">
            <h2>Gestión de Usuarios</h2>
            <table>
                <tr>
                    <th>Acción</th>
                    <th>Descripción</th>
                </tr>
                <tr>
                    <td>Listar usuarios</td>
                    <td>Ver la lista de usuarios registrados</td>
                </tr>
                <tr>
                    <td>Registrar Usuario</td>
                    <td>Agregar un nuevo usuario</td>
                </tr>
                <tr>
                    <td>Dar de baja</td>
                    <td>Eliminar un usuario existente</td>
                </tr>
                <tr>
                    <td>Modificar Usuario</td>
                    <td>Editar los datos de un usuario</td>
                </tr>
            </table>
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="Listar">
                <button type="submit" class="btn">Listar usuarios</button>
            </form>
            <form action="agregarUsuarios.jsp" method="post">
                <input type="hidden" name="accion" value="Agregar">
                <button type="submit" class="btn">Registrar Usuario</button>
            </form>
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="BajaUsuarios">
                <button type="submit" class="btn">Dar de baja</button>
            </form>
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="ModificarUsuario">
                <button type="submit" class="btn">Modificar Usuario</button>
            </form>
        </div>
        
        <div class="section">
            <h2>Gestión de Incidencias</h2>
            <table>
                <tr>
                    <th>Acción</th>
                    <th>Descripción</th>
                </tr>
                <tr>
                    <td>Listar Equipos</td>
                    <td>Ver la lista de equipos</td>
                </tr>
                <tr>
                    <td>Listar Incidencias</td>
                    <td>Ver la lista de incidencias reportadas</td>
                </tr>
                <tr>
                    <td>Crear Incidencia</td>
                    <td>Reportar una nueva incidencia</td>
                </tr>
                <tr>
                    <td>Borrar Incidencia</td>
                    <td>Eliminar una incidencia existente</td>
                </tr>
                <tr>
                    <td>Modificar Incidencias</td>
                    <td>Editar los datos de una incidencia</td>
                </tr>
            </table>
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="ListarEquipos">
                <button type="submit" class="btn">Listar Equipos</button>
            </form>
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="ListarIncidencias">
                <button type="submit" class="btn">Listar Incidencias</button>
            </form>
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="CrearIncidencia">
                <button type="submit" class="btn">Crear Incidencia</button>
            </form>
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="ListarIncidenciasEquipo">
                <button type="submit" class="btn">Borrar Incidencia</button>
            </form>
            <form action="Controlador" method="post">
                <input type="hidden" name="submit" value="ModificarIncidencias">
                <button type="submit" class="btn">Modificar Incidencias</button>
            </form>
        </div>
        
        <form action="Controlador" method="post" class="logout">
            <input type="hidden" name="submit" value="Salir">
            <button type="submit" class="btn logout-btn">Salir</button>
        </form>
    </div>
</body>
</html>
