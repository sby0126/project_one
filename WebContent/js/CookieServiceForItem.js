import {Cookie, ConstantCookie} from "./components/Cookie.js";
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
const cookieName = `recentlyItems`;

// 쿠키 생성
caller.on("createCookie", (name, value) => {
    console.log(name, value);
    const cookie = new Cookie(name, value);
    cookie.create();
});

// 쿠키 획득
caller.on("getCookie", (name) => {
    const cookie = new ConstantCookie();
    const value = cookie.get(name);

    const paramData = request.getParameter("data");
    const raw = decodeURIComponent(escape(atob(paramData)));
    const data = JSON.parse(raw);    
    const id = data.contentData[0].id;

    if( value ) {
        caller.emit("createCookie", [cookieName, `${value},` + id]);
    } else {
        caller.emit("createCookie", [cookieName, id]);
    }
});

caller.emit("getCookie", cookieName);