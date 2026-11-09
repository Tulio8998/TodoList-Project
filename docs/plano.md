# Plano de implementação: validação Regex

Este plano implementa a especificação em [spec.md](./spec.md). A prioridade e a data serão validadas tanto na criação quanto na edição, sem alterar os formatos ou mecanismos de persistência existentes.

## Tarefas

### 1. Validação de prioridade no backend

- [x] **O que será feito:** Validar a prioridade como texto com `^[1-5]$` antes de convertê-la para número, rejeitando entradas vazias, fora do intervalo ou com caracteres adicionais. Garantir que a regra de domínio também impeça valores inválidos ao criar ou atualizar tarefas e lance uma exceção clara.
- **Arquivos afetados:** `Backend/src/main/java/com/zg/todolist/model/Tarefa.java`, `Backend/src/main/java/com/zg/todolist/cli/MenuTerminal.java`, testes Spock em `Backend/src/test/groovy/com/zg/todolist/`.
- **Como validar:** Adicionar testes Spock para prioridades válidas e inválidas, executar `cd Backend && ./gradlew test` e confirmar que a entrada inválida não salva a tarefa.

### 2. Validação de data no backend

- [x] **O que será feito:** Validar o formato ISO `YYYY-MM-DD` com Regex antes do parse, rejeitar datas inexistentes e datas anteriores ao dia atual. Permitir hoje e datas futuras e lançar exceções claras por tipo de falha.
- **Arquivos afetados:** `Backend/src/main/java/com/zg/todolist/model/Tarefa.java`, `Backend/src/main/java/com/zg/todolist/cli/MenuTerminal.java`, testes Spock em `Backend/src/test/groovy/com/zg/todolist/`.
- **Como validar:** Cobrir com Spock formato inválido, data impossível, data passada, hoje e data futura; executar `cd Backend && ./gradlew test`.

### 3. Validação de prioridade no frontend

- [ ] **O que será feito:** Validar a entrada da prioridade com `^[1-5]$` sem convertê-la antes da validação e exibir uma mensagem clara se não corresponder. Aplicar a regra ao criar e editar, impedindo persistência inválida.
- **Arquivos afetados:** `Frontend/src/js/model/Tarefa.js`, `Frontend/src/js/components/tarefa.js` e, se necessário, o campo de prioridade nos componentes de formulário em `Frontend/src/components/tarefa.js`.
- **Como validar:** Teste manual no navegador: criar e editar tarefas com prioridades `1`, `3`, `5`, vazia, `0`, `6`, decimal, negativa e com caracteres extras; verificar mensagem e ausência de gravação em `localStorage` nos casos inválidos.

### 4. Validação de data no frontend

- [ ] **O que será feito:** Validar `YYYY-MM-DD` com Regex, confirmar que a data existe no calendário e rejeitar datas anteriores ao dia atual, permitindo hoje e datas futuras. Exibir mensagens claras e não salvar alterações inválidas.
- **Arquivos afetados:** `Frontend/src/js/model/Tarefa.js`, `Frontend/src/js/components/tarefa.js` e, se necessário, o campo de data nos componentes de formulário em `Frontend/src/components/tarefa.js`.
- **Como validar:** Teste manual no navegador: criar e editar tarefas com data válida futura, hoje, formato incorreto, dia inexistente e data passada; confirmar a mensagem e que os dados existentes permanecem inalterados quando a validação falha.

### 5. Verificação integrada dos fluxos

- [ ] **O que será feito:** Revisar os fluxos de criação e edição nas duas implementações para confirmar que as validações acontecem antes da persistência, que cada camada apresenta o tratamento de erro previsto e que os dados válidos continuam funcionando.
- **Arquivos afetados:** Testes Spock em `Backend/src/test/groovy/com/zg/todolist/` e, caso a execução revele falhas, somente os arquivos de implementação das tarefas anteriores.
- **Como validar:** Executar `cd Backend && ./gradlew test`; no frontend, servir `Frontend/` com `python3 -m http.server 8000`, abrir `http://localhost:8000/src/pages/signIn.html` e repetir os testes manuais de criação e edição para prioridade e data.
