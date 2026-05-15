package com.sistema.servicio;

import com.sistema.dao.InventarioDAO;
import java.util.List;

public class InventarioServicio {

    private final InventarioDAO dao = new InventarioDAO();

    public List<Object[]> listarInventarioConProducto(int idVendedor) {
        return dao.listarConProducto();
    }
}