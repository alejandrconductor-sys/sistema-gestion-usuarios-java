package com.sistema.dao;
import com.sistema.config.Conexion;
import com.sistema.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listarTodos() {

        String sql = "SELECT * FROM producto WHERE estado = 'ACTIVO'";
        List<Producto> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos", e);
        }

        return lista;
    }

    public List<Producto> listarPorVendedor(int idVendedor) {

        String sql = """
            SELECT p.*
            FROM producto p
            JOIN producto_vendedor pv ON p.id_producto = pv.id_producto
            WHERE pv.id_vendedor = ? AND p.estado = 'ACTIVO'
        """;

        List<Producto> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVendedor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearProducto(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos por vendedor", e);
        }

        return lista;
    }

    public void asignarProducto(int idProducto, int idVendedor) {

        String sql = "INSERT INTO producto_vendedor (id_producto, id_vendedor) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idVendedor);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al asignar producto", e);
        }
    }

    public void quitarProducto(int idProducto, int idVendedor) {

        String sql = "DELETE FROM producto_vendedor WHERE id_producto = ? AND id_vendedor = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idVendedor);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al quitar producto", e);
        }
    }

    public boolean existeAsignacion(int idProducto, int idVendedor) {

        String sql = """
            SELECT 1
            FROM producto_vendedor
            WHERE id_producto = ? AND id_vendedor = ?
            LIMIT 1
        """;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idVendedor);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al validar asignación producto", e);
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setIdProducto(rs.getInt("id_producto"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getBigDecimal("precio"));
        return p;
    }
    public int insertar(Producto producto) {

        String sql = """
            INSERT INTO producto (nombre, descripcion, precio, creado_por)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setBigDecimal(3, producto.getPrecio());
            ps.setInt(4, producto.getCreadoPor());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // 🔥 ID generado
            }

            throw new RuntimeException("No se pudo obtener ID producto");

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear producto", e);
        }
    }
}