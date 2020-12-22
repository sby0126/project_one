import {Cookie, ConstantCookie, getCookie, setCookie, unique} from "./components/Cookie.js";
import {EventEmitter} from "./EventEmitter.js";
import {Request} from "./Request.js";

/**
 * @class Impl
 */
class Impl extends EventEmitter {
    constructor() {
        super();
    }
}

const caller = new Impl();
const request = new Request();
const cookieName = `recentShopItems`;

// 쿠키 생성
caller.on("createCookie", (name, value) => {
    // value = unique(value.split(",")).join(",");
    setCookie(name, value);
});

// 쿠키 획득
caller.on("getCookie", (name) => {
    const cookie = new ConstantCookie();
    const value = getCookie(name);

    const id = request.getParameter("id");

    if( value ) {
        caller.emit("createCookie", cookieName, `${value},` + id);
    } else {
        caller.emit("createCookie", cookieName, id);
    }
});

caller.emit("getCookie", cookieName);