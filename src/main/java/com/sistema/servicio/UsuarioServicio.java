package com.sistema.servicio;
import com.sistema.dao.UsuarioDAO;
import com.sistema.modelo.Rol;
import com.sistema.modelo.Usuario;
//import java.util.ArrayList;
import java.util.List;

public class UsuarioServicio {


    public UsuarioServicio() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public static UsuarioServicio getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioServicio();
        }
        return instancia;
    }

    public Usuario guardar( String nombre, String apellido, String email, String estado, Rol rol, String password) {

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }

        if (usuarioDAO.existeEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setEstado(estado);
        usuario.setRol(rol);
        usuario.setPassword(password);
        
        return usuarioDAO.guardar(usuario);

    }

    public List<Usuario> listar() {
        return usuarioDAO.listarUsuario();
    }

    public List<Usuario> listaUsuarios() {
        return usuarioDAO.listarUsuario();
    }

    public boolean actualizarUsuario(Usuario usuario){

       if (usuarioDAO.existeEmailParaOtroUsuario(usuario.getEmail(), usuario.getIdUsuario())) {
           throw new IllegalArgumentException("El email ya está registrado por otro usuario.");
        } 

        return usuarioDAO.actualizarUsuario(usuario);
    }

    public void eliminar(int idUsuario){
        usuarioDAO.eliminar(idUsuario);
    }

    private UsuarioDAO usuarioDAO=new UsuarioDAO();
    private static UsuarioServicio instancia;


}