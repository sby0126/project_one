
class Item {
    constructor() {
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

    toJson() {
        const {label, qty} = this;
        
        return {
            label,
            qty
        };
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

            // 색상&사이즈
            const label = $(e).find("input:first");
            item.setLabel(label.val())

            // 수량
            const qty = $(e).find("input:last")
            item.setQuantity(qty.val());

            itemList.push(item.toJson());
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

    static processPay() {
        const json = JSON.stringify(this.toJson());

        const form = document.createElement("form");
        form.action = "/contents/pay.do";
        form.method = "POST";
        
        const input = document.createElement("input");
        input.type = "text";
        input.value = json;
        input.name = "data";
        
        form.appendChild(input);

        document.body.appendChild(form);

        form.submit();
    }
}
