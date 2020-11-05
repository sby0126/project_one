import {App} from "./app.js";

const app = new App();
app.on("ready", async () => {
    app.createLazyLoader();
});

// 데이터베이스가 존재하지 않으면 생성
const db = openDatabase("project_one_test_db", "1.0", "This database is test for purpose.", 1024 * 1024 * 4);
window.testDB = db;

db.transaction((tx) => {
    tx.executeSql("CREATE TABLE IF NOT EXISTS globalDB(id number(4) primary key, name varchar2(255), data varchar2(255));");
    tx.executeSql("insert into globalDB values(1, 'my-test', 'test-data');");
})

window.app = app;

window.addEventListener("load", () => {

    app.emit("ready");
});