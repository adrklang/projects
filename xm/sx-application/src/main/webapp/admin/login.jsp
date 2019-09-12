<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录小米后台管理系统</title>
<jsp:include page="/WEB-INF/headers.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/admin/css/style.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/admin/js/cloud.js" type="text/javascript"></script>

<script language="javascript">
	$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    })  
});  
</script> 

</head>

<body style="background-color:#1c77ac; background-image:url(images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">

    <div id="app">


        <div id="mainBody">
            <div id="cloud1" class="cloud"></div>
            <div id="cloud2" class="cloud"></div>
        </div>


        <div class="logintop">
            <span>欢迎登录后台管理界面平台</span>
            <ul>
                <li><a href="#">回首页</a></li>
                <li><a href="#">帮助</a></li>
                <li><a href="#">关于</a></li>
            </ul>
        </div>

        <div class="loginbody">

            <span class="systemlogo"></span>

            <div class="loginbox">


                <div action="" method="post">
                    <ul>
                        <li><input name="username" type="text" class="loginuser" v-model="user.username" value="" onclick="JavaScript:this.value=''"/></li>
                        <li><input name="password" type="password" class="loginpwd" v-model="user.password" value="" onclick="JavaScript:this.value=''"/></li>
                        <li><input name="" type="button" class="loginbtn" value="登录"  @click="login"  /><label><input name="" v-model="user.rememberMe" type="checkbox" value="" checked="checked" />记住密码</label><label><a href="#">忘记密码？</a></label></li>
                    </ul>
                </div>

            </div>

        </div>



        <div class="loginbm">版权所有  2013  .com 仅供学习交流，勿用于任何商业用途</div>
    </div>
</body>
<script type="text/javascript">
    let vue = new Vue({
        el:"#app",
        data:function(){
            return {
                user:{
                    username: "",
                    password:"",
                    rememberMe:false
                },
                username:"",
                message:""
            }
        },
        methods:{
            login(){
                if(this.validDataInfo()){
                    let v = this;
                    $.ajax({
                        url:"${pageContext.request.contextPath}/user/login",
                        type:'post',
                        data:v.user,
                        success:function(res){
                            toastr.success("登录成功，1秒后跳转管理界面");
                            if(v.user.rememberMe){
                                localStorage.setItem("info",escape(JSON.stringify(v.user)));
                            }else{
                                localStorage.removeItem("info");
                            }
                            setTimeout(function(){
                                window.location.href="${pageContext.request.contextPath}/admin/main.jsp";
                            },500)
                        },
                        error:function(err){
                            toastr.error("登录失败，用户名密码输入有误")
                        }
                    })
                }else{
                    toastr.error("登录失败，用户名密码输入有误")
                }
            },
            validDataInfo(){
                let passwordRegular = /^[a-zA-Z0-9]{5,16}$/;
                let usernameRegular = /^[a-zA-Z0-9]{5,10}$/;
                if(usernameRegular.test(this.user.username)){
                    if(passwordRegular.test(this.user.password)){
                        return true;
                    }
                    this.message = "密码输入有误[5-16]的数字加英文字母"
                    return false;
                }
                this.message = "用户名输入有误[5-10]的数字加英文字母"
                return false;
            }
        },
        mounted(){
            let user = localStorage.getItem("info");
            if(user){
                this.user = JSON.parse(unescape(user));
            }
        }
    })
</script>
</html>
