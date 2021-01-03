(function () {

    // 아이콘을 변경합니다.
    let icons = Quill.import("ui/icons");
    icons["image"] = `<i class="fas fa-arrow-circle-up"></i>`;

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

    // let Inline = Quill.import('blots/inline');

    // class XVideoBlot extends Inline { }
    // XVideoBlot.blotName = 'div';
    // XVideoBlot.tagName = 'a';
    
    // Quill.register(XVideoBlot);    
    window.quill = quill;
    
	function setTitle(text) {
		$("#input-item-title").val(text);	
		$(".contents-item-title span").css("visibility", "hidden");
	}
    
    /**
     * 게시물을 Quill 라이브러리의 델타 오브젝트로 채우는 기능입니다.
     * 
     * @param {Object} text 
     */
	function setContent(text) {
        text = JSON.parse(decodeURIComponent(text.replace(/\+/g, ' ')));
        const contents = text.contents;
        quill.setContents(contents);
    }
    
    const type = $("#type").val() || "none";
	       	
	if(type && type != "" && type == "modify") {
        setTitle($("#title").val());

        let contents = $("#contents").val();

		setContent(contents);    
	}
	
    /**
     * 이미지를 업로드 합니다.
     */
    function selectImage() {
        const input = document.createElement("input");
        input.type = "file";
        input.onchange = function () {
            const formData = new FormData();
            const file = $(this)[0].files[0];
            formData.append('image', file);

            const filename = file.name || "";
            let mimeType = '';

            if(filename.toLowerCase().indexOf(".png") >= 0) {
                console.log("이미지입니다.")
                mimeType = "image";
            } else if(filename.indexOf(".zip") >= 0) {
                console.log("압축 파일입니다...");
                mimeType = "zip";
            }

            // AJAX를 통해 이미지를 서버에 업로드합니다.
            $.ajax({
                type: "POST",
                enctype: "multipart/form-data",
                url: "/board/qna/imageUpload.do",
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    const range = quill.getSelection();
                    if(mimeType == "image") {
                        quill.insertEmbed(range.index, 'image', "/uploads/" + data.url);
                    } else {
                        let href = "/download.do?filename=" + data.url;
                        quill.clipboard.dangerouslyPasteHTML(range.index + 1, `<p><a href='${href}'><i class="fas fa-file-archive">${data.url}</i><a></p>`);
                    }
                },
                error: function (err) {
                    console.warn(err);
                }
            })
        }

        input.click();
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

        // https://stackoverflow.com/questions/54835375/how-to-edit-and-save-my-html-file-using-quill
        const result = {
			type,
            title: $("#input-item-title").val(),
            src: "",
			contents: JSON.stringify({
                html: quill.root.innerHTML,
                contents: quill.getContents(),
            })
        };

        let prevJson = JSON.stringify(result);

        // 리눅스에서 동작 안함....
        // navigator.clipboard.writeText(prevJson);

        const param = new URLSearchParams(location.search);
        const postNumber = param.get("postNumber") || 0;

        // 게시물 데이터를 보냅니다.
        $.ajax({
            url: "/board/qna/writeForm.do?postNumber=" + postNumber,
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

    window.quill = quill;

})();
