# YMail

![](https://img.shields.io/badge/Spring%20Boot-2.3.10.RELEASE-green) ![](https://img.shields.io/badge/Spring%20Security-2.3.10.RELEASE-green) ![](https://img.shields.io/badge/Redis-5.0.7-red) ![](https://img.shields.io/badge/MyBatis--Plus-3.3.1-yellow) ![](https://img.shields.io/badge/JWT-0.9.1-yellowgreen)



## 项目介绍

> 邮箱客户端后台服务



## 项目结构

```
.
├── pom.xml
├── doc               ----------------------- 文档、sql脚本
├── rjsoft-admin      ----------------------- 基础管理模块
└── rjsoft-common     ----------------------- 公共包
```



## 功能描述

包含邮件的收发、收件箱、已发送邮件、草稿箱、已删除邮件功能
其中收件箱、已删除邮件功能使用Java Mail自身api实现
已发送邮件、草稿箱功能使用在数据库自建表来实现(sql文件见doc)
后者的查询速度快、支持查询更灵活，但更换客户端后无法看到相应邮件, 前者反之

可根据自身情况两者选其一重构另外的功能



## 项目文档

启动项目后浏览器打开此[链接](http://localhost:8187/email/doc.html)