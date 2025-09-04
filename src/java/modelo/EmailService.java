package modelo;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    // Método existente para enviar con adjunto
    public static String enviarCorreoConAdjunto(String destinatario, String asunto, String cuerpo, byte[] archivoBytes, String nombreArchivo) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            jakarta.mail.Session session = jakarta.mail.Session.getInstance(props, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tapiasdaniel44@gmail.com", "jmsy ufib pqzt hyzj");
                }
            });

            jakarta.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tapiasdaniel44@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);

            // Cuerpo y adjunto
            jakarta.mail.Multipart multi = new jakarta.mail.internet.MimeMultipart();

            jakarta.mail.internet.MimeBodyPart texto = new jakarta.mail.internet.MimeBodyPart();
            texto.setContent(cuerpo, "text/html");

            jakarta.mail.internet.MimeBodyPart adjunto = new jakarta.mail.internet.MimeBodyPart();
            jakarta.activation.DataSource ds = new jakarta.mail.util.ByteArrayDataSource(archivoBytes, "application/pdf");
            adjunto.setDataHandler(new jakarta.activation.DataHandler(ds));
            adjunto.setFileName(nombreArchivo);

            multi.addBodyPart(texto);
            multi.addBodyPart(adjunto);

            message.setContent(multi);

            Transport.send(message);
            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    // NUEVO MÉTODO: enviar correo sin adjunto
    public static String enviarCorreo(String destinatario, String asunto, String cuerpo) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            jakarta.mail.Session session = jakarta.mail.Session.getInstance(props, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("tapiasdaniel44@gmail.com", "jmsy ufib pqzt hyzj");
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("tapiasdaniel44@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setContent(cuerpo, "text/html; charset=UTF-8");

            Transport.send(message);
            return "OK";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
