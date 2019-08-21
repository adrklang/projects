### 运行手册

```markdown
cd nacos-docker
集群方式
RUN
docker-compose -f example/cluster-hostname.yaml up -d
STOP
docker-compose -f example/cluster-hostname.yaml down //此操作会删除docker容器里面创建的应用docker-compose -f example/cluster-hostname.yaml stop //此操作仅关闭


单机内存数据库版
RUN
docker-compose -f example/standalone-derby.yaml up -d
STOP
docker-compose -f example/standalone-derby.yaml down
docker-compose -f example/standalone-derby.yaml stop

单机MYSQL数据库版
RUN
docker-compose -f example/standalone-mysql.yaml up -d
STOP
docker-compose -f example/standalone-mysql.yaml down
docker-compose -f example/standalone-mysql.yaml stop


```

