import { Component } from "./Component.js";
import { itemData, imgSrc, itemImg } from "../services/itemData.js";
import { Request } from "../Request.js";
import { ImagePath } from "./ImagePath.js";

/**
 * 주소에 있는 dataId 값을 인자를 파싱하여 id 값에 맞는 데이터를 동적으로 가져옵니다.
 * 
 * @author 어진석
 */
export class ItemDetailLoader extends Component {

    run() {

        // F 또는 M 값을 매개변수로부터 파싱합니다.
        $(".header-left a").eq(1).on("click", (ev) => {
            this._dataLoader.setParameter("gndr", "F");
        })
        $(".header-left a").eq(2).on("click", (ev) => {
            this._dataLoader.setParameter("gndr", "M");
        });

        const request = new Request();

        // 매개변수에서 데이터 ID 값을 파싱합니다.
        // 이 값은 없을 수도 있습니다.
        // 없을 경우에는 뒤로 가게 됩니다.
        const paramData = request.getParameter("data");
        const raw = decodeURIComponent(escape(atob(paramData)));
        const data = JSON.parse(raw);

        if(!data) {
            console.warn("데이터가 undefined이거나 null입니다.");
            return;
        }

        if(!data) {
            alert("정상적인 접근이 아닙니다.");
            history.go(-1);
            return;
        }

        const {contentData} = data;
        const myItem = contentData[0];
        
        // 구글 드라이브 메인 링크
        let imgSrc = data.thumbnail;

        // 상품명
        $("#detail-item-title, #title").text(myItem.title);

        // 상품 가격
        $("#detail-item-price, #price").text(myItem.price);
        
        $(".allPrice").text(0);
        
        // 이미지 주소
        $(".imgArea > img").attr("src", imgSrc);

    }

    static id() {
        return ItemDetailLoader;
    }
}