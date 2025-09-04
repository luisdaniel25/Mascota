package modelo;

import dao.TrazabilidadDAO;
import java.util.List;

/**
 * Servicio que maneja la trazabilidad de las acciones realizadas sobre las
 * mascotas. Permite registrar y listar todas las acciones (insertar,
 * actualizar, eliminar, etc.).
 */
public class ServicioTrasabilidad {

    /**
     * DAO para interactuar con la tabla de trazabilidad en la base de datos
     */
    private TrazabilidadDAO trazDAO = new TrazabilidadDAO();

    /**
     * Registra una acción realizada sobre una mascota.
     *
     * @param accion acción realizada (INSERTAR, ACTUALIZAR, ELIMINAR, etc.)
     * @param mascota objeto Mascota sobre el que se realiza la acción
     * @throws Exception si ocurre un error al insertar en la base de datos
     */
    public void registrar(String accion, Mascota mascota) throws Exception {
        Trazabilidad trasabilidad = new Trazabilidad();           // Crear objeto trazabilidad
        trasabilidad.setAccion(accion);                            // Guardar tipo de acción
        trasabilidad.setIdMascota(mascota.getId());               // Guardar ID de la mascota
        trasabilidad.setNombre(mascota.getNombre());              // Guardar nombre
        trasabilidad.setEspecie(mascota.getEspecie());            // Guardar especie
        trasabilidad.setEdad(mascota.getEdad());                  // Guardar edad
        trasabilidad.setFechaHora(java.time.LocalDateTime.now()); // Guardar fecha y hora actual

        trazDAO.insertar(trasabilidad);                           // Insertar registro en la base de datos
    }

    /**
     * Obtiene todas las trazas registradas.
     *
     * @return lista de objetos Trazabilidad
     * @throws Exception si ocurre un error al consultar la base de datos
     */
    public List<Trazabilidad> listarTrazas() throws Exception {
        return trazDAO.listar(); // Llamada al DAO para obtener todas las trazas
    }
}
