import { Component } from "./Component.js";

class Cookie extends Component {
    constructor(name, value) {

        super();
        this.initMembers(null);
        
        // 쿠키명 설정
        this._name = name;

        // 쿠키값 설정
        this._value = value;

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
        let cookie = this._name 
                    + "=" 
                    + escape(this._value)
                    + ";path=" 
                    + this._path
                    + ";expires="
                    + this._expire.toUTCString();

        document.cookie = cookie;
    }
}

export {Cookie};