<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户信息</title>
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
			<li><a href="#">用户管理</a></li>
		</ul>
	</div>

	<div class="rightinfo">

		<div class="tools">

			<ul class="toolbar">


             <%--   <li class="click"><span><img src="images/t02.png" /></span>修改</li>
                <li><span><img src="images/t04.png" /></span>统计</li>
--%>
				<li @click="deleteAllSelect" id="batchDelete" style="cursor: pointer;"><span><img src="${pageContext.request.contextPath}/admin/images/t03.png" /></span>批量删除</li>
			</ul>

		</div>
		<table class="tablelist">
			<thead>
			<tr>
				<th><input id="checkbox_main" type="checkbox" @click="allSelect" /></th>
				<th>序号<i class="sort"><img src="${pageContext.request.contextPath}/admin/images/px.gif" /></i></th>
				<th>姓名</th>
				<th>性别</th>
				<th>电话号码</th>
				<th>所在地区</th>
				<th>权限</th>
				<th>账号</th>
				<th>头像</th>
				<th>操作</th>
				<th>修改状态</th>
			</tr>
			</thead>
			<tbody>

			<tr v-for="user in allUser">
				<td><input name="ids" type="checkbox" v-model="user.check"/></td>
				<td v-text="user.uid"></td>
				<td v-text="user.name"></td>
				<td v-text="user.sex ? '女':'男'"></td>
				<td v-text="user.phoneNumber"></td>
				<td v-text="user.area"></td>
				<td v-text="user.manager == 0 ? '普通用户':'管理员'"></td>
				<td v-text="user.username"></td>
				<td style="text-align: center;">
					<img :src="user.photo" style="border: 1px solid gray" width="80" height="50" alt="" />
				</td>
				<td style="width: 190px">
					<button style="width: 80px" class="btn btn-warning" @click="editUser=user" data-toggle="modal" data-target="#myModal">修改</button>
					<button style="width: 80px" class="btn btn-danger" @click="deleteById(user.uid)">删除</button>
				</td>
				<td style="width: 160px">
					<button class="btn btn-warning" v-if="user.manager == 0" @click="updateState(user,1)">置为管理员</button>
					<button class="btn btn-warning" v-else @click="updateState(user,0)">置为普通用户</button>
				</td>
			</tr>

			</tbody>
		</table>

		<div class="pagin">
			<div class="message">共<i class="blue" v-text="total"></i>条记录，当前显示第&nbsp;<i class="blue" v-text="pageInfo.page">&nbsp;</i>页，共&nbsp;<i class="blue" v-text="maxPage">&nbsp;</i>页</div>
			<ul class="paginList">
				<li class="paginItem"><a @click.prevent="checkPageUsers(1)" href="javascript:;">首页</a></li>
				<li class="paginItem"><a @click.prevent="checkPageUsers(pageInfo.page - 1)" href="javascript:;">上一页</a></li>
				<li class="paginItem" v-for="i in Math.min(5,maxPage)"><a :style="index(i) == pageInfo.page ? 'color:red':''" @click="checkPageUsers(index(i))" href="javascript:;" v-text="index(i)"></a></li>
				<li class="paginItem"><a @click.prevent="checkPageUsers(pageInfo.page + 1)" href="javascript:;">下一页</a></li>
				<li class="paginItem"><a @click.prevent="checkPageUsers(maxPage)"  href="javascript:;">尾页</a></li>
				<li class="paginItem"><a><input type="text" @keyup.enter="checkPageUsers(enterPage)" style="width: 30px;height: 25px;" v-model="enterPage"/></a></li>
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
								<label class="col-lg-6 form-control" for="name">昵称:</label>
								<input class="form-control" style="font-size: 16px;width: 60%" v-model="editUser.name" id="name"/>
							</div>
							<br />
							<div class="form-inline" style="margin-left: 15%;">
								<label class="col-lg-6 form-control" for="area">地址:</label>
								<input class="form-control" style="font-size: 16px;width: 60%"  v-model="editUser.area" id="area"/>
							</div>
							<div class="form-inline" style="margin-left: 15%;margin-top: 10px;">
								<label class="col-lg-6 form-control">性别:</label>
								男<input type="radio" class="form-contol" name="sex" @click="editUser.sex = 0" checked="checked" value="1">
								女<input type="radio" name="sex" @click="editUser.sex = 1" value="0">
							</div>
							<br />
							<div class="form-inline" style="margin-left: 15%;">
								<label class="col-lg-6 form-control" for="photo">头像</label>
								<span @click="upload" class="btn btn-primary">上传头像</span>
								<img :src="editUser.photo" style="margin-left: 5px;width: 25px;height: 25px;border-radius: 100%" id="photo"/>
								<input @change="uploadFile" id="file" type="file" style="display: none"/>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							关闭
						</button>
						<button type="button" @click="edit" class="btn btn-primary">
							提交更改
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
		data:function(){
			return {
				pageInfo:{
					page:1,
					size:10,
				},
				users:[],
				total:0,
				maxPage:0,
				checked:false,
				editUser:{},
				enterPage:0,
			}
		},
		methods:{
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
			updateState(user,state){
				$.ajax({
					url:"${pageContext.request.contextPath}/super/updateState",
					type:"post",
					data:{
						uid:user.uid,
						state:state
					},
					dataType:"json",
					success:function(res){
						toastr.success("更新用户权限状态成功");
						setTimeout(() =>{
							window.location.reload();
						},1000);
					},
					error:function(err){
						toastr.error("更新用户权限状态失败");
					}
				})
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
								v.editUser.photo = res.message;
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
			deleteAllSelect(){
				let ids = "";
				this.users.forEach(item =>{
					if(item.check){
						ids += item.uid + ",";
					}
				});
				ids = ids.substr(0,ids.length - 1);
				$.ajax({
					url:"${pageContext.request.contextPath}/super/deletes?uids=" + ids,
					type:"DELETE",
					dataType:"json",
					success:function(res){
						toastr.success("删除用户成功");
						setTimeout(() =>{
							window.location.reload();
						},1500);
					},
					error:function(err){
						toastr.error("删除用户失败");
					}
				});
			},
			edit(){
				$.ajax({
					url:"${pageContext.request.contextPath}/super/update",
					type:"POST",
					data:this.editUser,
					dataType:"json",
					success:function(res){
						toastr.success("更新用户信息成功");
						$('#myModal').modal('hide');
					},
					error:function(err){
						toastr.error("更新用户信息失败");
					}
				})
			},
			deleteById(id){
				$.ajax({
					url:"${pageContext.request.contextPath}/super/delete?uid=" + id,
					type:"DELETE",
					dataType:"json",
					success:function(res){
						toastr.success("删除用户成功");
						setTimeout(() =>{
							window.location.reload();
						},1500);
					},
					error:function(err){
						toastr.error("删除用户失败");
					}
				});
			},
			allSelect(){
				this.checked = !this.checked
				for(let i = 0;i < this.users.length;i++){
					this.users[i].check = this.checked;
				}
			},
			checkPageUsers(page){
				if(page < 1){
					page = 1;
				}
				if(page > this.maxPage){
					page = this.maxPage;
				}
				this.getPageUsers(page);
			},
			getPageUsers(page){
				let v = this;
				$.ajax({
					url:"${pageContext.request.contextPath}/super/user_list",
					type:"get",
					data:{
						page:page,
						size:v.pageInfo.size
					},
					dataType:"json",
					success:function(res){
						v.total = res.maxTotal;
						let users = []
						console.log(res)
						res.content.forEach(item =>{
							let user = { ...item};
							user.check = false;
							users.push(user);
						});
						v.enterPage = res.currentPage;
						v.users = users;
						v.maxPage = res.maxPage;
						v.pageInfo.page = res.currentPage;
					},
					error:function(err){

					}
				})
			}
		},
		computed:{
			allUser(){
				return this.users;
			}
		},
		mounted(){
			this.getPageUsers(1);
		}
	})
</script>
</body>
</html>
