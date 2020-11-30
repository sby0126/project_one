(function () {

    // 게시물 에디터의 인스턴스를 만듭니다.
    var quill = new Quill('.editor', {
        modules: {
            toolbar: [
                ['bold', 'italic', 'underline', 'strike'], // toggled buttons
                ['blockquote', 'code-block'],

                [{
                    'header': 1
                }, {
                    'header': 2
                }], // custom button values
                [{
                    'list': 'ordered'
                }, {
                    'list': 'bullet'
                }],
                [{
                    'script': 'sub'
                }, {
                    'script': 'super'
                }], // superscript/subscript
                [{
                    'indent': '-1'
                }, {
                    'indent': '+1'
                }], // outdent/indent
                [{
                    'direction': 'rtl'
                }], // text direction

                [{
                    'size': ['small', false, 'large', 'huge']
                }], // custom dropdown
                [{
                    'header': [1, 2, 3, 4, 5, 6, false]
                }],

                [{
                    'color': []
                }, {
                    'background': []
                }], // dropdown with defaults from theme
                [{
                    'font': []
                }],
                [{
                    'align': []
                }],
                ['image'],
                ['clean'] // remove formatting button
            ]
        },
        placeholder: '내용을 작성하세요.',
        theme: 'snow',
    });

    /**
     * 이미지를 업로드 합니다.
     */
    function selectImage() {
        const input = document.createElement("input");
        input.type = "file";
        input.click();

        input.onchange = function () {
            const formData = new FormData();
            const file = $(this)[0].files[0];
            formData.append('image', file);

            $.ajax({
                type: "post",
                enctype: "multipart/form-data",
                url: "board/qna/imageUpload.do",
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    const range = quill.getSelection();
                    quill.insertEmbed(range.index, 'image', '/upload/' + data);
                },
                error: function (err) {
                    console.warn(err);
                }
            })
        }
    }

    quill.getModule("toolbar").addHandler('image', () => {
        selectImage();
    });

    // 업로드 버튼 클릭 시 
    document.querySelector("#upload-ok").onclick = function () {

        if ($("#input-item-title").val().length == 0) {
            alert("글 제목을 적지 않았습니다.");
            return false;
        }

        const result = {
            title: $("#input-item-title").val(),
            src: "",
            contents: quill.root.innerHTML
        };

        let prevJson = JSON.stringify(result);

        navigator.clipboard.writeText(prevJson);

        const param = new URLSearchParams(location.search);
        const postNumber = param.get("postNumber") || 0;

        // 게시물 데이터를 보냅니다.
        $.ajax({
            url: "/board/qna/writeForm.do",
            method: "POST",
            data: prevJson,
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                // 게시물 목록으로 리다이렉션 합니다.
                location.href = "/pages/board-default.jsp";
            },
            error: function (err) {
                console.log(err);
            }
        });

    }

    // placeholder 제어
    $("#input-item-title").on("change", (ev) => {
        /**
         * @type {HTMlInputElement
         */
        const target = ev.target;

        if (target.value && target.value.length > 0) {
            $("#input-item-title ~ span").css("visibility", "hidden");
        } else {
            $("#input-item-title ~ span").css("visibility", "visible");
        }

    });

    $("upload-cancel").on("click", () => {
        history.back();
    });

    const App = {
        init() {
            const params = new URLSearchParams(location.search);
            const postNumber = params.get("postNumber");
        },

        async load() {
            return new Promise((resolve, reject) => {
                $.post("", (data, textStatus, jqXHR) => {

                });
            });
        }
    }

})();
