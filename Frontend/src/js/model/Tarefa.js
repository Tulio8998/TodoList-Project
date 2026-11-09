const PRIORIDADE_REGEX = /^[1-5]$/;

export class Tarefa {
    constructor(nome, descricao, dataTermino, categoria, prioridade, status, emailUsuario) {
        this.prioridade = Tarefa.parsePrioridade(prioridade);

        const tarefas = JSON.parse(localStorage.getItem('tarefas_db') || '[]')
        const existe = tarefas.some(t => t.nome === nome && t.emailUsuario === emailUsuario)
        if (existe) {
            throw new Error('Nome de tarefa já existente para este usuário\nEscolha outro nome')
        }
        this.nome = nome
        this.descricao = descricao
        this.dataTermino = dataTermino
        this.categoria = categoria
        this.status = status
        this.emailUsuario = emailUsuario
    }

    static parsePrioridade(prioridade) {
        if (typeof prioridade !== 'string' || !PRIORIDADE_REGEX.test(prioridade)) {
            throw new Error('Prioridade inválida. Digite um único número entre 1 e 5.')
        }
        return Number(prioridade)
    }
}