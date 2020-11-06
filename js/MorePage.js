import {App} from "./app.js";
import {blobData, base64toBlob} from "./data.js";
import {saleData} from "./saleData.js";
import {parseBodyFromString, parseScriptFromString} from  "./bodyParser.js";

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
            {
                src: `login.html`,
                parent: ".container",
                isCreateNewDiv: true,
            }
        ];

        this._menuIndex = 3;
        
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
    }    

    addEventListeners() {
        this.on("loginView:ready", () => {
            const loginButton = document.querySelector(".header-right-login-button");
            /**
             * @type {HTMLDivElement}
             */
            const loginView = document.querySelector(".floating-login-view-wrapper");
            const lightBox = document.querySelector("#light-box-container");

            // 로그인 바가 열렸을 때 우측을 누르면 닫기
            window.addEventListener("click", (ev) => {
                if(ev.target == loginView && !$(".main .content_login").is(":visible")) {
                    lightBox.classList.remove("active");
                    loginView.style.transition = "all .8s ease-in-out";
                    loginView.style.left = "9999px";
                }
            });

            loginButton.addEventListener("click", () => {

                // 브라우저는 최적화 문제로 인해 모든 스타일의 속성을 즉각 계산하지 않습니다.
                // 따라서 스타일 값을 가져오려면 getComputedStyle 함수를 써야 합니다.
                const styled = getComputedStyle(loginView);

                // 스타일 값에는 px 단위가 들어가있습니다.
                // parseInt를 사용하면 단위 값이 제거되고 숫자만 남습니다.
                if(parseInt(styled.left) != 0) {
                    // active 클래스를 추가하면 화면에 라이트 박스가 표시됩니다.
                    lightBox.classList.add("active");
                    loginView.style.transition = "all .4s ease-out";
                    loginView.style.left = "1px";
                }
            });

            // 로그인 버튼을 누르면 우측에 로그인 바가 표시됩니다.
            const btn = document.querySelector("#close-login-view");
            btn.addEventListener("click", () => {
                lightBox.classList.remove("active");
                loginView.style.transition = "all .8s ease-in-out";
                loginView.style.left = "9999px";
            })
        });          
    }

    onLoad() {
        // 회원 가입 버튼 이벤트 등록
        document.querySelector("#join-button").addEventListener("click", () => {
            if(!this.isOpenModalDialog()) {
                this.openModalDialog(this.toResolvePath("join.html"), this.toResolvePath("join.js"));
            }
        });

        // 미리 정의해놓은 이벤트 함수를 호출합니다. (제이쿼리의 trigger와 유사합니다);
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