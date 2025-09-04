package dao;

import java.sql.*;
import java.util.*;
import modelo.Mascota;
import util.Conexion;

public class MascotaDAO {

    // Método listar 
    public List<Mascota> listar() {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas";

        try (Connection con = Conexion.conectarBD(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Mascota(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getInt("edad")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método insertar y recuperar ID generado
    public void insertar(Mascota m) {
        String sql = "INSERT INTO mascotas(nombre, especie, edad) VALUES (?, ?, ?)";

        try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecie());
            ps.setInt(3, m.getEdad());
            ps.executeUpdate();

            // Obtener ID generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getInt(1));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método actualizar
    public void actualizar(Mascota m) {
        String sql = "UPDATE mascotas SET nombre=?, especie=?, edad=? WHERE id=?";

        try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getEspecie());
            ps.setInt(3, m.getEdad());
            ps.setInt(4, m.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método eliminar
    public void eliminar(int id) {
        String sql = "DELETE FROM mascotas WHERE id=?";

        try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Nuevo método para obtener mascota por ID
    public Mascota obtenerPorId(int id) {
        String sql = "SELECT * FROM mascotas WHERE id=?";
        Mascota m = null;

        try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
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

        return m;
    }
}
