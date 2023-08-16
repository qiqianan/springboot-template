# springboot-template

## 前言

>  基于SpringBoot 2.7.14 + Spring Security 5.7.10 + Jwt + Redis + MybatisPlus + Knife4J等常用技术栈实现的一个框架

项目在开发中，不是最终版本

框架用于日常学习使用，有问题欢迎提出pr/issue

## 简单介绍

项目使用Spring Security + Jwt + Redis 实现登录认证和权限校验


## 项目依赖

> 用到的主要组件及说明（包括但不限于以下列表）
>
> 详细请看pom.xml文件

| 依赖                   | 说明                                                   |
| ---------------------- | ------------------------------------------------------ |
| JDK 1.8                | Java 编程语言版本                                      |
| SpringBoot 2.7.14      | 项目基础架构，集成了基本了框架能力                     |
| Spring Security 5.7.10 | Spring 生态系统中用于身份验证和授权的框架              |
| java-jwt 3.11.0        | 用于创建和验证 JSON Web Tokens (JWT)                   |
| lombok 1.18.28         | 注解自动化生成 Java 类的方法、构造函数和其他重复的代码 |
| redis                  | 内存数据存储系统                                       |
| knife4j                | Swagger 文档生成工具                                   |
| fastjson               | JSON 数据的序列化和反序列化                            |
| druid                  | 数据库连接池和监控工具                                 |
| ... ...                |                                                        |

​	

## 项目结构

```├─src
├─main
│  ├─java
│  │  └─com
│  │      └─west2
│  │          ├─common           // 通用类包
│  │          │  └─exception     // 自定义异常类包
│  │          ├─component        // 组件类包
│  │          ├─config           // 配置类包
│  │          ├─controller       // 控制器包
│  │          ├─dao              // 持久层
│  │          ├─domain           // 实体类
│  │          │  ├─dto           // 数据传输对象
│  │          │  └─vo            // 视图对象
│  │          ├─security         // 安全组件
│  │          ├─service          // 业务层
│  │          │  └─impl          // 业务实现类包
│  │          └─utils            // 工具类包
│  └─resources                   // 资源文件
└─test                           // 测试目录
    └─java
        └─com
            └─west2
```

