package com.school.service.impl;

import com.school.dao.StudentMapper;
import com.school.pojo.Student;
import com.school.service.StudentService;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class StudentImpl implements StudentService {
    private static StudentMapper studentMapper = new StudentMapper();

    @Override
    public Student selectByUserId(int userId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Student studentByUserId = studentMapper.getStudentByUserId(userId);
        return studentByUserId;
    }

    @Override
    public void add(Student student) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        studentMapper.add(student);
    }

    @Override
    public Student queryTheStudent(int studentId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return studentMapper.queryTheStudent(studentId);
    }

    @Override
    public List<Student> queryAllStudent() throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return studentMapper.queryAllStudent();
    }

    @Override
    public void changePhone(int studentId, String phone) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        studentMapper.changePhone(studentId, phone);
    }

    @Override
    public List<Student> studentsOfCourse(int courseId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return studentMapper.studentsOfCourse(courseId);
    }
}
