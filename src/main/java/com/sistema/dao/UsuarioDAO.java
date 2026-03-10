package com.sistema.dao;
import com.sistema.config.Conexion;
import com.sistema.modelo.Usuario;
import com.sistema.modelo.EstadoUsuario;
import com.sistema.modelo.Rol;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // LISTAR TODOS
    public List<Usuario> listarTodos() {
        String sql = """
            SELECT u.*, r.id_rol, r.nombre AS nombre_rol
            FROM usuario u
            LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario
            LEFT JOIN rol r ON ur.id_rol = r.id_rol
            """;
        return ejecutarConsulta(sql, null);
    }

    // LISTAR RRHH Y USUARIO
    public List<Usuario> listarRRHHyUsuarios() {
        String sql =
            "SELECT u.id_usuario, u.nombre, u.apellido, u.email, u.password, u.estado, " +
            "r.id_rol, r.nombre AS nombre_rol " +
            "FROM usuario u " +
            "LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario " +
            "LEFT JOIN rol r ON ur.id_rol = r.id_rol " +
            "WHERE r.nombre IN ('RRHH','USUARIO')";
        return ejecutarConsulta(sql, null);
    }

    // LISTAR POR ID
    public List<Usuario> listarPorId(int idUsuario) {
        String sql =
            "SELECT u.id_usuario, u.nombre, u.apellido, u.email, u.password, u.estado, " +
            "r.id_rol, r.nombre AS nombre_rol " +
            "FROM usuario u " +
            "LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario " +
            "LEFT JOIN rol r ON ur.id_rol = r.id_rol " +
            "WHERE u.id_usuario = ?";
        return ejecutarConsulta(sql, idUsuario);
    }

    // MÉTODO PRIVADO REUTILIZABLE
     private List<Usuario> ejecutarConsulta(String sql, Object parametro) {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

        if (sql.contains("?")) {
            if (parametro == null) {
                throw new RuntimeException("Se requiere un parámetro pero se pasó null: " + sql);
            }
            if (parametro instanceof Integer) ps.setInt(1, (Integer)parametro);
            else if (parametro instanceof String) ps.setString(1, (String)parametro);
        }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Usuario u = new Usuario();
                    u.setIdUsuario(rs.getInt("id_usuario"));
                    u.setNombre(rs.getString("nombre"));
                    u.setApellido(rs.getString("apellido"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setEstado(EstadoUsuario.valueOf(rs.getString("estado")));
                    int idRol = rs.getInt("id_rol");
                    String nombreRol = rs.getString("nombre_rol");
                if (idRol > 0) u.setRol(new Rol(idRol, nombreRol));
                    usuarios.add(u);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al ejecutar consulta", e);
        }

        return usuarios;
    }

// GUARDAR
    public Usuario guardar(Usuario usuario) {
        String sqlUsuario =
            "INSERT INTO usuario (nombre, apellido, email, password, estado) VALUES (?, ?, ?, ?, ?)";
        String sqlUsuarioRol =
            "INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                psUsuario.setString(1, usuario.getNombre());
                psUsuario.setString(2, usuario.getApellido());
                psUsuario.setString(3, usuario.getEmail());
                psUsuario.setString(4, usuario.getPassword());
                psUsuario.setString(5, usuario.getEstado().name());
                psUsuario.executeUpdate();

                ResultSet rs = psUsuario.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                } else {
                    throw new RuntimeException("No se generó el ID del usuario");
                }
            }

            try (PreparedStatement psRol = conn.prepareStatement(sqlUsuarioRol)) {
                psRol.setInt(1, usuario.getIdUsuario());
                psRol.setInt(2, usuario.getRol().getIdRol());
                psRol.executeUpdate();
            }

            conn.commit();
            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario", e);
        }
    }

    // ACTUALIZAR
    public void actualizar(Usuario usuario) {
        String sqlUsuario =
            "UPDATE usuario SET nombre=?, apellido=?, email=?, password=?, estado=? WHERE id_usuario=?";
        String sqlRol =
            "UPDATE usuario_rol SET id_rol=? WHERE id_usuario=?";

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

            try (PreparedStatement psRol = conn.prepareStatement(sqlRol)) {
                psRol.setInt(1, usuario.getRol().getIdRol());
                psRol.setInt(2, usuario.getIdUsuario());
                psRol.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    // DESACTIVAR
    public void desactivarUsuario(int idUsuario) {
        String sql = "UPDATE usuario SET estado='DESACTIVADO' WHERE id_usuario=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al desactivar usuario", e);
        }
    }

    // BUSCAR POR EMAIL
    public Usuario buscarPorEmail(String email) {
        String sql =
            "SELECT u.*, r.id_rol, r.nombre AS nombre_rol " +
            "FROM usuario u " +
            "LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario " +
            "LEFT JOIN rol r ON ur.id_rol = r.id_rol " +
            "WHERE u.email = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setEstado(EstadoUsuario.valueOf(rs.getString("estado")));

                int idRol = rs.getInt("id_rol");
                String nombreRol = rs.getString("nombre_rol");

                if (idRol > 0) {
                    u.setRol(new Rol(idRol, nombreRol));
                }

                return u;
            }

        }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por email", e);
        }

        return null;
    }

    public boolean existeEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error verificando email", e);
        }
        return false;
    }

    public Usuario buscarPorId(int idUsuario) {
        String sql =
            "SELECT u.*, r.id_rol, r.nombre AS nombre_rol " +
            "FROM usuario u " +
            "LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario " +
            "LEFT JOIN rol r ON ur.id_rol = r.id_rol " +
            "WHERE u.id_usuario = ?";
        List<Usuario> resultados = ejecutarConsulta(sql, idUsuario);
        return resultados.isEmpty() ? null : resultados.get(0);
    }

    public Rol buscarRolPorNombre(String nombreRol) {
        String sql = "SELECT * FROM rol WHERE nombre = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreRol);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Rol(rs.getInt("id_rol"), rs.getString("nombre"));
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error al buscar rol por nombre", e);
    }
        return null;
    }

}