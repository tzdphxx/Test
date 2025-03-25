AJAX

1. 编写AjaxServlet，并使用response输出字符串

2. 创建 XMLHttpRequest 对象:用于和服务器交换数据
   ```java
   var xmlhttp;
   if (window.XMLHttpRequest) {
   //code for IE7+, Firefox, Chrome, Opera, Safari
   xmlhttp= new XMLHttpRequest();
   } else {
   //code for lE6, IE5
   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
   }
   ```

   

3. 向服务器发送请求
   ```java
   xm/http.open("GET",“ur!")	xmlhttp.send();//发送请求
   ```

   

4. 获取服务器响应数据
   ```java
   xm/http.onreadystatechange = function () {
   if(xmlhttp.readystate==4&& xmlhttp.status ==200){
   alert(xmlhttp.responseText);
   	}
   }
   ```

   

# AXIOS

1.引入 axios 的js 文件

<script src="js/axios-0.18.0.js"></script>
2.使用axios 发送请求，并获取响应结果

```java
`axios({
    method:"get",
    url:"http://localhost:8080/ajax-demo1/aJAXDemo1?username=zhangsan"
}).then(function (resp){
    alert(resp.data);
})
```

```java
axios({
    method:"post",
    url:"http://localhost:8080/ajax-demo1/aJAXDemo1"data:"username=zhangsan",
}).then(function (resp){
    alert(resp.data);
});
```

简写

```java
axios.get("url")
    .then(function (resp){
        alert(resp.data);
});
```



# JSON

### 定义:

`var 变量名={"key1":value1."key2": value2,`

### 示例:

```html
var json ={"name": "zhangsan",
"age": 23,
"addr":["北京”,"上海”,“西安”]
};
```

- value 的数据类型为:
- 数字(整数或浮点数)
- 字符串(在双引号中)
- 逻辑值(true 或 false)
- 数组(在方括号中)
- 对象(在花括号中)
- null

获取数据:
`变量名.key`
`json.name`

### JSON 数据和Java对象转换

Fastjson是阿里巴巴提供的一个ava语言编写的高性能功能完善的JSON库，是目前Java语言中最快的JSON库，可以实现Java对象和JSON字符串的相互转换。

#### 使用:

导入坐标
```xml
<dependency>
<groupld>com.alibaba</groupld>
<artifactld>fastison</artifactld>
<version>1.2.62</version>
</dependency>
```

Java对象转JSON
```java
String jsonStr=JSoN.toJsONString(obj);
```

JSON字符串转Java对象
```java
User user=jSON.parseObject(isonStr, User.class);
```

