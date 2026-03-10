package com.sistema.servicio;
import com.sistema.dao.UsuarioDAO;
import com.sistema.modelo.Rol;
import com.sistema.modelo.Usuario;
import com.sistema.modelo.EstadoUsuario;
import com.sistema.seguridad.AutorizacionService;
import com.sistema.seguridad.Permiso;
import java.util.List;

public class UsuarioServicio {

    private static UsuarioServicio instancia;
    private UsuarioServicio() {}
    public static UsuarioServicio getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioServicio();
        }
        return instancia;
    }

    public Usuario guardar(String nombre, String apellido, String email, Rol rol, String password) {
        validarEmail(email);
        validarPassword(password);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setEstado(EstadoUsuario.PENDIENTE);
        usuario.setRol(rol);
        usuario.setPassword(password);

        return usuarioDAO.guardar(usuario);
    }

    public void actualizarUsuario(Usuario usuarioEditado, Usuario usuarioEditor) {
        Usuario usuarioExistente = usuarioDAO.buscarPorId(usuarioEditado.getIdUsuario());
        if (usuarioExistente == null) throw new RuntimeException("Usuario no encontrado");

        String rolEditor = usuarioEditor.getRol().getNombre();

        // Validar email solo si cambia
        if (!usuarioExistente.getEmail().equals(usuarioEditado.getEmail())) {
            validarEmail(usuarioEditado.getEmail());
            usuarioExistente.setEmail(usuarioEditado.getEmail());
        }

        usuarioExistente.setNombre(usuarioEditado.getNombre());
        usuarioExistente.setApellido(usuarioEditado.getApellido());
        // Control de roles
        if (rolEditor.equals("ADMIN")) {
            usuarioExistente.setEstado(usuarioEditado.getEstado());
            usuarioExistente.setRol(usuarioEditado.getRol());
        } else if (rolEditor.equals("RRHH")) {
            if (!usuarioExistente.getRol().getNombre().equals("USUARIO")) {
                throw new SecurityException("RRHH no puede modificar este usuario");
            }
            usuarioExistente.setEstado(usuarioEditado.getEstado());
        }
        // Cambiar contraseña si se proporcionó
        if (usuarioEditado.getPassword() != null && !usuarioEditado.getPassword().isEmpty()) {
            usuarioExistente.setPassword(usuarioEditado.getPassword());
        }

        usuarioDAO.actualizar(usuarioExistente);
    }

    public List<Usuario> listarSegunRol(Usuario solicitante) {
        String rol = solicitante.getRol().getNombre();
        return switch (rol) {
            case "ADMIN" -> usuarioDAO.listarTodos();
            case "RRHH" -> usuarioDAO.listarRRHHyUsuarios();
            default -> List.of(usuarioDAO.buscarPorId(solicitante.getIdUsuario()));
        };
    }

    public void desactivarUsuario(int idUsuario, Usuario usuarioLogueado) {
        if (!autorizacionService.tienePermiso(usuarioLogueado, Permiso.USUARIO_DESACTIVAR)) {
            throw new SecurityException("No tiene permisos para desactivar usuarios");
        }

        Usuario usuarioObjetivo = usuarioDAO.buscarPorId(idUsuario);
        if (usuarioObjetivo == null) throw new IllegalArgumentException("Usuario no encontrado");

        if (!usuarioLogueado.getRol().getNombre().equals("ADMIN") &&
            usuarioObjetivo.getRol().getNombre().equals("ADMIN")) {
            throw new SecurityException("No puede desactivar un ADMIN");
        }

        usuarioDAO.desactivarUsuario(idUsuario);
    }

    public Usuario autenticar(String email, String password) throws Exception {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario == null || !usuario.getPassword().equals(password)) {
            throw new Exception("Credenciales incorrectas, acceso denegado");
        }

        return switch (usuario.getEstado()) {
            case ACTIVO -> usuario;
            case PENDIENTE -> throw new Exception("Cuenta pendiente de aprobación del departamento de RRHH");
            case DESACTIVADO -> throw new Exception("Su cuenta ha sido desactivada");
            case BLOQUEADO -> throw new Exception("Su cuenta ha sido bloqueada");
        };
    }

    public Rol buscarRolPorNombre(String nombreRol) {
        Rol rol = usuarioDAO.buscarRolPorNombre(nombreRol);
        if (rol == null) throw new RuntimeException("El rol '" + nombreRol + "' no existe en la DB");
        return rol;
    }

    public Usuario buscarPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    public void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El email no tiene un formato válido.");
        }
        if (usuarioDAO.existeEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }
    }

    public void validarPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }
    }

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final AutorizacionService autorizacionService = new AutorizacionService();

}