import {App} from "./app.js";

const app = new App();
app.on("ready", async () => {
    app.createLazyLoader();
});

window.app = app;

window.addEventListener("load", () => {
    app.emit("ready");
});