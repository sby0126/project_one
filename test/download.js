const cp = require("child_process");
const {data} = {"data":[{"keywordType":"S","keywordGender":"M","keyword":"후리스","displayOrder":1,"prevDisplayOrder":1,"keywordSeason":"W","nvKeyword":"후리스,플리스","imgPath":"/_up/word/2018/1539334806373.jpg","searchCount":16689,"nvCount":906900},{"keywordType":"S","keywordGender":"M","keyword":"맨투맨","displayOrder":2,"prevDisplayOrder":2,"keywordSeason":"S","nvKeyword":"맨투맨","imgPath":"/_up/word/2019/1557389145901.jpg","searchCount":89050,"nvCount":347500},{"keywordType":"S","keywordGender":"M","keyword":"후드티","displayOrder":3,"prevDisplayOrder":3,"keywordSeason":"S","nvKeyword":"후드티","imgPath":"/_up/word/2019/1550110592305.jpg","searchCount":55200,"nvCount":324100},{"keywordType":"S","keywordGender":"M","keyword":"후드집업","displayOrder":4,"prevDisplayOrder":4,"keywordSeason":"S","nvKeyword":"후드집업,hoodzipup","imgPath":"/_up/word/2018/1534835362888.jpg","searchCount":20218,"nvCount":296800},{"keywordType":"S","keywordGender":"M","keyword":"패딩","displayOrder":5,"prevDisplayOrder":5,"keywordSeason":"W","nvKeyword":"패딩","imgPath":"/_up/word/2018/1539914338431.jpg","searchCount":27010,"nvCount":248900},{"keywordType":"S","keywordGender":"M","keyword":"니트","displayOrder":6,"prevDisplayOrder":7,"keywordSeason":"F","nvKeyword":"니트,스웨터","imgPath":"/_up/word/2018/1536713758023.jpg","searchCount":69078,"nvCount":184800},{"keywordType":"S","keywordGender":"M","keyword":"가디건","displayOrder":7,"prevDisplayOrder":6,"keywordSeason":"S","nvKeyword":"가디건","imgPath":"/_up/word/2019/1557388583713.jpg","searchCount":60841,"nvCount":182000},{"keywordType":"S","keywordGender":"M","keyword":"스니커즈","displayOrder":8,"prevDisplayOrder":8,"keywordSeason":"F","nvKeyword":"스니커즈,sneacker","imgPath":"/_up/word/2018/1538037644169.jpg","searchCount":15696,"nvCount":158700},{"keywordType":"S","keywordGender":"M","keyword":"에코백","displayOrder":9,"prevDisplayOrder":9,"keywordSeason":"M","nvKeyword":"에코백","imgPath":"/_up/word/2019/1557386652937.jpg","searchCount":5828,"nvCount":154800},{"keywordType":"S","keywordGender":"M","keyword":"조거팬츠","displayOrder":10,"prevDisplayOrder":10,"keywordSeason":"S","nvKeyword":"조거팬츠","imgPath":"/_up/word/2019/1551083755642.jpg","searchCount":16016,"nvCount":134800},{"keywordType":"S","keywordGender":"M","keyword":"백팩","displayOrder":11,"prevDisplayOrder":11,"keywordSeason":"A","nvKeyword":"백팩","imgPath":"/_up/word/2019/1550112383826.jpg","searchCount":7241,"nvCount":131300},{"keywordType":"S","keywordGender":"M","keyword":"코트","displayOrder":12,"prevDisplayOrder":12,"keywordSeason":"W","nvKeyword":"코트,COAT","imgPath":"/_up/word/2018/1539333485377.jpg","searchCount":21823,"nvCount":125960},{"keywordType":"S","keywordGender":"M","keyword":"첼시부츠","displayOrder":13,"prevDisplayOrder":14,"keywordSeason":"W","nvKeyword":"첼시부츠","imgPath":"/_up/word/2018/1542003572458.jpg","searchCount":1780,"nvCount":110200},{"keywordType":"S","keywordGender":"M","keyword":"자켓","displayOrder":14,"prevDisplayOrder":13,"keywordSeason":"F","nvKeyword":"자켓,블레이저","imgPath":"/_up/word/2018/1538038166508.jpg","searchCount":48825,"nvCount":108950},{"keywordType":"S","keywordGender":"M","keyword":"항공점퍼","displayOrder":15,"prevDisplayOrder":17,"keywordSeason":"S","nvKeyword":"항공점퍼,블루종,봄버","imgPath":"/_up/word/2019/1550111754914.jpg","searchCount":6196,"nvCount":104740},{"keywordType":"S","keywordGender":"M","keyword":"조끼","displayOrder":16,"prevDisplayOrder":19,"keywordSeason":"F","nvKeyword":"조끼,베스트","imgPath":"/_up/word/2019/1550110774480.jpg","searchCount":11612,"nvCount":102270},{"keywordType":"S","keywordGender":"M","keyword":"청바지","displayOrder":17,"prevDisplayOrder":15,"keywordSeason":"S","nvKeyword":"청바지","imgPath":"/_up/word/2019/1551083398233.jpg","searchCount":42786,"nvCount":101300},{"keywordType":"S","keywordGender":"M","keyword":"비니","displayOrder":18,"prevDisplayOrder":16,"keywordSeason":"W","nvKeyword":"비니","imgPath":"/_up/word/2020/1577945080035.jpg","searchCount":731,"nvCount":98900},{"keywordType":"S","keywordGender":"M","keyword":"팔찌","displayOrder":19,"prevDisplayOrder":18,"keywordSeason":"A","nvKeyword":"팔찌","imgPath":"/_up/word/2018/1524726509064.jpg","searchCount":3857,"nvCount":95190},{"keywordType":"S","keywordGender":"M","keyword":"버킷햇","displayOrder":20,"prevDisplayOrder":20,"keywordSeason":"A","nvKeyword":"벙거지,버킷햇","imgPath":"/_up/word/2020/1601348702883.jpg","searchCount":130,"nvCount":90560}],"meta":{"total":52},"api-version":"1.0"};
const mainUrl = `https://img.sta1.com`;

function startAria2c() {
    data.forEach(config => {
        const targetUrl = mainUrl + config.imgPath

        cp.spawn("aria2c.exe", [targetUrl]);
    })
}

function startPowershell() {
    data.forEach(config => {
        const targetUrl = mainUrl + config.imgPath;
        const name = targetUrl.substring(targetUrl.lastIndexOf("/") + 1, targetUrl.length);
        cp.spawn("powershell", ["wget", targetUrl, "-OutFile", name]);
    })
}

const aria2c = cp.exec("where aria2c.exe");
aria2c.on("exit", (signal) => {
    if(signal != 0) {
        startPowershell();
    } else {
        startAria2c();
    }
})
