package com.sistema.dao;
import com.sistema.config.Conexion;
import com.sistema.modelo.Usuario;
import com.sistema.modelo.EstadoUsuario;
import com.sistema.modelo.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // LISTADOS
    public List<Usuario> listarTodos() {
        String sql = baseQuery() + "";
        return ejecutarConsulta(sql, null);
    }

    public List<Usuario> listarPorRol(String nombreRol) {
        String sql = baseQuery() + " WHERE r.nombre = ?";
        return ejecutarConsulta(sql, nombreRol);
    }

    public List<Usuario> listarRRHHyUsuarios() {
        String sql = baseQuery() + " WHERE r.nombre IN ('RRHH','VENDEDOR','GERENTE_VENTAS')";
        return ejecutarConsulta(sql, null);
    }

    public List<Usuario> listarVendedores() {
        String sql = baseQuery() + " WHERE r.id_rol = ?";
        
        Rol rol = buscarRolPorNombre("VENDEDOR");
        
        return ejecutarConsulta(sql, rol.getIdRol());
    }

    public Usuario buscarPorId(int idUsuario) {
        String sql = baseQuery() + " WHERE u.id_usuario = ?";
        List<Usuario> lista = ejecutarConsulta(sql, idUsuario);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Usuario buscarPorEmail(String email) {
        String sql = baseQuery() + " WHERE u.email = ?";
        List<Usuario> lista = ejecutarConsulta(sql, email);
        return lista.isEmpty() ? null : lista.get(0);
    }

    private String baseQuery() {
        return """
            SELECT u.*, r.id_rol, r.nombre AS nombre_rol
            FROM usuario u
            LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
            LEFT JOIN rol r ON ur.id_rol = r.id_rol
        """;
    }

    // EJECUTOR GENERICO
    private List<Usuario> ejecutarConsulta(String sql, Object parametro) {

        List<Usuario> lista = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (parametro != null) {
                if (parametro instanceof Integer) {
                    ps.setInt(1, (Integer) parametro);
                } else if (parametro instanceof String) {
                    ps.setString(1, (String) parametro);
                }
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }

        }catch (SQLException e) {

            e.printStackTrace();

            throw new RuntimeException(
                "Error en consulta UsuarioDAO: " + e.getMessage(), e
            );
        }

        return lista;
    }

    // MAPPER
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {

        Usuario u = new Usuario();

        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setEstado(EstadoUsuario.valueOf(rs.getString("estado")));

        int idRol = rs.getInt("id_rol");
        String nombreRol = rs.getString("nombre_rol");

        if (idRol > 0 && nombreRol != null) {
            u.setRol(new Rol(idRol, nombreRol));
        }

        return u;
    }

    public Usuario guardar(Usuario usuario) {

        String sqlUsuario = """
            INSERT INTO usuario (nombre, apellido, email, password, estado)
            VALUES (?, ?, ?, ?, ?)
        """;

        String sqlRol = """
            INSERT INTO usuario_rol (id_usuario, id_rol)
            VALUES (?, ?)
        """;

        try (Connection conn = Conexion.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getApellido());
                ps.setString(3, usuario.getEmail());
                ps.setString(4, usuario.getPassword());
                ps.setString(5, usuario.getEstado().name());

                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlRol)) {
                ps.setInt(1, usuario.getIdUsuario());
                ps.setInt(2, usuario.getRol().getIdRol());
                ps.executeUpdate();
            }

            conn.commit();
            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Error guardando usuario", e);
        }
    }

    public void actualizar(Usuario usuario) {

        String sqlUsuario = """
            UPDATE usuario
            SET nombre=?, apellido=?, email=?, password=?, estado=?
            WHERE id_usuario=?
        """;

        String sqlRol = """
            UPDATE usuario_rol
            SET id_rol=?
            WHERE id_usuario=?
        """;

        try (Connection conn = Conexion.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sqlUsuario)) {

                ps.setString(1, usuario.getNombre());
                ps.setString(2, usuario.getApellido());
                ps.setString(3, usuario.getEmail());
                ps.setString(4, usuario.getPassword());
                ps.setString(5, usuario.getEstado().name());
                ps.setInt(6, usuario.getIdUsuario());

                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlRol)) {

                ps.setInt(1, usuario.getRol().getIdRol());
                ps.setInt(2, usuario.getIdUsuario());

                ps.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando usuario", e);
        }
    }

    public void desactivarUsuario(int idUsuario) {
        String sql = "UPDATE usuario SET estado='DESACTIVADO' WHERE id_usuario=?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error desactivando usuario", e);
        }
    }

    public boolean existeEmail(String email) {

        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error verificando email", e);
        }

        return false;
    }

    public Rol buscarRolPorNombre(String nombreRol) {

        String sql = "SELECT * FROM rol WHERE nombre = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombreRol);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Rol(rs.getInt("id_rol"), rs.getString("nombre"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error buscando rol", e);
        }

        return null;
    }
}