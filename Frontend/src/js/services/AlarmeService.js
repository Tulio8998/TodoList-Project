export class AlarmeService {
    dado = 'alarmes_db'

    buscarTodos() {
        return JSON.parse(localStorage.getItem(this.dado) || '[]')
    }

    adicionarAlarme(tarefa, horario) {
        const alarme = {
            horarioAlarme: horario,
            tarefa: tarefa
        }

        tarefa.alarmes.push(alarme)

        const alarmes = this.buscarTodos()
        alarmes.push(alarme)

        localStorage.setItem(this.dado, JSON.stringify(alarmes))
    }

    atualizarAlarme(tarefa, indexAlarme, novoHorario) {
        const alarme = tarefa.alarmes[indexAlarme]

        if (!alarme) {
            throw new Error('Alarme nao encontrado')
        }

        alarme.horarioAlarme = novoHorario

        const alarmes = this.buscarTodos()
        const index = alarmes.findIndex(a => a.tarefa.nome === tarefa.nome &&
                a.horarioAlarme === alarme.horarioAlarme)

        if (index !== -1) {
            alarmes[index] = alarme
        }

        localStorage.setItem(this.dado,JSON.stringify(alarmes))
    }

    removerAlarme(tarefa, indexAlarme) {
        const alarmeRemovido = tarefa.alarmes[indexAlarme]

        if (!alarmeRemovido) {
            throw new Error('Alarme não encontrado')
        }

        tarefa.alarmes.splice(indexAlarme, 1)

        const alarmes = this.buscarTodos()
        const index = alarmes.indexOf(alarmeRemovido)

        if (index !== -1) {
            alarmes.splice(index, 1)
        }

        localStorage.setItem(this.dado,JSON.stringify(alarmes))
    }

    removerTodosAlarmes(tarefa) {
        tarefa.alarmes = []
        const alarmes = this.buscarTodos()

        const novosAlarmes = alarmes.filter(alarme => alarme.tarefa.nome !== tarefa.nome)

        localStorage.setItem(this.dado,JSON.stringify(novosAlarmes))
    }

    buscarPorTarefa(tarefa) {
        const alarmes = this.buscarTodos()
        return alarmes.filter(alarme => alarme.tarefa.nome === tarefa.nome)
    }
}