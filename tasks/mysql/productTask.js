const path = require("path");
const fs = require("fs");
const file = require("../../WebContent/json/prebuilt.json");
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
        this._conn.query(`DROP TABLE if exists tblproduct2`, (err, result) => {
            if(err) {
                console.warn(err);
                return;
            }
            console.log("drop table 성공");
        })    

        return this._conn;
    }

    async createTable(conn) {
        
        conn.query(`create table tblproduct2 ( id int(11) not null auto_increment, pageType varchar(4) not null, genderType varchar(1) not NULL, shopType varchar(1) not null, shopName varchar(20), texts varchar(80), contentUrl varchar(100) not null, title varchar(50), price varchar(10), term varchar(15), CONSTRAINT PRIMARY KEY tblproduct2_pk (id) )`, (err, result) => {
            if(err) {
                console.warn(err);
                return;
            }
            console.log("테이블 생성 완료");
        });

        return conn;
    }

    async insertAllData(conn) {
        try {
            const query = `insert into tblproduct2(pageType, genderType, shopType, shopName, texts, contentUrl, title, price, term) values(?, ?, ?, ?, ?, ?, ?, ?, ?)`;
            file.forEach(data => {
                const {pageType, genderType, shopType, contentData} = data;

                contentData.forEach(i => {
                    const exec = conn.query(query, [
                        pageType,
                        genderType,
                        shopType,
                        i.shopName || i.shop,
                        i.texts,
                        i.url || "",
                        i.title,
                        i.price,
                        i.term
                    ]);                    
                });

            });

        } catch(e) {
            console.warn(e);
        }

        return conn;
    }
}

async function start() {
    try {
        const man = new SQLManagerImpl();

        await man.dropTable().then(async (conn) => {
            await man.createTable(conn);
            await man.insertAllData(conn);
        })
        
    } catch(e) {
        console.warn(e);
    }
}

start();