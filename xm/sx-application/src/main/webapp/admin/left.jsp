<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="js/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>


</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>功能列表</div>
    
    <dl class="leftmenu">
        
    <dd>
    <div class="title">
    <span><img src="images/leftico01.png" /></span>小米功能
    </div>
    	<ul class="menuson">
        <li><cite></cite><a href="index.jsp" target="rightFrame">欢迎页面</a><i></i></li>
        <li class="active"><cite></cite><a href="${pageContext.request.contextPath}/admin/user_list.jsp" target="rightFrame">用户管理</a><i></i></li>
        <li><cite></cite><a href="${pageContext.request.contextPath}/admin/category_list.jsp" target="rightFrame">分类管理</a><i></i></li>
        <li><cite></cite><a href="${pageContext.request.contextPath}/admin/product_list.jsp" target="rightFrame">商品管理</a><i></i></li>
        <li><cite></cite><a href="" target="rightFrame">订单管理</a><i></i></li>
        <li><cite></cite><a href="" target="rightFrame">支付信息管理</a><i></i></li>
        <li><cite></cite><a href="" target="rightFrame">收获地址管理</a><i></i></li>
        <li><cite></cite><a href="" target="rightFrame">退货/返货</a><i></i></li>
        <li><cite></cite><a href="" target="rightFrame">导航信息管理</a><i></i></li>
	
        </ul>    
    </dd>
</body>
</html>
