package com.sistema.seguridad;

import java.util.*;

import com.sistema.modelo.Usuario;

public class AutorizacionService {

    private final Map<String, Set<Permiso>> permisosPorRol = new HashMap<>();

    public AutorizacionService() {
        configurarPermisos();
    }

    private void configurarPermisos() {

        // ADMIN → todos los permisos
        permisosPorRol.put("ADMIN", EnumSet.allOf(Permiso.class));

        // RRHH
        permisosPorRol.put("RRHH", EnumSet.of(
                Permiso.USUARIO_VER,
                Permiso.USUARIO_EDITAR,
                Permiso.USUARIO_APROBAR,
                Permiso.USUARIO_DESACTIVAR
        ));

        // USUARIO
        permisosPorRol.put("USUARIO", EnumSet.of(
                Permiso.USUARIO_VER,
                Permiso.USUARIO_EDITAR
        ));
    }

    public boolean tienePermiso(Usuario usuario, Permiso permiso) {

        if (usuario == null || usuario.getRol() == null) {
            return false;
        }

        String nombreRol = usuario.getRol().getNombre();

        Set<Permiso> permisos = permisosPorRol.get(nombreRol);

        return permisos != null && permisos.contains(permiso);
    }
}

