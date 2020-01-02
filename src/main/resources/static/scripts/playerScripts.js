async function updateSearchStatus(sessionId) {
    await window.fetch('updateSearchStatus', {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => {
        console.log(sessionId);
        window.location.reload();
    });
}

async function updateDuelStatus(sessionId) {
    await window.fetch('updateDuelStatus', {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => {
        console.log(sessionId);
        window.location.reload();
    });
}