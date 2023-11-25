$(document).ready(() => {
    $("#football-field").click((e) => {
        let target = e.target
        let x = Math.floor(e.offsetX / target.width * 100)
        let y = Math.floor(e.offsetY / target.height * 100)
        console.log(x + " x " + y)
    })
})