package modelo;

import dao.TrazabilidadDAO;
import java.util.List;

public class ServicioTrasabilidad {

    private final TrazabilidadDAO trazDAO = new TrazabilidadDAO();

    // Método general para registrar cualquier acción
    public void registrar(String accion, Mascota mascota) throws Exception {
        Trazabilidad t = new Trazabilidad();
        t.setAccion(accion);
        t.setIdMascota(mascota.getId());
        t.setNombre(mascota.getNombre());
        t.setEspecie(mascota.getEspecie());
        t.setEdad(mascota.getEdad());
        t.setFechaHora(java.time.LocalDateTime.now());

        trazDAO.insertar(t);
    }

    // Nuevo método para obtener todas las trazas
    public List<Trazabilidad> listarTrazas() throws Exception {
        return trazDAO.listar(); // Debes tener un método listar() en TrazabilidadDAO que devuelva List<Trazabilidad>
    }
}
