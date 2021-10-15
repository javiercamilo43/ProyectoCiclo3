package co.edu.unbosque.ciclo3demo;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.json.simple.parser.ParseException;
 
/**
 * Servlet implementation class DemoServlet
 */
@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DemoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
 
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
        String listar = request.getParameter("Listar");
        String agregar = request.getParameter("Agregar");
        String eliminar = request.getParameter("eliminar");
        if (agregar != null) {
            agregarUsuario(request, response); 
        }
        if (listar != null) {
            try {
                listarUsuarios(request, response);
            } catch (ParseException | ServletException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        /*if (eliminar != null) {
        	eliminarUsuario(request, response);
        }*/
        
        }
 
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
    
    public void agregarUsuario(HttpServletRequest request, HttpServletResponse response) {
        Usuarios usuario = new Usuarios();
        usuario.setNombre_usuario(request.getParameter("nombre"));
        usuario.setCedula_usuario(Long.parseLong(request.getParameter("cedula")));
        usuario.setEmail_usuario(request.getParameter("email"));
        usuario.setUsuario(request.getParameter("usuario"));
        usuario.setPassword(request.getParameter("password"));
        int respuesta = 0;
        try{
            respuesta=TestJSON.postJSON(usuario);
            PrintWriter writer = response.getWriter();
            if (respuesta == 200) {
                writer.println(" Registro Agregado!");
            }else {
                writer.println(" Error: "+respuesta);
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        };
    }
    
    public void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ParseException, ServletException {
        try {
            ArrayList<Usuarios> lista = TestJSON.getJSON();
            String pagina = "/resultado.jsp";
            request.setAttribute("lista", lista);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(pagina);
            dispatcher.forward(request, response);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /*public void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) {
        long cedula = Long.parseLong(request.getParameter("cedula"));        
        int respuesta = 0;
        try{
            respuesta=TestJSON.deleteJSON(cedula);
            PrintWriter writer = response.getWriter();
            if (respuesta == 200) {
                writer.println(" Registro Eliminado!");
            }else {
                writer.println(" Error: "+respuesta);
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        };
    }*/
}
