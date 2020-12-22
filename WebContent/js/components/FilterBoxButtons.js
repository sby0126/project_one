import {Component} from "./Component.js";
import {App} from "../app.js";
import { parseBodyFromString, parseScriptFromString } from "../utils/bodyParser.js";

const AGES = [
    "all",
    "10",
    "20",
    "30"
];

export class FilterBoxButtons extends Component {

    initMembers(...args) {
        super.initMembers(...args);
        
        this._index = 0;
        this._ageTabIndex = 0;
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
        let self = this;

        // 선택 버튼 추가
        $(".header-filter-box-footer-right span")
            .on("click", function() {
                const container = $(".header-filter-box-footer-right span");

                container.removeClass("active");
                $(this).addClass("active");

                const idx = container.index($(this));

                const params = new URLSearchParams(location.search);
                params.set("ages", AGES[idx]);

                location.search = params.toString();
            });

        const params = new URLSearchParams(location.search);
        const container = $(".header-filter-box-footer-right span");
        if(params.get("ages")) {
            self._ageTabIndex = AGES.indexOf(params.get("ages"));
        } else {
            // 선택 버튼에 메뉴 인덱스 설정
            self._ageTabIndex = 0;
        }            

        container
            .removeClass("active")
            .eq(self._ageTabIndex)
            .addClass("active");

        /**
         * @type {HTMLDivElement[]}
         */
        let filterBoxHeader = document.querySelector(".header-filter-box-header");
        if(!filterBoxHeader) {
            filterBoxHeader = {children: []};
            return;
        }

        const filterBoxButtons = Array.from(filterBoxHeader.children);

        // 필터 버튼이 2개 있을 때 활성화 버튼을 토글 처리합니다.
        if(filterBoxButtons.length < 3) {
            const param = new URLSearchParams(location.search);
            filterBoxButtons.filter(e => e.classList.contains("active")).forEach(e => e.classList.remove("active"));
            if(param.get("shopType") == "B") {
                filterBoxButtons[1].classList.add("active");     
            } else {
                filterBoxButtons[0].classList.add("active");     
            }
        }


        filterBoxButtons.forEach((i, idx) => {
            i.addEventListener("click", async (ev) => {

                this._index = idx;

                if(filterBoxButtons.length < 3) {
                    const param = new URLSearchParams(location.search);
                    param.set("shopType", this._index > 0 ? "B" : "S");

                    // 아예 새로 고침이므로 CSS를 지울 필요가 없음.
                    location.search = param.toString();
                } else {
                    // 로그인 창을 켭니다.
                    if(this._index > 0) {
                        $(".header-right-login-button").trigger("click");
                    }
                }

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

                    // // 카드 이미지를 지웁니다.
                    // // 여기에서 d는 delete의 약자입니다.
                    // for(let i = 0; i < parent._headStyleSheets.length; i++) {
                    //     parent.emit("card:d-" + i);
                    // }

                    // // 카드 이미지를 생성합니다.
                    // await parent.loadHTML(parent.toResolvePath("pages/shop.html")).then(result => {
                    //     const container = document.querySelector(".contents-wrapper");
                    //     const body = parseBodyFromString(result);
                    //     container.innerHTML = body;     
                    //     parent.emit("contents:ready");   
                    // });

                }
            })
        });     
    }

    static id() {
        return FilterBoxButtons;
    }
}