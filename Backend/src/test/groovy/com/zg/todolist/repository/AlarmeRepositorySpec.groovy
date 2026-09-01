package com.zg.todolist.repository

import com.zg.todolist.model.Alarme
import com.zg.todolist.model.Status
import com.zg.todolist.model.Tarefa
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class AlarmeRepositorySpec extends Specification {
    TarefaRepository tarefaRepository = new TarefaRepository()
    AlarmeRepository alarmeRepository = new AlarmeRepository()
    Tarefa tarefa1
    Alarme alarme1
    Alarme alarme2

    def setup() {
        tarefa1 = new Tarefa(
                "Tarefa 1",
                "Descricao 1",
                LocalDate.of(2026, 10, 1),
                1,
                "Categoria 1",
                Status.DOING
        )
        alarme1 = new Alarme(
                LocalDateTime.of(2026, 10, 1, 15, 30),
                tarefa1
        )
        alarme2 = new Alarme(
                LocalDateTime.of(2026, 10, 10, 20, 30),
                tarefa1
        )
    }

    def "deve adicionar um alarme"() {
        given: "uma tarefa configurado no repositorio"
        and: "um usuario configurado no repositorio"
        tarefaRepository.usuarioAtual("teste@email.com")
        alarmeRepository.usuarioAtual("teste@email.com", tarefaRepository.getTarefas())

        when: "um novo alarme é adicionado"
        tarefaRepository.salvarTarefa(tarefa1)
        alarmeRepository.adicionarAlarme(alarme1)

        then: "o alarme deve esta presente na lista de alarmes"
        alarmeRepository.getAlarmes().contains(alarme1)
    }

    def "deve remover um alarme"() {
        given: "um alarme é adicionado previamente"
        tarefaRepository.usuarioAtual("teste@email.com")
        alarmeRepository.usuarioAtual("teste@email.com", tarefaRepository.getTarefas())
        tarefaRepository.salvarTarefa(tarefa1)
        alarmeRepository.adicionarAlarme(alarme1)

        when: "o alarme é removida do repositorio"
        alarmeRepository.removerAlarmes(tarefa1)

        then: "o alarme nao deve esta presente na lista de alarmes"
        !tarefaRepository.getTarefas().contains(alarme1)
    }

    def "deve reotornar todos os alarmes"() {
        given: "dois alarmes adicionados no repositorio"
        tarefaRepository.usuarioAtual("teste@email.com")
        alarmeRepository.usuarioAtual("teste@email.com", tarefaRepository.getTarefas())
        tarefaRepository.salvarTarefa(tarefa1)
        alarmeRepository.adicionarAlarme(alarme1)
        alarmeRepository.adicionarAlarme(alarme2)

        when: "a lista de alarmes é chamada"
        def alarmes = alarmeRepository.getAlarmes()

        then: "todos os alarmes devem ser retornados"
        alarmes.size() == 2
        alarmes.contains(alarme1)
        alarmes.contains(alarme2)
    }
}
