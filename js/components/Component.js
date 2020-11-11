import {EventEmitter} from "../EventEmitter.js";

/**
 * 기본 컴포넌트 인터페이스입니다.
 * 
 * 이 클래스 자체로는 아무것도 하지 않습니다.
 */
export class Component extends EventEmitter {
    constructor(parent) {
        super();
        this.initMembers(parent);
    }

    initMembers(parent) {
        this._parent = parent;
    }

    run() {

    }

    /**
     * 프로미스를 이용하여 JSON 파일을 동기 방식으로 로드합니다.
     * 원래는 비동기입니다.
     * 
     * 이 메서드는 async 키워드가 선언된 메서드 내에서만 사용해야 합니다.
     * 
     * @param {String} filename 경로를 기입하세요. json/shop/shop_data1.json
     */
    loadJsonAsync(filename) {

        return new Promise((resolve, reject) => {

            $.getJSON(filename, (data) => {
                resolve(data);
            }).fail(err => {
                reject(err);
            });

        });
        
    }

    /**
     * JSON 파일을 비동기 방식으로 로드합니다.
     * 
     * @param {String} filename 경로를 기입하세요. json/shop/shop_data1.json
     */    
    loadJson(filename, successCallback, failCallback) {
        if(typeof(successCallback) !== 'function') {
            console.warn("success 콜백이 설정되지 않았습니다!");
            return;
        }
        if(typeof(failCallback) !== 'function') {
            console.warn("fail 콜백이 설정되지 않았습니다!");
            return;
        }
        $.getJSON(filename, (data) => {
            successCallback(data);
        }).fail(err => {
            failCallback(err);
        });
    }

    /**
     * 클래스의 생성자를 반환하는 정적 멤버 함수입니다.
     */
    static id() {
        return Component;
    }

    /**
     * 컴포넌트를 자체적으로 생성하는 정적 멤버 함수입니다.
     * 
     * @param  {...any} args 
     */
    static builder(...args) {
        return new (this.id())(...args);
    }

}