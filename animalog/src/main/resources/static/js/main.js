/**
 * AnimaLog Pet Healthcare Management System
 * Main JavaScript File
 */

// ==========================================
// DOM READY
// ==========================================
document.addEventListener('DOMContentLoaded', function() {
    console.log('🐾 AnimaLog System Initialized');
    
    // Initialize all components
    initializeDeleteConfirmations();
    initializeAlerts();
    initializeFormValidation();
    initializeDateInputs();
    initializeTableSearch();
    initializeModals();
    initializeTooltips();
    initializeAnimations();
});

// ==========================================
// DELETE CONFIRMATIONS
// ==========================================
function initializeDeleteConfirmations() {
    const deleteLinks = document.querySelectorAll('a[href*="/delete/"], .btn-danger[href]');
    
    deleteLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            const confirmMessage = this.getAttribute('data-confirm') || 
                                  'Are you sure you want to delete this item? This action cannot be undone.';
            
            if (!confirm(confirmMessage)) {
                e.preventDefault();
                return false;
            }
        });
    });
}

// ==========================================
// ALERT AUTO-HIDE
// ==========================================
function initializeAlerts() {
    const alerts = document.querySelectorAll('.alert');
    
    alerts.forEach(alert => {
        // Add close button if not exists
        if (!alert.querySelector('.alert-close')) {
            const closeBtn = document.createElement('button');
            closeBtn.className = 'alert-close';
            closeBtn.innerHTML = '×';
            closeBtn.onclick = () => closeAlert(alert);
            alert.appendChild(closeBtn);
        }
        
        // Auto-hide after 5 seconds
        setTimeout(() => {
            closeAlert(alert);
        }, 5000);
    });
}

function closeAlert(alert) {
    alert.style.opacity = '0';
    alert.style.transform = 'translateX(20px)';
    setTimeout(() => {
        alert.remove();
    }, 300);
}

// ==========================================
// FORM VALIDATION
// ==========================================
function initializeFormValidation() {
    const forms = document.querySelectorAll('form');
    
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            // Remove previous error messages
            const existingErrors = form.querySelectorAll('.error-message');
            existingErrors.forEach(error => error.remove());
            
            // Validate required fields
            const requiredFields = form.querySelectorAll('[required]');
            let isValid = true;
            
            requiredFields.forEach(field => {
                if (!field.value.trim()) {
                    isValid = false;
                    showFieldError(field, 'This field is required');
                }
            });
            
            // Validate email fields
            const emailFields = form.querySelectorAll('input[type="email"]');
            emailFields.forEach(field => {
                if (field.value && !isValidEmail(field.value)) {
                    isValid = false;
                    showFieldError(field, 'Please enter a valid email address');
                }
            });
            
            // Validate phone fields
            const phoneFields = form.querySelectorAll('input[type="tel"], input[name*="phone"]');
            phoneFields.forEach(field => {
                if (field.value && !isValidPhone(field.value)) {
                    isValid = false;
                    showFieldError(field, 'Please enter a valid phone number');
                }
            });
            
            // Validate date fields
            const dateFields = form.querySelectorAll('input[type="date"]');
            dateFields.forEach(field => {
                if (field.value && field.hasAttribute('min')) {
                    const minDate = new Date(field.getAttribute('min'));
                    const selectedDate = new Date(field.value);
                    if (selectedDate < minDate) {
                        isValid = false;
                        showFieldError(field, 'Date cannot be in the past');
                    }
                }
            });
            
            // Validate number fields
            const numberFields = form.querySelectorAll('input[type="number"]');
            numberFields.forEach(field => {
                if (field.value) {
                    const min = field.getAttribute('min');
                    const max = field.getAttribute('max');
                    const value = parseFloat(field.value);
                    
                    if (min && value < parseFloat(min)) {
                        isValid = false;
                        showFieldError(field, `Value must be at least ${min}`);
                    }
                    if (max && value > parseFloat(max)) {
                        isValid = false;
                        showFieldError(field, `Value must be at most ${max}`);
                    }
                }
            });
            
            if (!isValid) {
                e.preventDefault();
                // Scroll to first error
                const firstError = form.querySelector('.error-message');
                if (firstError) {
                    firstError.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }
        });
    });
}

function showFieldError(field, message) {
    field.classList.add('field-error');
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    field.parentNode.appendChild(errorDiv);
    
    // Remove error on input
    field.addEventListener('input', function() {
        this.classList.remove('field-error');
        const error = this.parentNode.querySelector('.error-message');
        if (error) error.remove();
    }, { once: true });
}

