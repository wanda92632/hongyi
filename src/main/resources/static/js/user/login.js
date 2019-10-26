//用户登录
$(function () {
    layui.use('form', function () {
        var form = layui.form;
        //监听提交
        form.on('submit(login)', function (data) {
            var param = data.field;
            $.ajax({
                url: 'http://localhost:8080/hongyi/admin/login',
                type: 'POST',
                // 后端通过 @HttpRequestBody直接接收
                data: JSON.stringify(param),
                contentType: 'application/json',
                success: function (result) {
                    if (result.success) {
                        //localStorage.setItem("token",result.token);
                        layer.msg(JSON.stringify(result.token));
                        window.localStorage.setItem('token', result.token)
                        location.href = 'index.html';
                    } else {
                        layer.msg(JSON.stringify(result.errMsg));
                    }
                },
                error: function (xhr, status, error) {
                    layer.msg(xhr.statusText+':'+xhr.status)
                }
            });
            return false;
        });
    });
})