package com.school.service.impl;

import com.school.dao.Student_CoursesMapper;
import com.school.service.Student_CoursesService;


import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class Student_CoursesImpl implements Student_CoursesService {
    private static Student_CoursesMapper student_coursesMapper = new Student_CoursesMapper();

    @Override
    public void deleteByCourseIdAndStudentId(int courseId, int studentId) throws SQLException {
        student_coursesMapper.deleteByCourseIdAndStudentId(courseId, studentId);
    }

    @Override
    public void deleteByIds(int[] courseIds, int studentId) throws SQLException {
        student_coursesMapper.deleteByCourseIds(courseIds, studentId);
    }

    @Override
    public void addCourse(int student_id, int course_id) throws SQLException {
        student_coursesMapper.addCourse(student_id,course_id);
    }



    @Override
    public int HowChoose(int studentId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return student_coursesMapper.HowChoose(studentId);
    }

    @Override
    public void deleteCourse(int courseId) throws SQLException {
        student_coursesMapper.deleteCourse(courseId);
    }


}
