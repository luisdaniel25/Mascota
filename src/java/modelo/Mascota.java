package modelo;

/**
 * Clase que representa una Mascota en el sistema.
 * Contiene información básica como identificador, nombre, especie y edad.
 * 
 * Se utiliza en las operaciones CRUD del sistema y para generar reportes.
 */
public class Mascota {

    /** Identificador único de la mascota en la base de datos */
    private int id;

    /** Nombre de la mascota */
    private String nombre;

    /** Especie de la mascota (ej: perro, gato, etc.) */
    private String especie;

    /** Edad de la mascota en años */
    private int edad;

    /**
     * Constructor vacío.
     * Permite crear objetos Mascota sin inicializar sus atributos.
     */
    public Mascota() {
    }

    /**
     * Constructor con todos los atributos.
     * 
     * @param id      identificador único de la mascota
     * @param nombre  nombre de la mascota
     * @param especie especie a la que pertenece
     * @param edad    edad en años
     */
    public Mascota(int id, String nombre, String especie, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
    }

    // ================== Getters y Setters ==================

    /** @return el identificador único de la mascota */
    public int getId() {
        return id;
    }

    /** @param id asigna un nuevo identificador a la mascota */
    public void setId(int id) {
        this.id = id;
    }

    /** @return el nombre de la mascota */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre asigna un nuevo nombre a la mascota */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return la especie de la mascota */
    public String getEspecie() {
        return especie;
    }

    /** @param especie asigna una nueva especie a la mascota */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    /** @return la edad de la mascota en años */
    public int getEdad() {
        return edad;
    }

    /** @param edad asigna una nueva edad a la mascota */
    public void setEdad(int edad) {
        this.edad = edad;
    }
}
