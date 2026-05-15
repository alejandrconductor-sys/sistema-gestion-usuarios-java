package com.sistema.servicio;
import com.sistema.dao.UsuarioDAO;
import com.sistema.modelo.*;
import com.sistema.seguridad.AutorizacionService;
import com.sistema.seguridad.Permiso;
import java.util.List;

public class UsuarioServicio {

    private static UsuarioServicio instancia;
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final AutorizacionService autorizacionService = new AutorizacionService();
    private UsuarioServicio() {}

    public static UsuarioServicio getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioServicio();
        }
        return instancia;
    }

    // CREAR USUARIO
    public Usuario guardar(Usuario usuarioEditor, String nombre, String apellido,
                           String email, Rol rol, String password) {

        validarUsuarioAutenticado(usuarioEditor);
        validarEmail(email);
        validarPassword(password);
        validarCreacionPorRol(usuarioEditor, rol);

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(rol);
        usuario.setEstado(EstadoUsuario.PENDIENTE);

        return usuarioDAO.guardar(usuario);
    }

    private void validarUsuarioAutenticado(Usuario usuario) {
        if (usuario == null) {
            throw new SecurityException("No hay usuario autenticado");
        }
    }

    private void validarCreacionPorRol(Usuario editor, Rol rolCrear) {

        String rolEditor = editor.getRol().getNombre();

        switch (rolEditor) {

            case "ADMIN" -> {}

            case "RRHH" -> {
                if ("ADMIN".equals(rolCrear.getNombre())) {
                    throw new SecurityException("RRHH no puede crear ADMIN");
                }
            }

            case "GERENTE_VENTAS" -> {
                if (!"VENDEDOR".equals(rolCrear.getNombre())) {
                    throw new SecurityException("GERENTE solo puede crear VENDEDORES");
                }
            }

            case "VENDEDOR" ->
                throw new SecurityException("VENDEDOR no puede crear usuarios");

            default ->
                throw new SecurityException("Rol no autorizado");
        }
    }

    // ACTUALIZAR USUARIO
    public void actualizarUsuario(Usuario editado, Usuario editor) {

        Usuario existente = usuarioDAO.buscarPorId(editado.getIdUsuario());

        if (existente == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        validarCambioEmail(existente, editado);

        // datos básicos SIEMPRE
        existente.setNombre(editado.getNombre());
        existente.setApellido(editado.getApellido());

        aplicarReglasPorRol(editor, existente, editado);

        actualizarPasswordSiAplica(editado, existente);

        usuarioDAO.actualizar(existente);
    }

    private void validarCambioEmail(Usuario existente, Usuario editado) {
        if (!existente.getEmail().equals(editado.getEmail())) {
            validarEmail(editado.getEmail());
            existente.setEmail(editado.getEmail());
        }
    }

    private void aplicarReglasPorRol(Usuario editor, Usuario existente, Usuario editado) {

        String rolEditor = editor.getRol().getNombre();
        String rolObjetivo = existente.getRol().getNombre();

        switch (rolEditor) {

            case "ADMIN" -> {
                existente.setEstado(editado.getEstado());
                existente.setRol(editado.getRol());
            }

            case "RRHH" -> {
                if ("ADMIN".equals(rolObjetivo)) {
                    throw new SecurityException("RRHH no puede modificar ADMIN");
                }
                existente.setEstado(editado.getEstado());
            }

            case "GERENTE_VENTAS" -> manejarGerente(editor, existente, editado, rolObjetivo);

            case "VENDEDOR" -> manejarVendedor(editor, existente, editado);

            default -> throw new SecurityException("Rol no autorizado");
        }
    }

    private void manejarGerente(Usuario editor, Usuario existente,
                               Usuario editado, String rolObjetivo) {

        boolean esMismo = editor.getIdUsuario() == existente.getIdUsuario();

        // Auto-edición, datos personales
        if (esMismo) return;

        // Solo puede tocar VENDEDORES
        if (!"VENDEDOR".equals(rolObjetivo)) {
            throw new SecurityException("GERENTE solo puede modificar VENDEDORES");
        }

        existente.setEstado(editado.getEstado());
    }

    private void manejarVendedor(Usuario editor, Usuario existente, Usuario editado) {

        if (editor.getIdUsuario() != existente.getIdUsuario()) {
            throw new SecurityException("No puede modificar otros usuarios");
        }

    }

    private void actualizarPasswordSiAplica(Usuario editado, Usuario existente) {
        if (editado.getPassword() != null && !editado.getPassword().isEmpty()) {
            existente.setPassword(editado.getPassword());
        }
    }

    // LISTAR
    public List<Usuario> listarSegunRol(Usuario solicitante) {

        return switch (solicitante.getRol().getNombre()) {

            case "ADMIN" -> usuarioDAO.listarTodos();

            case "RRHH" -> usuarioDAO.listarRRHHyUsuarios();

            case "GERENTE_VENTAS" -> {
                List<Usuario> lista = usuarioDAO.listarPorRol("VENDEDOR");
                lista.add(usuarioDAO.buscarPorId(solicitante.getIdUsuario())); // se agrega a sí mismo
                yield lista;
            }

            case "VENDEDOR" -> List.of(usuarioDAO.buscarPorId(solicitante.getIdUsuario()));

            default -> throw new SecurityException("Rol no autorizado");
        };
    }

    // DESACTIVAR
    public void desactivarUsuario(int idUsuario, Usuario logueado) {

        if (!autorizacionService.tienePermiso(logueado, Permiso.USUARIO_DESACTIVAR)) {
            throw new SecurityException("No tiene permisos");
        }

        Usuario objetivo = usuarioDAO.buscarPorId(idUsuario);

        if (objetivo == null) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        if (!"ADMIN".equals(logueado.getRol().getNombre()) &&
            "ADMIN".equals(objetivo.getRol().getNombre())) {
            throw new SecurityException("No puede desactivar un ADMIN");
        }

        usuarioDAO.desactivarUsuario(idUsuario);
    }

    // LOGIN
    public Usuario autenticar(String email, String password) throws Exception {

        Usuario usuario = usuarioDAO.buscarPorEmail(email);

        if (usuario == null || !usuario.getPassword().equals(password)) {
            throw new Exception("Credenciales incorrectas");
        }

        return switch (usuario.getEstado()) {
            case ACTIVO -> usuario;
            case PENDIENTE -> throw new Exception("Cuenta pendiente de aprobación");
            case DESACTIVADO -> throw new Exception("Cuenta desactivada");
            case BLOQUEADO -> throw new Exception("Cuenta bloqueada");
        };
    }

    // REGISTRO PÚBLICO
    public Usuario registrar(String nombre, String apellido, String email, String password) {

        validarEmail(email);
        validarPassword(password);

        Rol rol = usuarioDAO.buscarRolPorNombre("VENDEDOR");

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(rol);
        usuario.setEstado(EstadoUsuario.PENDIENTE);

        return usuarioDAO.guardar(usuario);
    }

    // UTILIDADES
    public Rol buscarRolPorNombre(String nombreRol) {
        Rol rol = usuarioDAO.buscarRolPorNombre(nombreRol);
        if (rol == null) throw new RuntimeException("Rol no existe");
        return rol;
    }

    public Usuario buscarPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    public void validarEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email obligatorio");
        }

        if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Formato inválido");
        }

        if (usuarioDAO.existeEmail(email)) {
            throw new IllegalArgumentException("Email ya registrado");
        }
    }

    public void validarPassword(String password) {

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password obligatorio");
        }

        if (password.length() < 6) {
            throw new IllegalArgumentException("Mínimo 6 caracteres");
        }
    }
}
