/**
 * @author 어진석
 * 
 */
(function() {

    const toolkit = {

        init() {
            $("#example tbody").on("click", '.click-post', function () {
                alert("글 번호는 " + $(this).parent().prev().text() + " 입니다.");
            });          
            this.createButton();
            this.addWriteButtonEvent();
        },

        refresh() {
            // this.createButton();
            // this.addWriteButtonEvent();
        },
        
        createButton() {
            $("#example")
                .after(`<a id="write-button" href="#" class="primary-button">글쓰기</a>`);
        },

        addWriteButtonEvent() {
            $("#write-button").on("click", (ev) => {
                alert("글 작성");
            });                
        },

        createElement(targetName, attr) {
            if(!targetName) return;
            if(!attr) return

            return Object.assign(document.createElement(targetName), attr);
        },

        changeStyle(elementName, styleProperties) {
            const elem = document.querySelector(elementName);
            if(!elem) return;

            Object.assign(elem.style, styleProperties);
        }
    }

    const lang = {
        "decimal": "",
        "emptyTable": "데이터가 없습니다.",
        "info": "_START_ - _END_ (총 _TOTAL_ 개)",
        "infoEmpty": "0명",
        "infoFiltered": "(전체 _MAX_ 개 중 검색결과)",
        "infoPostFix": "",
        "thousands": ",",
        "lengthMenu": "_MENU_ 개씩 보기",
        "loadingRecords": "로딩중...",
        "processing": "처리중...",
        "search": "검색 : ",
        "zeroRecords": "검색된 게시물이 없습니다.",
        "paginate": {
            "first": "첫 페이지",
            "last": "마지막 페이지",
            "next": "다음",
            "previous": "이전"
        }
    }
    
    const table = $('#example').DataTable({
        "language": lang,
        "aria": {
            "sortAscending": " :  오름차순 정렬",
            "sortDescending": " :  내림차순 정렬"
        },
        dom: `l<"toolbar">frtip`,
        initComplete() {
            toolkit.init();
        },
        "responsive": true
    }).on("page", () => toolkit.refresh());            

})();