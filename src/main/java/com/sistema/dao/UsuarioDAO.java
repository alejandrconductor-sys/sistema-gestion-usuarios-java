package com.sistema.dao;
import com.sistema.config.Conexion;
import com.sistema.modelo.Usuario;
import com.sistema.modelo.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private static final String SQL_LISTAR =
            "SELECT id_usuario, nombre, apellido, email, estado FROM usuario";

    public List<Usuario> listarUsuario() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql=
                "SELECT " +
                "u.id_usuario, " +
                "u.nombre, " +
                "u.apellido, " +
                "u.email, " +
                "u.estado, " +
                "r.id_rol," +
                "r.nombre AS nombre_rol " +
            "FROM usuario u " +
            "LEFT JOIN usuario_rol ur ON u.id_usuario = ur.id_usuario " +
            "LEFT JOIN rol r ON ur.id_rol = r.id_rol";
            
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("id_usuario"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setEmail(rs.getString("email"));
                u.setEstado(rs.getString("estado"));
                int idRol = rs.getInt("id_rol");
                String nombreRol = rs.getString("nombre_rol");
                Rol rol=new Rol(idRol, nombreRol);
                u.setRol(rol);
                usuarios.add(u);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios", e);
        }

            return usuarios;

    }

        public boolean existeEmail(String email) {

        String sql = 
        "SELECT COUNT(*) FROM usuario WHERE email = ?";

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

        public boolean existeEmailParaOtroUsuario(String email, int idUsuario) {

        String sql = 
        "SELECT COUNT(*) FROM usuario WHERE email = ? AND id_usuario <> ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
           ps.setInt(2, idUsuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error verificando email en actualización", e);
        }

        return false;
    }


        public Usuario guardar(Usuario usuario) {

        String sqlUsuario = 
            "INSERT INTO usuario (nombre, apellido, email, estado, password) VALUES (?, ?, ?, ?, ?)";

        String sqlUsuarioRol = 
            "INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (?, ?)";

        try (Connection conn = Conexion.getConnection()) {

            conn.setAutoCommit(false); // Iniciamos transacción

        // Insertar usuario
        try (PreparedStatement psUsuario = conn.prepareStatement(
                sqlUsuario, PreparedStatement.RETURN_GENERATED_KEYS)) {

            psUsuario.setString(1, usuario.getNombre());
            psUsuario.setString(2, usuario.getApellido());
            psUsuario.setString(3, usuario.getEmail());
            psUsuario.setString(4, usuario.getEstado());
            psUsuario.setString(5, usuario.getPassword());

            psUsuario.executeUpdate();

            ResultSet rs = psUsuario.getGeneratedKeys();
            if (rs.next()) {
                usuario.setIdUsuario(rs.getInt(1));
            } else {
                throw new RuntimeException("No se genero elID delUsuario");
            }
        }

/*        // Buscar id_rol
            int idRol = 0;
            try (PreparedStatement psRol = conn.prepareStatement(sqlBuscarRol)) {
            psRol.setString(1, usuario.getRol().getNombre());
            ResultSet rsRol = psRol.executeQuery();

                if (rsRol.next()) {
                    idRol = rsRol.getInt("id_rol");
                } else {
                    throw new RuntimeException("Rol no encontrado");
                }
            }
*/
        // Insertar relación usuario_rol
            try (PreparedStatement psUsuarioRol = conn.prepareStatement(sqlUsuarioRol)) {
                
                int idRol = usuario.getRol().getIdRol();
                
                psUsuarioRol.setInt(1, usuario.getIdUsuario());
                psUsuarioRol.setInt(2, idRol);
                psUsuarioRol.executeUpdate();
            }

            conn.commit();// confirmamos
            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar usuario", e);
     }
    }

            // Actualizar Usuario

    public boolean actualizarUsuario(Usuario usuario){
        
        String sql = "UPDATE usuario SET nombre=?, apellido=?, email=?, estado=? WHERE id_usuario=?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getEstado());
            ps.setInt(5, usuario.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }                        
    }

    public void eliminar(int idUsuario) {

        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }

}
