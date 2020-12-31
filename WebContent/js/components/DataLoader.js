import {Component} from "./Component.js";

export class DataLoader extends Component {
    initMembers(parent) {
        super.initMembers(parent);
        
        this.initWithUrlParams();
    }

    initWithUrlParams() {
        const urlParams = new URLSearchParams(decodeURI(location.search));
        const gndr = urlParams.get("gndr") || "M";
        const shopType = urlParams.get("shopType") || "S";
        const category = parseInt(urlParams.get("category")) || 100;
        const pg = parseInt(urlParams.get("pg")) || 1;
        const ages = parseInt(urlParams.get("ages")) || "all";  
        const keyword = urlParams.get("keyword");

        const ret = {
            gndr, shopType, category, pg, ages, keyword
        };

        Object.assign(this, ret);
        
        return ret;
    }

    /**
     * @param {String} pageType
     */
    async get(pageType, success) {

        const data = this.loadJson(`json/prebuilt.json`, (data) => {
            const matched = data.filter(e => {
                return e.shopType === this.shopType && e.pageType === pageType;
            });

            console.log(data);
    
            const retData = matched.pop();

            success(retData);
        }, (err) => {
            console.warn(err);
        });
        
    }

    load(pageType, success, option) {
        const prom = new Promise((resolve, reject) => {
            const xhr = new XMLHttpRequest();
            
            let rangeQuery = "";
            
            // 불러올 데이터의 범위 제한 옵션입니다.
            if(option) {
                const {start, end} = option;
                rangeQuery = "";
                rangeQuery += `&start=` + start;
                rangeQuery += `&end=` + end;
            }
            
            // ${pageType}.do?pageType=${pageType}&shopType=S&gndr=F
            // xhr.open("GET", `json/prebuilt.json`); 
            if(pageType === 'item' && this.keyword) {
                xhr.open("GET", `/contents/${pageType}.do?pageType=${pageType}&shopType=${this.shopType}&gndr=${this.gndr}&category=${this.category}&ages=${this.ages}&keyword=${this.keyword}${rangeQuery}`);
            } else {
                if(pageType !== "sale") {
                    xhr.open("GET", `/contents/${pageType}.do?pageType=${pageType}&shopType=${this.shopType}&gndr=${this.gndr}&category=${this.category}&ages=${this.ages}${rangeQuery}`);
                } else {
                    xhr.open("GET", `/contents/${pageType}.do?pageType=${pageType}&shopType=${this.shopType}&gndr=${this.gndr}${rangeQuery}`);
                }
                
            }
            
            xhr.onload = () => {    
                resolve([].concat(JSON.parse(xhr.responseText)));
            }
            xhr.onerror = (err) => {
                reject(err);
            }
            xhr.send();
        });

        prom.then(ret => {
            
            const matched = ret.filter(e => {
                return e.shopType === this.shopType && 
                       e.pageType === pageType && 
                       e.genderType === this.gndr;
            });

            const retData = matched[0];

            console.log(retData);
            success(retData);

        }).catch(err => {
            console.warn(err);
        })
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