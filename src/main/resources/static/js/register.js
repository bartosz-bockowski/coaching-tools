let playerId
let x
let y
let settingSecondPoint
let eventTypeId
let pause = true
let startTime
let matchId = 0
let endTime = null
let firstHalf

let ourLeft = true

let csrfToken
let csrfHeader

let headers = new Headers()
let jsonHeaders = new Headers()

function getTimeInSeconds() {
    return Math.floor((new Date() - startTime) / 1000)
}

function createEventAndFetch(playerId, matchId, time, eventTypeId, x1, y1, x2, y2) {
    let event = {
        "playerId": Number(playerId),
        "matchId": matchId,
        "time": time,
        "eventTypeId": eventTypeId,
        "firstHalf": firstHalf,
        "x1": ourLeft ? x1 : 100 - x1,
        "y1": ourLeft ? y1 : 100 - y1,
        "x2": ourLeft ? x2 : 100 - x2,
        "y2": ourLeft ? y2 : 100 - y2
    }
    fetch("/admin/register/api/saveEvent", {
        method: "POST",
        headers: jsonHeaders,
        body: JSON.stringify(event)
    }).then(res => res.json())
        .then(res => {
            if (res === "OK") {
                alert("ok")
            } else {
                alert("error")
            }
        })
}

$(document).ready(() => {
    firstHalf = $("#first-half").text() === "true"
    let contDiv = $("#continue-div")
    if (contDiv.length > 0) {
        matchId = $("#continue-matchId").text()
        endTime = $("#continue-endTime").text()
    } else {
        startTime = null
        endTime = 0
    }

    csrfToken = $("meta[name='_csrf']").attr("content");
    csrfHeader = $("meta[name='_csrf_header']").attr("content");

    headers.append(csrfHeader, csrfToken)
    jsonHeaders.append(csrfHeader, csrfToken)
    jsonHeaders.append("Content-Type", "application/json")

    matchId = $("#football-match-id").text()
    $("#football-field").click((e) => {
        if (pause) return
        let target = e.target
        let x2 = Math.floor(e.offsetX / target.width * 100)
        let y2 = Math.floor(e.offsetY / target.height * 100)
        if (settingSecondPoint) {
            settingSecondPoint = false

            //save
            createEventAndFetch(playerId, matchId, getTimeInSeconds(), eventTypeId, x, y, x2, y2)
            console.log("saving")
            console.log("player " + playerId)
            console.log("event " + eventTypeId)
            console.log("x " + x)
            console.log("y " + y)
            console.log("x2 " + x2)
            console.log("y2 " + y2)
        } else {
            x = x2
            y = y2
            $("#football-field-div").hide()
            $("#event-div").show()
        }
    })

    $(".register-choose-event").click((e) => {
        let target = $(e.currentTarget)
        let twoPoints = target.find(".two-points").text()
        eventTypeId = target.find(".id").text()
        twoPoints = twoPoints === "true"
        if (twoPoints) {
            settingSecondPoint = true
        } else {
            //save
            console.log("saving\nplayer " + playerId + "\nevent " + eventTypeId + "\nx " + x + "\ny " + y)
            createEventAndFetch(playerId, matchId, getTimeInSeconds(), eventTypeId, x, y, null, null)
        }
        $("#event-div").hide()
        $("#football-field-div").show()
    })

    $(".register-choose-player").click((e) => {
        let target = $(e.currentTarget)
        playerId = target.find(".id").text()
        $("#players-div").hide()
        $("#football-field-div").show()
        let number = target.find(".player-number").text()
        let name = target.find(".player-name").text()
        $("#current-player").text((number !== "X" ? number + " - " : "") + name)
    })

    $(".change-player-button").click(() => {
        $("#football-field-div").hide()
        $("#players-div").show()
    })

    $(".start-button").click((e) => {
        pause = false
        if (startTime == null)
            $(e.currentTarget).hide()
        console.log(endTime)
        startTime = new Date(new Date() - endTime * 1000)
        if (!$("#continue-div").length) {
            fetch("/admin/register/api/startMatch/" + startTime.getTime() + "/" + $("#team-id").text(),
                {
                    method: "POST",
                    headers: headers
                })
                .then((res) => res.json())
                .then((res) => {
                    matchId = res
                })
        }
        if ($("#continue-div").length && $("#football-match-second-half-start").text() === "") {
            fetch("/admin/register/api/setSecondHalfStart/" + $("#football-match-id").text())
        }
        setInterval(() => {
            let time = Math.floor((new Date() - startTime) / 1000) - (!firstHalf ? $("#football-match-first-half-overtime").text() : 0)
            let seconds = time % 60
            let minutes = (time - seconds) / 60
            if (seconds < 10) seconds = "0" + seconds
            if (minutes < 10) minutes = "0" + minutes
            $("#time").text(minutes + ":" + seconds + (firstHalf ? (minutes > 44 ? " - doliczony" : "") : minutes > 89 ? " - doliczony" : ""))
            let stopBtn = $("#stop-button")
            stopBtn.attr("href", stopBtn.attr("editHref") + matchId + "/" + time)
        }, 1000)
    })

    $("#stop-button").click((e) => {
        e.preventDefault()
        let endHalf = false
        if ((firstHalf && confirm("czy chcesz zakonczyc polowe")) || (!firstHalf && confirm("czy chcesz zakonczyc mecz"))) {
            endHalf = true
        }
        if (confirm("czy na pewno?")) {
            window.location.href = $(e.currentTarget).attr("href") + "/" + endHalf
        }
    })

    $("#switch-sides").click(() => {
        let left = $("#left-side-div").find("div")
        let right = $("#right-side-div").find("div")
        let temp = left.html()
        left.html(right.html())
        right.html(temp)
        ourLeft = !ourLeft
    })
})