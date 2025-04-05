package com.school.controller;


import com.alibaba.fastjson.JSON;
import com.school.pojo.Student_Courses;
import com.school.service.Student_CoursesService;
import com.school.service.impl.Student_CoursesImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebServlet("/student_courses/*")
public class Student_CoursesServlet extends BaseServlet{
    private static Student_CoursesService studentCoursesService = new Student_CoursesImpl();


    public void deleteByCourseIdAndStudentId(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String studentId = req.getParameter("studentId");
        String courseId = req.getParameter("courseId");

        int studentID = Integer.parseInt(studentId);
        int courseID = Integer.parseInt(courseId);

        studentCoursesService.deleteByCourseIdAndStudentId(courseID,studentID);

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("success");
    }


    public void deleteByIds(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        BufferedReader br = req.getReader();
        String param = br.readLine();

        String studentId = req.getParameter("studentId");

        int []ids = JSON.parseObject(param, int[].class);

        studentCoursesService.deleteByIds(ids,Integer.parseInt(studentId));


        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("success");

    }
    public void addCourse(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String studentId = req.getParameter("studentId");
        String courseId = req.getParameter("courseId");

        int studentID = Integer.parseInt(studentId);
        int courseID = Integer.parseInt(courseId);

        resp.setContentType("text/html;charset=utf-8");


        if (studentCoursesService.HowChoose(studentID) >= 5){
            resp.getWriter().write("full");
            return;
        }

        studentCoursesService.addCourse(studentID,courseID);


        resp.getWriter().write("success");
    }

    public void deleteCourse(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String courseId = req.getParameter("courseId");

        int courseID = Integer.parseInt(courseId);

        studentCoursesService.deleteCourse(courseID);

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("success");
    }
}
