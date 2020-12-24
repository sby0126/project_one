function closeMemberInformation() {
    $("#light-box").removeClass("active");
    $(".member-information-form").hide();
}

function showMemberInformation(postNumber) {
    $("#light-box").addClass("active");
    $(".member-information-form").show();
}

function openImageView(src) {   
    $("#light-box").addClass("active");
    $("#image-view").show();
    $("#image-view img").attr("src", src);
}

function hideImageView() {
    $("#light-box").removeClass("active");
    $("#image-view").fadeOut();
}

function getPostNumber() {
    return parseInt( $(this).data("number") ); 
}

$( ".post-modify" ).on("click", function() {		
    alert("글 수정 번호는 " + getPostNumber.call(this));
});

$( ".post-delete" ).on("click", function() {
    alert("글 삭제 번호는 " + getPostNumber.call(this));
});

$(".whole-member").on("click", function() {
    const memberId = $(this).data("number");
    location.href = `/members/modifyMemberForm.do?id=` + memberId;
});

$( ".forced-secession" ).on("click", function() {
    alert("강제로 탈퇴시킬 회원 번호는 " + $(this).data("number"));
    
    const self = this;
    
    const id = $(this).data("number");
    
    if(id === "admin") {
        alert("관리자는 탈퇴시킬 수 없습니다.");
        return false;
    }
    
    $.ajax({
        url: "/members/foclySecessionMember.do?id=" + id,
        method: "GET",
        success: function(data) {
            if(data.status === "success") {
                alert("탈퇴 처리가 완료되었습니다");
                // $("button.forced-secession[data-number='1568304956']").eq(0).parent().parent()[0]
                $(self).parent().parent().remove();
            }
        },
        error: function(err) {
            if(err.code === 401) {
                alert("권한이 없습니다");
            } else if(err.code === 402) {
                alert("글이 남아있는 상태입니다.");
            } else {
                console.warn(err);
            }
        }
    })
    
});

$(window).on("click", function(ev) {
    if(ev.target.classList.contains("modal")) {
        $(".modal").hide();
    }
});

   function verifyIp(ip) {
       return /^(([1-9]?\d|1\d\d|2[0-4]\d|25[0-5])(\.(?!$)|(?=$))){4}$/.test(ip||"");
   }

   // 로컬 IP 숨김 기능
$("#hide-local-ip").on("click", () => {
    const isChecked = $("#hide-local-ip").prop("checked");
    
    if(isChecked) {
        $("#ip-logging-table tr").each((index, elem) => {
            const text = $(elem).find("td:nth-child(1)").text();
            if(text.indexOf("192") >= 0) {
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
       
       if(items.length == 0) {
           $("#manage-whole-member-table tbody tr").show();
       } else {
           items.hide();
       }
   }
   
   // 특정 멤버를 검색합니다.
$("#search-specific-member").on("change", function() {
    const target = $(this);
    const val = target.val() || "";
    searchMemberTable(val.trim());
});
   
   $("#search-specific-member-button").on("click", () => {
       const val = $("#search-specific-member").val();
       searchMemberTable(val.trim());
       return false;
   });