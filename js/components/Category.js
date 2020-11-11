import {Component} from "./Component.js";

const _CATEGORY = {
    Type: {
        ALL: 0,
        TREND: 1,
        DANDY: 2,
        UNIQUE: 3,
        REPLICA: 4,
        STREET: 5,
        CLASSICAL: 6,
        BIG: 7,
        SHOES: 8,
        ACCESSORY: 9,
    },
    KEYS: ["ALL", "TREND", "DANDY", "UNIQUE", "REPLICA", "STREET", "CLASSICAL", "BIG", "SHOES", "ACCESSORY"]
};

/**
 * 카테고리에 대한 모든 기능은 이 클래스에 구현됩니다.
 */
export class Category extends Component {

    initMembers(...args) {
        super.initMembers(...args);

        this._type = _CATEGORY.Type.ALL;
    }

    run() {
        // 샵 카테고리의 드롭 박스 메뉴에 변화가 생겼을때 실행되는 이벤트입니다.
        // 현재 선택된 메뉴를 제외하고 나머지 메뉴를 전부 비활성화하여 그룹 라디오 버튼과 같은 효과를 냅니다.
        $('.header-filter-box-left-shop-categories').on('change', function() {
            $('.header-filter-box-left-shop-categories').not(this).prop('checked', false);  
        });

        // 드랍 박스 화살표 방향을 바꿉니다.
        $(".header-filter-box-footer-left").on("mouseover", (ev) => {
            const isVisible = $(".header-filter-box-left-dropdown-menu").is(":visible");
            if(isVisible) {
                $(ev.currentTarget).find("i").removeClass("fa-caret-down");
                $(ev.currentTarget).find("i").addClass("fa-caret-up");
            }
        });

        $(".header-filter-box-footer-left").on("mouseout", (ev) => {
            $(ev.currentTarget).find("i").removeClass("fa-caret-up");
            $(ev.currentTarget).find("i").addClass("fa-caret-down");
        })                        
    }

    static id() {
        return Category;
    }
}