package com.sistema.ui;
import javax.swing.*;
import java.awt.*;

public class PanelPrincipal extends JPanel {

    public PanelPrincipal() {
        setLayout(new BorderLayout());
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JLabel titulo = new JLabel("Bienvenido al Sistema de Gestión RRHH", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        add(titulo, BorderLayout.CENTER);
    }
}
