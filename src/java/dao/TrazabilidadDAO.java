package dao; // Paquete DAO

import java.sql.*; // Librerías JDBC
import java.util.*; // List, ArrayList
import modelo.Trazabilidad; // Modelo Trazabilidad
import util.Conexion; // Clase de conexión a la BD

public class TrazabilidadDAO {

    // Método para insertar un registro de trazabilidad
    public void insertar(Trazabilidad trasa) {
        String sql = "INSERT INTO trazabilidad(accion, id_mascota, nombre, especie, edad, fecha_hora) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.conectarBD(); // Conectar a la BD
                 PreparedStatement ps = con.prepareStatement(sql)) { // Preparar sentencia SQL
            ps.setString(1, trasa.getAccion()); // Acción realizada (INSERTAR, ACTUALIZAR, ELIMINAR)
            ps.setObject(2, trasa.getIdMascota(), java.sql.Types.INTEGER); // ID de la mascota
            ps.setString(3, trasa.getNombre()); // Nombre de la mascota
            ps.setString(4, trasa.getEspecie()); // Especie
            ps.setObject(5, trasa.getEdad(), java.sql.Types.INTEGER); // Edad
            ps.setTimestamp(6, Timestamp.valueOf(trasa.getFechaHora())); // Fecha y hora de la acción
            ps.executeUpdate(); // Ejecutar inserción
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar errores en consola
        }
    }

    // Método para listar todos los registros de trazabilidad
    public List<Trazabilidad> listar() {
        List<Trazabilidad> lista = new ArrayList<>(); // Lista de retorno
        String sql = "SELECT * FROM trazabilidad ORDER BY fecha_hora DESC"; // Consulta ordenada por fecha descendente

        try (Connection con = Conexion.conectarBD(); // Conectar a la BD
                 Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) { // Ejecutar consulta
            while (rs.next()) { // Recorrer resultados
                lista.add(new Trazabilidad(
                        rs.getInt("id"), // ID del registro
                        rs.getString("accion"), // Acción realizada
                        rs.getInt("id_mascota"), // ID de la mascota
                        rs.getString("nombre"), // Nombre
                        rs.getString("especie"), // Especie
                        rs.getInt("edad"), // Edad
                        rs.getTimestamp("fecha_hora").toLocalDateTime() // Fecha y hora convertida a LocalDateTime
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar errores
        }

        return lista; // Retornar lista de trazabilidad
    }
}
