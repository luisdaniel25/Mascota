package modelo;

import java.time.LocalDateTime;

public class Trazabilidad {

    private int id;
    private String accion;
    private Integer idMascota;
    private String nombre;
    private String especie;
    private Integer edad;
    private LocalDateTime fechaHora;

    public Trazabilidad() {
    }

    public Trazabilidad(int id, String accion, Integer idMascota, String nombre, String especie, Integer edad, LocalDateTime fechaHora) {
        this.id = id;
        this.accion = accion;
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.especie = especie;
        this.edad = edad;
        this.fechaHora = fechaHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Integer getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(Integer idMascota) {
        this.idMascota = idMascota;
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

}
