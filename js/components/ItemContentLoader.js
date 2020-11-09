import { Component } from "./Component.js";
import {itemData} from "../itemData.js";

export class ItemContentLoader extends Component {
    run() {
        const items = Array.from(document.querySelectorAll(".card-container .card"));
        const parent = this._parent;

        items.forEach((card, idx) => {
            card.querySelector("p").setAttribute("d-"+idx, "");
            
            let myImgData = itemData[idx];

            if(myImgData) {
        
                const filename = myImgData;
                parent.createNewStyleSheet("d-"+idx, filename.url);     

                const myCard = card.querySelector("p");
                const {title, price, shop} = myImgData;

                card.onclick = function() {
                    location.href = "pages/detail.html";
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
            }
        });
    }

    static id() {
        return ItemContentLoader;
    }
}