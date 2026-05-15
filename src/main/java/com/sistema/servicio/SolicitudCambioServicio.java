package com.sistema.servicio;

import com.sistema.config.Conexion;
import com.sistema.dao.*;
import com.sistema.modelo.*;
import com.sistema.seguridad.AutorizacionService;
import java.sql.Connection;
import java.util.List;

public class SolicitudCambioServicio {

    private final SolicitudCambioDAO solicitudDAO = new SolicitudCambioDAO();
    private final VentaDAO ventaDAO = new VentaDAO();
    private final DetalleVentaDAO detalleDAO = new DetalleVentaDAO();
    private final InventarioDAO inventarioDAO = new InventarioDAO();
    private final AutorizacionService autorizacionService = new AutorizacionService();
    private final AuditoriaServicio auditoriaServicio = new AuditoriaServicio();

    // ===============================
    // 📌 LISTAR
    // ===============================
    public List<SolicitudCambio> listarPendientes() {
        return solicitudDAO.listarPorEstado("PENDIENTE");
    }

    // ===============================
    // ✅ APROBAR
    // ===============================
    public void aprobarSolicitud(int idSolicitud, Usuario admin) {

        validarAdmin(admin);

        Connection conn = null;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);

            SolicitudCambio s = solicitudDAO.obtenerPorId(idSolicitud);

            validarPendiente(s);

            String modulo = s.getModulo();

            switch (modulo) {

                case "VENTA" -> procesarAnulacionVenta(conn, s, admin);

                case "INVENTARIO" -> procesarAjusteInventario(conn, s);

                default -> throw new RuntimeException("Módulo no soportado: " + modulo);
            }

            solicitudDAO.aprobar(conn, idSolicitud);

            auditoriaServicio.registrar(
                    conn,
                    admin.getIdUsuario(),
                    "APROBAR_SOLICITUD",
                    modulo,
                    s.getReferenciaId(),
                    "Solicitud aprobada ID=" + idSolicitud
            );

            conn.commit();

        } catch (Exception e) {

            try {
                if (conn != null) conn.rollback();
            } catch (Exception ignored) {}

            throw new RuntimeException("Error al aprobar solicitud", e);

        } finally {

            try {
                if (conn != null) conn.close();
            } catch (Exception ignored) {}
        }
    }

    // ===============================
    // ❌ RECHAZAR
    // ===============================
    public void rechazarSolicitud(int idSolicitud, Usuario admin) {

        validarAdmin(admin);

        try (Connection conn = Conexion.getConnection()) {

            conn.setAutoCommit(false);

            SolicitudCambio s = solicitudDAO.obtenerPorId(idSolicitud);

            validarPendiente(s);

            solicitudDAO.rechazar(conn, idSolicitud);

            auditoriaServicio.registrar(
                    conn,
                    admin.getIdUsuario(),
                    "RECHAZAR_SOLICITUD",
                    s.getModulo(),
                    s.getReferenciaId(),
                    "Solicitud rechazada ID=" + idSolicitud
            );

            conn.commit();

        } catch (Exception e) {
            throw new RuntimeException("Error al rechazar solicitud", e);
        }
    }

    // ===============================
    // 🔐 VALIDACIONES
    // ===============================
    private void validarAdmin(Usuario admin) {
        if (!autorizacionService.esAdmin(admin)) {
            throw new SecurityException("No autorizado");
        }
    }

    private void validarPendiente(SolicitudCambio s) {
        if (s == null) throw new RuntimeException("Solicitud no encontrada");

        if (!"PENDIENTE".equalsIgnoreCase(s.getEstado())) {
            throw new RuntimeException("Solicitud ya procesada");
        }
    }

    // ===============================
    // 💥 PROCESOS
    // ===============================
    private void procesarAnulacionVenta(Connection conn, SolicitudCambio s, Usuario admin) {

        int idVenta = s.getReferenciaId();

        Venta venta = ventaDAO.obtenerPorId(conn, idVenta);

        if (venta == null) {
            throw new RuntimeException("Venta no encontrada");
        }

        if ("ANULADA".equalsIgnoreCase(venta.getEstado())) {
            throw new RuntimeException("Venta ya anulada");
        }

        List<DetalleVenta> detalles = detalleDAO.listarPorVenta(conn, idVenta);

        for (DetalleVenta d : detalles) {
            inventarioDAO.sumarStock(
                    conn,
                    d.getIdProducto(),
                    d.getCantidad()
            );
        }

        ventaDAO.cambiarEstado(conn, idVenta, "ANULADA", admin.getIdUsuario());
    }

    private void procesarAjusteInventario(Connection conn, SolicitudCambio s) {

        int idProducto = s.getReferenciaId();

        int cantidad;
        try {
            cantidad = Integer.parseInt(s.getDatosNuevos());
        } catch (Exception e) {
            throw new RuntimeException("Cantidad inválida en solicitud");
        }

        inventarioDAO.restarStock(conn, idProducto, s.getIdUsuario(), cantidad);
    }

    // ===============================
    // 🟡 CREAR SOLICITUD
    // ===============================
    public void crearSolicitud(
            int idUsuario,
            String modulo,
            String tipo,
            String accion,
            int referenciaId,
            Integer cantidad,
            String datosExtra
    ) {

        try (Connection conn = Conexion.getConnection()) {

            conn.setAutoCommit(false);

            if (solicitudDAO.existeSolicitudPendiente(conn, modulo, referenciaId)) {
                throw new RuntimeException("Ya existe una solicitud pendiente");
            }

            String datosNuevos = (cantidad != null) ? String.valueOf(cantidad) : null;

            solicitudDAO.crearSolicitud(
                    conn,
                    idUsuario,
                    modulo,
                    tipo,
                    accion,
                    referenciaId,
                    null,
                    datosNuevos
            );

            auditoriaServicio.registrar(
                    conn,
                    idUsuario,
                    "CREAR_SOLICITUD",
                    modulo,
                    referenciaId,
                    "Solicitud creada: " + accion
            );

            conn.commit();

        } catch (Exception e) {
            throw new RuntimeException("Error al crear solicitud", e);
        }
    }
}