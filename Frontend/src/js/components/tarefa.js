import { TarefaService } from "../services/TarefaService.js";
import { Tarefa } from "../model/Tarefa.js";
import { AlarmeService } from "../services/AlarmeService.js";
import { Alarme } from "../model/Alarme.js";

const usuarioLog = JSON.parse(localStorage.getItem('usuario_logado'));
if (!usuarioLog) {
    window.location.href = 'signIn.html';
}

const tarefaService = new TarefaService();
const alarmeService = new AlarmeService();
let currentEditNome = null; 
const cardsContainer = document.querySelector(".tarefa-cards");

const cardNewHtml = `
    <div class="card-new" id="card-new">
        <p>Adicionar tarefa +</p>
    </div>
`;

function renderTarefas(lista = tarefaService.buscarPorUsuario(usuarioLog.email)) {
    if (!cardsContainer) return;
    
    cardsContainer.innerHTML = cardNewHtml;
    
    let countTodo = 0;
    let countDoing = 0;
    let countDone = 0;

    lista.forEach(tarefa => {
        if (tarefa.status === 'TODO') countTodo++;
        if (tarefa.status === 'DOING') countDoing++;
        if (tarefa.status === 'DONE') countDone++;

        const card = document.createElement('div');
        card.className = 'card';
        card.dataset.nome = tarefa.nome;

        card.innerHTML = `
            <div class="checkbox-status">
                <input class="input-status" type="checkbox" style="cursor: pointer;">
            </div>
            <div class="background-up">
                <p>${tarefa.categoria}</p>
                <p>${tarefa.status}</p>
            </div>
            <div class="background-down">
                <p class="name-task">${tarefa.nome}</p>
            </div>
        `;
        
        card.addEventListener('click', (e) => {
            const editStatus = document.querySelector(".tarefa-cards").classList.contains("edit-status");
            
            if (editStatus) {
                if (!e.target.classList.contains('input-status')) {
                    const check = card.querySelector('.input-status');
                    check.checked = !check.checked;
                }
                return;
            }
            abrirEdicao(tarefa);
        });
        cardsContainer.appendChild(card);
    });

    const statusCounts = document.querySelector('.status-counts p');
    if (statusCounts) {
        statusCounts.innerHTML = `<span class="count-todo">TODO: ${countTodo}</span> <span class="count-doing">DOING: ${countDoing}</span> <span class="count-done">DONE: ${countDone}</span>`;
    }

    const cardNew = document.getElementById("card-new");
    if (cardNew) {
        cardNew.addEventListener("click", () => {
            const panelInner = document.querySelector("painel-new .panel-new");
            if (panelInner) {
                const form = document.querySelector("painel-new form");
                form.reset();
                
                const alarmesContainer = document.querySelector("painel-new .alarmes-container");
                alarmesContainer.innerHTML = '';
                adicionarCampoAlarme(alarmesContainer, "");
                
                panelInner.style.display = "block";
            }
        });
    }
}

function adicionarCampoAlarme(container, valor = "") {
    const wrapperDiv = document.createElement("div");
    wrapperDiv.className = "alarme-item";
    const novoInput = document.createElement("input");
    novoInput.className = "input-tarefa";
    novoInput.type = "datetime-local";
    novoInput.value = valor;
    
    const btnRemove = document.createElement("button");
    btnRemove.type = "button";
    btnRemove.className = "btn-remove-alarme";
    btnRemove.innerText = "x";
    btnRemove.addEventListener("click", () => {
        wrapperDiv.remove();
    });

    wrapperDiv.appendChild(novoInput);
    wrapperDiv.appendChild(btnRemove);
    container.appendChild(wrapperDiv);
}

