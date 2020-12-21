import {Component} from "./Component.js";
import {Cookie, ConstantCookie} from "./Cookie.js";

const IDS = {
    SHOP: `#recently-shop-count`,
    ITEMS: `#recently-item-count`
};

class RecentlyItems extends Component {
    constructor(parent) {
        super(parent);
    }

    /**
     * 
     */
    run() {

        // 최근 본 아이템 또는 상품의 갯수를 정수로 가져옵니다.
        const cookie = new ConstantCookie();
        let raw = decodeURIComponent( cookie.get(`recentShopItems`) ) || "";
        let count1 = 0;
        let count2 = 0;
        /**
         * @type {Array}
         */
        let items = [];

        // 최근 샵 갯수
        items = raw.split(",").map(i => i.trim());

        count1 = items.length || 0;
        
        $(IDS.SHOP).text(count1);

        // 최근 아이템 갯수
        raw = decodeURIComponent( cookie.get("recentlyItems") ) || "";
        items = raw.split(",").map(i => i.trim());
        count2 = items.length || 0;

        $(IDS.ITEMS).text(count2);
    }

    static id() {
        return RecentlyItems;
    }
}

export {RecentlyItems};