package com.sistema.ui.venta;
import com.sistema.config.Conexion;
import com.sistema.dao.ClienteDAO;
import com.sistema.dao.InventarioDAO;
import com.sistema.dao.ProductoVendedorDAO;
import com.sistema.modelo.Cliente;
import com.sistema.modelo.DetalleVenta;
import com.sistema.modelo.Producto;
import com.sistema.modelo.Usuario;
import com.sistema.modelo.Venta;
import com.sistema.servicio.VentaServicio;
import com.sistema.ui.table.DetalleVentaTableModel;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Connection;

public class VentaPanel extends JPanel {

    private JTextField txtCantidad;
    private JLabel lblTotal;
    private JComboBox<Producto> cbProducto;
    private JComboBox<Cliente> cbCliente;
    private JTable tabla;
    private DetalleVentaTableModel tableModel;
    private Usuario usuarioLogueado;

    public VentaPanel(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;

        setLayout(new BorderLayout(10, 10));
        add(crearPanelFormulario(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);

        cargarDatos();
    }

    private JPanel crearPanelFormulario() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cbProducto = new JComboBox<>();
        cbCliente = new JComboBox<>();
        txtCantidad = new JTextField(5);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.addActionListener(e -> agregarProducto());

        panel.add(new JLabel("Producto:"));
        panel.add(cbProducto);
        panel.add(new JLabel("Cantidad:"));
        panel.add(txtCantidad);
        panel.add(btnAgregar);
        panel.add(new JLabel("Cliente:"));
        panel.add(cbCliente);

        return panel;
    }

    private JScrollPane crearTabla() {
        tableModel = new DetalleVentaTableModel();
        tabla = new JTable(tableModel);
        return new JScrollPane(tabla);
    }

    private JPanel crearPanelInferior() {

        JPanel panel = new JPanel(new BorderLayout());

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarProducto());
        izquierda.add(btnEliminar);

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("Total: 0.00");

        JButton btnVender = new JButton("Vender");
        btnVender.addActionListener(e -> realizarVenta());

        derecha.add(lblTotal);
        derecha.add(btnVender);

        panel.add(izquierda, BorderLayout.WEST);
        panel.add(derecha, BorderLayout.EAST);

        return panel;
    }

    private void cargarDatos() {

        cbProducto.removeAllItems();
        cbCliente.removeAllItems();

        ProductoVendedorDAO pvDAO = new ProductoVendedorDAO();
        ClienteDAO clienteDAO = new ClienteDAO();

        int idVendedor = usuarioLogueado.getIdUsuario();

        // 🔥 PRODUCTOS CON STOCK REAL
        try (Connection conn = Conexion.getConnection()) {

            for (Producto p : pvDAO.listarPorVendedor(idVendedor)) {

                int stock = new InventarioDAO().obtenerStock(conn, p.getIdProducto());
                p.setStock(stock);

                cbProducto.addItem(p);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error cargando productos: " + e.getMessage());
        }

        // CLIENTES
        for (Cliente c : clienteDAO.listarPorVendedor(idVendedor)) {
            cbCliente.addItem(c);
        }
    }

    private void agregarProducto() {

        try {

            String textoCantidad = txtCantidad.getText().trim();

            if (textoCantidad.isEmpty()) {
                throw new IllegalArgumentException("Debe ingresar una cantidad");
            }

            int cantidad = Integer.parseInt(textoCantidad);

            if (cantidad <= 0) {
                throw new IllegalArgumentException("Cantidad inválida");
            }

            Producto p = (Producto) cbProducto.getSelectedItem();

            int stockDisponible = p.getStock();

            if (cantidad > stockDisponible) {
                throw new IllegalArgumentException(
                        "Stock insuficiente. Disponible: " + stockDisponible
                );
            }

            BigDecimal precio = p.getPrecio();
            BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidad));

            DetalleVenta d = new DetalleVenta();
            d.setIdProducto(p.getIdProducto());
            d.setCantidad(cantidad);
            d.setPrecio(precio);
            d.setSubtotal(subtotal);

            tableModel.agregarDetalle(d);

            actualizarTotal();
            txtCantidad.setText("");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarProducto() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }

        tableModel.eliminarDetalle(fila);
        actualizarTotal();
    }

    private void actualizarTotal() {
        lblTotal.setText("Total: " + tableModel.calcularTotal());
    }

    private void realizarVenta() {

        if (tableModel.getDetalles().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos");
            return;
        }

        try {

            Venta venta = new Venta();
            venta.setIdVendedor(usuarioLogueado.getIdUsuario());

            Cliente c = (Cliente) cbCliente.getSelectedItem();
            venta.setIdCliente(c.getIdCliente());

            venta.setDetalles(tableModel.getDetalles());

            VentaServicio servicio = new VentaServicio();
            servicio.registrarVenta(venta);

            JOptionPane.showMessageDialog(this, "Venta registrada");

            tableModel.limpiar();
            actualizarTotal();
            recargarVistaCompleta();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================
    // RECARGA UI
    // =========================

    private void recargarComboProductos() {

        cbProducto.removeAllItems();

        ProductoVendedorDAO pvDAO = new ProductoVendedorDAO();
        int idVendedor = usuarioLogueado.getIdUsuario();

        try (Connection conn = Conexion.getConnection()) {

            for (Producto p : pvDAO.listarPorVendedor(idVendedor)) {

                int stock = new InventarioDAO().obtenerStock(conn, p.getIdProducto());
                p.setStock(stock);

                cbProducto.addItem(p);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error recargando productos: " + e.getMessage());
        }
    }

    private void recargarVistaCompleta() {
        recargarComboProductos();
    }


}