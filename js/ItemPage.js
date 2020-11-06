import {App} from "./app.js";
import {blobData, base64toBlob} from "./data.js";
import {itemData} from "./itemData.js";
import {parseBodyFromString, parseScriptFromString} from  "./bodyParser.js";

/**
 * ==============================================
 * 아이템 페이지 구현
 * ==============================================
 */
class ItemPage extends App {
    initMembers() {
        super.initMembers();

        /**
         * 동적으로 삽입할 페이지의 경로를 기입해주세요.
         */
        this._pendingList = [
            {
                src: `../pages/login.html`,
                parent: ".container",
                isCreateNewDiv: true,
            },
            {
                src: `../pages/shop.html`,
                parent: ".contents-wrapper",
                isCreateNewDiv: false,
            }
        ];

        this._menuIndex = 1;
        
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
        const head = document.head || document.getElementsByTagName('head')[0];
        const style = document.createElement('style');

        head.appendChild(style);

        const css = `.card p[${dataID}]::before {
            content: "";
            width: 5.5em;
            height: 5.5em;
            background: url("${imagePath}") center;
            background-size: cover;
            position: absolute;
            border-radius: 50%;
            left: calc(50% - 5.5em / 2);
            top: 10%;
            z-index: 0;
        }

        .card p[${dataID}]:hover::before {
            filter: brightness(1.1);
            border-radius: 0;
            transition: all .2s linear;
        }
        
        `;

        if (style.styleSheet) {
            // IE 8
            style.styleSheet.cssText = css;
        } else {
            const child = document.createTextNode(css);
            style.appendChild(child);
            this._headStyleSheets.push(dataID);

            // 삭제 이벤트를 정의합니다.
            // 사용 예: 
            //  app.emit("card:d-0"); // 1번 카드의 스타일을 지운다.
            //  app.emit("card:d-1"); // 2번 카드의 스타일을 지운다.
            this.on(`card:${dataID}`, () => {
                const idx = this._headStyleSheets.indexOf(dataID);
                delete this._headStyleSheets[idx];

                // removeChild를 쓰면 메모리에 남아서 안지워짐.
                style.remove(child);

                // 등록된 이벤트를 제거합니다.
                setTimeout(() => {
                    this.off(`card:${dataID}`);
                }, 0);
            });
        }
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
        
        // 하위 컨텐츠를 모두 지우고 새로운 카드를 불러오기 위한 이벤트입니다.
        this.on("contents:ready", (htmlTexts) => {
            const items = Array.from(document.querySelectorAll(".card-container .card"));
            items.forEach((card, idx) => {
                card.querySelector("p").setAttribute("d-"+idx, "");
                
                // let myImgData = data[idx].imgPath;
                let myImgData = blobData[idx];

                if(myImgData) {
                    // const filename = "./test/" + myImgData.substr(myImgData.lastIndexOf("/") + 1, myImgData.length);
                    const filename = myImgData;
                    this.createNewStyleSheet("d-"+idx, filename.url);     

                    const myCard = card.querySelector("p");
                    const lines = filename.texts.replace(/([\d]+대\,[\d]+대\,[\d]+대)|([\d]+대[ ]*\,[ ]*[\d]+대)/, function(...args) {
                        return args[0] + "<br>";
                    });

                    myCard.innerHTML = `
                        <i class="shop-hot-icon"></i>
                        <h2 class="contents-shop-name">${filename.shopName}</h2>
                        <p class="shop-contents">${ lines }</p>
                        <div class="shop-button-container">
                            <button class="shop-button">전체 상품</button>
                            <button class="shop-button">
                                <p class="shop-button-text">마이샵</p>
                                <i class="shop-button-icon"></i>
                            </button>
                        </div>
                    `;
                }
            });
        });        
    }

    onLoad() {
        super.onLoad();

        // 미리 정의해놓은 이벤트 함수를 호출합니다. (제이쿼리의 trigger와 유사합니다);
        this.emit("loginView:ready");
        this.emit("contents:ready"); 
    }

}

const app = new ItemPage();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.app = app;
window.addEventListener("load", () => {
    app.emit("ready");
});