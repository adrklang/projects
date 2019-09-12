<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>红米5 plus</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <jsp:include page="${pageContext.request.contextPath}/WEB-INF/headers.jsp"></jsp:include>
</head>
<body>
   <div id="app">

       <div class="box">
           <div class="inner whiteGL">
               <div class="left">
                   <a class="mix" href="">仿小米商城-学习专用</a>
               </div>
               <div class="right">
                   <c:if test="${authorization_session_state_key == false}">
                       <a class="mix" href="../login.jsp">登录</a>
                       <a href="../register.jsp">注册</a>
                   </c:if>
                   <c:if test="${authorization_session_state_key == true}">
                       <a class="max"  href=""> 欢迎您：${authorization_session_key.name}</a>
                   </c:if>
                   <a class="max"  href="">消息通知</a>
                   <a href="${pageContext.request.contextPath}/cart/toCart">进入购物车！！</a>
               </div>
           </div>
       </div>

       <c:if test="${empty authorization_session_state_key}">
           <div class="plus5_no2 ">
               <div class="plus5_no2_in">
                   <a class="plus5_no2_in_a"> 为方便您购买，请提前登录 </a>
                   <a class="plus5_no2_in_a orange"style="color: pink;" href="../login.jsp">立即登录</a>
                   <a id="plus5_no2_in" class="plus5_no2_in_a" href="javascript:;">x</a>
               </div>
           </div>
       </c:if>
       <div class="plus5_no3">
           <div class="plus5_no3_img">
               <img width="560px" height="560px" :src="product.pic" style="display: block;border: 1px solid gray;-webkit-border-radius: 1px;-moz-border-radius: 1px;border-radius: 1px;" :alt="product.description">
           </div>
           <div class="plus5_no3_right">
               <h3>商品名称：</h3>
               <p class="plus5_no3_right_p2">
                   <span class="plus5_no3_right_span2" v-text="'「' + product.pname + '」'"></span>
               </p>
               <p class="plus5_no3_right_p3" v-text="'价格：' + product.price + '元'"></p>

               <h3 v-text="'商品编号：' + product.pid"></h3>
               <div class="plus5_no3_right_div4" style="height: 200px">
                   <div style="width: 50px;margin-left: 28%;">
                       <button class="form-control btn btn-primary" @click="checkProduct(true)">+</button>
                   </div>
                    <div style="width: 500px" class="form-inline">
                        <h3>购买数量：<input class="form-control" id="count" name="count" :value="count" size="10"></h3>
                    </div>
                   <div style="width: 50px;margin-left: 28%;">
                       <button class="form-control btn btn-primary" @click="checkProduct(false)">-</button>
                   </div>
               </div>
               <a href="javascript:;" @click="addCart"><div class="plus5_no3_right_div11" style="cursor: pointer;" >加入购物车</div></a>
               <div class="plus5_no3_right_div12">
                   <div class="plus5_no3_right_div12_y">√</div>
                   <div class="plus5_no3_right_div12_z">七天无理由退货</div>
                   <div class="plus5_no3_right_div12_y">√</div>
                   <div class="plus5_no3_right_div12_z">15天质量问题换货</div>
                   <div class="plus5_no3_right_div12_y">√</div>
                   <div class="plus5_no3_right_div12_z">360天保障</div>
               </div>
               <a href="javascript:void (0);"></a>

           </div>
       </div>

   </div>
  <%--   <jsp:include page="footer.jsp"></jsp:include> --%>
    
   <%-- <script>
        var plus5_no2_in = document.getElementById("plus5_no2_in");
        var plus5_no2 = document.getElementsByClassName("plus5_no2")[0];
        plus5_no2.onclick = function () {
            plus5_no2.className="plus5_no2 plus5_no2_close";
//            plus5_no2.style.display="none";
        }
        $(".plus5_no3_right_div9 .plus5_no3_right_div7_in").click(function () {
            console.log($(this).children().eq(1).html());
            $(".plus5_no3_right_div10_span2").html($(this).children().eq(1).html());

            $(".plus5_no3_img img").css("display","none").eq($(this).index()).css("display", "block");
            $(this).siblings().removeClass("plus5_no3_right_div7_in_hover").end().addClass("plus5_no3_right_div7_in_hover");

        })
        $(".plus5_no3_right_div7 .plus5_no3_right_div7_in").click(function () {
//            var value = $(".plus5_no3_right_div7_span0 ").html();
            console.log($(this).children().eq(1).html());
            $(".plus5_no3_right_div10_span0").html($(this).children().html());
            $(".plus5_no3_right_span10").html($(this).children().eq(1).html());
            var value = "总计:"+$(this).children().eq(1).html()
            $(".plus5_no3_right_div10_p2").html(value);

            $(this).siblings().removeClass("plus5_no3_right_div7_in_hover").end().addClass("plus5_no3_right_div7_in_hover");
        })
    </script>--%>
    <script type="text/javascript">
        let vue = new Vue({
            el:"#app",
            data:function(){
                return {
                    product:{
                        pid:`${product.pid}`,
                        pname:`${product.pname}`,
                        pic:`${product.pic}`,
                        color:`${product.color}`,
                        price:`${product.price}`,
                        description:`${product.description}`,
                        state:`${product.state}`,
                        version:`${product.version}`,
                        productDate:`${product.productDate}`,
                        cid:`${product.cid}`
                    },
                    count: 1
                }
            },
            methods:{
                addCart(){
                    let v = this;
                    if(${authorization_session_state_key == null ? false : authorization_session_state_key}){
                        $.ajax({
                            url:"${pageContext.request.contextPath}/cart/add",
                            type:"POST",
                            data:{
                                count: v.count,
                                pid: v.product.pid
                            },
                            dataType:"json",
                            success:function(res){
                                toastr.success("商品添加成功，即将跳转购物车页面");
                                setTimeout(() =>{
                                    window.location.href="${pageContext.request.contextPath}/cart/toCart"
                                },500);
                            },
                            error:function(err){
                                toastr.error("商品添加失败，购物车已存在该商品")
                            }
                        })
                    }else{
                        toastr.error("你没有登录，请先登录在操作");
                    }
                },
                checkProduct(state){
                    if(state){
                        this.count += 1;
                    }else{
                        this.count -= 1;
                        if(this.count < 1){
                            this.count = 1;
                        }
                    }
                }
            }
        })
    </script>
</body>
</html>