const cp = require("child_process");
const fs = require('fs');
const path = require('path');
const mainUrl = `https://img.sta1.com`;
const argv = require("minimist")(process.argv.slice(2));
// const {data} = require("./brand.json");
const {data} = require("./item_soho.json");

function startAria2c() {
    data.forEach(config => {
        const targetUrl = mainUrl + config.imgPath

        cp.spawn("aria2c.exe", [targetUrl]);
    })
}

function startPowershell() {
    data.forEach(config => {
        const targetUrl = mainUrl + config.imgPath;
        const name = targetUrl.substring(targetUrl.lastIndexOf("/") + 1, targetUrl.length);
        cp.spawn("powershell", ["wget", targetUrl, "-OutFile", name]);
    })
}

const aria2c = cp.exec("where aria2c.exe");
aria2c.on("exit", (signal) => {
    if(signal != 0) {
        startPowershell();
    } else {
        startAria2c();
    }
})


// Array.from(document.querySelectorAll(".image-ratio-wrapper > img")).map(i => i.src)

