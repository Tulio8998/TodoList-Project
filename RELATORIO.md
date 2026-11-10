# Relatório da feature: validação de tarefas

## Desvios e Correções

- **Instruções ambíguas sobre as tarefas 3 e 4:** os pedidos mencionaram backend, mas o plano aprovado definia prioridade e data no frontend. A discrepância foi apontada e a implementação seguiu o plano: as regras correspondentes já tinham sido implementadas no backend nas tarefas 1 e 2.
- **Teste manual do frontend após o commit:** a validação manual no navegador foi solicitada depois do commit da tarefa 4. Foi então executada contra o commit existente, cobrindo data válida, data inválida, mensagem e preservação do dado persistido; nenhum commit adicional foi necessário.
- **Ressalva identificada na revisão final do backend:** `TarefaService.salvarTarefa` e `setDataTermino` rejeitam datas passadas, mas o construtor Java que recebe `LocalDate` ainda aceita diretamente uma data passada. A observação foi comunicada como possível correção; não foi alterada nesta execução para manter o escopo do relatório. Se o construtor for considerado uma entrada pública de criação, recomenda-se fechar essa lacuna em uma alteração própria, preservando a capacidade de carregar dados históricos conforme a regra fora de escopo da spec.

## Verificações registradas

- Backend: `cd Backend && ./gradlew test --rerun-tasks` — passou.
- Frontend: `cd Frontend && node --test src/js/model/Tarefa.test.js` — 10 testes passaram.
- Frontend: `node --check` nos scripts de componentes — passou.
- Interface: verificações manuais no navegador confirmaram aceitação de data atual e rejeição de datas inválidas/passadas sem persistir alterações inválidas.
