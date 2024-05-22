# playtube项目

## 项目简介

playtube是一个基于SpringBoot框架的视频弹幕项目，包括视频上传、播放、评论、点赞等功能。通过本项目，可以了解和掌握现代Web应用开发的相关技术和架构设计。

## 技术架构

采用 JDK 17 + Spring Boot 3 架构，构建高性能的视频弹幕平台。

## 功能模块

- **用户管理**：用户注册、双令牌登录、登出。
- **视频管理**：视频投稿、视频播放。
- **文件管理**：文件分片上传、断点续传、秒传。
- **互动功能**：评论、点赞、收藏、投币。
- **社交功能**：关注、动态。
- **弹幕功能**：弹幕实时推送。

## 环境依赖

- JDK 17
- Spring Boot 3.0.7
- MySQL
- Redis
- RocketMQ
- FastDFS(后续会替换成MinIO)

## 快速开始

1. **克隆项目**

    ```bash
    git clone https://github.com/NonBrickable/playtube.git
    ```

2. **配置数据库**

    在`application.yml`中配置MySQL数据库连接信息：

    ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/playtube
        username: root
        password: yourpassword
    ```

3. **配置Redis**

    在`application.yml`中配置Redis连接信息：

    ```yaml
    spring:
        data:
            redis:
                host: localhost
                password: 123456
                port: 6379
    ```
    
4. **配置RocketMQ**

    在`application.yml`中配置RocketMQ连接信息：

    ```yaml
    rocketmq:
      nameServer: localhost:9876
    ```

5. **配置FastDFS**

    在`application.yml`中配置FastDFS服务信息：

    ```yaml
    fdfs:
      #连接超时时间
      connect-timeout: 600
      #读取时间
      so-timeout: 1500
      #缩略图
      thumb-image:
        height: 150
        width: 150
      tracker-list: ip:22122
      pool:
        jmx-enabled: false
      http:
        storage-addr: http://ip:8888/group1/
    ```
4. **启动并访问应用**

    在浏览器中打开 [http://localhost:8080](http://localhost:8080) 访问playtube项目。

## 目录结构

```plaintext
playtube
├── src
│   └── main
│       └── java
│           └── com.playtube
│               ├── aspect
│               │   └── annotation
│               ├── common
│               │   ├── constant
│               │   └── exception
│               ├── config
│               ├── controller
│               │   └── websocket
│               ├── dao
│               ├── dto
│               ├── pojo
│               │   └── auth
│               ├── service
│               │   └── impl
│               └── util
├── target
├── .gitignore
└── pom.xml
