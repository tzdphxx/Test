# Vue

Vue 是一套前端框架，免除原生JavaScript中的DOM操作，简化书写

基于MVVM(Model-View-ViewModel)思想，实现数据的双向绑定，将编程的关注点放在数据上

新建 HTML 页面，引入 Vue.js文件

```html
<script src="js/vue.js"></script>
```

在JS代码区域，创建Vue核心对象，进行数据绑定
```html
new Vue({
el:"#app",
data(){
return {
		username:""
		}
	}
});
```

编写视图

```html
<div id="app">
<input name="username" v-model="username" >{username}
</div>
```

### 指令

### 生命周期

![image-20250323224059398](C:\Users\17811\AppData\Roaming\Typora\typora-user-images\image-20250323224059398.png)

# Element

- Element:是饿了么公司前端开发团队提供的一套基于 Vue 的网站组件库，用于快速构建网页
- 组件:组成网页的部件，例如 超链接、按钮、图片、表格等等~

![image-20250323231422447](C:\Users\17811\AppData\Roaming\Typora\typora-user-images\image-20250323231422447.png)

1. 引入Element 的css、js文件 和 Vue.js

2. ```html
   <script srg "vue.js"></script>
   <script src="element-ui/lib/index.js"></script>
   <link rel="stylesheet" href="element-ui/lib/theme-chalk/index.css">
   ```

3. 创建Vue核心对象

4. ```html
   <script>
   new Vue({
       el:"#app"
   })
   </script>
   ```

5. 官网复制Element组件代码