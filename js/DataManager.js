import {blobData, base64toBlob} from "./data.js";

class DataManager {
    constructor() {
        this._data = {
            menuType: 'shop'
        };
    }

    setMenuType(type) {
        this._data["menuType"] = type;
    }

    saveData(key, value) {
        localStorage.setItem(key, value);
    }

    /**
     * 
     * @param {String} category 
     */
    getData(category) {
        switch(category.toUpperCase()) {
            case 'SHOP':
                break;
            case 'ITEM':
                break;
        }
    }
}

export {DataManager};