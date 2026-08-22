package com.zg.todolist.repository;

import com.zg.todolist.model.Alarme;
import com.zg.todolist.model.Status;
import com.zg.todolist.model.Tarefa;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlarmeRepository {
    List<Alarme> alarmes;
    private String nomeArquivo;

    public AlarmeRepository() {
        alarmes = new ArrayList<>();
    }

    private void salvarArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Alarme a : alarmes) {
                String linha = a.getTarefa().getNome() + ";" + a.getHorarioAlarme().toString();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo de alarmes");
        }
    }
    private void carregarArquivo(List<Tarefa> tarefasUsuario) {
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 2) {
                    String nomeTarefa = dados[0];
                    LocalDateTime horario = LocalDateTime.parse(dados[1]);
                    Tarefa tarefaEncontrada = tarefasUsuario.stream().filter(t -> t.getNome().equals(nomeTarefa)).findFirst().orElse(null);

                    if (tarefaEncontrada != null) {
                        Alarme novoAlarme = new Alarme(horario, tarefaEncontrada);
                        alarmes.add(novoAlarme);
                        tarefaEncontrada.getAlarmes().add(novoAlarme);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de alarmes");
        }
    }

    public void usuarioAtual(String email, List<Tarefa> tarefasUsuario) {
        this.nomeArquivo = "Backend/src/main/java/com/zg/todolist/files/alarmes_" + email + ".csv";
        this.alarmes.clear();
        carregarArquivo(tarefasUsuario);
    }

    public void adicionarAlarme(Alarme alarme) {
        alarmes.add(alarme);
        salvarArquivo();
    }

    public void salvarAlarme() {
        salvarArquivo();
    }

    public void removerAlarmes(Tarefa tarefa) {
        alarmes.removeIf(a -> a.getTarefa().getNome().equals(tarefa.getNome()));
        salvarArquivo();
    }

    public List<Alarme> getAlarmes() {
        return alarmes;
    }
}
