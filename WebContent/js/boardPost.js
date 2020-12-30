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
    POST_SHARE_BUTTON2: "#post-share-button2",
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

class UUID {
    /**
     * @link https://stackoverflow.com/a/2117523
     */
    static builder() {
        return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
          );
    }
}

//=============================================================================
// ! 게시판 글에 대한 모든 이벤트 리스너
//=============================================================================

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

            $.get({
                url: `/board/qna/deletePost.do?postNumber=${postNumber}`,
                method: "GET",
                contentType: false,
                processData: false,
                success: function(data) {
                    location.href = "/pages/board-default.jsp";
                },
                error: function(err) {
                    console.warn(err);
                    alert(err);
                }                
            });
            
        } else {
            ev.preventDefault();
        }     
    },
    POST_MORE_FUNCTION_BUTTON: function(ev) {
        alert("더보기 버튼을 눌렀습니다");
    },
	/**
	 * @link https://explainextended.com/2009/03/17/hierarchical-queries-in-mysql/
	 */
    REPLAY_OK: function(ev) {
	
		// 댓글 창이 이미 존재하는 경우, 
		if($("#child-comment-textarea").length) {
			ev.preventDefault();
			$("#child-comment-textarea").remove();
			return;
		}
    
        // 부모 코멘트
        const parentComment = $(ev.target).parent().parent().parent().parent();
        console.dir(parentComment);

        // 부모 코멘트의 번호
        const parentCommentOrder = $(".comment-author").index(parentComment) + 1;

        const parentCommentID = parentComment.parent().data("parentid");

        // 현재 쿼리값 파싱
        const params = new URLSearchParams(location.search);	

		// 부모 코멘트에서 pos 값을 찾습니다.
		if(!parentComment.data("pos")) {
			parentComment.data("pos", 0);
		} 
		
		// TODO: 부모 코멘트에 댓글이 하나 이상 있을 때, 
		// 부모 코멘트의 깊이보다 1 정도 큰,  
        // 마지막 자식 코멘트의 pos 값을 가져옵니다.

		// 부모 댓글의 순번
		// 같은 깊이의 댓글이 달리면 pos가 1, 2, 3으로 이어집니다.
		// 작성일순으로 정렬하면 pos도 필요 없지만 현재로썬 댓글 정렬을 위해 필요합니다.
		let parentPos = parseInt(parentComment.data("pos"));
//		if(parentPos) {
//			parentPos += 1;
//		}
		
		if(!parentComment.data("depth")) {
			parentComment.data("depth", 0);
		} 		
		
		// 부모 코멘트에서 깊이 값을 찾습니다.
		let depth = parseInt(parentComment.data("depth"));
//		if(depth) {
//			depth++;
//        }
		
		// 부모 코멘트에서 닉네임을 찾습니다.
		const nickname = parentComment.find("span").text();
        
        // 부모 코멘트 뒤에 댓글 작성란을 추가합니다.
        const uuid = UUID.builder();
        
        window["func_" + uuid] = function() {
            $(`div[data-uuid='${uuid}']`).remove();
        };

		const parent = $(ev.target).parents(".comment-area")[0];
        const self = parentComment.after(`
            <div class="detail-area" data-depth=${depth} data-pos=${parentPos + 1} data-uuid=${uuid}>
                <div class="add-comment-button-area">
                    <textarea id="child-comment-textarea" name="text"></textarea>
                    <input href="#" type="submit" id="child-comment-ok-button" class="btn btn-default" value="등록">
                	<button class="btn btn-default" id="close-sub-button" onclick="window['${'func_' + uuid}']()">닫기</button>
                </div>
            </div>
        `);

        // 대댓글 작성 버튼
        let area = self.next();

        area.find("#child-comment-ok-button").on("click", (ev) => {
            const yesNo = confirm("대댓글을 작성하시겠습니까?");

            // 대댓글 작성 버튼 삭제
            if(yesNo) {
    
                // 닉네임을 포함하여 댓글을 작성합니다.
		        const texts = $("#child-comment-textarea").val();
		        if(!texts.length) {
		            alert("댓글란이 비어있습니다.");
		            ev.preventDefault();
		            return false;
		        }
        
                // 주소에 글 번호 쿼리를 추가합니다.
		        const params = new URLSearchParams(location.search);
		        const postNumber = params.get("postNumber");
		
		        if(texts.length > 0) {
					
					// const parentID = parentCommentOrder;
					const parentID = $(parent).data("parentid");
			
		            $.get(
		                {
		                    url: `/board/qna/postReply.do`,
                            method: "POST",                      
                            data: {
                                postNumber: postNumber,
                                contents: texts,
                                method: "writeChild",
                                depth: depth,
                                parentID: parentID,
                                parentCommentID: parentCommentID,
                                pos: parentPos
                            },
		                    success: function(data) {
		                        location.reload();
		                    },
		                    error: function(err) {
		                        console.warn(err);
		                    }
		                }
		            )
		        }

                area.remove();
            }
        });

    },
    REPLAY_DELETE: function(ev) {
        const ret = window.confirm("댓글을 삭제하시겠습니까?");

        if(ret) {
			const comment = $(ev.target).parent().parent().parent().parent();
			const commentOrder = $(".comment-author").index(comment) + 1;
	        const params = new URLSearchParams(location.search);
	        const postNumber = params.get("postNumber");
			const texts = "empty";
			
			const parent = $(ev.target).parents(".comment-area")[0];
			
			// 코멘트 ID로 삭제
			const commentID = $(parent).data("commentid");

            $.get(
                {
                    url: `/board/qna/postReply.do?postNumber=${postNumber}&contents=${texts}&method=delete&depth=0&parentID=0&pos=0&commentOrder=${commentOrder}&commentID=${commentID}`,
                    method: "GET",
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        location.reload();
                    },
                    error: function(err) {
                        console.warn(err);
                    }
                }
            )
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

        const params = new URLSearchParams(location.search);
        const postNumber = params.get("postNumber");

        if(texts.length > 0) {
            $.get(
                {
                    url: `/board/qna/postReply.do?postNumber=${postNumber}&contents=${texts}&method=write&depth=0&parentID=0&pos=0`,
                    method: "GET",
                    contentType: false,
                    processData: false,
                    success: function(data) {
                        location.reload();
                    },
                    error: function(err) {
                        console.warn(err);
                    }
                }
            )
        }
    },
    BACK_TO_LIST: function(ev) {
    },
    MODIFY: function(ev) {
    },
    DELETE: function(ev) {

        const yesNo = confirm("정말로 삭제하시겠습니까?");
        const YES = true;

        if(yesNo == YES) {
            const params = new URLSearchParams(location.search);
            const postNumber = params.get("postNumber");

            $.get({
                url: `/board/qna/deletePost.do?postNumber=${postNumber}`,
                method: "GET",
                contentType: false,
                processData: false,
                success: function(data) {
                    location.href = "/pages/board-default.jsp";
                },
                error: function(err) {
                    console.warn(err);
                    alert(err);
                }                
            });
            
        } else {
            ev.preventDefault();
        }

    },
    POST_RECOMMAND_BUTTON: function(ev) {

        const params = new URLSearchParams(location.search);
        const postNumber = params.get("postNumber");

        if(!postNumber) {
            alert("잘못된 접근입니다.");
            return;
        }

        if(postNumber < 0) {
            alert("잘못된 게시물 번호입니다.");
            return;
        }

        if(!postNumber.match(/[\d]+/ig)) {
            alert("글 번호에 한글, 영문, 특수기호가 올 수 없습니다.");
            return;
        }

        $.ajax({
            url: `/board/qna/increaseRecommandCount.do?postNumber=${postNumber}`,
            method: "GET",
            success: function(data) {
                if(!data) data = "";
                if(data.indexOf("success") >= 0) {
                    location.reload();
                } else if(data.indexOf("duplicate") >= 0) {
                    alert("이미 추천하신 게시물입니다.");
                }

                console.log(data);
            },
            error: function(err) {
                console.warn(err);
            }
        });

    },
    POST_SHARE_BUTTON2: function(ev) {
        alert("글 공유하기 버튼을 눌렀습니다.");   
    }
};

