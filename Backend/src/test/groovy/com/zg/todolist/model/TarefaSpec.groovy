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
}
