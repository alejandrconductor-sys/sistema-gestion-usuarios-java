package com.sistema.ui.table;
import com.sistema.modelo.Usuario;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UsuarioTableModel extends AbstractTableModel {

    private final String[] columnas = {"ID", "Nombre", "Email", "Estado", "Rol"};
    private List<Usuario> usuarios;

    public UsuarioTableModel(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public int getRowCount() {
        return usuarios.size();
    }

    public int getColumnCount() {
        return columnas.length;
    }

    public String getColumnName(int column) {
        return columnas[column];
    }

public Object getValueAt(int rowIndex, int columnIndex) {
    Usuario u = usuarios.get(rowIndex);

    switch (columnIndex) {
        case 0:
            return u.getIdUsuario();
        case 1:
            return u.getNombre();
        case 2:
            return u.getEmail();
        case 3:
            return u.getEstado(); 
        case 4:
            return u.getRol().getNombre();// mostrar nombre
        default:
            return null;
    }
}
    public void actualizarDatos(List<Usuario> nuevosUsuarios) {
        this.usuarios = nuevosUsuarios;
        fireTableDataChanged();
    }

    public Usuario getUsuarioEn(int fila){
        return usuarios.get(fila);
    }

}
