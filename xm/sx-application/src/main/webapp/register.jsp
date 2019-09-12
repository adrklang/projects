<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册页面</title>
    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/register.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <jsp:include page="/WEB-INF/headers.jsp"></jsp:include>
</head>
<body>
<div class="sign_background" id="app">
    <div class="sign_background_in">
        <div class="sign_background_no1">
            <a href="/index.jsp"><img src="/img/logo.jpg" alt=""></a>
        </div>
        <div class="sign_background_no2">注册小米帐号</div>
        <div class="sign_background_no3">
            <font color='red'></font>
            <div class="sign_background_no5">
                <!-- enctype="multipart/form-data" -->
                <form id="f4" method="post">
                    <table style="width: 500px;" border="0" cellspacing="0">
                        <tr>
                            <td width="25%" class="_left">姓名：</td>
                            <td>
                                <input type="text" @blur.prevent="findByName" v-model="user.name" name="name">
                                <span :style="global.style" v-text="validInfo.name" id="s_name"></span>
                            </td>
                        </tr>
                        <tr>
                            <td width="25%" class="_left">性别：</td>
                            <td>
                                男<input type="radio" name="sex" @click="user.sex = 1" checked="checked" value="1">
                                女<input type="radio" name="sex" @click="user.sex = 0" value="0">
                            </td>
                        </tr>
                        <tr>
                            <td width="25%" class="_left">电话号码：</td>
                            <td>
                                <input type="text" @blur.prevent="findByPhone"
                                       v-model="user.phoneNumber" id="phone_number" name="phoneNumber">
                                <span id="s_phone_number" :style="global.style" v-text="validInfo.phoneNumber"></span>
                            </td>
                        </tr>
                        <tr>
                            <td width="25%" class="_left">所在地区：</td>
                            <td><input type="text" v-model="user.area" name="area"></td>
                        </tr>
                        <tr>
                            <td width="25%" class="_left">用户名：</td>
                            <td>
                                <input type="text" @blur.prevent="findByUsername" v-model="user.username"
                                       id="username" name="username">
                                <span :style="global.style" v-text="validInfo.username" id="s_username"></span>
                            </td>
                        </tr>
                        <tr>
                            <td width="25%" class="_left">密码：</td>
                            <td>
                                <input type="password" @blur.prevent="valid.password.enable=true"
                                       v-model="user.password" id="password" name="password">
                                <span :style="global.style" v-text="validInfo.password" id="s_password"></span>
                            </td>
                        </tr>
                        <tr>
                            <td width="25%" class="_left">上传头像：</td>
                            <td>
                                <input id="file" @change="uploadFile" type="file" style="display: none" name="photo">
                                <span @click="upload" class="btn btn-primary">上传头像</span>
                                <img v-if="user.photo" style="width: 45px;height: 45px;-webkit-border-radius: 100%;-moz-border-radius: 100%;border-radius: 100%;" :src="user.photo" />
                            </td>
                        </tr>
                    </table>
                    <div class="sign_background_no6" id="btn" @click="register">立即注册</div>
                </form>

            </div>
        </div>
        <div class="sign_background_no7">注册帐号即表示您同意并愿意遵守小米 <span>用户协议</span>和<span>隐私政策</span></div>


    </div>
    <div class="sign_background_no8"><img src="img/sign01.jpg" alt=""></div>

