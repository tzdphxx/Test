package com.school.controller;


import com.alibaba.fastjson.JSON;
import com.school.pojo.PageBean;
import com.school.pojo.Student;
import com.school.pojo.User;
import com.school.service.StudentService;
import com.school.service.impl.StudentImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/student/*")
public class StudentServlet extends BaseServlet {
    private static StudentService  studentService = new StudentImpl();

    public void selectByUserId(HttpServletRequest request, HttpServletResponse response) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String userId = request.getParameter("userId");

        Student student = studentService.selectByUserId(Integer.parseInt(userId));


        String jsonString = JSON.toJSONString(student);

        response.setContentType("text/json;charset=utf-8");

        response.getWriter().write(jsonString);

    }
    public void add(HttpServletRequest request, HttpServletResponse response) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        BufferedReader br = request.getReader();
        String param = br.readLine();


        Student student = new Student();

        student = JSON.parseObject(param, Student.class);

        studentService.add(student);

        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write("success");
    }

    public void queryTheStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String studentId = request.getParameter("studentId");

        int studentID = Integer.parseInt(studentId);

        Student student = studentService.queryTheStudent(studentID);

        String jsonString = JSON.toJSONString(student);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void queryAllStudent(HttpServletRequest request, HttpServletResponse response) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {

        List<Student>list = studentService.queryAllStudent();
        String jsonString = JSON.toJSONString(list);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }

    public void changePhone(HttpServletRequest request, HttpServletResponse response) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String studentId = request.getParameter("studentId");
        int studentID = Integer.parseInt(studentId);
        String phone = request.getParameter("phone");
        studentService.changePhone(studentID, phone);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write("success");
    }
    public void studentsOfCourse(HttpServletRequest request, HttpServletResponse response) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String courseId = request.getParameter("courseId");
        int courseID = Integer.parseInt(courseId);
        List<Student>list = studentService.studentsOfCourse(courseID);
        String jsonString = JSON.toJSONString(list);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
    public void selectByPage(HttpServletRequest request, HttpServletResponse response) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String currentPage = request.getParameter("currentPage");
        int currentPageNum = Integer.parseInt(currentPage);
        String pageSize = request.getParameter("pageSize");
        int pageNum = Integer.parseInt(pageSize);

        PageBean<Student> studentPageBean = studentService.selectByPage(currentPageNum, pageNum);

        String jsonString = JSON.toJSONString(studentPageBean);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);
    }
    public void selectByPageAndCondition(HttpServletRequest request, HttpServletResponse response) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        String currentPage = request.getParameter("currentPage");
        int currentPageNum = Integer.parseInt(currentPage);
        String pageSize = request.getParameter("pageSize");
        int pageNum = Integer.parseInt(pageSize);
        String studentName = request.getParameter("studentName");
        String studentNumber = request.getParameter("studentNumber");
        String gender = request.getParameter("gender");

        PageBean<Student> studentPageBean = studentService.selectByPageAndCondition(currentPageNum, pageNum, studentNumber,studentName, gender);

        String jsonString = JSON.toJSONString(studentPageBean);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(jsonString);

    }
}
