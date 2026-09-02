export class TarefaService {
    dado = 'tarefas_db'

    buscarTodos() {
        return JSON.parse(localStorage.getItem(this.dado) || [])
    }

    buscarPorNome(nome) {
        const tarefas = this.buscarTodos()
        return tarefas.find(t => t.nome === nome)
    }

    adicionarTarefa(tarefa){
        const tarefas = this.buscarTodos()

        if (this.buscarPorNome(tarefa.nome)) {
            throw new Error('Nome de tarefa já existente\nEscolha outro nome') 
        }

        tarefas.push(tarefa)

        localStorage.setItem(this.dado, JSON.stringify(tarefas))
    }

    removerTarefa(nome){
        const tarefas = this.buscarTodos()
        const novasTarefas = tarefas.filter(t => t.nome !== nome)

        localStorage.setItem(this.dado, JSON.stringify(novasTarefas))
    }

    atualizarTarefa(tarefa){
        const tarefas = this.buscarTodos()

        const index = tarefas.findIndex(t => t.nome === tarefa.nome)

        if (index === -1) {
            throw new Error('Tarefa não encontrada')
        }

        tarefas[index] = tarefa

        localStorage.setItem(this.dado, JSON.stringify(tarefas))
    }

    buscarPorStatus(status){
        const tarefas = this.buscarTodos()
        return tarefas.find(t => t.status === status)
    }

    buscarPorCategoria(categoria){
        const tarefas = this.buscarTodos()
        return tarefas.find(t => t.categoria === categoria)
    }

    buscarPorPrioridade(prioridade){
        const tarefas = this.buscarTodos()
        return tarefas.find(t => t.prioridade === prioridade)
    }

    contarStatus(status){
        const tarefas = this.buscarPorStatus()
        return tarefas.size()
    }
}