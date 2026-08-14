package com.zg.todolist.repository;

import com.zg.todolist.model.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private List<Usuario> usuarios;
    private final String NOME_ARQUIVO = "src/main/java/com/zg/todolist/files/usuarios.csv";

    public UsuarioRepository() {
        usuarios = new ArrayList<>();
        carregarDoArquivo();
    }

    private void salvarNoArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOME_ARQUIVO))) {
            for (Usuario u : usuarios) {
                String linha = u.getNome() + ";" + u.getEmail() + ";" + u.getSenha();
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo de usuarios");
        }
    }

    private void carregarDoArquivo() {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                if (dados.length == 3) {
                    String nome = dados[0];
                    String email = dados[1];
                    String senha = dados[2];
                    Usuario usuario = new Usuario(nome, email, senha);
                    usuarios.add(usuario);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar o arquivo de usuários: " + e.getMessage());
        }
    }

    public void adicionar(Usuario u) {
        usuarios.add(u);
        salvarNoArquivo();
    }

    public void remover(Usuario u) {
        usuarios.remove(u);
        salvarNoArquivo();
    }

    public Usuario buscarUsuario(String email, String senha) {
        return usuarios.stream().filter(u -> u.getEmail().equals(email) && u.getSenha().equals(senha)).findFirst().orElse(null);
    }

    public List<Usuario> buscarTodos() {
        return usuarios;
    }
}