import {EventEmitter} from "./EventEmitter.js";

/**
 * @author 어진석
 * @date 2020.11.09
 */

let config = {
    numCards: 2,
    fetchCards: 5,
    maxCards: 20,
    currentCards: 0,
    scrollSpeed: 1
};

/**
 * 저장소를 사용할 수 없을 때, 다음 저장소를 사용합니다.
 * 
 * @class EmptyStorage
 */
class EmptyStorage {

    constructor() {
        this._item = {};
    }

    clear() {
        this._item = {};
    }

    get length() {
        return Object.values(this._item).length;
    }

    getItem(key) {
        return this._item[key];
    }

    setItem(key, value) {
        this._item[key] = value;
    }

    key(index) {
        const keys = Object.keys(this._item);
        return keys[index];
    }
     
}

class IDataManager extends EventEmitter {

    constructor() {
        super();
        this.initMembers();
    }

    initMembers() {
        this._isReady = false;
        this._managers = new EmptyStorage();
    }

    start() {
        if(this.checkWithLocalStorage()) {
            this._managers = localStorage;
        } else if(this.checkWithSessionStorage()) {
            this._managers = sessionStorage;
        }

        this._isReady = true;
    }

    checkWithLocalStorage() {
        if(!!localStorage == false) {
            return false;
        }
        try {
            const key = "_______test";
            localStorage.setItem(key, 0);
            localStorage.removeItem(key);
            return true;
        } catch(e) {
            return false;
        }
    }

    checkWithSessionStorage() {
        if(!!sessionStorage == false) {
            return false;
        }
        try {
            const key = "_______test";
            sessionStorage.setItem(key, 0);
            sessionStorage.removeItem(key);
            return true;
        } catch(e) {
            return false;
        }
    }

    clear() {
        this._managers.clear();
    }

    get length() {
        return this._managers.length;
    }

    getItem(key) {
        return this._managers.getItem(key);
    }

    setItem(key, value) {
        this._managers.setItem(key, value);
    }

    key(index) {
        return this._managers.key(index);
    }    

}

/**
 * 
 * @class DataManager
 * @author 어진석
 */
class DataManager extends IDataManager {
    initMembers() {
        super.initMembers();

        this.start();
        this._config = {};
    }

    load() {
        let data = this.getItem("__MjAyMDExMDk=_data");
        if(data) {
            data = JSON.parse(data);
        }
            
        // 데이터가 없으면
        if(!data) {
            data = config;
        }

        this._config = data;

    }

    set(key, value) {
        this._config[key] = value;

        this.save();
    }

    get(key) {
        this.load();

        return this._config[key];
    }

    isValid(key) {
        return !!(key in this._config);
    }

    isValidSerializeData(key) {
        return !!this.getItem(key);
    }

    save() {
        let data = this._config;
        // 데이터를 직렬화하여 저장합니다.
        this.setItem("__MjAyMDExMDk=_data", JSON.stringify(data));
    }

    /**
     * 유일한 인스턴스를 생성합니다.
     */
    static getInstance() {
        if(!DataManager.Instance) {
            DataManager.Instance = new DataManager();
        }

        return DataManager.Instance;
    }
}

DataManager.Instance = null;

/**
 * private 액세스를 위해 빌더 함수를 통해 우회 접근을 합니다.
 * 
 * @return {DataManager}
 */
function getDataManager() {
    return DataManager.getInstance();
}

export {getDataManager};