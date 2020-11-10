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