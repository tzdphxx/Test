package com.school.controller;


import com.alibaba.fastjson.JSON;
import com.school.dao.UserMapper;
import com.school.pojo.User;
import com.school.service.UserService;
import com.school.service.impl.UserImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        try {
            User user = userService.selectByUserName(username);



            if (user != null && user.getPassWord().equals(password)) {
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
