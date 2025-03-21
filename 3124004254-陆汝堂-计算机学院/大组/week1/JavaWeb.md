# JavaWeb



## HttpServlet 使用步骤

1. 继承HttpServlet
2. 重写doGet和doPost方法

## HttpServlet原理

获取请求方式，并根据不同的请求方式，调用不同的doXxx方法

## Servlet urlPattern配置

Servlet 要想被访问，必须配置其访问路径(urlPattern)

### 1.一个Servlet，可以配置多个 urlPattern

@WebServlet(urlPatterns ={"/demo1","/demo2"})

### 2.urlPattern 配置规则

1. 精确匹配:

   配置路径:@WebServlet("/user/select")

   访问路径:localhost:8080/web-demo/user/select

   

2. 目录匹配:

   配置路径:Webservlet("/user/\")

   访问路径:

   localhost:8080/web-demd/user/aaa

   localhost:8080/web-demo/user/bbb

   

3. 扩展名匹配:

   配置路径:@WebServlet("*.do")

   访问路径:

   localhost:8080/web-demd/aaa.do

   localhost:8080/web-demc/bbb.do

   

4. 任意匹配:

   @WebServlet("/")

   配置路径:@WebServlet("/*")

   访问路径:

   Vhehelocalhost:8080/web-demc

   localhost:8080/web-demo/haha

   

## Request获取资源

请求数据分为3部分:

### 请求行:  

**GET /request-demo/req1?username=zhangsan HTTP/1.1**

String getMethod():  获取请求方式: GET

String getContextPath():获取虚拟目录(项目访问路径):/request-demo

StringBuffer getRequestURL(): 获取URL(统一资源定位符):http://localhost:8080/request-demo/reg1

String getRequestURl():获取URI(统一资源标识符):/request-demo/req1
String getQueryString():获取请求参数(GET方式):  username=zhangsan&password=123

### 请求头:

**User-Agent: Mozilla/5.0 Chrome/91.0.4472.106**

String getHeader(String name):根据请求头名称，获取值

### 请求体:

**username=superbaby&passryord=123**

ServletInputStream getInputStream():获取字节输入流

BufferedReader getReader():获取字符输入流

中文输入乱码：

- 如果是get方法：  先编码，再解码
- 如果是post方法：   req.setCharacterEncoding("GBK");

### 请求转发：

req.getRequestDispatcher("路径").forward(req,resp)

#### 请求转发资源间共享数据:

使用Request对象
void setAttribute(String name,Objecto):存储数据到 request域中

Object getAttribute(String nare):根据 key，获取值

void removeAttribute(String name):根据 key，删除该键值对