# AndroidLab2 - 实验二 网络访问

## 实验目标
- 掌握 Android 网络权限声明方式
- 理解明文 HTTP 通信的网络安全策略
- 通过对比验证 network_security_config.xml 的作用

## 运行方式
1. 用 Android Studio 打开本项目
2. 等待 Gradle Sync 完成
3. 连接模拟器或真机（Android 9+）
4. 点击 ▶ Run 运行 App

## 接口地址
- HTTPS：https://httpbin.org/get?room=A203
- HTTP：http://httpbin.org/get?room=A203

## 说明
**HTTP 仅用于教学对比，实际服务优先 HTTPS。**

HTTP 明文通信在 Android 9+ 默认被禁止，
需通过 res/xml/network_security_config.xml 显式放行
测试域名 httpbin.org 才能访问。
