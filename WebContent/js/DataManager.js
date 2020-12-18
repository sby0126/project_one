import {EventEmitter} from "./EventEmitter.js";

/**
 * @author 어진석
 * @date 2020.11.09
 * 
 * 
 * 본 파일은 EmptyStorage, IDataManager, DataManager 클래스를 정의하고 있습니다.
 * 
 * DataManager 클래스를 인스턴스를 하나만 생성합니다.
 */

let config = {
    numCards: 2,
    fetchCards: 5,
    maxCards: 20,
    currentCards: 0,
    scrollSpeed: 1
};

/**
 * 로컬 스토리지 또는 세션 스토리지가 없을 때 다음 클래스의 인스턴스를 생성하여 메모리에 저장합니다.
 * 
 * 이 값은 구조상 페이지가 바뀌거나 브라우저가 종료되면 소실됩니다.
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
        // 로컬 스토리지 또는 세션 스토리지가 지원되지 않을 경우, 사용할 공통 인터페이스입니다.
        this._managers = new EmptyStorage();
    }

    /**
     * 데이터베이스로 사용할 브라우저 저장소를 선택합니다.
     * 로컬 스토리지는 브라우저가 종료된 이후에도 데이터가 남습니다.
     * 세션 스토리지는 브라우저가 종료되거나 호스트가 달라지면 데이터가 지워집니다. 
     */
    start() {
        // 로컬 스토리지를 지원하는 지 확인합니다.
        if(this.checkWithLocalStorage()) {
            this._managers = localStorage;
        } else if(this.checkWithSessionStorage()) { 
            this._managers = sessionStorage; // 세션 스토리지를 지원하는 지 확인합니다.
        }

        this._isReady = true;
    }

    /**
     * 로컬 스토리지가 있는 지 확인합니다.
     * 로컬 스토리지는 비교적 최신 브라우저에서만 지원합니다.
     * 이 함수는 MDN에서 구현할 것을 권장하고 있습니다.
     */
    checkWithLocalStorage() {
        // 자바스크립트에서 객체 앞에 느낌표를 두 개 붙이면 true 또는 false만을 반환합니다.
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

    /**
     * 세션 스토리지가 있는 지 확인합니다.
     * 세션 스토리지는 비교적 최신 브라우저에서만 지원합니다.
     * 이 함수는 MDN에서 구현할 것을 권장하고 있습니다.
     */    
    checkWithSessionStorage() {
        // 자바스크립트에서 객체 앞에 느낌표를 두 개 붙이면 true 또는 false만을 반환합니다.
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
 * 이 클래스는 인스턴스를 단 하나만 만듭니다.
 * 본래 이런 패턴은 사용을 지양하고 있으나 금기시할 정도는 아닙니다.
 * 
 * @class DataManager
 * @author 어진석
 */
class DataManager extends IDataManager {
    initMembers() {
        super.initMembers();

        // 데이터 베이스로 사용할 저장소를 설정합니다.
        this.start();

        this._config = {};
    }

    load() {
        
        // 브라우저 저장 공간은 누구나 접근할 수 있기 때문에 알아볼 수 없게 base64로 인코딩하였습니다.
        // 그러나 암호화가 아니기 때문에 다시 복구할 수도 있습니다.
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

    /**
     * 데이터를 저장합니다.
     * 
     * @param {String}} key 
     * @param {String} value 
     */
    set(key, value) {
        this._config[key] = value;

        this.save();
    }

    get(key) {
        this.load();

        return this._config[key];
    }

    /**
     * 키가 있는 지 확인하는 메서드입니다.
     * 
     * @param {String} key 
     */
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
 * 자바스크립트에서는 구조상 private를 쓸 수 없기 때문에 이런 방법을 선택하였습니다.
 * 
 * @return {DataManager}
 */
function getDataManager() {
    return DataManager.getInstance();
}

export {getDataManager, DataManager};