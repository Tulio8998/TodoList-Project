---
name: feature-com-spec
description: 'Use ao conduzir desenvolvimento de features com Spec-Driven Development (SDD): criar e aprovar uma especificação, planejar tarefas pequenas e executar implementação iterativa com testes e commits locais.'
---

# Desenvolvimento guiado por especificação

## Objetivo

Conduzir uma feature do requisito até a implementação verificada, mantendo rastreabilidade entre especificação, plano, mudanças de código, testes e commits. O fluxo é:

**Spec → aprovação → Plano → aprovação → Implementação iterativa → Validação final**

## Entradas necessárias

- Descrição da feature, problema ou resultado esperado.
- Repositório e contexto de arquitetura, convenções e comandos de teste; leia `AGENTS.md` ou instruções equivalentes antes de alterar arquivos.
- Regras de negócio, critérios de aceite e limites conhecidos. Se faltarem detalhes importantes, esclareça-os antes de fixá-los na spec; não invente requisitos.
- Confirmação do usuário para avançar entre os marcos de especificação, plano e execução, quando o fluxo solicitado exigir aprovação.

## Passo a passo

1. **Reconhecer o projeto.** Leia as instruções locais e explore os arquivos relevantes para entender stack, camadas, persistência, testes e pontos de entrada da feature. Confira o estado do Git; preserve alterações preexistentes.
2. **Escrever a especificação.** Crie `docs/spec.md`, fundamentada no código e nos requisitos. Separe regras de negócio, critérios observáveis e itens explicitamente fora do escopo. Apresente o documento e aguarde aprovação antes de iniciar o plano ou a implementação, conforme solicitado.
3. **Escrever o plano.** Depois da aprovação da spec, crie `docs/plano.md` com tarefas numeradas pequenas e independentes sempre que possível. Cada tarefa deve ter checkbox `[ ]`, descrição do trabalho, arquivos/superfícies afetados e comando ou procedimento de validação. Inclua testes e uma verificação integrada final. Apresente o plano e aguarde aprovação quando solicitada.
4. **Executar uma tarefa por vez.**
   - Consulte o plano e o estado atual do repositório antes de cada tarefa.
   - Implemente somente o escopo da tarefa selecionada. Cubra comportamento válido, inválido e efeitos sobre persistência com os testes apropriados ao projeto.
   - Rode primeiro a validação direcionada; se passar, rode a suíte solicitada ou indicada no plano. Se falhar, investigue, corrija e rode novamente.
   - Para mudanças de interface, faça também a validação manual no navegador antes do commit: exercite caminhos válidos e inválidos, confira mensagens e persistência e limpe dados de teste.
   - Só depois de verificar os resultados, atualize a tarefa concluída para `[x]`, revise o diff e faça um commit local claro para essa tarefa. Não avance para outra tarefa sem nova instrução do usuário quando o trabalho for explicitamente parcelado dessa forma.
5. **Validar a integração.** Após as tarefas de implementação, execute as suítes relevantes do backend e frontend e revise consistência entre camadas, formatos, mensagens, criação/edição e persistência. Resolva regressões dentro do escopo antes de declarar o fluxo concluído.
6. **Relatar o resultado.** Resuma alterações, evidências de teste e commits. Informe claramente limitações ou testes manuais pendentes para o usuário.

## Regras de execução

- Trabalhe em **uma tarefa do plano por vez**; mantenha o plano como fonte de acompanhamento e marque `[x]` apenas após a validação daquela tarefa passar.
- Faça commits **locais**, normalmente um por tarefa concluída. Nunca execute `git push`.
- Não altere arquivos ou comportamentos fora do escopo aprovado. Não faça refatorações não solicitadas.
- Preserve mudanças locais preexistentes e não inclua arquivos não relacionados no stage ou no commit.
- Siga padrões existentes de arquitetura, estilo, testes e ferramentas. Não introduza dependências ou infraestrutura de teste sem necessidade aprovada.
- Verifique os fluxos de criação e edição e assegure que dados inválidos não sejam persistidos nem sobrescrevam dados válidos.
- Se a validação falhar, não marque a tarefa como concluída nem faça commit como se estivesse pronta.

## Estrutura base de `docs/spec.md`

```markdown
# Especificação: <nome da feature>

## Contexto e objetivo

<Problema atual, contexto do sistema e resultado pretendido.>

## Regras de negócio

1. **<Regra>**
   - <Comportamentos válidos e inválidos.>
   - <Quando e em quais camadas se aplica.>

## Critérios de aceite (Dado/Quando/Então)

- **Dado** <contexto>, **quando** <ação>, **então** <resultado observável>.

## Impacto no sistema

- **<Camada ou componente>:** <código, persistência, interfaces e testes afetados>.

## Fora do escopo

- <Exclusões explícitas.>
```

Adapte a quantidade de regras e critérios à feature. Use formatos e comportamentos comprovados no projeto, e explicite validações de entrada, domínio e persistência quando relevantes.

## Estrutura base de `docs/plano.md`

```markdown
# Plano de implementação: <nome da feature>

Este plano implementa a especificação em [spec.md](./spec.md).

## Tarefas

### 1. <Tarefa pequena e focada>

- [ ] **O que será feito:** <mudança e comportamento esperado>
- **Arquivos afetados:** `<caminhos>` e testes relacionados.
- **Como validar:** <comando direcionado/suíte ou roteiro manual observável>.

### 2. <Próxima tarefa>

- [ ] **O que será feito:** <mudança e comportamento esperado>
- **Arquivos afetados:** `<caminhos>` e testes relacionados.
- **Como validar:** <comando direcionado/suíte ou roteiro manual observável>.
```

Use em geral de quatro a seis tarefas, ajustadas ao tamanho real do trabalho. Inclua uma tarefa de validação integrada quando houver várias camadas. Atualize `[ ]` para `[x]` somente depois de implementar, testar e revisar a tarefa.
