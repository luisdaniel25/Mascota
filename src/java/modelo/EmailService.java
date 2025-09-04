package modelo; // Paquete modelo

// Librerías de Jakarta Mail
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    // Método para enviar correo con archivo adjunto (PDF)
    public static String enviarCorreoConAdjunto(String destinatario, String asunto, String cuerpo, byte[] archivoBytes, String nombreArchivo) {
        try {
            Properties props = new Properties(); // Configuración SMTP
            props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor Gmail
            props.put("mail.smtp.port", "587"); // Puerto
            props.put("mail.smtp.auth", "true"); // Autenticación
            props.put("mail.smtp.starttls.enable", "true"); // TLS

            // Crear sesión con autenticación
            jakarta.mail.Session session = jakarta.mail.Session.getInstance(props, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tapiasdaniel44@gmail.com", "jmsy ufib pqzt hyzj");
                }
            });

            // Crear mensaje
            jakarta.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tapiasdaniel44@gmail.com")); // Remitente
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
            message.setSubject(asunto); // Asunto

            // Crear multipart para texto y adjunto
            jakarta.mail.Multipart multi = new jakarta.mail.internet.MimeMultipart();

            // Parte de texto HTML
            jakarta.mail.internet.MimeBodyPart texto = new jakarta.mail.internet.MimeBodyPart();
            texto.setContent(cuerpo, "text/html");

            // Parte adjunta PDF
            jakarta.mail.internet.MimeBodyPart adjunto = new jakarta.mail.internet.MimeBodyPart();
            jakarta.activation.DataSource ds = new jakarta.mail.util.ByteArrayDataSource(archivoBytes, "application/pdf");
            adjunto.setDataHandler(new jakarta.activation.DataHandler(ds));
            adjunto.setFileName(nombreArchivo);

            // Agregar partes al mensaje
            multi.addBodyPart(texto);
            multi.addBodyPart(adjunto);
            message.setContent(multi);

            Transport.send(message); // Enviar correo
            return "OK"; // Retornar OK si se envía correctamente

        } catch (Exception e) {
            e.printStackTrace(); // Mostrar error en consola
            return e.getMessage(); // Retornar mensaje de error
        }
    }

    // Método para enviar correo sin adjunto
    public static String enviarCorreo(String destinatario, String asunto, String cuerpo) {
        try {
            Properties props = new Properties(); // Configuración SMTP
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Crear sesión con autenticación
            jakarta.mail.Session session = jakarta.mail.Session.getInstance(props, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tapiasdaniel44@gmail.com", "jmsy ufib pqzt hyzj");
                }
            });

            // Crear mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tapiasdaniel44@gmail.com")); // Remitente
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Destinatario
            message.setSubject(asunto); // Asunto
            message.setContent(cuerpo, "text/html; charset=UTF-8"); // Contenido HTML

            Transport.send(message); // Enviar correo
            return "OK"; // Retornar OK si se envía correctamente

        } catch (Exception e) {
            e.printStackTrace(); // Mostrar error en consola
            return e.getMessage(); // Retornar mensaje de error
        }
    }
}
