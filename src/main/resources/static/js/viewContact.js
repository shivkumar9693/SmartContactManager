// Optional: JS to show a custom tooltip for About column if you want fancy tooltips
document.addEventListener("DOMContentLoaded", () => {
    const aboutCells = document.querySelectorAll(".about-text");

    aboutCells.forEach(cell => {
        cell.addEventListener("mouseenter", function() {
            const tooltip = document.createElement("div");
            tooltip.className = "custom-tooltip";
            tooltip.textContent = this.getAttribute("title");
            document.body.appendChild(tooltip);

            const rect = this.getBoundingClientRect();
            tooltip.style.top = (rect.top - tooltip.offsetHeight - 5) + "px";
            tooltip.style.left = rect.left + "px";

            this.addEventListener("mouseleave", () => tooltip.remove(), { once: true });
        });
    });
});
