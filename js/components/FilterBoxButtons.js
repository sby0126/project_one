import {Component} from "./Component.js";
import {App} from "../app.js";
import { parseBodyFromString, parseScriptFromString } from "../utils/bodyParser.js";

export class FilterBoxButtons extends Component {

    initMembers(...args) {
        super.initMembers(...args);
        
        this._index = 0;
    }

    /**
     * 현재 탭 인덱스를 반환합니다.
     */
    get index() {
        return this._index;
    }

    run() {
        /**
         * @type {App}
         */
        const parent = this._parent;

        const filterBoxButtons = Array.from(document.querySelector(".header-filter-box-header").children);
        filterBoxButtons.forEach((i, idx) => {
            i.addEventListener("click", async (ev) => {
                
                this._index = idx;

                /**
                 * 화살표 함수에서는 this가 이벤트가 아니기 때문에 ev.currentTarget를 써야 합니다.
                 * 이것은 제이쿼리 이벤트에서 this와 같습니다.
                 * 
                 * @type {HTMLButtonElement}
                 */
                const target = ev.currentTarget;

                if(!target.classList.contains("active")) {

                    filterBoxButtons.filter(e => e.classList.contains("active")).forEach(e => e.classList.remove("active"));
                    target.classList.add("active");

                    // 카드 이미지를 지웁니다.
                    // 여기에서 d는 delete의 약자입니다.
                    for(let i = 0; i < parent._headStyleSheets.length; i++) {
                        parent.emit("card:d-" + i);
                    }

                    document.querySelector(".contents-wrapper").innerHTML = "";

                    // 카드 이미지를 생성합니다.
                    await parent.loadHTML(parent.toResolvePath("pages/shop.html")).then(result => {
                        const container = document.querySelector(".contents-wrapper");
                        const body = parseBodyFromString(result);
                        container.innerHTML = body;     
                        parent.emit("contents:ready");   
                    });

                }
            })
        });     
    }

    static id() {
        return FilterBoxButtons;
    }
}