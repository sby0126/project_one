/**
 * @description
 * 오라클 DB 연동
 * 
 * 데이터 삽입, 조회 가능
 * 
 * @see
 * http://oracle.github.io/node-oracledb/doc/api.html
 */
const oracledb = require("oracledb");

oracledb.getConnection({
    user: "scott",
    password: "tiger",
    host: "localhost",
    database: "orcl",
}, (err, conn) => {
    if(err) {
        console.log("DB 접속 실패!")
        return;
    }
    console.log("DB 접속 성공!!");

    conn.execute("select * from emp", {}, {outFormat: oracledb.OUT_FORMAT_OBJECT}, (err, result) => {
        if(err) {
            throw new Error(err);
        }

        console.log(result);
    });

});
