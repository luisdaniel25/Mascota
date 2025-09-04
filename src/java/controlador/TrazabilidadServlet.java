package controlador; // Paquete del servlet

// Librerías de Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importar modelo y servicio de trazabilidad
import modelo.ServicioTrasabilidad;
import modelo.Trazabilidad;

import java.io.IOException;
import java.util.List;

// URL para acceder a este servlet
@WebServlet("/trazabilidad") // URL que se usará en el navegador
public class TrazabilidadServlet extends HttpServlet {

    private final ServicioTrasabilidad servicio = new ServicioTrasabilidad(); // Instancia del servicio

    // Método GET para mostrar la lista de trazas
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Obtener todas las trazas desde el servicio
            List<Trazabilidad> trazas = servicio.listarTrazas();

            req.setAttribute("trazas", trazas); // Guardar lista en request para enviar al JSP
            req.getRequestDispatcher("/vista/trazabilidad.jsp").forward(req, resp); // Enviar datos al JSP
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar error en consola
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error cargando trazabilidad"); // Mostrar error 500
        }
    }
}
