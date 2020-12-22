import {App} from "./app.js";
import { JoinButton } from "./components/JoinButton.js";
import {LoginButton} from "./components/LoginButton.js";
import {ItemDetailLoader} from "./components/ItemdDetailLoader.js";
import {parseBodyFromString} from "./utils/bodyParser.js";
import { RecentlyItems } from "./components/RecentlyItems.js";

/**
 * ==============================================
 * 할인 페이지 구현
 * ==============================================
 */
class ItemDetailPage extends App {
    initMembers() {
        super.initMembers();

        /**
         * 동적으로 삽입할 페이지의 경로를 기입해주세요.
         */
        this._pendingList = [
        ];

        this._menuIndex = 1;
        
    }

    /**
     * 새로 고침을 하지 않고 페이지를 동적으로 만들 수 있는 비동기 페이지 로더입니다.
     */
    async createLazyLoader() {
        const path = location.protocol + "//" + location.host + location.pathname.substring(0, location.pathname.lastIndexOf("/"));

        Promise
            .all(this._pendingList.map(i => this.loadHTML(`${path}/${i.src}`)))
            .then(list => {

                list.forEach((elemRaw, i) => {
                    const container = document.querySelector(this._pendingList[i].parent);
                    const body = parseBodyFromString(elemRaw);
    
                    // 새로운 <div></div>에 특정 요소를 생성합니다.
                    if(this._pendingList[i].isCreateNewDiv) {
                        const newDiv = document.createElement("div");
                        newDiv.innerHTML = body;
                        container.appendChild(newDiv);
                    } else {
                        // <div></div>를 만들지 않고 하위 내용을 새로 변경합니다.
                        // 제이쿼리의 .html() 또는 .text()와 같습니다.
                        container.innerHTML = body;       
                    }

                });

                this.onLoad();                

            })
            .catch(err => {
                console.warn(err);
                this.onLoad();
            });

    }    

    createNewStyleSheet(dataID, imagePath) {
    }        

    addEventListeners() {
        this.on("loginView:ready", () => LoginButton.builder().run());        
    }

    onLoad() {
        JoinButton.builder(this).run();
        this.emit("loginView:ready");
        ItemDetailLoader.builder(this).run();
        RecentlyItems.builder(this).run();
    }

}

const app = new ItemDetailPage();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.app = app;
window.addEventListener("load", () => {
    app.emit("ready");
});