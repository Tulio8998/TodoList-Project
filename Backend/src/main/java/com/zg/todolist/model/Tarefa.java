package com.zg.todolist.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tarefa implements Comparable<Tarefa> {
    private String nome;
    private String descricao;
    private LocalDate dataTermino;
    private String categoria;
    private int prioridade;
    private Status status;
    private List<Alarme> alarmes;

    public Tarefa(String nome, String descricao, LocalDate dataTermino, int prioridade, String categoria, Status status) {
        if (prioridade < 1 || prioridade > 5) {
            throw new IllegalArgumentException("Prioridade invalida\n Deve esta entre 1 e 5");
        }

        this.descricao = descricao;
        this.nome = nome;
        this.dataTermino = dataTermino;
        this.categoria = categoria;
        this.prioridade = prioridade;
        this.status = status;
        this.alarmes = new ArrayList<Alarme>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Alarme> getAlarmes() {
        return alarmes;
    }

    public void setAlarmes(List<Alarme> alarmes) {
        this.alarmes = alarmes;
    }

    @Override
    public int compareTo(Tarefa tarefa) {
        return Integer.compare(this.prioridade, tarefa.getPrioridade());
    }
}
