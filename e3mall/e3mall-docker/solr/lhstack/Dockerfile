FROM solr
MAINTAINER lhstack <lhstack@foxmail.com>
# 安装中文分词
WORKDIR /opt/solr-8.1.1/server/solr-webapp/webapp/WEB-INF/lib
ADD IKAnalyzer2012_u6.jar .
WORKDIR /opt/solr-8.1.1/server/solr-webapp/webapp/WEB-INF
ADD stopword.dic .
ADD IKAnalyzer.cfg.xml .
COPY managed-schema /opt/solr-8.1.1/server/solr/configsets/_default/conf

WORKDIR /opt/solr-8.1.1

