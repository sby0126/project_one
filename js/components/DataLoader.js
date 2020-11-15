import {Component} from "./Component.js";

export class DataLoader extends Component {
    initMembers(parent) {
        super.initMembers(parent);
        
        this.initWithUrlParams();
    }

    initWithUrlParams() {
        const urlParams = new URLSearchParams(decodeURI(location.search));
        const gndr = parseInt(urlParams.get("gndr")) || "M";
        const shopType = parseInt(urlParams.get("shopType")) || "S";
        const category = parseInt(urlParams.get("category")) || 100;
        const pg = parseInt(urlParams.get("pg")) || 1;
        const ages = parseInt(urlParams.get("ages")) || 100;      

        const ret = {
            gndr, shopType, category, pg, ages
        };

        Object.assign(this, ret);
        
        return ret;
    }

    /**
     * https://stackoverflow.com/a/56081995
     * 
     * @param {String} name
     * @param {String} value
     */
    setParameter(name, value) {        
        const searchParams = new URLSearchParams(window.location.search);
        searchParams.set(name, value);

        window.location.search = searchParams.toString();

        // 빌더 패턴을 위해 this 반환
        return this;
    }

    run() {        

    }

    static id() {
        return DataLoader;
    }
}