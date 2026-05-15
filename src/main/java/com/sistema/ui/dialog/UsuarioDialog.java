package com.sistema.ui.dialog;
import javax.swing.*;
import java.awt.*;
import com.sistema.modelo.EstadoUsuario;
import com.sistema.modelo.Rol;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;

public class UsuarioDialog extends JDialog {

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

    public UsuarioDialog(Window parent, Usuario usuarioEditor) {
        super(parent, "Nuevo Usuario", ModalityType.APPLICATION_MODAL);
        this.usuarioEditor = usuarioEditor;
        init();
    }

    public UsuarioDialog(Window parent, Usuario usuario, Usuario usuarioEditor) {
        super(parent, "Editar Usuario", ModalityType.APPLICATION_MODAL);
        this.usuario = usuario;
        this.usuarioEditor = usuarioEditor;
        this.modoEdicion = true;
        init();
        cargarDatos();
    }

    // INIT

    private void init() {
        setSize(450, 380);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));

        add(crearFormulario(), BorderLayout.CENTER);
        add(crearBotones(), BorderLayout.SOUTH);

        aplicarRestricciones();
    }

    //  UI

    private JPanel crearFormulario() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = crearGBC();

        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtEmail = new JTextField(20);
        txtPassword = new JPasswordField(20);

        cmbEstado = new JComboBox<>(EstadoUsuario.values());
        cbRol = new JComboBox<>();

        cbRol.addItem(new Rol(1, "ADMIN"));
        cbRol.addItem(new Rol(2, "RRHH"));
        cbRol.addItem(new Rol(4, "GERENTE_VENTAS"));
        cbRol.addItem(new Rol(5, "VENDEDOR"));

        lblEstado = new JLabel("Estado:");
        lblRol = new JLabel("Rol:");

        addCampo(panel, gbc, 0, "Nombre:", txtNombre);
        addCampo(panel, gbc, 1, "Apellido:", txtApellido);
        addCampo(panel, gbc, 2, "Email:", txtEmail);
        addCampo(panel, gbc, 3, lblEstado, cmbEstado);
        addCampo(panel, gbc, 4, lblRol, cbRol);
        addCampo(panel, gbc, 5, "Password:", txtPassword);

        return panel;
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarUsuario());
        btnCancelar.addActionListener(e -> dispose());

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        return panel;
    }

    private GridBagConstraints crearGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        return gbc;
    }

    private void addCampo(JPanel panel, GridBagConstraints gbc, int y, String label, JComponent campo) {
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    private void addCampo(JPanel panel, GridBagConstraints gbc, int y, JLabel label, JComponent campo) {
        gbc.gridx = 0; gbc.gridy = y;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(campo, gbc);
    }

    // RESTRICCIONES

    private void aplicarRestricciones() {

        if (!modoEdicion) {
            aplicarRestriccionesCreacion();
            return;
        }

        aplicarRestriccionesEdicion();
    }

    private void aplicarRestriccionesCreacion() {

        String rolEditor = usuarioEditor.getRol().getNombre();

        switch (rolEditor) {

            case "RRHH" -> eliminarRol("ADMIN");

            case "GERENTE_VENTAS" -> dejarSoloRol("VENDEDOR");

            case "VENDEDOR" -> {
                mostrarErrorYSalir("No tiene permisos para crear usuarios");
            }
        }
    }

    private void aplicarRestriccionesEdicion() {

        String rolEditor = usuarioEditor.getRol().getNombre();
        boolean esMismoUsuario = usuarioEditor.getIdUsuario() == usuario.getIdUsuario();

        switch (rolEditor) {

            case "VENDEDOR" -> {
                if (!esMismoUsuario) {
                    mostrarErrorYSalir("No puede editar otros usuarios");
                    return;
                }
                bloquearCamposSensibles();
            }

            case "RRHH" -> {
                if (usuario.getRol().getNombre().equals("ADMIN")) {
                    bloquearTodo();
                } else {
                    cbRol.setEnabled(false);
                }
            }

            case "GERENTE_VENTAS" -> {

                // Se edita a sí mismo
                if (esMismoUsuario) {
                    bloquearCamposSensibles();
                    return;
                }

                // No puede editar otros roles
                if (!usuario.getRol().getNombre().equals("VENDEDOR")) {
                    mostrarErrorYSalir("Solo puede editar vendedores");
                    return;
                }

                // Edita vendedor
                cbRol.setEnabled(false);
            }
        }
    }

    //  HELPERS UI 

    private void eliminarRol(String nombreRol) {
        for (int i = 0; i < cbRol.getItemCount(); i++) {
            if (cbRol.getItemAt(i).getNombre().equals(nombreRol)) {
                cbRol.removeItemAt(i);
                break;
            }
        }
    }

    private void dejarSoloRol(String nombreRol) {
        for (int i = cbRol.getItemCount() - 1; i >= 0; i--) {
            if (!cbRol.getItemAt(i).getNombre().equals(nombreRol)) {
                cbRol.removeItemAt(i);
            }
        }
    }

    private void bloquearCamposSensibles() {
        txtEmail.setEnabled(false);
        ocultarRolEstado();
    }

    private void bloquearTodo() {
        txtNombre.setEnabled(false);
        txtApellido.setEnabled(false);
        txtEmail.setEnabled(false);
        ocultarRolEstado();
    }

    private void ocultarRolEstado() {
        cbRol.setVisible(false);
        cmbEstado.setVisible(false);
        lblRol.setVisible(false);
        lblEstado.setVisible(false);
    }

    private void mostrarErrorYSalir(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Acceso denegado", JOptionPane.ERROR_MESSAGE);
        dispose();
    }

    // ACCIONES

    private void guardarUsuario() {

        if (txtNombre.getText().trim().isEmpty() ||
            txtApellido.getText().trim().isEmpty() ||
            txtEmail.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Campos obligatorios", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (modoEdicion) {

                usuario.setNombre(txtNombre.getText());
                usuario.setApellido(txtApellido.getText());
                usuario.setEmail(txtEmail.getText());

                if (cmbEstado.isVisible()) {
                    usuario.setEstado((EstadoUsuario) cmbEstado.getSelectedItem());
                }

                if (cbRol.isEnabled()) {
                    usuario.setRol((Rol) cbRol.getSelectedItem());
                }

                if (txtPassword.getPassword().length > 0) {
                    usuario.setPassword(new String(txtPassword.getPassword()));
                }

                UsuarioServicio.getInstancia().actualizarUsuario(usuario, usuarioEditor);

                JOptionPane.showMessageDialog(this, "Usuario actualizado");
            } else {

                Usuario nuevo = UsuarioServicio.getInstancia().guardar(
                        usuarioEditor,
                        txtNombre.getText(),
                        txtApellido.getText(),
                        txtEmail.getText(),
                        (Rol) cbRol.getSelectedItem(),
                        new String(txtPassword.getPassword())
                );

                JOptionPane.showMessageDialog(this, "Usuario creado:\n" + nuevo);
            }

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatos() {

        txtNombre.setText(usuario.getNombre());
        txtApellido.setText(usuario.getApellido());
        txtEmail.setText(usuario.getEmail());
        cmbEstado.setSelectedItem(usuario.getEstado());

        for (int i = 0; i < cbRol.getItemCount(); i++) {
            if (cbRol.getItemAt(i).getNombre().equals(usuario.getRol().getNombre())) {
                cbRol.setSelectedIndex(i);
                break;
            }
        }
    }
}