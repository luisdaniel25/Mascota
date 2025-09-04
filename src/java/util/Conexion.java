package util;
// Paquete donde está esta clase (organiza tu proyecto)

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión a la base de datos MySQL.
 * Centraliza la configuración y permite obtener un objeto Connection
 * para que otras clases (DAO, servicios) puedan interactuar con la BD.
 */
public class Conexion {

    // Constantes con los datos de conexión (se recomienda mover a un archivo de configuración)
    private static final String URL = "jdbc:mysql://localhost:3306/mascotas"; // Nombre de la BD
    private static final String USUARIO = "root";                            // Usuario de MySQL
    private static final String CONTRASENA = "2556229";                       // Contraseña de MySQL
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";          // Driver de conexión

    /**
     * Método estático para establecer conexión con la base de datos.
     * @return objeto Connection si la conexión es exitosa, null si falla.
     */
    public static Connection conectarBD() {
        Connection conexion = null;

        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER);

            // Intentar la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

            // Mensaje de éxito (útil en entornos de prueba, no en producción)
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver JDBC no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
        }

        return conexion;
    }
}