const eventsContainer = document.getElementById('events-container');
const registrationForm = document.getElementById('registrationForm');
const eventSelect = document.getElementById('event');
const searchInput = document.getElementById('searchInput');
const categoryFilter = document.getElementById('categoryFilter');
const dateFilter = document.getElementById('dateFilter');
const priceFilter = document.getElementById('priceFilter');

let allEvents = [];

function loadEvents() {
    fetch('/api/events')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch events');
            }
            return response.json();
        })
        .then(events => {
            eventsContainer.innerHTML = '';
            eventSelect.innerHTML = '<option value="">Bitte wählen</option>';

            allEvents = events;
            renderEvents(events);
        })
        .catch(error => {
            console.error('Error loading events:', error);
            eventsContainer.innerHTML = '<p>Error loading events. Please try again later.</p>';
        });
}

function showEventDetails(event) {
    alert(`Details für ${event.name}:\n\n${event.description}\nDatum: ${event.date}`);
}

registrationForm.addEventListener('submit', async (event) => {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const eventId = document.getElementById('event').value;

    if (!email || !eventId) {
        alert('Bitte füllen Sie alle erforderlichen Felder aus');
        return;
    }

    const formData = {
        email: email,
        eventId: eventId
    };

    try {
        const response = await fetch('/api/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Registrierung fehlgeschlagen');
        }

        const result = await response.json();
        alert('Registrierung erfolgreich! Bitte überprüfen Sie Ihre E-Mails für die Bestätigung.');
        registrationForm.reset();
        
        // Refresh the events display to show updated capacity
        loadEvents();
    } catch (error) {
        console.error('Registrierungsfehler:', error);
        alert(error.message);
    }
});

document.addEventListener('DOMContentLoaded', loadEvents);

function showEventDetails(event) {
    document.getElementById('modal-title').textContent = event.name;
    document.getElementById('modal-description').textContent = event.description;
    document.getElementById('modal-date').textContent = `Datum: ${event.date}`;
    document.getElementById('modal').style.display = 'block';
}

document.querySelector('.close-btn').addEventListener('click', () => {
    document.getElementById('modal').style.display = 'none';
});

async function loadAndFilterEvents() {
    if (allEvents.length === 0) {
        const response = await fetch('/api/events');
        allEvents = await response.json();
    }
    
    let filteredEvents = allEvents.filter(event => {
        const matchesSearch = event.name.toLowerCase().includes(searchInput.value.toLowerCase()) ||
                            event.description.toLowerCase().includes(searchInput.value.toLowerCase());
        
        const matchesCategory = !categoryFilter.value || event.category === categoryFilter.value;
        const matchesPrice = !priceFilter.value || 
                           (priceFilter.value === 'FREE' && event.price === 0) ||
                           (priceFilter.value === 'PAID' && event.price > 0);
        
        return matchesSearch && matchesCategory && matchesPrice;
    });
    
    renderEvents(filteredEvents);
}

[searchInput, categoryFilter, dateFilter, priceFilter].forEach(element => {
    element.addEventListener('change', loadAndFilterEvents);
});

function renderEvents(events) {
    eventsContainer.innerHTML = '';
    eventSelect.innerHTML = '<option value="">Bitte wählen</option>';

    // Kategorie-Übersetzungen
    const categoryTranslations = {
        'MUSIC': 'Musik',
        'TECH': 'Technologie',
        'ARTS': 'Kunst',
        'SPORTS': 'Sport'
    };

    events.forEach(event => {
        const eventCard = document.createElement('div');
        eventCard.className = 'event-card';
        
        const detailsButton = document.createElement('button');
        detailsButton.textContent = 'Details anzeigen';
        detailsButton.className = 'details-btn';
        detailsButton.addEventListener('click', () => showEventDetails(event));

        // Format price with German number format
        const formattedPrice = new Intl.NumberFormat('de-DE', {
            style: 'currency',
            currency: 'EUR'
        }).format(event.price);

        eventCard.innerHTML = `
            <h3>${event.name}</h3>
            <p>${event.description}</p>
            <p><strong>Datum:</strong> ${new Date(event.date).toLocaleDateString('de-DE')}</p>
            <p><strong>Uhrzeit:</strong> ${event.time || 'Nicht angegeben'}</p>
            <p><strong>Ort:</strong> ${event.location || 'Wird noch bekannt gegeben'}</p>
            <p><strong>Kategorie:</strong> ${categoryTranslations[event.category] || event.category}</p>
            <p><strong>Preis:</strong> ${event.price > 0 ? formattedPrice : 'Kostenlos'}</p>
            <p><strong>Veranstalter:</strong> ${event.organizer || 'Nicht angegeben'}</p>
            <p><strong>Verfügbare Plätze:</strong> ${event.capacity - event.registeredAttendees}/${event.capacity}</p>
            <p><strong>Schlagworte:</strong> ${event.tags ? event.tags.join(', ') : 'Keine'}</p>
            <p><strong>Status:</strong> ${event.status === 'UPCOMING' ? 'Demnächst' : event.status}</p>
        `;
        eventCard.appendChild(detailsButton);
        eventsContainer.appendChild(eventCard);

        const option = document.createElement('option');
        option.value = event.id;
        option.textContent = event.name;
        eventSelect.appendChild(option);
    });
}
