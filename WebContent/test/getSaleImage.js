const data = [];

document.querySelectorAll(".image-ratio-wrapper").forEach((e, i, a) => {
  const card = e.parentElement.parentElement.parentElement;

  let imgRawSrc = card.querySelector("img").src;
  // let imgFileName = imgRawSrc.slice(imgRawSrc.lastIndexOf("/") + 1);

  data.push({
      "category": "sale",
      "src": imgRawSrc,
      "title": card.querySelector(".title").innerHTML,
      "term": card.querySelector(".term").innerHTML,
      "shop": card.querySelector(".shop").innerHTML,
  });

});