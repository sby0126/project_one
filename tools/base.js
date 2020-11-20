/**
 * @author 어진석
 * @description 
 * 실제 크롬을 실행하는 셀레니움 크롤러를 이용하여 몇몇 페이지의 데이터를 긁어옵니다.
 */

const webdriver = require('selenium-webdriver');
const By = require('selenium-webdriver').By;
const fs = require('fs');
const path = require('path');
const argv = require('minimist')(process.argv.slice(2));

const driver = new webdriver.Builder()
    .withCapabilities(webdriver.Capabilities.chrome())
    .build();



// 데이터 수집 배열
const data = [];

function removeAbsolutePath(imageData) {
    const output = imageData.map(i => {
        const raw = i.src.replace(/\?(.*)/g, "");
        const filename = path.basename(raw);
        i.url = filename;
        delete i.src;
        return i;
    });

    return output;
}

function output(filename, e, myData) {
    fs.writeFileSync(filename, JSON.stringify(myData), "utf-8");
    e.getDriver().executeScript("alert('크롤링 완료')");
    
    if(argv.download) {
        const Downloader = require("./downloader");
        
        // 깊은 복사...
        Downloader.start(JSON.parse(JSON.stringify(myData)), () => {
            fs.writeFileSync(filename, JSON.stringify(removeAbsolutePath(myData)), "utf-8");
        });
    }
}

function runShop() {
    const url = "https://www.sta1.com/shops/121?shopType=S&gndr=F";
    driver.get(url);

    const items = driver.findElements(By.css(".product-item-container"));
    items.then(products => {

        const lineNumber = ((i=0) => () => i++)();

        products.forEach(async e => {
            const newData = {};
            try {
                
                newData.src = await e.findElement(By.css(".image")).getAttribute("src");
                newData.title = await e.findElement(By.css(".title")).getText();
                newData.shop = await e.findElement(By.css(".shop")).getText();
                newData.price = await e.findElement(By.css(".price")).getText();

                data.push(newData);

                if(lineNumber() == products.length - 1) {
                    output("output_shop.json", e, data);
                }

            } catch(e) {
                console.warn(e);
            }
        })
    }).catch(err => {
        console.warn(err);
    })
}


function runShopWoman() {
    const url = "https://www.sta1.com/shops?gndr=F&shopType=B";
    driver.get(url);

    const items = driver.findElements(By.css(".shop-card > .info"));
    items.then(products => {

        const lineNumber = ((i=0) => () => i++)();

        products.forEach(async e => {
            const newData = {};
            try {
                
                newData.src = await e.findElement(By.css(".img > img")).getAttribute("src");
                newData.texts = await e.findElement(By.css("p:nth-of-type(1)")).getText();
                newData.texts += await e.findElement(By.css("p:nth-of-type(2)")).getText();
                
                newData.shop = await e.findElement(By.css(".shop-name")).getText();

                data.push(newData);

                if(lineNumber() == products.length - 1) {
                    output("output_shop.json", e, data);
                }

            } catch(e) {
                console.warn(e);
            }
        })
    }).catch(err => {
        console.warn(err);
    })
}

function runItem() {
    const url = "https://www.sta1.com/items?gndr=F&shopType=B";
    driver.get(url);

    const items = driver.findElements(By.css(".product-item-container"));
    items.then(products => {

        const lineNumber = ((i=0) => () => i++)();

        products.forEach(async e => {
            const newData = {};
            try {
                
                newData.src = await e.findElement(By.css(".image")).getAttribute("src");
                newData.title = await e.findElement(By.css(".title")).getText();
                newData.shop = await e.findElement(By.css(".shop")).getText();
                newData.price = await e.findElement(By.css(".price")).getText();

                data.push(newData);

                if(lineNumber() == products.length - 1) {
                    output("output_item.json", e, data);
                }

            } catch(e) {
                console.warn(e);
            }
        })
    }).catch(err => {
        console.warn(err);
    })
}

function runSale() {
    const url = "https://www.sta1.com/sales/all?gndr=F&shopType=S";
    driver.get(url);

    const items = driver.findElements(By.css(".item > .sale-card > a"));
    items.then(products => {

        const lineNumber = ((i=0) => () => i++)();

        products.forEach(async e => {
            const newData = {};
            try {
                
                newData.src = await e.findElement(By.css(".image")).getAttribute("src");
                newData.title = await e.findElement(By.css(".title")).getText();
                newData.term = await e.findElement(By.css(".term")).getText();
                newData.shop = await e.findElement(By.css(".shop")).getText();

                data.push(newData);

                if(lineNumber() == products.length - 1) {
                    output("output_sale.json", e, data);
                }

            } catch(e) {
                console.warn(e);
            }
        })
    }).catch(err => {
        console.warn(err);
    })
}

switch(argv.type.toLowerCase()) {
    case 'shop':
        runShop();
        break;
    case 'shopwoman':
        runShopWoman();
        break;
    case 'item':
        runItem();
        break;
    case 'sale':
        runSale();
        break;
}
