package com.ejemplo.mvc.model;

import java.util.HashMap;
import java.util.Map;

public class UsuarioDAO {
    // Usuarios en memoria; en produccion las claves se guardan con hash
    // (ej. bcrypt), nunca en texto plano. Se simplifica aqui para
    // enfocar el laboratorio en el uso de HttpSession y roles.
    private static final Map<String, Usuario> usuarios = new HashMap<>();

    static {
        usuarios.put("admin", new Usuario(
            "admin", "Admin123!", "Administrador General", "ADMIN"));
        usuarios.put("maria", new Usuario(
            "maria", "Maria2026!", "Mar\u00eda Fernanda Rojas", "USER"));
    }

    public Usuario buscarPorUsername(String username) {
        return usuarios.get(username);
    }
}
