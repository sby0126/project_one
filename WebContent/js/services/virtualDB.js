import {Component} from "../components/Component.js";

export class Schema {
    constructor(shopType, gndr) {
        
        if(!shopType) {
            throw new Error("Can't write shoptype.");
        }
        
        if(!gndr) {
            throw new Error("Can't write genderType");
        }

        this.dataId = 0; // storeSeq
        this.shopType = shopType;
        this.genderType = gndr;
        this.contents = {};
        this.virtualFileName = null;

    }

    setContents(data) {
        Object.assign(this.contents, data);
    }

    setVirtualFileName(filename) {
        this.virtualFileName = filename;
    }
}

export class VirtualDB extends Component {

    /**
     * @param {String} name Specify the filename.
     */
    constructor(name) {
        super();
    }

    initMembers() {
        super.initMembers();
        
        this.initWithAllSchema();
    }

    initWithAllSchema() {
        this._schema = {
            shop: new Schema(),
            item: new Schema(),
            sale: new Schema(),
        };
    }

    run() {

    }
}