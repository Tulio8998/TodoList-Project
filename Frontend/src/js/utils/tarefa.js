const cardNew = document.getElementById("card-new");
if (cardNew) {
    cardNew.addEventListener("click", () => {
        const panelInner = document.querySelector("painel-new .panel-new");
        if (panelInner) {
            panelInner.style.display = "block";
        }
    });
}

const cards = document.querySelectorAll(".card");
cards.forEach(card => {
    card.addEventListener("click", () => {
        const panelInner = document.querySelector("painel-view .panel-view");
        if (panelInner) {
            panelInner.style.display = "block";
        }
    });
});