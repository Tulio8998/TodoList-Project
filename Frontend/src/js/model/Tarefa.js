const PRIORIDADE_REGEX = /^[1-5]$/;
const DATA_TERMINO_REGEX = /^\d{4}-\d{2}-\d{2}$/;

export class Tarefa {
    constructor(nome, descricao, dataTermino, categoria, prioridade, status, emailUsuario) {
        this.prioridade = Tarefa.parsePrioridade(prioridade);
        this.dataTermino = Tarefa.parseDataTermino(dataTermino);

        const tarefas = JSON.parse(localStorage.getItem('tarefas_db') || '[]')
        const existe = tarefas.some(t => t.nome === nome && t.emailUsuario === emailUsuario)
        if (existe) {
            throw new Error('Nome de tarefa já existente para este usuário\nEscolha outro nome')
        }
        this.nome = nome
        this.descricao = descricao
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

    static parseDataTermino(dataTermino) {
        if (typeof dataTermino !== 'string' || !DATA_TERMINO_REGEX.test(dataTermino)) {
            throw new Error('Data de término inválida. Use o formato YYYY-MM-DD.')
        }

        const [ano, mes, dia] = dataTermino.split('-').map(Number);
        const data = new Date(0);
        data.setUTCHours(0, 0, 0, 0);
        data.setUTCFullYear(ano, mes - 1, dia);

        if (data.getUTCFullYear() !== ano || data.getUTCMonth() !== mes - 1 || data.getUTCDate() !== dia) {
            throw new Error('Data de término inválida. Informe uma data existente no formato YYYY-MM-DD.')
        }

        const hoje = new Date();
        const dataHoje = [
            String(hoje.getFullYear()).padStart(4, '0'),
            String(hoje.getMonth() + 1).padStart(2, '0'),
            String(hoje.getDate()).padStart(2, '0')
        ].join('-');

        if (dataTermino < dataHoje) {
            throw new Error('Data de término deve ser hoje ou uma data futura.')
        }

        return dataTermino;
    }
}