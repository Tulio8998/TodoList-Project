class BuscarTarefa extends HTMLElement{
    connectedCallback() {
        this.innerHTML = `
            <header class="search-bar">
                <div class="search-content">
                    <input class="input-search" type="text" placeholder="Buscar tarefa">
                </div>
                <a href="#" class="logout-link">Sair da Conta</a>
            </header>
        `;
        
        this.querySelector(".logout-link").addEventListener("click", (e) => {
            e.preventDefault();
            window.location.href = "signIn.html";
        });
    }
}
customElements.define("search-bar", BuscarTarefa)

class AlarmeTarefa extends HTMLElement{
    connectedCallback() {
        this.innerHTML = `
            <div class="alarm-class">
                <div class="alarm-container"></div>
            </div>
        `;
    }
}

customElements.define("alarm-class", AlarmeTarefa)

class AdicionarTarefa extends HTMLElement{
    connectedCallback() {
        this.innerHTML = `
            <div class="panel-new">
                <div class="panel-class">
                    <div class="panel-content" style="position: relative;">
                        <button class="close-panel" type="button">
                            x
                        </button>
                        <form action="">
                            <div class="input-data">
                                <div class="input-group">
                                    <p>Nome:</p>
                                    <input class="input-tarefa" type="text" placeholder="Nome da tarefa">
                                </div>
                                <div class="input-group">
                                    <p>Data de término:</p>
                                    <input class="input-tarefa" type="date">
                                </div>
                                <div class="input-group input-description">
                                    <p>Descrição:</p>
                                    <textarea class="input-tarefa input-desc" placeholder="Descrição"></textarea>
                                </div>
                                <div class="input-group">
                                    <p>Categoria:</p>
                                    <input class="input-tarefa" type="text" placeholder="Categoria">
                                </div>
                                <div class="input-group">
                                    <p>Prioridade:</p>
                                    <input class="input-tarefa" type="number" placeholder="1 a 5" min="1" max="5">
                                </div>
                                <div class="input-group">
                                    <p>Status:</p>
                                    <div class="status">
                                        <label>
                                            <input type="radio" name="status" value="TODO">
                                            TODO
                                        </label>
                                        <label>
                                            <input type="radio" name="status" value="DOING">
                                            DOING
                                        </label>
                                        <label>
                                            <input type="radio" name="status" value="DONE">
                                            DONE
                                        </label>
                                    </div>
                                </div>
                                <div class="input-group">
                                    <p>Alarmes:</p>
                                    <div class="alarmes-container">
                                        <div class="alarme-item">
                                            <input class="input-tarefa" type="datetime-local">
                                            <button type="button" class="btn-remove-alarme">
                                                x
                                            </button>
                                        </div>
                                    </div>
                                    <button type="button" class="btn-add-alarme">
                                        + Adicionar outro alarme
                                    </button>
                                </div>
                            </div>
                            <div class="button-class">
                                <button type="submit">Salvar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        `;

        const closeButton = this.querySelector(".close-panel");
        closeButton.addEventListener("click", () => {
            this.querySelector(".panel-new").style.display = "none";
        });

        const btnAddAlarme = this.querySelector(".btn-add-alarme");
        const alarmesContainer = this.querySelector(".alarmes-container");
        
        const btnRemoveInicial = this.querySelector(".btn-remove-alarme");
        if (btnRemoveInicial) {
            btnRemoveInicial.addEventListener("click", (evento) => {
                evento.target.parentElement.remove();
            });
        }
        
        btnAddAlarme.addEventListener("click", () => {
            const wrapperDiv = document.createElement("div");
            wrapperDiv.className = "alarme-item";

            const novoInput = document.createElement("input");
            novoInput.className = "input-tarefa";
            novoInput.type = "datetime-local";
            
            const btnRemove = document.createElement("button");
            btnRemove.type = "button";
            btnRemove.className = "btn-remove-alarme";
            btnRemove.innerText = "x";

            btnRemove.addEventListener("click", () => {
                wrapperDiv.remove();
            });

            wrapperDiv.appendChild(novoInput);
            wrapperDiv.appendChild(btnRemove);
            
            alarmesContainer.appendChild(wrapperDiv);
        });
    }
}

customElements.define("painel-new", AdicionarTarefa)





