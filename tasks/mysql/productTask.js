const path = require("path");
const fs = require("fs");
const file = require("../../WebContent/json/prebuilt.json");
const mysql = require("mysql");

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
        this._conn.query(`DROP TABLE if exists tblproduct`, (err, result) => {
            if(err) {
                console.warn(err);
                return;
            }
            console.log("drop table 성공");
        })    
    }

    async createTable() {
        this._conn = await this.createConnection();
        this._conn.query(`create table tblProduct ( pageType varchar(4) not null, genderType varchar(1) not NULL, shopType varchar(1) not null, shopName varchar(20), texts varchar(80), contentUrl varchar(100) not null, title varchar(50), price varchar(10), term varchar(15) )`, (err, result) => {
            if(err) {
                console.warn(err);
                return;
            }
            console.log("테이블 생성 완료");
        })
    }

    async getConnection() {
        try {
            this._conn = await this.createConnection();
            const query = `insert into tblProduct(pageType, genderType, shopType, shopName, texts, contentUrl, title, price, term) values(?, ?, ?, ?, ?, ?, ?, ?, ?)`;
            file.forEach(data => {
                const {pageType, genderType, shopType, contentData} = data;

                contentData.forEach(i => {
                    const exec = this._conn.query(query, [
                        pageType,
                        genderType,
                        shopType,
                        i.shopName,
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


    }
}

async function start() {
    try {
        const man = new SQLManagerImpl();

        await man.dropTable();
        await man.createTable();
        await man.getConnection();
    } catch(e) {
        console.warn(e);
    }
}

start();