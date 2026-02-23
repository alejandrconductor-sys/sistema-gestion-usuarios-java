package com.sistema.modelo;

public class Rol {

    public Rol() {
    }

    public Rol(int idRol) {
        this.idRol = idRol;
    }

    public Rol(int idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        return nombre;
    }

    private int idRol;
    private String nombre;


}
