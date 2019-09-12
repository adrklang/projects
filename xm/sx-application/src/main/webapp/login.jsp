<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
    <link rel="stylesheet" href="/css/index.css">
    <jsp:include page="/WEB-INF/headers.jsp"></jsp:include>
    <style>

    </style>
<script type="text/javascript">

//读秒的方法
var iTime = 59;
var Account;
function RemainTime(){
	document.getElementById('zphone').disabled = true;
	var iSecond,sSecond="",sTime="";
	if (iTime >= 0){
		iSecond = parseInt(iTime%60);
		iMinute = parseInt(iTime/60)
		if (iSecond >= 0){
			if(iMinute>0){
				sSecond = iMinute + "分" + iSecond + "秒";
			}else{
				sSecond = iSecond + "秒";
			}
		}
		sTime=sSecond;
		if(iTime==0){
			clearTimeout(Account);
			sTime='获取手机验证码';
			iTime = 59;
			document.getElementById('zphone').disabled = false;
		}else{
			Account = setTimeout("RemainTime()",1000);
			iTime=iTime-1;
		}
	}else{
		sTime='没有倒计时';
	}
	document.getElementById('zphone').value = sTime;
}
</script>
</head>
<body>
<div id="app">
    <div class="register_head_on">

    </div>
    <div class="register_head">
        <a href="index.html"><img src="img/logo.jpg" alt=""></a>
        <div class="register_head_right">
            <p class="register_head_right_p1">小 米 商 城</p>
            <p class="register_head_right_p2">让每个人都享受科技乐趣</p>
        </div>

    </div>

    <div class="register">
        <div class="register_boby">
            <div class="register_boby_min">
                <div class="register_boby_no1">
                    <div class="register_boby_no1_in">
                        <span style="color: #ff6700">手机验证码登录 </span>
                    </div>
                </div>
                <div id="f3" action="" method="post">
                    <div class="register_boby_no2">
                        <span id="msg" style="color: red;font-size: 12px;margin-left: 20px;">{{msg}}</span>
                        <input id="phone_number" v-model="phoneNumber" name="phone_number" @blur="enable=true" type="text" placeholder="手机号码">
                        <input name="code" id="code" type="text" v-model="validCode"  placeholder="手机校验码" style="width: 200px; margin-left: 15px;float: left;">
                        <!-- 新增加 -->
                        <input id="zphone" type="button" value=" 获取手机验证码 " @click.prevent="sendValidCode" style="width: 138px;float: left;height: 53px;margin-left: 5px;">
                        <div style="clear: both;">

                            <div class="register_boby_no2_div">
                                <span id="sub" @click="phoneLogin">登录</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="register_boby_no3">
                    <a href="javascript:void(0);" style="color: #ff6700">账号密码登录</a>
                    <sapn class="register_boby_no3_span">
                        <a href="/register.jsp">立即注册</a>
                        <span>|</span>
                        <a href="avascript:void(0);">忘记密码?</a>
                    </sapn>

                </div>
                <div class="register_boby_no4">
                    <img src="img/register02.jpg" alt="">
                </div>

            </div>
        </div>
    </div>
    <div class="register_foot">
        <img  src="img/register03.jpg" alt="">
    </div>

</div>

</body>
<script type="text/javascript">
    let vue = new Vue({
        el:"#app",
        data:function(){
            return {
                phoneNumber:"",
                validCode:"",
                enable:false
            }
        },
        methods:{
            phoneLogin(){
              if(this.validPhoneNumber() && this.validCode != ''){
                  $.ajax({
                      url:"${pageContext.request.contextPath}/user/phoneLogin",
                      data:{
                          phoneNumber:this.phoneNumber,
                          validCode:this.validCode
                      },
                      type:"POST",
                      dataType:"json",
                      success:function(res){
                          toastr.success("登录成功，3秒后跳转首页");
                          setTimeout(function(){
                              window.location.href="/";
                          },3000)
                      },
                      error:function(err){
                          toastr.error(err.responseJSON.message)
                      }
                  })
              }
            },
            sendValidCode(){
                if(this.validPhoneNumber()){
                    let v = this;
                    if(this.validPhoneNumber()){
                        $.ajax({
                            url:"${pageContext.request.contextPath}/user/findByPhone",
                            data:{
                                phoneNumber:this.phoneNumber
                            },dataType:"json",
                            success:function(res){
                                toastr.error("用户未注册,请先注册");
                            },
                            error:function(err){
                                $.ajax({
                                    url:"${pageContext.request.contextPath}/sms/send",
                                    type:"post",
                                    data:{
                                      phoneNumber:v.phoneNumber
                                    },
                                    dataType:"json",
                                    success:function(res){
                                        RemainTime();
                                        toastr.success(res.message);
                                    },
                                    error:function(err){
                                        toastr.error("验证码发送失败");
                                    }
                                })
                            }
                        })
                    }

                }
            },
            validPhoneNumber(){
                this.enable = true;
                if(!/^1[3456789]\d{9}$/.test(this.phoneNumber)){
                    return false;
                }
                return true;
            }
        },
        computed:{
            msg(){
                if(this.enable){
                    if(!this.validPhoneNumber()){
                        return "手机号码格式不正确";
                    }
                }
                return "";
            }
        }
    })
</script>
</html>