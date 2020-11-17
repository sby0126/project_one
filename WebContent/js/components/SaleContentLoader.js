import { Component } from "./Component.js";
import {saleData, imgSrc, saleImg} from "../services/saleData.js";

export class SaleContentLoader extends Component {

    initMembers(parent) {
        super.initMembers(parent);

        this._currentCards = 0; // 시작 갯수
        this._fetchCards = 3; // 새로 가져올 카드 갯수
        this._maxCards = 50; // 최대 카드 갯수
        this._interval = 800; // 스크롤 이벤트 실행 간격 (과다 실행 방지용)
    }

    /**
     * 무한 증식.....
     * @param {Number} count 
     */
    addFetchData(count) {

        if(this._currentCards >= this._maxCards) {
            console.log("새로 가져올 데이터가 필요합니다.");
        }

        const parent = $(".card-container");

        for(let i = 0; i < count; i++) {
            setTimeout(() => {
                const lastChildCount = document.querySelector(".card-container").children.length;
                const cloneNode = document.querySelector(".card").cloneNode(true);
                cloneNode.querySelector("p").setAttribute("d-"+(lastChildCount+i), "");
                this._currentCards++;
                parent.append(cloneNode);
            }, 0);
        }
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
                const cardTitle = $(this).find(".item-button-container h2").text().toLowerCase();

                return cardTitle.includes(itemName);

            })
            .show();
    }

    appendCards() {
        let currentCards = this._currentCards; // 현재 카드
        const fetchCards = this._fetchCards; // 새로 가져올 카드 갯수
        const maxCards = this._maxCards   
        const max = Math.min(currentCards + fetchCards, maxCards);

        const parent = this._parent;

        if(currentCards > this._maxCards) {
            return;
        }

        // 새로운 카드를 여러장 추가합니다.
        for(let idx = currentCards; idx < (currentCards + fetchCards); idx++) {
            const card = this._items[idx];

            let myImgData = saleData[idx];

            if(!card) {
                continue;
            }           

            if(myImgData) {

                // 데이터 번호를 설정합니다.
                card.querySelector("p").setAttribute("d-"+idx, "");

                const filename = myImgData;
                // 이미지 영역을 생성합니다.
                parent.createNewStyleSheet("d-"+idx, imgSrc + saleImg[filename.url]);     

                const myCard = card.querySelector("p");
                const {title, shop} = myImgData;

                // 카드의 내용을 훼손시키지 않고 요소의 뒤에 새로운 내용을 추가합니다.
                card.insertAdjacentHTML( 'afterbegin', `
                    <i class="shop-hot-icon" data-title="HOT"></i>
                    <div class="item-button-container"> 
                        <h2>${title}</h2>
                        <p>${shop}</p>
                        <button class="like-button"></button>
                    </div>
                `);
            }            

            // 현재 카드의 갯수를 1 증가시킵니다.
            if(this._currentCards < this._maxCards)
                this._currentCards++;            
        }        
    }

    isMobileDevice() {
        return (/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase()));
    }

    run() {

        // 입력 중에 콜백 함수가 과다하게 실행되는 것을 방지하는 쓰로틀링 (또는 디바운스로 교체 가능) 함수입니다.
        const onchange = _.throttle((ev) => {
            const self = ev.currentTarget;
            this.search($(self).val());
        }, 100);

        $(".header-filter-box-footer-center input").on("change", onchange);
        

        this._items = Array.from(document.querySelectorAll(".card-container .card"));

        // 카드 이미지의 크기를 조절합니다.
        // 큰 해상도에서는 가로가 30% 입니다.
        const isMobileDevice = this.isMobileDevice();
        $(".card").css({
            "flex-basis": isMobileDevice ? "80%" : "30%",
            "height": "35rem",
        });

        // 로딩 직후, 새로운 카드 이미지를 바로 생성합니다.
        this.appendCards();

        // 스크롤 시 새로운 카드 이미지를 일정 간격마다 추가합니다.
        const throttled  = _.throttle(() => {
            this.appendCards();
        }, this._interval);

        $(window).scroll(throttled);        

    }

    static id() {
        return SaleContentLoader;
    }
}