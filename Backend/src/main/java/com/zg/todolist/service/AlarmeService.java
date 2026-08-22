package com.zg.todolist.service;

import com.zg.todolist.model.Alarme;
import com.zg.todolist.model.Status;
import com.zg.todolist.model.Tarefa;
import com.zg.todolist.repository.AlarmeRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AlarmeService {
    private final AlarmeRepository alarmeRepository;
    Thread threadAlarme = Thread.currentThread();

    public AlarmeService(AlarmeRepository alarmeRepository) {
        this.alarmeRepository = alarmeRepository;
    }

    public void monitoraAlarme() {
        Thread threadMonitor = new Thread(() -> {
            while (true) {
                verificarAlarmes();
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });

        threadMonitor.setDaemon(true);
        threadMonitor.start();
    }

    public void verificarAlarmes() {
        LocalDateTime agora = LocalDateTime.now();
        for (Alarme t : alarmeRepository.getAlarmes()) {
            if (t.getHorarioAlarme() != null) {
                if (!t.getHorarioAlarme().isAfter(agora) && t.getTarefa().getStatus() != Status.DONE) {
                    System.out.println("Alarme! Tarefa proxima do termino: " + t.getTarefa().getNome());
                }
            }
        }
    }

    public void carregarAlarmesUsuario(String email, List<Tarefa> tarefasUsuario) {
        alarmeRepository.usuarioAtual(email, tarefasUsuario);
    }

    public void adicionarAlarme(Tarefa tarefa, LocalDateTime horario) {
        Alarme novoAlarme = new Alarme(horario, tarefa);
        tarefa.getAlarmes().add(novoAlarme);
        alarmeRepository.adicionarAlarme(novoAlarme);
    }

    public void atualizarAlarme(Tarefa tarefa, int indexAlarme, LocalDateTime novoHorario) {
        tarefa.getAlarmes().get(indexAlarme).setHorarioAlarme(novoHorario);
        alarmeRepository.salvarAlarme();
    }

    public void removerAlarme(Tarefa tarefa, int indexAlarme) {
        Alarme alarmeRemovido = tarefa.getAlarmes().remove(indexAlarme);
        alarmeRepository.getAlarmes().remove(alarmeRemovido);
        alarmeRepository.salvarAlarme();
    }

    public void removerTodosAlarmes(Tarefa tarefa) {
        alarmeRepository.removerAlarmes(tarefa);
    }

    public void salvarAlarme() {
        alarmeRepository.salvarAlarme();
    }
}
