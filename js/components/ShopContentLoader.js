import { Component } from "./Component.js";
import {blobData, base64toBlob} from "../data.js";
import { getDataManager } from "../DataManager.js";

export class ShopContentLoader extends Component {
    
    initMembers(parent) {
        super.initMembers(parent);

        this._currentCards = 0; // 현재 카드 갯수
        this._fetchCards = 10; // 새로 가져올 카드 갯수
        this._maxCards = 50; // 최대 카드 갯수
        this._interval = 800; // 이벤트 과대 실행 방지 용 실행 간격 800ms
    }

    /**
     * 검색 기능입니다.
     * 검색된 내용과 일치하는 카드를 찾습니다.
     * @param {String}} itemName 
     */
    search(itemName) {

        $(".card")
            .hide()
            .filter(function() {
                const cardTitle = $(this).find("h2").text().toLowerCase();

                return cardTitle.includes(itemName);

            })
            .show();
    }    

    async appendCards() {
        let currentCards = this._currentCards;
        const fetchCards = this._fetchCards;
        const maxCards = this._maxCards   
        const max = Math.min(currentCards + fetchCards, maxCards);

        const parent = this._parent;

        for(let idx = currentCards; idx < (currentCards + fetchCards); idx++) {
            const card = this._items[idx];

            // let myImgData = data[idx].imgPath;
            let myImgData = await this.loadJsonAsync(`json/shop/shop_data${idx}.json`);
            // blobData[idx];
            if(!card) {
                continue;
            }

            if(myImgData) {
                
                // 카드의 데이터 ID를 설정합니다.
                card.querySelector("p").setAttribute("d-"+idx, "");

                const filename = myImgData;
                parent.createNewStyleSheet("d-"+idx, filename.url);     

                // 정규 표현식을 이용하여 한 줄로 되어있는 텍스트를 여러 줄로 잘라냅니다.
                // 정규 표현식 문법 \d는 숫자를 나타냅니다.
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

            // 카드의 갯수를 1 증감시킵니다.
            if(this._currentCards < this._maxCards)
                this._currentCards++;            
        }

    }

    run() {

        // 입력 중에 콜백 함수가 과다하게 실행되는 것을 방지하는 쓰로틀링 (또는 디바운스로 교체 가능) 함수입니다.
        const onchange = _.throttle((ev) => {
            const self = ev.currentTarget;
            this.search($(self).val());
        }, 100);

        $(".header-filter-box-footer-center input").on("change", onchange);        

        this._items = Array.from(document.querySelectorAll(".card-container .card"));

        // 로딩 직후, 새로운 카드 이미지를 바로 생성합니다.
        this.appendCards();
        
        // 스크롤 시 새로운 카드 이미지를 일정 간격마다 추가합니다.
        const throttled  = _.throttle(() => {
            this.appendCards();
        }, this._interval);

        $(window).scroll(throttled);        

    }
    
    static id() {
        return ShopContentLoader;
    }
}