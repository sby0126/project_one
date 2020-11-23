
function join() {
    $(".main .content_login input").focus(function () {
        var read = $(this).prop("readonly");
        if (!read) {
            $(this).parent().find(".label1").css("display", "none")
        }
    }).blur(function () {
        var value = $(this).val()
        if (value == "")
            $(this).parent().find(".label1").css("display", "block")
    });
    
    $("#user_email1,#user_email2").focus(function () {
        $("#sp").html("@")
    }).blur(function () {
        $("#sp").html(" ")
    });
}

window.join = join;
