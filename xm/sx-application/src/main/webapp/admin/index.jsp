<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>

</head>


<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    </ul>
    </div>
    
    <div class="mainindex">
    
    <div class="welinfo">
    <span><img src="images/sun.png" alt="天气" /></span>
    <b>${authorization_session_key.name}早上好，欢迎使用信息管理系统</b>
    <a href="#">帐号设置</a>
    </div>
    
    <div class="welinfo">
    <span><img src="images/time.png" alt="时间" /></span>
    <i>您上次登录的时间：${cookie[loginTime].value}</i> （不是您登录的？<a href="#">请点这里</a>）
    </div>
    
    <div class="xline"></div>
    
    <ul class="iconlist">
    
    <!--<li><img src="images/ico01.png" /><p><a href="#">管理设置</a></p></li>
    <li><img src="images/ico02.png" /><p><a href="#">发布文章</a></p></li>
    <li><img src="images/ico03.png" /><p><a href="#">数据统计</a></p></li>
    <li><img src="images/ico04.png" /><p><a href="#">文件上传</a></p></li>
    <li><img src="images/ico05.png" /><p><a href="#">目录管理</a></p></li>
    <li><img src="images/ico06.png" /><p><a href="#">查询</a></p></li> -->
            
    </ul>
    
    
    
    
    </div>
</body>
</html>
