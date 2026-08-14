package com.zg.todolist.repository;

import com.zg.todolist.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private List<Usuario> usuarios = new ArrayList<>();

    public void adicionar(Usuario u) {
        usuarios.add(u);
    }

    public void remover(Usuario u) {
        usuarios.remove(u);
    }

    public Usuario buscarUsuario(String email, String senha) {
        return usuarios.stream().filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha)).findFirst().orElse(null);
    }

    public List<Usuario> buscarTodos() {
        return usuarios;
    }
}
