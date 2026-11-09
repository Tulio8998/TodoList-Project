package com.zg.todolist.model

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class TarefaSpec extends Specification {
    def "deve aceitar prioridade entre 1 e 5 na criação"() {
        expect:
        new Tarefa("Tarefa", "Descrição", LocalDate.of(2026, 12, 1), prioridade, "Estudo", Status.TODO)
                .getPrioridade() == prioridade

        where:
        prioridade << [1, 2, 3, 4, 5]
    }

    @Unroll
    def "deve rejeitar prioridade #prioridade na criação com IllegalArgumentException"() {
        when:
        new Tarefa("Tarefa", "Descrição", LocalDate.of(2026, 12, 1), prioridade, "Estudo", Status.TODO)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Prioridade inválida. Deve ser um único número entre 1 e 5."

        where:
        prioridade << [-1, 0, 6, 10]
    }

    @Unroll
    def "deve rejeitar texto de prioridade '#prioridade' com IllegalArgumentException"() {
        when:
        Tarefa.parsePrioridade(prioridade)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Prioridade inválida. Deve ser um único número entre 1 e 5."

        where:
        prioridade << ["", "0", "6", "01", "+1", "1.0", "1a", " 1", "1 ", null]
    }

    def "deve rejeitar prioridade inválida no setter sem alterar o valor atual"() {
        given:
        def tarefa = new Tarefa("Tarefa", "Descrição", LocalDate.of(2026, 12, 1), 3, "Estudo", Status.TODO)

        when:
        tarefa.setPrioridade(6)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Prioridade inválida. Deve ser um único número entre 1 e 5."
        tarefa.getPrioridade() == 3
    }

    def "deve aceitar data de término no formato ISO para hoje e datas futuras"() {
        expect:
        def hoje = LocalDate.now()
        [hoje, hoje.plusDays(1), hoje.plusDays(30)].each { data ->
            assert Tarefa.parseDataTermino(data.toString()) == data
        }
    }

    @spock.lang.Unroll
    def "deve rejeitar formato de data inválido '#data' com IllegalArgumentException"() {
        when:
        Tarefa.parseDataTermino(data)

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Data de término inválida. Use o formato YYYY-MM-DD."

        where:
        data << ["", "09/11/2026", "2026-1-09", "2026/11/09", null]
    }

    def "deve rejeitar data inexistente com IllegalArgumentException"() {
        when:
        Tarefa.parseDataTermino("2026-02-30")

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Data de término inválida. Informe uma data existente no formato YYYY-MM-DD."
        exception.cause instanceof java.time.format.DateTimeParseException
    }

    def "deve rejeitar data passada com IllegalArgumentException"() {
        when:
        Tarefa.parseDataTermino(LocalDate.now().minusDays(1).toString())

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Data de término deve ser hoje ou uma data futura."
    }

    def "deve rejeitar data passada no setter sem alterar o valor atual"() {
        given:
        def dataValida = LocalDate.now().plusDays(1)
        def tarefa = new Tarefa("Tarefa", "Descrição", dataValida, 3, "Estudo", Status.TODO)

        when:
        tarefa.setDataTermino(LocalDate.now().minusDays(1))

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Data de término deve ser hoje ou uma data futura."
        tarefa.getDataTermino() == dataValida
    }
}
