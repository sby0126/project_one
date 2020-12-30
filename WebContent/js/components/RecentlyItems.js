import {Component} from "./Component.js";
import {Cookie, ConstantCookie, getCookie} from "./Cookie.js";

const IDS = {
    SHOP: `#recently-shop-count`,
    ITEMS: `#recently-item-count`
};

class RecentlyItems extends Component {
    constructor(parent) {
        super(parent);
    }

    unique(array) {
        // return [...new Set(array)];
        return array.filter((e, i, a) => {
            return a.indexOf(e) == i;
        });
    }

    /**
     * 
     */
    run() {

        // 최근 본 아이템 또는 상품의 갯수를 정수로 가져옵니다.
        const cookie = new ConstantCookie();
        let raw = getCookie(`recentShopItems`) || "";

        let count1 = 0;
        let count2 = 0;
        /**
         * @type {Array}
         */
        let items = [];

        // 최근 샵 갯수
        items = raw.split(",").map(i => i.trim()).filter(i => {
            // 정규표현식을 이용한 정교한 숫자 체크
            if(/[\d]+/g.exec(i)) {
                return true;
            }
            return false;
        });

        items = this.unique(items);

        count1 = items.length || 0;
        
        $(IDS.SHOP).text(count1);

        // 최근 아이템 갯수
        let raw2 = getCookie("recentlyItems") || "";

        console.log("최근 아이템 갯수 : " + raw2, cookie.get("recentlyItems"));

        let items2 = raw2.split(",").map(i => i.trim()).filter(i => {
            // 정규표현식을 이용한 정교한 숫자 체크
            if(/[\d]+/g.exec(i)) {
                return true;
            }
            return false;
        });

        items2 = this.unique(items2);

        count2 = items2.length || 0;

        $(IDS.ITEMS).text(count2);
    }

    static id() {
        return RecentlyItems;
    }
}

export {RecentlyItems};