package com.sistema.ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;
import com.sistema.modelo.Usuario;
import com.sistema.modelo.Producto;
import com.sistema.servicio.ProductoServicio;
import com.sistema.dao.InventarioDAO;
import com.sistema.config.Conexion;

public class InventarioPanel extends JPanel {

    private final Usuario gerente;
    private final ProductoServicio productoServicio = new ProductoServicio();
    private final InventarioDAO inventarioDAO = new InventarioDAO();
    private JComboBox<Producto> comboProducto;
    private JLabel lblStock;
    private JTextField txtCantidad;
    private JTable tabla;
    private DefaultTableModel modelo;

    public InventarioPanel(Usuario gerente) {
        this.gerente = gerente;
        setLayout(new BorderLayout());

        initTop();
        initTable();

        cargarProductos();
        refrescarTodo();
    }

    // PANEL SUPERIOR
    private void initTop() {

        JPanel panel = new JPanel(new FlowLayout());

        comboProducto = new JComboBox<>();
        lblStock = new JLabel("Stock actual: -");
        txtCantidad = new JTextField(5);

        JButton btnAgregar = new JButton("Agregar Stock");

        panel.add(new JLabel("Producto:"));
        panel.add(comboProducto);

        panel.add(lblStock);

        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);

        panel.add(btnAgregar);

        add(panel, BorderLayout.NORTH);

        // EVENTOS
        comboProducto.addActionListener(e -> actualizarStock());
        btnAgregar.addActionListener(e -> agregarStock());
    }

    // TABLA
    private void initTable() {

        modelo = new DefaultTableModel(new String[]{"Producto", "Stock"}, 0);
        
        tabla = new JTable(modelo) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    // CARGAS
    private void cargarProductos() {

        comboProducto.removeAllItems();

        List<Producto> productos = productoServicio.listarTodos();

        try (Connection conn = Conexion.getConnection()) {

            for (Producto p : productos) {

                int stock = inventarioDAO.obtenerStock(conn, p.getIdProducto());
                p.setStock(stock);

                comboProducto.addItem(p);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando productos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

// =========================
    // RECARGA UI
    // =========================

    private void recargarComboProductos() {

        comboProducto.removeAllItems();

        List<Producto> productos = productoServicio.listarTodos();

        try (Connection conn = Conexion.getConnection()) {

            for (Producto p : productos) {

                int stock = inventarioDAO.obtenerStock(conn, p.getIdProducto());
                p.setStock(stock);

                comboProducto.addItem(p);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error recargando combo: " + e.getMessage());
        }
    }

    private void recargarVistaCompleta() {
        recargarComboProductos();
        cargarInventario();
        actualizarStock();
    }

    private void cargarInventario() {

        modelo.setRowCount(0);

        List<Object[]> lista = inventarioDAO.listarConProducto();

        for (Object[] fila : lista) {
            modelo.addRow(new Object[]{
                    fila[1],
                    fila[2]
            });
        }
    }

    private void actualizarStock() {

        Producto p = (Producto) comboProducto.getSelectedItem();
        if (p == null) return;

        try (Connection conn = Conexion.getConnection()) {

            int stock = inventarioDAO.obtenerStock(conn, p.getIdProducto());
            lblStock.setText("Stock actual: " + stock);

        } catch (Exception e) {
            lblStock.setText("Stock actual: 0");
        }
    }

    

    private void refrescarInventario() {
        cargarInventario();
    }

    private void agregarStock() {

        try {

            Producto producto = (Producto) comboProducto.getSelectedItem();

            if (producto == null) {
                JOptionPane.showMessageDialog(this, "Seleccione producto");
                return;
            }

            int cantidad = Integer.parseInt(txtCantidad.getText());

            if (cantidad <= 0) {
                throw new IllegalArgumentException("Cantidad inválida");
            }

            try (Connection conn = Conexion.getConnection()) {

                conn.setAutoCommit(false);

                inventarioDAO.crearSiNoExiste(conn, producto.getIdProducto());
                inventarioDAO.sumarStock(conn, producto.getIdProducto(), cantidad);

                conn.commit();
            }

            JOptionPane.showMessageDialog(this, "Stock agregado correctamente");

            txtCantidad.setText("");
            recargarVistaCompleta();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void refrescarTodo() {
        recargarVistaCompleta();
    }
}