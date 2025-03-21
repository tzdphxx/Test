# JAVAWEB

### 项目的创建

1. #### 构建一个普通的maven项目，删掉src目录，并建立moudel；这个空 的过程就是Maven的主工程；

2. #### 父项目会有`xml`

   ```
   <modules>
       <module>untitled</module>
   </modules>
   ```

3. #### 子项目会有`xml`

   ```
   <parent>
       <groupId>org.example</groupId>
       <artifactId>javaweb-servlet01</artifactId>
       <version>1.0-SNAPSHOT</version>
   </parent>
   ```

4. #### 父项目的jar包子项目可以直接使用

5. #### Maven环境优化：

   修改web.xml为最新的

   将Maven结构搭建完整

6. #### 编写Servlet程序

   1. 编写一个普通类
   2. 实现servlet接口，**继承**HttpServlet

   `java`

   ```
   public class HelloServlet extends HttpServlet {
   
       //由于get和post只是请求实现的不同的方式，可以相互调用，业务逻辑是一样的
   
       @Override
       protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           PrintWriter writer = resp.getWriter();
   
           writer.print("hello Servlet");
       }
   
       @Override
       protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           super.doGet(req, resp);
       }
   ```

7. #### 编写Servlet的映射

   我们写的是java程序，但是要通过浏览器访问，而浏览器需要连接web服务器，所以需要在web服务中注册我们写的Servlet，还需给他一个浏览器能够访问的路径；

   `xml`

   ```
   <!--
   注册Servlet
   -->
   <servlet>
     <servlet-name>hello</servlet-name>
     <servlet-class>com.Sevlet.HelloServlet</servlet-class>
   </servlet>
   <!--
   Servlet请求路径
   -->
   <servlet-mapping>
     <servlet-name>hello</servlet-name>
     <url-pattern>/hello</url-pattern>
   </servlet-mapping>
   ```

8. #### 配置Tomcat

   配置项目发布的路径

9. #### 启动测试

   优先级问题：指定了固有映射路径的优先级最高，如果找不到就会走默认的处理请求

   ## ServletContext

   1. ServletContext

      web容器启动时，他会为每个web程序创建对应的ServletContext对象，他代表了当前的web应用

      - 共享数据

        在这个servlet保存的数据可以在另外一个servlet中拿到

        `java`

        - ```
          public class GetServlet extends HttpServlet {
              @Override
              protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                  ServletContext Context = this.getServletContext();
                  String username = (String) Context.getAttribute("username");
          
                  resp.setContentType("text/html;charset=utf-8");
          
                  resp.getWriter().print("<h1>" + username + "</h1>");
              }
          
              @Override
              protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                  doGet(req, resp);
              }
          }
          
          
          public class HelloServlet extends HttpServlet {
              @Override
              protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
          
          
                  ServletContext context = this.getServletContext();
          
                  String username = "胖猫";//数据
                  context.setAttribute("username", username);//将一个数据保存在ServletContext中，名字为：username，值为username
          
                  System.out.println("hello");
              }
          }
          
          
          ```

   2. 获取初始化参数

      `xml`

      ```
      配置web应用的初始化参数
      -->
        <context-param>
          <param-name>url</param-name>
          <param-value>jabc:mysql://localhost:3306/jabcstudy</param-value>
        </context-param>
      ```

      `java`

      ```
      public class ServletDemo01 extends HttpServlet {
      
          @Override
          protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              ServletContext context = this.getServletContext();
      
              String url = context.getInitParameter("url");
              resp.setContentType("text/html;charset=utf-8");
              resp.getWriter().print("<h1>" + url + "</h1>");
      
          }
      
          @Override
          protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              doGet(req, resp);
          }
      }
      ```

   3. 请求转发

   4. 读取资源文件

      在java目录下新建properties

      在resources目录下新建properties

      发现：都被打包到同一路径下：classes

      思路：需要一个文件流

      `xml`

      ```
      public class ServletDemo03 extends HttpServlet {
          @Override
          protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              InputStream is = this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");
              Properties prop = new Properties();
              prop.load(is);
      
              String username = prop.getProperty("username");
              String password = prop.getProperty("password");
      
              resp.setContentType("text/html;charset=utf-8");
      
              resp.getWriter().print("<h1>" + username + "</h1>" + "<h1>"+password+"</h1>");
          }
      
          @Override
          protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              doGet(req, resp);
          }
      }
      ```

      

   ## HttpServltResponse

   web服务器接受到客户端的http请求，针对这个请求，分别创建一个请求的HttpServltRequest对象，代表响应的一个HttpServltResponse；

   - 如果要获取客户端请求的参数：找HttpServltRequest

   - 如果要给客户响应一些信息：找HttpServltResponse

     1. 简单分类

        负责向浏览器发送数据的方法

        `ServletOutputStream getOutStream() throws IOException;`

        `PrinWriter getWriter() throws IOException;`

        负责向浏览器发送响应头的方法

        响应状态码

     2. 常见应用

        1. 向浏览器输出消息

        2. 下载文件

           1. 要获取下载文件的路径

           2. 下载 的文件名是什么

           3. 让浏览器支持我们下载需要的东西（要是中文名，要用URlEncoder.encode(fileName,"UTF-8")    否则可能出现乱码）

           4. 获取下载文件的输入流

           5. 创建缓冲区

           6. 获取OutputStream对象

           7. 将FileOutputStream流写入buffer缓冲区

           8. 使用OutputStream将缓冲区的数据输出到客户端

           9. `java`

              ```
              public class FileServlet extends HttpServlet {
              
                  @Override
                  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
              
              
              //        1. 要获取下载文件的路径
                      String realPath = "E:\\java\\java_code\\javaweb-servlet01\\untitled2\\src\\main\\resources\\1.jpg";
              
                      System.out.println("获取要下载文件的路径："+realPath);
                      //2. 下载 的文件名是什么
                      String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);
              
              //        3. 让浏览器支持我们下载需要的东西
                      resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);
              
              
              //        4. 获取下载文件的输入流
                      FileInputStream in = new FileInputStream(realPath);
              
              //        5. 创建缓冲区
                      int len;
                      byte[] bytes = new byte[1024];
              
              //        6. 获取OutputStream对象
                      ServletOutputStream out = resp.getOutputStream();
              
              //        7. 将FileOutputStream流写入buffer缓冲区   8. 使用OutputStream将缓冲区的数据输出到客户端
                      while ((len = in.read(bytes)) != -1) {
                          out.write(bytes,0, len);
                      }
              
              
                      //关流
                      out.close();
                      in.close();
              
                  }
              
                  @Override
                  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                      doGet(req, resp);
                  }
              ```

        

     3. 验证码功能

        - 前端实现
        - 后端实现，要用java生成一个图片

     4. 实现重定向

        一个web资源收到客户端请求后，他会通知客户端去访问另外一个web资源 

     5. sendRedirect(String var1)

     6. 重定向和转发的区别

        相同点：页面都会跳转

        不同点：

        - 重定向时，url会变化
        - 请求转发时，url不会发生变化

        

   ## HttpServltRequest

1. HttpServletRequest代表客户端的请求，用户通过Http访问服务器协议访问服务器，HTTP请求中的所有信息会被封装到HttpServletRequert，通过这个HttpServletrequest的方法，获得客户端的所有信息
2. 