package com.zg.todolist.repository;

import com.zg.todolist.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    List<Usuario> usuarios;

    public UsuarioRepository() {
        usuarios = new ArrayList<>();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
