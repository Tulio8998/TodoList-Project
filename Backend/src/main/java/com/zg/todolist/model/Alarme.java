package com.zg.todolist.model;

import java.time.LocalDateTime;

public class Alarme {
    private LocalDateTime horarioAlarme;
    private Tarefa tarefa;

    public Alarme(LocalDateTime horarioAlarme, Tarefa tarefa) {
        this.horarioAlarme = horarioAlarme;
        this.tarefa = tarefa;
    }

    public LocalDateTime getHorarioAlarme() {
        return horarioAlarme;
    }

    public void setHorarioAlarme(LocalDateTime horarioAlarme) {
        this.horarioAlarme = horarioAlarme;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }
}
