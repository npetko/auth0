document.addEventListener('DOMContentLoaded', function () {
    fetch('api/tickets/count')
        .then(response => response.json())
        .then(data => {
            document.getElementById('ticket-count').textContent = data.count;
        })
        .catch(err => {
            console.error('Could not fetch ticket count:', err);
            document.getElementById('ticket-count').textContent = 'Pogreška prilikom dohvata broja';
        })
})