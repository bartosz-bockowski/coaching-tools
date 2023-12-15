let playerId
let teamId
let matchId

let firstHalf
let startTime
let secondHalfStartTime

let choosenEventId
let eventTime
let settingSecondPoint
let eventCommand
let x1, y1

let csrfHeader
let csrfToken

let headers = new Headers()
let jsonHeaders = new Headers()

function fetchContinueMatch() {
    fetch("/admin/register/api/continueMatch/" + matchId)
        .then(res => res.json())
        .then(res => {
            startTime = new Date(res)
            console.log(res)
            setInterval(() => {
                if (firstHalf) {
                    $("#time").text(Math.floor((new Date() - startTime) / 1000))
                } else {
                    $("#time").text(Math.floor((new Date() - new Date(startTime)) / 1000))
                }
            }, 1000)
        })
}

function fetchEventCommand() {
    console.log(eventCommand)
}

function isContinuation() {
    return $("#continue-div").length
}

function getTime() {
    return $("#time").text()
}

function setup() {
    csrfHeader = $("#_csrf_header").text()
    csrfToken = $("#_csrf_token").text()

    headers.append(csrfHeader, csrfToken)
    jsonHeaders.append(csrfHeader, csrfToken)
    jsonHeaders.append("Content-Type", "application/json")

    firstHalf = $("#first-half").text() === "true"
    teamId = $("#team-id").text()

    matchId = $("#football-match-id").text()
    matchId = matchId === "" ? null : matchId

    secondHalfStartTime = $("#football-match-second-half-start").text()

    console.log(secondHalfStartTime)
    if (matchId !== null && (secondHalfStartTime !== "" || firstHalf)) {
        fetchContinueMatch()
    }
}

$(document).ready(() => {

    setup()

    $(".register-choose-player").click((e) => {
        playerId = $(e.currentTarget).find(".id").text()
        $("#players-div").hide()
        $("#football-field-div").show()
    })
    $(".start-button").click(() => {
        if (!confirm("czy na pewno?")) {
            return
        }
        let footballField = $("#football-field")
        footballField.click((e) => {
            eventTime = getTime()
            let width = footballField.innerWidth()
            let height = footballField.innerHeight()
            x1 = Math.floor(e.offsetX / width * 100)
            y1 = Math.floor(e.offsetY / height * 100)
            if (settingSecondPoint) {
                eventCommand.x2 = x1
                eventCommand.y2 = y1
                fetchEventCommand()
                settingSecondPoint = false
                return
            }
            $("#football-field-div").hide()
            $("#event-div").show()
        })
        $(".start-button").hide()
        if (isContinuation()) {
            if (!firstHalf) {
                fetch("/admin/register/api/startSecondHalf/" + matchId, {
                    headers: headers
                })
            }
            fetchContinueMatch()
            return
        }
        fetch("/admin/register/api/startMatch/" + teamId, {
            method: "POST",
            headers: headers
        }).then(res => res.json())
            .then(res => {
                matchId = res[0]
                startTime = new Date(res[1])
                setInterval(() => {
                    $("#time").text(Math.floor((new Date() - startTime) / 1000))
                }, 1000)
            })

    })
    $("#stop-button").click((e) => {
        e.preventDefault()
        if (!confirm("czy na pewno?")) {
            return
        }
        window.location.href = $(e.currentTarget).attr("href") + (matchId === null ? 0 : matchId) + "/" + getTime() + "/" + (parseInt(getTime()) >= 60 * 45 && firstHalf && confirm("czy chcesz zakonczyc polowe"))
    })
    $(".register-choose-event").click((e) => {
        let target = $(e.currentTarget)
        choosenEventId = target.find(".id").text()
        let twoPoints = target.find(".two-points").text() === "true"
        eventCommand = {
            "time": eventTime,
            "playerId": playerId,
            "matchId": matchId,
            "eventTypeId": choosenEventId,
            "firstHalf": firstHalf,
            "x1": x1,
            "y1": y1
        }
        settingSecondPoint = twoPoints
        if (!settingSecondPoint) {
            fetchEventCommand()
        }
        $("#event-div").hide()
        $("#football-field-div").show()
    })
})