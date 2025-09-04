package controlador;

import dao.MascotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Mascota;
import modelo.EmailService;
import modelo.ServicioTrasabilidad;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import modelo.CertificadoService;

@WebServlet("/mascotas")
public class MascotaServlet extends HttpServlet {

    private final MascotaDAO dao = new MascotaDAO();
    private final ServicioTrasabilidad trazService = new ServicioTrasabilidad();
    private static final String DESTINATARIO = "tapiasdaniel44@gmail.com";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("accion");
        String msg = "";

        try {
            if ("insertar".equals(action)) {
                Mascota mascota = new Mascota();
                mascota.setNombre(req.getParameter("nombre"));
                mascota.setEspecie(req.getParameter("especie"));
                mascota.setEdad(req.getParameter("edad") != null && !req.getParameter("edad").isEmpty()
                        ? Integer.parseInt(req.getParameter("edad")) : 0);

                dao.insertar(mascota);
                trazService.registrar("INSERTAR", mascota);

                String cuerpo = "<h2>🐾 Nueva Mascota Registrada 🐾</h2>"
                        + "<table border='1' cellpadding='5' cellspacing='0'>"
                        + "<tr><th>ID Mascota</th><td>" + mascota.getId() + "</td></tr>"
                        + "<tr><th>Nombre</th><td>" + mascota.getNombre() + "</td></tr>"
                        + "<tr><th>Especie</th><td>" + mascota.getEspecie() + "</td></tr>"
                        + "<tr><th>Edad</th><td>" + mascota.getEdad() + "</td></tr>"
                        + "<tr><th>Fecha/Hora</th><td>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td></tr>"
                        + "</table>";

                String resultadoCorreo = EmailService.enviarCorreo(DESTINATARIO, "Mascota agregada", cuerpo);
                msg = "Mascota agregada correctamente. " + ("OK".equals(resultadoCorreo) ? "Correo enviado." : "⚠️ " + resultadoCorreo);

            } else if ("actualizar".equals(action)) {
                Mascota mascota = new Mascota();
                mascota.setId(Integer.parseInt(req.getParameter("id")));
                mascota.setNombre(req.getParameter("nombre"));
                mascota.setEspecie(req.getParameter("especie"));
                mascota.setEdad(req.getParameter("edad") != null && !req.getParameter("edad").isEmpty()
                        ? Integer.parseInt(req.getParameter("edad")) : 0);

                dao.actualizar(mascota);
                trazService.registrar("ACTUALIZAR", mascota);

                String cuerpo = "<h2>🐾 Mascota Actualizada 🐾</h2>"
                        + "<table border='1' cellpadding='5' cellspacing='0'>"
                        + "<tr><th>ID Mascota</th><td>" + mascota.getId() + "</td></tr>"
                        + "<tr><th>Nombre</th><td>" + mascota.getNombre() + "</td></tr>"
                        + "<tr><th>Especie</th><td>" + mascota.getEspecie() + "</td></tr>"
                        + "<tr><th>Edad</th><td>" + mascota.getEdad() + "</td></tr>"
                        + "<tr><th>Fecha/Hora</th><td>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td></tr>"
                        + "</table>";

                String resultadoCorreo = EmailService.enviarCorreo(DESTINATARIO, "Mascota actualizada", cuerpo);
                msg = "Mascota actualizada correctamente. " + ("OK".equals(resultadoCorreo) ? "Correo enviado." : "⚠️ " + resultadoCorreo);

            } else if ("eliminar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Mascota mascota = dao.obtenerPorId(id); // Obtener antes de eliminar
                dao.eliminar(id);
                trazService.registrar("ELIMINAR", mascota);

                String cuerpo = "<h2>🐾 Mascota Eliminada 🐾</h2>"
                        + "<table border='1' cellpadding='5' cellspacing='0'>"
                        + "<tr><th>ID Mascota</th><td>" + mascota.getId() + "</td></tr>"
                        + "<tr><th>Nombre</th><td>" + mascota.getNombre() + "</td></tr>"
                        + "<tr><th>Especie</th><td>" + mascota.getEspecie() + "</td></tr>"
                        + "<tr><th>Edad</th><td>" + mascota.getEdad() + "</td></tr>"
                        + "<tr><th>Fecha/Hora</th><td>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td></tr>"
                        + "</table>";

                String resultadoCorreo = EmailService.enviarCorreo(DESTINATARIO, "Mascota eliminada", cuerpo);
                msg = "Mascota eliminada correctamente. " + ("OK".equals(resultadoCorreo) ? "Correo enviado." : "⚠️ " + resultadoCorreo);

            } else if ("certificado".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Mascota mascota = dao.obtenerPorId(id);

                byte[] pdf = CertificadoService.generarCertificado(mascota);

                String cuerpo = "<h2>🐾 Certificado de Registro de Mascota 🐾</h2>"
                        + "<p>Adjunto encontrarás el certificado oficial de registro de tu mascota:</p>"
                        + "<table border='1' cellpadding='5' cellspacing='0'>"
                        + "<tr><th>ID Mascota</th><td>" + mascota.getId() + "</td></tr>"
                        + "<tr><th>Nombre</th><td>" + mascota.getNombre() + "</td></tr>"
                        + "<tr><th>Especie</th><td>" + mascota.getEspecie() + "</td></tr>"
                        + "<tr><th>Edad</th><td>" + mascota.getEdad() + "</td></tr>"
                        + "<tr><th>Fecha/Hora</th><td>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td></tr>"
                        + "</table>";

                String resultadoCorreo = EmailService.enviarCorreoConAdjunto(
                        DESTINATARIO,
                        "Certificado de Registro de Mascota",
                        cuerpo,
                        pdf,
                        "Certificado_Mascota_" + mascota.getId() + ".pdf"
                );

                msg = "Certificado generado y enviado. " + ("OK".equals(resultadoCorreo) ? "Correo enviado." : "⚠️ " + resultadoCorreo);

            } else {
                msg = "Acción no reconocida.";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            msg = "Error: " + ex.getMessage();
        }

        req.getSession().setAttribute("msg", msg);
        resp.sendRedirect(req.getContextPath() + "/mascotas?accion=listar");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("accion");

        try {
            if ("listar".equals(action)) {
                List<Mascota> lista = dao.listar();
                req.setAttribute("lista", lista);
                req.getRequestDispatcher("vista/mascotas.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/vista/mascotas.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/vista/mascotas.jsp?error=" + e.getMessage());
        }
    }
}
