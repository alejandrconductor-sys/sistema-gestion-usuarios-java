package com.sistema.servicio;

import java.sql.Connection;

import com.sistema.dao.AuditoriaDAO;

public class AuditoriaServicio {

    private final AuditoriaDAO auditoriaDAO = new AuditoriaDAO();

    public void registrar(
            Connection conn,
            int idUsuario,
            String accion,
            String modulo,
            Integer referenciaId,
            String descripcion
    ) {
        try {
            auditoriaDAO.registrar(conn, idUsuario, accion, modulo, referenciaId, descripcion);
        } catch (Exception e) {
            // ⚠️ NO romper flujo principal
            System.err.println("Error en auditoría: " + e.getMessage());
        }
    }
}