package com.sistema.ui.dialog;

import com.sistema.modelo.Producto;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.ProductoServicio;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class ProductoDialog extends JDialog {

    private JTextField txtNombre, txtDescripcion, txtPrecio;
    private final ProductoServicio servicio = new ProductoServicio();
    private final Usuario usuario;

    public ProductoDialog(Frame owner, Usuario usuario) {
        super(owner, "Nuevo Producto", true);
        this.usuario = usuario;

        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 8, 8, 8);

        txtNombre = crearCampo();
        txtDescripcion = crearCampo();
        txtPrecio = crearCampo();

        agregarCampo(panel, gbc, 0, "Nombre:", txtNombre);
        agregarCampo(panel, gbc, 1, "Descripción:", txtDescripcion);
        agregarCampo(panel, gbc, 2, "Precio:", txtPrecio);

        add(panel, BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setPreferredSize(new Dimension(120, 35));
        btnGuardar.addActionListener(e -> guardar());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnGuardar);

        add(panelBoton, BorderLayout.SOUTH);

        setSize(400, 250);
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

        try {
            Producto p = new Producto();
            p.setNombre(txtNombre.getText());
            p.setDescripcion(txtDescripcion.getText());
            p.setPrecio(new BigDecimal(txtPrecio.getText()));

            servicio.crearProducto(p, usuario.getIdUsuario());

            JOptionPane.showMessageDialog(this, "Producto creado");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Precio inválido");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error");
        }
    }
}