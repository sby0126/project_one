/**
 * @author 어진석
 * @description
 * 
 * 이미지를 리사이즈 하는 유틸리티 스크립트입니다.
 * 
 * 두 개의 인자를 받습니다.
 * 
 * node resize.js -i-<폴더명> -s=<가로크기>
 * 
 * i는 input의 약자로, 입력된 폴더 안에 있는 이미지를 읽어
 * 
 * 전달된 사이즈에 맞게 리사이즈하게 됩니다.
 * 
 * 이 파일은 output_data.json 파일을 생성합니다.
 * 
 */
const fs = require('fs');
const path = require('path');
const argv = require('minimist')(process.argv.slice(2));

/***
 * node resize.js -i=item -s=200
 */
const inputDir = argv.i;
const size = argv.s;

const dirList = fs.readdirSync(path.resolve(inputDir), {encoding: "utf-8"});

// 이 라이브러리는 이미지를 리사이즈하고 크롭합니다.
const sharp = require('sharp');

// 빌드 파일이 없는 경우 새로 만듭니다.
if(!fs.existsSync(!path.resolve(inputDir, "build"))) {
    fs.mkdirSync(path.resolve(inputDir, "build"));
}

// 폴더 내 파일을 1 level 깊이에서만 읽습니다.
// 본래 하위 서브 트리까지 읽으려면 재귀로 구현해야 하지만 재귀는 여기서 필요 없습니다.
dirList.forEach(file => {
    const result = path.resolve(inputDir, file);

    sharp(result)
        .resize(size)
        .toFile(path.resolve(inputDir, "build", file), (err, info) => {
            if(err) {
                console.warn(err.message);
            }

            console.log(info);
        })
})