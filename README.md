# web端
## 部署步骤
1. 在IntelliJ IDEA打开项目文件，按照提示下载对应依赖包
2. 下载mysql、navicat（或其他mysql管理工具）
3. 新建链接（username：`root` password：`root`）--> 新建数据库 --> 运行sql文件（项目中的mall_db.sql文件）
4. 将项目中的upload文件夹移到D盘根目录下

## 运行：
登录网址
### 后台管理系统
```
https://localhost:28098/admin
```
管理员用户名和密码分别是`admin`和`123456`
### 客户端系统
```
https://localhost:28098/login
```
用户名和密码需要自己注册
