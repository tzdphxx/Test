package com.school.service.impl;

import com.school.dao.CoursesMapper;
import com.school.pojo.Courses;
import com.school.pojo.PageBean;
import com.school.service.CoursesService;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class CoursesImpl implements CoursesService {

    private static CoursesMapper coursesMapper = new CoursesMapper();

    @Override
    public List<Courses> selectAllCourse() throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Courses> list = coursesMapper.selectAllCourse();

        return list;
    }

    @Override
    public PageBean<Courses> selectByPage(int currentPage, int pageSize, String courseName, String teacherName, String status) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int start = (currentPage - 1) * pageSize;
        List<Courses> list = coursesMapper.selectByPage(start, pageSize);

        int totalCount = coursesMapper.selectCountByPageAndCondition(courseName, teacherName, status);

        PageBean<Courses> pageBean = new PageBean<>();
        pageBean.setTotalCount(totalCount);
        pageBean.setRows(list);
        return pageBean;
    }

    @Override
    public List<Courses> selectByStudentId(int studentId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Courses> list = coursesMapper.selectByStudentId(studentId);

        return list;
    }

    @Override
    public List<Courses> selectAllCourseByTime(int studentId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Courses> list = coursesMapper.selectAllCourseByTime(studentId);
        return list;
    }

    @Override
    public void deleteCourse(int courseId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        coursesMapper.deleteCourse(courseId);
    }

    @Override
    public void editCourse(Courses course) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        coursesMapper.editCourse(course);
    }

    @Override
    public PageBean<Courses> selectByPageAndCondition(int currentPage, int pageSize, String courseName, String teacherName, String status) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int begin = (currentPage - 1) * pageSize;

        List<Courses> list = coursesMapper.selectByPageAndCondition(begin,pageSize,courseName,teacherName,status);
        int totalCount = coursesMapper.selectCountByPageAndCondition(courseName,teacherName,status);
        PageBean<Courses> pageBean = new PageBean<>();
        pageBean.setTotalCount(totalCount);
        pageBean.setRows(list);
        return pageBean;
    }




}
