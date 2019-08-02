### e3mall solr搭建

```makefile
1.安装docker-compose //注意，docker-compose需要依赖docker ，如果已安装，则跳过此操作
将当前目录下面的docker-compose 复制到linux下`/usr/local/bin`目录下面，并添加可执行权限
命令 chmod +x docker-compose
```

```markdown
2.安装solr
进入solr目录,运行 `docker-compose up -d`即可
```

```markdown
3.执行docker命令
  (1).docker exec -ti solr bash
	进入solr 容器，执行以下命令
		1). solr create -c collection
		创建完成之后
		2) .exit 退出容器

  (2). docker cp managed-schema solr:/var/solr/data/collection/conf
  (3). docker restart solr 即可
```

