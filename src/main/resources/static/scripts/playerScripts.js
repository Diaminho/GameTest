async function updateSearchStatus(sessionId) {
    await window.fetch('updateSearchStatus', {
        method: 'POST', // *GET, POST, PUT, DELETE, etc.
    }).then(response => {
        //console.log(sessionId);
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

    // Get today's date and time
    let now = new Date().getTime();

    // Find the distance between now and the count down date
    let distance = countDownDate - now;

    let seconds = Math.floor((distance % (1000 * 60)) / 1000);

    // Output the result in an element with id="demo"
    document.getElementById("timer").innerHTML = "Соперник найден. Дуэль начнется через " + seconds + " секунд";

    // If the count down is over, write some text
    if (distance < 0) {
        window.location.replace("/duel");
    }
}


