package com.sistema.ui.panel;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.UsuarioServicio;
import com.sistema.ui.dialog.UsuarioDialog;
import com.sistema.ui.table.UsuarioTableModel;
import javax.swing.*;
import java.awt.*;

public class UsuarioPanel extends JPanel {

    private UsuarioTableModel tableModel;
    private JTable tabla;

    public UsuarioPanel(Frame owner) {
        setLayout(new BorderLayout(10, 10));
        inicializarComponentes(owner);
    }

    private void inicializarComponentes(Frame owner) {

        tableModel = new UsuarioTableModel(
                UsuarioServicio.getInstancia().listar()
        );

        tabla = new JTable(tableModel);
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(crearPanelBotones(owner), BorderLayout.SOUTH);
    }

    private JPanel crearPanelBotones(Frame owner) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnNuevo = new JButton("Nuevo Usuario");
        JButton btnEditar = new JButton("Editar Usuario");
        JButton btnEliminar = new JButton("Eliminar Usuario");

        //Nuevo
        btnNuevo.addActionListener(e -> {
            UsuarioDialog dialog =new UsuarioDialog(owner);
            dialog.setVisible(true);
            refrescarTabla();
        });

        // EDITAR
       btnEditar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario para editar.",
            "Aviso",
            JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario usuario = tableModel.getUsuarioEn(fila);

        UsuarioDialog dialog = new UsuarioDialog(owner, usuario); // constructor nuevo
        dialog.setVisible(true);
        refrescarTabla();
        });

        // ELIMINAR
        btnEliminar.addActionListener(e -> {

            int fila = tabla.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un usuario para eliminar.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el usuario?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            Usuario usuario = tableModel.getUsuarioEn(fila);

            UsuarioServicio.getInstancia()
                    .eliminar(usuario.getIdUsuario());

            refrescarTabla();
        }
    });

    panel.add(btnNuevo);
    panel.add(btnEditar);
    panel.add(btnEliminar);

    return panel;
}


    private void refrescarTabla() {
        tableModel.actualizarDatos(
                UsuarioServicio.getInstancia().listar()
        );
    }
}
