package com.school.controller;


import com.alibaba.fastjson.JSON;
import com.school.pojo.Courses;
import com.school.pojo.PageBean;
import com.school.service.CoursesService;
import com.school.service.CoursesService;
import com.school.service.impl.CoursesImpl;
import com.school.service.impl.CoursesImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/courses/*")
public class CoursesServlet extends BaseServlet{

    private static CoursesService coursesService = new CoursesImpl();

    public void selectAllCourse(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        List<Courses> list = coursesService.selectAllCourse();

        String jsonString = JSON.toJSONString(list, true);

        resp.setContentType("text/json;charset=utf-8");

        resp.getWriter().write(jsonString);
    }

    /*public void selectByPage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String currentPage = req.getParameter("currentPage");
        String pageSize = req.getParameter("pageSize");

        int currentPageInt = Integer.parseInt(currentPage);
        int pageSizeInt = Integer.parseInt(pageSize);

        PageBean<Courses> list = coursesService.selectByPage(currentPageInt,pageSizeInt);

        String jsonString = JSON.toJSONString(list, true);

        resp.setContentType("text/json;charset=utf-8");

        resp.getWriter().write(jsonString);
    }*/

    public void selectByStudentId(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String studentId = req.getParameter("studentId");

        List<Courses> list = coursesService.selectByStudentId(Integer.parseInt(studentId));

        String jsonString = JSON.toJSONString(list, true);
        resp.setContentType("text/json;charset=utf-8");

        resp.getWriter().write(jsonString);
    }

    public void selectAllCourseByTime(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        BufferedReader bufferedReader = req.getReader();
        String param = bufferedReader.readLine();

        int studentId = JSON.parseObject(param, int.class);

        List<Courses> list = coursesService.selectAllCourseByTime(studentId);

        String jsonString = JSON.toJSONString(list, true);
        resp.setContentType("text/json;charset=utf-8");

        resp.getWriter().write(jsonString);
    }

    public void deleteCourse(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String courseId = req.getParameter("courseId");

        int courseID = Integer.parseInt(courseId);

        coursesService.deleteCourse(courseID);

        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write("success");
    }

    public void editCourse(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        BufferedReader br = req.getReader();
        String param = br.readLine();

        Courses courses = JSON.parseObject(param, Courses.class);

        coursesService.editCourse(courses);

        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write("success");
    }

    public void selectAllCourseAndCondition(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String courseName = req.getParameter("courseName");
        String teacherName = req.getParameter("teacherName");
        String status = req.getParameter("status");
        String currentPage = req.getParameter("currentPage");
        int currentPageInt = Integer.parseInt(currentPage);
        String pageSize = req.getParameter("pageSize");
        int pageSizeInt = Integer.parseInt(pageSize);

        PageBean<Courses> pageBean = coursesService.selectByPageAndCondition(currentPageInt, pageSizeInt, courseName, teacherName, status);
        String jsonString = JSON.toJSONString(pageBean, true);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

}
