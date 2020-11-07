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
            if(!parent.isOpenModalDialog()) {
                parent.openModalDialog(parent.toResolvePath("pages/join.html"), parent.toResolvePath("join.js"));
            }
        });        
    }

    static id() {
        return JoinButton;
    }
}