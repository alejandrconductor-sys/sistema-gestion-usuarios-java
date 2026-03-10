package com.sistema.modelo;
import java.time.LocalDateTime;

public class Usuario {

    public Usuario() {
    }

    public Usuario(Integer id, String nombre, String apellido, String email, EstadoUsuario estado, Rol rol, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido=apellido;
        this.email = email;
        this.estado=estado;
        this.rol=rol;
        this.password=password;
    }

    // Getters y Setters
    public int getIdUsuario() {
        return id;
    }

    public void setIdUsuario(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public Integer getAprobadoPor() {
        return aprobadoPor;
    }

    public void setAprobadoPor(Integer aprobadoPor) {
        this.aprobadoPor = aprobadoPor;
    }

    public LocalDateTime getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDateTime fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }       

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String toString(){
        return nombre+" - "+email+"(" +rol+ ")";
    }

    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Rol rol;
    private EstadoUsuario estado;
    private Integer aprobadoPor;
    private LocalDateTime fechaAprobacion;
    
}
