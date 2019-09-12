<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>小米首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <jsp:include page="/WEB-INF/headers.jsp"></jsp:include>
    <style>
        .title_color:hover{
            color:darkred;
        }
    </style>

</head>
<body>
    <div id="app">
        <div class="box">
            <div class="inner whiteGL">
                <div class="left">
                    <a class="mix" href="">仿小米商城-学习专用</a>
                </div>
                <div class="right">
                    <c:choose>

                        <c:when test="${authorization_session_state_key == true}">
                            <a>${authorization_session_key.name}</a>
                            <img src="${authorization_session_key.photo}" style="width: 30px;height: 30px;"/>
                        </c:when>
                        <c:otherwise>
                            <a class="mix" href="${pageContext.request.contextPath}/login.jsp">登录</a>
                            <a href="${pageContext.request.contextPath}/register.jsp">注册</a>
                        </c:otherwise>
                    </c:choose>
                    <a class="max"  href="">消息通知</a>
                    <a href="${pageContext.request.contextPath}/cart/toCart"><span src="img/cart.jpg" alt="" v-text="'购物车:' + count"></span></a>
                </div>
            </div>
        </div>

        <div class="logo">
            <div class="logo_left">
                <div>
                    <a href="javascript:void(0);" title="小米官网"><img class="xiaomi" src="img/logo.jpg"></a>
                </div>
            </div>
            <ul class="logo_center orangeGL">
                <a href="">小米手机</a>
                <a href="">红米</a>
                <a href="">笔记本</a>
                <a href="">电视</a>
                <a href="">盒子</a>
                <a href="">新品</a>
                <a href="">路由器</a>
                <a href="">智能硬件</a>
                <a href="">服务</a>
                <a href="">社区</a>
            </ul>
            <formv class="logo_right">
                <div class="logo_input"><input type="text">
                    <!--   <div class="logo_input_div">
                          <a class="logo_input_a" href="">小米5 新品</a>
                          <a class="logo_input_a" href="">小米Note 3</a>
                      </div> -->


                </div>
                <a class="logo_right_a"><img src="img/find.jpg"></a>
                <!--<a href="">红米5新品</a>-->
                <!--<a href="">小米Noto 3</a>-->
            </formv>
        </div>
        <div class="scroll">
            <ul>
                <li><a href=""><img src="img/scroll_02.jpg" alt=""></a></li>
                <li><a href=""><img src="img/scroll_03.jpg" alt=""></a></li>
                <li><a href=""><img src="img/scroll_04.jpg" alt=""></a></li>
                <li><a href=""><img src="img/scroll_05.jpg" alt=""></a></li>
                <li><a href=""><img src="img/scroll_06.jpg" alt=""></a></li>
            </ul>
            <div class="scroll_dot">
                <span class="scroll_dot_span"></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
            </div>
            <div class="scroll_arrows">
                <a href="javascript:void(0);"><span class="left scroll_arrows_back">〈</span></a>
                <a href="javascript:void(0);"><span class="right scroll_arrows_back">〉</span></a>
            </div>
            <div class="scroll_left">
                <ul class="scr-ul">
                    <c:forEach var="item" items="${categories}">
                        <li class="scr_li"><a href="${pageContext.request.contextPath}/category/findById?cid=${item.cid}">${item.cname}</a><i class="scr_i"></i></li>
                    </c:forEach>
                </ul>
            </div>
            <!--<div class="scroll_right"><img src="img/scroll_02.jpg"></div>-->
        </div>
        <div class="bot">
            <div class="bot_max">
                <div class="bot_first">
                    <div class="bot_one">
                        <div><a href=""><img src="img/bot_01.jpg">选购手机</a></div>
                        <div><a href=""><img src="img/bot_02.jpg">企业团购</a></div>
                        <div><a href=""><img src="img/bot_03.jpg">F码通道</a></div>
                        <div><a href=""><img src="img/bot_04.jpg">img米粉卡</a></div>
                        <div><a href=""><img src="img/bot_05.jpg">以旧换新</a></div>
                        <div><a href=""><img src="img/bot_06.jpg">话费充值</a></div>
                    </div>

                </div>
                <a href="#"><img src="img/3_02.jpg" alt=""></a>
                <a href="#"><img src="img/3_03.jpg" alt=""></a>
                <a href="#"><img src="img/3_04.jpg" alt=""></a>
            </div>
        </div>
        <div class="time">
            <div class="H">小米明星单品</div>
            <div class="time_in">
                <c:forEach var="product" items="${products4}">
                    <div style="background-color: #fff;width: 234px;height:320px;float: left;margin-left: 11px; ">
                        <a href="${pageContext.request.contextPath}/product/findByPid?pid=${product.pid.intValue()}" target="_blank">
                            <img class="time_min" style="width:234px;height: 234px;" src="${product.pic}" alt="">
                        </a>
                        <div style="clear: both;text-align: center"> ${product.pname}</div>

                        <div style="width: 234px;height: 85px;">
                            <div style="width: 234px;height: 20px;line-height: 20px;text-align: center;font-size: 10px;font-family: Arial" title="${product.description}">
                                    ${product.description.substring(0,10)}
                            </div>
                            <div style="width: 234px;height: 30px;line-height: 30px;text-align: center;color: #ff6700;font-size: 14px;font-family: Arial">
                                <span> &nbsp;<a class="title_color" style="color:darkslategrey;" href="${pageContext.request.contextPath}/product/findByPid?pid=${product.pid.intValue()}">${product.price}￥</a> </span>
                            </div>

                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
        <div class="appliances">
            <div class="capabackground">
                <div class="capacity">
                    <div class="capa_box_top">
                        <div class="capa_box_top_al">智能</div>
                        <div class="capa_box_top_ar">
                            <div class="capa_box_top_ar_min orangeGL"><a class="script_capa_box_top_ar" href="javascript:;">路由器</a></div>
                            <div class="capa_box_top_ar_min orangeGL"><a class="script_capa_box_top_ar" href="javascript:;">酷玩</a></div>
                            <div class="capa_box_top_ar_min orangeGL"><a class="script_capa_box_top_ar" href="javascript:;">健康</a></div>
                            <div class="capa_box_top_ar_min orangeGL"><a class="script_capa_box_top_ar" href="javascript:;">出行</a></div>
                            <div class="capa_box_top_ar_min orangeGL"><a class="script_capa_box_top_ar" href="javascript:;">热门</a></div>
                        </div>
                    </div>
                    <div class="capa_box scrip_capa_box scrip_capa_box_on">
                        <div class="capa_top">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_01.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_02.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div>
                                </a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家LED 智能台灯</li>
                                        <li style="color: #b0b0b0">无可视频闪,四种场景可变调光</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >169元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_03.jpg"><div class="tran">
                                    <span>现在看起来挺不错的，性价比超高！持续关注呀</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">九号平衡车 Plus</li>
                                        <li style="color: #b0b0b0">35km超长续航,一键自动跟随</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >3499元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_04.jpg"><div class="tran">
                                    <span>米兔智能故事机.</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米兔智能故事机</li>
                                        <li style="color: #b0b0b0">微信远程互动,智能语音交互</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                            <div class="capa capa_shadow capa_unright">
                                <a href=""><img src="img/capa_05.jpg" alt="">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家恒温电水壶</li>
                                        <li style="color: #b0b0b0">水温智能控制,304不锈钢内胆</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                        <div class="capa_bottom">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_06.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_07.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">iHealth体温计</li>
                                        <li style="color: #b0b0b0">一秒速测,LED屏清晰读数</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">129元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_08.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家行车记录仪</li>
                                        <li style="color: #b0b0b0">晚上能拍清车牌的行车记录仪</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">349元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_09.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家iHealth血压计</li>
                                        <li style="color: #b0b0b0">爸妈上手就会用的智能血压计</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">399元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_unright" >
                                <div class="capa_min ">
                                    <img class="capa_shadow" src="img/capa_10.jpg">
                                </div>
                                <div class="capa_min bottom capa_shadow">
                                    <div class="figure_a">
                                        <h3>浏览更多</h3>
                                        <li style="color:#757575; ">电影影视</li>

                                    </div>

                                    <div class="figure_b">
                                        <div class="figure_b_yuan">
                                            <li >→</li>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="capa_box scrip_capa_box ">
                        <div class="capa_top">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_05.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_04.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家LED 智能台灯</li>
                                        <li style="color: #b0b0b0">无可视频闪,四种场景可变调光</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >169元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_03.jpg"><div class="tran">
                                    <span>现在看起来挺不错的，性价比超高！持续关注呀</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">九号平衡车 Plus</li>
                                        <li style="color: #b0b0b0">35km超长续航,一键自动跟随</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >3499元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_02.jpg"><div class="tran">
                                    <span>米兔智能故事机.</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米兔智能故事机</li>
                                        <li style="color: #b0b0b0">微信远程互动,智能语音交互</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                            <div class="capa capa_shadow capa_unright">
                                <a href=""><img src="img/capa_01.jpg" alt="">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家恒温电水壶</li>
                                        <li style="color: #b0b0b0">水温智能控制,304不锈钢内胆</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                        <div class="capa_bottom">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_06.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_07.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">iHealth体温计</li>
                                        <li style="color: #b0b0b0">一秒速测,LED屏清晰读数</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">129元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_08.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家行车记录仪</li>
                                        <li style="color: #b0b0b0">晚上能拍清车牌的行车记录仪</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">349元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_09.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家iHealth血压计</li>
                                        <li style="color: #b0b0b0">爸妈上手就会用的智能血压计</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">399元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_unright" >
                                <div class="capa_min ">
                                    <img class="capa_shadow" src="img/capa_10.jpg">
                                </div>
                                <div class="capa_min bottom capa_shadow">
                                    <div class="figure_a">
                                        <h3>浏览更多</h3>
                                        <li style="color:#757575; ">电影影视</li>

                                    </div>

                                    <div class="figure_b">
                                        <div class="figure_b_yuan">
                                            <li >→</li>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="capa_box scrip_capa_box">
                        <div class="capa_top">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_01.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_02.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家LED 智能台灯</li>
                                        <li style="color: #b0b0b0">无可视频闪,四种场景可变调光</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >169元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_03.jpg"><div class="tran">
                                    <span>现在看起来挺不错的，性价比超高！持续关注呀</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">九号平衡车 Plus</li>
                                        <li style="color: #b0b0b0">35km超长续航,一键自动跟随</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >3499元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_04.jpg"><div class="tran">
                                    <span>米兔智能故事机.</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米兔智能故事机</li>
                                        <li style="color: #b0b0b0">微信远程互动,智能语音交互</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                            <div class="capa capa_shadow capa_unright">
                                <a href=""><img src="img/capa_05.jpg" alt="">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家恒温电水壶</li>
                                        <li style="color: #b0b0b0">水温智能控制,304不锈钢内胆</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                        <div class="capa_bottom">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_10.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_09.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">iHealth体温计</li>
                                        <li style="color: #b0b0b0">一秒速测,LED屏清晰读数</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">129元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_08.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家行车记录仪</li>
                                        <li style="color: #b0b0b0">晚上能拍清车牌的行车记录仪</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">349元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_07.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家iHealth血压计</li>
                                        <li style="color: #b0b0b0">爸妈上手就会用的智能血压计</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">399元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_unright" >
                                <div class="capa_min ">
                                    <img class="capa_shadow" src="img/capa_06.jpg">
                                </div>
                                <div class="capa_min bottom capa_shadow">
                                    <div class="figure_a">
                                        <h3>浏览更多</h3>
                                        <li style="color:#757575; ">电影影视</li>

                                    </div>

                                    <div class="figure_b">
                                        <div class="figure_b_yuan">
                                            <li >→</li>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="capa_box scrip_capa_box">
                        <div class="capa_top">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_03.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_03.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家LED 智能台灯</li>
                                        <li style="color: #b0b0b0">无可视频闪,四种场景可变调光</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >169元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_03.jpg"><div class="tran">
                                    <span>现在看起来挺不错的，性价比超高！持续关注呀</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">九号平衡车 Plus</li>
                                        <li style="color: #b0b0b0">35km超长续航,一键自动跟随</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >3499元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_04.jpg"><div class="tran">
                                    <span>米兔智能故事机.</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米兔智能故事机</li>
                                        <li style="color: #b0b0b0">微信远程互动,智能语音交互</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                            <div class="capa capa_shadow capa_unright">
                                <a href=""><img src="img/capa_05.jpg" alt="">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家恒温电水壶</li>
                                        <li style="color: #b0b0b0">水温智能控制,304不锈钢内胆</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                        <div class="capa_bottom">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_06.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_07.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">iHealth体温计</li>
                                        <li style="color: #b0b0b0">一秒速测,LED屏清晰读数</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">129元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_08.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家行车记录仪</li>
                                        <li style="color: #b0b0b0">晚上能拍清车牌的行车记录仪</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">349元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_09.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家iHealth血压计</li>
                                        <li style="color: #b0b0b0">爸妈上手就会用的智能血压计</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">399元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_unright" >
                                <div class="capa_min ">
                                    <img class="capa_shadow" src="img/capa_10.jpg">
                                </div>
                                <div class="capa_min bottom capa_shadow">
                                    <div class="figure_a">
                                        <h3>浏览更多</h3>
                                        <li style="color:#757575; ">电影影视</li>

                                    </div>

                                    <div class="figure_b">
                                        <div class="figure_b_yuan">
                                            <li >→</li>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                    <div class="capa_box scrip_capa_box">
                        <div class="capa_top">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_01.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_02.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家LED 智能台灯</li>
                                        <li style="color: #b0b0b0">无可视频闪,四种场景可变调光</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >169元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_03.jpg"><div class="tran">
                                    <span>现在看起来挺不错的，性价比超高！持续关注呀</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">九号平衡车 Plus</li>
                                        <li style="color: #b0b0b0">35km超长续航,一键自动跟随</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >3499元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_04.jpg"><div class="tran">
                                    <span>米兔智能故事机.</span>
                                </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米兔智能故事机</li>
                                        <li style="color: #b0b0b0">微信远程互动,智能语音交互</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                            <div class="capa capa_shadow capa_unright">
                                <a href=""><img src="img/capa_05.jpg" alt="">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家恒温电水壶</li>
                                        <li style="color: #b0b0b0">水温智能控制,304不锈钢内胆</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                        </div>
                        <div class="capa_bottom">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_06.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_07.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">iHealth体温计</li>
                                        <li style="color: #b0b0b0">一秒速测,LED屏清晰读数</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">129元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_08.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家行车记录仪</li>
                                        <li style="color: #b0b0b0">晚上能拍清车牌的行车记录仪</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">349元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_09.jpg">
                                    <div class="tran">
                                        <span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>
                                    </div></a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家iHealth血压计</li>
                                        <li style="color: #b0b0b0">爸妈上手就会用的智能血压计</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">399元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_unright" >
                                <div class="capa_min ">
                                    <img class="capa_shadow" src="img/capa_10.jpg">
                                </div>
                                <div class="capa_min bottom capa_shadow">
                                    <div class="figure_a">
                                        <h3>浏览更多</h3>
                                        <li style="color:#757575; ">电影影视</li>

                                    </div>

                                    <div class="figure_b">
                                        <div class="figure_b_yuan">
                                            <li >→</li>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>

                </div>
            </div>
            <div class="recommend">
                <div class="capacity">
                    <div class="capa_box_top">
                        <div class="capa_box_top_al">为你推荐</div>
                        <div class="capa_box_top_ar">
                            <!--<div class="capa_box_top_ar_min orangeGL"><a href="">路由器</a></div>-->
                            <!--<div class="capa_box_top_ar_min orangeGL"><a href="">酷玩</a></div>-->
                            <!--<div class="capa_box_top_ar_min orangeGL"><a href="">健康</a></div>-->
                            <!--<div class="capa_box_top_ar_min orangeGL"><a href="">出行</a></div>-->
                            <!--<div class="capa_box_top_ar_min orangeGL"><a href="">热门</a></div>-->
                        </div>
                    </div>
                    <div class="capa_box">
                        <div class="capa_top">
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_01.jpg"></a>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_02.jpg">
                                    <!--<div class="tran">-->
                                    <!--<span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>-->
                                    <!--</div>-->
                                </a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家LED 智能台灯</li>
                                        <li style="color: #b0b0b0">无可视频闪,四种场景可变调光</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >169元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_03.jpg">
                                    <!--<div class="tran">-->
                                    <!--<span>现在看起来挺不错的，性价比超高！持续关注呀</span>-->
                                    <!--</div>-->
                                </a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">九号平衡车 Plus</li>
                                        <li style="color: #b0b0b0">35km超长续航,一键自动跟随</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700" >3499元</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="capa capa_shadow">
                                <a href=""><img src="img/capa_04.jpg">
                                    <!--<div class="tran">-->
                                    <!--<span>米兔智能故事机.</span>-->
                                    <!--</div>-->
                                </a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米兔智能故事机</li>
                                        <li style="color: #b0b0b0">微信远程互动,智能语音交互</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                            <div class="capa capa_shadow capa_unright">
                                <a href=""><img src="img/capa_05.jpg" alt="">
                                    <!--<div class="tran">-->
                                    <!--<span>小米一如既往的好前天下单今天就到了，送货到家很细心的...</span>-->
                                    <!--</div>-->
                                </a>
                                <div class="capa_a">
                                    <ul>
                                        <li style="color: #333333">米家恒温电水壶</li>
                                        <li style="color: #b0b0b0">水温智能控制,304不锈钢内胆</li>
                                        <li style="font-size: 14px;margin-top: 10px; color: #ff6700">199元</li>
                                    </ul>
                                </div>

                            </div>
                        </div>


                    </div>

                </div>
            </div>
            <div class="popular">
                <div class="popular_background">
                    <div class="popular_box_top">
                        <div class="popular_box_top_al">热门产品</div>

                    </div>
                    <div class="popu_box">
                        <div class="popu popu_unleft popu_shadow">
                            <a href=""><img src="img/popu_01.jpg"></a>
                            <div class="popu_bottom">
                                <p class="review">
                                    外形简洁大方，大爱小米！全家人都在用小米的产品，真心不错，最主要的是性价比高。附图，给客服妹子一个大...
                                </p>
                                <p class="author"> 来自于 秘密 的评价 </p>
                                <div class="info">
                                    <h3 class="title">小米随身蓝牙音箱</h3>
                                    <span class="sep">|</span>
                                    <p class="price"><span>69</span>元</p>
                                </div>
                            </div>
                        </div>
                        <div class="popu popu_shadow">
                            <a href=""><img src="img/popu_02.jpg"></a>
                            <div class="popu_bottom">
                                <p class="review">
                                    这箱子很好，外观漂亮，实用性强。很轻，有点软但不影响它的坚固。
                                </p>
                                <p class="author"> 来自于 秘密 的评价 </p>
                                <div class="info">
                                    <h3 class="title">90分旅行箱 20寸</h3>
                                    <span class="sep">|</span>
                                    <p class="price"><span>229</span>元</p>
                                </div>
                            </div>
                        </div>
                        <div class="popu popu_shadow">
                            <a href=""><img src="img/popu_03.jpg"></a>
                            <div class="popu_bottom">
                                <p class="review">
                                    安装简洁方便，信号不错！！！！！
                                </p>
                                <p class="author"> 来自于 秘密 的评价 </p>
                                <div class="info">
                                    <h3 class="title">小米路由器3</h3>
                                    <span class="sep">|</span>
                                    <p class="price"><span>149</span>元</p>
                                </div>
                            </div>
                        </div>
                        <div class="popu popu_shadow">
                            <a href=""><img src="img/popu_04.jpg"></a>
                            <div class="popu_bottom">
                                <p class="review">
                                    包装很让人感动，式样也很可爱，做出的饭全家人都爱吃，超爱五星！手机控制还是不太熟练，最主要是没有连接...
                                </p>
                                <p class="author"> 来自于 秘密 的评价 </p>
                                <div class="info">
                                    <h3 class="title">米家压力IH电饭煲</h3>
                                    <span class="sep">|</span>
                                    <p class="price"><span>999</span>元</p>
                                </div>
                            </div>
                        </div>
                    </div>


                </div>
            </div>
            <div class="content">
                <div class="popular_background">
                    <div class="popular_box_top">
                        <div class="popular_box_top_al">内容</div>

                    </div>
                    <div class="popu_box">
                        <div class="popu cont_border_top1 popu_unleft popu_shadow">
                            <div class="popu_bottom">
                                <h2 class="con_title">图书</h2>
                                <h4 class="popu_name">哈利·波特与被诅咒的孩子</h4>
                                <p class="popu_desc"><a href="">“哈利·波特”第八个故事中文版震撼来袭！特别彩排版剧本！ </a></p>
                                <p class="popu_price"><a href="">29.37元</a></p>
                            </div>
                            <a href=""><img src="img/cont_01.jpg"></a>

                        </div>
                        <div class="popu cont_border_top2 popu_shadow ">

                            <div class="popu_bottom">
                                <h2 class="con_title">MIUI 主题</h2>
                                <h4 class="popu_name">包宝宝养成记</h4>
                                <p class="popu_desc"><a href="">包宝宝定制主题Q萌上线！快来领取萌萌包宝宝吧！</a></p>
                                <p class="popu_price"><a href="">4.2米币</a></p>
                            </div>
                            <a href=""><img src="img/cont_02.jpg"></a>
                        </div>
                        <div class="popu cont_border_top3 popu_shadow ">

                            <div class="popu_bottom">
                                <h2 class="con_title">游戏</h2>
                                <h4 class="popu_name">小米超神</h4>
                                <p class="popu_desc"><a href="">实力派！一起团！</a></p>
                                <p class="popu_price"><a href=""></a></p>
                            </div>
                            <a href=""><img src="img/cont_03.jpg"></a>
                        </div>
                        <div class="popu popu_border_top4 popu_shadow ">

                            <div class="popu_bottom">
                                <h2 class="con_title">应用</h2>
                                <h4 class="popu_name">2017金米奖</h4>
                                <p class="popu_desc"><a href="">最优秀的应用和游戏</a></p>
                                <p class="popu_price"><a href=""></a></p>
                            </div>
                            <a href=""><img src="img/cont_04.jpg"></a>
                        </div>
                    </div>


                </div>
            </div>
            <div class="video">
                <div class="popular_background">
                    <div class="popular_box_top">
                        <div class="popular_box_top_al">视频</div>

                    </div>
                    <div class="popu_box">
                        <div class="vid popu_unleft popu_shadow">
                            <div class="video_top">
                                <a href=""><video class="this_vid" src="img/snowPerson.mp4" controls loop  poster="img/video_01.jpg"></video></a>
                            </div>
                            <div class="video_bottom">
                                <h3 class="vid_title"><a href="">小米8，一部与众不同的手机</a></h3>
                                <p class="vid_desc">透明探索版，将科技与美学完美结合</p>
                            </div>
                        </div>
                        <div class="vid  popu_shadow">
                            <div class="video_top">
                                <a href=""><video class="this_vid" src="img/snowPerson.mp4" controls loop  poster="img/video_02.jpg"></video></a>
                            </div>
                            <div class="video_bottom">
                                <h3 class="vid_title"><a href="">小米MIX 2S，一面科技 一面艺术</a></h3>
                                <p class="vid_desc">艺术品般陶瓷机身，惊艳、璀璨</p>
                            </div>
                        </div>
                        <div class="vid  popu_shadow">
                            <div class="video_top">
                                <a href=""><video class="this_vid" src="img/snowPerson.mp4" controls loop  poster="img/video_03.jpg"></video></a>
                            </div>
                            <div class="video_bottom">
                                <h3 class="vid_title"><a href="">天生丽质的小米6X</a></h3>
                                <p class="vid_desc">让你一见倾心</p>
                            </div>
                        </div>
                        <div class="vid  popu_shadow">
                            <div class="video_top">
                                <a href=""><video class="this_vid" src="img/snowPerson.mp4" controls loop  poster="img/video_01.jpg"></video></a>
                            </div>
                            <div class="video_bottom">
                                <h3 class="vid_title"><a href="">生活中无所不在的小爱同学</a></h3>
                                <p class="vid_desc">透明探索版，将科技与美学完美结合</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="foot">
                <ul class="foot_ul">
                    <li class="foot_li">预约维修服务</li>
                    <li class="foot_li">7天无理由退货</li>
                    <li class="foot_li">15天免费换货</li>
                    <li class="foot_li">满15元包邮</li>
                    <li class="foot_li foot_fot">520余家售后网点</li>
                </ul>
                <div class="foot_center">
                    <div class="foot_cen_left">
                        <dl class="foot_dl">
                            <dt class="foot_dt">帮助中心</dt>
                            <dd class="foot_dd"><a>账户管理</a></dd>
                            <dd class="foot_dd"><a>购物指南</a></dd>
                            <dd class="foot_dd"><a>订单操作</a></dd>
                        </dl>
                        <dl class="foot_dl">
                            <dt class="foot_dt">帮助中心</dt>
                            <dd class="foot_dd"><a>账户管理</a></dd>
                            <dd class="foot_dd"><a>购物指南</a></dd>
                            <dd class="foot_dd"><a>订单操作</a></dd>
                        </dl>
                        <dl class="foot_dl">
                            <dt class="foot_dt">帮助中心</dt>
                            <dd class="foot_dd"><a>账户管理</a></dd>
                            <dd class="foot_dd"><a>购物指南</a></dd>
                            <dd class="foot_dd"><a>订单操作</a></dd>
                        </dl>
                        <dl class="foot_dl">
                            <dt class="foot_dt">帮助中心</dt>
                            <dd class="foot_dd"><a>账户管理</a></dd>
                            <dd class="foot_dd"><a>购物指南</a></dd>
                            <dd class="foot_dd"><a>订单操作</a></dd>
                        </dl>
                        <dl class="foot_dl">
                            <dt class="foot_dt">帮助中心</dt>
                            <dd class="foot_dd"><a>账户管理</a></dd>
                            <dd class="foot_dd"><a>购物指南</a></dd>
                            <dd class="foot_dd"><a>订单操作</a></dd>
                        </dl>
                        <dl class="foot_dl">
                            <dt class="foot_dt">帮助中心</dt>
                            <dd class="foot_dd"><a>账户管理</a></dd>
                            <dd class="foot_dd"><a>购物指南</a></dd>
                            <dd class="foot_dd"><a>订单操作</a></dd>
                        </dl>
                    </div>
                    <div class="foot_cen_right">
                        <p class="foot-phone">400-100-5678</p>
                        <p class="foot-right-center">周一至周日 8:00-18:00<br>（仅收市话费)</p>
                        <a class="foot-right-bottmo" href="">联系客服</a>
                    </div>
                </div>
            </div>
            <div class="fot_bot_max">
                <div class="fot_bot">
                    <img src="img/logo.jpg" class="fot_bot_logo" >
                    <div class="fot_bot_text">
                        <p class="fot_bot_p">
                            <a class="fot_bot_a" href="">小米商城</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">MIUI</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">米家</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">米聊</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">多看</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">游戏</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">路由器</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">米粉卡</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">政企服务</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">小米天猫店</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">隐私政策</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">问题反馈</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">廉政举报</a>
                            <span class="fot_bot_span">|</span>
                            <a class="fot_bot_a" href="">Select Region</a>
                        </p>
                        <p class="fot_bot_p fot_bot_a1">
                            "©"
                            <a class="fot_bot_a1" href="">mi.con</a>
                            " 京ICP证110507号"
                            <a class="fot_bot_a1" href="">京ICP备10046444号</a>
                            <a class="fot_bot_a1" href="">京公网安备11010802020134号</a>
                            <a class="fot_bot_a1" href="">京网文[2017]1530-131号</a>
                            "<br>违法和不良信息举报电话：185-0130-1238，本网站所列数据，除特殊说明，所有数据均出自我司实验室测试"



                        </p>
                    </div>
                    <div class="fot_bot_right">
                        <a href="" class="fot_bot_right_img"><img src="img/foot_01.png" alt=""></a>
                        <a href="" class="fot_bot_right_img"><img src="img/foot_02.png" alt=""></a>
                        <a href="" class="fot_bot_right_img"><img src="img/foot_03.png" alt=""></a>
                        <a href="" class="fot_bot_right_img"><img src="img/foot_04.png" alt=""></a>
                        <a href="" class="fot_bot_right_img"><img src="img/foot_05.png" alt=""></a>
                    </div>

                </div>
                <img class="fot_bottom_img" src="img/foot_06.png">
            </div>


            <script>
                var abc = document.getElementsByClassName("script_capa_box_top_ar");
                console.log("abc:"+abc);
                console.log("abc[0]"+abc[0]);
                var box = document.getElementsByClassName("scrip_capa_box");
                console.log("box:"+box);
                console.log("box[0]:"+box[0]);
                for(var i = 0; i<abc.length;i++){
                    abc[i].index=i;
                    abc[i].onmouseover=function(){
                        for(var j = 0 ;j<abc.length;j++){
                            box[j].className="capa_box scrip_capa_box";
                        }
                        box[this.index].className="capa_box scrip_capa_box scrip_capa_box_on"
                        console.log("this.index:"+this.index);
                    }
                }
                var n = 0;

                /*setInterval(function () {
                    n++;
                     if(n > $(".scroll ul li").length){
                     n=0;
                     }
                    $(".scroll ul li").css("opacity","0").eq(n).css("opacity","1")
                },100)*/
                var t = setInterval(fun,1000);
                function  fun() {
                    n++;
                    if(n > $(".scroll>ul>li").length-1){
                        n = 0;
                    }
                    $(".scroll>ul>li").css("opacity","0").eq(n).css("opacity","1")
                    $(".scroll_dot span").eq(n).addClass("scroll_dot_span").siblings().removeClass("scroll_dot_span");
                }
                $(".scroll_arrows .left").click(function() {
                    n -= 2;
                    if(n <-1){
                        n=4;
                    }
                    fun()

                } );
                $(".scroll_arrows .right").click(function() {
                    fun()
                });
                $(".scroll_dot span").click(function () {
                    console.log($(this).index());
                    n=$(this).index()-1;
                    $(this).siblings().removeClass("scroll_dot_span").end().addClass("scroll_dot_span");
                    fun()
                }); $(".scroll").hover(function () {
                        clearInterval(t);
                    },
                    function () {
                        t = setInterval(fun,1000);
                    });

            </script>
            <script type="text/javascript">
                let vue = new Vue({
                    el:"#app",
                    data:function(){
                        return {
                            count:0
                        }
                    },
                    mounted(){
                        let v = this;
                        if(${authorization_session_state_key == null ? false : authorization_session_state_key}){
                            $.ajax({
                                url:"${pageContext.request.contextPath}/cart/count",
                                type:"POST",
                                dataType:"json",
                                success:function(res){
                                    v.count = res.list.length;
                                },
                                error:function(err){
                                    console.log(err)
                                }
                            })
                        }
                    }
                })
            </script>
    </div>
</body>
</html>