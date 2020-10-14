#### crowd-funding

在线众筹平台，分后台管理系统和前台会员系统两部分：
1. 后台管理系统是基于`SSM`整合的单一架构
2. 前台会员系统是基于`SpringBoot+SpringCloud`的微服务架构



> [源码地址](https://www.bilibili.com/video/BV1bE411T7oZ)


##### 遇到的问题
*IDEA 中运行 Maven 聚合项目* 
> [IntelliJ IDEA中创建Web聚合项目(Maven多模块项目)](https://cloud.tencent.com/developer/article/1081471)
> 每次启动都得重新打包

* 模糊查询和全量查询共用的SQL语句 *
> where searchName like concat("%", #{keyword}, "%");

