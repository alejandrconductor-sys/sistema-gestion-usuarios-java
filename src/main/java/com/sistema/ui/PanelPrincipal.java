package com.sistema.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class PanelPrincipal extends JPanel {

    public PanelPrincipal() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        // =========================
        // PANEL PRINCIPAL CENTRAL
        // =========================
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(245, 245, 245));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // =========================
        // TITULO
        // =========================
        JLabel titulo = new JLabel("SISTEMA DE GESTIÓN EMPRESARIAL v1.1");
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Proyecto desarrollado en Java + MariaDB + Power BI");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitulo.setForeground(Color.DARK_GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =========================
        // DESCRIPCION
        // =========================
        JTextArea descripcion = new JTextArea(
            "Sistema multiplataforma orientado a gestión empresarial.\n\n" +
            "Incluye módulos de:\n" +
            "- Usuarios\n" +
            "- Ventas\n" +
            "- Inventario\n" +
            "- Solicitudes\n" +
            "- Gestión Comercial\n" +
            "- Reportes y análisis con Power BI"
        );

        descripcion.setEditable(false);
        descripcion.setFocusable(false);
        descripcion.setFont(new Font("Arial", Font.PLAIN, 15));
        descripcion.setBackground(new Color(245, 245, 245));
        descripcion.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // =========================
        // TECNOLOGIAS
        // =========================
        JLabel tecnologias = new JLabel(
            "Tecnologías: Java | Swing | MariaDB | Git | GitHub | Power BI | Ubuntu | Windows"
        );

        tecnologias.setFont(new Font("Arial", Font.BOLD, 14));
        tecnologias.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =========================
        // PANEL BOTONES
        // =========================
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 15, 15));
        panelBotones.setBackground(new Color(245, 245, 245));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));

        JButton btnGitHub = crearBoton("GitHub");
        JButton btnLinkedIn = crearBoton("LinkedIn");
        JButton btnPowerBI = crearBoton("Power BI");
        JButton btnVideo10 = crearBoton("Video Demo 1.0");
        JButton btnUbuntu = crearBoton("Video Ubuntu v1.1");
        JButton btnWindows = crearBoton("Video Windows v1.1");

        // =========================
        // EVENTOS BOTONES
        // =========================
        btnGitHub.addActionListener(e -> abrirEnlace(
            "https://github.com/alejandrconductor-sys/sistema-gestion-usuarios-java"
        ));

        btnLinkedIn.addActionListener(e -> abrirEnlace(
            "https://www.linkedin.com/in/rafael-alejandro-marquez-araujo-4276093b7/"
        ));

        btnPowerBI.addActionListener(e -> abrirEnlace(
            "https://app.powerbi.com/groups/me/reports/8a5e2020-15a6-4af8-8595-be4e50deb41b/a5a71bfffa2413879405?experience=power-bi"
        ));

        btnVideo10.addActionListener(e -> abrirEnlace(
            "https://www.youtube.com/watch?v=jih3bNQ8UdI"
        ));

        // TEMPORALES HASTA SUBIR VIDEOS
        btnUbuntu.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Video Ubuntu v1.1 próximamente disponible.")
        );

        btnWindows.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Video Windows v1.1 próximamente disponible.")
        );

        // =========================
        // AGREGAR BOTONES
        // =========================
        panelBotones.add(btnGitHub);
        panelBotones.add(btnLinkedIn);
        panelBotones.add(btnPowerBI);
        panelBotones.add(btnVideo10);
        panelBotones.add(btnUbuntu);
        panelBotones.add(btnWindows);

        // =========================
        // FOOTER
        // =========================
        JLabel footer = new JLabel(
            "Desarrollado por Rafael Marquez",
            SwingConstants.CENTER
        );

        footer.setFont(new Font("Arial", Font.ITALIC, 14));
        footer.setForeground(Color.GRAY);

        // =========================
        // AGREGAR COMPONENTES
        // =========================
        panelCentral.add(titulo);
        panelCentral.add(Box.createVerticalStrut(10));
        panelCentral.add(subtitulo);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(descripcion);
        panelCentral.add(tecnologias);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(panelBotones);
        panelCentral.add(Box.createVerticalStrut(15));
        panelCentral.add(footer);

        add(panelCentral, BorderLayout.CENTER);
    }

    // =========================
    // CREAR BOTONES
    // =========================
    private JButton crearBoton(String texto) {

        JButton boton = new JButton(texto);

        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(52, 73, 94));
        boton.setForeground(Color.WHITE);

        return boton;
    }

    // =========================
    // ABRIR ENLACES
    // =========================
    private void abrirEnlace(String url) {

        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "No se pudo abrir el enlace.");
        }
    }
}