package com.sistema.ui.dialog;
import javax.swing.*;
import java.awt.*;
import com.sistema.modelo.Rol;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;

public class UsuarioDialog extends JDialog {


    // CONSTRUCTOR NUEVO
    public UsuarioDialog(Window parent) {
        super(parent, "Nuevo Usuario", ModalityType.APPLICATION_MODAL);
        configurarVentana();
        inicializarComponentes();
    }

    // CONSTRUCTOR EDITAR
    public UsuarioDialog(Window parent, Usuario usuario) {
        super(parent, "Editar Usuario", ModalityType.APPLICATION_MODAL);
        this.usuario = usuario;
        this.modoEdicion = true;

        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setSize(450, 350);
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
        panel.add(new JLabel("Estado:"), gbc);

        gbc.gridx = 1;
        cmbEstado = new JComboBox<>(new String[]{"Activo","Inactivo"});
        panel.add(cmbEstado, gbc);

        // Rol
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Rol:"), gbc);

        gbc.gridx = 1;
        cbRol = new JComboBox<>();
        cbRol.addItem(new Rol(1,"ADMIN"));
        cbRol.addItem(new Rol(2,"RRHH"));
        cbRol.addItem(new Rol(3,"USUARIO"));
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

                // NO crear nuevo objeto
                usuario.setNombre(txtNombre.getText());
                usuario.setApellido(txtApellido.getText());
                usuario.setEmail(txtEmail.getText());
                usuario.setEstado(cmbEstado.getSelectedItem().toString());
                usuario.setRol((Rol) cbRol.getSelectedItem());

                UsuarioServicio.getInstancia().actualizarUsuario(usuario);

                JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");

            } else {

                Usuario nuevo = UsuarioServicio
                        .getInstancia()
                        .guardar(
                                txtNombre.getText(),
                                txtApellido.getText(),
                                txtEmail.getText(),
                                cmbEstado.getSelectedItem().toString(),
                                (Rol) cbRol.getSelectedItem(),
                                new String(txtPassword.getPassword())
                        );

                JOptionPane.showMessageDialog(this, "Usuario guardado correctamente:\n" + nuevo);
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
        cbRol.setSelectedItem(usuario.getRol());
    }

    private boolean modoEdicion = false;
    private Usuario usuario;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JComboBox<String> cmbEstado;
    private JComboBox<Rol> cbRol;
    private JPasswordField txtPassword;

}