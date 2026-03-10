package com.sistema.ui.dialog;

import javax.swing.*;
import java.awt.*;
import com.sistema.modelo.EstadoUsuario;
import com.sistema.modelo.Rol;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;

public class UsuarioDialog extends JDialog {


    // Para crear NUEVO usuario
    public UsuarioDialog(Window parent, Usuario usuarioEditor) {
        super(parent, "Nuevo Usuario", ModalityType.APPLICATION_MODAL);
        this.usuarioEditor = usuarioEditor;
        configurarVentana();
        inicializarComponentes();
        aplicarRestricciones(); // Bloquea roles según el editor
    }

    // Para EDITAR usuario existente
    public UsuarioDialog(Window parent, Usuario usuario, Usuario usuarioEditor) {
        super(parent, "Editar Usuario", ModalityType.APPLICATION_MODAL);
        this.usuario = usuario;
        this.usuarioEditor = usuarioEditor;
        this.modoEdicion = true;
        configurarVentana();
        inicializarComponentes();
        cargarDatos();
        aplicarRestricciones();
    }
    
    private void configurarVentana() {
        setSize(450, 380);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        add(crearPanelFormulario(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelFormulario() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);

        // Apellido
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        txtApellido = new JTextField(20);
        panel.add(txtApellido, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);

        // Estado
        gbc.gridx = 0; gbc.gridy = 3;
        lblEstado = new JLabel("Estado:");
        panel.add(lblEstado, gbc);
        gbc.gridx = 1;
        cmbEstado = new JComboBox<>(EstadoUsuario.values());
        panel.add(cmbEstado, gbc);

        // Rol
        gbc.gridx = 0; gbc.gridy = 4;
        lblRol = new JLabel("Rol:");
        panel.add(lblRol, gbc);
        gbc.gridx = 1;
        cbRol = new JComboBox<>();
        cbRol.addItem(new Rol(1, "ADMIN"));
        cbRol.addItem(new Rol(2, "RRHH"));
        cbRol.addItem(new Rol(3, "USUARIO"));
        panel.add(cbRol, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        panel.add(txtPassword, gbc);

        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> dispose());
        btnGuardar.addActionListener(e -> guardarUsuario());

        panel.add(btnGuardar);
        panel.add(btnCancelar);
        return panel;
    }

    private void aplicarRestricciones() {
        if (!modoEdicion && usuarioEditor != null) {
            // Crear usuario
            if (usuarioEditor.getRol().getNombre().equals("RRHH")) {
                // RRHH no puede asignar ADMIN
                for (int i = 0; i < cbRol.getItemCount(); i++) {
                    Rol rol = cbRol.getItemAt(i);
                    if (rol.getNombre().equals("ADMIN")) {
                        cbRol.removeItem(rol);
                        break;
                    }
                }
            }
        }

        if (!modoEdicion) return;

        String rolEditor = usuarioEditor.getRol().getNombre();

        if (rolEditor.equals("USUARIO")) {
            if (usuarioEditor.getIdUsuario() != usuario.getIdUsuario()) {
                JOptionPane.showMessageDialog(this,
                        "No tiene permisos para editar este usuario.",
                        "Acceso denegado",
                        JOptionPane.ERROR_MESSAGE);
                dispose();
                return;
            }

            txtEmail.setEnabled(false);
            cbRol.setVisible(false);
            cmbEstado.setVisible(false);
            lblRol.setVisible(false);
            lblEstado.setVisible(false);

        } else if (rolEditor.equals("RRHH")) {
            // RRHH: bloquear edición de ADMIN y roles
            if (usuario.getRol().getNombre().equals("ADMIN")) {
                txtNombre.setEnabled(false);
                txtApellido.setEnabled(false);
                txtEmail.setEnabled(false);
                cbRol.setVisible(false);
                lblRol.setVisible(false);
                cmbEstado.setVisible(false);
                lblEstado.setVisible(false);
            } else {
                cbRol.setEnabled(false); // RRHH no puede cambiar roles
            }
        }
    }

    private void guardarUsuario() {
        if (txtNombre.getText().trim().isEmpty() ||
            txtApellido.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Todos los campos obligatorios deben completarse.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!txtEmail.getText().contains("@")) {
            JOptionPane.showMessageDialog(this,
                    "Email inválido.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (modoEdicion) {
                usuario.setNombre(txtNombre.getText());
                usuario.setApellido(txtApellido.getText());

                String rolEditor = usuarioEditor.getRol().getNombre();
                if (!rolEditor.equals("USUARIO")) {
                    usuario.setEmail(txtEmail.getText());
                    usuario.setEstado((EstadoUsuario) cmbEstado.getSelectedItem());
                    if (rolEditor.equals("ADMIN")) {
                        usuario.setRol((Rol) cbRol.getSelectedItem());
                    }
                }

                if (txtPassword.getPassword().length > 0) {
                    usuario.setPassword(new String(txtPassword.getPassword()));
                }

                UsuarioServicio.getInstancia().actualizarUsuario(usuario, usuarioEditor);
                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
            } else {
                Rol rolSeleccionado = (Rol) cbRol.getSelectedItem();
                Usuario nuevo = UsuarioServicio.getInstancia().guardar(
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtEmail.getText(),
                        rolSeleccionado,
                        new String(txtPassword.getPassword())
                );
                JOptionPane.showMessageDialog(this,
                        "Usuario guardado correctamente:\n" + nuevo);
            }

            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatos() {
        txtNombre.setText(usuario.getNombre());
        txtApellido.setText(usuario.getApellido());
        txtEmail.setText(usuario.getEmail());
        cmbEstado.setSelectedItem(usuario.getEstado());

        for (int i = 0; i < cbRol.getItemCount(); i++) {
            Rol rolItem = cbRol.getItemAt(i);
            if (rolItem.getNombre().equals(usuario.getRol().getNombre())) {
                cbRol.setSelectedIndex(i);
                break;
            }
        }
    }

    private boolean modoEdicion = false;
    private Usuario usuario;
    private Usuario usuarioEditor;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JComboBox<EstadoUsuario> cmbEstado;
    private JComboBox<Rol> cbRol;
    private JPasswordField txtPassword;
    private JLabel lblEstado;
    private JLabel lblRol;

}