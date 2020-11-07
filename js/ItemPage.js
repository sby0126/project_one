import {App} from "./app.js";
import {LoginButton} from "./components/LoginButton.js";
import { ItemContentLoader } from "./components/ItemContentLoader.js";

/**
 * ==============================================
 * 아이템 페이지 구현
 * ==============================================
 */
class ItemPage extends App {
    initMembers() {
        super.initMembers();

        /**
         * 동적으로 삽입할 페이지의 경로를 기입해주세요.
         */
        this._pendingList = [
            {
                src: `pages/login.html`,
                parent: ".container",
                isCreateNewDiv: true,
            },
            {
                src: `pages/shop.html`,
                parent: ".contents-wrapper",
                isCreateNewDiv: false,
            }
        ];

        this._menuIndex = 1;
        
    }

    /**
     * CSS를 자바스크립트에서 동적으로 생성합니다.
     * 이 메소드는 가상 요소로 만든 둥근 이미지를 변경하기 위해 정의하였습니다.
     * <p></p> 요소는 각각 특정 dataID를 attribute로 가집니다.
     * 
     * @link https://stackoverflow.com/a/524721
     * @param {String} dataId 
     * @param {String} imagePath 
     */
    createNewStyleSheet(dataID, imagePath) {
        const head = document.head || document.getElementsByTagName('head')[0];
        const style = document.createElement('style');

        head.appendChild(style);

        const css = `
        .card {
            display: flex;
            flex-wrap: wrap;
            flex: auto 1 auto;
            flex-direction: column;
            border: 1px solid #F2F5F9;
        }
        
        .card p[${dataID}]::before {
            content: "";
            width: 100%;
            height: 78%;
            background: url("${imagePath}") left top;
            background-size: cover;
            background-repeat: no-repeat;
            position: absolute;
            border-radius: 0;
            left: 0;
            top: 0;
            z-index: 0;
        }

        .card p[${dataID}]:hover::before {
            filter: brightness(1.1);
            border-radius: 0;
            transition: all .2s linear;
        }
        `;

        if (style.styleSheet) {
            // IE 8
            style.styleSheet.cssText = css;
        } else {
            const child = document.createTextNode(css);
            style.appendChild(child);
            this._headStyleSheets.push(dataID);

            // 삭제 이벤트를 정의합니다.
            // 사용 예: 
            //  app.emit("card:d-0"); // 1번 카드의 스타일을 지운다.
            //  app.emit("card:d-1"); // 2번 카드의 스타일을 지운다.
            this.on(`card:${dataID}`, () => {
                const idx = this._headStyleSheets.indexOf(dataID);
                delete this._headStyleSheets[idx];

                // removeChild를 쓰면 메모리에 남아서 안지워짐.
                style.remove(child);

                // 등록된 이벤트를 제거합니다.
                setTimeout(() => {
                    this.off(`card:${dataID}`);
                }, 0);
            });
        }
    }    

    addEventListeners() {
        this.on("loginView:ready", () => LoginButton.builder().run());      
        this.on("contents:ready", () => ItemContentLoader.builder(this).run());        
    }

    onLoad() {
        super.onLoad();

        // 미리 정의해놓은 이벤트 함수를 호출합니다. (제이쿼리의 trigger와 유사합니다);
        this.emit("loginView:ready");
        this.emit("contents:ready"); 
    }

}

const app = new ItemPage();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.app = app;
window.addEventListener("load", () => {
    app.emit("ready");
});