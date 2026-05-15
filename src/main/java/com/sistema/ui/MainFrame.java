package com.sistema.ui;
import javax.swing.*;
import java.awt.*;

import com.sistema.modelo.Usuario;
import com.sistema.modelo.EstadoUsuario;

import com.sistema.servicio.UsuarioServicio;

import com.sistema.ui.dialog.LoginDialog;
import com.sistema.ui.gerente.GerenteVentasPanel;

import com.sistema.ui.panel.SolicitudCambioPanel;
import com.sistema.ui.panel.UsuarioPanel;
import com.sistema.ui.panel.GestionInventarioPanel;
import com.sistema.ui.panel.InventarioPanel;
import com.sistema.ui.panel.VentaGerentePanel;

import com.sistema.ui.venta.VentaPanel;

public class MainFrame extends JFrame {

    private UsuarioServicio usuarioServicio;
    private Usuario usuarioLogueado;
    private JMenu menuUsuarios;
    private JMenuItem menuItemSesion;
    private JMenuItem menuItemSalir;

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

    // 🔥 MÉTODO CLAVE (AQUÍ VA TODO)
    private void iniciarSesion() {

        LoginDialog login = new LoginDialog(this, usuarioServicio);
        login.setVisible(true);

        Usuario usuario = login.getUsuarioAutenticado();

        if (usuario != null && usuario.getEstado() == EstadoUsuario.ACTIVO) {

            usuarioLogueado = usuario;
            menuItemSesion.setText("Cerrar Sesión");

            JTabbedPane tabs = new JTabbedPane();

            String rol = usuarioLogueado.getRol().getNombre();

            // 🔹 SIEMPRE USUARIOS
            tabs.addTab("Usuarios", new UsuarioPanel(this, usuarioLogueado));

            // 🔹 ADMIN
            if ("ADMIN".equals(rol)) {
                tabs.addTab("Solicitudes", new SolicitudCambioPanel(usuarioLogueado));
            }

            // 🔹 GERENTE
            if ("GERENTE_VENTAS".equals(rol)) {

                tabs.addTab("Gestión Comercial", new GerenteVentasPanel(usuarioLogueado));

                // Inventario y Adminventas
                tabs.addTab("Inventario", new InventarioPanel(usuarioLogueado));
                tabs.addTab("Gestión Ventas", new VentaGerentePanel(usuarioLogueado));            
                tabs.addTab("Gestión Inventario", new GestionInventarioPanel(usuario));
            }
        
            // 🔹 VENDEDOR
            if ("VENDEDOR".equals(rol)) {
                tabs.addTab("Ventas", new VentaPanel(usuarioLogueado));
            }

            setContentPane(tabs);
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
        cerrarSesion();
    }
}