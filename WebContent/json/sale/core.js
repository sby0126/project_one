const blobData = require("./collection.json");

const fs = require('fs');
const path = require('path');

for(let i = 0; i < blobData.length; i++) {
    const json = blobData[i];
    const filepath = path.join(path.resolve(__dirname), `sale_data${i}.json`);
    fs.writeFileSync(filepath, JSON.stringify(json), "utf8");
}