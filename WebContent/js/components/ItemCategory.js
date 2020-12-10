import { Component } from "./Component.js";

class ItemCategory extends Component {
    initMembers(...args) {
        $.ajax({
            url: "/contents/item_category.do",
            success: (data) => {
                const {categories} = data;
                this.createManually(categories);
            },
            error: (err) => {
                console.warn(err);
            }
        });
    }

    static id() {
        return ItemCategory;
    }
}
