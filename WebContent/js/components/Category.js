import {Component} from "./Component.js";
import { DataLoader } from "./DataLoader.js";

const _CATEGORY = {
    Type: {
        ALL: 100,
        TREND: 101,
        DANDY: 102,
        UNIQUE: 103,
        REPLICA: 104,
        STREET: 105,
        CLASSICAL: 106,
        BIG: 107,
        SHOES: 108,
        ACCESSORY: 109,
    },
    KEYS: Array.from(document.querySelectorAll("dd span")).map(i => i.innerHTML == "ALL" ? "" : i.innerHTML.trim())
};

const BOX = {
    CATEGORIES: '.header-filter-box-left-shop-categories',
    FOOTER_LEFT: ".header-filter-box-footer-left",
    DROPDOWN_MENU: ".header-filter-box-left-dropdown-menu",
    BUTTON: ".header-filter-box-footer-left-button",
}

/**
 * @link https://stackoverflow.com/questions/9907419/how-to-get-a-key-in-a-javascript-object-by-its-value
 * 
 * @param {Object} object 
 * @param {any} value 
 */
function getKeyByValue(object, value) {
    return Object.keys(object).find(key => object[key] === value);
  }

/**
 * 카테고리에 대한 모든 기능은 이 클래스에 구현됩니다.
 */
export class Category extends Component {

    initMembers(...args) {
        super.initMembers(...args);

        /**
         * @type {DataLoader}
         */
        this._urlParam = DataLoader.builder(this);

        // 주소에 카테고리 매개변수가 있으면 파싱하여 이에 맞는 카테고리로 변경합니다.
        const category = this._urlParam.category;
        // const key = getKeyByValue( _CATEGORY.Type, category );
        // this._type = _CATEGORY.Type[ key ];

        // 카테고리 인덱스를 0 ~ 9 사이로 변경합니다.
        this.changeIndex( parseInt(category) - 100 );
    }

    changeIndex(idx) {            
        // 카테고리 명을 변경합니다.
        $(`${BOX.BUTTON} em`).text( _CATEGORY.KEYS[idx] );   

        // 현재 카테고리 객체를 클릭 처리합니다.
        const currentCategory = $($(BOX.CATEGORIES).eq(idx));
        currentCategory.trigger("click");

        // 인덱스 값을 저장합니다.
        this._index = idx;
    }

    run() {

        const self = this;

        // 샵 카테고리의 드롭 박스 메뉴에 변화가 생겼을때 실행되는 이벤트입니다.
        // 현재 선택된 메뉴를 제외하고 나머지 메뉴를 전부 비활성화하여 그룹 라디오 버튼과 같은 효과를 냅니다.
        $(BOX.CATEGORIES).on('change', function() {
            $(BOX.CATEGORIES).not(this).prop('checked', false);  
            const idx = $(BOX.CATEGORIES).index($(this));
            self.changeIndex(idx);

            const params = new URLSearchParams(location.search);
            params.set("category", 100 + idx);
            location.search = params.toString();
        });

        // 드랍 박스 화살표 방향을 바꿉니다.
        $(BOX.FOOTER_LEFT).on("mouseover", (ev) => {
            const isVisible = $(BOX.DROPDOWN_MENU).is(":visible");
            if(isVisible) {
                $(ev.currentTarget).find("i").removeClass("fa-caret-down");
                $(ev.currentTarget).find("i").addClass("fa-caret-up");
            }
        });

        $(BOX.FOOTER_LEFT).on("mouseout", (ev) => {
            $(ev.currentTarget).find("i").removeClass("fa-caret-up");
            $(ev.currentTarget).find("i").addClass("fa-caret-down");
        })                  
    }

    static id() {
        return Category;
    }
}