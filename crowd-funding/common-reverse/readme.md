
##### 基于mybatis 的逆向工程
1. 在 pom.xml 中导入依赖
```xml
<!-- 控制Maven在构建过程中的相关配置 -->
    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.0</version>

                <dependencies>
                    <!-- 逆向工程核心依赖 -->
                    <dependency>
                        <groupId>org.mybatis.generator</groupId>
                        <artifactId>mybatis-generator-core</artifactId>
                        <version>1.3.2</version>
                    </dependency>
                    <dependency>
                        <groupId>com.mchange</groupId>
                        <artifactId>c3p0</artifactId>
                        <version>0.9.2</version>
                    </dependency>
                    <!--MySQL驱动-->
                    <dependency>
                        <groupId>mysql</groupId>
                        <artifactId>mysql-connector-java</artifactId>
                        <version>8.0.15</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```
2. 编写 generatorConfig.xml 文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="atguiguTables" targetRuntime="MyBatis3">
        <commentGenerator>
            <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
            <property name="suppressAllComments" value="true" />
        </commentGenerator>

        <!-- 数据库链接URL、用户名、密码 -->
        <jdbcConnection
                driverClass="com.mysql.cj.jdbc.Driver"
                connectionURL="jdbc:mysql://localhost:3306/project_rowd?serverTimezone=UTC"
                userId="root"
                password="root">
        </jdbcConnection>

        <!--
        默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer
            true，把JDBC DECIMAL 和 NUMERIC 类型解析为java.math.BigDecimal
        -->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>

        <!--
        生成model模型，对应的包路径，以及文件存放路径(targetProject)，targetProject可以指定具体的路径,如./src/main/java，
        也可以使用“MAVEN”来自动生成，这样生成的代码会在target/generatord-source目录下
        -->
        <!--<javaModelGenerator targetPackage="com.joey.mybaties.test.pojo" targetProject="MAVEN">-->
        <javaModelGenerator targetPackage="crowd.entity" targetProject=".\src\main\java">
            <!--是否让schema作为包的后缀-->
            <property name="enableSubPackages" value="false"/>
            <!-- 从数据库返回的值被清理前后的空格  -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>

        <!--对应的mapper.xml文件  -->
        <sqlMapGenerator targetPackage="mapper" targetProject=".\src\main\java">
            <!--是否让schema作为包的后缀-->
            <property name="enableSubPackages" value="false"/>
        </sqlMapGenerator>

        <!-- 对应的Mapper接口类文件 -->
        <javaClientGenerator type="XMLMAPPER" targetPackage="org.fall.mapper" targetProject=".\src\main\java">
            <!--是否让schema作为包的后缀-->
            <property name="enableSubPackages" value="false"/>
        </javaClientGenerator>

        <!-- 数据库表名与需要的实体类对应映射的指定 -->
        <table tableName="t_admin" domainObjectName="Admin"/>
    </context>
</generatorConfiguration>
```
在 idea 中使用 `mybatis-generator`，配置文件头部
文档类型定义报红(`http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd`）
**解决方案**
> https://www.cnblogs.com/yz-bky/p/12817330.html