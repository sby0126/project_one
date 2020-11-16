import {Component} from "../js/components/Component.js";

(function() {

    const postSchema = [{
        "type": "faq",
        "index" : "0",
        "title" : "",
        "contents": "",
        "create_at": "2020-11-16",
        "modify_at": "2020-11-16",
        "author": "",
        "view": "10",
        "comments": [{
            "author": "",
            "create_at": "",
            "modify_at": "",
            "contents": "",
        }]
    }];

    function Editor() {
        this.initialize.apply(this, ...args);
    }

    Editor.prototype = Object.create(Component.prototype);
    Editor.prototype.constructor = Editor;
    Editor.prototype.initialize = function(...args) {
        Component.prototype.constructor.call(this, ...args);
    }
    
})();