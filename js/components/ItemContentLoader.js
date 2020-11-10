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
        this._fetchCards = 10;
        this._maxCards = 50;
        this._interval = 800;
    }

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
        let currentCards = this._currentCards;
        const fetchCards = this._fetchCards;
        const maxCards = this._maxCards   
        const max = Math.min(currentCards + fetchCards, maxCards);

        const parent = this._parent;

        for(let idx = currentCards; idx < (currentCards + fetchCards); idx++) {
            const card = this._items[idx];
            
            let myImgData = itemData[idx];

            if(!card) {
                continue;
            }            

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

        const onchange = _.throttle((ev) => {
            const self = ev.currentTarget;
            this.search($(self).val());
        }, 100);

        $(".header-filter-box-footer-center input").on("change", onchange);

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