async function updateSearchStatus(sessionId) {
    await window.fetch('updateSearchStatus', {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => {
        console.log("update search")
        window.location.reload();
    });
}

async function updateDuelStatus(sessionId) {
    await window.fetch('updateDuelStatus', {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => {
        window.location.reload();
    });
}

function countdownTimer(countDownDate) {
    let now = new Date().getTime();
    let distance = countDownDate - now;
    let seconds = Math.floor((distance % (1000 * 60)) / 1000);
    document.getElementById("timer").innerHTML = "Соперник найден. Дуэль начнется через " + seconds + " секунд";
    if (distance < 1) {
        window.location.replace("/setDuelInProgress");
    }
}


