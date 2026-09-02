class Usuario {
    constructor(nome, email, senha) {
        const usuarios = JSON.parse(localStorage.getItem('usuarios_db') || '[]')
        const existe = usuarios.some(u => u.email === email)
        if (existe) {
             throw new Error('Email ja cadastrado')
        }

        this.nome = nome
        this.email = email
        this.senha = senha
        this.tarefas = []
    }
}