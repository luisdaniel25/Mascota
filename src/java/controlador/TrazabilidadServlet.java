package controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.ServicioTrasabilidad;
import modelo.Trazabilidad;

import java.io.IOException;
import java.util.List;

@WebServlet("/trazabilidad") // Esta es la URL que usarás en el navegador
public class TrazabilidadServlet extends HttpServlet {

    private final ServicioTrasabilidad servicio = new ServicioTrasabilidad();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Llamamos a un método listarTrazas() que necesitamos crear en ServicioTrasabilidad
            List<Trazabilidad> trazas = servicio.listarTrazas();

            req.setAttribute("trazas", trazas); // Mandamos la lista al JSP
            req.getRequestDispatcher("/vista/trazabilidad.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error cargando trazabilidad");
        }
    }
}
