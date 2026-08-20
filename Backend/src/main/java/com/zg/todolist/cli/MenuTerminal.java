package com.zg.todolist.cli;

import com.zg.todolist.model.Tarefa;
import com.zg.todolist.model.Usuario;
import com.zg.todolist.service.TarefaService;
import com.zg.todolist.service.UsuarioService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import com.zg.todolist.model.Status;
import static com.zg.todolist.util.Util.leInteiroConsole;
import static com.zg.todolist.util.Util.leStringConsole;

public class MenuTerminal {
    Scanner scanner = new Scanner(System.in);
    private final UsuarioService usuarioService;
    private final TarefaService tarefaService;

    public MenuTerminal(UsuarioService usuarioService, TarefaService tarefaService) {
        this.usuarioService = usuarioService;
        this.tarefaService = tarefaService;
    }

    public void executaTerminal() {
        Usuario teste = new Usuario("1", "1", "1");
        usuarioService.cadastrarUsuario(teste);
        System.out.println("Seja bem vindo");
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Entrar");
            System.out.println("2. Cadastrar");
            System.out.println("3. Sair");
            System.out.print("Resposta: ");
            int resposta = leInteiroConsole(scanner);

            switch (resposta) {
                case 1:
                    entrar();
                    break;
                case 2:
                    cadastroUsuario();
                    break;
                case 3:
                    System.out.println("Saindo");
                    return;
                default:
                    System.out.println("Erro!\nEscolha novamente");
            }

        } while(true);
    }

    public void cadastroUsuario() {
        System.out.println("Seja bem vindo ao cadastro de usuario");
        System.out.println("Digite seu nome: ");
        String nome = leStringConsole(scanner);
        System.out.println("Digite seu email: ");
        String email = leStringConsole(scanner);
        System.out.println("Digite seu senha: ");
        String senha = leStringConsole(scanner);

        Usuario usuario = new Usuario(nome, email, senha);
        if (usuarioService.cadastrarUsuario(usuario)) {
            System.out.println("Usuario cadastrado");
        } else {
            System.out.println("Email ja pertecente a outro usuario");
        }
    }

    public void entrar() {
        System.out.println("Seja bem vindo");
        System.out.println("Digite seu email: ");
        String email = leStringConsole(scanner);
        System.out.println("Digite seu senha: ");
        String senha = leStringConsole(scanner);

        Usuario usuario = usuarioService.entrar(email, senha);
        if (usuario != null) {
            System.out.println("Usuario logado");
            tarefaService.carregarTarefaUsuario(usuario);
            tarefas(usuario);
        } else {
            System.out.println("Usuario nao encontrado\nEmail ou senha incorreta");
        }
    }

    public void tarefas(Usuario usuario) {
        System.out.println("Seja bem vindo as suas tarefas");
        do {
            tarefaService.verificarAlarmes();
            System.out.println("\nMenu:");
            System.out.println("1. Cadastrar tarefas");
            System.out.println("2. Editar tarefas");
            System.out.println("3. Exluir tarefas");
            System.out.println("4. Listar tarefas");
            System.out.println("5. Consultar quantidade por status");
            System.out.println("6. Sair");
            System.out.print("Resposta: ");
            int resposta = leInteiroConsole(scanner);

            switch (resposta) {
                case 1:
                    cadastroTarefa();
                    break;
                case 2:
                    atualizarTarefa();
                    break;
                case 3:
                    excluirTarefa();
                    break;
                case 4:
                    listarTarefas();
                    break;
                case 5:
                    consultarQuantidadeStatus();
                    break;
                case 6:
                    System.out.println("Saindo");
                    return;
                default:
                    System.out.println("Erro!\nEscolha novamente");
            }
        } while(true);
    }

    public void cadastroTarefa() {
        System.out.println("Cadastro de tarefa");

        System.out.print("Nome: ");
        String nome = leStringConsole(scanner);
        System.out.print("Descricao: ");
        String descricao = leStringConsole(scanner);
        LocalDate dataTermino;
        while (true) {
            System.out.print("Data de termino (AAAA-MM-DD): ");
            try {
                dataTermino = LocalDate.parse(leStringConsole(scanner));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida!\nDigite no formato AAAA-MM-DD");
            }
        }
        System.out.print("Nível de prioridade (1 a 5): ");
        int prioridade = leInteiroConsole(scanner);
        if (prioridade < 1 || prioridade > 5) {
            System.out.println("Prioridade invalida. Deve estar entre 1 e 5");
            return;
        }
        System.out.print("Categoria: ");
        String categoria = leStringConsole(scanner);
        System.out.println("Status:");
        System.out.println("1. TODO");
        System.out.println("2. DOING");
        System.out.println("3. DONE");
        int opcaoStatus = leInteiroConsole(scanner);

        Status status;
        switch (opcaoStatus) {
            case 1:
                status = Status.TODO;
                break;
            case 2:
                status = Status.DOING;
                break;
            case 3:
                status = Status.DONE;
                break;
            default:
                System.out.println("Status invalido");
                return;
        }
        LocalDateTime horarioAlarme;
        while (true) {
            System.out.print("Data de alarme (AAAA-MM-DDTHH:mm): ");
            try {
                horarioAlarme = LocalDateTime.parse(leStringConsole(scanner));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida!\nDigite no formato AAAA-MM-DDTHH:mm");
            }
        }
        Tarefa novaTarefa = new Tarefa(nome, descricao, dataTermino, prioridade, categoria, status, horarioAlarme);
        tarefaService.salvarTarefa(novaTarefa);

        System.out.println("Tarefa cadastrada com sucesso!");
    }

    public void atualizarTarefa() {
        List<Tarefa> tarefas = tarefaService.listarTarefas();
        if (tarefas.isEmpty()) {
            System.out.println("cao existem tarefas cadastradas");
            return;
        }

        System.out.println("\nTarefas cadastradas:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ". " + tarefas.get(i).getNome());
        }

        System.out.print("Escolha a tarefa que deseja atualizar: ");
        int tarefaEscolhida = leInteiroConsole(scanner);
        if (tarefaEscolhida < 1 || tarefaEscolhida > tarefas.size()) {
            System.out.println("Opcao invalida");
            return;
        }

        Tarefa tarefa = tarefas.get(tarefaEscolhida - 1);
        System.out.println("\nO que deseja atualizar?");
        System.out.println("1. Nome");
        System.out.println("2. Descricao");
        System.out.println("3. Categoria");
        System.out.println("4. Status");
        System.out.println("5. Prioridade");
        System.out.println("6. Data de termino");
        System.out.println("7. Alarme atual");
        int resposta = leInteiroConsole(scanner);
        String valor;

        switch (resposta) {
            case 1:
                System.out.print("Novo nome: ");
                valor = leStringConsole(scanner);
                break;
            case 2:
                System.out.print("Nova descricao: ");
                valor = leStringConsole(scanner);
                break;
            case 3:
                System.out.print("Nova categoria: ");
                valor = leStringConsole(scanner);
                break;
            case 4:
                System.out.println("Escolha o novo status:");
                System.out.println("1. TODO");
                System.out.println("2. DOING");
                System.out.println("3. DONE");
                int opcaoStatus = leInteiroConsole(scanner);

                switch (opcaoStatus) {
                    case 1:
                        valor = "TODO";
                        break;
                    case 2:
                        valor = "DOING";
                        break;
                    case 3:
                        valor = "DONE";
                        break;
                    default:
                        System.out.println("Opcao invalida");
                        return;
                }
                break;
            case 5:
                while (true) {
                    System.out.print("Nova prioridade (1 a 5): ");
                    valor = leStringConsole(scanner);
                    try {
                        int prioridade = Integer.parseInt(valor);
                        if (prioridade >= 1 && prioridade <= 5) {
                            break;
                        }
                        System.out.println("Prioridade invalida. Deve estar entre 1 e 5");
                    } catch (NumberFormatException e) {
                        System.out.println("Prioridade invalida. Digite um número de 1 a 5");
                    }
                }
                break;
            case 6:
                while (true) {
                    System.out.print("Data de termino (AAAA-MM-DD): ");
                    valor = leStringConsole(scanner);
                    try {
                        LocalDate.parse(valor);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println("Data invalida! Digite no formato AAAA-MM-DD");
                    }
                }
                break;
            case 7:
                while (true) {
                    System.out.print("Data e hora do alarme (AAAA-MM-DDTHH:mm): ");
                    valor = leStringConsole(scanner);
                    try {
                        LocalDateTime.parse(valor);
                        break;
                    } catch (DateTimeParseException e) {
                        System.out.println(
                                "Data invalida! Digite no formato AAAA-MM-DDTHH:mm"
                        );
                    }
                }
                break;
            default:
                System.out.println("Opcao invalida.");
                return;
        }
        tarefaService.atualizarTarefa(tarefa, resposta, valor);

        System.out.println("Tarefa atualizada com sucesso!");
    }

    public void excluirTarefa() {
        List<Tarefa> tarefas = tarefaService.listarTarefas();

        if (tarefas.isEmpty()) {
            System.out.println("Nao existem tarefas cadastradas");
            return;
        }

        System.out.println("Tarefas cadastradas:");
        for (int i = 0; i < tarefas.size(); i++) {
            System.out.println((i + 1) + ". " + tarefas.get(i).getNome());
        }

        System.out.print("Escolha o numero da tarefa que deseja excluir: ");
        int opcao = leInteiroConsole(scanner);
        if (opcao < 1 || opcao > tarefas.size()) {
            System.out.println("Opcao invalida");
            return;
        }

        Tarefa tarefa = tarefas.get(opcao - 1);
        tarefaService.removerTarefa(tarefa);
        System.out.println("Tarefa excluida com sucesso");
    }

    public void listarTarefas() {
        System.out.println("\nComo deseja listar as tarefas?");
        System.out.println("1. Por categoria");
        System.out.println("2. Por prioridade");
        System.out.println("3. Por status");
        System.out.println("4. Listar todas");
        System.out.print("Resposta: ");
        int opcao = leInteiroConsole(scanner);
        List<Tarefa> tarefas;

        switch (opcao) {
            case 1:
                System.out.print("Digite a categoria: ");
                String categoria = leStringConsole(scanner);
                tarefas = tarefaService.listarPorCategoria(categoria);
                break;
            case 2:
                System.out.print("Digite a prioridade (1 a 5): ");
                int prioridade = leInteiroConsole(scanner);
                if (prioridade < 1 || prioridade > 5) {
                    System.out.println("Prioridade invalida. Deve estar entre 1 e 5");
                    return;
                }
                tarefas = tarefaService.listarPorPrioridade(prioridade);
                break;
            case 3:
                System.out.println("Escolha o status:");
                System.out.println("1. TODO");
                System.out.println("2. DOING");
                System.out.println("3. DONE");
                System.out.print("Resposta: ");
                int opcaoStatus = leInteiroConsole(scanner);

                Status status;
                switch (opcaoStatus) {
                    case 1:
                        status = Status.TODO;
                        break;
                    case 2:
                        status = Status.DOING;
                        break;
                    case 3:
                        status = Status.DONE;
                        break;
                    default:
                        System.out.println("Status invalido");
                        return;
                }
                tarefas = tarefaService.listarPorStatus(status);
                break;
            case 4:
                tarefas = tarefaService.listarTarefas();
                break;
            default:
                System.out.println("Opcao invalida");
                return;
        }

        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada");
            return;
        }

        System.out.println("\nTarefas encontradas:");
        tarefas.forEach(t -> {
            System.out.println("Nome: " + t.getNome());
            System.out.println("Descricao: " + t.getDescricao());
            System.out.println("Data de término: " + t.getDataTermino());
            System.out.println("Prioridade: " + t.getPrioridade());
            System.out.println("Categoria: " + t.getCategoria());
            System.out.println("Status: " + t.getStatus());
            System.out.println("Alarme: " + t.getHorarioAlarme());
            System.out.println("\n");
        });
    }

    public void consultarQuantidadeStatus() {
        int todo = tarefaService.contarTarefasStatus(Status.TODO);
        int doing = tarefaService.contarTarefasStatus(Status.DOING);
        int done = tarefaService.contarTarefasStatus(Status.DONE);

        System.out.println("\nQuantidade de tarefas:");
        System.out.println("TODO: " + todo);
        System.out.println("DOING: " + doing);
        System.out.println("DONE: " + done);
    }
}
