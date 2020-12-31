import { Component } from "./Component.js";
import {blobData, imgSrc, mainImg} from "../services/data.js";
import { DataLoader } from "./DataLoader.js";
import { ImagePath } from "./ImagePath.js";

export class ShopContentLoader extends Component {
    
    initMembers(parent) {
        super.initMembers(parent);

        this._currentCards = 0; // 현재 카드 갯수
        this._fetchCards = 20; // 새로 가져올 카드 갯수
        this._maxCards = 100; // 최대 카드 갯수
        this._interval = 100; // 이벤트 과대 실행 방지 용 실행 간격 100ms
        this._data = {};

        this._offset = {
            start: 20,
            end: 20 + this._fetchCards
        };

        this._loaders = {};

        this._isLocalMode = true;
        
        /**
         * @type {DataLoader}
         */
        this._dataLoader = DataLoader.builder(this);
    }

    /**
     * 새로운 데이터를 가져옵니다.
     * 
     * @param {Number} count 
     */
    addFetchData(count) {
    
        this._offset.start = this._offset.end + 1;
        this._offset.end = this._offset.start + count;

        const {start, end} = this._offset;

        if(this._currentCards >= this._maxCards) {
            alert("마지막 페이지 입니다.");
            return;
        }        

        this._dataLoader.load("shop", (data) => {

            if(data == null) {
                return;
            }

            this._data = data;
            this._maxCards = this._data.contentData.length;

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

        if(currentCards > this._maxCards) {
            return;
        }

        // 새로운 데이터를 가져옵니다.
        for(let idx = currentCards; idx < (currentCards + fetchCards); idx++) {
            const card = this._items[idx];

            if(this._loaders[idx]) {
                continue;
            }

            let myImgData = this._data.contentData[idx];
            const imgSrc = this._data.imageUrl;
            const mainImg = this._data.imageData;

            if(!card) {
                continue;
            }

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
                
                // 카드의 데이터 ID를 설정합니다.
                card.querySelector("p").setAttribute("d-"+idx, "");

                const filename = myImgData;

                // 로컬 모드일 경우, 로컬에 있는 이미지 데이터를 사용합니다.
                if(this._isLocalMode) {
                    const {gndr, shopType} = this._dataLoader;
                    parent.createNewStyleSheet("d-"+idx, ImagePath.getShopPath(gndr, shopType, filename.url));     
                } else {
                    parent.createNewStyleSheet("d-"+idx, imgSrc + mainImg[filename.url]);     
                }
                
                // 정규 표현식을 이용하여 한 줄로 되어있는 텍스트를 여러 줄로 잘라냅니다.
                // 정규 표현식 문법 \d는 숫자를 나타냅니다.
                const myCard = card.querySelector("p");
                const lines = filename.texts.replace(/([\d]+대\,[\d]+대\,[\d]+대)|([\d]+대[ ]*\,[ ]*[\d]+대)/, function(...args) {
                    return args[0] + "<br>";
                });

                // 마이샵 버튼을 활성화할 것인가?
                let active = "";
                if(filename.active) {
                    active = "active";
                }
                if(filename.isMyShop) {
                    active += " isMyShop";
                }

                $(card.querySelector("p")).on("click", () => {
                    window.open(filename.link, "_blank");
                })

                $(myCard).html(`
                    <a href="${filename.link}" target='_blank'>
                        <i class="shop-hot-icon" data-title="HOT"></i>
                        <h2 class="contents-shop-name">${filename.shopName}</h2>
                        <p class="shop-contents">${ lines }</p>
                        <div class="shop-button-container" data-id="${filename.id}">
                            <button class="shop-button all-item-button">전체 상품</button>
                            <button class="shop-button myshop-button ${active}" onclick="return false">
                                <p class="shop-button-text" onclick="return false">마이샵</p>
                                <i class="shop-button-icon"></i>
                            </button>
                        </div>                    
                    </a>
                `);

                // 상위 노드에 걸린 클릭 이벤트의 실행을 방지합니다.
                $(myCard).find(".shop-button-container button").on("click", (ev) => {
                    return false;
                });

                const myShopBtnClassName = `div[data-id='${filename.id}'] > .myshop-button`;

                // 마이샵 버튼을 눌렀을 때의 처리
                $(myShopBtnClassName).on("click", function(ev) {
                    const parent = $(this).parent();
                    const id = parent.data("id");
                    
                    const isActive = $(myShopBtnClassName).hasClass("active");

                    const REST_API_TYPE = isActive ? "deleteMyShop.do" : "addMyShop.do";

                    $.ajax({
                        url: `/contents/${REST_API_TYPE}?shopId=${filename.id}`,
                        method: "GET",
                        success: function(data) {
                            if(data && data.status === "success") {
                                $(myShopBtnClassName).toggleClass("active");
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
                    });

                    return false;
                });

                // 전체 상품 버튼이 클릭되었을 때 실행되어야 하는 내용을 정의하세요.
                $(`div[data-id='${filename.id}'] > button.all-item-button`).on("click", (ev) => {
                    const parent = $(ev.currentTarget).parent();
                    const id = filename.id;

                    // AJAX 호출
                    $.ajax({
                        url: `/contents/searchShopItem.do?pageType=item&id=${id}`,
                        method: "GET",
                        success: function(data) {
                            location.href = "/pages/shop-detail.jsp?id=" + id;
                        },
                        error: function(err) {
                            console.log(`${id}에 대한 상품 데이터가 없습니다.`);
                            location.href = "/pages/shop-detail.jsp?id=" + id;
                        }
                    })

                });

            }

            // 카드의 갯수를 1 증감시킵니다.
            if(this._currentCards < this._maxCards)
                this._currentCards++;            
        }

    }

    removeEmptyChildren() {
        // 카드 컨테이너 선언
        const cardContainer = $(".card-container");

        cardContainer.each((index, elem) => {
            
        });
    }

    run() {

        // 입력 중에 콜백 함수가 과다하게 실행되는 것을 방지하는 쓰로틀링 (또는 디바운스로 교체 가능) 함수입니다.
        const onchange = _.throttle((ev) => {
            const self = ev.currentTarget;
            this.search($(self).val());
        }, 100);

        $(".header-filter-box-footer-center input").on("change", onchange);        

        this._items = Array.from(document.querySelectorAll(".card-container .card"));

        // 클릭 시 정렬 기준을 여성으로 변경
        $(".header-left a").eq(1).on("click", (ev) => {
            this._dataLoader.setParameter("gndr", "F");
        })
        // 클릭 시 정렬 기준을 남성으로 변경
        $(".header-left a").eq(2).on("click", (ev) => {
            this._dataLoader.setParameter("gndr", "M");
        })

        // 데이터를 가져옵니다.
        this._dataLoader.initWithUrlParams();
        this._dataLoader.load("shop", (data) => {
            
            /**
             * @type {Array}
             */
            this._data = data;

            // 로딩 직후, 새로운 카드 이미지를 바로 생성합니다.
            this.addFetchData(20);  
            
            // 스크롤 시 새로운 카드 이미지를 일정 간격마다 추가합니다.
            const throttled  = _.throttle(() => {
                const count = this._fetchCards;
                if(this._data.length > this._currentCards + count) {

                    this.addFetchData(count);
                }
            }, this._interval);

            $(window).scroll(throttled);               
        }, {start:0, end:20});

    }
    
    static id() {
        return ShopContentLoader;
    }
}