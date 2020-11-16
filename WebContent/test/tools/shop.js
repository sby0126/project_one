const axios = require("axios");
const cheerio = require("cheerio");
const cp = require("child_process");
const {promisify} = require("util");
const spwan = promisify(cp.spawn);
const {
  createCanvas,
  loadImage
} = require('canvas');
const {
  pathToFileURL
} = require("url");
const canvas = createCanvas(130, 130);
const ctx = canvas.getContext('2d')
const path = require('path');
const fs = require("fs");
const argv = require("minimist")(process.argv.slice(2));

let ret = [];

// 크롤러 시작
const getHtml = async () => {
  try {
    return await axios.get("https://www.sta1.com/shops?gndr=M&shopType=B");
  } catch (error) {
    console.error(error);
  }
}

// 분할 다운로드 시작
function startAria2c(data) {
  data.forEach(async config => {
      // const targetUrl = mainUrl + config.imgPath
      const targetUrl = config.src;

      try {
        await spwan("aria2c.exe", [targetUrl], {});
      } catch(e) {
        console.warn(e);
      }
      
  });
}

// 파워쉘 시작
function startPowershell(data) {
  data.forEach(async config => {
      // const targetUrl = mainUrl + config.imgPath;
      // const name = targetUrl.substring(targetUrl.lastIndexOf("/") + 1, targetUrl.length);
      const targetUrl = config.src;
      try {
        await spawn("powershell", ["wget", targetUrl, "-OutFile", name], {});
      } catch(e) {
        config.warn(e);
      }
      ``
  });
}

/**
 * 
 */
function load() {

  const list = require("./output.json");

  return new Promise((resolve, reject) => {
    list.forEach(async i => {

      const shopName = i.shopName;
      const texts = i.texts;

      try {
        const image = await loadImage(path.basename(i.src));
        
        ctx.drawImage(image, 0, 0, image.width, image.height);
        const item = {
		  category: "shop",
          url: canvas.toDataURL(),
          shopName,
          texts
        };
        ret.push(item);

      } catch(e) {
        reject("image is not find");
      }
    });

    resolve(ret);
  })
}

if(argv.download) {
  getHtml().then(html => {
    let imgList = [];
    const $ = cheerio.load(html.data);
    const info = $(".info");
    const list = $(".img");
  
    list.each(function (i, elem) {
      var self = this;
      imgList.push({
		category: "shop",
        src: $(self).find('img').attr("src"),
        shopName: $(self).parent().find("h2.shop-name").text(),
        texts: $(self).parent().find("p").text(),
      });
    });
  
    fs.writeFileSync("output.json", JSON.stringify(imgList), {
      encoding: "utf-8"
    });  
  
    const aria2c = cp.exec("where aria2c.exe");
    aria2c.on("exit", (signal) => {
        if(signal != 0) {
            startPowershell(imgList);
        } else {
            startAria2c(imgList);
        }
    });      
  
    return imgList;
  });

}

if(argv.output) {
  load().then(ret => {
    fs.writeFileSync("output_blob.json", JSON.stringify(ret, null, "\t"), "utf-8");
  });  
}