window.BoardEvent = FUNC;

//=============================================================================
// ! 댓글 관련 기능
//=============================================================================

const SDK = (() => {
    class SDKImpl {

        constructor() {
            this._lastIndex = 0;
        }

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
                publishedTimeText = (Math.abs(23 - publishedTime.getHours()) + (currentTime.getHours())) % 24 + mark;
                let m = Math.abs(currentTime.getHours() - publishedTime.getHours());
                if(m === 0) {
                    m = "";
                    mark = "방금";
                }
                publishedTimeText = m + mark;
            }            

            return publishedTimeText;
        }

        /**
         * 댓글을 작성합니다.
         * 
         * @param {*} comment 
         * @param {*} index 
         */
        registerComment(comment, index) {
			const commentID = comment.commentID;
			const authorId = comment.author;
			const timer = comment.create_at;
			const texts = comment.contents;
			const depth = comment.depth;
			const pos = comment.pos;
			const parentID = comment.parentID;
			
            // const timeText = this.getTimeText(new Date(timer));
            const timeText = timer; 
            let commentRaw = "";
            /**
             * @type {String[]}
             */
            const lines = texts.split(/[\r\n]+/);
            lines.forEach(line => {
                commentRaw += `<span>${line}</span>`
            });

            if(!index) {
                index = 0;
            }

            $(".add-comment-button-area").before(
                $(`
                <div class="comment-area" data-commentID=${commentID} data-index=${index} data-depth=${depth} data-pos=${pos} data-parentID=${parentID}>
                    <div class="comment-author well" data-depth=${depth} data-pos=${pos} data-parentID=${parentID}>
                        <div class="profile-box">
                            <span><i class="fas fa-user-circle fa-3x"></i></span>
                        </div>
                        <div class="detail-area">
                            <div class="detail-area-author-id">
                                <span>${authorId}</span>       
                                <span>(댓글 번호 : <strong>${commentID}</strong>)</span>
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
                </div>
                <img src='data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7' onload='SDK.updateCommentsCount();this.parentNode.removeChild(this);'>       
                `).css("marginLeft", `${2 * depth}em`)
            );

            this._lastIndex++;
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
            contentType: "application/json; charset=UTF-8",
            success: function(data) {
                cb(data);
            },
            error: function(err) {
                alert("데이터를 볼 수 있는 권한이 없습니다");					

				history.go(-1);
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
            $(".board-article-contents").html(
                
                    (function() {
                        console.log(data.contents);
                        let {html} = JSON.parse(data.contents);

                        html = html.replace(/&lt;/ig, "<");
                        html = html.replace(/&gt;/ig, ">");
                        html = html.replace(/&amp;/ig, ";");
                        return html;
                    })()

                );

            $("#regdate").text(data.create_at);
            $("#viewcount").text(`조회수 ${data.view}`);
            $("#commentsCount").text(`댓글수 ${data.comments.length}개`);

            // 댓글 뱃지 업데이트
            $(".badge").text(data.comments.length);

            // 이미 작성된 댓글을 생성합니다.
            data.comments.forEach((comment, index) => {
                SDK.registerComment(comment, index);
            });

            // 이벤트 리스너를 연결합니다.
            for(let  i in FUNC) {
                if(i === "REPLAY_DELETE") {
                    continue;
                }
                if( $(ID[i]).length ) {
                    $(ID[i]).on("click", FUNC[i].bind(this));
                }
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
