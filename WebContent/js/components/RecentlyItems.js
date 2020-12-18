import {Component} from "./Component.js";
import {Cookie, ConstantCookie} from "./Cookie.js";

const cookieName = `recentShopItems`;

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
        let raw = cookie.get(cookieName) || "";
        let count = 0;
        /**
         * @type {Array}
         */
        let items = [];

        items = raw.split(",").map(i => i.trim());

        count = items.length || 0;

        $(IDS.SHOP).text(count);
    }

    static id() {
        return RecentlyItems;
    }
}

export {RecentlyItems};