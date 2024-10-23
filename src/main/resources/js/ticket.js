document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const ticketId = urlParams.get('id')

    fetch(`/api/tickets/${ticketId}`)
        .then(response => response.json())
        .then(data => {
            if (data.error) {
                document.getElementById('ticket-info').textContent = 'Karta nije pronađena';
            } else {
                document.getElementById('ticket-oib').textContent = data.vatin;
                document.getElementById('ticket-name').textContent = `${data.firstName} ${data.lastName}`;
                document.getElementById('ticket-created-at').textContent = data.createdAt;
            }
        })
        .catch(err => {
            console.error('Error fetching ticket details:', err);
            document.getElementById('ticket-info').textContent = 'Pogreška prilikom dohvata karte';
        })
    document.getElementById('logout-button').addEventListener('click', function () {
        fetch('/api/logout', { method: 'POST' })
            .then(() => {
                window.location.href = '/';
            })
            .catch(err => {
                console.error('Pogreška prilikom logouta:', err);
            });
    });
})