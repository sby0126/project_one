import { Component } from "./Component.js";
import {saleData} from "../saleData.js";

export class SaleContentLoader extends Component {
    run() {

        const items = Array.from(document.querySelectorAll(".card-container .card"));
        const parent = this._parent;

        $(".card").css({
            "flex-basis": "30%",
            "height": "35rem",
        });

        items.forEach((card, idx) => {
            card.querySelector("p").setAttribute("d-"+idx, "");
            
            let myImgData = saleData[idx];

            if(myImgData) {
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
        });
    }

    static id() {
        return SaleContentLoader;
    }
}