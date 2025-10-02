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

// Sync editor content with hidden input on form submit
   const form = document.getElementById('scmContactForm'); // your form id
   form.addEventListener('submit', function() {
       document.getElementById('aboutField').value = document.getElementById('aboutEditor').innerHTML;
   });

   // Optional: update character count live
   const editor = document.getElementById('aboutEditor');
   const charCount = document.getElementById('charCount');
   editor.addEventListener('input', () => {
       charCount.textContent = editor.innerText.length;
   });

   // Toolbar formatting function
   function applyFormatting(command) {
       if (command === 'insertUnorderedList' || command === 'insertOrderedList') {
           document.execCommand(command, false, null);
       } else if (command === 'bold' || command === 'italic' || command === 'underline') {
           document.execCommand(command, false, null);
       }
   }

   // Optional: color picker
   const colorPicker = document.getElementById('colorPicker');
   colorPicker.addEventListener('input', () => {
       document.execCommand('foreColor', false, colorPicker.value);
   });