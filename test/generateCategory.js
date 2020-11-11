/**
 * 카테고리 데이터를 생성하는 유틸리티 스크립트입니다.
 */

const fs = require('fs');
const path = require('path');
const argv = require('minimist')(process.argv.slice(2));

const _CATEGORY = {
    Type: {
        ALL: 0,
        TREND: 1,
        DANDY: 2,
        UNIQUE: 3,
        REPLICA: 4,
        STREET: 5,
        CLASSICAL: 6,
        BIG: 7,
        SHOES: 8,
        ACCESSORY: 9,
    },
    KEYS: ["ALL", "TREND", "DANDY", "UNIQUE", "REPLICA", "STREET", "CLASSICAL", "BIG", "SHOES", "ACCESSORY"]
};

if(argv.help) {
    console.log(` 
        --help : show help documention.
        --page=<page_name>: please pass a page name to a site. Note that the string is only lower case.
    `);
    return;
}

var PAGE = argv.page || 'shop';

const list = require('../json/prebuilt_data.json');

const data = Object.values(list[PAGE]);
const retData = [];

// 1~9 사이의 카테고리 인덱스 데이터를 추출
for(let i = 0; i < data.length; i++) {
    const selectedIndex = Math.floor(Math.random() * 9) + 1;
    retData.push(selectedIndex);
}

// 인덱스 배열을 생성한 후 파일로 내보냅니다.
fs.writeFileSync(path.resolve(`category_${PAGE}_indexed_array.json`), JSON.stringify(retData), "utf-8");


