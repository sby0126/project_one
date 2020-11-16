/**
 * @author 어진석
 * @description
 * 호스트 명과 자세한 경로를 지우고 파일명만 남깁니다.
 */

const {sale} = require("../json/prebuilt_data.json");
const path = require('path');
const fs = require('fs');

const output = sale.map(i => {
    const raw = i.src.replace(/\?(.*)/g, "");
    const filename = path.basename(raw);
    i.url = filename;
    delete i.src;
    return i;
});

fs.writeFileSync("output_data.json", JSON.stringify(output), "utf8");

