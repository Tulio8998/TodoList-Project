export class AlarmeService {
    dado = 'alarmes_db'
    
    buscarTodos() {
        return JSON.parse(localStorage.getItem(this.dado) || '[]')
    }
    
    buscarPorUsuario(email) {
        return this.buscarTodos().filter(a => a.emailUsuario === email)
    }
    
    buscarPorTarefa(nomeTarefa, email) {
        return this.buscarPorUsuario(email).filter(a => a.nomeTarefa === nomeTarefa)
    }

    adicionarAlarme(alarme) {
        const alarmes = this.buscarTodos()
        alarmes.push(alarme)
        localStorage.setItem(this.dado, JSON.stringify(alarmes))
    }

    removerPorTarefa(nomeTarefa, email) {
        const alarmes = this.buscarTodos()
        const novosAlarmes = alarmes.filter(a => !(a.nomeTarefa === nomeTarefa && a.emailUsuario === email))
        localStorage.setItem(this.dado, JSON.stringify(novosAlarmes))
    }

    atualizarAlarme(alarmeAtualizado) {
        const alarmes = this.buscarTodos()
        const index = alarmes.findIndex(a => 
            a.nomeTarefa === alarmeAtualizado.nomeTarefa && 
            a.horarioAlarme === alarmeAtualizado.horarioAlarme && 
            a.emailUsuario === alarmeAtualizado.emailUsuario
        )
        if (index !== -1) {
            alarmes[index] = alarmeAtualizado
            localStorage.setItem(this.dado, JSON.stringify(alarmes))
        }
    }
}