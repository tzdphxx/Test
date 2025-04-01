package com.school.service.impl;

import com.school.dao.CoursesMapper;
import com.school.pojo.Courses;
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
    public List<Courses> selectByPage(int begin, int pageSize) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Courses> list = coursesMapper.selectByPage(begin,pageSize);
        return list;
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
}
