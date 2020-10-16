<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/login.css">
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <style>
    </style>
</head>

<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
    </div>
</nav>

<div class="container">

    <form action="" method="post" class="form-signin" role="form" id="registerForm">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
        <div class="form-group has-success has-feedback">
            <input type="text" name="login-user" class="form-control" placeholder="请输入用户名" autofocus>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" name="login-pwd" class="form-control" placeholder="请输入密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="password" name="login-pwd-again" class="form-control" placeholder="请重复输入密码" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <div class="form-group has-success has-feedback">
            <input type="email" name="login-email" class="form-control" placeholder="请输入邮箱" style="margin-top:10px;">
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        </div>
        <button type="button" id="registerBtn" class="btn btn-lg btn-success btn-block">注册</button>
    </form>
</div>
<script type="text/javascript">
    $("#registerBtn").click(function () {
        var data = {};
        var value = $('#registerForm').serializeArray();
        $.each(value, function (index, item) {
            data[item.name] = item.value;
        });
        var paramData = JSON.stringify(data);

        $.ajax({
            "url": "admin/doRegister.json",
            "type": "post",
            "data": paramData,
            "contentType": "application/json;charset=UTF-8",
            "dataType": "json",
            "success": function (response) {
                if (response.result == 'SUCCESS') {
                    layer.msg("注册成功,请重新登录");
                    window.location.href = "/admin-webui/admin/login/page.html";
                } else {
                    layer.msg(response.message);
                }
            },
            "error": function (response) {
                layer.msg(response.message);
            }
        })
    });
</script>
</body>
</html>
