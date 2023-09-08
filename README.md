<p align="center">
  <img align="center" src="https://raw.githubusercontent.com/bihell/blog-img/master/logo.png"/>
</p>
<p align="center">
    <a alt="java"><img src="https://img.shields.io/badge/java-17-yellow.svg"/></a>
    <a alt="spring boot"><img src="https://img.shields.io/badge/spring%20boot-3-blue"/></a>
</p>

* 基于`java17` `spring-boot3` `spring-security ` 开发的个人管理系统: 目前有用户、角色、权限管理基础模块.

> 演示账号：

> 管理员：bg 123456

> 数据库每日零点重制

## 二、项目结构

```
bg
├── bg-ms-admin        --  后台管理前端，基于vue-vben-admin项目开发。
├── bg-ms-front        --  博客SEO前端，基于vue-next项目开发
└── bg-ms              --  后端服务，Spring Boot全家桶
    ├── bg-bootstrap     --  启动模块
    ├── bg-framework     --  框架核心模块
    ├── bg-nav           --  导航模块
    └── bg-system        --  系统模块
```

## 三、参与开发

目前后端刚切`SpringBoot3`和`SpringSecurity`，一些细节还未调整，欢迎各位参与进来。

> 请确保系统中已经安装`docker`、`docker-componse`、`nodejs`、`npm`、`Java17`、`Redis`、`MySQL`等必须的依赖。

### 3.1 安装依赖（MacOS）

#### Java

[Oracle官方](https://www.oracle.com/hk/java/technologies/downloads/#java17)直接下载安装

> 注：spring-boot 3开始支持Java17以上版本

#### MySQL

```Bash
brew install mysql
mysql.server start
```

> 如果你的MySQL版本较新，可能会碰到无法连接的错误。可以重新设置一下账号权限，方式如下：

> ```
> ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'password'
> flush privileges;
> ```

#### Redis

```Bash
brew install redis
brew services start redis
```

#### Pnpm

版本\>=`8.1.0`

```Bash
brew install pnpm
```

#### Node

版本\>=`16.15.1`

```Bash
brew install node
```

#### Git

版本\>= 2.x

```Bash
brew install git
```

### 3.2 启动运行

克隆项目到本地

   ```
   git clone git@github.com:wujie329426446/bg-ms.git
   ```

#### 3.2.1 `bg-ms` Java后台

项目使用lombok插件，如果要在ide中调试要有lombok插件
数据库初始语句:`bg-ms/misc/init.sql`

##### 修改相应配置

进入服务端文件夹`cd bg-ms`,修改spring-boot配置文件`vi bg-ms/src/main/resources/config/application-dev.yml`

```
spring:
    datasource:
      driverClassName: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/bg?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
      username: root
      password: root
```

将数据库的用户名和密码修改成对应你数据库的用户名密码

##### 项目启动

多种启动方式：

1. 直接运行main方法

直接在IDE中运行`bg-bootstrap`模块的`BgApplication`启动类的main方法就可以看到项目启动了。

2. 打包启动

```Bash
mvn clean package -Dmaven.test.skip=true -Pdev
java -jar target/bg.jar
```

> 注意：Maven版本要>=3.2.5