function isValidEmail(email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

function isValidPhone(phone) {
    return /^[\d\s\-\+\(\)]{10,}$/.test(phone);
}

// ==========================================
// DATE INPUT ENHANCEMENTS
// ==========================================
function initializeDateInputs() {
    // Set today as default for date inputs with data-default-today
    const todayDateInputs = document.querySelectorAll('input[type="date"][data-default-today]');
    const today = new Date().toISOString().split('T')[0];
    
    todayDateInputs.forEach(input => {
        if (!input.value) {
            input.value = today;
        }
    });
    
    // Set min date for future date inputs
    const futureDateInputs = document.querySelectorAll('input[type="date"][data-future-only]');
    futureDateInputs.forEach(input => {
        input.min = today;
    });
    
    // Handle datetime-local inputs
    const datetimeInputs = document.querySelectorAll('input[type="datetime-local"]');
    datetimeInputs.forEach(input => {
        if (!input.value && input.hasAttribute('data-default-now')) {
            const now = new Date();
            now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
            input.value = now.toISOString().slice(0, 16);
        }
    });
}

// ==========================================
// TABLE SEARCH & FILTER
// ==========================================
function initializeTableSearch() {
    const searchInputs = document.querySelectorAll('.table-search');
    
    searchInputs.forEach(input => {
        const tableId = input.getAttribute('data-table');
        const table = document.getElementById(tableId) || input.closest('.section').querySelector('table');
        
        if (table) {
            input.addEventListener('input', function() {
                filterTable(table, this.value.toLowerCase());
            });
        }
    });
}

function filterTable(table, searchTerm) {
    const rows = table.querySelectorAll('tbody tr');
    let visibleCount = 0;
    
    rows.forEach(row => {
        const text = row.textContent.toLowerCase();
        if (text.includes(searchTerm)) {
            row.style.display = '';
            visibleCount++;
        } else {
            row.style.display = 'none';
        }
    });
    
    // Show empty state if no results
    const tbody = table.querySelector('tbody');
    let emptyRow = tbody.querySelector('.empty-row');
    
    if (visibleCount === 0 && searchTerm) {
        if (!emptyRow) {
            emptyRow = document.createElement('tr');
            emptyRow.className = 'empty-row';
            emptyRow.innerHTML = `<td colspan="100%" class="text-center p-4">
                <div class="empty-state">
                    <div class="empty-state-icon">🔍</div>
                    <h3>No results found</h3>
                    <p>Try adjusting your search terms</p>
                </div>
            </td>`;
            tbody.appendChild(emptyRow);
        }
        emptyRow.style.display = '';
    } else if (emptyRow) {
        emptyRow.style.display = 'none';
    }
}

// ==========================================
// MODAL FUNCTIONALITY
// ==========================================
function initializeModals() {
    // Modal triggers
    const modalTriggers = document.querySelectorAll('[data-modal]');
    modalTriggers.forEach(trigger => {
        trigger.addEventListener('click', function(e) {
            e.preventDefault();
            const modalId = this.getAttribute('data-modal');
            openModal(modalId);
        });
    });
    
    // Modal close buttons
    const closeButtons = document.querySelectorAll('.modal-close, [data-modal-close]');
    closeButtons.forEach(button => {
        button.addEventListener('click', function() {
            const modal = this.closest('.modal-overlay');
            if (modal) closeModal(modal);
        });
    });
    
    // Close on overlay click
    const overlays = document.querySelectorAll('.modal-overlay');
    overlays.forEach(overlay => {
        overlay.addEventListener('click', function(e) {
            if (e.target === this) {
                closeModal(this);
            }
        });
    });
    
    // Close on ESC key
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            const openModal = document.querySelector('.modal-overlay');
            if (openModal) closeModal(openModal);
        }
    });
}

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.style.display = 'flex';
        document.body.style.overflow = 'hidden';
    }
}

function closeModal(modal) {
    modal.style.display = 'none';
    document.body.style.overflow = '';
}

// ==========================================
// TOOLTIPS
// ==========================================
function initializeTooltips() {
    const tooltipElements = document.querySelectorAll('[data-tooltip]');
    
    tooltipElements.forEach(element => {
        element.addEventListener('mouseenter', function() {
            const text = this.getAttribute('data-tooltip');
            const tooltip = createTooltip(text);
            document.body.appendChild(tooltip);
            positionTooltip(tooltip, this);
        });
        
        element.addEventListener('mouseleave', function() {
            const tooltip = document.querySelector('.tooltip');
            if (tooltip) tooltip.remove();
        });
    });
}

