//contact form 
// Initialize variables
const aboutEditor = document.getElementById('aboutEditor');
const charCount = document.getElementById('charCount');
const colorPicker = document.getElementById('colorPicker');
const contactForm = document.getElementById('contactForm');
const imgFileInput = document.getElementById('imgfile');
const imagePreview = document.getElementById('imagePreview');
let selectedImageFile = null;

// Update character count
function updateCharCount() {
    const text = aboutEditor.innerText || '';
    const length = text.length;
    charCount.textContent = length;

    if (length > 1000) {
        charCount.style.color = '#e53e3e';
    } else {
        charCount.style.color = '#718096';
    }
}

// Apply text formatting
function applyFormatting(command, value) {
    document.execCommand(command, false, value);
    aboutEditor.focus();
}

// Handle color picker change
colorPicker.addEventListener('change', function(e) {
    applyFormatting('foreColor', e.target.value);
});

// Handle about editor input
aboutEditor.addEventListener('input', updateCharCount);

// Handle image file selection
imgFileInput.addEventListener('change', function(e) {
    const file = e.target.files[0];

    if (file && file.type.startsWith('image/')) {
        selectedImageFile = file;
        const reader = new FileReader();

        reader.onload = function(event) {
            imagePreview.innerHTML = `
                <img src="${event.target.result}" alt="Preview">
                <button type="button" class="remove-image" onclick="removeImage()">Remove</button>
            `;
            imagePreview.classList.add('show');
        };

        reader.readAsDataURL(file);
    }
});

// Remove selected image
function removeImage() {
    selectedImageFile = null;
    imgFileInput.value = '';
    imagePreview.innerHTML = '';
    imagePreview.classList.remove('show');
}

// Validate email format
function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

// Validate phone number format
function isValidPhone(phone) {
    const phoneRegex = /^[\d\s\-\+\(\)]+$/;
    return phoneRegex.test(phone);
}

// Show error message
function showError(fieldId, message) {
    const errorElement = document.getElementById(fieldId + 'Error');
    const inputElement = document.getElementById(fieldId);

    if (errorElement && inputElement) {
        errorElement.textContent = message;
        errorElement.classList.add('show');
        inputElement.classList.add('input-error');
    }
}

// Hide error message
function hideError(fieldId) {
    const errorElement = document.getElementById(fieldId + 'Error');
    const inputElement = document.getElementById(fieldId);

    if (errorElement && inputElement) {
        errorElement.textContent = '';
        errorElement.classList.remove('show');
        inputElement.classList.remove('input-error');
    }
}

// Clear all errors
function clearAllErrors() {
    const errorFields = ['name', 'email', 'phoneno'];
    errorFields.forEach(field => hideError(field));
}

// Add input event listeners to clear errors on typing
document.getElementById('name').addEventListener('input', function() {
    hideError('name');
});

document.getElementById('email').addEventListener('input', function() {
    hideError('email');
});

document.getElementById('phoneno').addEventListener('input', function() {
    hideError('phoneno');
});

// Validate form
function validateForm() {
    clearAllErrors();
    let isValid = true;

    // Validate name
    const name = document.getElementById('name').value.trim();
    if (!name) {
        showError('name', 'Name is required');
        isValid = false;
    }

    // Validate email
    const email = document.getElementById('email').value.trim();
    if (!email) {
        showError('email', 'Email is required');
        isValid = false;
    } else if (!isValidEmail(email)) {
        showError('email', 'Invalid email format');
        isValid = false;
    }

    // Validate phone number (optional, but validate format if provided)
    const phoneno = document.getElementById('phoneno').value.trim();
    if (phoneno && !isValidPhone(phoneno)) {
        showError('phoneno', 'Invalid phone number format');
        isValid = false;
    }

    // Check about character limit
    const aboutText = aboutEditor.innerText || '';
    if (aboutText.length > 1000) {
        alert('About section exceeds 1000 characters limit');
        isValid = false;
    }

    return isValid;
}

// Get form data
function getFormData() {
    return {
        name: document.getElementById('name').value.trim(),
        nickname: document.getElementById('nickname').value.trim(),
        email: document.getElementById('email').value.trim(),
        about: aboutEditor.innerHTML,
        work: document.getElementById('work').value.trim(),
        phoneno: document.getElementById('phoneno').value.trim(),
        imageFile: selectedImageFile
    };
}

// Reset form
function resetForm() {
    contactForm.reset();
    aboutEditor.innerHTML = '';
    updateCharCount();
    clearAllErrors();
    removeImage();
}

/*// Handle form submission
contactForm.addEventListener('submit', function(e) {
    e.preventDefault();

    if (validateForm()) {
        const formData = getFormData();
        console.log('Form submitted:', formData);

        // Display success message
        alert('Contact added successfully!');

        // Reset form
        resetForm();

        // Here you can add code to send data to your server
        // Example:
        // fetch('/api/contacts', {
        //     method: 'POST',
        //     headers: {
        //         'Content-Type': 'application/json',
        //     },
        //     body: JSON.stringify(formData)
        // })
        // .then(response => response.json())
        // .then(data => {
        //     console.log('Success:', data);
        //     alert('Contact added successfully!');
        //     resetForm();
        // })
        // .catch(error => {
        //     console.error('Error:', error);
        //     alert('Failed to add contact. Please try again.');
        // });
    }
});
*/
// Prevent pasting content that exceeds character limit
aboutEditor.addEventListener('paste', function(e) {
    e.preventDefault();
    const text = (e.clipboardData || window.clipboardData).getData('text/plain');
    const currentLength = (aboutEditor.innerText || '').length;

    if (currentLength + text.length > 1000) {
        const allowedLength = 1000 - currentLength;
        const truncatedText = text.substring(0, allowedLength);
        document.execCommand('insertText', false, truncatedText);
    } else {
        document.execCommand('insertText', false, text);
    }
});
document.addEventListener("DOMContentLoaded", function() {
    const flashMessages = document.querySelectorAll('.flash-message');
    flashMessages.forEach(msg => {
        // Show popup
        msg.classList.add('show');

        // Hide automatically after 3 seconds
        setTimeout(() => {
            msg.classList.remove('show');
            setTimeout(() => msg.remove(), 500); // remove from DOM after fade out
        }, 3000);
    });
});

 