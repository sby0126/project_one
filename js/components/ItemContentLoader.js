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

                card.onclick = function(ev) {
                    /**
                     * @type {HTMLDivElement}
                     */
                    const target = ev.currentTarget;
                    const title = encodeURI(target.querySelector('h2').textContent);
                    const price = target.querySelectorAll('p')[0].textContent;
                    const shop = target.querySelectorAll('p')[1].textContent;

                    const dataId = idx; // 기본키

                    location.href = `pages/detail.html?date=${Date.now()}&title=${title}&price=${price}&shop=${shop}&dateId=${dataId}`;
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