import {Component} from "./Component.js";
import {App} from "../app.js";


export class LoginButton extends Component {
    run() {
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
    }

    static id() {
        return LoginButton;
    }
}