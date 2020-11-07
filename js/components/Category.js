import {Component} from "./Component.js";

export class Category extends Component {
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