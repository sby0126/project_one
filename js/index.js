import {EventEmitter} from "./EventEmitter.js";

/**
 * @author Eo Jinseok
 * @date 2020.11.01
 * @version v1.0.0
 */
class App extends EventEmitter {

    constructor() {
        super();

        /**
         * 동적으로 삽입할 페이지의 경로를 기입해주세요.
         */
        this._pendingList = [
            `view/login.html`,
        ];
        
    }

    /**
     * 새로 고침을 하지 않고 페이지를 동적으로 만들 수 있는 비동기 페이지 로더입니다.
     */
    async createLazyLoader() {
        const idx = location.href.lastIndexOf("/");
        const path = location.href.substring(0, idx);

        this._pendingList.forEach(async (i) => {
            await this.loadHTML(`${path}/${i}`)
            .then(result => {
                const container = document.querySelector(".container");
                const divElem = document.createElement("div");
                divElem.innerHTML = result;
    
                divElem.style.display = "none";
    
                container.appendChild(divElem);
    
            }).catch(err => {
                console.warn(err);
            })
        });
    }

    /**
     * 제이쿼리 없이 구현된 AJAX 메소드입니다.
     * 
     * @param {String} src 동적으로 로드할 페이지의 경로
     */
    loadHTML(src) {
        return new Promise((resolve, reject) => {

            const xhr = new XMLHttpRequest();
            xhr.open("GET", src);
            xhr.onload = () => {
                if(xhr.status === 200) {
                    resolve(xhr.responseText);
                } else {
                    reject(xhr.statusText);
                }
            };
            xhr.onerror = (err) => {
                reject(error);
            }
            xhr.send(null);
        });
    }
}

const app = new App();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.addEventListener("load", () => {
    app.emit("ready");
});