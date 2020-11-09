import { Component } from "./Component.js";
import {itemData} from "../itemData.js";

/**
 * 주소에 있는 dataId 값을 인자를 파싱하여 id 값에 맞는 데이터를 동적으로 가져옵니다.
 * 
 * @author 어진석
 */
export class ItemDetailLoader extends Component {

    run() {

        const urlParams = new URLSearchParams(decodeURI(location.search));

        const dataId = parseInt(urlParams.get("dataId"));

        const item = itemData[dataId]; // 여기엔 large image, content 정보가 없음.
        
        $("#detail-item-title").text(item.title);
        $("#detail-item-price, .allPrice").text(item.price);
        $(".imgArea > img").attr("src", itemData[dataId].url);

    }

    static id() {
        return ItemDetailLoader;
    }
}