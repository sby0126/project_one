import {App} from "./app.js";
import {blobData, base64toBlob} from "./data.js";
import {parseBodyFromString, parseScriptFromString} from  "./bodyParser.js";

/**
 * ==============================================
 * 아이템 페이지 구현
 * ==============================================
 */
class TermsPage extends App {
    initMembers() {
        super.initMembers();

        /**
         * 동적으로 삽입할 페이지의 경로를 기입해주세요.
         */
        this._pendingList = [
            {
                src: `/pages/login.html`,
                parent: ".container",
                isCreateNewDiv: true,
            }
        ];

        this._menuIndex = 3;
        
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
        super.onLoad();

        // 미리 정의해놓은 이벤트 함수를 호출합니다. (제이쿼리의 trigger와 유사합니다);
        this.emit("loginView:ready");
        this.emit("contents:ready"); 
    }

}

const app = new TermsPage();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.app = app;
window.addEventListener("load", () => {
    app.emit("ready");
});