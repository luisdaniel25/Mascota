package controlador;

// Librerías de iText para generar PDF
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

// DAO y modelo
import dao.MascotaDAO;
import modelo.Mascota;

// Librerías de Jakarta Mail para enviar correo
import jakarta.activation.DataHandler;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;

// Librerías de Servlet
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

// Librerías estándar de Java
import java.io.*;
import java.util.*;

@WebServlet("/enviarEmail") // URL para acceder al servlet
public class EmailMascotasServlet extends HttpServlet {

    // DAO para consultar mascotas
    private MascotaDAO dao = new MascotaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Datos del correo
        String remitente = "tapiasdaniel44@gmail.com"; // correo de envío
        String clave = "jmsy ufib pqzt hyzj";         // clave de aplicación Gmail
        String destino = req.getParameter("correo");   // correo receptor desde formulario

        // Configuración del servidor SMTP de Gmail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true"); // requiere autenticación
        props.put("mail.smtp.starttls.enable", "true"); // usa TLS
        props.put("mail.smtp.host", "smtp.gmail.com"); // servidor SMTP
        props.put("mail.smtp.port", "587"); // puerto TLS

        // Crear sesión con autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave); // usuario y clave
            }
        });

        try {

            // GENERAR PDF CON LISTA MASCOTAS
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); // PDF en memoria
            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();

            // Título del PDF
            Paragraph titulo = new Paragraph(
                    " Reporte de Mascotas Registradas ",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE)
            );
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            doc.add(titulo);

            // Crear tabla con 4 columnas
            PdfPTable tabla = new PdfPTable(4);
            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Especie");
            tabla.addCell("Edad");

            // Agregar datos de mascotas a la tabla
            for (Mascota m : dao.listar()) {
                tabla.addCell(String.valueOf(m.getId()));
                tabla.addCell(m.getNombre());
                tabla.addCell(m.getEspecie());
                tabla.addCell(String.valueOf(m.getEdad()));
            }

            // Agregar tabla al documento PDF
            doc.add(tabla);
            doc.close(); // cerrar documento

            // Obtener PDF como bytes
            byte[] pdfBytes = baos.toByteArray();

            // CREAR CORREO ELECTRÓNICO
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente)); // remitente
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino)); // receptor
            mensaje.setSubject("Reporte de Mascotas"); // asunto

            // Crear adjunto PDF
            MimeBodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfBytes, "application/pdf")));
            adjunto.setFileName("reporte_mascotas.pdf");

            // Crear contenido del correo (solo adjunto)
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(adjunto);
            mensaje.setContent(multipart);

            // Enviar correo
            Transport.send(mensaje);

            // Confirmación en la respuesta
            resp.getWriter().println("Correo enviado a " + destino);

        } catch (Exception e) {
            // Manejo de errores
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}
