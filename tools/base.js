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
                    fs.writeFileSync("output_shop.json", JSON.stringify(data), "utf-8");
                    e.getDriver().executeScript("alert('크롤링 완료')");
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
    const url = "https://www.sta1.com/items?gndr=F&shopType=S";
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
                    fs.writeFileSync("output_item.json", JSON.stringify(data), "utf-8");
                    e.getDriver().executeScript("alert('크롤링 완료')");
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
                    fs.writeFileSync("output_sale.json", JSON.stringify(data), "utf-8");
                    e.getDriver().executeScript("alert('크롤링 완료')");
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
    case 'item':
        runItem();
        break;
    case 'sale':
        runSale();
        break;
}
