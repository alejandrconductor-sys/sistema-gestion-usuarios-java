package com.sistema.ui.panel;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;
import com.sistema.ui.dialog.UsuarioDialog;
import com.sistema.ui.table.UsuarioTableModel;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UsuarioPanel extends JPanel {

    public UsuarioPanel(Frame owner, Usuario usuarioLogueado) {

        this.usuarioLogueado = usuarioLogueado;

        setLayout(new BorderLayout(10, 10));
        inicializarComponentes(owner);
        aplicarPermisos();
    }

    private void inicializarComponentes(Frame owner) {

        List<Usuario> usuarios =
                UsuarioServicio.getInstancia()
                        .listarSegunRol(usuarioLogueado);

        tableModel = new UsuarioTableModel(usuarios);

        tabla = new JTable(tableModel);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(crearPanelBotones(owner), BorderLayout.SOUTH);
    }

    private JPanel crearPanelBotones(Frame owner) {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        btnNuevo = new JButton("Nuevo Usuario");
        btnEditar = new JButton("Editar Usuario");

        // NUEVO

        btnNuevo.addActionListener(e -> {
        UsuarioDialog dialog = new UsuarioDialog(owner, usuarioLogueado);
        dialog.setVisible(true);
        refrescarTabla();
        });

        // EDITAR
        btnEditar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un usuario para editar.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Usuario usuario = tableModel.getUsuarioEn(fila);

            UsuarioDialog dialog =
                    new UsuarioDialog(owner, usuario, usuarioLogueado);

            dialog.setVisible(true);
            refrescarTabla();
        });

        panel.add(btnNuevo);
        panel.add(btnEditar);

        return panel;
    }

    private void refrescarTabla() {
        if (tableModel != null) {
            List<Usuario> usuarios = UsuarioServicio.getInstancia().listarSegunRol(usuarioLogueado);
            tableModel.actualizarDatos(usuarios);
        }
    }

    // 🔐 CONTROL DE BOTONES POR ROL
    private void aplicarPermisos() {

        String rol = usuarioLogueado.getRol().getNombre();

        if (rol.equals("USUARIO")) {
            btnNuevo.setVisible(false);
        }
    }

    private Usuario usuarioLogueado;
    private UsuarioTableModel tableModel;
    private JTable tabla;
    private JButton btnNuevo, btnEditar;
}