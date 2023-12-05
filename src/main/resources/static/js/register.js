let playerId
let x
let y
let settingSecondPoint
let eventId

$(document).ready(() => {
    $("#football-field").click((e) => {
        let target = e.target
        let x2 = Math.floor(e.offsetX / target.width * 100)
        let y2 = Math.floor(e.offsetY / target.height * 100)
        if(settingSecondPoint){
            settingSecondPoint = false

            //save
            console.log("saving")
            console.log("player " + playerId)
            console.log("event " + eventId)
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
        eventId = target.find(".id").text()
        twoPoints = twoPoints === "true"
        if(twoPoints){
            settingSecondPoint = true
        } else {
            //save
            console.log("saving\nplayer " + playerId + "\nevent " + eventId + "\nx " + x + "\ny " + y)
        }
        $("#event-div").hide()
        $("#football-field-div").show()
    })

    $(".register-choose-player").click((e) => {
        let target = $(e.currentTarget)
        playerId = target.find(".id").text()
        $("#players-div").hide()
        $("#football-field-div").show()
    })

    $(".change-player-button").click(() => {
        $("#football-field-div").hide()
        $("#players-div").show()
    })
})