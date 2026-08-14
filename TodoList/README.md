## Autor
Desenvolvido por Túlio

## Tecnologias Utilizadas
* **Java:** Linguagem principal do projeto (Lógica, Orientação a Objetos, Stream API, File IO)
* **Gradle:** Ferramenta de build

## Comentários
O projeto foi desenvolvido pensando primeiro no funcionamento do MVP, mas também
buscando manter o código organizado e fácil de entender. Algumas decisões da
implementação foram:

* *Organização do projeto:** O código foi separado em Model, Repository,
  Service e CLI, deixando cada parte responsável por uma função diferente.
  Não foram utilizados frameworks como Spring Boot.

* **Dados:** Foi utilizado arquivo .csv para salvar os dados
  dos usuários e das tarefas. Dessa forma, os dados não são perdidos quando a
  aplicação é encerrada. Cada usuário possui seu próprio arquivo de tarefas.

* **Ordenação por prioridade:** A classe Tarefa implementa Comparable<Tarefa>
  para manter as tarefas ordenadas pela prioridade, que varia de 1 a 5.
  Quando uma tarefa é adicionada ou atualizada, a lista é ordenada novamente.
* **Sistema de alarmes:** Foi adicionada uma opção para configurar um alarme
  para as tarefas. Ao entrar no sistema, os alarmes das tarefas que já estão
  no período configurado são verificados e apresentados no terminal.

## Execução pela IDE
1. Abra o projeto no IntelliJ IDEA.
2. Aguarde o Gradle sincronizar o projeto.
3. Localize a classe Main.java.
4. Clique no botão Run ▶ ao lado do método main.
5. Siga as instruções do menu interativo no terminal.

Observação: O projeto utiliza Java 21 e Gradle 9.7.0 como ferramenta de build

