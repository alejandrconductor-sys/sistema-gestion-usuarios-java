package com.sistema.modelo;
import java.time.LocalDateTime;

public class Cliente {

    private int idCliente;
    private String nombre;
    private String documento;
    private String telefono;
    private String email;
    private String direccion;
    private String estado;
    private int creadoPor;
    private LocalDateTime fechaCreacion;

        // Getters y Setters

    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
    this.idCliente = idCliente;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
    this.nombre = nombre;
    }
    public String toString() {
        return nombre;
    }
    public String getDocumento() {
        return documento;
    }
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getCreadoPor() {
        return creadoPor;
    }
    public void setCreadoPor(int creadoPor) {
        this.creadoPor = creadoPor;
    }
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    } 
}
