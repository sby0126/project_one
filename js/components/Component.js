import {EventEmitter} from "../EventEmitter.js";

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

    static id() {
        return Component;
    }

    static builder(...args) {
        return new (this.id())(...args);
    }

}