package com.sistema.app;
import com.sistema.dao.UsuarioDAO;
import com.sistema.modelo.Rol;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;
import com.sistema.ui.MainFrame;

import java.util.List;

import javax.swing.SwingUtilities;

public class SistemaGestionApp {

    public static void main(String[] args) {

       SwingUtilities.invokeLater(() ->{
            new MainFrame();
        });

/*         UsuarioDAO usuarioDAO = new UsuarioDAO();

    List<Usuario> usuarios = usuarioDAO.listarUsuario();

    for (Usuario u : usuarios) {
        System.out.println("ID: " + u.getIdUsuario());
        System.out.println("Nombre: " + u.getNombre() + " " + u.getApellido());
        System.out.println("Email: " + u.getEmail());
        System.out.println("Estado: " + u.getEstado());
        System.out.println("Rol: " + u.getRol());
        System.out.println("---------------------------");
    }

    */
   


/*    PRUEBA CARGAR DESDE EL MAIN UN USUARIO

Rol rol = new Rol(1); // usa un id_rol que exista en tu BD

Usuario usuario = new Usuario();
usuario.setNombre("Prueba");
usuario.setApellido("Sistema");
usuario.setEmail("prueba@sistema.com");
usuario.setEstado("ACTIVO");
usuario.setPassword("1234");
usuario.setRol(rol);

UsuarioServicio servicio = new UsuarioServicio();
servicio.guardar(usuario);

System.out.println("Usuario guardado con ID: " + usuario.getIdUsuario());
  

FIN PRUEBA*/
}
}