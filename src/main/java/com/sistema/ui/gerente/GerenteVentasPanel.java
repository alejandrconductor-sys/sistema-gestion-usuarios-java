package com.sistema.ui.gerente;

import com.sistema.modelo.*;
import com.sistema.servicio.*;
import com.sistema.ui.dialog.ClienteDialog;
import com.sistema.ui.dialog.ProductoDialog;
import com.sistema.dao.InventarioDAO;
import com.sistema.dao.ProductoVendedorDAO;
import com.sistema.dao.UsuarioDAO;
import com.sistema.config.Conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class GerenteVentasPanel extends JPanel {

    private final Usuario usuarioLogueado;

    private final ClienteServicio clienteServicio = new ClienteServicio();
    private final ProductoServicio productoServicio = new ProductoServicio();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ProductoVendedorDAO productoVendedorDAO = new ProductoVendedorDAO();

    // LISTAS
    private JList<Usuario> listaVendedores;
    private JList<Cliente> listaClientes;
    private JList<Producto> listaProductos;
    private final InventarioDAO inventarioDAO = new InventarioDAO();
    private DefaultListModel<Usuario> modeloVendedores = new DefaultListModel<>();
    private DefaultListModel<Cliente> modeloClientes = new DefaultListModel<>();
    private DefaultListModel<Producto> modeloProductos = new DefaultListModel<>();

    // TABLAS
    private JTable tablaClientes;
    private JTable tablaProductos;

    private DefaultTableModel modeloTablaClientes;
    private DefaultTableModel modeloTablaProductos;

    public GerenteVentasPanel(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;

        setLayout(new BorderLayout(10, 10));

        add(new JLabel("Gestión Comercial - Clientes y Productos por Vendedor", SwingConstants.CENTER), BorderLayout.NORTH);
        add(crearSplitPrincipal(), BorderLayout.CENTER);

        recargarVistaCompleta();
    }

    // =========================
    // UI
    // =========================

    private JSplitPane crearSplitPrincipal() {

        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                crearPanelCentral(),
                crearPanelDetalle()
        );

        split.setDividerLocation(250);
        split.setResizeWeight(0.5);

        return split;
    }

    private JPanel crearPanelCentral() {

        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));

        listaVendedores = new JList<>(modeloVendedores);
        listaClientes = new JList<>(modeloClientes);
        listaProductos = new JList<>(modeloProductos);

        listaVendedores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listaVendedores.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDetalleVendedor();
                cargarProductosPorVendedor();
            }
        });

        panel.add(crearPanel("Vendedores", listaVendedores));
        panel.add(crearPanel("Clientes Disponibles", listaClientes));
        panel.add(crearPanel("Productos Disponibles", listaProductos));

        return panel;
    }

    private JPanel crearPanelDetalle() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Asignaciones del Vendedor"));

        modeloTablaClientes = new DefaultTableModel(new String[]{"ID", "Cliente"}, 0);
        tablaClientes = new JTable(modeloTablaClientes);

        modeloTablaProductos = new DefaultTableModel(new String[]{"ID", "Producto"}, 0);
        tablaProductos = new JTable(modeloTablaProductos);

        JPanel tablas = new JPanel(new GridLayout(1, 2, 10, 10));
        tablas.add(new JScrollPane(tablaClientes));
        tablas.add(new JScrollPane(tablaProductos));

        panel.add(tablas, BorderLayout.CENTER);
        panel.add(crearPanelBotones(), BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelBotones() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton btnAsignarCliente = new JButton("Asignar Cliente");
        JButton btnQuitarCliente = new JButton("Quitar Cliente");

        JButton btnAsignarProducto = new JButton("Asignar Producto");
        JButton btnQuitarProducto = new JButton("Quitar Producto");

        btnAsignarCliente.addActionListener(e -> asignarCliente());
        btnQuitarCliente.addActionListener(e -> quitarCliente());

        btnAsignarProducto.addActionListener(e -> asignarProducto());
        btnQuitarProducto.addActionListener(e -> quitarProducto());

        JButton btnNuevoCliente = new JButton("Nuevo Cliente");
        JButton btnNuevoProducto = new JButton("Nuevo Producto");

        btnNuevoCliente.addActionListener(e -> {
            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
            new ClienteDialog(frame, usuarioLogueado).setVisible(true);

            recargarVistaCompleta();
        });

        btnNuevoProducto.addActionListener(e -> {
            Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
            new ProductoDialog(frame, usuarioLogueado).setVisible(true);

            recargarVistaCompleta();
        });

        panel.add(btnNuevoCliente);
        panel.add(btnAsignarCliente);
        panel.add(btnQuitarCliente);
        panel.add(btnNuevoProducto);
        panel.add(btnAsignarProducto);
        panel.add(btnQuitarProducto);

        return panel;
    }

    private JPanel crearPanel(String titulo, JComponent comp) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(titulo));
        panel.add(new JScrollPane(comp), BorderLayout.CENTER);
        return panel;
    }

    // =========================
    // CARGA DATOS
    // =========================

    private void cargarDatos() {

        modeloVendedores.clear();
        modeloClientes.clear();
        modeloProductos.clear();

        usuarioDAO.listarVendedores().forEach(modeloVendedores::addElement);
        clienteServicio.listarTodos().forEach(modeloClientes::addElement);

        List<Producto> productos = productoServicio.listarTodos();

        try (Connection conn = Conexion.getConnection()) {

            for (Producto p : productos) {

                int stock = inventarioDAO.obtenerStock(conn, p.getIdProducto());
                p.setStock(stock); // 🔥 AQUÍ ESTÁ LA CLAVE REAL

                modeloProductos.addElement(p); // usa el toString()
            }

        } catch (Exception e) {
            mostrarError("Error cargando productos: " + e.getMessage());
        }
    }

    // =========================
    // RECARGA UI
    // =========================

    /**
     * Recarga únicamente la lista de productos disponible.
     * Útil cuando cambia stock y no quieres recargar todo.
     */
    private void recargarListaProductos() {

        modeloProductos.clear();

        List<Producto> productos = productoServicio.listarTodos();

        try (Connection conn = Conexion.getConnection()) {

            for (Producto p : productos) {

                int stock = inventarioDAO.obtenerStock(conn, p.getIdProducto());
                p.setStock(stock);

                modeloProductos.addElement(p);
            }

        } catch (Exception e) {
            mostrarError("Error actualizando lista productos: " + e.getMessage());
        }
    }

    /**
     * Recarga toda la vista del panel.
     * Recomendado después de operaciones importantes.
     */
    private void recargarVistaCompleta() {
        cargarDatos();
        cargarDetalleVendedor();
        cargarProductosPorVendedor();
    }

    private void cargarDetalleVendedor() {

        Usuario vendedor = listaVendedores.getSelectedValue();
        if (vendedor == null) return;

        modeloTablaClientes.setRowCount(0);

        List<Cliente> clientes = clienteServicio.listarPorVendedor(vendedor.getIdUsuario());

        clientes.forEach(c ->
                modeloTablaClientes.addRow(new Object[]{c.getIdCliente(), c.getNombre()})
        );
    }

    private void cargarProductosPorVendedor() {

        modeloTablaProductos.setRowCount(0);

        Usuario vendedor = listaVendedores.getSelectedValue();
        if (vendedor == null) return;

        List<Producto> productos =
                productoVendedorDAO.listarPorVendedor(vendedor.getIdUsuario());

        try (Connection conn = Conexion.getConnection()) {

            for (Producto p : productos) {

                int stock = inventarioDAO.obtenerStock(conn, p.getIdProducto());
                p.setStock(stock);

                modeloTablaProductos.addRow(new Object[]{
                        p.getIdProducto(),
                        p.getNombre() + " (Stock: " + p.getStock() + ")"
                });
            }

        } catch (Exception e) {  // 🔥 ESTA LÍNEA FALTABA
            mostrarError("Error cargando productos: " + e.getMessage());
        }
    }

    // =========================
    // ACCIONES CLIENTES
    // =========================

    private void asignarCliente() {

        Usuario vendedor = listaVendedores.getSelectedValue();
        Cliente cliente = listaClientes.getSelectedValue();

        if (vendedor == null || cliente == null) {
            mostrarMensaje("Seleccione vendedor y cliente");
            return;
        }

        try {
            clienteServicio.asignarClienteAVendedor(
                    cliente.getIdCliente(),
                    vendedor.getIdUsuario()
            );

            mostrarMensaje("Cliente asignado correctamente");
            cargarDetalleVendedor();

        } catch (Exception ex) {
            mostrarError("Error al asignar cliente");
        }
    }

    private void quitarCliente() {

        int fila = tablaClientes.getSelectedRow();
        Usuario vendedor = listaVendedores.getSelectedValue();

        if (fila == -1 || vendedor == null) {
            mostrarMensaje("Seleccione cliente");
            return;
        }

        int idCliente = (int) modeloTablaClientes.getValueAt(fila, 0);

        clienteServicio.quitarClienteDeVendedor(idCliente, vendedor.getIdUsuario());

        mostrarMensaje("Cliente quitado correctamente");
        cargarDetalleVendedor();
    }

    // =========================
    // ACCIONES PRODUCTOS
    // =========================

    private void asignarProducto() {

        Usuario vendedor = listaVendedores.getSelectedValue();
        Producto producto = listaProductos.getSelectedValue();

        if (vendedor == null || producto == null) {
            mostrarMensaje("Seleccione vendedor y producto");
            return;
        }

        try (Connection conn = Conexion.getConnection()) {

            productoVendedorDAO.asignar(
                    conn,
                    producto.getIdProducto(),
                    vendedor.getIdUsuario()
            );

            mostrarMensaje("Producto asignado correctamente");

            // 🔥 refresca lista + tabla + stock
            recargarVistaCompleta();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

private void quitarProducto() {

        int fila = tablaProductos.getSelectedRow();
        Usuario vendedor = listaVendedores.getSelectedValue();

        if (fila == -1 || vendedor == null) {
            mostrarMensaje("Seleccione producto");
            return;
        }

        int idProducto = (int) modeloTablaProductos.getValueAt(fila, 0);

        try (Connection conn = Conexion.getConnection()) {

            productoVendedorDAO.eliminar(
                    conn,
                    idProducto,
                    vendedor.getIdUsuario()
            );

            mostrarMensaje("Producto quitado correctamente");
            recargarVistaCompleta();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    // =========================
    // MENSAJES
    // =========================

    private void mostrarMensaje(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}