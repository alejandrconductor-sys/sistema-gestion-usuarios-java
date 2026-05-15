package com.sistema.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuditoriaDAO {

    public void registrar(
            Connection conn,
            int idUsuario,
            String accion,
            String modulo,
            Integer referenciaId,
            String descripcion
    ) {

        String sql = "INSERT INTO auditoria " +
                     "(id_usuario, accion, modulo, referencia_id, descripcion) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setString(2, accion);
            ps.setString(3, modulo);

            if (referenciaId != null) {
                ps.setInt(4, referenciaId);
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.setString(5, descripcion);

            if (ps.executeUpdate() == 0) {
                throw new RuntimeException("No se registró auditoría");
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar auditoría", e);
        }
    }
}