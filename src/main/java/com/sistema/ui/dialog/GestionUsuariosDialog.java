package com.sistema.ui.dialog;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;

public class GestionUsuariosDialog extends JDialog {

    public GestionUsuariosDialog(Frame owner, Usuario usuarioLogueado) {
        super(owner, "Gestión de Usuarios", true);
        this.usuarioLogueado = usuarioLogueado;
        configurarVentana();
        inicializarComponentes();
        cargarUsuariosEnTabla();
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
        btnEditar = new JButton("Editar");
        btnRefrescar = new JButton("Refrescar");
        btnCerrar = new JButton("Cerrar");

        btnNuevo.addActionListener(e -> abrirFormularioUsuario());
        btnEditar.addActionListener(e -> editarUsuarioSeleccionado());
        btnRefrescar.addActionListener(e -> cargarUsuariosEnTabla());
        btnCerrar.addActionListener(e -> dispose());

        panel.add(btnNuevo);
        panel.add(btnEditar);
        panel.add(btnRefrescar);
        panel.add(btnCerrar);

        return panel;
    }
    
    private void cargarUsuariosEnTabla() {

        modeloTabla.setRowCount(0);

        List<Usuario> usuarios = UsuarioServicio
            .getInstancia()
            .listarSegunRol(usuarioLogueado);

        for (Usuario u : usuarios) {
            modeloTabla.addRow(new Object[]{
                u.getIdUsuario(),
                u.getNombre(),
                u.getEmail(),
                u.getRol().getNombre()
            });
        }
    }

    private void abrirFormularioUsuario() {
        Window owner = SwingUtilities.getWindowAncestor(this); // obtiene el Window padre
        UsuarioDialog dialog = new UsuarioDialog(owner, usuarioLogueado); // nuevo usuario
        dialog.setVisible(true);
        cargarUsuariosEnTabla(); 
    }
    
    private Usuario getUsuarioLogueado() {
       return usuarioLogueado;
    }

    private void editarUsuarioSeleccionado() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
            "Seleccione un usuario",
            "Aviso",
            JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idUsuario = (int) modeloTabla.getValueAt(fila, 0);
        Usuario usuarioSeleccionado = UsuarioServicio.getInstancia().buscarPorId(idUsuario);

        Window owner = SwingUtilities.getWindowAncestor(this); // <--- importante
        UsuarioDialog dialog = new UsuarioDialog(owner, usuarioSeleccionado, usuarioLogueado);
        dialog.setVisible(true);
        cargarUsuariosEnTabla();
    }

    private Usuario usuarioLogueado;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo, btnEditar, btnRefrescar, btnCerrar;
}
