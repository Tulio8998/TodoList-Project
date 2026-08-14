package com.zg.todolist.repository;

import com.zg.todolist.model.Status;
import com.zg.todolist.model.Tarefa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TarefaRepository {
    List<Tarefa> tarefas;

    public TarefaRepository() {
        tarefas = new ArrayList<>();
    }

    public void salvarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
    }

    public void removerTarefa(Tarefa tarefa) {
        tarefas.remove(tarefa);
    }

    public void atualizarTarefa(Tarefa tarefa) {
        int index = tarefas.indexOf(tarefa);
        if (index != -1) {
            tarefas.set(index, tarefa);
            Collections.sort(tarefas);
        }
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }
}