class VisualizarTarefa extends HTMLElement{
    connectedCallback() {
        this.innerHTML = `
            <div class="panel-view">
                <div class="panel-class">
                    <div class="panel-content" style="position: relative;">
                        <button class="close-panel" type="button">
                            x
                        </button>
                        <form action="">
                            <div class="input-data">
                                <div class="input-group">
                                    <p>Nome:</p>
                                    <input class="input-tarefa" type="text" placeholder="Nome da tarefa">
                                </div>
                                <div class="input-group">
                                    <p>Data de término:</p>
                                    <input class="input-tarefa" type="date">
                                </div>
                                <div class="input-group input-description">
                                    <p>Descrição:</p>
                                    <textarea class="input-tarefa input-desc" placeholder="Descrição"></textarea>
                                </div>
                                <div class="input-group">
                                    <p>Categoria:</p>
                                    <input class="input-tarefa" type="text" placeholder="Categoria">
                                </div>
                                <div class="input-group">
                                    <p>Prioridade:</p>
                                    <input class="input-tarefa" type="number" placeholder="1 a 5" min="1" max="5">
                                </div>
                                <div class="input-group">
                                    <p>Status:</p>
                                    <div class="status">
                                        <label>
                                            <input type="radio" name="status" value="TODO">
                                            TODO
                                        </label>
                                        <label>
                                            <input type="radio" name="status" value="DOING">
                                            DOING
                                        </label>
                                        <label>
                                            <input type="radio" name="status" value="DONE">
                                            DONE
                                        </label>
                                    </div>
                                </div>
                                <div class="input-group">
                                    <p>Alarmes:</p>
                                    <div class="alarmes-container">
                                        <div class="alarme-item">
                                            <input class="input-tarefa" type="datetime-local">
                                            <button type="button" class="btn-remove-alarme">
                                                x
                                            </button>
                                        </div>
                                    </div>
                                    <button type="button" class="btn-add-alarme">
                                        + Adicionar outro alarme
                                    </button>
                                </div>
                            </div>
                            <div class="button-class" style="gap: 1rem;">
                                <button type="submit">Salvar</button>
                                <button type="button" class="btn-excluir">Excluir Tarefa</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        `;
        
        const closeButton = this.querySelector(".close-panel");
        closeButton.addEventListener("click", () => {
            this.querySelector(".panel-view").style.display = "none";
        });

        const btnAddAlarme = this.querySelector(".btn-add-alarme");
        const alarmesContainer = this.querySelector(".alarmes-container");
        
        const btnRemoveInicial = this.querySelector(".btn-remove-alarme");
        if (btnRemoveInicial) {
            btnRemoveInicial.addEventListener("click", (evento) => {
                evento.target.parentElement.remove();
            });
        }
        
        btnAddAlarme.addEventListener("click", () => {
            const wrapperDiv = document.createElement("div");
            wrapperDiv.className = "alarme-item";

            const novoInput = document.createElement("input");
            novoInput.className = "input-tarefa";
            novoInput.type = "datetime-local";
            
            const btnRemove = document.createElement("button");
            btnRemove.type = "button";
            btnRemove.className = "btn-remove-alarme";
            btnRemove.innerText = "x";

            btnRemove.addEventListener("click", () => {
                wrapperDiv.remove();
            });

            wrapperDiv.appendChild(novoInput);
            wrapperDiv.appendChild(btnRemove);
            
            alarmesContainer.appendChild(wrapperDiv);
        });
    }
}

customElements.define("painel-view", VisualizarTarefa)


class FiltrarTarefa extends HTMLElement {
    connectedCallback(){
        this.innerHTML = `
            <div class="filter-container">
                <button class="btn-filter" type="button">Filtros</button>
                <div class="filter-dropdown" style="display: none;">
                    <div class="filter-group">
                        <label>Categoria:</label>
                        <input type="text" placeholder="Categoria">
                    </div>
                    <div class="filter-group">
                        <label>Prioridade:</label>
                        <input type="number" min="1" max="5" placeholder="1 a 5">
                    </div>
                    <div class="filter-group">
                        <label>Status:</label>
                        <select>
                            <option value="">Todos</option>
                            <option value="TODO">TODO</option>
                            <option value="DOING">DOING</option>
                            <option value="DONE">DONE</option>
                        </select>
                    </div>
                    <button class="btn-apply-filter" type="button">Aplicar</button>
                </div>
            </div>
        `
                
        const btnFilter = this.querySelector(".btn-filter");
        const filterDropdown = this.querySelector(".filter-dropdown");
        
        btnFilter.addEventListener("click", () => {
            filterDropdown.style.display = filterDropdown.style.display === "none" ? "block" : "none";
        });
    }

    
}

customElements.define("filter-container", FiltrarTarefa)