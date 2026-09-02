class Tarefa {
    constructor(nome, descricao, dataTermino, categoria, prioridade, status) {
        const tarefas = JSON.parse(localStorage.getItem('tarefas_db'))
        const existe = tarefas.some(t => t.nome === nome)
        if (existe) {
            throw new Error('Nome de tarefa já existente\nEscolha outro nome')
        }
        if (prioridade < 1 || prioridade > 5) {
             throw new Error('Prioridade invalida\n Deve esta entre 1 e 5')
        }

        this.nome = nome
        this.descricao = descricao
        this.dataTermino = dataTermino
        this.categoria = categoria
        this.prioridade = prioridade
        this.status = status
        this.alarmes = []
    }
}