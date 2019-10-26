//用户登录
$(function () {
    $("#logout").click(function () {
        $.ajax({
            url: "http://localhost:8080/hongyi/admin/logout",
            type: "GET",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + window.localStorage.getItem('token'));
            },
            contentType: "application/json",
            success: function (result) {
                layer.msg("推出成功");
            },
            error: function (xhr, status, error) {
                alert(window.localStorage.getItem('token'))
                layer.msg(xhr.statusText + ":" + xhr.status);
            }
        });
    });
});
