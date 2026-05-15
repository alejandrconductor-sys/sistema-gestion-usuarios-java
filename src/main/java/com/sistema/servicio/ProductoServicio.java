package com.sistema.servicio;

import com.sistema.config.Conexion;
import com.sistema.dao.ProductoDAO;
import com.sistema.dao.ProductoVendedorDAO;
import com.sistema.dao.InventarioDAO;
import com.sistema.modelo.Producto;

import java.sql.Connection;
import java.util.List;

public class ProductoServicio {

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final ProductoVendedorDAO productoVendedorDAO = new ProductoVendedorDAO();
    private final InventarioDAO inventarioDAO = new InventarioDAO();
    private final AuditoriaServicio auditoriaServicio = new AuditoriaServicio();

    // ===============================
    // 📌 LISTADOS
    // ===============================
    public List<Producto> listarTodos() {
        return productoDAO.listarTodos();
    }

    public List<Producto> listarPorVendedor(int idVendedor) {
        return productoDAO.listarPorVendedor(idVendedor);
    }

    // ===============================
    // 📌 ASIGNACIÓN SIMPLE
    // ===============================
    public void asignarProductoAVendedor(int idProducto, int idVendedor) {

        try (Connection conn = Conexion.getConnection()) {

            productoVendedorDAO.asignar(conn, idProducto, idVendedor);

        } catch (Exception e) {
            throw new RuntimeException("Error al asignar producto a vendedor", e);
        }
    }

    public void quitarProductoDeVendedor(int idProducto, int idVendedor) {

        try (Connection conn = Conexion.getConnection()) {

            productoVendedorDAO.eliminar(conn, idProducto, idVendedor);

        } catch (Exception e) {
            throw new RuntimeException("Error al quitar producto del vendedor", e);
        }
    }

    public void agregarStock(int idGerente, int idProducto, int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        try (Connection conn = Conexion.getConnection()) {

            conn.setAutoCommit(false);

            // Asegurar que existe inventario
            inventarioDAO.crearSiNoExiste(conn, idProducto);

            // Sumar stock GLOBAL
            inventarioDAO.sumarStock(conn, idProducto, cantidad);

            auditoriaServicio.registrar(
                    conn,
                    idGerente,
                    "AGREGAR_STOCK",
                    "INVENTARIO",
                    idProducto,
                    "Stock agregado: " + cantidad
            );

            conn.commit();

        } catch (Exception e) {
            throw new RuntimeException("Error agregando stock", e);
        }
    }
    public void crearProducto(Producto producto, int idUsuario) {

        producto.setCreadoPor(idUsuario);

        productoDAO.insertar(producto);
    }
}