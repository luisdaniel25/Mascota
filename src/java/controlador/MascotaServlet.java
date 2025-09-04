package controlador; // Paquete del servlet

// Importar DAO para operaciones CRUD con Mascota
import dao.MascotaDAO;

// Importar librerías de Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importar modelos
import modelo.Mascota;
import modelo.EmailService;
import modelo.ServicioTrasabilidad;
import modelo.CertificadoService;

// Librerías Java estándar
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

// Definir URL de acceso al servlet
@WebServlet("/mascotas")
public class MascotaServlet extends HttpServlet {

    // Instancia del DAO para operaciones con la base de datos
    private MascotaDAO dao = new MascotaDAO();

    // Servicio para registrar acciones (insertar, actualizar, eliminar)
    private ServicioTrasabilidad trazService = new ServicioTrasabilidad();

    // Correo fijo para enviar notificaciones
    private String DESTINATARIO = "tapiasdaniel44@gmail.com";

    // Método POST para insertar, actualizar, eliminar o generar certificado
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Evitar problemas de acentos y caracteres especiales
        String action = req.getParameter("accion"); // Obtener acción del formulario
        String msg = ""; // Mensaje de resultado para mostrar al usuario

        try {

            // INSERTAR NUEVA MASCOTA
            if ("insertar".equals(action)) {
                Mascota mascota = new Mascota(); // Crear objeto mascota
                mascota.setNombre(req.getParameter("nombre")); // Obtener nombre del formulario
                mascota.setEspecie(req.getParameter("especie")); // Obtener especie
                // Obtener edad; si está vacío usar 0
                mascota.setEdad(req.getParameter("edad") != null && !req.getParameter("edad").isEmpty()
                        ? Integer.parseInt(req.getParameter("edad")) : 0);

                dao.insertar(mascota); // Guardar en la base de datos
                trazService.registrar("INSERTAR", mascota); // Registrar acción en trazabilidad

                // Preparar cuerpo del correo en HTML
                String cuerpo = "<h2>🐾 Nueva Mascota Registrada 🐾</h2>"
                        + "<table border='1' cellpadding='5' cellspacing='0'>"
                        + "<tr><th>ID Mascota</th><td>" + mascota.getId() + "</td></tr>"
                        + "<tr><th>Nombre</th><td>" + mascota.getNombre() + "</td></tr>"
                        + "<tr><th>Especie</th><td>" + mascota.getEspecie() + "</td></tr>"
                        + "<tr><th>Edad</th><td>" + mascota.getEdad() + "</td></tr>"
                        + "<tr><th>Fecha/Hora</th><td>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td></tr>"
                        + "</table>";

                // Enviar correo de notificación
                String resultadoCorreo = EmailService.enviarCorreo(DESTINATARIO, "Mascota agregada", cuerpo);
                msg = "Mascota agregada correctamente. "
                        + ("OK".equals(resultadoCorreo) ? "Correo enviado." : "️ " + resultadoCorreo);

                // ACTUALIZAR MASCOTA
            } else if ("actualizar".equals(action)) {
                Mascota mascota = new Mascota(); // Crear objeto mascota
                mascota.setId(Integer.parseInt(req.getParameter("id"))); // Obtener ID del formulario
                mascota.setNombre(req.getParameter("nombre")); // Actualizar nombre
                mascota.setEspecie(req.getParameter("especie")); // Actualizar especie
                // Actualizar edad
                mascota.setEdad(req.getParameter("edad") != null && !req.getParameter("edad").isEmpty()
                        ? Integer.parseInt(req.getParameter("edad")) : 0);

                dao.actualizar(mascota); // Actualizar en BD
                trazService.registrar("ACTUALIZAR", mascota); // Registrar acción

                // Preparar cuerpo del correo HTML
                String cuerpo = "<h2>🐾 Mascota Actualizada 🐾</h2>"
                        + "<table border='1' cellpadding='5' cellspacing='0'>"
                        + "<tr><th>ID Mascota</th><td>" + mascota.getId() + "</td></tr>"
                        + "<tr><th>Nombre</th><td>" + mascota.getNombre() + "</td></tr>"
                        + "<tr><th>Especie</th><td>" + mascota.getEspecie() + "</td></tr>"
                        + "<tr><th>Edad</th><td>" + mascota.getEdad() + "</td></tr>"
                        + "<tr><th>Fecha/Hora</th><td>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td></tr>"
                        + "</table>";

                // Enviar correo de actualización
                String resultadoCorreo = EmailService.enviarCorreo(DESTINATARIO, "Mascota actualizada", cuerpo);
                msg = "Mascota actualizada correctamente. "
                        + ("OK".equals(resultadoCorreo) ? "Correo enviado." : "⚠️ " + resultadoCorreo);

                // ELIMINAR MASCOTA
            } else if ("eliminar".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id")); // Obtener ID a eliminar
                Mascota mascota = dao.obtenerPorId(id); // Obtener datos antes de eliminar
                dao.eliminar(id); // Eliminar de la BD
                trazService.registrar("ELIMINAR", mascota); // Registrar acción

                // Cuerpo correo HTML
                String cuerpo = "<h2>🐾 Mascota Eliminada 🐾</h2>"
                        + "<table border='1' cellpadding='5' cellspacing='0'>"
                        + "<tr><th>ID Mascota</th><td>" + mascota.getId() + "</td></tr>"
                        + "<tr><th>Nombre</th><td>" + mascota.getNombre() + "</td></tr>"
                        + "<tr><th>Especie</th><td>" + mascota.getEspecie() + "</td></tr>"
                        + "<tr><th>Edad</th><td>" + mascota.getEdad() + "</td></tr>"
                        + "<tr><th>Fecha/Hora</th><td>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td></tr>"
                        + "</table>";

                // Enviar correo de eliminación
                String resultadoCorreo = EmailService.enviarCorreo(DESTINATARIO, "Mascota eliminada", cuerpo);
                msg = "Mascota eliminada correctamente. "
                        + ("OK".equals(resultadoCorreo) ? "Correo enviado." : "⚠️ " + resultadoCorreo);

                // GENERAR CERTIFICADO PDF
            } else if ("certificado".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id")); // Obtener ID
                Mascota mascota = dao.obtenerPorId(id); // Obtener datos de mascota

                byte[] pdf = CertificadoService.generarCertificado(mascota); // Generar PDF

                // Cuerpo del correo HTML
                String cuerpo = "<h2>🐾 Certificado de Registro de Mascota 🐾</h2>"
                        + "<p>Adjunto encontrarás el certificado oficial de registro de tu mascota:</p>"
                        + "<table border='1' cellpadding='5' cellspacing='0'>"
                        + "<tr><th>ID Mascota</th><td>" + mascota.getId() + "</td></tr>"
                        + "<tr><th>Nombre</th><td>" + mascota.getNombre() + "</td></tr>"
                        + "<tr><th>Especie</th><td>" + mascota.getEspecie() + "</td></tr>"
                        + "<tr><th>Edad</th><td>" + mascota.getEdad() + "</td></tr>"
                        + "<tr><th>Fecha/Hora</th><td>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "</td></tr>"
                        + "</table>";

                // Enviar correo con PDF adjunto
                String resultadoCorreo = EmailService.enviarCorreoConAdjunto(
                        DESTINATARIO,
                        "Certificado de Registro de Mascota",
                        cuerpo,
                        pdf,
                        "Certificado_Mascota_" + mascota.getId() + ".pdf"
                );

                msg = "Certificado generado y enviado. "
                        + ("OK".equals(resultadoCorreo) ? "Correo enviado." : "⚠️ " + resultadoCorreo);

            } else {
                msg = "Acción no reconocida."; // Acción inválida
            }

        } catch (Exception ex) {
            ex.printStackTrace(); // Mostrar errores en consola
            msg = "Error: " + ex.getMessage(); // Mensaje de error
        }

        // Guardar mensaje en sesión para mostrarlo en la vista
        req.getSession().setAttribute("msg", msg);

        // Redirigir a la lista de mascotas
        resp.sendRedirect(req.getContextPath() + "/mascotas?accion=listar");
    }

    // Método GET para listar mascotas o redirigir a la vista
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8"); // Evitar problemas de acentos
        String action = req.getParameter("accion"); // Obtener acción

        try {
            if ("listar".equals(action)) {
                List<Mascota> lista = dao.listar(); // Obtener todas las mascotas
                req.setAttribute("lista", lista); // Guardar en request
                req.getRequestDispatcher("vista/mascotas.jsp").forward(req, resp); // Enviar a JSP
            } else {
                resp.sendRedirect(req.getContextPath() + "/vista/mascotas.jsp"); // Redirigir por defecto
            }
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar error en consola
            resp.sendRedirect(req.getContextPath() + "/vista/mascotas.jsp?error=" + e.getMessage()); // Redirigir con error
        }
    }
}
