package com.zg.todolist.service

import com.zg.todolist.model.Usuario
import com.zg.todolist.repository.UsuarioRepository
import spock.lang.Specification

class UsuarioServiceSpec extends Specification {
    UsuarioRepository usuarioRepository = Mock(UsuarioRepository)
    UsuarioService usuarioService = new UsuarioService(usuarioRepository)

    Usuario usuario1
    Usuario usuario2

    def setup() {
        usuario1 = new Usuario(
                "Usuario 1",
                "usuario1@usuario.com",
                "usuario123"
        )
        usuario2 = new Usuario(
                "Usuario 2",
                "usuario2@usuario.com",
                "usuario321"
        )
    }

    def "deve cadastrar um usuario"() {
        given: "um usuario que ainda nao esta cadastrado"
        usuarioRepository.buscarTodos() >> []

        when: "é cadastrado um usuario"
        def resultado = usuarioService.cadastrarUsuario(usuario1)

        then: "o repositorio deve receber o novo usuario"
        1 * usuarioRepository.adicionar(usuario1)

        and: "o cadastro deve ser realizado com sucesso"
        resultado
    }

    def "deve remover um usuario"() {
        given: "um usuario existente"
        usuarioRepository.buscarTodos() >> [usuario1]

        when: "é removido um usuario"
        def resultado = usuarioService.removerUsuario(usuario1)

        then: "o repositorio deve remover o usuario"
        1 * usuarioRepository.remover(usuario1)

        and: "o usuario deve ser removido com sucesso"
        resultado
    }

    def "deve permitir o usuario entrar"() {
        given: "um usuario existente"
        usuarioRepository.buscarUsuario(usuario1.email, usuario1.senha) >> usuario1

        when: "um usuario entra"
        def resultado = usuarioService.entrar(usuario1.email, usuario1.senha)

        then: "o usuario deve ser retornado"
        resultado == usuario1
    }

    def "deve listar todos os usuarios"() {
        given: "dois usuarios existentes"
        usuarioRepository.buscarTodos() >> [usuario1, usuario2]

        when: "é chamada a lista de usuarios"
        def usuarios = usuarioService.listarUsuarios()

        then: "deve retornar todos os usuarios"
        usuarios == [usuario1, usuario2]
    }
}