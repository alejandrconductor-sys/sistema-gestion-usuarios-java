package com.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sistema.config.Conexion;
import com.sistema.modelo.Producto;

public class ProductoVendedorDAO {

    public boolean existeRelacion(Connection conn, int idProducto, int idVendedor) {

        String sql = """
            SELECT 1
            FROM producto_vendedor
            WHERE id_producto = ? AND id_vendedor = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idVendedor);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            throw new RuntimeException("Error validando relación producto-vendedor", e);
        }
    }

    public void asignar(Connection conn, int idProducto, int idVendedor) {

        if (existeRelacion(conn, idProducto, idVendedor)) return;

        String sql = """
            INSERT INTO producto_vendedor (id_producto, id_vendedor)
            VALUES (?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idVendedor);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error asignando producto", e);
        }
    }

    public void eliminar(Connection conn, int idProducto, int idVendedor) {

        String sql = """
            DELETE FROM producto_vendedor
            WHERE id_producto = ? AND id_vendedor = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idVendedor);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Relación no encontrada");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error eliminando relación", e);
        }
    }

    // 🔥 MÉTODO CLAVE
    public List<Producto> listarPorVendedor(int idVendedor) {

        List<Producto> lista = new ArrayList<>();

        String sql = """
            SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.estado,
                   i.cantidad AS stock
            FROM producto p
            JOIN producto_vendedor pv ON p.id_producto = pv.id_producto
            JOIN inventario i ON p.id_producto = i.id_producto
            WHERE pv.id_vendedor = ?
        """;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVendedor);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Producto p = new Producto();
                    p.setIdProducto(rs.getInt("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setEstado(rs.getString("estado"));
                    p.setStock(rs.getInt("stock"));

                    lista.add(p);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error listando productos por vendedor", e);
        }

        return lista;
    }
}