# Filter

## 概念

- Filter 表示过滤器，是JavaWeb 三大组件(Servlet、Filter、Listener)之一
- 过滤器可以把对资源的请求拦截下来，从而实现一些特殊的功能。
- 过滤器一般完成一些通用的操作，比如:权限控制、统一编码处理、敏感字符 处理等等.

## 实现

1. 定义类，实现 Fiter接口，并重写其所有方法

   ```java
   public class FilterDemo implements Filter {public void init(Filterconfig filterconfig)public void doFilter(ServletRequest requestpublic void destroy(){}
   ```

   

2. 配置Filter拦截资源的路径:在类上定义 @WebFilter 注解

   ```java
   @WebFilter("/*")
   public class FilterDemo implements Filter {
   ```

3. 在doFilter方法中输出一句话，并放行

   ```java
   public void doFilter(ServletRequest request,SerSystem.out.println("filter 被执行了..."); //放行
   chain.doFilter(request,response);
                        }
   ```

## Filter 拦截

```java
@WebFilter("/*")
public class FilterDemo
```



- 拦截具体的资源:/index.jsp:只有访问indexjsp时才会被拦截。
- 目录拦截:/user/*:访问/user下的所有资源，都会被拦截*
- *后缀名拦截:*jsp:访问后缀名为jsp的资源，都会被拦截
- 拦截所有:/*:访问所有资源，都会被拦截

## 过滤器链

一个web应用，可以配置多个过滤器，这多个过滤器称为过滤器链

注解配置的Filter，优先级按照过滤器类名（字符串）的自然排序（字典序）

# Listener

## ServletContextListener 使用

1. 定义类，实现ServletContextListener接口
   ```java
   public class ContextLoaderListener implements ServletContextListener {
       
   //ServletContext对象被创建:整个web应用发布成功
       
   public voidcontextInitialized(ServletContextEvent sce){}
   
   //ServletContext对象被销毁:整个web应用卸载
   
   public void contextDestroyed(ServletContextEvent sce){}
   ```

   

2. 在类上添加@WebListener 注解

   
   