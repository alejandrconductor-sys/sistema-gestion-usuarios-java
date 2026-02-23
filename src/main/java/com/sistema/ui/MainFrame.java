package com.sistema.ui;
import javax.swing.*;
import com.sistema.ui.dialog.*;
import com.sistema.ui.panel.UsuarioPanel;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        configurarVentana();
        inicializarMenu();
        inicializarContenido();
        setVisible(true);
        new GestionUsuariosDialog(this);    
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
        JMenu menuUsuarios = new JMenu("Usuarios");
        JMenuItem itemGestionUsuarios = new JMenuItem("Gestión de Usuarios");

        menuUsuarios.add(itemGestionUsuarios);
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemSalir = new JMenuItem("Salir");
        itemSalir.addActionListener(e -> System.exit(0));
        menuSistema.add(itemSalir);
        menuBar.add(menuUsuarios);
        menuBar.add(menuSistema);
        setJMenuBar(menuBar);

         itemGestionUsuarios.addActionListener(e -> {
        setContentPane(new UsuarioPanel(this));
        revalidate();
        repaint();
        });
        
    }

    private void inicializarContenido() {
        add(new PanelPrincipal(), BorderLayout.CENTER);
    }

}
