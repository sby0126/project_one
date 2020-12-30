import {App} from "./app.js";
import { JoinButton } from "./components/JoinButton.js";
import {LoginButton} from "./components/LoginButton.js";
import { RecentlyItems } from "./components/RecentlyItems.js";
import {SearchBox } from "./components/SearchBox.js";

/**
 * ==============================================
 * 할인 페이지 구현
 * ==============================================
 */
class MorePage extends App {
    initMembers() {
        super.initMembers();

        /**
         * 동적으로 삽입할 페이지의 경로를 기입해주세요.
         */
        this._pendingList = [
//            {
//                src: `login.jsp`,
//                parent: ".container",
//                isCreateNewDiv: true,
//            }
        ];

        this._menuIndex = 3;
        
    }

    createNewStyleSheet(dataID, imagePath) {
    }        

    addEventListeners() {
        this.on("loginView:ready", () => LoginButton.builder().run());        
    }

    onLoad() {
        JoinButton.builder(this).run();
        SearchBox.builder(this).run();
        RecentlyItems.builder().run();
        this.emit("loginView:ready");
    }

}

const app = new MorePage();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.app = app;
window.addEventListener("load", () => {
    app.emit("ready");
});