# 项目介绍
自定义的spring-boot的feign httpclient starter，为feign替换原生的URLConnection使用httpclient提供简易的配置并集成spring-boot的auto configuration
# 打包
修改相关的maven私服地址
```shell
gradle clean install uploadArchives
```
# 使用方式
## 依赖
```shell
compile "jthink:spring-boot-starter-feign-httpclient:0.0.1"
```
## 集成
在spring-boot项目的application.properties文件中加入相关的配置项，并赋予正确的值，如：
```shell
spring.cloud.feign.httpclient.maxTotal=200
spring.cloud.feign.httpclient.defaultMaxPerRoute=20
spring.cloud.feign.httpclient.alive=5000
```
