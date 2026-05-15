package com.sistema.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sistema.config.Conexion;

public class InventarioDAO {

    public int obtenerStock(Connection conn, int idProducto) {

        String sql = """
            SELECT cantidad 
            FROM inventario 
            WHERE id_producto = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("cantidad");
                return 0; // mejor que romper el flujo
            }

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo stock", e);
        }
    }
    public void crearSiNoExiste(Connection conn, int idProducto) {

        String checkSql = """
            SELECT 1 FROM inventario 
            WHERE id_producto = ?
        """;

        try (PreparedStatement check = conn.prepareStatement(checkSql)) {

            check.setInt(1, idProducto);

            ResultSet rs = check.executeQuery();

            if (!rs.next()) {

                String insertSql = """
                    INSERT INTO inventario (id_producto, cantidad)
                    VALUES (?, 0)
                """;

                try (PreparedStatement insert = conn.prepareStatement(insertSql)) {

                    insert.setInt(1, idProducto);
                    insert.executeUpdate();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error creando inventario", e);
        }
    }

    public void restarStock(Connection conn, int idProducto, int idVendedor, int cantidad) {

        validarCantidad(cantidad);

        String sql = """
            UPDATE inventario
            SET cantidad = cantidad - ?
            WHERE id_producto = ?
            AND cantidad >= ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Stock insuficiente");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error restando stock", e);
        }
    }
        //aqui resta
    public void actualizarStock(Connection conn, int idProducto, int cantidad) {

        validarCantidad(cantidad);

        String sql = """
            UPDATE inventario
            SET cantidad = cantidad - ?
            WHERE id_producto = ?
        """;

        ejecutarUpdate(conn, sql, cantidad, idProducto);

    }

    public void sumarStock(Connection conn, int idProducto, int cantidad) {

        validarCantidad(cantidad);

        String sql = """
            UPDATE inventario
            SET cantidad = cantidad + ?
            WHERE id_producto = ?
        """;

        ejecutarUpdate(conn, sql, cantidad, idProducto);
    }

    private void ejecutarUpdate(Connection conn, String sql, int cantidad, int idProducto) {

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("Inventario no encontrado");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error actualizando stock", e);
        }
    }

    public List<Object[]> listarConProducto() {

        List<Object[]> lista = new ArrayList<>();

        String sql = """
            SELECT p.id_producto, p.nombre, i.cantidad
            FROM inventario i
            JOIN producto p ON p.id_producto = i.id_producto
        """;

        try (Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad")
                });
            }

        } catch (Exception e) {
            throw new RuntimeException("Error listando inventario", e);
        }

        return lista;
    }

    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }
    }
    public void crearInventarioInicial(int idProducto) {

        String sql = """
            INSERT INTO inventario (id_producto, id_vendedor, cantidad)
            VALUES (?, 0, 0)
        """;

        try (Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error creando inventario inicial", e);
        }
    }
}