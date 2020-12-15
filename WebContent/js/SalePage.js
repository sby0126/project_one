import {App} from "./app.js";
import { LoginButton } from "./components/LoginButton.js";
import { SaleContentLoader } from "./components/SaleContentLoader.js";
import { CardStyleSheetBuilder } from "./components/CardStyleSheetBuilder.js";

/**
 * ==============================================
 * 할인 페이지 구현
 * ==============================================
 */
class SalePage extends App {
    initMembers() {
        super.initMembers();

        /**
         * 동적으로 삽입할 페이지의 경로를 기입해주세요.
         */
        this._pendingList = [
//            {
//                src: `pages/login.jsp`,
//                parent: ".container",
//                isCreateNewDiv: true,
//            },
            {
                src: `pages/shop.jsp`,
                parent: ".contents-wrapper",
                isCreateNewDiv: false,
            }
        ];

        this._menuIndex = 2;
        
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

        CardStyleSheetBuilder.builder(this, `        
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
        `, dataID).run();

    }

    addEventListeners() {
        this._contentLoader = SaleContentLoader.builder(this);

        this.on("loginView:ready", () => LoginButton.builder().run());      
        this.on("contents:ready", () => this._contentLoader.run());        
    }

    loadCSS() {
        const head = document.head;  
        const link = document.createElement('link'); 
        link.rel = 'stylesheet';
        link.type = 'text/css'; 
        link.href = 'css/contents-sale.css';  

        head.after(link);
    }

    onLoad() {
        super.onLoad();

        // 미리 정의해놓은 이벤트 함수를 호출합니다. (제이쿼리의 trigger와 유사합니다);
        this.emit("loginView:ready");
        this.emit("contents:ready"); 

        this.loadCSS();

    }

    fetchNewData() {
        if(this._contentLoader) {
            this._contentLoader.addFetchData(5);
        }
    }    

}

const app = new SalePage();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.app = app;
window.addEventListener("load", () => {
    app.emit("ready");
});