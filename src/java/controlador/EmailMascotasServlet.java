package controlador;

// Librerías de iText para generar PDF
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

// DAO y modelo para obtener los datos de la base de datos
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

// Librerías de Java estándar
import java.io.*;
import java.util.*;

@WebServlet("/enviarEmail") // URL con la que se llama a este servlet
public class EmailMascotasServlet extends HttpServlet {

    // DAO para consultar mascotas
    private MascotaDAO dao = new MascotaDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // --- Datos de configuración del correo ---
        String remitente = "tapiasdaniel44@gmail.com"; // tu correo
        String clave = "jmsy ufib pqzt hyzj";                 // clave de aplicación de Gmail
        String destino = req.getParameter("correo");  // correo del receptor 

        // --- Configuración del servidor SMTP de Gmail ---
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // --- Autenticación con Gmail ---
        Session session = Session.getInstance(props,
                new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        try {
            // ================================
            //  Generar PDF con lista mascotas
            // ================================
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document doc = new Document();
            PdfWriter.getInstance(doc, baos);
            doc.open();
            // Título bonito
            Paragraph titulo = new Paragraph(
                    " Reporte de Mascotas Registradas ",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE)
            );
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            doc.add(titulo);
            // Crear tabla con encabezados
            PdfPTable tabla = new PdfPTable(4);
            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Especie");
            tabla.addCell("Edad");

            // Llenar la tabla con datos desde la base
            for (Mascota m : dao.listar()) {
                tabla.addCell(String.valueOf(m.getId()));
                tabla.addCell(m.getNombre());
                tabla.addCell(m.getEspecie());
                tabla.addCell(String.valueOf(m.getEdad()));
            }

            // Agregar la tabla al documento PDF
            doc.add(tabla);
            doc.close();

            // El PDF queda en memoria en forma de bytes
            byte[] pdfBytes = baos.toByteArray();

            // ================================
            // 2. Crear correo electrónico
            // ================================
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente)); // remitente
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino)); // receptor
            mensaje.setSubject("Reporte de Mascotas"); // asunto del correo

            // Crear adjunto con el PDF
            MimeBodyPart adjunto = new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new ByteArrayDataSource(pdfBytes, "application/pdf")));
            adjunto.setFileName("reporte_mascotas.pdf");

            // El correo tendrá solo el adjunto (sin texto adicional)
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(adjunto);
            mensaje.setContent(multipart);

            // ================================
            // 3. Enviar el correo
            // ================================
            Transport.send(mensaje);

            resp.getWriter().println("Correo enviado a " + destino);

        } catch (Exception e) {
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}
