package com.zg.todolist.service

import com.zg.todolist.model.Status
import com.zg.todolist.model.Tarefa
import com.zg.todolist.repository.AlarmeRepository
import com.zg.todolist.repository.TarefaRepository
import com.zg.todolist.repository.UsuarioRepository
import spock.lang.Specification

import java.time.LocalDate

class TarefaServiceSpec extends Specification {
    TarefaRepository tarefaRepository = Mock(TarefaRepository)
    AlarmeService alarmeService = Mock(AlarmeService)
    TarefaService tarefaService = new TarefaService(tarefaRepository, alarmeService)

    Tarefa tarefa1
    Tarefa tarefa2

    def setup() {
        tarefa1 = new Tarefa(
                "Tarefa 1",
                "Descrição 1",
                LocalDate.of(2026, 9, 10),
                1,
                "Estudo",
                Status.DOING
        )
        tarefa2 = new Tarefa(
                "Tarefa 2",
                "Descrição 2",
                LocalDate.of(2026, 9, 15),
                2,
                "Trabalho",
                Status.TODO
        )
    }

    def "deve salvar tarefa"() {
        given: "uma tarefa configurada"
        tarefaRepository.getTarefas() >> [tarefa1]

        when: "é salva uma tarefa"
        tarefaService.salvarTarefa(tarefa1)

        then: "o repositorio deve receber uma nova tarefa"
        1 * tarefaRepository.salvarTarefa(tarefa1)
    }

    def "deve remover tarefa"() {
        given: "uma tarefa previamente adicionada"

        when: "é removido uma tarefa"
        tarefaService.removerTarefa(tarefa1)

        then: "o repositorio deve remover uma tarefa"
        1 * tarefaRepository.removerTarefa(tarefa1)
    }

    def "deve atualizar tarefa"() {
        given: "uma tarefa previamente adicionada"

        when: "é atualizado uma tarefa"
        tarefaService.atualizarTarefa(tarefa1, 1 , "Tarefa 1.1")

        then: "a tarefa deve ser atualizada no repositorio"
        1 * tarefaRepository.atualizarTarefa(tarefa1)
    }

    def "deve rejeitar prioridade inválida sem persistir a atualização"() {
        when:
        tarefaService.atualizarTarefa(tarefa1, 5, "6")

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Prioridade inválida. Deve ser um único número entre 1 e 5."
        0 * tarefaRepository.atualizarTarefa(_)
        tarefa1.getPrioridade() == 1
    }

    def "deve validar prioridade como texto antes de atualizar"() {
        when:
        tarefaService.atualizarTarefa(tarefa1, 5, "01")

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Prioridade inválida. Deve ser um único número entre 1 e 5."
        0 * tarefaRepository.atualizarTarefa(_)
        tarefa1.getPrioridade() == 1
    }

    def "deve listar tarefa por prioridade"() {
        given: "duas tarefas previamente adicionadas"
        tarefaRepository.getTarefas() >> [tarefa1, tarefa2]

        when: "é chamado tarefas por prioridade 1"
        def resultado = tarefaService.listarPorPrioridade(1)

        then: "a deve retornar uma lista de tarefas de prioridade 1"
        resultado.contains(tarefa1)
    }

    def "deve listar tarefa por categoria"() {
        given: "duas tarefas previamente adicionadas"
        tarefaRepository.getTarefas() >> [tarefa1, tarefa2]

        when: "é chamado tarefas por categoria 1"
        def resultado = tarefaService.listarPorCategoria("Estudo")

        then: "a deve retornar uma lista de tarefas de categoria Estudo"
        resultado.contains(tarefa1)
    }

    def "deve listar tarefa por status"() {
        given: "duas tarefas previamente adicionadas"
        tarefaRepository.getTarefas() >> [tarefa1, tarefa2]

        when: "é chamado tarefas por status DOING"
        def resultado = tarefaService.listarPorStatus(Status.DOING)

        then: "a deve retornar uma lista de tarefas de status DOING"
        resultado.contains(tarefa1)
    }

    def "deve listar tarefas"() {
        given: "duas tarefas previamente adicionadas"
        tarefaRepository.getTarefas() >> [tarefa1, tarefa2]

        when: "é chamado a lista de tarefas"
        def tarefas = tarefaService.listarTarefas()

        then: "a deve retornar uma lista de tarefas"
        tarefas ==  [tarefa1, tarefa2]
    }

    def "deve contar tarefas por status"() {
        given: "duas tarefas previamente adicionadas"
        tarefaRepository.listarPorStatus(Status.TODO) >> [tarefa2]

        when: "é chamada a contagem de tarefas por status TODO"
        def resultado = tarefaService.contarTarefasStatus(Status.TODO)

        then: "deve retornar a quantidade de tarefas TODO"
        resultado == 1
    }
}
