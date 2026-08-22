package com.zg.todolist.service;

import com.zg.todolist.model.Alarme;
import com.zg.todolist.model.Status;
import com.zg.todolist.model.Tarefa;
import com.zg.todolist.model.Usuario;
import com.zg.todolist.repository.AlarmeRepository;
import com.zg.todolist.repository.TarefaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final AlarmeService alarmeService;

    public TarefaService(TarefaRepository tarefaRepository, AlarmeService alarmeService) {
        this.tarefaRepository = tarefaRepository;
        this.alarmeService = alarmeService;
    }

    public void salvarTarefa(Tarefa tarefa) {
        tarefaRepository.salvarTarefa(tarefa);
        Collections.sort(tarefaRepository.getTarefas());
    }

    public void removerTarefa(Tarefa tarefa) {
        tarefaRepository.removerTarefa(tarefa);
        alarmeService.removerTodosAlarmes(tarefa);
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
            case 4:
                tarefa.setStatus(Status.valueOf(valor.toUpperCase()));
                break;
            case 5:
                int prioridade = Integer.parseInt(valor);
                if (prioridade < 1 || prioridade > 5) {
                    System.out.println("A prioridade deve estar entre 1 e 5.");
                }
                tarefa.setPrioridade(prioridade);
                break;
            case 6:
                tarefa.setDataTermino(LocalDate.parse(valor));
                break;
            case 7:
                String[] dadosAlarme = valor.split(";");
                int indexAlarme = Integer.parseInt(dadosAlarme[0]);
                LocalDateTime horario = LocalDateTime.parse(dadosAlarme[1]);
                tarefa.getAlarmes().get(indexAlarme).setHorarioAlarme(horario);
                alarmeService.salvarAlarme();
                break;

            default:
                System.out.println("Opção inválida.");
        }
        if (opcao != 7) {
            tarefaRepository.atualizarTarefa(tarefa);
        }
    }

    public List<Tarefa> listarPorPrioridade(int prioridade) {
        return tarefaRepository.getTarefas().stream().filter(t -> t.getPrioridade() == prioridade).toList();
    }

    public List<Tarefa> listarPorCategoria(String categoria) {
        return tarefaRepository.getTarefas().stream().filter(t -> t.getCategoria().equals(categoria)).toList();
    }

    public List<Tarefa> listarPorStatus(Status status) {
        return tarefaRepository.getTarefas().stream().filter(t -> t.getStatus() == status).toList();
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.getTarefas();
    }

    public int contarTarefasStatus(Status status) {
        return tarefaRepository.listarPorStatus(status).size();
    }

    public void carregarTarefaUsuario(Usuario usuario) {
        tarefaRepository.usuarioAtual(usuario.getEmail());
        alarmeService.carregarAlarmesUsuario(usuario.getEmail(), tarefaRepository.getTarefas());
    }

}
