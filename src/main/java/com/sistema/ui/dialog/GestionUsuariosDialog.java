package com.sistema.ui.dialog;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;

public class GestionUsuariosDialog extends JDialog {


    public GestionUsuariosDialog(Frame owner) {
        super(owner, "Gestión de Usuarios", true);
        configurarVentana();
        inicializarComponentes();
        cargarUsuariosEnTabla();
        setVisible(true);
    }

    private void configurarVentana() {
        setSize(700, 400);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    //tabla
    private JScrollPane crearPanelTabla() {
        modeloTabla = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Nombre", "Email", "Rol"}
        ) {
            // Evita edición directa en la tabla
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return new JScrollPane(tablaUsuarios);
    }

    //botones
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnNuevo = new JButton("Nuevo");
        btnRefrescar = new JButton("Refrescar");
        btnCerrar = new JButton("Cerrar");

        btnNuevo.addActionListener(e -> abrirFormularioUsuario());
        btnRefrescar.addActionListener(e -> cargarUsuariosEnTabla());
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnNuevo);
        panel.add(btnRefrescar);
        panel.add(btnCerrar);

        return panel;
    }

    
    private void cargarUsuariosEnTabla() {
        modeloTabla.setRowCount(0);

        List<Usuario> usuarios = UsuarioServicio
                .getInstancia()
                .listaUsuarios();

        for (Usuario u : usuarios) {
            modeloTabla.addRow(new Object[]{
                u.getIdUsuario(),
                u.getNombre(),
                u.getEmail(),
                u.getRol()
            });
        }
    }

    private void abrirFormularioUsuario() {
        UsuarioDialog dialog=new UsuarioDialog(this);
        dialog.setVisible(true);
        cargarUsuariosEnTabla(); // refresca al cerrar el formulario
    }

    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo;
    private JButton btnRefrescar;
    private JButton btnCerrar;

}
