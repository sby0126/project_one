class Request {
    constructor() {
        this._o = new URLSearchParams(location.search);
    }

    getParameter(name) {
        return this._o.get(name);
    }
}

export {Request}