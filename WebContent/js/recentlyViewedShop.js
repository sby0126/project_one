import {EventEmitter} from "./EventEmitter.js";
import { getDataManager, DataManager } from "./DataManager.js";

/**
 * @type {DataManager}
 */
const DataMan = getDataManager();
const cookieName = `recentShopItems`;

class EntryPoint extends EventEmitter {
    constructor() {
        super();        
    }

    run() {

        let data = DataMan.get(cookieName);

        $.ajax({
            url: "/contents/recentlyShopList.do",
            method: "POST",
            dataType: "json",
            data: JSON.stringify({
                recentShopItems: decodeURIComponent( data )
            }),
            success: function(data) {
                console.log(data);
            },
            error: function(err) {
                alert(err);
            }
        })
    }
}

const entryPoint = new EntryPoint();
entryPoint.run();

export {EntryPoint}

