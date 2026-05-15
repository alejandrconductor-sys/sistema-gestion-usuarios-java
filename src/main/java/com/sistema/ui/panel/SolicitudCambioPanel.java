package com.sistema.ui.panel;

import com.sistema.dao.SolicitudCambioDAO;
import com.sistema.modelo.Usuario;
import com.sistema.servicio.SolicitudCambioServicio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SolicitudCambioPanel extends JPanel {

    private final SolicitudCambioServicio servicio = new SolicitudCambioServicio();
    private final SolicitudCambioDAO dao = new SolicitudCambioDAO();

    private JComboBox<String> comboFiltro;
    private JTable tabla;
    private DefaultTableModel modelo;

    private final Usuario admin;

    public SolicitudCambioPanel(Usuario admin) {

        this.admin = admin;

        setLayout(new BorderLayout());

        initUI();
        initTabla();
        initBotones();

        cargarDatos();
    }

    // =========================
    // UI
    // =========================
    private void initUI() {

        comboFiltro = new JComboBox<>(new String[]{"TODOS", "VENTAS", "INVENTARIO"});

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Filtrar:"));
        top.add(comboFiltro);

        add(top, BorderLayout.NORTH);

        comboFiltro.addActionListener(e -> cargarDatos());
    }

    // =========================
    // TABLA
    // =========================
    private void initTabla() {

        modelo = new DefaultTableModel(
                new Object[]{
                        "ID", "Módulo", "Referencia", "Acción", "Datos", "Estado", "Fecha"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    // =========================
    // BOTONES
    // =========================
    private void initBotones() {

        JButton btnAprobar = new JButton("Aprobar");
        JButton btnRechazar = new JButton("Rechazar");
        JButton btnRefrescar = new JButton("Refrescar");

        btnAprobar.addActionListener(e -> aprobar());
        btnRechazar.addActionListener(e -> rechazar());
        btnRefrescar.addActionListener(e -> cargarDatos());

        JPanel panel = new JPanel();
        panel.add(btnAprobar);
        panel.add(btnRechazar);
        panel.add(btnRefrescar);

        add(panel, BorderLayout.SOUTH);
    }

    // =========================
    // 🔥 FIX REAL AQUÍ
    // =========================
    private void cargarDatos() {

        modelo.setRowCount(0);

        String filtro = (String) comboFiltro.getSelectedItem();

        List<Object[]> todas = dao.listarTodasSolicitudes();
        List<Object[]> lista;

        switch (filtro) {

            case "INVENTARIO":
                lista = todas.stream()
                        .filter(r -> "INVENTARIO".equals(String.valueOf(r[1])))
                        .toList();
                break;

            case "VENTAS":
                lista = todas.stream()
                        .filter(r -> "VENTA".equals(String.valueOf(r[1])))
                        .toList();
                break;

            default:
                lista = todas;
        }

        for (Object[] fila : lista) {
            modelo.addRow(fila);
        }

        tabla.clearSelection();
    }

    // =========================
    // APROBAR
    // =========================
    private void aprobar() {

        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        try {
            int id = (int) modelo.getValueAt(fila, 0);
            servicio.aprobarSolicitud(id, admin);

            cargarDatos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // =========================
    // RECHAZAR
    // =========================
    private void rechazar() {

        int fila = tabla.getSelectedRow();
        if (fila == -1) return;

        try {
            int id = (int) modelo.getValueAt(fila, 0);
            servicio.rechazarSolicitud(id, admin);

            cargarDatos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}