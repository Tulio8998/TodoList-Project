package com.zg.todolist.repository

import com.zg.todolist.model.Status
import com.zg.todolist.model.Tarefa
import spock.lang.Specification
import java.time.LocalDate

class TarefaRepositorySpec extends Specification {
    TarefaRepository tarefaRepository = new TarefaRepository()
    Tarefa tarefa1
    Tarefa tarefa2

    def setup() {
        tarefa1 = new Tarefa(
                "Tarefa 1",
                "Descricao 1",
                LocalDate.of(2026, 10, 1),
                1,
                "Categoria 1",
                Status.DOING
        )

        tarefa2 = new Tarefa(
                "Tarefa 2",
                "Descricao 2",
                LocalDate.of(2026, 10, 1),
                1,
                "Categoria 2",
                Status.TODO
        )
    }

    def "deve adicionar uma tarefa"() {
        given: "uma tarefa é configurado no repositorio"
        and: "um novo usuario configurado no repositorio"
        tarefaRepository.usuarioAtual("teste@email.com")

        when: "uma nova tarefa é adicionada"
        tarefaRepository.salvarTarefa(tarefa1)

        then: "a tarefa deve estar presente na lista de tarefas"
        tarefaRepository.getTarefas().contains(tarefa1)
    }

    def "deve remover uma tarefa"() {
        given: "uma tarefa adicionada previamente"
        tarefaRepository.usuarioAtual("teste@email.com")
        tarefaRepository.salvarTarefa(tarefa2)

        when: "a tarefa é removida do repositorio"
        tarefaRepository.removerTarefa(tarefa2)

        then: "a tarefa não deve estar presente na lista de tarefas"
        !tarefaRepository.getTarefas().contains(tarefa2)
    }

    def "deve atualizar uma tarefa"() {
        given: "uma tarefa adicionada previamente"
        tarefaRepository.usuarioAtual("teste@email.com")
        tarefaRepository.salvarTarefa(tarefa1)

        when: "a tarefa é atualizada no repositorio"
        tarefaRepository.atualizarTarefa(tarefa1)

        then: "a tarefa deve continuar presente no repostorio atualizada"
        tarefaRepository.getTarefas().contains(tarefa1)
    }

    def "deve retornar todas as tarefas"() {
        given: "duas tarefas adicionadas no repostiorio"
        tarefaRepository.usuarioAtual("teste@email.com")
        tarefaRepository.salvarTarefa(tarefa1)
        tarefaRepository.salvarTarefa(tarefa2)

        when: "a lista de tarefas é chamada"
        def tarefas= tarefaRepository.getTarefas()

        then: "todas as tarefas adicionadas devem ser retornadas"
        tarefas.size() == 2
        tarefas.contains(tarefa1)
        tarefas.contains(tarefa2)
    }

    def "deve listar as tarefas por status"() {
        given: "duas tarefas adicionadas com status diferentes"
        tarefaRepository.usuarioAtual("teste@email.com")
        tarefaRepository.salvarTarefa(tarefa1)
        tarefaRepository.salvarTarefa(tarefa2)

        when: "é buscado apenas as tarefas pendentes"
        def tarefas = tarefaRepository.listarPorStatus(Status.TODO)

        then: "somentes tarefas pendendetes deve retornar"
        tarefas.size() == 1
        tarefas.contains(tarefa2)
        !tarefas.contains(tarefa1)
    }
}
