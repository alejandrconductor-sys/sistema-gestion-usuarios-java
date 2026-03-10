package com.sistema.ui;

import javax.swing.*;
import java.awt.*;
import com.sistema.modelo.Usuario;
import com.sistema.modelo.EstadoUsuario;
import com.sistema.servicio.UsuarioServicio;
import com.sistema.ui.dialog.LoginDialog;
import com.sistema.ui.panel.UsuarioPanel;

public class MainFrame extends JFrame {

    public MainFrame() {
        usuarioServicio = UsuarioServicio.getInstancia();
        configurarVentana();
        inicializarMenu();
        inicializarContenido();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión para RRHH");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inicializarMenu() {
        JMenuBar menuBar = new JMenuBar();

        menuUsuarios = new JMenu("Usuarios");
        menuItemSesion = new JMenuItem("Iniciar Sesión");
        menuUsuarios.add(menuItemSesion);
        menuBar.add(menuUsuarios);

        JMenu menuSistema = new JMenu("Sistema");
        menuItemSalir = new JMenuItem("Salir");
        menuItemSalir.addActionListener(e -> System.exit(0));
        menuSistema.add(menuItemSalir);
        menuBar.add(menuSistema);

        setJMenuBar(menuBar);

        // Acción dinámica iniciar / cerrar sesión
        menuItemSesion.addActionListener(e -> {
            if (usuarioLogueado == null) {
                iniciarSesion();
            } else {
                cerrarSesion();
            }
        });
    }

    private void inicializarContenido() {
        setContentPane(new PanelPrincipal());
        revalidate();
        repaint();
    }

    private void iniciarSesion() {
        LoginDialog login = new LoginDialog(this, usuarioServicio);
        login.setVisible(true);

        Usuario usuario = login.getUsuarioAutenticado();
        if (usuario != null && usuario.getEstado() == EstadoUsuario.ACTIVO) {
            usuarioLogueado = usuario;

            menuItemSesion.setText("Cerrar Sesión");

            setContentPane(new UsuarioPanel(this, usuarioLogueado));
            revalidate();
            repaint();
        }
    }

    private void cerrarSesion() {
        usuarioLogueado = null;

        setContentPane(new PanelPrincipal());
        revalidate();
        repaint();

        menuItemSesion.setText("Iniciar Sesión");
    }

    public void volverAlPanelPrincipal() {
        usuarioLogueado = null;
        setContentPane(new PanelPrincipal());
        revalidate();
        repaint();
        menuItemSesion.setText("Iniciar Sesión");
    }

    private UsuarioServicio usuarioServicio;
    private Usuario usuarioLogueado;
    private JMenu menuUsuarios;
    private JMenuItem menuItemSesion;
    private JMenuItem menuItemSalir;
}