package com.sistema.ui.dialog;

import java.awt.*;
import javax.swing.*;
import com.sistema.modelo.EstadoUsuario;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;
import com.sistema.ui.MainFrame;

public class LoginDialog extends JDialog {

    public LoginDialog(Frame parent, UsuarioServicio usuarioServicio) {
        super(parent, "Iniciar Sesión", true);
        this.usuarioServicio = usuarioServicio;
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setSize(420, 230); 
        setLayout(new BorderLayout(15, 15));
        setResizable(false);

        JPanel panelCentro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); 
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCentro.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panelCentro.add(txtEmail, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCentro.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        panelCentro.add(txtPassword, gbc);

        add(panelCentro, BorderLayout.CENTER);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnIngresar = new JButton("Ingresar");
        btnCancelar = new JButton("Cancelar");
        btnRegistrarse = new JButton("Registrarse");
        panelBotones.add(btnIngresar);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnRegistrarse);

        add(panelBotones, BorderLayout.SOUTH);

        setLocationRelativeTo(getOwner());

        configurarEventos();
    }

    private void configurarEventos() {
        btnIngresar.addActionListener(e -> autenticar());
        btnCancelar.addActionListener(e -> {
            usuarioAutenticado = null;
            dispose();
        });
        btnRegistrarse.addActionListener(e -> abrirRegistro());
    }

    private void autenticar() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe completar todos los campos",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Usuario usuario = usuarioServicio.autenticar(email, password);

            usuarioAutenticado = usuario;
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirRegistro() {
        if (getOwner() instanceof MainFrame mainFrame) {
            RegistroUsuarioDialog dialog = new RegistroUsuarioDialog(mainFrame, usuarioServicio);
            dialog.setVisible(true);
        }
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnIngresar, btnCancelar, btnRegistrarse;
    private Usuario usuarioAutenticado;
    private UsuarioServicio usuarioServicio;
}