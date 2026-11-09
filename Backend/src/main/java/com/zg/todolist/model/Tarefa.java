package com.zg.todolist.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Tarefa implements Comparable<Tarefa> {
    private static final String PRIORIDADE_REGEX = "^[1-5]$";

    private String nome;
    private String descricao;
    private LocalDate dataTermino;
    private String categoria;
    private int prioridade;
    private Status status;
    private List<Alarme> alarmes;

    public Tarefa(String nome, String descricao, LocalDate dataTermino, int prioridade, String categoria, Status status) {
        this.descricao = descricao;
        this.nome = nome;
        this.dataTermino = dataTermino;
        this.categoria = categoria;
        this.prioridade = validarPrioridade(prioridade);
        this.status = status;
        this.alarmes = new ArrayList<Alarme>();
    }

    public static int parsePrioridade(String prioridade) {
        if (prioridade == null || !prioridade.matches(PRIORIDADE_REGEX)) {
            throw new IllegalArgumentException("Prioridade inválida. Deve ser um único número entre 1 e 5.");
        }
        return Integer.parseInt(prioridade);
    }

    private static int validarPrioridade(int prioridade) {
        return parsePrioridade(Integer.toString(prioridade));
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
        this.prioridade = validarPrioridade(prioridade);
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
