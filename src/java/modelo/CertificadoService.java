package modelo; // Paquete modelo

// Librerías iText para PDF
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream; // Para guardar PDF en memoria
import java.time.LocalDateTime; // Fecha actual
import java.time.format.DateTimeFormatter; // Formato de fecha

public class CertificadoService {

    // Método estático que genera un certificado PDF de la mascota y devuelve los bytes
    public static byte[] generarCertificado(Mascota mascota) {
        try {
            Document document = new Document(); // Crear documento PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); // Guardar PDF en memoria
            PdfWriter.getInstance(document, baos); // Asociar documento con flujo de salida
            document.open(); // Abrir documento para escritura

            // Título del certificado
            Font titulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph pTitulo = new Paragraph("CERTIFICADO DE REGISTRO DE MASCOTA", titulo);
            pTitulo.setAlignment(Element.ALIGN_CENTER); // Centrar título
            pTitulo.setSpacingAfter(20); // Espacio después del título
            document.add(pTitulo); // Agregar título al PDF

            // Contenido con datos de la mascota
            Font contenidoFont = new Font(Font.FontFamily.HELVETICA, 12);
            Paragraph contenido = new Paragraph(
                    "Se certifica que la mascota ha sido registrada correctamente en nuestra plataforma.\n\n"
                    + "Datos de la Mascota:\n"
                    + "ID: " + mascota.getId() + "\n"
                    + "Nombre: " + mascota.getNombre() + "\n"
                    + "Especie: " + mascota.getEspecie() + "\n"
                    + "Edad: " + mascota.getEdad() + " años\n\n"
                    + "Fecha de emisión: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    contenidoFont
            );
            document.add(contenido); // Agregar contenido al PDF

            // Pie de página
            Paragraph footer = new Paragraph("© 2025 Gestión Empresarial de Mascotas",
                    new Font(Font.FontFamily.HELVETICA, 10));
            footer.setAlignment(Element.ALIGN_CENTER); // Centrar pie de página
            footer.setSpacingBefore(30); // Espacio antes del pie
            document.add(footer); // Agregar pie de página al PDF

            document.close(); // Cerrar documento
            return baos.toByteArray(); // Retornar PDF como array de bytes
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar error en consola
            return null; // Retornar null si falla
        }
    }
}
