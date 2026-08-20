package com.zg.todolist;

/*
 * Desenvolvido por: Túlio
 * ZG-Hero Project - Trilha (K1-T3): Java
*/

import com.zg.todolist.cli.MenuTerminal;
import com.zg.todolist.repository.AlarmeRepository;
import com.zg.todolist.repository.TarefaRepository;
import com.zg.todolist.repository.UsuarioRepository;
import com.zg.todolist.service.AlarmeService;
import com.zg.todolist.service.TarefaService;
import com.zg.todolist.service.UsuarioService;

public class Main {
    public static void main(String[] args) {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);

        AlarmeRepository alarmeRepository = new AlarmeRepository();
        AlarmeService alarmeService = new AlarmeService(alarmeRepository);

        TarefaRepository tarefaRepository = new TarefaRepository();
        TarefaService tarefaService = new TarefaService(tarefaRepository, alarmeService);

        MenuTerminal menu = new MenuTerminal(usuarioService, tarefaService,alarmeService);
        alarmeService.monitoraAlarme();
        menu.executaTerminal();
    }
}