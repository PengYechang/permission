<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/favicon.ico">

    <title>新用户注册</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap3.3.5/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<div class="container">
    <form class="form-horizontal" action="/register">
        <h2 class="text-center">请注册</h2>
        <div class="form-group">
            <label for="username" class="col-sm-offset-2 col-sm-2 control-label">昵称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="username" name="username" placeholder="Nickname" value="${username}">
            </div>
        </div>
        <div class="form-group">
            <label for="telephone" class="col-sm-offset-2 col-sm-2 control-label">电话</label>
            <div class="col-sm-4">
                <input type="tel" class="form-control" id="telephone" name="telephone" placeholder="Telephone" value="${telephone}">
            </div>
        </div>
        <div class="form-group">
            <label for="mail" class="col-sm-offset-2 col-sm-2 control-label">邮箱</label>
            <div class="col-sm-4">
                <input type="email" class="form-control" id="mail" name="mail" placeholder="Mail" value="${mail}">
            </div>
        </div>
        <div class="form-group">
            <label for="password1" class="col-sm-offset-2 col-sm-2 control-label">密码</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="password1" name="password1" placeholder="Password" value="${password1}">
            </div>
        </div>
        <div class="form-group">
            <label for="password2" class="col-sm-offset-2 col-sm-2 control-label">确认密码</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="password2" name="password2" placeholder="Password" value="${password2}">
            </div>
        </div>
        <div class="checkbox col-sm-offset-4" style="color: red;">${error}</div>
        <div class="form-group">
            <div class="col-sm-offset-4 col-sm-4">
                <button type="submit" class="btn btn-lg btn-info btn-block">注册</button>
            </div>
        </div>
    </form>
</div>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
