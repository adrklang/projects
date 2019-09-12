<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>商品信息</title>
    <link href="${pageContext.request.contextPath}/admin/css/style.css" rel="stylesheet" type="text/css"/>
    <script language="javascript" type="text/javascript"
            src="${pageContext.request.contextPath}/admin/js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
    <jsp:include page="/WEB-INF/headers.jsp"></jsp:include>
    <script type="text/javascript">

        // old write
        $(document).ready(function () {
            $(".click").click(function () {
                $(".tip").fadeIn(200);
            });

            $(".tiptop a").click(function () {
                $(".tip").fadeOut(200);
            });

            $(".sure").click(function () {
                $(".tip").fadeOut(100);
            });

            $(".cancel").click(function () {
                $(".tip").fadeOut(100);
            });
        });

    </script>
</head>
<body>
<div id="app">

    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">商品管理</a></li>
        </ul>
    </div>

    <div class="rightinfo">

        <div class="tools">

            <ul class="toolbar">

                <!--

                <li class="click"><span><img src="images/t02.png" /></span>修改</li>
                <li><span><img src="images/t04.png" /></span>统计</li>
                 -->
                <li style="cursor: pointer;" @click="currentProduct={state:0,pic:''};updateState='add'"
                    data-toggle="modal" data-target="#myModal" id="addProduct"><span><img
                        src="${pageContext.request.contextPath}/admin/images/t01.png"/></span>添加商品
                </li>
                <li style="cursor: pointer;" @click="deletes"><span><img src="${pageContext.request.contextPath}/admin/images/t03.png"/></span>批量删除
                </li>
            </ul>
            <div id="f5">
                <div style="width: 100%;height: 30px; text-align: center;">
                    商品名称：<input name="pname" v-model="search.pname" style="height: 25px;border:1px solid #ccc;" type="text"/> &nbsp;&nbsp;
                    <!-- 0 正常,1热门产品，2为你推荐，3，新品 4，小米明星单品 -->

                    是否热推：<select v-model="search.state" name="state" style="height: 28px;border:1px solid #ccc;">
                    <option  value="0">=== 请选择 ===</option>
                    <option  value="1">热门产品</option>
                    <option  value="2">为你推荐</option>
                    <option  value="3">新品</option>
                    <option  value="4">小米明星单品</option>
                </select> &nbsp;&nbsp;
                    时间：<input class="Wdate" name="start_time" v-model="search.startTime" id="startTime" type="text" style="height: 25px;border:1px solid #ccc;" onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/> ~
                    <input class="Wdate" v-model="search.endTime" id="endTime" name="end_time" type="text" style="height: 25px;border:1px solid #ccc;" onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})" />&nbsp;&nbsp;
                    <input type="button" class="btn btn-primary" value="查询" @click="processSearch" style="width: 60px;height: 30px;"  />
                </div><br/>
            </div>
        </div>

        <table class="tablelist">
            <thead>
            <tr>
                <th><input name="" type="checkbox" @click="allSelect" value=""/></th>
                <th>序号<i class="sort"><img src="${pageContext.request.contextPath}/admin/images/px.gif"/></i></th>
                <th>商品类别</th>
                <th>商品名称</th>
                <th>商品颜色</th>
                <th>商品价格</th>
                <th width="10%">简介</th>
                <th>商品展示图</th>
                <th>是否热推</th>
                <th>型号</th>
                <th>生产日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="product in products">
                <td><input name="" type="checkbox" v-model="product.check" value=""/></td>
                <td v-text="product.pid">22</td>
                <td v-text="product.cname">手机</td>
                <td v-text="product.pname" width="160px">小米10</td>
                <td v-text="product.color">红色</td>
                <td v-text="product.price">2999</td>
                <td width="10%" v-text="product.description.substr(0,10)" :title="product.description"></td>
                <td>
                    <img :src="product.pic" alt="" width="100" height="80" style="border: 1px solid gray;-webkit-border-radius: 2px;-moz-border-radius: 2px;border-radius: 2px;"/>
                </td>
                <td v-text="product.state == 0 ? '正常' : product.state == 1 ? '热门商品' : product.state == 2 ? '为你推荐' : product.state == 3 ? '新品' : '小米明星单品'"></td>
                <td v-text="product.version">1</td>
                <td v-text="product.productDate">2019</td>
                <td>
                    <a href="javascript:;" @click="deleteById(product.pid)">删除</a>
                    <a href="javascript:;" @click="currentProduct=product;updateState='edit'" data-toggle="modal"
                       data-target="#myModal">修改</a>
                </td>
            </tr>
            </tbody>
        </table>


        <div class="pagin">
            <div class="message">共<i class="blue" v-text="total"></i>条记录，当前显示第&nbsp;<i class="blue"
                                                                                       v-text="pageInfo.page">&nbsp;</i>页，共&nbsp;<i
                    class="blue" v-text="maxPage">&nbsp;</i>页
            </div>
            <ul class="paginList">

                <li class="paginItem"><a href="javascript:;" @click="getAllProductPages(1)">首页</a></li>
                <li class="paginItem"><a href="javascript:;" @click="getAllProductPages(pageInfo.page - 1)">上一页</a></li>
                <li class="paginItem" v-for="i in Math.min(5,maxPage)"><a
                        :style="index(i) == pageInfo.page ? 'color:red':''" @click="getAllProductPages(index(i))"
                        href="javascript:;" v-text="index(i)"></a></li>
                <li class="paginItem"><a href="javascript:;" @click="getAllProductPages(pageInfo.page + 1)">下一页</a></li>
                <li class="paginItem"><a href="javascript:;" @click="getAllProductPages(maxPage)">尾页</a></li>
                <li class="paginItem"><a><input type="text" @keyup.enter="getAllProductPages(enterPage)"
                                                style="width: 30px;height: 25px;" v-model="enterPage"/></a></li>
            </ul>
        </div>

        <!-- 模态框（Modal） -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel">
                            修改用户信息
                        </h4>
                    </div>
                    <div class="modal-body">
                        <form class="form ">
                            <div class="form-inline" style="margin-left: 15%;">
                                <label class="col-lg-8 form-control" for="name">商品名称:</label>
                                <input class="form-control" style="font-size: 16px;width: 60%"
                                       v-model="currentProduct.pname" id="name"/>
                            </div>
                            <br/>
                            <div class="form-inline" style="margin-left: 15%;">
                                <span class="form-control col-lg-8" for="color">商品颜色:</span>
                                <input class="form-control" style="font-size: 16px;width: 60%"
                                       v-model="currentProduct.color" type="text" id="color"/>
                            </div>
                            <div class="form-inline" style="margin-left: 15%;margin-top: 10px;">
                                <label class="col-lg-8 form-control" for="price">商品价格:</label>
                                <input class="form-control" style="font-size: 16px;width: 60%" type="number"
                                       v-model="currentProduct.price" id="price"/>
                            </div>
                            <div class="form-inline" style="margin-left: 15%;margin-top: 10px;">
                                <label class="col-lg-8 form-control" for="version">商品品牌:</label>
                                <input class="form-control" style="font-size: 16px;width: 60%" type="text"
                                       v-model="currentProduct.version" id="version"/>
                            </div>
                            <div class="form-inline" style="margin-left: 15%;margin-top: 10px;">
                                <label class="col-lg-8 form-control" for="cid">商品类别:</label>
                                <select v-model="currentProduct.cid" class="form-control" id="cid">
                                    <option :value="category.cid" :selected="category.cid == currentProduct.cid"
                                            v-for="category in categories" v-text="category.cname"></option>
                                </select>
                            </div>
                            <div class="form-inline" style="margin-left: 15%;margin-top: 10px;">
                                <label class="col-lg-8 form-control" for="state">商品状态:</label>
                                <select v-model="currentProduct.state" class="form-control" id="state">
                                    <option value="0">正常</option>
                                    <option value="1">热门商品</option>
                                    <option value="2">为你推荐</option>
                                    <option value="3">新品</option>
                                    <option value="4">小米明显单品</option>
                                </select>
                            </div>
                            <div class="form-inline" style="margin-left: 15%;margin-top: 10px;position: relative">
                                <div class="form-inline">
                                    <label class="form-control col-lg-8" for="photo">商品图片</label>
                                    <span @click="upload" class="btn btn-primary">上传图片</span>
                                </div>
                                <img :src="currentProduct.pic" v-show="currentProduct.pic" style="width: 100px;height: 45px;margin-top: 10px;border: 1px solid black;" id="photo"/><br/>
                                <input @change="uploadFile" id="file" type="file" style="display: none"/>
                            </div>
                            <div class="form-inline" style="margin-left: 15%;margin-top: 10px;">
                                <label class="col-lg-8 form-control" for="description">商品说明:</label>
                                <textarea class="form-control" rows="7" style="font-size: 12px;width: 60%"
                                          v-model="currentProduct.description" id="description"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            关闭
                        </button>
                        <button type="button" @click="edit" v-show="updateState=='edit'" class="btn btn-primary">
                            提交更改
                        </button>
                        <button type="button" @click="add" v-show="updateState=='add'" class="btn btn-primary">
                            添加
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
        </div>


    </div>

