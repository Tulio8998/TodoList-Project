package com.zg.todolist.repository

import com.zg.todolist.model.Usuario
import spock.lang.Specification

class UsuarioRepositoryTest extends Specification {
    UsuarioRepository usuarioRepository = new UsuarioRepository()
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

    def "deve adicionar usuario"() {
        given: "um usuario é configurado no repositorio"

        when: "um novo usuario é adicionado"
        usuarioRepository.adicionar(usuario1)

        then: "o usuario deve estar presente na lista de usuarios"
        usuarioRepository.buscarTodos().contains(usuario1)
    }

    def "deve remover usuario"() {
        given: "um usuario é adicionado previamente"
        usuarioRepository.adicionar(usuario1)

        when: "um é removido usuario do repositorio"
        usuarioRepository.remover(usuario1)

        then: "o usuario não deve estar presente na lista de usuarios"
        !usuarioRepository.buscarTodos().contains(usuario1)
    }

    def "deve buscar usuario"() {
        given: "dois usuarios sao adicionados"
        usuarioRepository.adicionar(usuario1)
        usuarioRepository.adicionar(usuario2)

        when: "é buscado um certo usuario"
        usuarioRepository.buscarUsuario(usuario1.email, usuario1.senha)

        then: "o usuario deve estar presente no retorno da busca"
        usuarioRepository.buscarTodos().contains(usuario1)
    }

    def "deve buscar todos usuarios"() {
        given: "dois usuarios sao adicionados"
        usuarioRepository.adicionar(usuario1)
        usuarioRepository.adicionar(usuario2)

        when: "é chamado uma lista de usuarios"
        def usuarios = usuarioRepository.buscarTodos()

        then: "todos os usuarios adicionados sao retornados"
        usuarios.size() == 2
        usuarios.contains(usuario1)
        usuarios.contains(usuario2)
    }
}
