import { Component } from "./Component.js";
import {blobData, base64toBlob} from "../data.js";

export class ShopContentLoader extends Component {

    run() {

        const parent = this._parent;

        const items = Array.from(document.querySelectorAll(".card-container .card"));

        items.forEach((card, idx) => {
            card.querySelector("p").setAttribute("d-"+idx, "");
            
            // let myImgData = data[idx].imgPath;
            let myImgData = blobData[idx];

            if(myImgData) {
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
        });        
    }
    
    static id() {
        return ShopContentLoader;
    }
}