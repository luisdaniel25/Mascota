package modelo;

public class Mascota {

    private int id;

    private String nombre;

    private String especie;

    private int edad;

    public Mascota() {
    }

    public Mascota(int id, String nombre, String especie, int edad) {
        this.id = id;               // Inicializa ID
        this.nombre = nombre;       // Inicializa nombre
        this.especie = especie;     // Inicializa especie
        this.edad = edad;           // Inicializa edad
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
