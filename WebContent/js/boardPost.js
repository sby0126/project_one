import {Component} from "../js/components/Component.js";

const postSchema = [{
    "boardType": "faq",
    "postNumber" : "0",
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

// 버튼 ID 목록
const ID = {
    POST_SHARE_BUTTON: "#post-share-button",
    POST_DELETE_BUTTON: "#post-delete-button",
    POST_MORE_FUNCTION_BUTTON: "#post-more-function-button",
    REPLAY_OK: "#replay-ok",
    REPLAY_DELETE: "#replay-delete",
    COMMENT_OK: "#comment-ok-button",
    BACK_TO_LIST: "#list-button",
    MODIFY: "#modify-button",
    DELETE: "#delete-button",
    POST_RECOMMAND_BUTTON: "#post-recommand-button",
    POST_SHARE_BUTTON2: "#post-share-button2"
};

const FUNC = {
    POST_SHARE_BUTTON: function(ev) {
        alert("공유 버튼을 눌렀습니다");
    },
    POST_DELETE_BUTTON: function(ev) {
        alert("삭제 버튼을 눌렀습니다");
    },
    POST_MORE_FUNCTION_BUTTON: function(ev) {
        alert("더보기 버튼을 눌렀습니다");
    },
    REPLAY_OK: function(ev) {
        alert("리플 OK 버튼을 눌렀습니다.");   
    },
    REPLAY_DELETE: function(ev) {
        alert("리플 삭제 버튼을 눌렀습니다.");   
    },
    COMMENT_OK: function(ev) {
        alert("코멘트 OK 버튼을 눌렀습니다.");   
    },
    BACK_TO_LIST: function(ev) {
        alert("목록 버튼을 눌렀습니다.");   
    },
    MODIFY: function(ev) {
        alert("수정 버튼을 눌렀습니다.");   
    },
    DELETE: function(ev) {
        alert("삭제 버튼을 눌렀습니다.");   
    },
    POST_RECOMMAND_BUTTON: function(ev) {
        alert("글 추천 버튼을 눌렀습니다.");   
    },
    POST_SHARE_BUTTON2: function(ev) {
        alert("글 공유하기 버튼을 눌렀습니다.");   
    }
};

/**
 * 이 클래스는 게시물의 postNumber 값을 파싱하여 데이터를 가져와서 표시하는 역할을 합니다.
 * 
 * ES5 스타일은 스크립트 작성 시, 자동 완성이 전혀 들어먹질 않기 때문에 ES6 스타일로 작성하는 게 효율적입니다.
 */
class Editor extends Component {

    initMembers() {
        super.initMembers();
        this.initWithData();
    }

    /**
     * 주소 값으로부터 매개변수를 가져옵니다.
     * 
     * 이 방법은 다른 페이지로 데이터를 전달할 때 효율적입니다.
     */
    initWithData() {
        const params = new URLSearchParams(location.search);

        const boardType = params.get("boardType");
        const postNumber = parseInt(params.get("postNumber"));

        this._boardType = boardType;
        this._postNumber = postNumber;
    }

    run() {
        for(let  i in FUNC) {
            $(ID[i]).on("click", FUNC[i]);
        }
    }

    static id() {
        return Editor;
    }
}

Editor.builder().run();
