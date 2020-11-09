import { Component } from "./Component.js";
import {itemData} from "../itemData.js";
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
            
            let myImgData = itemData[idx];

            if(myImgData) {

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
                    const title = encodeURI(target.querySelector('h2').textContent);
                    const price = target.querySelectorAll('p')[0].textContent;
                    const shop = target.querySelectorAll('p')[1].textContent;

                    const dataId = idx; // 기본키

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

                if(this._currentCards < this._maxCards)
                    this._currentCards++;

            }            
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
        return ItemContentLoader;
    }
}