</div>
<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>
<script type="text/javascript">
    let vue = new Vue({
        el: "#app",
        data: function () {
            return {
                pageInfo: {
                    page: 1,
                    size: 5
                },
                total: 0,
                maxPage: 0,
                checked: false,
                enterPage: 0,
                products: [],
                currentProduct: {},
                updateState: "add",
                categories: [],
                search:{
                    pname:"",
                    state:"",
                    startTime:"",
                    endTime:""
                }
            }
        },
        methods: {
            processSearch(){
                this.search.startTime = $("#startTime").val();
                this.search.endTime = $("#endTime").val();
                this.getAllProductPages(this.pageInfo.page);
            },
            deletes() {
                let ids = "";
                this.products.forEach(item => {
                    if(item.check){
                        ids += item.pid + ",";
                    }
                });
                ids = ids.substr(0, ids.length - 1);
                $.ajax({
                    url: "${pageContext.request.contextPath}/product/deletes?pids=" + ids,
                    type: "DELETE",
                    dataType: "JSON",
                    success: function (res) {
                        toastr.success("删除商品成功");
                        setTimeout(() => {
                            window.location.reload();
                            },500);
                        },
                    error: function (err) {
                        toastr.error("删除商品失败");
                    }
                })
            },
            deleteById(id) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/product/delete?id=" + id,
                    type: "DELETE",
                    dataType: "JSON",
                    success: function (res) {
                        toastr.success("删除商品成功");
                        setTimeout(() => {
                            window.location.reload();
                            },500);
                        },
                    error: function (err) {
                        toastr.error("删除商品失败");
                    }
                })
            },
            edit() {
                let v = this;
                $.ajax({
                    url: "${pageContext.request.contextPath}/product/update",
                    type: "post",
                    data: v.currentProduct,
                    dataType: "JSON",
                    success: function (res) {
                        toastr.success("更新商品成功");
                        $('#myModal').modal('hide');
                    },
                    error: function (err) {
                        toastr.error("更新商品失败");
                    }
                })
            },
            add() {
                let v = this;
                $.ajax({
                    url: "${pageContext.request.contextPath}/product/add",
                    type: "post",
                    data: v.currentProduct,
                    dataType: "JSON",
                    success: function (res) {
                        toastr.success("添加商品成功");
                        setTimeout(() => {
                            window.location.reload();
                        },500);
                    },
                    error: function (err) {
                        toastr.error("添加商品失败");
                    }
                })
            },
            getAllCategory() {
                let v = this;
                $.ajax({
                    url: "${pageContext.request.contextPath}/category/all",
                    dataType: "JSON",
                    success: function (res) {
                        v.categories = res;
                    },
                    error: function (err) {

                    }
                })
            },
            index(i) {
                if (this.pageInfo.page <= 3 || this.maxPage <= 5) {
                    // 当前页小于3，则页码就是从 i 开始
                    return i;
                } else if (this.pageInfo.page <= this.maxPage - 2) {
                    // 大于3，则从page-2开始，然后逐个加1
                    return this.pageInfo.page - 3 + i;
                } else {
                    return this.maxPage - 5 + i;
                }
            },
            uploadFile() {
                let v = this;
                let data = new FormData();
                let file = $("#file")[0].files[0];
                if (file.type.indexOf("jpeg") != -1 || file.type.indexOf("png") != -1 || file.type.indexOf("gif") != -1 || file.type.indexOf("jpg") != -1 || file.type.indexOf("webp")) {
                    if (file.size < (1024 * 5 * 1024)) {
                        data.append("upload", file);
                        $.ajax({
                            url: "${pageContext.request.contextPath}/upload/file",
                            data: data,
                            dataType: "json",
                            processData: false, // 使数据不做处理
                            contentType: false,
                            type: "POST",
                            success: function (res) {
                                v.currentProduct.pic = res.message;
                            },
                            error: function (err) {
                                toastr.error("头像上传失败")
                            }
                        })
                    } else {
                        toastr.error("上传文件过大")
                    }
                } else {
                    toastr.error("上传文件不是图片")
                }

            },
            upload() {
                $("#file")[0].click();
            },
            allSelect() {
                this.checked = !this.checked;
                let v = this;
                this.products.forEach(item => {
                    item.check = v.checked;
            })
            },
            getAllProductPages(page) {
                if (page > this.maxPage) {
                    page = this.maxPage;
                }
                let v = this;
                let search = v.search;
                let searchCondition = {... search};
                searchCondition.page = page;
                searchCondition.size = v.pageInfo.size;
                console.log(searchCondition)
                $.ajax({
                    url: "${pageContext.request.contextPath}/product/list",
                    type: "post",
                    data:searchCondition,
                    dataType: "json",
                    success: function (res) {
                        v.pageInfo.page = res.currentPage;
                        v.maxPage = res.maxPage;
                        v.enterPage = res.currentPage;
                        v.total = res.maxTotal;
                        let products = [];
                        res.content.forEach(item => {
                            let product = {... item};
                        product.check = false;
                        products.push(product);
                    });
                        v.products = products;
                    },
                    error: function (err) {
                        console.log(err)
                    }
                })
            }
        },
        mounted() {
            this.getAllProductPages(1);
            this.getAllCategory();
        }
    })
</script>
</body>
</html>
