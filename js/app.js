import {EventEmitter} from "./EventEmitter.js";
import {cssRuleSet} from "./styleRules.js";
import {parseBodyFromString, parseScriptFromString} from  "./bodyParser.js";
import {blobData, base64toBlob} from "./data.js";

window.imageBlobs = [];

/**
 * @author Eo Jinseok
 * @date 2020.11.01
 * @version v1.0.0
 */
class App extends EventEmitter {

    constructor() {
        super();

        this.initMembers();
        this.addEventListeners();
        this.initWithNav();
    }
    
    /**
     * 멤버 변수를 초기화합니다.
     */
    initMembers() {

        this._pendingList = [];

        /**
         * 동적으로 생성한 스타일 시트를 삭제하기 위해 ID 값을 저장해둡니다.
         */
        this._headStyleSheets = [];

        // 마지막 노드 추적
        this._lastModelElement = {
            container: null,
            child: null
        };

        /**
         * 모달 대화 상자(dialog)가 열려있으면 참, 아니면 거짓
         */
        this._isOpenModalDialog = false;


        this._menuIndex = 0;

    }

    /**
     * 네비게이션의 슬라이드 막대 이동 이벤트를 정의합니다.
     */
    initWithNav() {
        // 메뉴 슬라이드가 정의된 코드입니다.
        // 메뉴 슬라이드 바는 가상 요소로 정의되어있습니다.
        // 하지만 가상 요소는 DOM 쿼리 선택자로 선택이 되지 않습니다.
        // 따라서 모든 CSS 속성을 검색하여 속성을 직접 변경하는 방법을 사용하였습니다.
        // 메뉴 슬라이드 바는 색상, 위치가 클릭한 메뉴의 위치에 따라 변경됩니다.        
        const menuItems = Array.from(document.querySelectorAll(".header-center > a"));
        const lastMenuIndex = menuItems.length - 1;

        // 메뉴를 다시 처음으로 되돌립니다.
        const slideUp = () => {
            $(".header-popup-container").slideUp();
            cssRuleSet(".header-center::after", "border-bottom", `4px solid #FF6B00`);
            cssRuleSet(".header-center::after", "left", 0 + "px");
        };

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

                // 다른 메뉴를 선택하거나 다른 곳을 선택하면 닫습니다.
                if(idx === lastMenuIndex) {
                    $(".header-popup-container").slideDown();
                    $(".container").not(".header-popup-container").on("mouseup", (ev) => {

                        // 클래스 목록에 menu가 포함되어있으면 슬라이드 업을 하지 않습니다.
                        const classFilter = Array.from(ev.target.classList).filter(i => i.indexOf("menu") >= 0)
                        if(classFilter.length == 0) {
                            slideUp();
                        }
                    });
                } else {
                    if($(".header-popup-container").is(":visible")) {
                        slideUp();
                    }
                }
                
            });
        });        

        menuItems[this._menuIndex].click();
    }

    addEventListeners() {

    }

    /**
     * 라이트 박스를 표시합니다.
     */
    openLightBox() {
        const lightBox = document.querySelector("#light-box-container");
        if(!lightBox) return;
        if(lightBox.classList.contains("active")) {
            return;
        }

        lightBox.classList.add("active");
    }

    /**
     * 라이트 박스를 감춥니다.
     */
    hideLightBox() {
        const lightBox = document.querySelector("#light-box-container");
        if(!lightBox) return;
        if(!lightBox.classList.contains("active")) {
            return;
        }

        lightBox.classList.remove("active");        
    }

    /**
     * 라이트 박스를 감추거나 표시합니다.
     */
    toggleLightBox() {
        const lightBox = document.querySelector("#light-box-container");
        
        if(!lightBox) {
            return;
        }

        const isActivated = lightBox.classList.contains("active");

        // 활성화 상태라면 제거하고, 활성화 상태가 아니면 다시 표시합니다.
        if(isActivated) {
            lightBox.classList.remove("active");        
        } else {
            lightBox.classList.add("active");
        }
    }

    /**
     * 
     * @param {String} htmlFileName 
     */
    async openModalDialog(htmlFileName, scriptSrc) {
        await this.loadHTML(htmlFileName).then(resultText => {
            const body = parseBodyFromString(resultText);
            const scripts = parseScriptFromString(resultText);

            // 라이트 박스 화면에 표시
            this.openLightBox();

            // 경로 획득
            const idx = location.href.lastIndexOf("/");
            const path = location.href.substring(0, idx);            

            // 컨텐츠 삽입 위치 (부모 노드)
            const container = document.querySelector(`.contents-wrapper`);
            if(!container) {
                return;
            }

            // 모달을 만들 새로운 요소 추가
            const newDiv = document.createElement("div");
            newDiv.classList.add("modal-dialog-normal");

            // 모달 우측에 닫기 버튼 추가
            const closeButton = document.createElement("div");
            closeButton.classList.add("modal-dialog-close-button-normal");
            closeButton.innerHTML = `
                <i class="fas fa-times-circle fa-4x"></i>
            `;

            // 닫기 버튼에 클릭 이벤트 추가
            // 클릭하면 모달 상자가 닫힙니다.
            closeButton.addEventListener("click", () => {
                this.closeModalDialog();
            })
 
            newDiv.innerHTML = body;

            setTimeout(() => {
                const script = document.createElement('script');
                script.src = scriptSrc;
                document.body.appendChild(script);     
                
                Object.assign(this._lastModelElement, {
                    script: script,
                });                       
            }, 0);
       
            // 나중에 대화 상자를 제거하기 위해 마지막 대화 상자의 주소 값을 저장해둡니다.
            Object.assign(this._lastModelElement, {
                container: container,
                child: newDiv
            });

            // 컨테이너에 추가
            container.appendChild(newDiv);
            newDiv.appendChild(closeButton);

            // 대화상자를 오픈 상태로 바꿉니다.
            this._isOpenModalDialog = true;
        });
    }

    /**
     * 모달 대화 상자를 닫습니다.
     */
    closeModalDialog() {
        const loginView = document.querySelector(".floating-login-view-wrapper");
        const config = this._lastModelElement;

        // 마지막에 열린 대화 상자(노드)를 완전히 제거합니다.
        if(config.container) {
            config.container.removeChild(config.child);
            document.body.removeChild(config.script);
            this._lastModelElement = {
                container: null,
                child: null,
                script: null,
            };

            // 라이트 박스를 닫습니다.
            this.hideLightBox();
            // 로그인 뷰를 제거합니다.
            loginView.style.left = "9999px";
            // 대화 상자를 닫습니다.
            this._isOpenModalDialog = false;
        }

    }

    /**
     * 대화 상자가 열려있는가?
     * 
     * @return {Boolean}
     */
    isOpenModalDialog() {
        return this._isOpenModalDialog;
    }

    createNewStyleSheet(dataID, imagePath) {

    }

    isRoot() {
        const path = location.pathname;
        if(path.indexOf("pages") >= 0) {
            return false;
        }

        return location.pathname == "/";
    }

    toResolvePath(url) {

        if(location.host.indexOf("github.io") >= 0) {
            const items = location.href.split("/").slice(2, -1);
            if(items.includes("biud436.github.io")) {
                url = location.protocol + "//" + items.join("/") + "/";
            }
        }

        if(url.indexOf("pages") >= 0) {
            return url;
        }
        
        let items = url.split(".");
        let ext = items.pop();
        let rootFolder = "pages";
        const isRoot = this.isRoot();
        
        switch(ext) {
            default:
            case 'html':
                rootFolder = isRoot ? "pages/" : "";
                break;
            case 'js':
                rootFolder = isRoot ? "js/" : "../js/";
                break;
            case 'css':
                rootFolder = isRoot ? "css/" : "../css/";
                break;
        }

        return rootFolder + url;
    }

    onLoad() {

        $('.header-filter-box-left-shop-categories').on('change', function() {
            $('.header-filter-box-left-shop-categories').not(this).prop('checked', false);  
        });

        // 회원 가입 버튼 이벤트 등록
        document.querySelector("#join-button").addEventListener("click", () => {
            if(!this.isOpenModalDialog()) {
                this.openModalDialog(this.toResolvePath("pages/join.html"), this.toResolvePath("join.js"));
            }
        });

        // 검색 필터 박스에서 소호/브랜드 버튼 효과 구현
        const filterBoxButtons = Array.from(document.querySelector(".header-filter-box-header").children);
        filterBoxButtons.forEach((i, idx) => {
            i.addEventListener("click", async (ev) => {
                
                /**
                 * 화살표 함수에서는 this가 이벤트가 아니기 때문에 ev.currentTarget를 써야 합니다.
                 * 이것은 제이쿼리 이벤트에서 this와 같습니다.
                 * 
                 * @type {HTMLButtonElement}
                 */
                const target = ev.currentTarget;

                if(!target.classList.contains("active")) {

                    target.classList.add("active");
                    filterBoxButtons[(idx + 1) % filterBoxButtons.length].classList.remove("active");

                    // 카드 이미지를 지웁니다.
                    // 여기에서 d는 delete의 약자입니다.
                    for(let i = 0; i < this._headStyleSheets.length; i++) {
                        this.emit("card:d-" + i);
                    }

                    document.querySelector(".contents-wrapper").innerHTML = "";

                    // 카드 이미지를 생성합니다.
                    await this.loadHTML(this.toResolvePath("pages/shop.html")).then(result => {
                        const container = document.querySelector(".contents-wrapper");
                        const body = parseBodyFromString(result);
                        container.innerHTML = body;     
                        this.emit("contents:ready");   
                    });

                    // 카드 이미지를 뒤섞습니다.
                    // const shuffle = arr => arr.sort(() => Math.random() - 0.5);
                    // shuffle(blobData);

                }
            })
        });

        // 드랍 박스 화살표 방향을 바꿉니다.
        $(".header-filter-box-footer-left").on("mouseover", (ev) => {
            const isVisible = $(".header-filter-box-left-dropdown-menu").is(":visible");
            if(isVisible) {
                $(ev.currentTarget).find("i").removeClass("fa-caret-down");
                $(ev.currentTarget).find("i").addClass("fa-caret-up");
            }
        });

        $(".header-filter-box-footer-left").on("mouseout", (ev) => {
            $(ev.currentTarget).find("i").removeClass("fa-caret-up");
            $(ev.currentTarget).find("i").addClass("fa-caret-down");
        })                
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
            const myPath = this.toResolvePath(i.src);
            await this.loadHTML(myPath)
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

export {App};