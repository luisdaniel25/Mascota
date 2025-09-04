package dao; // Paquete DAO

import java.sql.*; // Librerías JDBC
import java.util.*; // List, ArrayList
import modelo.Mascota; // Modelo Mascota
import util.Conexion; // Clase de conexión a la BD

public class MascotaDAO {

    // Método para listar todas las mascotas
    public List<Mascota> listar() {
        List<Mascota> lista = new ArrayList<>(); // Lista de retorno
        String sql = "SELECT * FROM mascotas"; // Consulta SQL

        try (Connection con = Conexion.conectarBD(); // Conectar a BD
                 Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) { // Ejecutar consulta
            while (rs.next()) { // Recorrer resultados
                lista.add(new Mascota(
                        rs.getInt("id"), // ID
                        rs.getString("nombre"), // Nombre
                        rs.getString("especie"), // Especie
                        rs.getInt("edad") // Edad
                ));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Mostrar errores
        }
        return lista; // Retornar lista
    }

    // Método para insertar una mascota y recuperar su ID generado
    public void insertar(Mascota m) {
        String sql = "INSERT INTO mascotas(nombre, especie, edad) VALUES (?, ?, ?)";

        try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNombre()); // Parametro nombre
            ps.setString(2, m.getEspecie()); // Parametro especie
            ps.setInt(3, m.getEdad()); // Parametro edad
            ps.executeUpdate(); // Ejecutar inserción

            // Obtener ID generado automáticamente
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getInt(1)); // Asignar ID al objeto
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para actualizar una mascota existente
    public void actualizar(Mascota m) {
        String sql = "UPDATE mascotas SET nombre=?, especie=?, edad=? WHERE id=?";

        try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecie());
            ps.setInt(3, m.getEdad());
            ps.setInt(4, m.getId());
            ps.executeUpdate(); // Ejecutar actualización
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar una mascota por ID
    public void eliminar(int id) {
        String sql = "DELETE FROM mascotas WHERE id=?";

        try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); // ID a eliminar
            ps.executeUpdate(); // Ejecutar eliminación
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener una mascota por su ID
    public Mascota obtenerPorId(int id) {
        String sql = "SELECT * FROM mascotas WHERE id=?";
        Mascota m = null;

        try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); // Parámetro ID
            try (ResultSet rs = ps.executeQuery()) { // Ejecutar consulta
                if (rs.next()) {
                    m = new Mascota(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("especie"),
                            rs.getInt("edad")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return m; // Retornar mascota o null si no existe
    }
}
