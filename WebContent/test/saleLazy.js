// const {Builder,By,Key,until} = require('selenium-webdriver');  //모듈 불러오기
// const chrome = require('selenium-webdriver/chrome');           //크롬 사용시 

// const url = "https://www.sta1.com/sales/all?gndr=F&shopType=B&pg=2";

// const data = [];

// (async function myFunction() {
// 	let driver = await new Builder().forBrowser('chrome').build();  //가상 브라우저 빌드
// 		try { 
//             await driver.get(url);    //get(url) 인거 보면 뭔지 알것같이 생겼다
//             let card = await driver.wait(until.elementLocated(By.css('.image-ratio-wrapper')), 1000); //기다리기
//             card.
//             imgs = await img.getAttribute('src'); //imgs에 바로 위 코드에서 가져온 element의 src속성값을 가져온다

// 		}
//         finally {
// 			await driver.quit(); //가상 브라우저를 종료시킨다
//         }
// })();

// document.querySelectorAll(".image-ratio-wrapper").forEach((e, i, a) => {
//   const card = e.parentElement.parentElement.parentElement;

//   let imgRawSrc = card.querySelector("img").src;
//   // let imgFileName = imgRawSrc.slice(imgRawSrc.lastIndexOf("/") + 1);

//   data.push({
//       "category": "sale",
//       "src": imgRawSrc,
//       "title": card.querySelector(".title").innerHTML,
//       "term": card.querySelector(".term").innerHTML,
//       "shop": card.querySelector(".shop").innerHTML,
//   });

// });