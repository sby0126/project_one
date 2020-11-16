const axios = require("axios");
const cheerio = require("cheerio");
const cp = require("child_process");
const {promisify} = require("util");

const sizeOf = require('image-size');

const spwan = promisify(cp.spawn);
const {
  createCanvas,
  loadImage
} = require('canvas');
const {
  pathToFileURL
} = require("url");
const path = require('path');
const fs = require("fs");
const argv = require("minimist")(process.argv.slice(2));

let ret = [];

const config = {
  headers: {
    'user-agent' : 'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36', 
    'Accept-Charset': 'utf-8'
  }
};


// 크롤러 시작
const getHtml = async () => {
  try {
    return await axios.get("https://www.sta1.com/sales/all?gndr=M&shopType=S", config);
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

      const title = i.title;
      const price = i.price;
	  const shop = i.shop;

      try {
        const image = await loadImage(path.basename(i.src));
        const dimensions = sizeOf(path.basename(i.src))
        const canvas = createCanvas(dimensions.width, dimensions.height);
        const ctx = canvas.getContext('2d')        
        
        ctx.drawImage(image, 0, 0, image.width, image.height);
        const item = {
		  category: "item",
          url: canvas.toDataURL(),
          title,
          price,
		  shop
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
    // let imgList = [];
    // const $ = cheerio.load(html.data);
    // const list = $(".item .sale-card a");
	
    // list.each(function (i, elem) {
    //   var self = this;
    //   imgList.push({
    //   category: "sale",
    //       src: $(self).find(".image-ratio-wrapper").find("img").attr("src"),
    //       title: $(self).find(".info h2").text(),
    //       shop: $(self).find(".info .shop").text(),
    //     term: $(self).find(".info .term").text()
    //     });
	  
	  // console.log(imgList[i]);
    // });

    const imgList = [{
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/11/1604551820253_m.jpg",
      "title": "인기슈즈 59% SALE",
      "shop": "힙합퍼",
      "term": "11.05 ~ 11.30"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/11/1604551682098_m.jpg",
      "title": "올세인츠 up to 50% off!",
      "shop": "SI빌리지",
      "term": "11.05 ~ 11.16"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/11/1604551154677_m.jpg",
      "title": "스포츠브랜드 슈즈 세일 50%",
      "shop": "ABC마트",
      "term": "11.05 ~ 11.15"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/11/1604370283744_m.jpg",
      "title": "에잇세컨즈 신상특가 ~50%",
      "shop": "SSF샵",
      "term": "11.03 ~ 재고 소진시"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/11/1604369757518_m.jpg",
      "title": "어반에이지 20FW 빅세일 68%",
      "shop": "어반에이지",
      "term": "11.03 ~ 재고 소진시"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/11/1604369231024_m.jpg",
      "title": "명품하이엔드 ~52%할인",
      "shop": "셀렉온",
      "term": "11.03 ~ 11.09"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1604034563430_m.jpg",
      "title": "탑텐 아우터페스티벌 50%off",
      "shop": "탑텐",
      "term": "10.30 ~ 11.30"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1604034171416_m.jpg",
      "title": "네스티킥x네스티팬시클럽 80%",
      "shop": "크루비",
      "term": "10.30 ~ 재고 소진시"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1604033872625_m.jpg",
      "title": "플루크 미드세일 50%!",
      "shop": "인플럭스",
      "term": "10.30 ~ 11.10"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1604033519539_m.jpg",
      "title": "챔피온 겨울상품 ~30%off",
      "shop": "29CM",
      "term": "10.30 ~ 11.11"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1604033228818_m.jpg",
      "title": "슈즈브랜드 세일 up to 70%",
      "shop": "ABC마트",
      "term": "10.30 ~ 11.01"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1604030222317_m.jpg",
      "title": "필루미네이트 빅세일 -70%",
      "shop": "필루미네이트",
      "term": "10.30 ~ 재고 소진시"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1603637206984_m.jpg",
      "title": "베이직하우스 미드세일 40%off",
      "shop": "베이직하우스",
      "term": "10.26 ~ 재고 소진시"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1603636719854_m.jpg",
      "title": "브랜드 니트컬렉션 ~77%",
      "shop": "무신사",
      "term": "10.26 ~ 11.08"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1603335324081_m.jpg",
      "title": "컨버스 모음전 40% SALE",
      "shop": "힙합퍼",
      "term": "10.22 ~ 10.26"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1603334465572_m.jpg",
      "title": "명품브랜드 아우터 ~66% off",
      "shop": "셀렉온",
      "term": "10.22 ~ 10.26"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1603333551834_m.jpg",
      "title": "인기명품 특가 up to 57%",
      "shop": "SSF샵",
      "term": "10.22 ~ 재고 소진시"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1603112686912_m.jpg",
      "title": "가을모자 특가모음 ~68%",
      "shop": "슈마커",
      "term": "10.20 ~ 10.24"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1602745468255_m.jpg",
      "title": "인기브랜드 상품 최대 ~86%",
      "shop": "무신사",
      "term": "10.15 ~ 10.19"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1602490555500_m.jpg",
      "title": "브랜드 니트특가 최대 62%할인",
      "shop": "크루비",
      "term": "10.12 ~ 재고 소진시"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1602489498501_m.jpg",
      "title": "아디다스 인기상품 50%off",
      "shop": "무신사",
      "term": "10.12 ~ 11.01"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1602076811370_m.jpg",
      "title": "에르노 최대 ~50%off",
      "shop": "SI빌리지",
      "term": "10.08 ~ 10.19"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1602074775870_m.jpg",
      "title": "컨템포러리 시계 ~65% 할인",
      "shop": "셀렉온",
      "term": "10.08 ~ 10.12"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1602074396229_m.jpg",
      "title": "스포츠 슈즈 가을맞이 ~80%",
      "shop": "풋스케이프",
      "term": "10.08 ~ 10.31"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/10/1601865189187_m.jpg",
      "title": "인기브랜드 아우터 ~26%off",
      "shop": "힙합퍼",
      "term": "10.05 ~ 10.31"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/09/1601381875086_m.jpg",
      "title": "플라이데이 시즌오프 ~70%",
      "shop": "플라이데이",
      "term": "09.30 ~ 재고 소진시"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/09/1601381428417_m.jpg",
      "title": "칼하트 최대 67%할인!",
      "shop": "에이랜드",
      "term": "09.30 ~ 10.04"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/09/1601380311236_m.jpg",
      "title": "나이키 추석세일 ~50%",
      "shop": "ABC마트",
      "term": "09.30 ~ 10.07"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/09/1601351559719_m.jpg",
      "title": "8seconds 추석맞이 up to 50%",
      "shop": "에잇세컨즈",
      "term": "09.29 ~ 10.11"
  }, {
      "category": "sale",
      "src": "https://img.sta1.com/_up/sales/2020/09/1601351076269_m.jpg",
      "title": "자라 해피추석 40%off",
      "shop": "자라",
      "term": "09.29 ~ 재고 소진시"
  }
];
  
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
