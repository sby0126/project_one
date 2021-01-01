
class Item {
    constructor() {
        this.name = "";
        this.label = "";
        this.qty = 0;
    }

    setName(value) {
        this.name = value;
    }

    setLabel(value) {
        this.label = value;
    }

    setQuantity(value) {
        this.qty = value;
    }
}

class Payment {
    constructor() {

    }

    processPay() {
        
    }
}

class Manager {

    static getTitle() {
        return $("#detail-item-title").text();
    }

    static getAllPrice() {
        return $(".allPrice").text();
    }

    static getAmount() {
        return $(".add_button").attr("value");
    }

    static getProductId() {
        return new URLSearchParams(location.search).get("id");
    }

    static getPrice() {
        return $("#detail-item-price").text();
    }

    static getOption() {
        let itemList = [];

        $("div[data-value*=pd_]").each((i, e) => {
            const item = new Item();

            const label = $(e).find("input:first");
            item.setLabel(label.val())

            const qty = $(e).find("input:last")
            item.setQuantity(qty.val());

            itemList.push(item);
        });

        return itemList;
    }

    static toJson() {

        const title = this.getTitle();
        const amount = this.getAmount();
        const price = this.getPrice();
        const productId = this.getProductId();
        const options = this.getOption();

        const data = {
            title,
            amount,
            price,
            productId,
            options
        }

        return data;
    }
}
