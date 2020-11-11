const fs = require('fs');
const path = require('path');
const argv = require('minimist')(process.argv.slice(2));

/***
 * node resize.js -i=item -s=200
 */
const inputDir = argv.i;
const size = argv.s;

const dirList = fs.readdirSync(path.resolve(inputDir), {encoding: "utf-8"});

const sharp = require('sharp');

if(!fs.existsSync(!path.resolve(inputDir, "build"))) {
    fs.mkdirSync(path.resolve(inputDir, "build"));
}

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