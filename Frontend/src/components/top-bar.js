class TopBar extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
            <header class="top-bar">
                <h1 class="title">Todo List</h1>
            </header>
        `;
    }
}

customElements.define("top-bar", TopBar);