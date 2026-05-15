package com.sistema.dao;
import com.sistema.config.Conexion;
import com.sistema.modelo.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM cliente WHERE estado = 'ACTIVO'";
        List<Cliente> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes", e);
        }

        return lista;
    }

    public List<Cliente> listarPorVendedor(int idVendedor) {

        String sql = """
            SELECT c.*
            FROM cliente c
            JOIN cliente_vendedor cv ON c.id_cliente = cv.id_cliente
            WHERE cv.id_vendedor = ? AND c.estado = 'ACTIVO'
        """;

        List<Cliente> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVendedor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearCliente(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes por vendedor", e);
        }

        return lista;
    }

    public void asignarCliente(int idCliente, int idVendedor) {

        String sql = "INSERT INTO cliente_vendedor (id_cliente, id_vendedor) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ps.setInt(2, idVendedor);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al asignar cliente", e);
        }
    }

    public void quitarCliente(int idCliente, int idVendedor) {

        String sql = "DELETE FROM cliente_vendedor WHERE id_cliente = ? AND id_vendedor = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ps.setInt(2, idVendedor);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al quitar cliente", e);
        }
    }

    public boolean existeAsignacion(int idCliente, int idVendedor) {

        String sql = """
            SELECT 1
            FROM cliente_vendedor
            WHERE id_cliente = ? AND id_vendedor = ?
            LIMIT 1
        """;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ps.setInt(2, idVendedor);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al validar asignación cliente", e);
        }
    }

    // EVITA DUPLICACIÓN
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNombre(rs.getString("nombre"));
        return c;
    }

    public void insertar(Cliente cliente) {

        String sql = """
            INSERT INTO cliente (nombre, documento, telefono, email, direccion, creado_por)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexion.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getDocumento());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getDireccion());
            ps.setInt(6, cliente.getCreadoPor());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear cliente", e);
        }
    }
}