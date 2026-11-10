# Guia do projeto

## Visão geral

O TodoList é um projeto com duas implementações independentes de uma lista de tarefas:

- Um aplicativo de terminal em Java para cadastro de usuários, tarefas e alarmes, com persistência em arquivos CSV.
- Uma interface web em HTML, CSS e JavaScript puro para cadastro/login e gerenciamento de tarefas, com dados no `localStorage` do navegador.

O frontend não consome o backend Java: cada parte mantém seus próprios dados e fluxo de execução.

## Stack e estrutura

### Backend Java — `Backend/`

- Java 21; Gradle 9.7.0 via Gradle Wrapper.
- Groovy 5 e Spock 2.4 para testes, executados pela plataforma JUnit.
- Aplicação de terminal; não utiliza Spring ou outro framework web.

Estrutura principal:

```text
Backend/
├── build.gradle                 # Plugins, dependências, classe principal e configuração dos testes
├── settings.gradle              # Nome do projeto Gradle
├── gradlew / gradlew.bat        # Gradle Wrapper para Unix/Windows
└── src/
    ├── main/java/com/zg/todolist/
    │   ├── Main.java            # Montagem das dependências e inicialização
    │   ├── cli/                 # Menus e interação com o terminal
    │   ├── model/               # Usuario, Tarefa, Alarme e Status
    │   ├── repository/          # Acesso e persistência de usuários, tarefas e alarmes
    │   ├── service/             # Regras e operações da aplicação
    │   ├── util/                # Utilitários compartilhados
    │   └── files/               # Arquivos CSV usados pela aplicação
    └── test/groovy/com/zg/todolist/
        ├── repository/          # Testes de repositórios
        └── service/             # Testes de serviços
```

### Frontend web — `Frontend/`

- HTML5, CSS3 e JavaScript ES modules, sem framework ou ferramenta de build declarada.
- Não há `package.json` nem runner de testes JavaScript configurado.
- Os dados da interface são armazenados no `localStorage`; não há integração com os CSVs do backend.

Estrutura principal:

```text
Frontend/
└── src/
    ├── pages/                   # signIn.html, signUp.html e tarefas.html
    ├── components/              # Scripts clássicos que registram Web Components de layout/formulários
    ├── js/
    │   ├── main.js, app.js      # Inicialização e fluxo de cadastro/login
    │   ├── components/          # Lógica da tela de tarefas e interação com os componentes
    │   ├── model/               # Modelos Usuario, Tarefa, Alarme e Status
    │   └── services/            # Operações e persistência via localStorage
    └── styles/                  # CSS global e estilos por página/componente
```

## Como executar

### Backend

Pré-requisito: Java 21 instalado. Na pasta `Backend/`:

```bash
./gradlew run
```

No Windows, use `gradlew.bat run`. O programa inicia o menu interativo no terminal.

### Frontend

Sirva a pasta `Frontend/` por um servidor HTTP estático. Por exemplo, com Python:

```bash
cd Frontend
python3 -m http.server 8000
```

Abra `http://localhost:8000/src/pages/signIn.html` no navegador. O frontend funciona de forma independente do backend.

## Testes

Na pasta `Backend/`, execute todos os testes Java/Spock:

```bash
./gradlew test
```

Os testes ficam em `src/test/groovy` e usam Spock `Specification`, métodos de feature nomeados em texto e blocos `given`, `when` e `then`; mocks são criados com `Mock(...)` e interações verificadas nos blocos de resultado. Prefira o sufixo `Spec` para novos arquivos de especificação, seguindo os arquivos existentes.

## Convenções de código e arquitetura

### Java

- Mantenha os pacotes em minúsculas (`com.zg.todolist`), classes e enums em `PascalCase`, e métodos/variáveis em `camelCase`.
- Preserve a separação existente: `model` representa os dados, `repository` lida com armazenamento e consultas, `service` concentra regras/operações e `cli` trata da interação com o usuário.
- `Main` compõe as dependências entre essas camadas. Não introduza controllers ou frameworks web sem solicitação; o backend atual é uma aplicação de terminal, não uma arquitetura MVC web.
- A persistência atual é baseada em CSV nos repositórios; considere essa escolha ao alterar o backend.

### JavaScript e Web Components

- Use JavaScript puro e módulos ES (`import`/`export`) onde já adotados, sem adicionar framework ou etapa de build sem solicitação.
- Modelos e serviços ficam separados em `src/js/model/` e `src/js/services/`; a lógica de tela fica em `src/js/components/`.
- Web Components estendem `HTMLElement`, montam seu conteúdo em `connectedCallback()` e são registrados com `customElements.define()` usando nomes de tag em kebab-case (por exemplo, `top-bar`, `painel-new` e `filter-container`).
- Preserve os nomes das tags e os seletores/eventos usados pelas páginas e pela lógica da interface ao alterar um componente.
- Os dados do frontend são persistidos pelos serviços em `localStorage`; mantenha esse fluxo independente do armazenamento CSV do Java.

## Restrições de alteração

- Altere somente o necessário para a feature ou correção solicitada; não modifique código fora desse escopo.
- Não faça refatorações não solicitadas.
- Nunca execute `git push`. Commits, quando solicitados, devem permanecer locais.
