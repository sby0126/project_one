import { Component } from "./Component.js";
import {blobData, base64toBlob} from "../data.js";
import { getDataManager } from "../DataManager.js";

export class ShopContentLoader extends Component {
    
    initMembers(parent) {
        super.initMembers(parent);

        this._currentCards = 0;
        this._fetchCards = 5;
        this._maxCards = 50;
        this._interval = 800;
    }

    appendCards() {
        let currentCards = this._currentCards;
        const fetchCards = this._fetchCards;
        const maxCards = this._maxCards   
        const max = Math.min(currentCards + fetchCards, maxCards);

        const parent = this._parent;

        for(let idx = currentCards; idx < (currentCards + fetchCards); idx++) {
            const card = this._items[idx];

            // let myImgData = data[idx].imgPath;
            let myImgData = blobData[idx];

            if(!card) {
                continue;
            }

            if(myImgData) {
                
                card.querySelector("p").setAttribute("d-"+idx, "");

                // const filename = "./test/" + myImgData.substr(myImgData.lastIndexOf("/") + 1, myImgData.length);
                const filename = myImgData;
                parent.createNewStyleSheet("d-"+idx, filename.url);     

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

            if(this._currentCards < this._maxCards)
                this._currentCards++;            
        }

    }

    run() {

        this._items = Array.from(document.querySelectorAll(".card-container .card"));

        this.appendCards();
        
        const throttled  = _.throttle(() => {
            this.appendCards();
        }, this._interval);

        $(window).scroll(throttled);        

    }
    
    static id() {
        return ShopContentLoader;
    }
}