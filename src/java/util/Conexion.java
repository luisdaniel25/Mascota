package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/mascotas";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "2556229";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection conectarBD() {
        Connection conexion = null; // Objeto que contendrá la conexión

        try {
            Class.forName(DRIVER); // Cargar el driver de MySQL
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA); // Intentar conexión
            System.out.println(" Conexión exitosa a la base de datos."); // Mensaje de éxito
        } catch (ClassNotFoundException e) {
            System.err.println(" Driver JDBC no encontrado: " + e.getMessage()); // Error driver
        } catch (SQLException e) {
            System.err.println(" Error al conectar a la base de datos: " + e.getMessage()); // Error conexión
        }

        return conexion; // Retornar objeto Connection (puede ser null si falla)
    }
}
