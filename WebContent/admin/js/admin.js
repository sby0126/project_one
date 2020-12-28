Object.assign(window, {

    /**
     * 멤버 정보창을 닫습니다.
     */
    closeMemberInformation() {
        $("#light-box").removeClass("active");
        $(".member-information-form").hide();
    },

    /**
     * 멤버 정보 창을 표시합니다.
     * @param {Number} postNumber 
     */
    showMemberInformation(postNumber) {
        $("#light-box").addClass("active");
        $(".member-information-form").show();
    },

    /**
     * 이미지 보기 창을 표시합니다.
     * 
     * @param {String} src 
     */
    openImageView(src) {
        $("#light-box").addClass("active");
        $("#image-view").show();
        $("#image-view img").attr("src", src);
    },

    /**
     * 이미지 숨기기 창을 표시합니다.
     * 
     */
    hideImageView() {
        $("#light-box").removeClass("active");
        $("#image-view").fadeOut();
    },

    getPostNumber() {
        return parseInt($(this).data("number"));
    },

});

(function () {

    $(".post-modify").on("click", function () {
        alert("글 수정 번호는 " + getPostNumber.call(this));
    });

    $(".post-delete").on("click", function () {
        alert("글 삭제 번호는 " + getPostNumber.call(this));
    });

    $(".whole-member").on("click", function () {
        const memberId = $(this).data("number");
        location.href = `/members/modifyMemberForm.do?id=` + memberId;
    });

    $(".forced-secession").on("click", function () {
        alert("강제로 탈퇴시킬 회원 번호는 " + $(this).data("number"));

        const self = this;

        const id = $(this).data("number");

        if (id === "admin") {
            alert("관리자는 탈퇴시킬 수 없습니다.");
            return false;
        }

        $.ajax({
            url: "/members/foclySecessionMember.do?id=" + id,
            method: "GET",
            success: function (data) {
                if (data.status === "success") {
                    alert("탈퇴 처리가 완료되었습니다");
                    // $("button.forced-secession[data-number='1568304956']").eq(0).parent().parent()[0]
                    $(self).parent().parent().remove();
                }
            },
            error: function (err) {
                if (err.code === 401) {
                    alert("권한이 없습니다");
                } else if (err.code === 402) {
                    alert("글이 남아있는 상태입니다.");
                } else {
                    console.warn(err);
                }
            }
        })

    });

    $(window).on("click", function (ev) {
        if (ev.target.classList.contains("modal")) {
            $(".modal").hide();
        }
    });

    function verifyIp(ip) {
        return /^(([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])(\.(?!$)|(?=$))){4}$/.test(ip || "");
    }

    // 로컬 IP 숨김 기능
    $("#hide-local-ip").on("click", () => {
        const isChecked = $("#hide-local-ip").prop("checked");

        if (isChecked) {
            $("#ip-logging-table tr").each((index, elem) => {
                const text = $(elem).find("td:nth-child(1)").text();
                if (text.indexOf("192") >= 0) {
                    $(elem).hide();
                } else if(text.indexOf("0:0:0:0:0:0:0:1") >= 0) {
                    $(elem).hide();
                } else if(text.indexOf("10.0.0.2") >= 0) {
                    $(elem).hide();
                }
            })
        } else {
            $("#ip-logging-table tr").each((index, elem) => {
                $(elem).show();
            });
        }
    });

    /**
     * 멤버 검색 기능입니다.
     */
    function searchMemberTable(keyword) {
        const items = $("#manage-whole-member-table tbody tr").filter((index, elem) => {
            // 특정 검색어가 없으면   	    		
            return $(elem).text().indexOf(keyword) === -1;
        });

        if (items.length == 0) {
            $("#manage-whole-member-table tbody tr").show();
        } else {
            items.hide();
        }
    }

    // 특정 멤버를 검색합니다.
    $("#search-specific-member").on("change", function () {
        const target = $(this);
        const val = target.val() || "";
        searchMemberTable(val.trim());
    });

    $("#search-specific-member-button").on("click", () => {
        const val = $("#search-specific-member").val();
        searchMemberTable(val.trim());
        return false;
    });

    $("#return-button").on("click", ev => {
        location.href = "/index.jsp";
        return true;
    })

    $("#uploads").find("input[name='file']").on("click", (ev) => {
        const len = $("#uploads").find("input[name='file']").filter((index, elem) => {
            return $(elem).prop("checked") === true;
        }).length;

        if (len > 0) {
            $("#multiple-files-delete-button").removeClass("disabled");
        } else {
            $("#multiple-files-delete-button").addClass("disabled");
        }

        $("#selection-file-count").text(len);
    });

    // 전체 선택 구현
    $("#all-file-selection").on("click", ev => {
        $("#uploads").find("input[name='file']").each((index, elem) => {
            const isChecked = $("#all-file-selection").prop("checked");
            $(elem).prop("checked", isChecked);
        });

        const len = $("#uploads").find("input[name='file']").filter((index, elem) => {
            return $(elem).prop("checked") === true;
        }).length;        

        if (len > 0) {
            $("#multiple-files-delete-button").removeClass("disabled");
        } else {
            $("#multiple-files-delete-button").addClass("disabled");
        }        

        $("#selection-file-count").text(len);
    });

    $("#multiple-files-delete-button").on("click", ev => {
        const form = document.createElement("form");
        form.action = "/myadmin/multipleFileDelete.do";

        /**
         * @type {JQuery[]}
         */
        let items = [];

        $("#uploads").find("input[name='file']").each((index, elem) => {
            if ($(elem).length > 0) {
                items.push($(elem));
            }
        });

        // 자식 체크 박스를 만듭니다.
        items.forEach(elem => {

            const childInput = document.createElement("input");
            childInput.name = elem.prop("name");
            childInput.value = elem.val();

            form.appendChild(childInput);

        });

        // 활성화 되어있는 게 1개 이상이면 폼을 전송합니다.
        const isPossibleToDelete = items.filter(i => i.prop("checked")).length > 0;

        if(isPossibleToDelete) {
            document.body.appendChild(form);
            
            form.onsubmit = function() {
                setTimeout(function() {
                    document.body.removeChild(form);
                }, 20);
                
                return true;
            }
            
            form.submit();
        }

    });

})();

