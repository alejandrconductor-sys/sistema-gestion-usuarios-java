package com.sistema.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sistema.modelo.DetalleVenta;

public class DetalleVentaDAO {

    public void guardar(Connection conn, DetalleVenta d) {

        String sql = """
            INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio, subtotal)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, d.getIdVenta());
            ps.setInt(2, d.getIdProducto());
            ps.setInt(3, d.getCantidad());
            ps.setBigDecimal(4, d.getPrecio());
            ps.setBigDecimal(5, d.getSubtotal());

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error guardando detalle", e);
        }
    }

    public List<DetalleVenta> listarPorVenta(Connection conn, int idVenta) {

        List<DetalleVenta> lista = new ArrayList<>();

        String sql = "SELECT * FROM detalle_venta WHERE id_venta = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVenta);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    DetalleVenta d = new DetalleVenta();
                    d.setIdVenta(idVenta);
                    d.setIdProducto(rs.getInt("id_producto"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setPrecio(rs.getBigDecimal("precio"));
                    d.setSubtotal(rs.getBigDecimal("subtotal"));
                    lista.add(d);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error listando detalles", e);
        }

        return lista;
    }
}