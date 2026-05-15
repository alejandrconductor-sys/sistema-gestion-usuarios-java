package com.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClienteVendedorDAO {

    public boolean existeRelacion(Connection conn, int idCliente, int idVendedor) {

        String sql = """
            SELECT 1 
            FROM cliente_vendedor 
            WHERE id_cliente = ? AND id_vendedor = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ps.setInt(2, idVendedor);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            throw new RuntimeException("Error validando relación cliente-vendedor", e);
        }
    }

    public void asignarCliente(Connection conn, int idCliente, int idVendedor) {

        if (existeRelacion(conn, idCliente, idVendedor)) return;

        String sql = """
            INSERT INTO cliente_vendedor (id_cliente, id_vendedor)
            VALUES (?, ?)
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ps.setInt(2, idVendedor);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Error asignando cliente", e);
        }
    }
}