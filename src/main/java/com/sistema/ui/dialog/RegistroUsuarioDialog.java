package com.sistema.ui.dialog;
import javax.swing.*;
import java.awt.*;
import com.sistema.servicio.UsuarioServicio;
import com.sistema.ui.MainFrame;

public class RegistroUsuarioDialog extends JDialog {

    private JTextField tfNombre, tfApellido, tfEmail;
    private JPasswordField pfPassword;
    private JButton btnRegistrar, btnCancelar;
    private UsuarioServicio usuarioServicio;

    public RegistroUsuarioDialog(Frame owner, UsuarioServicio usuarioServicio) {
        super(owner, "Registro de Usuario", true);
        this.usuarioServicio = usuarioServicio;
        initComponents();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {

        setSize(450, 320);
        setLayout(new BorderLayout(15, 15));
        setResizable(false);

        // PANEL CAMPOS
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelCampos.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        tfNombre = new JTextField(20);
        panelCampos.add(tfNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelCampos.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        tfApellido = new JTextField(20);
        panelCampos.add(tfApellido, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelCampos.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        tfEmail = new JTextField(20);
        panelCampos.add(tfEmail, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelCampos.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        pfPassword = new JPasswordField(20);
        panelCampos.add(pfPassword, gbc);

        add(panelCampos, BorderLayout.CENTER);

        // PANEL BOTONES
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));

        btnRegistrar = new JButton("Registrar");
        btnCancelar = new JButton("Cancelar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        configurarEventos();
    }

    private void configurarEventos() {
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void registrarUsuario() {

        // Validación básica UI (solo formato mínimo)
        if (tfNombre.getText().trim().isEmpty() ||
            tfApellido.getText().trim().isEmpty() ||
            tfEmail.getText().trim().isEmpty() ||
            pfPassword.getPassword().length == 0) {

            JOptionPane.showMessageDialog(this,
                    "Todos los campos son obligatorios.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {

            usuarioServicio.registrar(
                    tfNombre.getText().trim(),
                    tfApellido.getText().trim(),
                    tfEmail.getText().trim(),
                    new String(pfPassword.getPassword())
            );

            JOptionPane.showMessageDialog(this,
                    "Registro exitoso. Pendiente de aprobación por RRHH.",
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
}