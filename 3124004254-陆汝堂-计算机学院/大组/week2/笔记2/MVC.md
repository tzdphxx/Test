# 框架

![](C:\Users\17811\AppData\Roaming\Typora\typora-user-images\image-20250322162516036.png)

# 会话跟踪技术

- 会话:用户打开浏览器，访问web服务器的资源，会话建立，直到有一方断开连接，会话结束。在一次会话中可以包含多次请求和响应
- 会话跟踪:一种维护浏览器状态的方法，服务器需要识别多次请求是否来自于同一浏览器，以便在同一次会话的多次请求间共享数据
- HTTP协议是无状态的，每次浏览器向服务器请求时，服务器都会将该请求视为新的请求，因此我们需要会话跟踪技术来实现会话内数据共享
- 实现方式：
  1. 客户端会话跟踪技术：Cookie
  2. 服务端会话跟踪技术：Session

# Cookie

客户端会话技术，将数据保存到客户端，以后每次请求都携带Cookie数据进行访

## Cookie 基本使用

### 创建Cookie对象，设置数据

`Cookie cookie = new Cookie("key","value");`

### 发送Cookie到客户端:使用response对象

`response.addCookie(cookie);`

### 获取客户端携带的所有Cookie，使用request对象

`Cookie [] cookies = request.getCookies();`

### 遍历数组，获取每一个Cookie对象: for

### 使用Cookie对象方法获取数据

`cookie.getName();`
`cookie.getValue();`

## Cookie 使用细节

### Cookie 存活时间

默认情况下，Cookie 存储在浏览器内存中，当浏览器关闭，内存释放，则Cookie被销毁

#### setMaxAge(int seconds):设置Cookie存活时

- 正数:将 Cookie写入浏览器所在电脑的硬盘，持久化存储。到时间自动删除
- 负数:默认值，Cookie在当前浏览器内存中，当浏览器关闭，则Cookie被销毁
- 零:删除对应 Cookie

### Cookie 存储中文

Cookie 不能直接存储中文
如需要存储，则需要进行转码:URL编码

# Session

服务端会话跟踪技术:将数据保存到服务端
JavaEE 提供 HttpSession接口，来实现一次会话的多次请求间数据共亨功能

## 使用:

1. 获取Session对象

   `HttpSession session =request.getSession();`

2. Session对象功能:

   `void setAttribute(String name, Object o)`:存储数据到 session 域中

   `Object getAttribute(String name)`:根据 key，获取值

   `void removeAttribute(String name)`:根据 key，删除该键值对

## Session 使用细节

### Session 钝化、活化:

服务器重启后，Session中的数据是否还在?

- 钝化:在服务器正常关闭后，Tomcat会自动将 Session数据写入硬盘的文件中
- 活化:再次启动服务器后，从文件中加载数据到

### Session中Seesion 销毁:

1. 默认情况下，无操作，30分钟自动销毁

   `xml`

   <session-config>
   <session-timeout>30</session-timeout></session-config>

2. 调用 Session对象的 invalidate()方法

## 小结

### Cookie 和 Session 都是来完成一次会话内多次请求间数据共享的

### 区别:

- 存储位置:Cookie 是将数据存储在客户端，Session 将数据存储在服务端
- 安全性:Cookie 不安全，Session安全
- 数据大小:Cookie 最大3KB，Session 无大小限制
- 存储时间:Cookie 可以长期存储，Session 默认30分钟
- 服务器性能:Cookie 不占服务器资源，Session 占用服务器资源

## 记住用户 -- 获取Cookie

#### 如果用户勾选“记住用户“则下次访问登陆页面自动填充用户名密码

### 如何自动填充用户名和密码?

1. 将用户名和密码写入Cookie中，并且持久化存储Cookie,下次访问浏览器会自动携带Cookie
2. 在页面获取Cookie数据后，设置到用户名和密码框中:

`${cookie.key.value}` // key指存储在cookie中的键名称!
