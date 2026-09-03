export class TarefaService {
    dado = 'tarefas_db'
    
    buscarTodos() {
        return JSON.parse(localStorage.getItem(this.dado) || '[]')
    }

    buscarPorUsuario(email) {
        return this.buscarTodos().filter(t => t.emailUsuario === email)
    }

    buscarPorNome(nome, email) {
        return this.buscarPorUsuario(email).find(t => t.nome === nome)
    }

    adicionarTarefa(tarefa) {
        const tarefas = this.buscarTodos()
        tarefas.push(tarefa)
        localStorage.setItem(this.dado, JSON.stringify(tarefas))
    }

    removerTarefa(nome, email) {
        const tarefas = this.buscarTodos()
        const novasTarefas = tarefas.filter(t => !(t.nome === nome && t.emailUsuario === email))
        localStorage.setItem(this.dado, JSON.stringify(novasTarefas))
    }

    atualizarTarefa(tarefa) {
        const tarefas = this.buscarTodos()
        const index = tarefas.findIndex(t => t.nome === tarefa.nome && t.emailUsuario === tarefa.emailUsuario)
        if (index === -1) {
            throw new Error('Tarefa não encontrada')
        }
        tarefas[index] = tarefa
        localStorage.setItem(this.dado, JSON.stringify(tarefas))
    }
}