</div>
<script type="text/javascript">

    let app = new Vue({
        el: "#app",
        data: function () {
            return {
                global: {
                    style: 'color:red;font-size:12'
                },
                user: {
                    name: "",
                    sex: 1,
                    phoneNumber: "",
                    area: "",
                    username: "",
                    password: "",
                    photo: ""
                },
                validResultInfo:{
                    name:"",
                    phoneNumber:"",
                    username:""
                },
                valid: { //验证规则
                    name: {
                        length: 10,
                        empty: "请输入昵称",
                        message: "昵称最大长度为10",
                        enable: false,
                        match: false
                    },
                    phoneNumber: {
                        regular: /^1[3456789]\d{9}$/,
                        message: "电话号码格式不正确",
                        empty: "请输入电话号码",
                        enable: false,
                        match: false
                    },
                    username: {
                        length: 10,
                        empty: "请输入用户名",
                        message: "用户名最大长度为10",
                        notMatch: "用户名输入有误",
                        regular: /^[a-zA-Z0-9]{5,10}$/,
                        enable: false,
                        match: false
                    },
                    password: {
                        length: 16,
                        empty: "请输入密码",
                        message: "密码最大长度为16",
                        notMatch: "密码输入有误",
                        regular: /^[a-zA-Z0-9]{5,16}$/,
                        enable: false,
                        match: false
                    }
                }
            }
        },
        computed: {
            validInfo: function () {
                let message = {};
                for (let key in this.valid) {
                    message[key] = this.matches(this.user[key], this.valid[key]);
                }
                return message;
            }
        },
        methods: {

            findByUsername() {
                this.valid.username.enable = true;
                let v = this;
                if(this.valid.username.regular.test(this.user.username)){
                    this.valid.username.match = true;
                }
                if (this.valid.username.match) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/user/findByUsername",
                        data: {
                            username: this.user.username
                        },
                        type: "GET",
                        dataType: "json",
                        success: function (res) {

                        },
                        error: function (err) {
                            toastr.error("用户名已存在");
                        }
                    })
                }
            },
            findByPhone() {
                this.valid.phoneNumber.enable = true;
                let v = this;
                if(this.valid.phoneNumber.regular.test(this.user.phoneNumber)){
                    this.valid.phoneNumber.match = true;
                }
                if (this.valid.phoneNumber.match) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/user/findByPhone",
                        data: {
                            phoneNumber: this.user.phoneNumber
                        },
                        type: "GET",
                        dataType: "json",
                        success: function (res) {

                        },
                        error: function (err) {
                            toastr.error("手机号码已存在");
                        }
                    })
                }
            },
            findByName() {
                this.valid.name.enable = true;
                let v = this;
                if(this.user.name.length > 0 && this.user.name.length <= this.valid.name.length){
                    this.valid.name.match = true;
                }
                if (this.valid.name.match) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/user/findByName",
                        data: {
                            name: this.user.name
                        },
                        type: "GET",
                        dataType: "json",
                        success: function (res) {

                        },
                        error: function (err) {
                            toastr.error("昵称已存在")
                        }
                    })
                }
            },
            uploadFile(){
                let v = this;
                let data = new FormData();
                let file = $("#file")[0].files[0];
                if(file.type.indexOf("jpeg") != -1 || file.type.indexOf("png") != -1 || file.type.indexOf("gif")){
                   if(file.size < (1024 * 5 * 1024)){
                       data.append("upload",file);
                       $.ajax({
                           url:"${pageContext.request.contextPath}/upload/file",
                           data: data,
                           dataType:"json",
                           processData : false, // 使数据不做处理
                           contentType : false,
                           type:"POST",
                           success:function(res){
                                v.user.photo = res.message;
                           },
                           error:function(err){
                               toastr.error("头像上传失败")
                           }
                       })
                   }else{
                       toastr.error("上传文件过大")
                   }
                }else{
                    toastr.error("上传文件不是图片")
                }

            },
            upload() {
                $("#file")[0].click();
            },
            register() {
                if (this.validUser()) {
                    $.ajax({
                        url: "${pageContext.request.contextPath}/user/register",
                        data: this.user,
                        type: "POST",
                        dataType: "json",
                        success: function (res) {
                            toastr.success("注册成功，3秒后跳转到登录页面");
                            setTimeout(function(){
                                window.location.href = "${pageContext.request.contextPath}/login.jsp";
                            },3000)
                        },
                        error: function (err) {
                            toastr.error("用户注册失败，请稍后重新注册")
                        }
                    })
                }
            },
            validUser() {
                for (let key in this.valid) {
                    this.valid[key].enable = true;
                    if (this.valid[key].match == false) {
                        return false;
                    }
                }
                return true;
            },
            matches(userVal, validObj) {
                validObj.match = false;
                if (validObj.enable) {
                    if (validObj.empty) {
                        if (userVal.trim() == "") {
                            return validObj.empty;
                        }
                    }
                    if (validObj.message) {
                        if (validObj.length) {
                            if (userVal.trim().length > validObj.length) {
                                return validObj.message;
                            }
                        }
                        if (validObj.regular) {
                            if (!validObj.regular.test(userVal.trim())) {
                                if (validObj.notMatch) {
                                    return validObj.notMatch;
                                } else {
                                    return validObj.message;
                                }
                            }
                        }
                    }
                    validObj.match = true;
                }
            }
        },
        match: {}
    })
    window.Vue = app;
</script>
</body>
</html>