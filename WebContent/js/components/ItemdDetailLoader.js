import { Component } from "./Component.js";
import { itemData, imgSrc, itemImg } from "../services/itemData.js";

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
        
<<<<<<< Updated upstream
        $("#detail-item-title").text(item.title);
        $("#detail-item-price, .allPrice").text(item.price);
        $(".imgArea > img").attr("src", imgSrc + itemImg[item.url]);
=======
        // 구글 드라이브 메인 링크
        let imgSrc = data.thumbnail;

        // 상품명
        $("#detail-item-title").text(myItem.title);
        $("#title").attr("value", myItem.title);

        // 상품 가격
        $("#detail-item-price, .allPrice").text(myItem.price);
        $("#price").attr("value", myItem.price);

        // 이미지 주소
        $(".imgArea > img").attr("src", imgSrc);
        $("#img").attr("value", imgSrc);
>>>>>>> Stashed changes

    }

    static id() {
        return ItemDetailLoader;
    }
}