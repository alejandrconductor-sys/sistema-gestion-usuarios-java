package com.sistema.servicio;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import com.sistema.config.Conexion;
import com.sistema.dao.*;
import com.sistema.modelo.*;
import com.sistema.seguridad.AutorizacionService;

public class VentaServicio {

    private final VentaDAO ventaDAO = new VentaDAO();
    private final DetalleVentaDAO detalleDAO = new DetalleVentaDAO();
    private final InventarioDAO inventarioDAO = new InventarioDAO();
    private final ClienteVendedorDAO clienteVendedorDAO = new ClienteVendedorDAO();
    private final ProductoVendedorDAO productoVendedorDAO = new ProductoVendedorDAO();
    private final SolicitudCambioDAO solicitudDAO = new SolicitudCambioDAO();
    private final AutorizacionService auth = new AutorizacionService();
    private final AuditoriaServicio auditoria = new AuditoriaServicio();

    // VALIDACIÓN CENTRAL
    private void validarVentaEditable(Venta venta) {
        if (venta == null) throw new RuntimeException("Venta no encontrada");

        if (VentaDAO.ESTADO_ANULADA.equalsIgnoreCase(venta.getEstado())) {
            throw new RuntimeException("Venta ya anulada (solo auditoría)");
        }
    }

    public void registrarVenta(Venta venta) {

        validarDatosVenta(venta);

        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);

            validarRelaciones(conn, venta);

            BigDecimal total = calcularTotal(venta);
            venta.setTotal(total);

            int idVenta = ventaDAO.guardar(conn, venta);

            procesarDetalles(conn, venta, idVenta);

            auditoria.registrar(conn,
                    venta.getIdVendedor(),
                    "CREAR_VENTA",
                    "VENTA",
                    idVenta,
                    "Venta registrada: " + total);

            conn.commit();

        } catch (Exception e) {
            rollback(conn);
            throw new RuntimeException("Error al registrar venta", e);
        } finally {
            cerrar(conn);
        }
    }

    // GERENTE
    public void solicitarAnulacionVenta(int idVenta, Usuario gerente) {

        if (!auth.esGerente(gerente)) {
            throw new RuntimeException("Solo gerente puede solicitar");
        }

        int idGerente = gerente.getIdUsuario();

        try (Connection conn = Conexion.getConnection()) {

            Venta venta = ventaDAO.obtenerPorId(conn, idVenta);

            validarVentaEditable(venta);

            if (solicitudDAO.existeSolicitudPendiente(conn, "VENTA", idVenta)) {
                throw new RuntimeException("Ya existe solicitud pendiente");
            }

            solicitudDAO.crearSolicitud(
                    conn,
                    idGerente,
                    "VENTA",
                    "VENTA",
                    "ANULAR",
                    idVenta,
                    null,
                    null
            );

            auditoria.registrar(conn,
                    idGerente,
                    "SOLICITAR_ANULACION",
                    "VENTA",
                    idVenta,
                    "Solicitud creada");

        } catch (Exception e) {
            throw new RuntimeException("Error al solicitar anulación", e);
        }
    }

    // 🔧 MÉTODOS PRIVADOS

    private void validarDatosVenta(Venta venta) {
        if (venta == null) throw new IllegalArgumentException("Venta null");
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty())
            throw new IllegalArgumentException("Venta sin productos");
    }

    private void validarRelaciones(Connection conn, Venta venta) {

        if (!clienteVendedorDAO.existeRelacion(conn, venta.getIdCliente(), venta.getIdVendedor())) {
            throw new RuntimeException("Cliente no asignado");
        }
    }

    private BigDecimal calcularTotal(Venta venta) {

        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVenta d : venta.getDetalles()) {

            if (d.getCantidad() <= 0) {
                throw new RuntimeException("Cantidad inválida");
            }

            total = total.add(d.getSubtotal());
        }

        return total;
    }

    private void procesarDetalles(Connection conn, Venta venta, int idVenta) {

        for (DetalleVenta d : venta.getDetalles()) {

            if (!productoVendedorDAO.existeRelacion(conn, d.getIdProducto(), venta.getIdVendedor())) {
                throw new RuntimeException("Producto no asignado");
            }

            int stock = inventarioDAO.obtenerStock(conn, d.getIdProducto());

            if (stock < d.getCantidad()) {
                throw new RuntimeException("Stock insuficiente");
            }

            d.setIdVenta(idVenta);
            detalleDAO.guardar(conn, d);

            inventarioDAO.actualizarStock(
                    conn,
                    d.getIdProducto(),
                    d.getCantidad()
            );
        }
    }

    private void devolverStock(Connection conn, int idVenta, int idVendedor) {

        List<DetalleVenta> detalles = detalleDAO.listarPorVenta(conn, idVenta);

        for (DetalleVenta d : detalles) {
            inventarioDAO.sumarStock(
                    conn,
                    d.getIdProducto(),
                    d.getCantidad()
            );
        }
    }

    private void rollback(Connection conn) {
        try { if (conn != null) conn.rollback(); } catch (Exception ignored) {}
    }

    private void cerrar(Connection conn) {
        try { if (conn != null) conn.close(); } catch (Exception ignored) {}
    }

    // 🔹 LISTADO
    public List<Object[]> listarVentasPorVendedor(int idVendedor) {
        return ventaDAO.listarPorVendedor(idVendedor);
    }
}