function abrirEdicao(tarefa) {
    currentEditNome = tarefa.nome;
    const panelInner = document.querySelector("painel-view .panel-view");
    const form = document.querySelector("painel-view form");
    
    if (panelInner && form) {
        const inputs = form.querySelectorAll(".input-tarefa");
        inputs[0].value = tarefa.nome;
        inputs[0].readOnly = true; 
        inputs[1].value = tarefa.dataTermino;
        inputs[2].value = tarefa.descricao;
        inputs[3].value = tarefa.categoria;
        inputs[4].value = tarefa.prioridade;
        
        const radios = form.querySelectorAll('input[name="status"]');
        radios.forEach(radio => {
            radio.checked = (radio.value === tarefa.status);
        });

        const alarmesDaTarefa = alarmeService.buscarPorTarefa(tarefa.nome, usuarioLog.email);
        const alarmesContainer = document.querySelector("painel-view .alarmes-container");
        alarmesContainer.innerHTML = '';
        
        if (alarmesDaTarefa.length > 0) {
            alarmesDaTarefa.forEach(alarmeObj => {
                adicionarCampoAlarme(alarmesContainer, alarmeObj.horarioAlarme);
            });
        } else {
            adicionarCampoAlarme(alarmesContainer, "");
        }

        panelInner.style.display = "block";
    }
}

const formNew = document.querySelector("painel-new form");
if (formNew) {
    formNew.addEventListener("submit", (e) => {
        e.preventDefault();
        
        const inputs = formNew.querySelectorAll(".input-tarefa");
        const nome = inputs[0].value.trim();
        const dataTermino = inputs[1].value;
        const descricao = inputs[2].value.trim();
        const categoria = inputs[3].value.trim();
        const prioridade = inputs[4].value;

        if (!nome || !descricao || !categoria || !prioridade) {
            alert("Preencha todos os campos da tarefa para prosseguir.");
            return;
        }

        const statusChecked = formNew.querySelector('input[name="status"]:checked');
        const alarmesInputs = formNew.querySelectorAll(".alarmes-container input[type='datetime-local']");
        
        try {
            const novaTarefa = new Tarefa(
                nome, descricao, dataTermino, categoria, prioridade,
                statusChecked ? statusChecked.value : 'TODO',
                usuarioLog.email
            );

            tarefaService.adicionarTarefa(novaTarefa);
            
            alarmesInputs.forEach(inp => { 
                if(inp.value) {
                    const novoAlarme = new Alarme(inp.value, nome, usuarioLog.email);
                    alarmeService.adicionarAlarme(novoAlarme);
                }
            });
            
            document.querySelector("painel-new .panel-new").style.display = "none";
            renderTarefas();
        } catch (error) {
            alert(error.message); 
        }
    });
}

const formView = document.querySelector("painel-view form");
if (formView) {
    formView.addEventListener("submit", (e) => {
        e.preventDefault();
        
        const tarefaEditada = tarefaService.buscarPorNome(currentEditNome, usuarioLog.email);
        
        if (tarefaEditada) {
            const inputs = formView.querySelectorAll(".input-tarefa");
            const dataTermino = inputs[1].value;
            const descricao = inputs[2].value.trim();
            const categoria = inputs[3].value.trim();
            const prioridade = inputs[4].value;

            if (!descricao || !categoria || !prioridade) {
                alert("Preencha todos os campos da tarefa para prosseguir.");
                return;
            }

            let prioridadeValidada;
            let dataTerminoValidada;
            try {
                prioridadeValidada = Tarefa.parsePrioridade(prioridade);
                dataTerminoValidada = Tarefa.parseDataTermino(dataTermino);
            } catch (error) {
                alert(error.message);
                return;
            }

            const statusChecked = formView.querySelector('input[name="status"]:checked');
            const alarmesInputs = formView.querySelectorAll(".alarmes-container input[type='datetime-local']");
            
            tarefaEditada.dataTermino = dataTerminoValidada;
            tarefaEditada.descricao = descricao;
            tarefaEditada.categoria = categoria;
            tarefaEditada.prioridade = prioridadeValidada;
            tarefaEditada.status = statusChecked ? statusChecked.value : 'TODO';
            tarefaService.atualizarTarefa(tarefaEditada);
            alarmeService.removerPorTarefa(currentEditNome, usuarioLog.email);
            
            alarmesInputs.forEach(inp => { 
                if(inp.value) {
                    const novoAlarme = new Alarme(inp.value, currentEditNome, usuarioLog.email);
                    alarmeService.adicionarAlarme(novoAlarme);
                }
            });
        }

        document.querySelector("painel-view .panel-view").style.display = "none";
        renderTarefas();
    });
}

