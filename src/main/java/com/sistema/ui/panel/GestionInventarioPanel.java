package com.sistema.ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;
import com.sistema.modelo.Usuario;
import com.sistema.modelo.Producto;
import com.sistema.dao.ProductoDAO;
import com.sistema.dao.InventarioDAO;
import com.sistema.config.Conexion;
import com.sistema.servicio.SolicitudCambioServicio;

public class GestionInventarioPanel extends JPanel {

    private final Usuario gerente;

    private JComboBox<Producto> comboProducto;
    private JLabel lblStock;
    private JTable tabla;
    private DefaultTableModel modelo;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final InventarioDAO inventarioDAO = new InventarioDAO();
    private final SolicitudCambioServicio solicitudCambioServicio = new SolicitudCambioServicio();

    public GestionInventarioPanel(Usuario gerente) {

        this.gerente = gerente;

        setLayout(new BorderLayout());

        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);

        cargarProductos();
    }

    // 🔹 PANEL SUPERIOR
    private JPanel crearPanelSuperior() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        comboProducto = new JComboBox<>();
        lblStock = new JLabel("Stock actual: -");

        JButton btnVer = new JButton("Ver Stock");
        JButton btnSolicitar = new JButton("Solicitar Reducción");

        btnVer.addActionListener(e -> actualizarStock());
        btnSolicitar.addActionListener(e -> solicitarReduccion());

        panel.add(new JLabel("Producto:"));
        panel.add(comboProducto);
        panel.add(btnVer);
        panel.add(lblStock);
        panel.add(btnSolicitar);

        return panel;
    }

    // 🔹 TABLA
    private JScrollPane crearTabla() {

        modelo = new DefaultTableModel(
                new Object[]{"Producto", "Stock Actual"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);

        return new JScrollPane(tabla);
    }

    // 🔹 CARGAS
    private void cargarProductos() {

        comboProducto.removeAllItems();

        List<Producto> productos = productoDAO.listarTodos();

        for (Producto p : productos) {

            int stock = obtenerStockActual(p.getIdProducto());
            p.setStock(stock); // 🔥 AQUÍ ESTÁ LA CLAVE

            comboProducto.addItem(p);
        }
    }

    private void recargarComboProductos() {

        comboProducto.removeAllItems();

        List<Producto> productos = productoDAO.listarTodos();

        for (Producto p : productos) {

            int stock = obtenerStockActual(p.getIdProducto());
            p.setStock(stock);

            comboProducto.addItem(p);
        }
    }

    private void recargarVistaCompleta() {
        recargarComboProductos();
        actualizarStock();
    }

    private int obtenerStockActual(int idProducto) {
        try (Connection conn = Conexion.getConnection()) {
            return inventarioDAO.obtenerStock(conn, idProducto);
        } catch (Exception e) {
            return 0;
        }
    }

    private void actualizarStock() {

        modelo.setRowCount(0);

        Producto p = (Producto) comboProducto.getSelectedItem();
        if (p == null) return;

        int stock = obtenerStockActual(p.getIdProducto());

        lblStock.setText("Stock actual: " + stock);

        modelo.addRow(new Object[]{
                p.getNombre(),
                stock
        });
    }

    // 🔥 LÓGICA CLAVE
    private void solicitarReduccion() {

        Producto p = (Producto) comboProducto.getSelectedItem();

        if (p == null) {
            mostrarMensaje("Seleccione producto");
            return;
        }

        int stockActual = obtenerStockActual(p.getIdProducto());

        if (stockActual <= 0) {
            mostrarMensaje("No hay stock disponible");
            return;
        }

        String input = JOptionPane.showInputDialog(
                this,
                "Stock disponible: " + stockActual + "\nIngrese cantidad a reducir:",
                "Solicitud de Reducción",
                JOptionPane.PLAIN_MESSAGE
        );

        if (input == null) return;

        try {

            int cantidad = Integer.parseInt(input);

            if (cantidad <= 0) {
                throw new IllegalArgumentException("Cantidad inválida");
            }

            if (cantidad > stockActual) {
                throw new IllegalArgumentException(
                        "No puedes reducir más de lo disponible (" + stockActual + ")"
                );
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Solicitar reducción de " + cantidad + " unidades?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) return;

            // ✅ LLAMADA CORRECTA AL SERVICIO
            solicitudCambioServicio.crearSolicitud(
                    gerente.getIdUsuario(),
                    "INVENTARIO",
                    "PRODUCTO",
                    "REDUCIR",
                    p.getIdProducto(),
                    cantidad,
                    null
            );

            mostrarMensaje("Solicitud enviada al ADMIN");

            // 🔄 refrescar UI
            recargarVistaCompleta();

        } catch (NumberFormatException e) {
            mostrarError("Cantidad inválida");
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // 🔹 UTILIDADES
    private void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}