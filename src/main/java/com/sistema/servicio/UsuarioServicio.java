package com.sistema.servicio;
import com.sistema.dao.UsuarioDAO;
import com.sistema.modelo.Usuario;

import java.util.List;

public class UsuarioServicio {

    private UsuarioDAO usuarioDAO;

    public UsuarioServicio() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Obtiene la lista de usuarios del sistema.
     * Aplica reglas de negocio básicas.
     */
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = usuarioDAO.listarUsuarios();

        // Regla de negocio: nunca devolver null
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios Registrados");
        }

        return usuarios;
    }
}
