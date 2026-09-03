export class Tarefa {
    constructor(nome, descricao, dataTermino, categoria, prioridade, status, emailUsuario) {
        const tarefas = JSON.parse(localStorage.getItem('tarefas_db') || '[]')
        const existe = tarefas.some(t => t.nome === nome && t.emailUsuario === emailUsuario)
        if (existe) {
            throw new Error('Nome de tarefa já existente para este usuário\nEscolha outro nome')
        }
        if (prioridade < 1 || prioridade > 5) { 
             throw new Error('Prioridade invalida\n Deve estar entre 1 e 5')
        }
        this.nome = nome
        this.descricao = descricao
        this.dataTermino = dataTermino
        this.categoria = categoria
        this.prioridade = prioridade
        this.status = status
        this.emailUsuario = emailUsuario
    }
}