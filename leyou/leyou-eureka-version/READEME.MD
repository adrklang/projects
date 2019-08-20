### host配置

```markdown
127.0.0.1 www.leyou.com #门户
127.0.0.1 manage.leyou.com #后台管理界面
127.0.0.1 api.leyou.com #网关
127.0.0.1 leyou.admin.server #springBootAdmin监控中心
127.0.0.1 image.leyou.com #图片服务器
127.0.0.1 db.mysql #mysql服务
127.0.0.1 leyou.registry.center #注册中心
127.0.0.1 leyou.images.repository #静态图片地址
127.0.0.1 leyou.rabbit.com #rabbitmq
127.0.0.1 leyou.redis.com #reids
```

### environment

```markdown
docker-enviroment目录下面是一些中间件服务
elasticsearch:6.4.3,需手动安装
```

### explain

```markdown
background:
	ly-parent:
		ly-api api接口，swagger文档注释
		ly-auth 授权中心
			ly-auth-common 授权服务通用工具模块
			ly-auth-service 授权服务
		ly-boot-admin springbootadmin监控中心
		ly-cart 购物车服务
		ly-common 通用工具
		ly-config 配置服务
		ly-item 子服务
			ly-item-interface
			ly-item-service 
		ly-order 订单服务
		ly-page 页面静态化服务
		ly-registry 注册中心
		ly-search 搜索服务
		ly-sms 消息服务
		ly-upload 文件上传服务
		ly-user 用户服务
			ly-user-interface
			ly-user-service
		ly-zuul-gateway zuul网关
frontend:
	manager:
		leyou-manage-web
			config
			src 
				assets 静态资源
				components 通用组件
				pages 页面
				router 路由
				config.js 配置文件
				http.js http请求模块
				main.js 入口
				menu.js 菜单数据
				mockDB.js 模拟数据
			static 静态资源
			index.html
			package.json
	portal:
		leyou-portal
			css
			fonts
			img
			items
			js
			plugins
			cart.html 购物车
			copyright.html
			foot.html
			getOrderInfo.html 获取订单信息页面
			home.html
			home-index.html
			home-orderDetail.html
			home-order-eveluate.html
			home-order-pay.html
			home-order-receive.html
			home-order-send.html
			home-person-collect.html
			home-person-footmark.html
			home-setting-address.html
			home-setting-address-complete.html
			home-setting-address-phone.html
			home-setting-info-html
			home-setting-safe.html
			index.html
			item.html
			login.html
			pay.html
			payfail.html
			paysuccess.html
			register.html
			search.html
			seckill-index.html
			seckill-item.html
			shortcut.html
			side.html
			success.html
			success-cart.html
			top.html
```

