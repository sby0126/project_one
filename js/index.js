import {EventEmitter} from "./EventEmitter.js";
import {cssRuleSet} from "./styleRules.js";
import {parseBodyFromString} from  "./bodyParser.js";

// const e = React.createElement;

// class CardContainer extends React.Component {
//     render() {
//         return React.createElement('div', null, `안녕하세요. ${this.props.toWhat}`);
//     }
// }

/**
 * @author Eo Jinseok
 * @date 2020.11.01
 * @version v1.0.0
 */
class App extends EventEmitter {

    constructor() {
        super();

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

        this.addEventListeners();

        // 메뉴 슬라이드가 정의된 코드입니다.
        // 메뉴 슬라이드 바는 가상 요소로 정의되어있습니다.
        // 하지만 가상 요소는 DOM 쿼리 선택자로 선택이 되지 않습니다.
        // 따라서 모든 CSS 속성을 검색하여 속성을 직접 변경하는 방법을 사용하였습니다.
        // 메뉴 슬라이드 바는 색상, 위치가 클릭한 메뉴의 위치에 따라 변경됩니다.
        const menuItems = Array.from(document.querySelectorAll(".header-center > a"));
        menuItems.forEach((i, idx) => {
            i.addEventListener("click", ev => {

                // 전체 메뉴 영역의 크기
                const parentRect = document.querySelector(".header-center").getBoundingClientRect();

                // 현재 메뉴 아이템의 크기
                const rect = i.getBoundingClientRect();

                // 현재 X 좌표 값입니다. 
                // (현재 메뉴 아이템의 X좌표 - 전체 메뉴 영역의 X좌표)
                const pos = (rect.left - parentRect.x);

                // 현재 메뉴와 가장 멀리 떨어진 메뉴의 X 좌표 값입니다.
                const targetRect = menuItems[menuItems.length - idx - 1].getBoundingClientRect();
                const targetPos = (targetRect.left - parentRect.x);

                // 180 ~ 255 사이의 색상값을 16진수로 변환합니다.
                const color = (180 + Math.floor(75 * targetPos / parentRect.width)).toString(16);

                // 가상 요소의 CSS 속성을 변경합니다.
                cssRuleSet(".header-center::after", "border-bottom", `4px solid #${color}6B00`);
                cssRuleSet(".header-center::after", "left", pos + "px");
            });
        });

    }

    addEventListeners() {
        this.on("loginView:ready", () => {
            const loginButton = document.querySelector(".header-right-login-button");
            const loginView = document.querySelector(".floating-login-view-wrapper");
            const lightBox = document.querySelector("#light-box-container");

            loginButton.addEventListener("click", () => {

                // 브라우저는 최적화 문제로 인해 모든 스타일의 속성을 즉각 계산하지 않습니다.
                // 따라서 스타일 값을 가져오려면 getComputedStyle 함수를 써야 합니다.
                const styled = getComputedStyle(loginView);

                // 스타일 값에는 px 단위가 들어가있습니다.
                // parseInt를 사용하면 단위 값이 제거되고 숫자만 남습니다.
                if(parseInt(styled.left) != 0) {
                    // active 클래스를 추가하면 화면에 라이트 박스가 표시됩니다.
                    lightBox.classList.add("active");
                    loginView.style.left = "0";
                } else {
                    lightBox.classList.remove("active");
                    // display:none은 브라우저 버그로 인해 동작하지 않습니다.
                    loginView.style.left = "9999px";
                }
            });

            // 로그인 버튼을 누르면 우측에 로그인 바가 표시됩니다.
            const btn = document.querySelector("#close-login-view");
            btn.addEventListener("click", () => {
                lightBox.classList.remove("active");
                loginView.style.left = "9999px";
            })
        });      
        
        // 하위 컨텐츠를 모두 지우고 새로운 카드를 불러오기 위한 이벤트입니다.
        this.on("contents:ready", (htmlTexts) => {
            const items = Array.from(document.querySelectorAll(".card-container .card"));
            items.forEach((card, idx) => card.querySelector("p").setAttribute("d-"+idx, ""));
        });
    }

    onLoad() {
        this.emit("loginView:ready");
        this.emit("contents:ready");  
        
        // ReactDOM.render(
        //     React.createElement(CardContainer, {
        //         toWhat: "React 테스트 중입니다."
        //     }, null),
        //     document.querySelector('.card-container')
        // );        
    }

