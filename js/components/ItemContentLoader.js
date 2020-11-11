import { Component } from "./Component.js";
import { getDataManager } from "../DataManager.js";

/**
 * @author 어진석
 * @class ItemContentLoader
 * 
 */
export class ItemContentLoader extends Component {

    initMembers(parent) {
        super.initMembers(parent);

        this._currentCards = 0;
        this._fetchCards = 10;
        this._maxCards = 50;
        this._interval = 800;

        this._loaders = {};
    }

    /**
     * 특정 문자열이 카드에 포함되어있는 지 확인하고 해당 카드만 남깁니다.
     * @param {String} itemName 
     */
    search(itemName) {

        // 바닐라 자바스크립트는 너무 길어지는 관계로 제이쿼리를 사용하였습니다.
        $(".card")
            .hide()
            .filter(function() {
                // 대문자로 모두 변경합니다.
                const cardTitle = $(this).find(".item-button-container h2").text().toLowerCase();

                // 특정 단어가 포함되어있는지 확인합니다.
                return cardTitle.includes(itemName);

            })
            .show();
    }    

    appendCards() {
        // 현재 카드 갯수
        let currentCards = this._currentCards;

        // 가져올 카드 갯수
        const fetchCards = this._fetchCards;

        // 최대 카드 갯수
        const maxCards = this._maxCards   
        const max = Math.min(currentCards + fetchCards, maxCards);

        // 카드 컨테이너
        const parent = this._parent;

        // 카드를 새로 가져옵니다.
        for(let idx = currentCards; idx < (currentCards + fetchCards); idx++) {
            const card = this._items[idx];

            if(this._loaders[idx]) {
                continue;
            }            

            if(!card) {
                continue;
            }            

            this.loadJson(`json/item/item_data${idx}.json`, myImgData => {

                if(myImgData) {
    
                    this._loaders[idx] = true;
    
                    card.querySelector("p").setAttribute("d-"+idx, "");
            
                    const filename = myImgData;
                    parent.createNewStyleSheet("d-"+idx, filename.url);     
    
                    const myCard = card.querySelector("p");
                    const {title, price, shop} = myImgData;
    
                    card.onclick = function(ev) {
                        /**
                         * @type {HTMLDivElement}
                         */
                        const target = ev.currentTarget;
    
                        // 상품 명
                        const title = encodeURI(target.querySelector('h2').textContent);
                        // 상품 가격
                        const price = target.querySelectorAll('p')[0].textContent;
                        // 판매자 명
                        const shop = target.querySelectorAll('p')[1].textContent;
    
                        const dataId = idx; // 기본키
    
                        // 주소에 데이터 ID를 포함시킵니다.
                        // 이렇게 GET과 비슷한 식으로 URL을 만들면 로컬 스토리지 등을 이용하지 않아도 데이터를 간단히 전송할 수 있습니다.
                        location.href = `pages/detail.html?date=${Date.now()}&title=${title}&price=${price}&shop=${shop}&dataId=${dataId}`;
                    }
    
                    card.insertAdjacentHTML( 'afterbegin', `
                        <i class="shop-hot-icon"></i>
                        <div class="item-button-container"> 
                            <h2>${title}</h2>
                            <p>${price}</p>
                            <p>${shop}</p>
                            <button class="like-button"></button>
                        </div>
                    `);
    
                    // 카드의 갯수를 1 증감시킵니다.
                    if(this._currentCards < this._maxCards)
                        this._currentCards++;
    
                }            
            }, err => {
                console.warn(err);
            });
            
            // let myImgData = ;

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

        // 기본적으로 
        this.appendCards();

        // 전역 스크롤 이벤트 선언
        // 스크롤 할 때 마다 많은 스크롤 이벤트가 실행됩니다.
        // 그것을 막지하는 기법을 '쓰로틀링'이라고 하고, 언더스코어 라이브러리에서 지원하는 쓰토틀링 라이브러리로
        // 문제를 해결했습니다.
        const throttled  = _.throttle(() => {
            this.appendCards();
        }, this._interval);

        // 쓰로틀링 함수는 반드시 개별 전달되어야 합니다.
        $(window).scroll(throttled);
    }

    static id() {
        return ItemContentLoader;
    }
}