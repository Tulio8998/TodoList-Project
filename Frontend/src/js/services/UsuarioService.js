export class UsuarioService {
    dado = 'usuarios_db'

    buscarTodos() {
        return JSON.parse(localStorage.getItem(this.dado) || '[]')
    }

    buscarPorEmail(email) {
        const usuarios = this.buscarTodos()
        return usuarios.find(u => u.email === email)
    }

    buscarPorEmailSenha(email, senha) {
        const usuarios = this.buscarTodos()
        return usuarios.find(u => u.email === email && u.senha === senha)
    }

    adicionarUsuario(usuario) {
        const usuarios = this.buscarTodos()

        if (this.buscarPorEmail(usuario.email)) {
            throw new Error('E-mail já cadastrado')
        }

        usuarios.push(usuario)

        localStorage.setItem(this.dado, JSON.stringify(usuarios))
    }

    removerUsuario(email) {
        const usuarios = this.buscarTodos()

        const novosUsuarios = usuarios.filter(usuario => usuario.email !== email)

        localStorage.setItem(this.dado, JSON.stringify(novosUsuarios))
    }

    atualizarUsuario(usuario) {
        const usuarios = this.buscarTodos()

        const index = usuarios.findIndex(u => u.email === usuario.email)

        if (index === -1) {
            throw new Error('Usuario não encontrado')
        }

        usuarios[index] = usuario
        localStorage.setItem(this.dado, JSON.stringify(usuarios))
    }
}