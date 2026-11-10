## Autor
Desenvolvido por Túlio

## Tecnologias Utilizadas
* **Java:** Linguagem principal do projeto (Lógica, Orientação a Objetos, Stream API, File IO)
* **Gradle:** Ferramenta de build
* **HTML5, CSS3 e JavaScript puro (Vanilla JS):** Tecnologias base para a construção da interface do usuário (Frontend) e lógica de tela.
* **Spock Framework:** Utilizado para a cobertura de testes unitários e desenvolvimento orientado a testes.

## Comentários
O projeto foi desenvolvido pensando primeiro no funcionamento do MVP, mas também
buscando manter o código organizado e fácil de entender. Algumas decisões da
implementação foram:

* **Organização do projeto:** O código foi separado em Model, Repository,
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

### Versão 1.1.0
* **Arquitetura Visual:** Implementação de Web Components personalizados para isolar as responsabilidades, modularizar o layout e facilitar o reaproveitamento de código na interface.
* **Gerenciamento de Estado (CRUD):** A lógica de criação, leitura, atualização e exclusão das tarefas foi construída utilizando arrays em memória. Sem persistência de dados.
* **UX:** Adição de interatividade cliente, incluindo um sistema de filtros combinados (categoria, prioridade e status), barra de pesquisa dinâmica por nome da tarefa e um sistema de alarmes

### Versão 1.2.0
* **Testes Unitários:** Implementação de testes unitários com Spock para os Services e Repositories.
* **Cobertura:** Testes das operações de criação, atualização, remoção e consulta de tarefas e alarmes.
* **Mocks:** Utilização de mocks para isolar as dependências dos Services durante os testes.

### Versão 1.3.0
* **Persistência de dados:** Implementação de Local Storage para armazernar os dados no navegador
* **Setar status:** Implementação de alterar o status de mais de uma tarefa ao mesmo tempo

### Versão 1.4.0

* **Validação de Dados com Regex:** Implementação de validações de formulário utilizando Expressões Regulares (Regex) em ambas as camadas (Frontend com Vanilla JS/HTML5 e Backend com Java).
* **Regras de Negócio de Criação:** Restrição rigorosa do nível de prioridade (aceitando apenas valores de 1 a 5) e validação da data de término, bloqueando a inserção de datas no passado.
* **Cobertura de Exceções:** Inserção de mensagens de erro responsivas no frontend e adição de testes unitários no Spock para cobrir os cenários de falha de validação no backend.
* **Engenharia (SDD):** Feature arquitetada e desenvolvida utilizando a metodologia Spec-Driven Development (SDD), orientada por documentação prévia (Especificação e Plano).

## Execução pela IDE
1. Abra o projeto no IntelliJ IDEA.
2. Aguarde o Gradle sincronizar o projeto.
3. Localize a classe Main.java.
4. Clique no botão Run ao lado do método main.
5. Siga as instruções do menu interativo no terminal.

## Execução via Gradle
1. Abra o terminal na pasta Backend.
2. Execute o comando correspondente ao seu sistema: ./gradlew run

Observação: O projeto utiliza Java 21 e Gradle 9.7.0 como ferramenta de build