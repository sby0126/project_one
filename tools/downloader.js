const cp = require("child_process");
const {
    promisify
} = require("util");
const spwan = promisify(cp.spawn);

class Downloader {

    // 분할 다운로드 시작
    startAria2c(data) {
        data.forEach(async config => {
            const targetUrl = config.src;

            try {
                await spwan("aria2c.exe", [targetUrl], {});
            } catch (e) {
                console.warn(e);
            }

        });
    }

    // 파워쉘 시작
    startPowershell(data) {
        data.forEach(async config => {
            const targetUrl = config.src;
            try {
                await spawn("powershell", ["wget", targetUrl, "-OutFile", name], {});
            } catch (e) {
                config.warn(e);
            }
            ``
        });
    }

    start(imgList, done) {
        const aria2c = cp.exec("where aria2c.exe");
        aria2c.on("exit", (signal) => {
            if(signal != 0) {
                this.startPowershell(imgList);
            } else {
                this.startAria2c(imgList);
            }
            done();
        });              
    }

}

module.exports = new Downloader();