class SelectionManager {
    constructor() {
        this._container = null;
        this._allFileSelection = null;
        this._multipleFilesButton = null;
        this._selectionFileCount = null;
    }

    addContainer(container) {
        this._container = container;
        return this;
    }

    addAllFileSelection(elem) {
        this._allFileSelection = elem;
        return this;
    }

    addMultipleFilesButton(elem) {
        this._multipleFilesButton = elem;
        return this;
    }

    addSelectionFileCount(elem) {
        this._selectionFileCount = elem;
        return this;
    }

    addAction(action) {
        $(this._multipleFilesButton).on("click", ev => {
            const form = document.createElement("form");
            form.action = "/myadmin/multipleFileDelete.do";
    
            /**
             * @type {JQuery[]}
             */
            let items = [];
    
            $("#uploads").find("input[name='file']").each((index, elem) => {
                if ($(elem).length > 0) {
                    items.push($(elem));
                }
            });
    
            // 자식 체크 박스를 만듭니다.
            items.forEach(elem => {
    
                const childInput = document.createElement("input");
                childInput.name = elem.prop("name");
                childInput.value = elem.val();
    
                form.appendChild(childInput);
    
            });
    
            // 활성화 되어있는 게 1개 이상이면 폼을 전송합니다.
            const isPossibleToDelete = items.filter(i => i.prop("checked")).length > 0;
    
            if(isPossibleToDelete) {
                document.body.appendChild(form);
                
                form.onsubmit = function() {
                    setTimeout(function() {
                        document.body.removeChild(form);
                    }, 20);
                    
                    return true;
                }
                
                form.submit();
            }
    
        });
    }

    click() {

        $(this._allFileSelection).on("click", ev => {
            $(this._container).find("input[name='file']").each((index, elem) => {
                const isChecked = $(this._allFileSelection).prop("checked");
                $(elem).prop("checked", isChecked);
            });
    
            const len = $(this._container).find("input[name='file']").filter((index, elem) => {
                return $(elem).prop("checked") === true;
            }).length;        
    
            if (len > 0) {
                $(this._multipleFilesButton).removeClass("disabled");
            } else {
                $(this._multipleFilesButton).addClass("disabled");
            }        
    
            $(this._selectionFileCount).text(len);
        });

    }

}