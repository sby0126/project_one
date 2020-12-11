import { Component } from "./Component.js";
import { itemData, imgSrc, itemImg } from "../services/itemData.js";
import {parseBodyFromString} from "./utils/bodyParser.js";

/**
 * 주소에 있는 dataId 값을 인자를 파싱하여 id 값에 맞는 데이터를 동적으로 가져옵니다.
 * 
 * @author 어진석
 */
export class ItemDetailLoaderCart extends Component {

    rltprice = 0;
    
    run() {

        const urlParams = new URLSearchParams(decodeURI(location.search));

        const dataId = parseInt(urlParams.get("dataId"));

        const item = itemData[dataId]; // 여기엔 large image, content 정보가 없음.
        
        $(".product-name").text(item.title);
        $(".product-price, .produce-rltprice").text(item.price);
        $(".product-img").attr("src", imgSrc + itemImg[item.url]);


        rltprice += Integer.parseInt(item.price);
    }

    static id() {
        return ItemDetailLoader;
    }
}