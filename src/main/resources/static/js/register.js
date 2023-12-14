let playerId
let teamId
let matchId

let firstHalf
let startTime
let secondHalfStartTime

let csrfHeader
let csrfToken

let headers = new Headers()
let jsonHeaders = new Headers()

function fetchFun(link, method, headers, body) {
    fetch(link, {
        method: method,
        headers: headers,
        body: body
    })
        .then(res => res.json())
        .then(res => {
            return res
        })
    return null
}

function fetchContinueMatch() {
    fetch("/admin/register/api/continueMatch/" + matchId)
        .then(res => res.json())
        .then(res => {
            startTime = new Date(res)
            setInterval(() => {
                if (firstHalf) {
                    $("#time").text(Math.floor((new Date() - startTime) / 1000))
                } else {
                    $("#time").text(Math.floor((new Date() - new Date(secondHalfStartTime)) / 1000))
                }
            }, 1000)
        })
}

function isContinuation() {
    return $("#continue-div").length
}

$(document).ready(() => {
    //setup
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

    if (matchId !== null) {
        fetchContinueMatch()
    }


    //end of setup
    $(".register-choose-player").click((e) => {
        playerId = $(e.currentTarget).find(".id").text()
        $("#players-div").hide()
        $("#football-field-div").show()
    })
    $(".start-button").click(() => {
        if (!confirm("czy na pewno?")) {
            return
        }
        $(".start-button").hide()
        if (isContinuation()) {
            if (!firstHalf) {
                fetch("/admin/register/api/startSecondHalf/" + matchId, {
                    headers: headers
                })
            }
            // if (!firstHalf) {
            //     secondHalfStartTime = new Date()
            //     //save second half start time
            //     setInterval(() => {
            //         let time = new Date() - secondHalfStartTime
            //         $("#time").text(Math.floor(time / 1000) + 45 * 60)
            //     }, 1000)
            // }
            fetchContinueMatch()
            return
        }
        fetch("/admin/register/api/startMatch/" + teamId, {
            method: "POST",
            headers: headers
        }).then(res => res.json())
            .then(res => {
                $("#football-match-id").text(res[0])
                startTime = new Date(res[1])
                console.log(startTime)
                //add onclick on football field
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
        window.location.href = $(e.currentTarget).attr("href") + (matchId === null ? 0 : matchId) + "/" + $("#time").text() + "/" + (parseInt($("#time").text()) > 60 * 45 && firstHalf && confirm("czy chcesz zakonczyc polowe"))
    })
})