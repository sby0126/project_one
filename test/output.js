const {item} = require("../json/prebuilt_data.json");
const path = require('path');
const fs = require('fs');

const output = item.map(i => {
    const raw = i.src.replace(/\?(.*)/g, "");
    const filename = path.basename(raw);
    i.url = filename;
    delete i.src;
    return i;
});

fs.writeFileSync("output_data.json", JSON.stringify(output), "utf8");

