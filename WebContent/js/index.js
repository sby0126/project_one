import {App, ID} from "./app.js";
import {LoginButton} from "./components/LoginButton.js";
import { ShopContentLoader } from "./components/ShopContentLoader.js";
import { CardStyleSheetBuilder } from "./components/CardStyleSheetBuilder.js";
import { Cookie } from "./components/Cookie.js";

/**
 * jshint esversion: 6
 * ==============================================
 * 메인 페이지 구현
 * ==============================================
 */
class MainPage extends App {

    initMembers() {
        super.initMembers();

        /**
         * 동적으로 삽입할 페이지의 경로를 기입해주세요.
         */
        this._pendingList = [
//            {
//                src: `pages/login.jsp`,
//                parent: ID.CONTAINER,
//                isCreateNewDiv: true,
//            },
            {
                src: `/pages/shop.jsp`,
                parent: ID.CONTENTS_WRAPPER,
                isCreateNewDiv: false,
            }    
        ];
        
    }

    createNewStyleSheet(dataID, imagePath) {

        // 카드 스타일 시트 빌더는 특정한 이미지를 원형으로 만들어냅니다.
        // (가상 요소에 클릭 이벤트 적용 : http://jsfiddle.net/ZWw3Z/70/)
        CardStyleSheetBuilder.builder(this, `
        
            .card p[${dataID}]::before {
                content: "";
                width: 5.5em;
                height: 5.5em;
                background: url("${imagePath}") center;
                background-size: cover;
                position: absolute;
                border-radius: 50%;
                left: calc(50% - 5.5em / 2);
                top: 10%;
                z-index: 0;
                pointer-events: all;
            }

            .card p[${dataID}]:hover::before {
                filter: brightness(1.1);
                border-radius: 0;
                transition: all .2s linear;
            }
        
        `, dataID).run();
    }

    addEventListeners() {

        /**
         * @type {ShopContentLoader}
         */
        this._contentLoader = ShopContentLoader.builder(this);

        this.on("login:ready", () => LoginButton.builder().run());
        this.on("contents:ready", () => this._contentLoader.run());
    }

    onLoad() {
        super.onLoad();
        
        // 미리 정의해놓은 이벤트 함수를 호출합니다. (제이쿼리의 trigger와 유사합니다);
        this.emit("login:ready");
        this.emit("contents:ready");
    }

    fetchNewData() {
        if(this._contentLoader) {
            this._contentLoader.addFetchData(10);
        }
    }

}

const app = new MainPage();
app.on("ready", async () => app.createLazyLoader());

window.app = app;
window.addEventListener("load", () => app.emit("ready"));