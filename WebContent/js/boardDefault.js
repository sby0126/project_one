/**
 * @author 어진석
 */
(function() {
    
    let postCount = 100;

    // 한 페이지에 표시되는 글의 수
    let maxPostOfPage = 10;

    // 전체 게시물 수 / 한 페이지에 표시되는 글의 수
    let perPage = (postCount / maxPostOfPage) + 1;

    // 전체 페이지 수
    let pageCount = Math.floor(postCount / perPage) + 1;

    // TODO: 현재 페이지를 구하려면, maxPostOfPage + 1로 나누면 된다.
    let currentPostNumber = 18;
    let currentPage = (currentPostNumber / perPage) + 1;

    // 게시판 데이터 스키마
    let boardSchema = [
        {
            postNo: 0,
            postType: 'N', // Normal.
            postTitle: "",
            name: "",
            create_at: new Date(),
            view: 0,
            recommandCount: 0
        }
    ]

    let columns = {
        "postNumber": "글 번호",
        "postType": "분류",
        "postTitle": "글 제목",
        "name": "이름",
        "create_at": "날짜",
        "view": "조회",
        "recommandCount": "추천"        
    }

    let boardData = [{
        "postNumber": "18",
        "postType": "Notice",
        "postTitle": "공지사항입니다.",
        "name": "관리자",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "17",
        "postType": "Normal",
        "postTitle": "반품 관련으로 문의합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "16",
        "postType": "Normal",
        "postTitle": "걱정입니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "15",
        "postType": "Normal",
        "postTitle": "반품 문의 합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "14",
        "postType": "Normal",
        "postTitle": "반품 문의 합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "13",
        "postType": "Normal",
        "postTitle": "반품 관련으로 문의합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "12",
        "postType": "Normal",
        "postTitle": "걱정입니다.[2]",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "11",
        "postType": "Normal",
        "postTitle": "반품 문의 합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "10",
        "postType": "Normal",
        "postTitle": "반품 관련으로 문의합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "9",
        "postType": "Normal",
        "postTitle": "걱정입니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "8",
        "postType": "Normal",
        "postTitle": "반품 문의 합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "7",
        "postType": "Normal",
        "postTitle": "반품 관련으로 문의합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "6",
        "postType": "Normal",
        "postTitle": "걱정입니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "5",
        "postType": "Normal",
        "postTitle": "반품 문의 합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "4",
        "postType": "Normal",
        "postTitle": "반품 관련으로 문의합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "3",
        "postType": "Normal",
        "postTitle": "걱정입니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "2",
        "postType": "Normal",
        "postTitle": "반품 문의 합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }, {
        "postNumber": "1",
        "postType": "Normal",
        "postTitle": "반품 관련으로 문의합니다.",
        "name": "kjkj****",
        "create_at": "2020.11.20",
        "view": "0",
        "recommandCount": "0"
    }];

    const Editor = {
        init() {

            $("#editor .table tbody").append(`
            <tr>
                <td>데이터가 없습니다</td>
            </tr>
        `);

            this.load(this.initWithEvent.bind(this));
            this.initWithPages();
        },

        /**
         * 모바일에서 구동 중인지 확인합니다.
         */
        isMobileDevice() {
            // userAgent가 모바일이면 모바일 기기로 판단
            const isMobile = (/android|webos|iphone|ipad|ipod|blackberry|iemobile|opera mini/i.test(navigator.userAgent.toLowerCase()));
            if(isMobile) return true;
            // 가로 길이가 720 이하라면 모바일 기기로 판단
            isMobile = $(window).innerWidth() <= 720;
            return isMobile;
        },

        load(cb) {
            $.ajax({
                url: "/board/qna/listAll.do",
                method: "GET",
                contentType: "application/json",
                success: function(data) {
                    cb(data);
                },
                error: function(err) {
                    console.log(err);
                }
            });
        },

        initWithEvent(data) {
            const self = this;
            this._elem = $("#editor");

            const postData = [];

            if(data && Array.isArray(data) && data.length > 0) {
                $("#editor .table tbody tr").remove();
            }

            data.forEach((i, idx) => {
                $("#editor .table tbody").append(`
                    <tr>
                        <td>${i.postNumber}</td>
                        <td>${i.postType}</td>
                        <td><a href="#">${i.postTitle}</a></td>
                        <td>${i.name}</td>
                        <td>${i.create_at}</td>
                        <td>${i.view}</td>
                        <td>${i.recommandCount}</td>
                    </tr>
                `);

                postData.push(i);

            });

            const fancy = $("#editor .table").fancyTable({
                sortColumn: 0,
                sortOrder:'descending',
                searchable:false,   
                sortable: true,     
                pagination: true,     
                perPage: 10,
                paginationElement: ".board-footer-pages-buttons-wrapper",
                onUpdate: () => {
                    this._elem.find("td > a").each((index, item) => {
                        $(item).on("click", function(ev) {
                            let url = new URLSearchParams(location.search);
                            const postNumber = $(this)
                            .parent()
                            .parent()
                            .find("td").eq(0).text();

                            // url.set("postNumber", postData[index].postNumber);
                            url.set("postNumber", postNumber);

                            location.href = "board-post.jsp?" + url.toString();  
                        });
                    });
        
                }
            });

        },

        initWithPages() {

        },

        nextPage() {

        },

        prevPage() {

        },

        toJson() {

            const data = [];

            $("#editor").find("tr").each((i, elem) => {
                const tdItems = $(elem).children();
                data.push({
                    postNumber: tdItems.eq(0).text(),
                    postType: tdItems.eq(1).text().trim() === "공지" ? "Notice" : "Normal",
                    postTitle : tdItems.eq(2).text(),
                    name : tdItems.eq(3).text(),
                    create_at: tdItems.eq(4).text(),
                    view: tdItems.eq(5).text(),
                    recommandCount: tdItems.eq(6).text()
                });
            });

            return data;
        }
    };

    Editor.init();

})();