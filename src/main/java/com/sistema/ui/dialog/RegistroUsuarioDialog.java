package com.sistema.ui.dialog;

import javax.swing.*;
import java.awt.*;
import com.sistema.servicio.UsuarioServicio;
import com.sistema.ui.MainFrame;

public class RegistroUsuarioDialog extends JDialog {

    public RegistroUsuarioDialog(Frame owner, UsuarioServicio usuarioServicio) {
        super(owner, "Registro de Usuario", true);
        this.usuarioServicio = usuarioServicio;
        initComponents();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setSize(450, 320); // un poco más grande y cómodo
        setLayout(new BorderLayout(15, 15));
        setResizable(false); // evita que el usuario deforme el diálogo

        // Panel central con GridBagLayout para alineación precisa
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); // espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Nombre
        panelCampos.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        tfNombre = new JTextField(20);
        panelCampos.add(tfNombre, gbc);

        // Apellido
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelCampos.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        tfApellido = new JTextField(20);
        panelCampos.add(tfApellido, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelCampos.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        tfEmail = new JTextField(20);
        panelCampos.add(tfEmail, gbc);

        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 3;
        panelCampos.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        pfPassword = new JPasswordField(20);
        panelCampos.add(pfPassword, gbc);

        add(panelCampos, BorderLayout.CENTER);

    // Panel de botones alineado a la derecha
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);
        add(panelBotones, BorderLayout.SOUTH);

    // Centrar diálogo sobre su dueño
        setLocationRelativeTo(getOwner());

        configurarEventos();
    }
    private void configurarEventos() {
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> dispose());
    }

 private void registrarUsuario() {

    try {

        usuarioServicio.guardar(
            tfNombre.getText(),
            tfApellido.getText(),
            tfEmail.getText(),
            usuarioServicio.buscarRolPorNombre("USUARIO"),
            new String(pfPassword.getPassword())
        );

        JOptionPane.showMessageDialog(this,
            "Registro exitoso. Su cuenta estará pendiente de activación por RRHH.",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);

        dispose();

        if (getOwner() instanceof MainFrame mainFrame) {
            mainFrame.volverAlPanelPrincipal();
        }

    } catch (Exception ex) {

        JOptionPane.showMessageDialog(this,
            ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

    private JTextField tfNombre, tfApellido, tfEmail;
    private JPasswordField pfPassword;
    private JButton btnRegistrar, btnCancelar;
    private UsuarioServicio usuarioServicio;
}