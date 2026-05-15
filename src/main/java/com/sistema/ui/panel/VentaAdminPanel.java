package com.sistema.ui.panel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.VentaServicio;
import com.sistema.servicio.UsuarioServicio;

public class VentaAdminPanel extends JPanel {

    private final Usuario admin;
    private final VentaServicio ventaServicio = new VentaServicio();
    private final UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();
    private JComboBox<Usuario> comboVendedores;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentaAdminPanel(Usuario admin) {
        this.admin = admin;

        setLayout(new BorderLayout());

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);

        cargarVendedores();
    }

    // PANEL SUPERIOR
    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        comboVendedores = new JComboBox<>();

        JButton btnCargar = new JButton("Ver Ventas");
        JButton btnRefrescar = new JButton("Refrescar");

        btnCargar.addActionListener(e -> cargarVentas());
        btnRefrescar.addActionListener(e -> cargarVentas());

        panel.add(new JLabel("Vendedor:"));
        panel.add(comboVendedores);
        panel.add(btnCargar);
        panel.add(btnRefrescar);

        return panel;
    }

    // TABLA
    private JScrollPane crearTabla() {

        modelo = new DefaultTableModel(
                new Object[]{"ID Venta", "Cliente", "Total", "Fecha", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 🔒 tabla solo lectura
            }
        };

        tabla = new JTable(modelo);

        return new JScrollPane(tabla);
    }

    // CARGA DE VENDEDORES
    private void cargarVendedores() {

        comboVendedores.removeAllItems();

        List<Usuario> vendedores = usuarioServicio.listarSegunRol(admin);

        for (Usuario u : vendedores) {
            if ("VENDEDOR".equalsIgnoreCase(u.getRol().getNombre())) {
                comboVendedores.addItem(u);
            }
        }
    }

    // CARGAR VENTAS
    private void cargarVentas() {

        modelo.setRowCount(0);

        Usuario vendedor = (Usuario) comboVendedores.getSelectedItem();

        if (vendedor == null) return;

        List<Object[]> ventas = ventaServicio.listarVentasPorVendedor(vendedor.getIdUsuario());

        for (Object[] v : ventas) {
            modelo.addRow(v);
        }
    }

    private void anularVenta() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            mostrarMensaje("Seleccione una venta");
            return;
        }

        String estado = (String) modelo.getValueAt(fila, 4);

        // BLOQUEO UI
        if (estado != null && estado.trim().equalsIgnoreCase("ANULADA")) {
            mostrarMensaje("La venta ya está anulada (solo auditoría)");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Desea solicitar anulación de esta venta?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int idVenta = (int) modelo.getValueAt(fila, 0);

            ventaServicio.solicitarAnulacionVenta(idVenta, admin);

            mostrarMensaje("Solicitud enviada correctamente");

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // UTILIDADES UI
    private void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}