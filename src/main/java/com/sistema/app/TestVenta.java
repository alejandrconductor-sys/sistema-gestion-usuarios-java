package com.sistema.app;
import java.math.BigDecimal;
import java.util.List;
import com.sistema.modelo.DetalleVenta;
import com.sistema.modelo.Venta;
import com.sistema.servicio.VentaServicio;

public class TestVenta {

    public static void main(String[] args) {

        Venta venta = new Venta();
        venta.setIdVendedor(3);
        venta.setIdCliente(1);
        venta.setTotal(new BigDecimal("100"));

        DetalleVenta d = new DetalleVenta();
        d.setIdProducto(1);
        d.setCantidad(2);
        d.setPrecio(new BigDecimal("50"));
        d.setSubtotal(new BigDecimal("100"));

        venta.setDetalles(List.of(d));

        VentaServicio servicio = new VentaServicio();
        servicio.registrarVenta(venta);

        System.out.println(" Venta registrada correctamente");
        venta.setDetalles(List.of(d));
    }
}