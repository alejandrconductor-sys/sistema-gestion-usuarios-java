package com.sistema.modelo;
import java.time.LocalDateTime;

public class Inventario {

    private int idInventario;
    private int idProducto;
    private int cantidad;
    private String estado;
    private LocalDateTime fechaActualizacion;

    // Getters y Setters
    public int getIdInventario() {
        return idInventario;
    }
    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }
    public int getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}