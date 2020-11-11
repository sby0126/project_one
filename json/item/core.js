const blobData = require("./collection.json");

const fs = require('fs');
const path = require('path');

// for(let i = 0; i < len; i++) {
//     const json = blobData[i];
//     const filepath = path.join(path.resolve(__dirname), `item_data${i}.json`);
//     fs.writeFileSync(filepath, JSON.stringify(json), "utf8");
// }

for(let i = 0, j = 0; i < blobData.length; i+=5, j++) {
    const list = blobData.slice(i, i + 5);
    const filepath = path.join(path.resolve(__dirname), `item_data${j}.json`);
    fs.writeFileSync(filepath, JSON.stringify(list), "utf8");    
}