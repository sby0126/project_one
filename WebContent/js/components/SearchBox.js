import {Component} from "./Component.js";
import {Request} from "../Request.js";

class SearchBox extends Component {

    async run() {
        // 검색을 시작했을 때
        $("#my-search-box").on("change", () => {

            const req = new Request();
            const pageType = req.getParameter("pageType") || "item";
            const shopType = req.getParameter("shopType") || "S";
            const gndr = req.getParameter("gndr") || "M";
            const ages = req.getParameter("ages") || "all";
            const keyword = $("#my-search-box").val();

            $.ajax({
                url: `/contents/search.do?pageType=${pageType}&shopType=${shopType}&gndr=${gndr}&category=100&ages=${ages}&keyword=${keyword}`,
                success: function(data) {
                    setTimeout(() => {
                        if(location.pathname == "/item.jsp") {
                            const param = new URLSearchParams(location.href);
                            param.set("gndr", gndr);
                            param.set("ages", ages);
                            param.set("pageType", pageType);
                            param.set("shopType", shopType);
                            param.set("keyword", keyword);

                            location.search = param.toString();

                            console.log("작동됨");
                        } else {
                            location.href = `/item.jsp?pageType=${pageType}&shopType=${shopType}&gndr=${gndr}&ages=${ages}&keyword=${keyword}`;
                        }
                        
                    }, 0);
                },
                error: function(err) {
                    console.log(err);
                }
            });
            
        });
    }

    static id() {
        return SearchBox;
    }
}

export {SearchBox};