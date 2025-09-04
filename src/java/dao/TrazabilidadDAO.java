package dao;

import java.sql.*;
import java.util.*;
import modelo.Trazabilidad;
import util.Conexion;

public class TrazabilidadDAO {

    public void insertar(Trazabilidad trasa) {
        String sql = "INSERT INTO trazabilidad(accion, id_mascota, nombre, especie, edad, fecha_hora) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.conectarBD(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, trasa.getAccion());
            ps.setObject(2, trasa.getIdMascota(), java.sql.Types.INTEGER);
            ps.setString(3, trasa.getNombre());
            ps.setString(4, trasa.getEspecie());
            ps.setObject(5, trasa.getEdad(), java.sql.Types.INTEGER);
            ps.setTimestamp(6, Timestamp.valueOf(trasa.getFechaHora()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Trazabilidad> listar() {
        List<Trazabilidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM trazabilidad ORDER BY fecha_hora DESC";

        try (Connection con = Conexion.conectarBD(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Trazabilidad(
                        rs.getInt("id"),
                        rs.getString("accion"),
                        rs.getInt("id_mascota"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getInt("edad"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
