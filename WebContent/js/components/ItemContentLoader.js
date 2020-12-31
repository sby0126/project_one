import { Component } from "./Component.js";
import { getDataManager } from "../DataManager.js";
import { itemData, imgSrc, itemImg } from "../services/itemData.js";
import { DataLoader } from "./DataLoader.js";
import { Request } from "../Request.js";
import { ImagePath } from "./ImagePath.js";

/**
 * @author 어진석
 * @class ItemContentLoader
 * 
 */
export class ItemContentLoader extends Component {

    initMembers(parent) {
        super.initMembers(parent);

        this._currentCards = 0;
        this._fetchCards = 20;
        this._maxCards = 100;
        this._interval = 800;

        this._loaders = {};
        this._index = 0;

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

        this._offset.start = this._offset.end + 1;
        this._offset.end = this._offset.start + count;

        const {start, end} = this._offset;

        this._dataLoader.load("item", (data) => {

            if(data == null) {
                return;
            }
            
            this._data = data;

            if(this._currentCards >= this._maxCards) {
                console.log("새로 가져올 데이터가 필요합니다.");
            }

            console.log("시작 %d, 종료: %d", this._offset.start, this._offset.end);        
    
            const parent = $(".card-container");
    
            for(let i = 0; i < count; i++) {
                setTimeout(() => {
                    const child = $(                `
                    <div class="card">
                        <p>
                        </p>
                    </div>                
                    `);
                    parent.append(child);
    
                    this._items.push(child.get()[0]);
                }, 0);
            }
    
            this.appendCards();
        }, {start, end});

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

        if(currentCards > this._maxCards) {
            return;
        }

        // 카드를 새로 가져옵니다.
        for(let idx = currentCards; idx < (currentCards + fetchCards); idx++) {
            let card = this._items[idx];

            if(this._loaders[idx]) {
                continue;
            }            
            
            // let myImgData = itemData[idx];

            let myImgData = this._data.contentData[idx];
            const imgSrc = this._data.imageUrl;
            const itemImg = this._data.imageData;            

            if(myImgData) {

                if(!card) {
                    const child = $(                `
                    <div class="card">
                        <p>
                        </p>
                    </div>                
                    `);

                    $(".card-container").append(child);
    
                    this._items.push(child.get()[0]);

                    card = child.get(0);
                }   

                this._loaders[idx] = true;

                card.querySelector("p").setAttribute("d-"+idx, "");
        
                const filename = myImgData;
                
                let isOtherCDN = false;
                
                /**
                 * 파일명이 http로 시작하면
                 * @type {String}
                 */
                const startUrl = itemImg[filename.url] || "";
                if(startUrl.startsWith("http")) {
                    isOtherCDN = true;
                }

                const {gndr, shopType} = this._dataLoader;
                let imgUrl = ImagePath.getItemPath(gndr, shopType, filename.url);

                if(isOtherCDN) {
                    imgUrl = filename.url;
                }
            
                parent.createNewStyleSheet("d-"+idx, imgUrl);     

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

                    const request = new Request();

                    const myData = {
                        pageType: request.getParameter("pageType") || "shop",
                        genderType: request.getParameter("gndr") || "M",
                        shopType: request.getParameter("shopType") || "S",
                        offset: {
                            start: 0,
                            count: 68,
                        },
                        imageUrl: "https://drive.google.com/uc?export=view&id=",
                        contentData: [
                            myImgData
                        ],
                        thumbnail: imgUrl,
                    };
                    // 주소에 데이터 ID를 포함시킵니다.
                    // 이렇게 GET과 비슷한 식으로 URL을 만들면 로컬 스토리지 등을 이용하지 않아도 데이터를 간단히 전송할 수 있습니다.
                    location.href = `pages/detail.jsp?data=${btoa(unescape(encodeURIComponent(JSON.stringify(myData))))}&id=${myImgData.id}`;
                }

                // 좋아요 버튼을 활성화 해야 할까요?
                let active = "";
                if(filename.active) {
                    active = "active";
                }                

                card.insertAdjacentHTML( 'afterbegin', `
                    <a href="${filename.link} target='_blank'>
                        <i class="shop-hot-icon"></i>
                        </a>
                        <div class="item-button-container"> 
                            <h2>${title}</h2>
                            <p>${price}</p>
                            <p>${shop}</p>
                            <button class="like-button ${active}" data-id=${myImgData.id}></button>
                        </div>
                `);

                $(card).find(".like-button").on("click", (ev) => {
                    const likeButton = $(card).find(".like-button");

                    const productId = likeButton.data("id")

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
                })

                // 카드의 갯수를 1 증감시킵니다.
                if(this._currentCards < this._maxCards)
                    this._currentCards++;

            }            
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

        $(".header-left a").eq(1).on("click", (ev) => {
            this._dataLoader.setParameter("gndr", "F");
        })
        $(".header-left a").eq(2).on("click", (ev) => {
            this._dataLoader.setParameter("gndr", "M");
        });

        // 카드 컨테이너 생성
        $("<div class='card-container'></div>").appendTo(".contents-wrapper");

        // 데이터를 가져옵니다.
        this._dataLoader.initWithUrlParams();
        this._dataLoader.load("item", (data) => {
            this._data = data;

            // 기본적으로 
            this.addFetchData(20);            

            // 전역 스크롤 이벤트 선언
            // 스크롤 할 때 마다 많은 스크롤 이벤트가 실행됩니다.
            // 그것을 막지하는 기법을 '쓰로틀링'이라고 하고, 언더스코어 라이브러리에서 지원하는 쓰토틀링 라이브러리로
            // 문제를 해결했습니다.
            const throttled  = _.throttle(() => {
                const count = this._fetchCards;
                if(this._data.length > this._currentCards + count) {

                    this.addFetchData(count);
                }
            }, this._interval);

            // 쓰로틀링 함수는 반드시 개별 전달되어야 합니다.
            $(window).scroll(throttled);            
        }, {start:0, end:20});


    }

    static id() {
        return ItemContentLoader;
    }
}