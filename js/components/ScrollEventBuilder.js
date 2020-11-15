import { Component } from "./Component.js";

export class ScrollEventBuilder extends Component {
    constructor(parent) {
        super(parent);
    }

    run() {
        const windowDOM = $(window);

        windowDOM.on("scroll", () => {   
            if($(window).scrollTop() + $(window).height() == $(document).height()) {
                this.emit("fetch");
            }
        });                
    }

    static id() {
        return ScrollEventBuilder;
    }
}