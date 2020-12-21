import {EventEmitter} from "./EventEmitter.js";
import { getDataManager, DataManager } from "./DataManager.js";
import { ShopContentLoader } from "./components/ShopContentLoader.js";
import { JoinButton } from "./components/JoinButton.js";
import { RecentlyItems } from "./components/RecentlyItems.js";
import { CardStyleSheetBuilder } from "./components/CardStyleSheetBuilder.js";
import {App, ID} from "./app.js";
import {LoginButton} from "./components/LoginButton.js";

/**
 * @type {DataManager}
 */
const DataMan = getDataManager();
const cookieName = `recentShopItems`;

class EntryPoint extends EventEmitter {

    initMembers() {
        super.initMembers();
        
        this.adapter = new App();

        this.adapter.initMembers();
        this.addEventListeners();
        this.adapter.initWithNav();

        this._pendingList = [
            {
                src: `pages/shop.jsp`,
                parent: ID.CONTENTS_WRAPPER,
                isCreateNewDiv: false,
            }    
        ];

        // 어댑터 선언
    }

    createNewStyleSheet(dataID, imagePath) {

        // 카드 스타일 시트 빌더는 특정한 이미지를 원형으로 만들어냅니다.
        CardStyleSheetBuilder.builder(this, `
        
            .card p[${dataID}]::before {
                content: "";
                width: 5.5em;
                height: 5.5em;
                background: url("${imagePath}") center;
                background-size: cover;
                position: absolute;
                border-radius: 50%;
                left: calc(50% - 5.5em / 2);
                top: 10%;
                z-index: 0;
            }

            .card p[${dataID}]:hover::before {
                filter: brightness(1.1);
                border-radius: 0;
                transition: all .2s linear;
            }
        
        `, dataID).run();
    }   
    
    addEventListeners() {
        /**
         * 1. 컨텐츠 로더 생성
         * @type {ShopContentLoader}
         */
        this._contentLoader = ShopContentLoader.builder(this);

        this.on("login:ready", () => LoginButton.builder().run());
        this.on("contents:ready", () => this._contentLoader.run());
    }

    onLoad() {
        JoinButton.builder(this).run();

        this.run();

        RecentlyItems.builder(this).run();

        this.emit("login:ready");
        this.emit("contents:ready");
    }

    /**
     * 새로운 데이터를 가져옵니다.
     */
    fetchNewData() {
        if(this._contentLoader) {
            this._contentLoader.addFetchData(5);
        }
    }

    run() {

        // 서버에서 데이터를 받아옵니다.
        let data = DataMan.get(cookieName);

        $.ajax({
            url: "/contents/recentlyShopList.do",
            method: "POST",
            dataType: "json",
            data: JSON.stringify({
                recentShopItems: decodeURIComponent( data )
            }),
            success: function(data) {
                
                const {contentData} = data;

                if(contentData.length > 0)
                    $(".contents-wrapper .card-container").empty();
                
                const pre = $("<pre></pre>");
                $(".card-container").append(pre);
            },
            error: function(err) {
                // alert(err);
            }
        });


    }
}

const entryPoint = new EntryPoint();
entryPoint.run();

entryPoint.on("ready", async () => app.adapter.createLazyLoader());
window.addEventListener("load", () => entryPoint.emit("ready"));

export {EntryPoint}

