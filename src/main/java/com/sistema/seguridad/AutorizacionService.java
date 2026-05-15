package com.sistema.seguridad;
import com.sistema.modelo.Usuario;

public class AutorizacionService {

    public boolean tienePermiso(Usuario usuario, Permiso permiso) {

        if (usuario == null || usuario.getRol() == null) {
            return false;
        }

        String rol = usuario.getRol().getNombre();

        if ("ADMIN".equals(rol)) {
            return true;
        }

        if ("GERENTE_VENTAS".equals(rol)) {
            return switch (permiso) {
                case USUARIO_DESACTIVAR -> true;
                case VENTA_ANULAR -> true;
                case INVENTARIO_AJUSTAR -> true;
                default -> false;
            };
        }

        if ("VENDEDOR".equals(rol)) {
            return permiso == Permiso.VENTA_CREAR;
        }

        return false;
    }

    public boolean esAdmin(Usuario usuario) {

        if (usuario == null || usuario.getRol() == null) {
            return false;
        }

        return "ADMIN"
                .equalsIgnoreCase(usuario.getRol().getNombre());
    }

    public boolean esGerente(Usuario usuario) {

        if (usuario == null || usuario.getRol() == null) {
            return false;
        }

        return "GERENTE_VENTAS"
                .equalsIgnoreCase(usuario.getRol().getNombre());
    }

}