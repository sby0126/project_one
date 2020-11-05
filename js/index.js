import {App} from "./app.js";
import {blobData, base64toBlob} from "./data.js";
import {itemData} from "./itemData.js";

const app = new App();
app.on("ready", async () => {
    app.createLazyLoader();
});

// 데이터베이스가 존재하지 않으면 생성
// const db = openDatabase("database", "1.0", "This database can save or controll a virtual shop data", 1024 * 1024 * 8);
// window.testDB = db;

// db.transaction((tx) => {
//     tx.executeSql("drop table globalDB");
//     tx.executeSql("CREATE TABLE if not exists globalDB(id INTEGER primary key, category TEXT, url TEXT, name TEXT, texts TEXT );");
    
//     for(let i = 0; i < blobData.length; i++) {
//         const myData = blobData[i];
//         const url = JSON.stringify(myData.url);
//         const name = myData.shopName;
//         const texts = myData.texts;

//         tx.executeSql(`insert into globalDB values('${i}', 'shop', '${url}', '${name}', '${texts}');`);
//     }

//     itemData.forEach((data, i) => {
//         const url = JSON.stringify(data.url);
//         const title = data.title;
//         const price = data.price;
//         tx.executeSql(`insert into globalDB values('${blobData.length + i}', '${data.category}', '${url}', '${title}', '${price}');`);
//     });

//     tx.executeSql("select * from globalDB where name like '제%';", null, (execute, result) => {
        
//         const rows = Array.from(result.rows);
//         rows.forEach(row => {
//             console.log(`
//                 id = ${row.id}
//                 key = ${row.category}
//                 value = ${row.url}
//                 name = ${row.name}
//                 texts = ${row.texts}
//             `);        
//         });
//     });
    
// });

window.app = app;

window.addEventListener("load", () => {

    app.emit("ready");
});