<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <jsp:include page="/WEB-INF/headers.jsp"></jsp:include>
<script type="text/javascript">
	
	
</script>
</head>
<body>
    <div id="app">
        <div class="order_head">
            <div class="head_background">
                <div class="head_box">
                    <a href="${pageContext.request.contextPath}/index.jsp" class="head_left_a"><img src="${pageContext.request.contextPath}/img/logo.jpg" alt="" class="head_left_p"></a>
                    <h1 class="head_h1">我的购物车</h1>
                    <div class="head_right">
                        <span class="head_right_in btn btn-warning" @click="clearAllCart" v-text="'清空购物车:' + count"> </span>
                    </div>

                </div>
            </div>
        </div>
        <div class="trolley_background">
            <div class="trolley_background_in">
                <div class="tro_tab_h">
                    <div class="col tro_tab_check">
                        <h1 class="tro_tab_check_p">√</h1>
                        <span class="tro_tab_check_sp" style="cursor: pointer;" @click="allSelect">全选</span>

                    </div>
                    <div class="col tro_tab_img">
                    </div>
                    <p class="col tro_tab_name">商品名称</p>
                    <div class="col tro_tab_price">单价</div>
                    <div class="col tro_tab_num">数量</div>
                    <div class="col tro_tab_total">小计</div>
                    <div class="col tro_tab_action">操作</div>
                </div>
                <div class="tro_tab_h1" v-for="item in list">
                    <div class="col tro_tab_check">
                        <h1 class="tro_tab_check_p" style="background-color: #fff">
                            <input type="checkbox" v-model="item.check" name="ids">
                        </h1>
                        <span class="tro_tab_check_sp"></span>

                    </div>
                    <div class="col tro_tab_img">

                    </div>
                    <div class="col tro_tab_name">
                        <!--<h2 tro_tab_name_h2>小米电视4A 32英寸 黑色 32英寸</h2>-->
                        <li class="tro_tab_name_li1" style="font-size: 16px;" v-text="item.product.pname"></li>
                    </div>
                    <div class="col tro_tab_price">
                        <span  id="price"></span><span v-text="item.product.price">元</span>
                    </div>
                    <div class="col" style="margin-left: 65px;margin-top: 30px;">
                        <input v-model="item.count" @change="changeNum(item)" style="width: 60px" type="number" id="num">
                    </div>
                    <div class="col tro_tab_total ">
                        <span class="tro_tab_total_value" id="prices" v-text="maxTotal(item)"></span>元
                    </div>
                    <div class="col tro_tab_action" style="cursor: pointer;width: 40px;height: 40px;" @click="delCart(item.product.pid)">删除</div>
                </div>


            </div>

            <div class="tro_close_bot ">
                <!--<p class="tro_bot_ppp">+</p>-->

                <p class="tro_close_p "> <a href="${pageContext.request.contextPath}/index.jsp">继续购物 </a></p>
                <p class="tro_close_p_c">合计:<span id="close" v-text="getMaxTotal()"></span>元</p>

                <p class="tro_close_p_r" style="cursor: pointer;">去结算</p>
            </div>

        </div>
    </div>
<script type="text/javascript">
    let vue = new Vue({
        el:"#app",
        data:function(){
            return {
                count:0,
                list:[],
                total:0,
                checked:false
            }
        },
        methods:{
            allSelect(){
                this.checked = !this.checked;
                let c = this.checked;
              this.list.forEach(item =>{
                  item.check = c;
              })
            },
            getMaxTotal(){
               let total = 0;
               this.list.forEach(item =>{
                   total += item.total;
               })
                this.total = total;
               return this.total;
            },
            delCart(pid){
                let v = this;
                $.ajax({
                    url:"${pageContext.request.contextPath}/cart/delete?pid=" + pid,
                    type:"DELETE",
                    success:function(res){
                        toastr.success("商品删除成功");
                        v.getAllCartCount();
                    },
                    error:function(err){
                        toastr.error("商品删除失败");
                    }
                })
            },
            changeNum(item){
                if(item.count < 1){
                    item.count = 1;
                }
                $.ajax({
                    url:"${pageContext.request.contextPath}/cart/update",
                    type:"post",
                    data:{
                        count: item.count,
                        pid: item.product.pid
                    },
                    success:function(res){

                    },
                    error:function(err){

                    }
                })
            },
            maxTotal(item){
                item.total = item.count * item.product.price;
                return item.total;
            },
            clearAllCart(){
                let v = this;
                $.ajax({
                    url:"${pageContext.request.contextPath}/cart/clearAll",
                    type:"post",
                    success:function(res){
                        toastr.success("清空购物车成功");
                        v.getAllCartCount();
                        v.list = []
                    },
                    error:function(err){
                        toastr.error("清空购物车失败")
                    }
                })
            },
            getAllCartCount(){
                let v = this;
                if(${authorization_session_state_key == null ? false : authorization_session_state_key}){
                    $.ajax({
                        url:"${pageContext.request.contextPath}/cart/count",
                        type:"POST",
                        dataType:"json",
                        success:function(res){
                            try{
                                v.count = res.list.length;
                                let list = [];
                                res.list.forEach(item =>{
                                    let cart = {... item};
                                    cart.check = false;
                                    list.push(cart);
                                });
                                v.list = list;
                            }catch (e) {
                                v.count = 0;
                                v.list = [];
                            }
                        },
                        error:function(err){
                            console.log(err)
                        }
                    })
                }
            }
        },
        mounted(){
           this.getAllCartCount();
        }
    })
</script>
</body>
</html>