export function app() {
    const form = document.querySelector('.form-content form');
    if (!form) return;
    
    const isSignUp = document.querySelector('h2').innerText.toLowerCase().includes('cadastro');
    
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const inputs = form.querySelectorAll('input');
        let valid = true;
        
        inputs.forEach(inp => {
            if (!inp.value.trim()) valid = false;
        });
        
        if (!valid) {
            alert('Preencha todos os campos para prosseguir.');
            return;
        }
        
        if (isSignUp) {
            window.location.href = './signIn.html';
        } else {
            window.location.href = './tarefas.html';
        }
    });
}