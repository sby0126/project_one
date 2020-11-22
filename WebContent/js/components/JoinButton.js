import {Component} from "./Component.js";
import {App} from "../app.js";

export class JoinButton extends Component {
    run() {
        /**
         * @type {App}
         */
        const parent = this._parent;

        // 회원 가입 버튼 이벤트 등록
        document.querySelector("#join-button").addEventListener("click", () => {
            location.href = "/pages/join.jsp";
        });       

        $(".floating-login-view-wrapper input[type=submit]").on("click", (ev) => {
            
            const checker =[
                $("#floating-login-view-login-id").val().length > 0,
                $("#floating-login-view-login-pw").val().length > 0,
            ];

            // 아이디 또는 비밀번호를 입력하지 않았을 때, 경고창 띄우기
            if(!checker.every(i => !!i)) {
                alert("ID 또는 비밀번호를 입력하지 않았습니다.")
                ev.preventDefault();
            }
            
        });        
    }

    static id() {
        return JoinButton;
    }
}