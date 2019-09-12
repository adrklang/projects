<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品分类</title>
<link href="${pageContext.request.contextPath}/admin/css/style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
<jsp:include page="/WEB-INF/headers.jsp"></jsp:include>

<script type="text/javascript">

// old write 
$(document).ready(function(){
  $(".click").click(function(){
  $(".tip").fadeIn(200);
  });
  
  $(".tiptop a").click(function(){
  $(".tip").fadeOut(200);
});

  $(".sure").click(function(){
  $(".tip").fadeOut(100);
});

  $(".cancel").click(function(){
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
            <li><a href="#">分类管理</a></li>
        </ul>
    </div>

    <div class="rightinfo">

        <div class="tools">

            <ul class="toolbar">


                <%-- <li class="click"><span><img src="${pageContext.request.contextPath}/admin/images/t02.png" /></span>修改</li>
                <li><span><img src="${pageContext.request.contextPath}/admin/images/t04.png" /></span>统计</li> --%>
                <li style="cursor: pointer;"  @click="currentCategory={state:1};updateState='add'" data-toggle="modal" data-target="#myModal"  id="add_category"><span><img src="${pageContext.request.contextPath}/admin/images/t01.png"  /></span>添加类别</li>
                <li style="cursor: pointer;" id="" @click="deleteByIds"><span><img src="${pageContext.request.contextPath}/admin/images/t03.png" /></span>批量删除</li>
            </ul>

        </div>

        <table class="tablelist">
            <thead>
            <tr>
                <th><input name="" type="checkbox" value="" @click="allSelect"/></th>
                <th>序号<i class="sort"><img src="${pageContext.request.contextPath}/admin/images/px.gif" /></i></th>
                <th>类别名称</th>
                <th>启用状态</th>
                <th>排序序号</th>
                <th>创建时间</th>
                <th>描述</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="category in categories">
                <td><input name="" type="checkbox" v-model="category.check" /></td>
                <td v-text="category.cid">1</td>
                <td v-text="category.cname">手机</td>
                <td v-text="category.state == 1 ? '启用':'未启用'"></td>
                <td v-text="category.orderNumber"></td>
                <td v-text="category.createTime"></td>
                <td v-text="category.description.substr(0,10)" :title="category.description"></td>
                <td style="width: 200px">
                    <span class="btn btn-warning col-lg-5" @click="currentCategory=category;updateState='edit'" data-toggle="modal" data-target="#myModal" style="margin-left: 7%;">修改</span><span class="btn btn-danger col-lg-5" @click="deleteById(category.cid)">删除</span>
                </td>
            </tr>
            </tbody>
        </table>


        <div class="pagin">
            <div class="message">共<i class="blue" v-text="total"></i>条记录，当前显示第&nbsp;<i class="blue" v-text="pageInfo.page">&nbsp;</i>页，共&nbsp;<i class="blue" v-text="maxPage">&nbsp;</i>页</div>
            <ul class="paginList">

                <li class="paginItem"><a href="javascript:;" @click="getAllPagesCategory(1)">首页</a></li>
                <li class="paginItem"><a href="javascript:;" @click="getAllPagesCategory(pageInfo.page - 1)">上一页</a></li>
                <li class="paginItem" v-for="i in Math.min(5,maxPage)"><a :style="index(i) == pageInfo.page ? 'color:red':''" @click="getAllPagesCategory(index(i))" href="javascript:;" v-text="index(i)"></a></li>
                <li class="paginItem"><a href="javascript:;" @click="getAllPagesCategory(pageInfo.page + 1)">下一页</a></li>
                <li class="paginItem"><a href="javascript:;" @click="getAllPagesCategory(maxPage)">尾页</a></li>
                <li class="paginItem"><a><input type="text" @keyup.enter="getAllPagesCategory(enterPage)" style="width: 30px;height: 25px;" v-model="enterPage"/></a></li>
            </ul>
        </div>
        <!-- 模态框（Modal） -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
                                <label class="col-lg-8" for="name">类别名称:</label>
                                <input class="form-control" style="font-size: 16px;width: 60%" v-model="currentCategory.cname" id="name"/>
                            </div>
                            <br />
                            <div class="form-inline" style="margin-left: 15%;">
                                <span class="form-control" for="">启用状态:</span>
                                <div class="form-control">
                                    启用:<input type="radio" checked name="state" @click="currentCategory.state = 1"/> 不启用:<input type="radio" name="state" @click="currentCategory.state = 0"/>
                                </div>
                            </div>
                            <div class="form-inline" style="margin-left: 15%;margin-top: 10px;">
                                <label class="col-lg-8" for="orderNumber">排序编号:</label>
                                <input class="form-control" style="font-size: 16px;width: 60%" type="number" v-model="currentCategory.orderNumber" id="orderNumber"/>
                            </div>
                            <br />
                            <div class="form-inline" style="margin-left: 15%;margin-top: 10px;">
                                <label class="col-lg-8" for="description">说明:</label>
                                <textarea class="form-control" style="font-size: 16px;width: 60%"  v-model="currentCategory.description" id="description"></textarea>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">
                            关闭
                        </button>
                        <button type="button" v-show="updateState=='edit'" @click="edit" class="btn btn-primary">
                            提交更改
                        </button>
                        <button type="button" v-show="updateState=='add'" @click="addCategory" class="btn btn-primary">
                            添加
                        </button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal -->
    </div>
</div>
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>
<script type="text/javascript">
    let vue = new Vue({
        el:"#app",
        methods:{
            deleteByIds(){
                let ids = "";
                this.categories.forEach(item =>{
                    if(item.check){
                        ids += item.cid + ",";
                    }
                });
                ids = ids.substr(0,ids.length - 1);
                $.ajax({
                    url:"${pageContext.request.contextPath}/category/deletes?cids=" + ids,
                    dataType:"JSON",
                    type:"DELETE",
                    success:function(res){
                        toastr.success("删除类别成功")
                        setTimeout(()=>{
                            window.location.reload();
                    },500);
                    },
                    error:function(err){
                        toastr.error("删除类别失败")
                    }
                })
            },
            deleteById(id){
                $.ajax({
                    url:"${pageContext.request.contextPath}/category/delete?cid=" + id,
                    dataType:"JSON",
                    type:"DELETE",
                    success:function(res){
                        toastr.success("删除类别成功")
                        setTimeout(()=>{
                            window.location.reload();
                         },500);
                    },
                    error:function(err){
                        toastr.error("删除类别失败")
                    }
                })
            },
            addCategory(){
                if(!this.currentCategory.cname){
                    toastr.error("请添加分类名称");
                    return;
                }
                if(!this.currentCategory.orderNumber){
                    toastr.error("请填写排序编号");
                    return;
                }
                $.ajax({
                    url:"${pageContext.request.contextPath}/category/add",
                    data:this.currentCategory,
                    dataType:"JSON",
                    type:"POST",
                    success:function(res){
                        toastr.success("添加分类信息成功")
                        setTimeout(()=>{
                            window.location.reload();
                        },500);
                    },
                    error:function(err){
                        toastr.error("添加分类信息失败")
                    }
                })
            },
            edit(){
               $.ajax({
                   url:"${pageContext.request.contextPath}/category/update",
                   data:this.currentCategory,
                   dataType:"JSON",
                   type:"POST",
                   success:function(res){
                       toastr.success("分类信息更新成功")
                       $('#myModal').modal('hide');
                   },
                   error:function(err){
                       toastr.error("分类信息更新失败")
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
            allSelect(){
                let v = this;
                v.checked = !this.checked;
              this.categories.forEach(item =>{
                  item.check = v.checked;
              })
            },
            getAllPagesCategory(page){
                if(page > this.maxPage){
                    page = this.maxPage;
                }
                let v = this;
                $.ajax({
                    url:"${pageContext.request.contextPath}/category/list",
                    type:"post",
                    data:{
                        page:page,
                        size:v.pageInfo.size
                    },
                    dataType:"json",
                    success:function(res){
                        v.pageInfo.page = res.currentPage;
                        v.maxPage = res.maxPage;
                        v.enterPage = res.currentPage;
                        v.total = res.maxTotal;
                        let categories = [];
                        res.content.forEach(item =>{
                            let category = {... item};
                            category.check = false;
                            if(!item.description){
                                category.description = ""
                            }
                            categories.push(category);
                        });
                        v.categories = categories;
                    },
                    error:function(err){
                        console.log(err)
                    }
                })
            }
        },
        data:function(){
            return {
                pageInfo:{
                    page:1,
                    size:10
                },
                total:0,
                maxPage:0,
                checked:false,
                enterPage:0,
                categories:[],
                currentCategory:{},
                updateState:"add"
            }
        },
        mounted(){
            this.getAllPagesCategory(1);
        }
    })
</script>
</body>
</html>
