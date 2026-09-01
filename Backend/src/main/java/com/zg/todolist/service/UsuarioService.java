package com.zg.todolist.service;

import com.zg.todolist.model.Usuario;
import com.zg.todolist.repository.UsuarioRepository;

import java.util.List;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository repository) {
        this.usuarioRepository = repository;
    }

    public boolean cadastrarUsuario(Usuario u) {
        if (usuarioRepository.buscarTodos().contains(u)) {
            return false;
        }
        usuarioRepository.adicionar(u);
        return true;
    }

    public boolean removerUsuario(Usuario u) {
        if (!usuarioRepository.buscarTodos().contains(u)) {
            return false;
        }
        usuarioRepository.remover(u);
        return true;
    }

    public Usuario entrar(String email, String senha) {
        return usuarioRepository.buscarUsuario(email, senha);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.buscarTodos();
    }
}
