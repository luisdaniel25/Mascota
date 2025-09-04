package modelo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CertificadoService {

    public static byte[] generarCertificado(Mascota mascota) {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Título
            Font titulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph pTitulo = new Paragraph("CERTIFICADO DE REGISTRO DE MASCOTA", titulo);
            pTitulo.setAlignment(Element.ALIGN_CENTER);
            pTitulo.setSpacingAfter(20);
            document.add(pTitulo);

            // Contenido
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
            document.add(contenido);

            // Pie de página
            Paragraph footer = new Paragraph("© 2025 Gestión Empresarial de Mascotas", new Font(Font.FontFamily.HELVETICA, 10));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
