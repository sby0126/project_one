import { Component } from "./Component.js";

export class CardStyleSheetBuilder extends Component {
    /**
     * 
     * @param {App} parent 
     * @param {String} css 
     * @param {String} dataID 
     */
    constructor(parent, css, dataID) {
        super(parent);
        this._css = css;
        this._dataID = dataID;
    }

    run() {

        const parent = this._parent;
        const css = this._css;
        const dataID = this._dataID;

        const head = document.head || document.getElementsByTagName('head')[0];
        const style = document.createElement('style');

        head.appendChild(style);

        if (style.styleSheet) {
            // IE 8
            style.styleSheet.cssText = css;
        } else {
            const child = document.createTextNode(css);
            style.appendChild(child);
            parent._headStyleSheets.push(dataID);

            // 삭제 이벤트를 정의합니다.
            // 사용 예: 
            //  app.emit("card:d-0"); // 1번 카드의 스타일을 지운다.
            //  app.emit("card:d-1"); // 2번 카드의 스타일을 지운다.
            parent.on(`card:${dataID}`, () => {
                const idx = parent._headStyleSheets.indexOf(dataID);
                delete parent._headStyleSheets[idx];

                // removeChild를 쓰면 메모리에 남아서 안지워짐.
                style.remove(child);

                // 등록된 이벤트를 제거합니다.
                setTimeout(() => {
                    parent.off(`card:${dataID}`);
                }, 0);
            });
        }
    }

    static id() {
        return CardStyleSheetBuilder;
    }
}