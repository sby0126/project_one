import {App} from "./app.js";
import { JoinButton } from "./components/JoinButton.js";
import {LoginButton} from "./components/LoginButton.js";
import {ItemDetailLoader} from "./components/ItemdDetailLoader.js";
import {parseBodyFromString} from "./utils/bodyParser.js";

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
            {
                src: `login.html`,
                parent: ".container",
                isCreateNewDiv: true,
            }
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
        this.emit("loginView:ready");
        ItemDetailLoader.builder(this).run();
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