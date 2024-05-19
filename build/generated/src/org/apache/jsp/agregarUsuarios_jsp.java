package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class agregarUsuarios_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <title>Formulario de Registro de Usuario</title>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <h2>Registro de Nuevo Usuario</h2>\n");
      out.write("    \n");
      out.write("     <c:if test=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${not empty mensajeError}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\">\n");
      out.write("        <p style=\"color: red;\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${mensajeError}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</p>\n");
      out.write("    </c:if>\n");
      out.write("    <form action=\"Controlador\" method=\"post\">\n");
      out.write("        <input type=\"hidden\" name=\"accion\" value=\"Agregar\">\n");
      out.write("        <input type=\"text\" name=\"nombre\" id=\"nombre\" placeholder=\"Nombre de usuario\" /><br>\n");
      out.write("        <input type=\"text\" name=\"apellidos\" id=\"apellidos\" placeholder=\"Apellidos de usuario\" /><br>\n");
      out.write("        <input type=\"email\" name=\"email\" id=\"email\" placeholder=\"Email de usuario\" /><br>\n");
      out.write("        <input type=\"password\" name=\"password\" id=\"password\" placeholder=\"Contraseña\" /><br>\n");
      out.write("        <label>Roles:</label><br>\n");
      out.write("        <input type=\"checkbox\" name=\"roles\" id=\"profesor\" value=\"profesor\">\n");
      out.write("        <label for=\"profesor\">Profesor</label><br>\n");
      out.write("        <input type=\"checkbox\" name=\"roles\" id=\"alumno\" value=\"alumno\">\n");
      out.write("        <label for=\"alumno\">Alumno</label><br>\n");
      out.write("        <input type=\"checkbox\" name=\"roles\" id=\"tic\" value=\"tic\">\n");
      out.write("        <label for=\"tic\">TIC</label><br>\n");
      out.write("        <input type=\"submit\" value=\"Agregar\" name=\"submit\" />\n");
      out.write("        <input type=\"submit\" value=\"Volver\" name=\"submit\" />\n");
      out.write("    </form>\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