    /**
     * 새로 고침을 하지 않고 페이지를 동적으로 만들 수 있는 비동기 페이지 로더입니다.
     */
    async createLazyLoader() {
        const idx = location.href.lastIndexOf("/");
        const path = location.href.substring(0, idx);

        /**
         * await는 비동기 함수가 끝날 때 까지 대기하는 명령입니다.
         * this.loadHTML 메서드는 비동기 함수입니다.
         */
        for await(let i of this._pendingList) {
            await this.loadHTML(`${path}/${i.src}`)
            .then(result => {
                const container = document.querySelector(i.parent);
                const body = parseBodyFromString(result);

                // 새로운 <div></div>에 특정 요소를 생성합니다.
                if(i.isCreateNewDiv) {
                    const newDiv = document.createElement("div");
                    newDiv.innerHTML = body;
                    container.appendChild(newDiv);
                } else {
                    // <div></div>를 만들지 않고 하위 내용을 새로 변경합니다.
                    // 제이쿼리의 .html() 또는 .text()와 같습니다.
                    container.innerHTML = body;       
                }
    
            }).catch(err => {
                console.warn(err);
            })
        }

        this.onLoad();
    }

    /**
     * 제이쿼리 없이 구현된 AJAX 메소드입니다.
     * 
     * @param {String} src 동적으로 로드할 페이지의 경로
     */
    loadHTML(src) {
        return new Promise((resolve, reject) => {

            const xhr = new XMLHttpRequest();
            xhr.open("GET", src);
            xhr.onload = () => {
                /**
                 * Status-Code    = "100"   ; Continue(계속)
                 * "101"   ; Switching Protocols(규약 전환)
                 * "200"   ; OK
                 * "201"   ; Created(생성 되었음)
                 * "202"   ; Accepted(접수 되었음)
                 * "203"   ; Non-Authoritative Information(비 인증 정보)
                 * "204"   ; No Content (내용이 없음)
                 * "205"   ; Reset Content(내용을 지움)
                 * "206"   ; Partial Content(부분 내용)
                 * "300"   ; Multiple Choices(복수 선택)
                 * "301"   ; Moved Permanently(영구 이동)
                 * "302"   ; Moved Temporarily(임시 이동)
                 * "303"   ; See Other(다른 것을 참조)
                 * "304"   ; Not Modified(변경되지 않았음)
                 * "305"   ; Use Proxy(프락시를 사용할 것)
                 * "400"   ; Bad Request(잘못된 요구)
                 * "401"   ; Unauthorized(인증되지 않았음)
                 * "402"   ; Payment Required(요금 지불 요청)
                 * "403"   ; Forbidden(금지되었음)
                 * "404"   ; Not Found(찾을 수 없음)
                 * "405"   ; Method Not Allowed(method를 사용할 수 없음)
                 * "406"   ; Not Acceptable (접수할 수 없음)
                 * "407"   ; Proxy Authentication Required(프락시 인증 필요)
                 * "408"   ; Request Time-out(요구 시간 초과)
                 * "409"   ; Conflict(충돌)
                 * "410"   ; Gone(내용물이 사라졌음)
                 * "411"   ; Length Required(길이가 필요함)
                 * "412"   ; Precondition Failed(사전 조건 충족 실패)
                 * "413"   ; Request Entity Too Large (요구 엔터티가 너무 큼)
                 * "414"   ; Request-URI Too Large(Request-URI가 너무 김)
                 * "415"   ; Unsupported Media Type(지원되지 않는 미디어 유형)
                 * "500"   ; Internal Server Error(서버 내부 에러)
                 * "501"   ; Not Implemented(구현되지 않았음)
                 * "502"   ; Bad Gateway(불량 게이트웨이)
                 * "503"   ; Service Unavailable(서비스를 사용할 수 없음)
                 * "504"   ; Gateway Time-out(게이트웨이 시간 초과).
                 * "505"   ; HTTP Version not supported (지원되지 않는 HTTP 버전)
                 */
                if(xhr.status === 200) {
                    resolve(xhr.responseText);
                } else {
                    reject(xhr.statusText);
                }
            };
            xhr.onerror = (err) => {
                reject(error);
            }
            xhr.send(null);
        });
    }
}



const app = new App();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.app = app;

window.addEventListener("load", () => {
    app.emit("ready");
});