function createTooltip(text) {
    const tooltip = document.createElement('div');
    tooltip.className = 'tooltip';
    tooltip.textContent = text;
    return tooltip;
}

function positionTooltip(tooltip, target) {
    const rect = target.getBoundingClientRect();
    tooltip.style.position = 'fixed';
    tooltip.style.top = (rect.top - tooltip.offsetHeight - 10) + 'px';
    tooltip.style.left = (rect.left + (rect.width / 2) - (tooltip.offsetWidth / 2)) + 'px';
}

// ==========================================
// ANIMATIONS
// ==========================================
function initializeAnimations() {
    // Fade in on scroll
    const observerOptions = {
        threshold: 0.1,
        rootMargin: '0px 0px -50px 0px'
    };
    
    const observer = new IntersectionObserver(function(entries) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add('fade-in-visible');
                observer.unobserve(entry.target);
            }
        });
    }, observerOptions);
    
    const fadeElements = document.querySelectorAll('.fade-in-on-scroll');
    fadeElements.forEach(el => observer.observe(el));
}

// ==========================================
// UTILITY FUNCTIONS
// ==========================================

// Format date to local format
function formatDate(date) {
    return new Date(date).toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

// Format datetime to local format
function formatDateTime(datetime) {
    return new Date(datetime).toLocaleString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}

// Calculate age from date of birth
function calculateAge(dateOfBirth) {
    const today = new Date();
    const birthDate = new Date(dateOfBirth);
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();
    
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    
    return age;
}

// Show loading spinner
function showLoading(element) {
    const spinner = document.createElement('div');
    spinner.className = 'spinner';
    element.appendChild(spinner);
}

// Hide loading spinner
function hideLoading(element) {
    const spinner = element.querySelector('.spinner');
    if (spinner) spinner.remove();
}

// Show toast notification
function showToast(message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.textContent = message;
    
    document.body.appendChild(toast);
    
    setTimeout(() => {
        toast.classList.add('toast-show');
    }, 10);
    
    setTimeout(() => {
        toast.classList.remove('toast-show');
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// Debounce function for search inputs
function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// ==========================================
// TABLE SORTING
// ==========================================
function initializeTableSorting() {
    const tables = document.querySelectorAll('table.sortable');
    
    tables.forEach(table => {
        const headers = table.querySelectorAll('th[data-sort]');
        
        headers.forEach(header => {
            header.style.cursor = 'pointer';
            header.addEventListener('click', function() {
                const column = this.getAttribute('data-sort');
                const order = this.classList.contains('sort-asc') ? 'desc' : 'asc';
                
                // Remove sort classes from other headers
                headers.forEach(h => h.classList.remove('sort-asc', 'sort-desc'));
                
                // Add sort class to current header
                this.classList.add(`sort-${order}`);
                
                // Sort table
                sortTable(table, column, order);
            });
        });
    });
}

function sortTable(table, column, order) {
    const tbody = table.querySelector('tbody');
    const rows = Array.from(tbody.querySelectorAll('tr'));
    
    rows.sort((a, b) => {
        const aValue = a.querySelector(`td[data-${column}]`)?.textContent || '';
        const bValue = b.querySelector(`td[data-${column}]`)?.textContent || '';
        
        if (order === 'asc') {
            return aValue.localeCompare(bValue, undefined, { numeric: true });
        } else {
            return bValue.localeCompare(aValue, undefined, { numeric: true });
        }
    });
    
    rows.forEach(row => tbody.appendChild(row));
}

// ==========================================
// PRINT FUNCTIONALITY
// ==========================================
function printSection(sectionId) {
    const section = document.getElementById(sectionId);
    if (!section) return;
    
    const printWindow = window.open('', '_blank');
    printWindow.document.write(`
        <!DOCTYPE html>
        <html>
        <head>
            <title>Print - AnimaLog</title>
            <style>
                body { font-family: Arial, sans-serif; padding: 20px; }
                table { width: 100%; border-collapse: collapse; margin: 20px 0; }
                th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
                th { background: #4CAF50; color: white; }
                h1, h2 { color: #2c3e50; }
                @media print {
                    button { display: none; }
                }
            </style>
        </head>
        <body>
            <h1>🐾 AnimaLog Pet Healthcare</h1>
            ${section.innerHTML}
            <script>
                window.onload = function() {
                    window.print();
                    window.onafterprint = function() {
                        window.close();
                    }
                }
            </script>
        </body>
        </html>
    `);
    printWindow.document.close();
}

// ==========================================
// EXPORT TO CSV
// ==========================================
function exportTableToCSV(tableId, filename) {
    const table = document.getElementById(tableId) || document.querySelector('table');
    if (!table) return;
    
    let csv = [];
    const rows = table.querySelectorAll('tr');
    
    rows.forEach(row => {
        const cols = row.querySelectorAll('td, th');
        const csvRow = [];
        
        cols.forEach(col => {
            csvRow.push('"' + col.textContent.replace(/"/g, '""') + '"');
        });
        
        csv.push(csvRow.join(','));
    });
    
    // Download CSV
    const csvContent = csv.join('\n');
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
    const link = document.createElement('a');
    const url = URL.createObjectURL(blob);
    
    link.setAttribute('href', url);
    link.setAttribute('download', filename || 'animalog_export.csv');
    link.style.visibility = 'hidden';
    
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

// ==========================================
// PET AGE CALCULATOR
// ==========================================
function updatePetAges() {
    const ageElements = document.querySelectorAll('[data-birth-date]');
    
    ageElements.forEach(element => {
        const birthDate = element.getAttribute('data-birth-date');
        if (birthDate) {
            const age = calculateAge(birthDate);
            const years = Math.floor(age);
            const months = Math.floor((age - years) * 12);
            
            let ageText = '';
            if (years > 0) {
                ageText += `${years} year${years > 1 ? 's' : ''}`;
            }
            if (months > 0) {
                if (ageText) ageText += ', ';
                ageText += `${months} month${months > 1 ? 's' : ''}`;
            }
            
            element.textContent = ageText || 'Less than 1 month';
        }
    });
}

// ==========================================
// VACCINATION DUE ALERTS
// ==========================================
function checkVaccinationAlerts() {
    const vaccinationDates = document.querySelectorAll('[data-next-due]');
    const today = new Date();
    
    vaccinationDates.forEach(element => {
        const dueDate = new Date(element.getAttribute('data-next-due'));
        const daysUntilDue = Math.floor((dueDate - today) / (1000 * 60 * 60 * 24));
        
        if (daysUntilDue < 0) {
            element.classList.add('overdue');
            element.title = 'Overdue!';
        } else if (daysUntilDue <= 7) {
            element.classList.add('due-soon');
            element.title = `Due in ${daysUntilDue} days`;
        }
    });
}

// ==========================================
// APPOINTMENT REMINDERS
// ==========================================
function checkAppointmentReminders() {
    const appointments = document.querySelectorAll('[data-appointment-date]');
    const today = new Date();
    
    appointments.forEach(element => {
        const appointmentDate = new Date(element.getAttribute('data-appointment-date'));
        const hoursUntil = (appointmentDate - today) / (1000 * 60 * 60);
        
        if (hoursUntil > 0 && hoursUntil <= 24) {
            element.classList.add('reminder');
            showToast(`Upcoming appointment in ${Math.floor(hoursUntil)} hours`, 'info');
        }
    });
}

// ==========================================
// SIDEBAR TOGGLE (MOBILE)
// ==========================================
function initializeSidebarToggle() {
    const toggleBtn = document.querySelector('.sidebar-toggle');
    const sidebar = document.querySelector('.sidebar');
    
    if (toggleBtn && sidebar) {
        toggleBtn.addEventListener('click', function() {
            sidebar.classList.toggle('sidebar-open');
            document.body.classList.toggle('sidebar-active');
        });
    }
}

// ==========================================
// THEME TOGGLE (OPTIONAL)
// ==========================================
function initializeThemeToggle() {
    const themeToggle = document.querySelector('.theme-toggle');
    
    if (themeToggle) {
        // Check for saved theme
        const savedTheme = localStorage.getItem('theme') || 'light';
        document.body.setAttribute('data-theme', savedTheme);
        
        themeToggle.addEventListener('click', function() {
            const currentTheme = document.body.getAttribute('data-theme');
            const newTheme = currentTheme === 'light' ? 'dark' : 'light';
            
            document.body.setAttribute('data-theme', newTheme);
            localStorage.setItem('theme', newTheme);
        });
    }
}

// ==========================================
// INITIALIZE ON LOAD
// ==========================================
window.addEventListener('load', function() {
    updatePetAges();
    checkVaccinationAlerts();
    checkAppointmentReminders();
    initializeSidebarToggle();
    initializeTableSorting();
});

// ==========================================
// EXPORT FUNCTIONS FOR GLOBAL USE
// ==========================================
window.AnimaLog = {
    showToast,
    showLoading,
    hideLoading,
    formatDate,
    formatDateTime,
    calculateAge,
    exportTableToCSV,
    printSection,
    openModal,
    closeModal
};