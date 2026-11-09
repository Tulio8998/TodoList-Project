# Especificação: validação de dados com Regex

## Contexto e objetivo

O projeto mantém implementações independentes da lista de tarefas no frontend web e no backend Java/terminal. Hoje, a prioridade é limitada numericamente no modelo, e a data de término já é tratada como `YYYY-MM-DD` (campo HTML `type="date"` e `LocalDate.parse` no Java), mas as entradas não são uniformemente validadas por Regex nem impedidas de usar datas passadas.

Esta feature padroniza a validação de prioridade e data de término nas duas camadas, impedindo valores inválidos antes de salvar ou atualizar uma tarefa e comunicando claramente a falha ao usuário ou chamador.

## Regras de negócio

1. **Prioridade**
   - Aceitar exclusivamente um único dígito de `1` a `5`, validado com Regex (`^[1-5]$`).
   - Rejeitar valores vazios, fora do intervalo, decimais, sinalizados ou com caracteres adicionais, mesmo que a conversão numérica resulte em um valor permitido.
   - Aplicar a regra tanto ao criar quanto ao editar uma tarefa, no frontend e no backend.

2. **Data de término**
   - Usar o formato ISO `YYYY-MM-DD`, já utilizado pelo campo HTML `type="date"` e pelo `LocalDate.parse` no backend.
   - Validar o formato textual com Regex (`^\d{4}-\d{2}-\d{2}$`) antes de aceitar o valor.
   - Além da Regex, validar que a data existe no calendário (por exemplo, rejeitar `2026-02-30`).
   - Rejeitar datas anteriores à data atual. A data de hoje é válida; a comparação deve considerar o dia local, sem diferença causada por conversão de fuso horário.
   - Aplicar as validações na criação e edição de tarefas, antes de persistir os dados.

3. **Tratamento e mensagens de erro**
   - No frontend, informar claramente qual campo está inválido e o valor esperado, sem salvar/atualizar a tarefa quando houver falha.
   - No backend, lançar uma exceção clara (`IllegalArgumentException` ou equivalente já adotado) indicando se a prioridade, o formato/data ou a regra de data passada falhou.
   - As regras devem ser aplicadas no modelo/regra de domínio, além da validação de entrada na interface/CLI, para não depender somente dos controles HTML ou do fluxo do menu.

## Critérios de aceite (Dado/Quando/Então)

### Prioridade

- **Dado** um formulário de tarefa no frontend, **quando** a prioridade for `1`, `3` ou `5` e a tarefa for salva ou atualizada, **então** a validação deve aceitar o valor e permitir a operação.
- **Dado** um formulário de tarefa, **quando** a prioridade estiver vazia, for `0`, `6`, decimal, negativa ou contiver letras/caracteres extras, **então** o frontend deve exibir uma mensagem clara e não deve persistir a tarefa.
- **Dado** uma tentativa de criar ou atualizar uma tarefa no backend, **quando** a prioridade não corresponder a um único dígito entre `1` e `5`, **então** o backend deve lançar uma exceção clara e não deve persistir a alteração.

### Data de término

- **Dado** uma tarefa, **quando** a data estiver no formato `YYYY-MM-DD`, representar uma data real e for hoje ou futura, **então** frontend e backend devem aceitá-la.
- **Dado** uma tarefa, **quando** a data estiver vazia, usar outro formato (como `dd/MM/yyyy`) ou representar uma data inexistente, **então** frontend e backend devem rejeitá-la; o frontend deve explicar o formato esperado e o backend deve lançar uma exceção clara.
- **Dado** uma tarefa, **quando** a data de término for anterior ao dia atual, **então** frontend e backend devem rejeitá-la antes de salvar/atualizar; o frontend deve informar que a data não pode estar no passado e o backend deve lançar uma exceção clara.
- **Dado** uma edição de tarefa existente, **quando** a nova prioridade ou data for inválida, **então** os dados previamente persistidos devem permanecer inalterados.

## Impacto no sistema

- **Backend:** regras no modelo `Tarefa` e validação/conversão da entrada de tarefa no `MenuTerminal`; adicionar/ajustar testes Spock para valores aceitos e rejeitados.
- **Frontend:** validação do modelo `Tarefa` e mensagens no fluxo de criação/edição em `src/js/components/tarefa.js`; adicionar testes se houver ou for estabelecido um mecanismo de testes JavaScript apropriado.
- **Persistência:** não alterar formatos CSV nem estrutura dos objetos armazenados no `localStorage`; entradas inválidas não podem gravar novos dados ou substituir dados válidos.
- **Compatibilidade:** manter `YYYY-MM-DD` como formato de entrada e saída da data nas implementações existentes.

## Fora do escopo

- Integrar o frontend ao backend ou unificar seus mecanismos de persistência.
- Alterar o formato de data já utilizado para `dd/MM/yyyy`.
- Validar outros campos da tarefa ou modificar as regras de alarmes.
- Refatorar arquitetura, telas ou fluxos não diretamente necessários para aplicar estas validações.
- Migrar, corrigir ou remover tarefas inválidas já existentes em CSV ou `localStorage`.
