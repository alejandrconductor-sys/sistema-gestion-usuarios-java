package com.sistema.servicio;
import com.sistema.dao.ClienteDAO;
import com.sistema.modelo.Cliente;
import java.util.List;

public class ClienteServicio {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public List<Cliente> listarTodos() {
        return clienteDAO.listarTodos();
    }

    public List<Cliente> listarPorVendedor(int idVendedor) {
        return clienteDAO.listarPorVendedor(idVendedor);
    }

    public void asignarClienteAVendedor(int idCliente, int idVendedor) {

        if (clienteDAO.existeAsignacion(idCliente, idVendedor)) {
            throw new IllegalStateException("CLIENTE_YA_ASIGNADO");
        }

        clienteDAO.asignarCliente(idCliente, idVendedor);
    }

    public void quitarClienteDeVendedor(int idCliente, int idVendedor) {
        clienteDAO.quitarCliente(idCliente, idVendedor);
    }
    public void crearCliente(Cliente cliente, int idUsuario) {

        cliente.setCreadoPor(idUsuario);

        try {
            clienteDAO.insertar(cliente);
        } catch (Exception e) {

            if (e.getMessage().contains("Duplicate")) {
                throw new RuntimeException("DOCUMENTO_DUPLICADO");
            }

            throw e;
        }
    }
}