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

    run() {        

    }

    static id() {
        return DataLoader;
    }
}