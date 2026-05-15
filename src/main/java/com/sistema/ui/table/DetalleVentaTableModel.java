package com.sistema.ui.table;
import com.sistema.modelo.DetalleVenta;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaTableModel extends AbstractTableModel {

    public int getRowCount() {
        return detalles.size();
    }

    public int getColumnCount() {
        return columnas.length;
    }

    public String getColumnName(int column) {
        return columnas[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        DetalleVenta d = detalles.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> d.getIdProducto(); // luego cambiaremos por nombre
            case 1 -> d.getCantidad();
            case 2 -> d.getPrecio();
            case 3 -> d.getSubtotal();
            default -> null;
        };
    }

    public void agregarDetalle(DetalleVenta d) {
        detalles.add(d);
        fireTableDataChanged();
    }

    public void eliminarDetalle(int fila) {
        detalles.remove(fila);
        fireTableDataChanged();
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public BigDecimal calcularTotal() {
        return detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void limpiar() {
        detalles.clear();
        fireTableDataChanged();
    }

    private final String[] columnas = {"Producto", "Cantidad", "Precio", "Subtotal"};
    private List<DetalleVenta> detalles = new ArrayList<>();

}