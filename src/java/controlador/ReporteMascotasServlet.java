package controlador;

// Librerías para generar PDF con iText
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.MascotaDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

import modelo.Mascota;


@WebServlet("/reporteMascotas")
public class ReporteMascotasServlet extends HttpServlet {

    // DAO para acceder a los datos de mascotas
    private MascotaDAO dao = new MascotaDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Indicamos al navegador que la respuesta será un PDF
        response.setContentType("application/pdf");

        // Forzamos la descarga con el nombre "mascotas.pdf"
        response.setHeader("Content-Disposition", "attachment; filename=mascotas.pdf");

        try {
            // Crear el documento en tamaño A4
            Document documento = new Document(PageSize.A4);

            // Asociar el documento con el flujo de salida HTTP (para que se envíe al navegador)
            PdfWriter.getInstance(documento, response.getOutputStream());

            // Abrir el documento para comenzar a escribir en él
            documento.open();

            // Agregar título al reporte con fuente negrita tamaño 16
            documento.add(new Paragraph("Reporte de Mascotas",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));

            // Agregar un espacio en blanco
            documento.add(new Paragraph(" "));

            // Crear una tabla con 4 columnas
            PdfPTable table = new PdfPTable(4);

            // Encabezados de la tabla
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Especie");
            table.addCell("Edad");

            // Obtener lista de mascotas desde el DAO
            List<Mascota> lista = dao.listar();

            // Recorrer cada mascota y agregar sus datos a la tabla
            for (Mascota m : lista) {
                table.addCell(String.valueOf(m.getId()));   // Columna ID
                table.addCell(m.getNombre());               // Columna Nombre
                table.addCell(m.getEspecie());              // Columna Especie
                table.addCell(String.valueOf(m.getEdad())); // Columna Edad
            }

            // Agregar la tabla al PDF
            documento.add(table);

            // Cerrar el documento (obligatorio para finalizarlo correctamente)
            documento.close();

        } catch (Exception e) {
            e.printStackTrace(); // Mostrar errores en la consola del servidor
        }
    }
}
