package com.school.controller;


import com.alibaba.fastjson.JSON;
import com.school.dao.UserMapper;
import com.school.pojo.User;
import com.school.service.UserService;
import com.school.service.impl.UserImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private static UserService userService = new UserImpl();


    public void selectByUserName(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String username = req.getParameter("userName");
        String password = req.getParameter("passWord");

        String remember =req.getParameter("remember");

        try {
            User user = userService.selectByUserName(username);



            if (user != null && user.getPassWord().equals(password)) {

                HttpSession session = req.getSession();
                session.setAttribute("user", user);


                if ("true".equals(remember)) {
                    Cookie c_username = new Cookie("username", username);
                    Cookie c_password = new Cookie("password", password);

                    c_username.setPath("/");
                    c_password.setPath("/");

                    c_username.setMaxAge(60 * 60 * 24 * 7);
                    c_password.setMaxAge(60 * 60 * 24 * 7);

                    resp.addCookie(c_username);
                    resp.addCookie(c_password);
                } else {
                    Cookie c_name = new Cookie("username", "");
                    Cookie c_pwd = new Cookie("password", "");

                    c_name.setPath("/");
                    c_pwd.setPath("/");

                    c_name.setMaxAge(60 * 60 * 24 * 7);
                    c_pwd.setMaxAge(60 * 60 * 24 * 7);

                    resp.addCookie(c_name);
                    resp.addCookie(c_pwd);
                }



                Map<String, Object> responseData = new HashMap<>();
                responseData.put("userId", user.getUserId());
                responseData.put("role", user.getRole());

                resp.setContentType("application/json; charset=utf-8");

                resp.getWriter().write(JSON.toJSONString(responseData));
            }else {
                resp.setStatus(400);
                resp.getWriter().write("密码错误！");
            }
        } catch (SQLException e) {

            resp.getWriter().write(e.getMessage());
        }
    }

    public void selectAll (HttpServletRequest req, HttpServletResponse resp) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        List<User> users = userService.selectAll();

        String jsonString = JSON.toJSONString(users, true);

        resp.setContentType("application/json; charset=utf-8");

        resp.getWriter().write(jsonString);

    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BufferedReader br = req.getReader();
        String param = br.readLine();

        User user = new User();
        user = JSON.parseObject(param, User.class);

        try {
            userService.add(user);

            User newUser = userService.selectByUserName(user.getUserName());


            Map<String, Object> responseData = new HashMap<>();
            responseData.put("id", newUser.getUserId());

            resp.setContentType("application/json; charset=utf-8");
            resp.getWriter().write(JSON.toJSONString(responseData));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
