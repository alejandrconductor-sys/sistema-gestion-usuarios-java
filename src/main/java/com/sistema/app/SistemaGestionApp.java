package com.sistema.app;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;
import java.util.List;

public class SistemaGestionApp {

    public static void main(String[] args) {

        System.out.println("=== SISTEMA DE GESTIÓN ===");

        UsuarioServicio usuarioServicio = new UsuarioServicio();
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            for (Usuario usuario : usuarios) {
                System.out.println(
                        "ID: " + usuario.getIdUsuario() +
                        " | Nombre: " + usuario.getNombre() +
                        " " + usuario.getApellido() +
                        " | Email: " + usuario.getEmail()
                );
            }
        }
    }
}