package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.ConexionBD;
import modelo.Usuario;
import modelo.UsuarioDAO;
import javax.servlet.RequestDispatcher;
import modelo.Equipo;
import modelo.Incidencia;
import modelo.IncidenciaDAO;





public class Controlador extends HttpServlet {
    
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // Datos de la bd 
            String servidor = "localhost";
            String database = "centro";
            String usuarioBD = "Oly";
            String passwordBD = "";

           
            ConexionBD cnx = new ConexionBD(servidor, database, usuarioBD, passwordBD);
            UsuarioDAO usuarioDAO = new UsuarioDAO(cnx);
            IncidenciaDAO incidenciaDAO= new IncidenciaDAO(cnx);
            
     

            // Obtener parámetros de formularios
            String submit = request.getParameter("submit");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String rolSeleccionado = request.getParameter("rol");
            HttpSession  session = request.getSession();
             
             
            String  userEmail = (String) session.getAttribute("email");
            String userRol = (String) session.getAttribute("rol");

             switch (submit) {
                
            case "Login":
                    // Verificar inicio de sesión
                    String loginMessage = usuarioDAO.iniciarSesion(email, password, rolSeleccionado);
                    if (loginMessage == null) {
                        // crea la sesion 
                        session.setAttribute("email", email);
                        session.setAttribute("rol", rolSeleccionado);

                        // redirigir la pagina segun el rol seleccionado 
                        String redirectURL = "";
                        switch (rolSeleccionado) {
                            case "profesor":
                                redirectURL = "profesor.jsp";
                                break;
                            case "tic":
                                redirectURL = "tic.jsp";
                                break;
                            case "alumno":
                                redirectURL = "alumno.jsp";
                                break;
                        }
                        response.sendRedirect(redirectURL);
                    } else {
                       
                        request.setAttribute("error", true);
                        request.setAttribute("message", loginMessage);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                        dispatcher.forward(request, response);
                    }
                    break;

    case "Agregar":
    // Recoger los datos introducidos por el usuario del formulario 
    String nombre = request.getParameter("nombre");
    String apellidos = request.getParameter("apellidos");
   email = request.getParameter("email");
   password = request.getParameter("password");
    String[] roles = request.getParameterValues("roles");

    // Verificar si algún campo está vacío y muestra mensaje de error 
    if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || password.isEmpty() || roles == null || roles.length == 0) {
       
        request.setAttribute("mensajeError", "Por favor, complete todos los campos y seleccione al menos un rol.");
      
        RequestDispatcher dispatcher = request.getRequestDispatcher("agregarUsuarios.jsp");
        dispatcher.forward(request, response);
    } else {
        try {
            // verificar si el usuario ya existe con el mismo rol
            if (usuarioDAO.existeUsuarioConRol(email, roles)) {
                request.setAttribute("mensajeError", "El usuario ya tiene uno o más de los roles seleccionados.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("agregarUsuarios.jsp");
                dispatcher.forward(request, response);
            } else {
                // si todo va bien se registra 
                usuarioDAO.registrarUsuario(nombre, apellidos, email, password, roles);
             
                request.setAttribute("mensajeExito", "Usuario registrado con éxito.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("agregarUsuarios.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensajeError", "Error al registrar el usuario: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("agregarUsuarios.jsp");
            dispatcher.forward(request, response);
        }
    }
    break;
        case "Listar":

        List<Usuario> usuariosConRoles = usuarioDAO.listarUsuariosConRoles();
        
        request.setAttribute("usuarios", usuariosConRoles);
      
        request.getRequestDispatcher("listar.jsp").forward(request, response);
    
    break;
    
    
case "BajaUsuarios":
    //Recoge los id segun la seleccion del formulario
    String[] usuariosSeleccionados = request.getParameterValues("usuariosSeleccionados");

    if (usuariosSeleccionados != null && usuariosSeleccionados.length > 0) {
        try {
            //parsea los id a numeros enteros 
            for (String idUsuario : usuariosSeleccionados) {
                int id = Integer.parseInt(idUsuario);
                usuarioDAO.darDeBajaUsuario(id);
            }
            
            request.getSession().setAttribute("mensaje", "Usuarios dados de baja exitosamente");
        } catch (Exception e) {
           
            request.getSession().setAttribute("mensaje", "Error al dar de baja usuarios: " + e.getMessage());
        }
    } else {
        request.getSession().setAttribute("mensaje", "No se seleccionaron usuarios para dar de baja");
    }

    // actualiza el formulario
    response.sendRedirect("baja.jsp");
    break;
    
  case "ModificarUsuario":
      //recoger los datos del formulario
                    if (request.getParameter("idUsuario") != null) {
                        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                       nombre = request.getParameter("nombre");
                      apellidos = request.getParameter("apellidos");
                        String emailMod = request.getParameter("email");
                        String passwordMod = request.getParameter("password");
                        String[] rolesMod = request.getParameterValues("roles");

                 try {
                     //procedemos a la modificacion
            boolean modificacionExitosa = usuarioDAO.modificarUsuario(idUsuario, nombre, apellidos, emailMod, passwordMod, rolesMod);
            if (modificacionExitosa) {
                request.getSession().setAttribute("mensaje", "Usuario modificado exitosamente.");
            } else {
                request.getSession().setAttribute("mensaje", "No se pudo modificar el usuario.");
            }
        } catch (SQLException e) {
            request.getSession().setAttribute("mensaje", "Error al procesar la solicitud: " + e.getMessage());
        }
                 //si es exitoso me recarga el metodo 
        response.sendRedirect("Controlador?submit=ModificarUsuario");
        return; 
    }
//lista los usuarios 
    List<Usuario> usuariosModificar = usuarioDAO.listarUsuariosConRoles();
    request.setAttribute("usuarios", usuariosModificar);
    String mensaje = (String) request.getSession().getAttribute("mensaje");
    if (mensaje != null) {
        request.setAttribute("mensaje", mensaje);
        request.getSession().removeAttribute("mensaje");
    }
    //redirige a la pagina de modificacion 
    RequestDispatcher dispatcher = request.getRequestDispatcher("modificar.jsp");
    dispatcher.forward(request, response);
    break;

    
 case "ListarEquipos":
                try {
                    // Obtiene  la lista de equipos
                    List<Equipo> equipos = incidenciaDAO.listarEquipos();
                    request.setAttribute("equipos", equipos);

                    // redirige a la pagina 
                     dispatcher = request.getRequestDispatcher("listarEquipos.jsp");
                    dispatcher.forward(request, response);
                } catch (SQLException e) {
                    e.printStackTrace();
                    dispatcher = request.getRequestDispatcher("error.jsp");
                  dispatcher.forward(request, response);
                }
                break;
                
                

   
    case "ListarIncidencias":
   // recoge el rol y el email del usuario de la sesión 
    userEmail = (String) session.getAttribute("email");
    userRol = (String) session.getAttribute("rol");

    // obtiene la lista segun usuario que ha iniciado sesion
    List<Equipo> equiposConIncidencias = null;
    try {
        equiposConIncidencias = incidenciaDAO.listarEquiposConIncidencias(userEmail, userRol);
    } catch (SQLException e) {
       
        e.printStackTrace();
        
        request.getSession().setAttribute("mensaje", "Error al obtener equipos con incidencias.");
       
        response.sendRedirect("error.jsp");
        return; 
    }

   
    request.setAttribute("equiposConIncidencias", equiposConIncidencias);

    // redirecciona a la pagina de listar incidencias 
 dispatcher = request.getRequestDispatcher("listarIncidencias.jsp");
    dispatcher.forward(request, response);
    break;
   
                
      case "CrearIncidencia":
          
    // recoge la lista de equipos 
    List<Equipo> equipos = incidenciaDAO.listarEquipos();
    
    // la guarda 
    request.setAttribute("equipos", equipos);
    
    // redirecciona a la pagina para crear una incidencia 
    request.getRequestDispatcher("crearIncidencia.jsp").forward(request, response);
    break;

case "AgregarIncidencias":
    // recoge el id del equipo del formulario
    String nombreEquipo = request.getParameter("idEquipo");
    int idEquipo = incidenciaDAO.obtenerIdEquipoPorNombre(nombreEquipo);

    // Verificar si encuentra el equipo
    if (idEquipo != -1) {
        // crear una nueva incidencia con los datos recogidos  del formulario
        Incidencia incidencia = new Incidencia();
        incidencia.setIdEquipo(idEquipo);
        incidencia.setEstado(request.getParameter("estado")); 
        incidencia.setDescripcion(request.getParameter("descripcion"));
        incidencia.setPrioridad(request.getParameter("prioridad"));

        // comprueba el usuario de la sesion
        session = request.getSession();
        userEmail = (String) session.getAttribute("email");
        userRol = (String) session.getAttribute("rol");

        try {
            // llama al metodo y inserta la incidencia 
            boolean agregado = incidenciaDAO.agregarIncidencia(incidencia, userEmail, userRol);
            request.setAttribute("agregado", agregado);
            
            // redirecciona al formulario
           dispatcher = request.getRequestDispatcher("/crearIncidencia.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace(); 
          //si hay error muestra mensaje y vuelve a recargar la pagina 
            request.setAttribute("error", true);
            request.setAttribute("message", "Error al agregar la incidencia. Por favor, inténtelo de nuevo.");
          dispatcher = request.getRequestDispatcher("/crearIncidencia.jsp");
            dispatcher.forward(request, response);
        }
    } else {
        // Si no se encontróequipo, muestra un mensaje de error y recarga la pagina 
        request.setAttribute("error", true);
        request.setAttribute("message", "Equipo no encontrado. Por favor, seleccione un equipo válido.");
   
       dispatcher = request.getRequestDispatcher("/crearIncidencia.jsp");
        dispatcher.forward(request, response);
    }
    break;

     
 case "ListarIncidenciasEquipo":
    //identifica quien ha iniciado sesion
    userEmail = (String) session.getAttribute("email");
    userRol = (String) session.getAttribute("rol");

  
       equiposConIncidencias = null;
    try {
        equiposConIncidencias = incidenciaDAO.listarEquiposConIncidencias(userEmail, userRol);
    } catch (SQLException e) {
        
        e.printStackTrace();
    
        request.getSession().setAttribute("mensaje", "Error al obtener equipos con incidencias.");
        
        response.sendRedirect("error.jsp");
        return; 
    }

    // muestra los equipos
    request.setAttribute("equiposConIncidencias", equiposConIncidencias);

    // redirige a la pagina de borrar
    dispatcher = request.getRequestDispatcher("borrarIncidencia.jsp");
    dispatcher.forward(request, response);
    break;

case "BorrarIncidenciasConfirmar":
    
    // recoge los id de los equipos que se van a borrar
    idEquipo = Integer.parseInt(request.getParameter("idEquipo"));
    String[] incidenciasSeleccionadas = request.getParameterValues("idIncidenciaSeleccionada");

    // comprueba si se ha seleccionado 
    if (incidenciasSeleccionadas == null || incidenciasSeleccionadas.length == 0) {
      
        request.getSession().setAttribute("mensaje", "Por favor, seleccione al menos una incidencia para borrar.");
        // redirecciona a case anterior para listar otraves
        response.sendRedirect("Controlador?submit=ListarIncidenciasEquipo");
        break;
    }

    // parsea los id de las incidencias 
    List<Integer> idIncidencias = new ArrayList<>();
    for (String incidenciaId : incidenciasSeleccionadas) {
        idIncidencias.add(Integer.parseInt(incidenciaId));
    }

    // borra y si se bora bien muestra mensaje de exito si no de error
    try {
        boolean borradoExitoso = incidenciaDAO.borrarIncidenciasEquipo(idEquipo, idIncidencias, userEmail, userRol);
        if (borradoExitoso) {
           
            request.getSession().setAttribute("mensaje", "Incidencias borradas exitosamente.");
        } else {
          
            request.getSession().setAttribute("mensaje", "No se pudo borrar alguna de las incidencias.");
        }
    } catch (SQLException e) {
        
        e.printStackTrace();
  
        request.getSession().setAttribute("mensaje", "Error al procesar la solicitud: " + e.getMessage());
    }

    // redirecciona a case anterior
    response.sendRedirect("Controlador?submit=ListarIncidenciasEquipo");
    break;

case "ModificarIncidencias":
    try {
        // recoge datos del usuario que ha iniciado sesion
       userEmail = (String) session.getAttribute("email");
       userRol = (String) session.getAttribute("rol");

        // comprueba los datos que se quiere modificar del formulario 
        String[] idEquipos = request.getParameterValues("idEquipo");
        String[] idIncidenciasArr = request.getParameterValues("idIncid");

        if (idEquipos == null || idIncidenciasArr == null) {
            // lista las incidencias 
            equiposConIncidencias = incidenciaDAO.listarEquiposConIncidencias(userEmail, userRol);
            request.setAttribute("equiposConIncidencias", equiposConIncidencias);
            dispatcher = request.getRequestDispatcher("modificarIncidencias.jsp");
            dispatcher.forward(request, response);
        } else {
            // modifica los datos del formulario
            List<Incidencia> incidenciasModificar = new ArrayList<>();
            for (int i = 0; i < idIncidenciasArr.length; i++) {
               idEquipo = Integer.parseInt(idEquipos[i]);
                int idIncid = Integer.parseInt(idIncidenciasArr[i]);
                String estado = request.getParameter("estado" + idIncid); 
                String prioridad = request.getParameter("prioridad" + idIncid); 

                //  le asigna los nuevos datos 
                Incidencia incidencia = new Incidencia();
                incidencia.setIdIncidencia(idIncid);
                incidencia.setIdEquipo(idEquipo);
                incidencia.setEstado(estado);
                incidencia.setPrioridad(prioridad);

                // añadir la incidencia a la lista
                incidenciasModificar.add(incidencia);
            }

            // Llamar al método para modificar las incidencia
            boolean modificacionesExitosas = incidenciaDAO.modificarIncidencias(incidenciasModificar, userEmail, userRol);

            if (modificacionesExitosas) {
              
                request.getSession().setAttribute("mensaje", "Incidencias modificadas exitosamente.");
            } else {
               
                request.getSession().setAttribute("mensaje", "No se pudieron modificar todas las incidencias.");
            }

            // Redirige al metodo 
            response.sendRedirect("Controlador?submit=ModificarIncidencias");
        }
    } catch (SQLException e) {
      
        e.printStackTrace();
       
        request.getSession().setAttribute("mensaje", "Error al procesar la solicitud: " + e.getMessage());
        response.sendRedirect("error.jsp");
    }
    break;

    
    case "Volver":
   
    session = request.getSession();
    userEmail = (String) session.getAttribute("email");
    userRol = (String) session.getAttribute("rol");

 
    if (userEmail != null && userRol != null) {
        
        switch (userRol) {
            case "tic":
                response.sendRedirect("tic.jsp");
                break;
            case "profesor":
                response.sendRedirect("profesor.jsp");
                break;
            case "alumno":
                response.sendRedirect("alumno.jsp");
                break;
            default:
                
                response.sendRedirect("error.jsp");
                break;
          
        }
    } else {
        
        response.sendRedirect("error.jsp");
    }
    break;
    case "Salir":
    response.sendRedirect("index.jsp");
    break;
            }
        } catch (Exception e) {
         
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
