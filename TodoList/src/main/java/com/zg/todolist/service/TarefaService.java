package com.zg.todolist.service;

import com.zg.todolist.model.Status;
import com.zg.todolist.model.Tarefa;
import com.zg.todolist.repository.TarefaRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TarefaService {
    private TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public void salvarTarefa(Tarefa tarefa) {
        tarefaRepository.salvarTarefa(tarefa);
        Collections.sort(tarefaRepository.getTarefas());
    }

    public void atualizarTarefa(Tarefa tarefa, int opcao, String valor) {
        switch (opcao) {
            case 1:
                tarefa.setNome(valor);
                break;
            case 2:
                tarefa.setDescricao(valor);
                break;
            case 3:
                tarefa.setCategoria(valor);
                break;
            default:
                throw new IllegalArgumentException("Opção invalida");
        }
        tarefaRepository.atualizarTarefa(tarefa);
    }

    public List<Tarefa> listarPorPrioridade(int prioridade) {
        return tarefaRepository.getTarefas().stream().filter(t -> t.getPrioridade() == prioridade).collect(Collectors.toList());
    }

    public List<Tarefa> listarPorCategoria(String categoria) {
        return tarefaRepository.getTarefas().stream().filter(t -> t.getCategoria().equals(categoria)).collect(Collectors.toList());
    }

    public List<Tarefa> listarPorStatus(Status status) {
        return tarefaRepository.getTarefas().stream().filter(t -> t.getStatus() == status).collect(Collectors.toList());
    }
}
