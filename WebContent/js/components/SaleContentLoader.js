import { Component } from "./Component.js";
import {saleData, imgSrc, saleImg} from "../services/saleData.js";
import { DataLoader } from "./DataLoader.js";
import { ImagePath } from "./ImagePath.js";

export class SaleContentLoader extends Component {

    initMembers(parent) {
        super.initMembers(parent);

        this._currentCards = 0; // 시작 갯수
        this._fetchCards = 3; // 새로 가져올 카드 갯수
        this._maxCards = 100; // 최대 카드 갯수
        this._interval = 800; // 스크롤 이벤트 실행 간격 (과다 실행 방지용)

        this._offset = {
            start: 20,
            end: 20 + this._fetchCards
        }; 

        /**
         * @type {DataLoader}
         */
        this._dataLoader = DataLoader.builder(this);           
    }

    /**
     * 무한 증식.....
     * @param {Number} count 
     */
    addFetchData(count) {

        const {start, end} = this._offset;

        this._dataLoader.load("sale", (data) => {

            if(data == null) {
                return;
            }

            this._data = data;

            if(this._currentCards >= this._maxCards) {
                console.log("새로 가져올 데이터가 필요합니다.");
            }
    
            this._offset.start = this._offset.end + 1;
            this._offset.end = this._offset.start + count;
    
            console.log("시작 %d, 종료: %d", this._offset.start, this._offset.end);                
    
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
        });


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

            // let myImgData = saleData[idx];
            let myImgData = this._data.contentData[idx];
            const imgSrc = this._data.imageUrl;
            const saleImg = this._data.imageData;            

            if(!card) {
                continue;
            }           

            if(myImgData) {

                // 데이터 번호를 설정합니다.
                card.querySelector("p").setAttribute("d-"+idx, "");

                const filename = myImgData;
                const {gndr} = this._dataLoader;
                // 이미지 영역을 생성합니다.
                parent.createNewStyleSheet("d-"+idx, ImagePath.getSalePath(gndr, filename.url));     

                const myCard = card.querySelector("p");
                const {title, shop} = myImgData;

                // 좋아요 버튼을 활성화 해야 할까요?
                let active = "";
                if(filename.active) {
                    active = "active";
                }

                // 카드의 내용을 훼손시키지 않고 요소의 뒤에 새로운 내용을 추가합니다.
                card.insertAdjacentHTML( 'afterbegin', `
                    <i class="shop-hot-icon" data-title="HOT"></i>
                    <div class="item-button-container"> 
                        <h2>${title}</h2>
                        <p>${shop}</p>
                        <button class="like-button ${active}"></button>
                    </div>
                `);

                $(card).find(".like-button").on("click", (ev) => {
                    const likeButton = $(card).find(".like-button");
                    const productId = filename.id;

                    const isActive = $(card).find(".like-button").hasClass("active");
                    const REST_API_TYPE = isActive ? "deleteInterest.do" : "addInterest.do";

                    // AJAX를 요청합니다.
                    $.ajax({
                        url: `/contents/${REST_API_TYPE}?productId=${productId}`,
                        method: "GET",
                        success: function(data) {
                            if(data.status === "success") {
                                likeButton.toggleClass("active");
                            }
                        },
                        error: function(err) {
                            const code = err.status;
                            if(code === 401) {
                                console.warn("로그인이 되어있지 않습니다.");
                            } else {
                                console.warn("DB 작업에 실패하였습니다.");
                            }
                        }
                    })

                    return false;                    
                });

                // 톰캣 문제로 인해 링크 안에 요소가 있으면 링크가 동작하지 않습니다.
                $(card).on("click", (ev) => {
                    window.open(filename.link, "_blank");
                    return false;
                });
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

        $(".header-left a").eq(1).on("click", (ev) => {
            this._dataLoader.setParameter("gndr", "F");
        });

        $(".header-left a").eq(2).on("click", (ev) => {
            this._dataLoader.setParameter("gndr", "M");
        });

        // 카드 이미지의 크기를 조절합니다.
        // 큰 해상도에서는 가로가 30% 입니다.
        const isMobileDevice = this.isMobileDevice();
        $(".card").css({
            "flex-basis": isMobileDevice ? "80%" : "30%",
            "height": "35rem",
        });

        // 데이터를 가져옵니다.
        this._dataLoader.initWithUrlParams();      
        this._dataLoader.load("sale", (data) => {          

            this._data = data;

            // 로딩 직후, 새로운 카드 이미지를 바로 생성합니다.
            this.appendCards();

            // 스크롤 시 새로운 카드 이미지를 일정 간격마다 추가합니다.
            const throttled  = _.throttle(() => {
                this.appendCards();
            }, this._interval);

            $(window).scroll(throttled);              
        }, {start:0, end:20});

    }

    static id() {
        return SaleContentLoader;
    }
}