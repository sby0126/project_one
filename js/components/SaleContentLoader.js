import { Component } from "./Component.js";
import {saleData} from "../saleData.js";

export class SaleContentLoader extends Component {

    initMembers(parent) {
        super.initMembers(parent);

        this._currentCards = 0;
        this._fetchCards = 3;
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

            let myImgData = saleData[idx];

            if(!card) {
                continue;
            }           

            if(myImgData) {
                card.querySelector("p").setAttribute("d-"+idx, "");

                const filename = myImgData;
                parent.createNewStyleSheet("d-"+idx, filename.url);     

                const myCard = card.querySelector("p");
                const {title, shop} = myImgData;

                card.insertAdjacentHTML( 'afterbegin', `
                    <i class="shop-hot-icon"></i>
                    <div class="item-button-container"> 
                        <h2>${title}</h2>
                        <p>${shop}</p>
                        <button class="like-button"></button>
                    </div>
                `);
            }            

            if(this._currentCards < this._maxCards)
                this._currentCards++;            
        }        
    }

    run() {

        this._items = Array.from(document.querySelectorAll(".card-container .card"));

        $(".card").css({
            "flex-basis": "30%",
            "height": "35rem",
        });

        this.appendCards();

        const throttled  = _.throttle(() => {
            this.appendCards();
        }, this._interval);

        $(window).scroll(throttled);        

    }

    static id() {
        return SaleContentLoader;
    }
}