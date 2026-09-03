import { UsuarioService } from "./services/UsuarioService.js";
import { Usuario } from "./model/Usuario.js";

const usuarioService = new UsuarioService();

export function app() {
    const form = document.querySelector('.form-content form');
    if (!form) return;
    
    const isSignUp = document.querySelector('h2').innerText.toLowerCase().includes('cadastro');
    
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const inputs = form.querySelectorAll('input');
        
        if (isSignUp) {
            signUp(inputs);
        } else {
            signIn(inputs);
        }
    });
}

function signUp(inputs) {
    const nome = inputs[0].value.trim();
    const email = inputs[1].value.trim();
    const senha = inputs[2].value.trim();

    if (!nome || !email || !senha) {
        alert('Preencha todos os campos para prosseguir!');
        return;
    }

    try {
        const novoUsuario = new Usuario(nome, email, senha);
        usuarioService.adicionarUsuario(novoUsuario);
        alert('Cadastro realizado com sucesso');
        window.location.href = './signIn.html';
    } catch (error) {
        alert(error.message); 
    }
}

function signIn(inputs) {
    const email = inputs[0].value.trim();
    const senha = inputs[1].value.trim();

    if (!email || !senha) {
        alert('Preencha todos os campos para prosseguir!');
        return;
    }

    const usuarioEnc = usuarioService.buscarPorEmailSenha(email, senha);
    
    if (usuarioEnc) {
        localStorage.setItem('usuario_logado', JSON.stringify({
            email: usuarioEnc.email, 
            nome: usuarioEnc.nome
        }));
        
        window.location.href = './tarefas.html';
    } else {
        alert('Email ou senha invalidos');
    }
}