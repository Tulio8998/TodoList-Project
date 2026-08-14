package com.zg.todolist;

import com.zg.todolist.cli.MenuTerminal;
import com.zg.todolist.repository.TarefaRepository;
import com.zg.todolist.repository.UsuarioRepository;
import com.zg.todolist.service.TarefaService;
import com.zg.todolist.service.UsuarioService;

public class Main {
    public static void main(String[] args) {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);

        TarefaRepository tarefaRepository = new TarefaRepository();
        TarefaService tarefaService = new TarefaService(tarefaRepository);

        MenuTerminal menu = new MenuTerminal(usuarioService, tarefaService);
        menu.executaTerminal();
    }
}