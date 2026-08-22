let tarefas = [];
let currentEditId = null;

const cardsContainer = document.querySelector(".tarefa-cards");
const cardNewHtml = `
    <div class="card-new" id="card-new">
        <p>Adicionar tarefa +</p>
    </div>
`;

function renderTarefas(lista = tarefas) {
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
        card.dataset.id = tarefa.id;
        
        card.innerHTML = `
            <div class="background-up">
                <p>${tarefa.categoria}</p>
                <p>${tarefa.status}</p>
            </div>
            <div class="background-down">
                <p class="name-task">${tarefa.nome}</p>
            </div>
        `;
        
        card.addEventListener('click', () => abrirEdicao(tarefa));
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
    currentEditId = tarefa.id;
    const panelInner = document.querySelector("painel-view .panel-view");
    const form = document.querySelector("painel-view form");
    
    if (panelInner && form) {
        const inputs = form.querySelectorAll(".input-tarefa");
        inputs[0].value = tarefa.nome;
        inputs[1].value = tarefa.dataTermino;
        inputs[2].value = tarefa.descricao;
        inputs[3].value = tarefa.categoria;
        inputs[4].value = tarefa.prioridade;
        
        const radios = form.querySelectorAll('input[name="status"]');
        radios.forEach(radio => {
            radio.checked = (radio.value === tarefa.status);
        });

        const alarmesContainer = document.querySelector("painel-view .alarmes-container");
        alarmesContainer.innerHTML = '';
        
        if (tarefa.alarmes && tarefa.alarmes.length > 0) {
            tarefa.alarmes.forEach(alarmeObj => {
                adicionarCampoAlarme(alarmesContainer, alarmeObj.time);
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

        if (!nome || !dataTermino || !descricao || !categoria || !prioridade) {
            alert("Preencha todos os campos da tarefa para prosseguir.");
            return;
        }

        const statusChecked = formNew.querySelector('input[name="status"]:checked');
        const alarmesInputs = formNew.querySelectorAll(".alarmes-container input[type='datetime-local']");
        
        const alarmes = [];
        alarmesInputs.forEach(inp => { if(inp.value) alarmes.push({ time: inp.value, rang: false }); });

        const novaTarefa = {
            id: Date.now().toString(),
            nome: nome,
            dataTermino: dataTermino,
            descricao: descricao,
            categoria: categoria,
            prioridade: prioridade,
            status: statusChecked ? statusChecked.value : 'TODO',
            alarmes: alarmes
        };

        tarefas.push(novaTarefa);
        document.querySelector("painel-new .panel-new").style.display = "none";
        renderTarefas();
    });
}

const formView = document.querySelector("painel-view form");
if (formView) {
    formView.addEventListener("submit", (e) => {
        e.preventDefault();
        
        const index = tarefas.findIndex(t => t.id === currentEditId);
        if (index !== -1) {
            const inputs = formView.querySelectorAll(".input-tarefa");
            const nome = inputs[0].value.trim();
            const dataTermino = inputs[1].value;
            const descricao = inputs[2].value.trim();
            const categoria = inputs[3].value.trim();
            const prioridade = inputs[4].value;

            if (!nome || !dataTermino || !descricao || !categoria || !prioridade) {
                alert("Preencha todos os campos da tarefa para prosseguir.");
                return;
            }

            const statusChecked = formView.querySelector('input[name="status"]:checked');
            const alarmesInputs = formView.querySelectorAll(".alarmes-container input[type='datetime-local']");
            
            const alarmes = [];
            alarmesInputs.forEach(inp => { 
                const existingAlarm = tarefas[index].alarmes.find(a => a.time === inp.value);
                if (existingAlarm) {
                    alarmes.push(existingAlarm);
                } else {
                    if(inp.value) alarmes.push({ time: inp.value, rang: false }); 
                }
            });

            tarefas[index] = {
                ...tarefas[index],
                nome: nome,
                dataTermino: dataTermino,
                descricao: descricao,
                categoria: categoria,
                prioridade: prioridade,
                status: statusChecked ? statusChecked.value : 'TODO',
                alarmes: alarmes
            };
        }

        document.querySelector("painel-view .panel-view").style.display = "none";
        renderTarefas();
    });
}

const btnExcluir = document.querySelector(".btn-excluir");
if (btnExcluir) {
    btnExcluir.addEventListener("click", () => {
        tarefas = tarefas.filter(t => t.id !== currentEditId);
        document.querySelector("painel-view .panel-view").style.display = "none";
        renderTarefas();
    });
}

const inputSearch = document.querySelector(".input-search");
if (inputSearch) {
    inputSearch.addEventListener("input", (e) => {
        const term = e.target.value.toLowerCase().trim();
        if (!term) {
            renderTarefas();
            return;
        }
        
        const filtradas = tarefas.filter(t => t.nome.toLowerCase().includes(term));
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

        const tarefasFiltradas = tarefas.filter(tarefa => {
            let match = true;
            
            if (filterCategoria && (!tarefa.categoria || !tarefa.categoria.toLowerCase().includes(filterCategoria))) {
                match = false;
            }
            if (filterPrioridade && tarefa.prioridade !== filterPrioridade) {
                match = false;
            }
            if (filterStatus && tarefa.status !== filterStatus) {
                match = false;
            }
            
            return match;
        });

        renderTarefas(tarefasFiltradas);
        document.querySelector(".filter-dropdown").style.display = "none";
    });
}

setInterval(() => {
    const now = new Date();

    tarefas.forEach(tarefa => {
        if(tarefa.status === 'DONE') return;
        
        if(tarefa.alarmes) {
            tarefa.alarmes.forEach(alarme => {
                if (!alarme.rang) {
                    const alarmTime = new Date(alarme.time);
                    if (alarmTime <= now) {
                        alarme.rang = true;
                        dispararAlarme(tarefa.nome, alarme.time);
                    }
                }
            });
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
    
    alarmEl.querySelector(".close-alarm").addEventListener("click", () => {
        alarmEl.remove();
    });
    
    container.appendChild(alarmEl);
}

document.addEventListener("DOMContentLoaded", () => {
    renderTarefas();
});