
const sidebar = document.getElementById('sidebar');
const toggleBtn = document.getElementById('sidebarCollapse');
const body = document.body;
const wrapper = document.querySelector('.wrapper');

toggleBtn.addEventListener('click', () => {
    sidebar.classList.toggle('active');
    body.classList.toggle('sidebar-open');
    wrapper.classList.toggle('sidebar-active');
});

// Optional: Close sidebar when clicking outside on mobile
document.addEventListener('click', (e) => {
    if (window.innerWidth <= 768) {
        if (!sidebar.contains(e.target) && !toggleBtn.contains(e.target) && sidebar.classList.contains('active')) {
            sidebar.classList.remove('active');
            body.classList.remove('sidebar-open');
            wrapper.classList.remove('sidebar-active');
        }
    }
});

// Optional: Store sidebar state
const savedState = localStorage.getItem('sidebarOpen');
if (savedState === 'true') {
    sidebar.classList.add('active');
    body.classList.add('sidebar-open');
    wrapper.classList.add('sidebar-active');
}

toggleBtn.addEventListener('click', () => {
    const isOpen = sidebar.classList.contains('active');
    localStorage.setItem('sidebarOpen', isOpen);
});


