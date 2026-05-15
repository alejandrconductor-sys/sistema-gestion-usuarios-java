package com.sistema.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.sistema.config.Conexion;
import com.sistema.modelo.SolicitudCambio;

public class SolicitudCambioDAO {

    // =========================
    // CREAR SOLICITUD
    // =========================
    public void crearSolicitud(
            Connection conn,
            int idUsuario,
            String modulo,
            String tipoEntidad,
            String accion,
            int referenciaId,
            String datosAnteriores,
            String datosNuevos
    ) {

        String sql = "INSERT INTO solicitud_cambio " +
                "(id_usuario, modulo, tipo_entidad, accion, referencia_id, datos_anteriores, datos_nuevos) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setString(2, modulo);
            ps.setString(3, tipoEntidad);
            ps.setString(4, accion);
            ps.setInt(5, referenciaId);
            ps.setString(6, datosAnteriores);
            ps.setString(7, datosNuevos);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear solicitud de cambio", e);
        }
    }

    // =========================
    // LISTAR TODAS
    // =========================
    public List<Object[]> listarTodasSolicitudes() {

        List<Object[]> lista = new ArrayList<>();

        String sql = """
            SELECT 
                s.id_solicitud,
                s.modulo,
                s.referencia_id,
                s.accion,
                COALESCE(s.datos_nuevos, 'SIN DETALLE'),
                s.estado,
                s.fecha
            FROM solicitud_cambio s
            ORDER BY s.fecha DESC
        """;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Object[]{
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getTimestamp(7)
                });
            }

        } catch (Exception e) {
            throw new RuntimeException("Error listando solicitudes", e);
        }

        return lista;
    }


    // =========================
    // EXISTE PENDIENTE
    // =========================
    public boolean existeSolicitudPendiente(Connection conn, String modulo, int referenciaId) {

        String sql = "SELECT COUNT(*) FROM solicitud_cambio " +
                "WHERE modulo = ? AND referencia_id = ? AND estado = 'PENDIENTE'";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, modulo);
            ps.setInt(2, referenciaId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // =========================
    // APROBAR / RECHAZAR
    // =========================
    public void aprobar(Connection conn, int idSolicitud) {

        String sql = "UPDATE solicitud_cambio SET estado = 'APROBADO' WHERE id_solicitud = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSolicitud);
            if (ps.executeUpdate() == 0) {
               throw new RuntimeException("Solicitud no encontrada");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rechazar(Connection conn, int idSolicitud) {

        String sql = "UPDATE solicitud_cambio SET estado = 'RECHAZADO' WHERE id_solicitud = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSolicitud);
            if (ps.executeUpdate() == 0) {
               throw new RuntimeException("Solicitud no encontrada");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // =========================
    // OBTENER POR ID
    // =========================
    public SolicitudCambio obtenerPorId(int idSolicitud) {

        String sql = "SELECT * FROM solicitud_cambio WHERE id_solicitud = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    SolicitudCambio s = new SolicitudCambio();

                    s.setIdSolicitud(rs.getInt("id_solicitud"));
                    s.setIdUsuario(rs.getInt("id_usuario"));
                    s.setModulo(rs.getString("modulo"));
                    s.setTipoEntidad(rs.getString("tipo_entidad"));
                    s.setAccion(rs.getString("accion"));
                    s.setReferenciaId(rs.getInt("referencia_id"));
                    s.setDatosAnteriores(rs.getString("datos_anteriores"));
                    s.setDatosNuevos(rs.getString("datos_nuevos"));
                    s.setEstado(rs.getString("estado"));
                    s.setFecha(rs.getTimestamp("fecha"));

                    return s;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    // =========================
    // LISTAR POR ESTADO
    // =========================
    public List<SolicitudCambio> listarPorEstado(String estado) {

        List<SolicitudCambio> lista = new ArrayList<>();

        String sql = "SELECT * FROM solicitud_cambio WHERE estado = ? ORDER BY fecha ASC";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                SolicitudCambio s = new SolicitudCambio();

                s.setIdSolicitud(rs.getInt("id_solicitud"));
                s.setIdUsuario(rs.getInt("id_usuario"));
                s.setModulo(rs.getString("modulo"));
                s.setTipoEntidad(rs.getString("tipo_entidad"));
                s.setAccion(rs.getString("accion"));
                s.setReferenciaId(rs.getInt("referencia_id"));
                s.setDatosAnteriores(rs.getString("datos_anteriores"));
                s.setDatosNuevos(rs.getString("datos_nuevos"));
                s.setEstado(rs.getString("estado"));
                s.setFecha(rs.getTimestamp("fecha"));

                lista.add(s);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return lista;
    }
}