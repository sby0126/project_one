import { Component } from "./Component.js";
import { getDataManager, DataManager } from "../DataManager.js";

const config = {
    isSlow: false
};

/**
 * @type {DataManager}
 */
const DataMan = getDataManager();

class Cookie extends Component {

    /**
     * 
     * @param {String} name 쿠키의 이름을 기입하십시오
     * @param {String} value 쿠키의 값을 기입하십시오
     */
    constructor(args) {

        super();
        this.initMembers(null);
        
        // 쿠키명 설정
        this._name = args[0];

        // 쿠키값 설정
        this._value = args[1];

        // 쿠키 만료 기간 설정 (현재 시간부터 내일까지만)
        let currentDate = new Date();
        currentDate.setDate(currentDate.getDate() + 1);

        this.setMaxAge(currentDate);

        // 쿠키가 설정될 경로 설정
        this._path = "/";

    }

    /**
     * 쿠키 만료 기간을 설정합니다.
     * @param {Date} day 
     */
    setMaxAge(day) {
        this._expire = day;
    }

    /**
     * 쿠키를 생성합니다.
     */
    create() {
        let cookie = encodeURIComponent(this._name)
                    + "=" 
                    + encodeURIComponent(this._value)
                    + "; path=" 
                    + this._path
                    + "; expires="
                    + this._expire.toUTCString();

        DataMan.set(this._name, this._value);
        
        if(config.isSlow) {
            DataMan.set(this._name, this._value);
        } else {
            document.cookie = cookie;
        }
    }

    /**
     * 중복 데이터를 제거합니다.
     * 
     * @param {String}} values 
     */
    unique(values) {
        let q = values.split(",");
        let uniqueValue = q.filter((e,i,a) => {
            return a.indexOf(e.trim()) == i;
        });

        return uniqueValue.join(",");
    }

    /**
     * 
     * @param {String} myKey 
     */
    get(myKey) {
        let raw = "";

        if(config.isSlow) {
            raw = DataMan.get(myyKey);
        } else {
            raw = document.cookie;
        }
        
        /**
         * @type {Array}
         */
        let arr = raw.split(';');    

        let cookies = {};

        arr.forEach(kv => {
            const v = kv.split("=");
            const key = v[0];
            if(!v[1]) {
                return;
            }
            const value = decodeURIComponent( v[1] );

            // 중복된 값을 제거합니다.
            cookies[key] = this.unique(value);
        })

        return cookies[myKey];
        
    }
}

class ConstantCookie extends Cookie {
    constructor() {
        super("", "");
    }
}

export {Cookie, ConstantCookie};