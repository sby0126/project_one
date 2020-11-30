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
    REPLAY_OK: ".replay-ok",
    REPLAY_DELETE: ".replay-delete",
    COMMENT_OK: "#comment-ok-button",
    BACK_TO_LIST: "#list-button",
    MODIFY: "#modify-button",
    DELETE: "#delete-button",
    POST_RECOMMAND_BUTTON: "#post-recommand-button",
    POST_SHARE_BUTTON2: "#post-share-button2"
};

const AJAX = {
    doPost(url, data, success) {
        $.ajax({
            "url":url,
            "async": true,
            "type":'POST',
            "data": data,
            "dataType":"json",// xml, json, script, html
            success:success,
        });
    }
}

// 실행하기 위한 이벤트를 여기에 추가하세요.
const FUNC = {
    POST_SHARE_BUTTON: function(ev) {
        alert("공유 버튼을 눌렀습니다");
    },
    POST_DELETE_BUTTON: function(ev) {

        const yesNo = confirm("정말로 삭제하시겠습니까?");
        const YES = true;

        if(yesNo == YES) {
            const params = new URLSearchParams(location.search);
            const postNumber = params.get("postNumber");

            AJAX.doPost("delete.jsp", {"postNumber": postNumber}, (jqXHR) => {
                history.back();
            });
        }   
    },
    POST_MORE_FUNCTION_BUTTON: function(ev) {
        alert("더보기 버튼을 눌렀습니다");
    },
    REPLAY_OK: function(ev) {
        alert("대댓글 버튼을 눌렀습니다.");   
    },
    REPLAY_DELETE: function(ev) {
        const ret = window.confirm("댓글을 삭제하시겠습니까?");

        if(ret) {
            $(ev.target).parent().parent().parent().parent().remove();
            SDK.updateCommentsCount();
        }
        
        ev.preventDefault();
    },
    COMMENT_OK: function(ev) {
        ev.preventDefault();

        const texts = $("#comment-textarea").val();
        if(texts.length == 0) {
            alert("댓글란이 비어있습니다.");
            ev.preventDefault();
            return false;
        }
        if(texts.length > 0) {
            SDK.registerComment("테스터", texts);
        }
    },
    BACK_TO_LIST: function(ev) {
    },
    MODIFY: function(ev) {
    },
    DELETE: function(ev) {
        alert("삭제 버튼을 눌렀습니다.");   

        const yesNo = confirm("정말로 삭제하시겠습니까?");
        const YES = true;

        if(yesNo == YES) {
            const params = new URLSearchParams(location.search);
            const postNumber = params.get("postNumber");

            AJAX.doPost("delete.jsp", {
                "postNumber": postNumber
            }, jqXHR => {
                history.back();
            });
        }   

    },
    POST_RECOMMAND_BUTTON: function(ev) {
        alert("글 추천 버튼을 눌렀습니다.");   
    },
    POST_SHARE_BUTTON2: function(ev) {
        alert("글 공유하기 버튼을 눌렀습니다.");   
    }
};

window.BoardEvent = FUNC;

const SDK = (() => {
    class SDKImpl {
        updateCommentsCount() {
            let val = parseInt($(".comment-header span em").text().trim());
            if(val) {
                let count = $(".comment-author").length;
                $(".comment-header span em").text(count);
            }
        }

        /**
         * 시간 차이를 계산합니다.
         * @param {Date} currentTime 
         * @param {Date} publishedTime 
         */
        getDayInterval(currentTime, publishedTime) {
            const c = currentTime.getTime();
            const s = publishedTime.getTime();
            const thod = (60 * 60 * 24 * 1000);
            const dayInterval = (c / (thod)) - (s / (thod));

            return dayInterval;
        }
                
        /**
         * 
         * @param {Date} publishedTime 
         */
        getTimeText(publishedTime) {
            const currentTime = new Date();

            const days = Math.floor(this.getDayInterval(currentTime, publishedTime));
            let publishedTimeText = "";

            // 심플한 시간을 가져오는 기능이 없어 직접 구현 (계산이 안 맞을 수도 있음)
            let mark = "";
            if(days >= 365) {
                mark = "년 전";
                publishedTimeText = Math.floor(days / 365.0) + mark;
            } else if(days >= 31) {
                mark = "개월 전";
                publishedTimeText = Math.floor(days / 31) + mark;
            } else if(days >= 7) {
                mark = "주 전";
                publishedTimeText = Math.floor(days / 7) + mark;
            } else if(days >= 1) {
                mark = "일 전";
                publishedTimeText = days + mark;
            } else {
                mark = "시간 전";
                // publishedTimeText = (Math.abs(23 - publishedTime.getHours()) + (currentTime.getHours())) % 24 + mark;
                let m = Math.abs(currentTime.getHours() - publishedTime.getHours());
                if(m === 0) {
                    m = "";
                    mark = "방금";
                }
                publishedTimeText = m + mark;
            }            

            return publishedTimeText;
        }

        registerComment(authorId, texts, timer) {
            const timeText = this.getTimeText(new Date(timer));
            let commentRaw = "";
            /**
             * @type {String[]}
             */
            const lines = texts.split(/[\r\n]+/);
            lines.forEach(line => {
                commentRaw += `<span>${line}</span>`
            });

            $(".add-comment-button-area").before(
                $(`
                <div class="comment-area">
                <div class="comment-author well">
                    <div class="profile-box">
                        <span><i class="fas fa-user-circle fa-3x"></i></span>
                    </div>
                    <div class="detail-area">
                        <div class="detail-area-author-id">
                            <span>${authorId}</span>        
                        </div>
                        <div class="detail-area-date-panel">
                            <span>${timeText}</span>
                            <span style="cursor:pointer;"><a href="" class="replay-ok">답글</a></span>
                            <span style="cursor:pointer;"><a href="" class="replay-delete">삭제</a></span>
                        </div>
                        <div class="detail-area-contentss">
                            ${commentRaw}
                        </div>
                    </div>       
                </div>      
                <img src='data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7' onload='SDK.updateCommentsCount();this.parentNode.removeChild(this);'>       
                `)
            );
        }

    }

    return new SDKImpl();
})();

window.SDK = SDK;

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

    load(postNumber, cb) {
        $.ajax({
            url: "/board/qna/postView.do",
            method: "GET",
            data: {postNumber: postNumber},
            contentType: "application/json",
            success: function(data) {
                cb(data);
            },
            error: function(err) {
                console.log(err);
            }
        });
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
        this.load(this._postNumber, (data) => {

            if(!data) {
                return;
            }

            // 제목
            $("#title").text(data.title)

            // 작성자
            $(".detail-area-author-id span").text(data.author);

            // 내용
            $(".board-article-contents").html(data.contents);

            $("#regdate").text(data.create_at);
            $("#viewcount").text(`조회수 ${data.view}`);
            $("#commentsCount").text(`댓글수 ${data.comments.length}개`);

            data.comments.forEach(comment => {
                SDK.registerComment(comment.author, comment.contents, comment.create_at);
            })

            for(let  i in FUNC) {
                if(i === "REPLAY_DELETE") {
                    continue;
                }
                document.querySelector(ID[i]).onclick = FUNC[i];
            }
    
            $(".board-post-comment").on("click", (ev) => {
                if(ev.target.tagName === "A") {
                    if(ev.target.classList.contains("replay-delete")) {
                        FUNC.REPLAY_DELETE(ev);
                    }
                }
                ev.preventDefault();
            })
        });
    }

    static id() {
        return Editor;
    }
}

Editor.builder().run();
