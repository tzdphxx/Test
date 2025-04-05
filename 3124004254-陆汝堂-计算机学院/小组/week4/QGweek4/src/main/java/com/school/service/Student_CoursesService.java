package com.school.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface Student_CoursesService {
    void deleteByCourseIdAndStudentId(int courseId, int studentId) throws SQLException;

    void deleteByIds(int []courseIds, int  studentId) throws SQLException;

    void addCourse(int student_id, int course_id) throws SQLException;

    int HowChoose(int studentId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    void deleteCourse(int courseId) throws SQLException;
}
