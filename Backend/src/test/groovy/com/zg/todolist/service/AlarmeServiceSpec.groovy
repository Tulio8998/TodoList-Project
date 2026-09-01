package com.zg.todolist.service

import com.zg.todolist.model.Alarme
import com.zg.todolist.model.Status
import com.zg.todolist.model.Tarefa
import com.zg.todolist.repository.AlarmeRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class AlarmeServiceSpec extends Specification {
    AlarmeRepository alarmeRepository = Mock(AlarmeRepository)
    AlarmeService alarmeService = new AlarmeService(alarmeRepository)

    Tarefa tarefa1
    Tarefa tarefa2
    Alarme alarme1
    Alarme alarme2

    def setup() {
        tarefa1 = new Tarefa(
                "Tarefa 1",
                "Descricao 1",
                LocalDate.of(2026, 9, 10),
                1,
                "Estudo",
                Status.TODO
        )

        tarefa2 = new Tarefa(
                "Tarefa 2",
                "Descricao 2",
                LocalDate.of(2026, 9, 15),
                2,
                "Trabalho",
                Status.DOING
        )

        alarme1 = new Alarme(
                LocalDateTime.of(2026, 9, 10, 10, 0),
                tarefa1
        )

        alarme2 = new Alarme(
                LocalDateTime.of(2026, 9, 15, 14, 0),
                tarefa2
        )
    }

    def "deve monitorar alarmes"() {
        when: "o monitoramento dos alarmes é iniciado"
        alarmeService.monitoraAlarme()

        then: "o método deve iniciar o monitoramento sem bloquear a aplicação"
        noExceptionThrown()
    }

    def "deve verificar alarmes"() {
        when: "os alarmes são verificados"
        alarmeService.verificarAlarmes()

        then: "o repositorio deve fornecer os alarmes"
        1 * alarmeRepository.getAlarmes() >> [alarme1]
    }

    def "deve adicionar alarme"() {
        given: "uma tarefa configurada"
        def horario = LocalDateTime.of(2026, 9, 10, 10, 0)

        when: "um alarme é adicionado"
        alarmeService.adicionarAlarme(tarefa1, horario)

        then: "o repositorio deve adicionar o alarme"
        1 * alarmeRepository.adicionarAlarme(_)

        and: "a tarefa deve possuir o novo alarme"
        tarefa1.getAlarmes().size() == 1
        tarefa1.getAlarmes()[0].getHorarioAlarme() == horario
    }

    def "deve atualizar alarme"() {
        given: "uma tarefa com um alarme"
        tarefa1.getAlarmes().add(alarme1)

        and: "um novo horario"
        def novoHorario = LocalDateTime.of(2026, 9, 10, 12, 0)

        when: "o alarme é atualizado"
        alarmeService.atualizarAlarme(tarefa1, 0, novoHorario)

        then: "o horario do alarme deve ser atualizado"
        tarefa1.getAlarmes()[0].getHorarioAlarme() == novoHorario

        and: "o repositorio deve salvar o alarme"
        1 * alarmeRepository.salvarAlarme()
    }

    def "deve remover alarme"() {
        given: "uma tarefa com um alarme"
        tarefa1.getAlarmes().add(alarme1)

        and: "o alarme cadastrado no repositorio"
        def alarmes = [alarme1]
        alarmeRepository.getAlarmes() >> alarmes

        when: "o alarme é removido"
        alarmeService.removerAlarme(tarefa1, 0)

        then: "o alarme deve ser removido da tarefa"
        !tarefa1.getAlarmes().contains(alarme1)

        and: "o alarme deve ser removido do repositorio"
        !alarmes.contains(alarme1)

        and: "o repositorio deve salvar a alteração"
        1 * alarmeRepository.salvarAlarme()
    }

    def "deve remover todos os alarmes"() {
        when: "todos os alarmes de uma tarefa são removidos"
        alarmeService.removerTodosAlarmes(tarefa1)

        then: "o repositorio deve remover os alarmes da tarefa"
        1 * alarmeRepository.removerAlarmes(tarefa1)
    }
}