/**
 * @author 어진석(Eo Jinseok)
 * @date 2020.10.30
 * @description
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
class EventEmitter {

    constructor() {
        this._events = {};
    }

    /**
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
    }

    /**
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

}