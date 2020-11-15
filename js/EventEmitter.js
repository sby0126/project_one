/**
 * @author Eo Jinseok
 * @date 2020.10.30
 * @version v1.0.0
 * @description
 * v1.0.0(2020.10.30)
 *  -
 * @example
 * 
 * const emitter = new EventEmitter();
 * emitter.on("ready", function(str) {
 *  alert(str);
 * });
 * 
 * window.onload = function() {
 *  emitter.emit("ready", "테스트");
 * }
 */
export class EventEmitter {

    constructor() {
        this._events = {};
    }

    /**
     * 이벤트를 등록합니다.
     * 
     * @param {String} name 등록할 이벤트 명을 기입하세요.
     * @param {Function} listeners 콜백 함수를 등록하세요.
     */
    on(name, listeners) {
        if(!this._events[name]) {
            this._events[name] = [];
        }

        if(typeof(listeners) !== "function") {
            throw new Error("listener가 함수 타입이 아닙니다.");
        }

        this._events[name].push(listeners);

        return this;
    }

    /**
     * 등록된 이벤트를 호출합니다.
     * 
     * @param {String} name 호출할 이벤트 명을 기입하세요.
     * @param  {...any} args 전달할 이벤트 매개변수를 기입하세요.
     */
    emit(name, ...args) {
        if(!(name in this._events)) {
            return;
        }

        if(!this._events[name]) {
            this._events[name] = [];
        }

        this._events[name].forEach(func => {
            if(typeof(func) === "function") {
                func.call(this, ...args);
            }
        });
    }

    /**
     * 등록된 이벤트를 삭제합니다.
     * 
     * @param {String}} name 
     */
    off(name) {
        if(!this._events[name]) {
            return;
        }        

        delete this._events[name];
    }

}