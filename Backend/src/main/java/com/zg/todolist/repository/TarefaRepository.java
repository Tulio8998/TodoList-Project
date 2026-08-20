package com.zg.todolist.repository;

import com.zg.todolist.model.Status;
import com.zg.todolist.model.Tarefa;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TarefaRepository {
    List<Tarefa> tarefas;
    private String nomeArquivo;

    public TarefaRepository() {
        tarefas = new ArrayList<>();
    }

    private void salvarArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Tarefa t : tarefas) {
                String linha = t.getNome() + ";" + t.getDescricao() + ";" + t.getDataTermino().toString() + ";" +
                        t.getPrioridade() + ";" + t.getCategoria() + ";" + t.getStatus().name() + ";" + t.getHorarioAlarme();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo de tarefas");
        }
    }

    private void carregarArquivo() {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 7) {
                    String nome = dados[0];
                    String descricao = dados[1];
                    LocalDate data = LocalDate.parse(dados[2]);
                    int prioridade = Integer.parseInt(dados[3]);
                    String categoria = dados[4];
                    Status status = Status.valueOf(dados[5]);
                    LocalDateTime horarioAlarme = LocalDateTime.parse(dados[6]);
                    Tarefa tarefa = new Tarefa(nome, descricao, data, prioridade, categoria, status, horarioAlarme);
                    tarefas.add(tarefa);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de tarefas");
        }
    }

    public void usuarioAtual(String email) {
        this.nomeArquivo = "Backend/src/main/java/com/zg/todolist/files/tarefas_" + email + ".csv";
        this.tarefas.clear();
        carregarArquivo();
    }

    public void salvarTarefa(Tarefa tarefa) {
        tarefas.add(tarefa);
        salvarArquivo();
    }

    public void removerTarefa(Tarefa tarefa) {
        tarefas.remove(tarefa);
        salvarArquivo();
    }

    public void atualizarTarefa(Tarefa tarefa) {
        int index = tarefas.indexOf(tarefa);
        if (index != -1) {
            tarefas.set(index, tarefa);
            Collections.sort(tarefas);
            salvarArquivo();
        }
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public List<Tarefa> listarPorStatus(Status status) {
        return tarefas.stream().filter(t -> t.getStatus() == status).collect(Collectors.toList());
    }
}
