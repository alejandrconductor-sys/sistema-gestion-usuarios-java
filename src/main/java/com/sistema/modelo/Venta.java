package com.sistema.modelo;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Venta {

    private int idVenta;
    private int idVendedor;
    private int idCliente;
    private LocalDateTime fecha;
    private BigDecimal total;
    private List<DetalleVenta> detalles;
    private String estado;

    public int getIdVenta() {
        return idVenta;
    }    
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
    public int getIdVendedor() {
        return idVendedor;
    }
    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }
    public int getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public List<DetalleVenta> getDetalles() {
        return detalles;
    }
    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
    public String getEstado() {
       return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
