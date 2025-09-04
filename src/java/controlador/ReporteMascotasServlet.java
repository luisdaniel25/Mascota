package controlador; // Paquete del servlet

// Librerías de iText para generar PDFs
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// Importar DAO y modelos
import dao.MascotaDAO;
import modelo.Mascota;

// Librerías de Servlet
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

// URL para acceder a este servlet
@WebServlet("/reporteMascotas")
public class ReporteMascotasServlet extends HttpServlet {

    // Instancia DAO para obtener datos de mascotas
    private MascotaDAO dao = new MascotaDAO();

    // Método GET para generar PDF
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/pdf"); // Indicar que la respuesta será PDF
        response.setHeader("Content-Disposition", "attachment; filename=mascotas.pdf"); // Forzar descarga con nombre

        try {
            Document documento = new Document(PageSize.A4); // Crear documento tamaño A4
            PdfWriter.getInstance(documento, response.getOutputStream()); // Asociar PDF al flujo HTTP
            documento.open(); // Abrir documento para escribir

            // Agregar título en negrita y tamaño 16
            documento.add(new Paragraph("Reporte de Mascotas",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));

            documento.add(new Paragraph(" ")); // Espacio en blanco

            PdfPTable table = new PdfPTable(4); // Crear tabla con 4 columnas

            // Encabezados de la tabla
            table.addCell("ID");
            table.addCell("Nombre");
            table.addCell("Especie");
            table.addCell("Edad");

            List<Mascota> lista = dao.listar(); // Obtener lista de mascotas desde DAO

            // Agregar cada mascota a la tabla
            for (Mascota m : lista) {
                table.addCell(String.valueOf(m.getId()));   // Columna ID
                table.addCell(m.getNombre());               // Columna Nombre
                table.addCell(m.getEspecie());              // Columna Especie
                table.addCell(String.valueOf(m.getEdad())); // Columna Edad
            }

            documento.add(table); // Agregar tabla al PDF
            documento.close(); // Cerrar documento

        } catch (Exception e) {
            e.printStackTrace(); // Mostrar errores en consola del servidor
        }
    }
}
