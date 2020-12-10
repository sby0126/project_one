const path = require("path");
const fs = require("fs");
const mysql = require("mysql2");

class SQLManagerImpl {

    constructor() {
    }

    createConnection() {
        return new Promise((resolve, reject) => {
            const pool = mysql.createPool({
                connectionLimit: 10,
                host: "localhost",
                port: 3306,
                user: "root",
                password: "1234",
                database: "mydb",
                debug: true,
                multipleStatements: true
            });

            pool.getConnection((err, conn) => {
                if(err) {
                    reject(err);
                    return;
                }
                resolve(conn);
            });
        });
    }

    async dropTable() {
        this._conn = await this.createConnection();
        this._conn.query(`DROP TABLE if EXISTS tblLogger`, (err, result) => {
            if(err) {
                console.warn(err);
                return;
            }
            console.log("drop table 성공");
        })    

        return this._conn;
    }

    async createTable(conn) {
        
        conn.query(`CREATE TABLE tblLogger ( mdate DATETIME, mauthor VARCHAR(15), mtext text )`, (err, result) => {
            if(err) {
                console.warn(err);
                return;
            }
            console.log("테이블 생성 완료");
        });

        return conn;
    }

    async createTrigger(conn) {
        
        conn.query(
            `DROP TRIGGER if exists my_trigger; delimiter $$ CREATE TRIGGER my_trigger AFTER insert ON tblqnaboard FOR EACH ROW BEGIN INSERT INTO tblLogger VALUES (NOW(), new.authorID, CONCAT(new.authorID, '님이 글을 작성하셨습니다')); END $$ delimiter ;`.replace(/[\r\n]+/g, " "), 
            
            (err, result) => {
                if(err) {
                    console.warn(err);
                    return;
                }
                console.log("트리거 생성 완료");
            });

        return conn;
    }
}

async function start() {
    try {
        const man = new SQLManagerImpl();

        await man.dropTable().then(async (conn) => {
            await man.createTable(conn);
            await man.createTrigger(conn);
        })
        
    } catch(e) {
        console.warn(e);
    }
}

start();