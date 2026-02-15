package com.sistema.app;

import com.sistema.dao.UsuarioDAO;
import com.sistema.modelo.Usuario;
import java.util.List;

public class TestUsuarioDAO {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        List<Usuario> usuarios = usuarioDAO.listarUsuarios();

        if (usuarios.isEmpty()) {
            System.out.println("⚠️ No hay usuarios registrados.");
        } else {
            System.out.println("✅ Lista de usuarios:");
            for (Usuario u : usuarios) {
                System.out.println(u);
            }
        }
    }
}
