const path = require("path");
const fs = require("fs");
const mysql = require("mysql2");
const settings = require("./settings.json");

class SQLManagerImpl {

    constructor() {
    }

    createConnection() {
        return new Promise((resolve, reject) => {
            const pool = mysql.createPool({
                connectionLimit: 10,
                host: settings.host,
                port: settings.port,
                user: settings.user,
                password: settings.password,
                database: settings.database,
                debug: true
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
        this._conn.query(`DROP TABLE if EXISTS tblqnaboardrec`, (err, result) => {
            if(err) {
                console.warn(err);
                return;
            }
            console.log("drop table 성공");
        })    

        return this._conn;
    }

    async createTable(conn) {
        conn.query(`CREATE TABLE tblQnaBoardRec ( board_id int(11) NOT NULL, receiver_id VARCHAR(15) NOT NULL )`, (err, result) => {
            if(err) {
                console.warn(err);
                return;
            }
            console.log("테이블 생성 완료");
        })

        return conn;
    }

    async alterTable(conn) {
        conn.query(`ALTER TABLE tblQnaBoardRec ADD CONSTRAINT tblQnaBoardRec_PK PRIMARY KEY(board_id, receiver_id)`, (err, result) => {
            if(err) {
                console.warn(err);
                return;
            }
            console.log("테이블 권한 변경 완료");
        })  

        return conn;
    }
}

async function start() {
    try {
        const man = new SQLManagerImpl();

        man.dropTable().then(async (conn) => {
            await man.createTable(conn);
            await man.alterTable(conn);
        }).then(i => {});
    } catch(e) {
        console.warn(e);
    }
}

start();