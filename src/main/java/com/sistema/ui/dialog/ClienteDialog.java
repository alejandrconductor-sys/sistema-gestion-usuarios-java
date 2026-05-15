package com.sistema.ui.dialog;

import com.sistema.modelo.Cliente;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.ClienteServicio;

import javax.swing.*;
import java.awt.*;

public class ClienteDialog extends JDialog {

    private JTextField txtNombre, txtDocumento, txtTelefono, txtEmail, txtDireccion;
    private final ClienteServicio servicio = new ClienteServicio();
    private final Usuario usuario;

    public ClienteDialog(Frame owner, Usuario usuario) {
        super(owner, "Nuevo Cliente", true);
        this.usuario = usuario;

        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        txtNombre = crearCampo();
        txtDocumento = crearCampo();
        txtTelefono = crearCampo();
        txtEmail = crearCampo();
        txtDireccion = crearCampo();

        agregarCampo(panel, gbc, 0, "Nombre:", txtNombre);
        agregarCampo(panel, gbc, 1, "Documento:", txtDocumento);
        agregarCampo(panel, gbc, 2, "Teléfono:", txtTelefono);
        agregarCampo(panel, gbc, 3, "Email:", txtEmail);
        agregarCampo(panel, gbc, 4, "Dirección:", txtDireccion);

        add(panel, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(120, 35));
        btnGuardar.addActionListener(e -> guardar());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnGuardar);

        add(panelBoton, BorderLayout.SOUTH);

        setSize(450, 350);
        setLocationRelativeTo(owner);
    }

    private JTextField crearCampo() {
        JTextField txt = new JTextField();
        txt.setPreferredSize(new Dimension(200, 30));
        return txt;
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int y, String label, JTextField campo) {

        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(campo, gbc);
    }

    private void guardar() {

        Cliente c = new Cliente();
        c.setNombre(txtNombre.getText());
        c.setDocumento(txtDocumento.getText());
        c.setTelefono(txtTelefono.getText());
        c.setEmail(txtEmail.getText());
        c.setDireccion(txtDireccion.getText());

        try {
            servicio.crearCliente(c, usuario.getIdUsuario());
            JOptionPane.showMessageDialog(this, "Cliente creado");
            dispose();
        } catch (Exception e) {
            if ("DOCUMENTO_DUPLICADO".equals(e.getMessage())) {
                JOptionPane.showMessageDialog(this, "Documento ya existe");
            } else {
                JOptionPane.showMessageDialog(this, "Error");
            }
        }
    }
}