const btnExcluir = document.querySelector(".btn-excluir");
if (btnExcluir) {
    btnExcluir.addEventListener("click", () => {
        if(currentEditNome) {
            tarefaService.removerTarefa(currentEditNome, usuarioLog.email);
            alarmeService.removerPorTarefa(currentEditNome, usuarioLog.email);
            document.querySelector("painel-view .panel-view").style.display = "none";
            renderTarefas();
        }
    });
}

const inputSearch = document.querySelector(".input-search");
if (inputSearch) {
    inputSearch.addEventListener("input", (e) => {
        const term = e.target.value.toLowerCase().trim();
        const tarefasDoUsuario = tarefaService.buscarPorUsuario(usuarioLog.email);
        
        if (!term) {
            renderTarefas(tarefasDoUsuario);
            return;
        }
        
        const filtradas = tarefasDoUsuario.filter(t => t.nome.toLowerCase().includes(term));
        renderTarefas(filtradas);
    });
}

const btnApplyFilter = document.querySelector(".btn-apply-filter");
if (btnApplyFilter) {
    btnApplyFilter.addEventListener("click", () => {
        const container = document.querySelector("filter-container");
        const inputs = container.querySelectorAll("input");
        const select = container.querySelector("select");

        const filterCategoria = inputs[0].value.trim().toLowerCase();
        const filterPrioridade = inputs[1].value;
        const filterStatus = select.value;

        const tarefasFiltradas = tarefaService.buscarPorUsuario(usuarioLog.email).filter(tarefa => {
            let match = true;
            if (filterCategoria && (!tarefa.categoria || !tarefa.categoria.toLowerCase().includes(filterCategoria))) match = false;
            if (filterPrioridade && parseInt(tarefa.prioridade) !== parseInt(filterPrioridade)) match = false;
            if (filterStatus && tarefa.status !== filterStatus) match = false;
            return match;
        });

        renderTarefas(tarefasFiltradas);
        document.querySelector(".filter-dropdown").style.display = "none";
    });
}

setInterval(() => {
    const now = new Date();
    const alarmesDoUsuario = alarmeService.buscarPorUsuario(usuarioLog.email);
    
    alarmesDoUsuario.forEach(alarme => {
        if (!alarme.rang) {
            const alarmTime = new Date(alarme.horarioAlarme);
            if (alarmTime <= now) {
                const tarefaRef = tarefaService.buscarPorNome(alarme.nomeTarefa, usuarioLog.email);
                
                if (tarefaRef && tarefaRef.status !== 'DONE') {
                    alarme.rang = true;
                    dispararAlarme(alarme.nomeTarefa, alarme.horarioAlarme);
                    
                    alarmeService.atualizarAlarme(alarme);
                }
            }
        }
    });
}, 1000);

function dispararAlarme(nomeTarefa, horario) {
    const container = document.querySelector(".alarm-container");
    if (!container) return;
    
    const alarmEl = document.createElement("div");
    alarmEl.className = "alarm-triggered";
    alarmEl.innerHTML = `
        <div>
            <p class="alarm-title">Lembrete: ${nomeTarefa}</p>
            <small class="alarm-time">${horario.replace('T', ' ')}</small>
        </div>
        <button class="close-alarm" type="button">x</button>
    `;
    alarmEl.querySelector(".close-alarm").addEventListener("click", () => alarmEl.remove());
    container.appendChild(alarmEl);
}

document.addEventListener("DOMContentLoaded", () => {
    const titleBar = document.querySelector(".title");
    if(titleBar && usuarioLog) {
        titleBar.innerText = `Todo List - ${usuarioLog.nome}`;
    }
    renderTarefas();
});


document.addEventListener("applyEditStatus", (e) => {
    const { nomes, novoStatus } = e.detail;
    nomes.forEach(nome => {
        const tarefa = tarefaService.buscarPorNome(nome, usuarioLog.email);
        if (tarefa) {
            tarefa.status = novoStatus;
            tarefaService.atualizarTarefa(tarefa);
        }
    });
    
    renderTarefas();
});