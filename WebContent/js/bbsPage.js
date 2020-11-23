import {App} from "./app.js";
import {LoginButton} from "./components/LoginButton.js";

/**
 * ==============================================
 * 맵 페이지 구현
 * ==============================================
 */
class bbsPage extends App {
    initMembers() {
        super.initMembers();

        /**
         * 동적으로 삽입할 페이지의 경로를 기입해주세요.
         */
        this._pendingList = [
            {
                src: `login.jsp`,
                parent: ".container",
                isCreateNewDiv: true,
            },
        ];

        this._menuIndex = 4;
        
    }

    createNewStyleSheet(dataID, imagePath) {
    }    

    addEventListeners() {
        this.on("loginView:ready", () => LoginButton.builder().run());              
    }

    onLoad() {
        super.onLoad();

        // 미리 정의해놓은 이벤트 함수를 호출합니다. (제이쿼리의 trigger와 유사합니다);
        this.emit("loginView:ready");
    }
}

const app = new bbsPage();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.app = app;
window.addEventListener("load", () => {
    app.emit("ready");
});