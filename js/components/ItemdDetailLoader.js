import { Component } from "./Component.js";
import {itemData} from "../itemData.js";

export class ItemDetailLoader extends Component {
    run() {
        const urlParams = new URLSearchParams(decodeURI(location.search));

        const dataId = parseInt(urlParams.get("dataId"));

        const item = itemData[dataId];
        let content;

        if("content" in item) {
            content = item.content;
        }
        
        $("#detail-item-title").text(item.title);
        $("#detail-item-price, .allPrice").text(item.price);

    }

    static id() {
        return ItemDetailLoader;
    }
}