package com.sistema.ui.panel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.VentaServicio;
import com.sistema.servicio.UsuarioServicio;

public class VentaGerentePanel extends JPanel {

    private final Usuario gerente;
    private final VentaServicio ventaServicio = new VentaServicio();
    private final UsuarioServicio usuarioServicio = UsuarioServicio.getInstancia();
    private JComboBox<Usuario> comboVendedores;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VentaGerentePanel(Usuario gerente) {
        this.gerente = gerente;

        setLayout(new BorderLayout());

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);

        cargarVendedores();
    }

    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        comboVendedores = new JComboBox<>();

        JButton btnCargar = new JButton("Ver Ventas");
        JButton btnRefrescar = new JButton("Refrescar");
        JButton btnSolicitar = new JButton("Solicitar Anulación");

        btnCargar.addActionListener(e -> cargarVentas());
        btnRefrescar.addActionListener(e -> cargarVentas());
        btnSolicitar.addActionListener(e -> solicitarAnulacion());

        panel.add(new JLabel("Vendedor:"));
        panel.add(comboVendedores);
        panel.add(btnCargar);
        panel.add(btnRefrescar);
        panel.add(btnSolicitar);

        return panel;
    }

    private JScrollPane crearTabla() {

        modelo = new DefaultTableModel(
                new Object[]{
                        "ID Venta", "Cliente", "Total", "Fecha",
                        "Estado Venta", "Estado Solicitud"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);

        // RESTRICCIÓN DE SELECCIÓN
        tabla.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                int fila = tabla.getSelectedRow();

                if (fila != -1) {

                    String estadoSolicitud = (String) modelo.getValueAt(fila, 5);

                    if (!"SIN SOLICITUD".equalsIgnoreCase(estadoSolicitud)) {

                        tabla.clearSelection();

                        JOptionPane.showMessageDialog(
                                this,
                                "Solo puede seleccionar ventas sin solicitud",
                                "Aviso",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            }
        });

        return new JScrollPane(tabla);
    }

    private void cargarVendedores() {

        comboVendedores.removeAllItems();

        List<Usuario> vendedores = usuarioServicio.listarSegunRol(gerente);

        for (Usuario u : vendedores) {
            if ("VENDEDOR".equalsIgnoreCase(u.getRol().getNombre())) {
                comboVendedores.addItem(u);
            }
        }
    }

    private void cargarVentas() {

        modelo.setRowCount(0);

        Usuario vendedor = (Usuario) comboVendedores.getSelectedItem();

        if (vendedor == null) return;

        List<Object[]> ventas = ventaServicio.listarVentasPorVendedor(vendedor.getIdUsuario());

        for (Object[] v : ventas) {
            modelo.addRow(v); 
            // IMPORTANTE:
            // v debe traer:
            // [id, cliente, total, fecha, estadoVenta, estadoSolicitud]
        }
    }

    private void solicitarAnulacion() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            mostrarMensaje("Seleccione una venta");
            return;
        }

        String estadoVenta = (String) modelo.getValueAt(fila, 4);
        String estadoSolicitud = (String) modelo.getValueAt(fila, 5);

        if ("ANULADA".equalsIgnoreCase(estadoVenta)) {
            mostrarMensaje("La venta ya está anulada");
            return;
        }

        if ("PENDIENTE".equalsIgnoreCase(estadoSolicitud)) {
            mostrarMensaje("Ya existe una solicitud pendiente");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Desea solicitar la anulación de esta venta?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int idVenta = (int) modelo.getValueAt(fila, 0);

            ventaServicio.solicitarAnulacionVenta(idVenta, gerente);

            mostrarMensaje("Solicitud enviada al ADMIN");

            cargarVentas(); // refresca automáticamente

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