FROM solr
MAINTAINER lhstack <lhstack@foxmail.com>
# 安装中文分词
WORKDIR /opt/solr-8.1.1/server/solr-webapp/webapp/WEB-INF/lib
ADD ik-analyzer-8.1.1.jar .
WORKDIR /opt/solr-8.1.1/server/solr-webapp/webapp/WEB-INF
ADD stopword.dic .
ADD ext.dic .
ADD IKAnalyzer.cfg.xml .
WORKDIR /opt/solr-8.1.1

