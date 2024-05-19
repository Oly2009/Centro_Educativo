package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"es\">\n");
      out.write("<head>\n");
      out.write("    <meta charset=\"UTF-8\">\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <title>Iniciar Sesión</title>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("    <h2>Iniciar Sesión</h2>\n");
      out.write("    <form action=\"Controlador\" method=\"post\">\n");
      out.write("        <label for=\"email\">Correo Electrónico:</label><br>\n");
      out.write("        <input type=\"email\" id=\"email\" name=\"email\" required><br>\n");
      out.write("        <label for=\"password\">Contraseña:</label><br>\n");
      out.write("        <input type=\"password\" id=\"password\" name=\"password\" required><br>\n");
      out.write("        <label for=\"rol\">Seleccione Rol:</label><br>\n");
      out.write("        <select id=\"rol\" name=\"rol\" required>\n");
      out.write("            <option value=\"profesor\">Profesor</option>\n");
      out.write("            <option value=\"alumno\">Alumno</option>\n");
      out.write("            <option value=\"tic\">TIC</option>\n");
      out.write("        </select><br><br>\n");
      out.write("        <input type=\"submit\" value=\"Login\" name=\"submit\">\n");
      out.write("        <input type=\"reset\" value=\"Limpiar\" />\n");
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
