package com.sistema.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sistema.config.Conexion;
import com.sistema.modelo.Venta;

public class VentaDAO {

    public static final String ESTADO_APROBADA = "APROBADA";
    public static final String ESTADO_ANULADA = "ANULADA";

    public int guardar(Connection conn, Venta venta) throws SQLException {

        String sql = """
            INSERT INTO venta (id_vendedor, id_cliente, total, estado)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, venta.getIdVendedor());
            ps.setInt(2, venta.getIdCliente());
            ps.setBigDecimal(3, venta.getTotal());
            ps.setString(4, ESTADO_APROBADA);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

            throw new SQLException("No se generó ID de venta");
        }
    }

    public Venta obtenerPorId(Connection conn, int idVenta) {

        String sql = """
            SELECT id_venta, id_vendedor, estado
            FROM venta
            WHERE id_venta = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVenta);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Venta v = new Venta();
                    v.setIdVenta(rs.getInt("id_venta"));
                    v.setIdVendedor(rs.getInt("id_vendedor"));
                    v.setEstado(rs.getString("estado"));
                    return v;
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener venta", e);
        }
    }

    // CAMBIAR ESTADO (ADMIN)
    public void cambiarEstado(Connection conn, int idVenta, String estado, int aprobadoPor) {

        String sql = """
            UPDATE venta
            SET estado = ?, aprobado_por = ?, fecha_aprobacion = NOW()
            WHERE id_venta = ?
        """;

        ejecutarUpdate(conn, sql, ps -> {
            ps.setString(1, estado);
            ps.setInt(2, aprobadoPor);
            ps.setInt(3, idVenta);
        });
    }

    // LISTADO COMPLETO (GERENTE)
    public List<Object[]> listarPorVendedor(int idVendedor) {

        List<Object[]> lista = new ArrayList<>();

        String sql = """
            SELECT 
                v.id_venta,
                c.nombre,
                v.total,
                v.fecha,
                v.estado AS estado_venta,
                COALESCE(s.estado, 'SIN SOLICITUD') AS estado_solicitud
            FROM venta v
            JOIN cliente c ON c.id_cliente = v.id_cliente

            LEFT JOIN solicitud_cambio s 
                ON s.id_solicitud = (
                    SELECT MAX(sc.id_solicitud)
                    FROM solicitud_cambio sc
                    WHERE sc.referencia_id = v.id_venta
                      AND sc.modulo = 'VENTA'
                      AND sc.accion = 'ANULAR'
                )

            WHERE v.id_vendedor = ?
            ORDER BY v.fecha DESC
        """;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVendedor);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(new Object[]{
                        rs.getInt("id_venta"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("total"),
                        rs.getTimestamp("fecha"),
                        rs.getString("estado_venta"),
                        rs.getString("estado_solicitud")
                    });
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar ventas", e);
        }

        return lista;
    }

    // HELPER reutilizable
    private void ejecutarUpdate(Connection conn, String sql, SQLConsumer<PreparedStatement> consumer) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            consumer.accept(ps);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error en update", e);
        }
    }

    private interface SQLConsumer<T> {
        void accept(T t) throws Exception;
    }
}