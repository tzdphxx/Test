package com.school.service.impl;

import com.school.dao.StudentMapper;
import com.school.pojo.PageBean;
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

    @Override
    public PageBean<Student> selectByPage(int currentPage, int pageSize) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int begin = (currentPage - 1) * pageSize;
        List<Student> list = studentMapper.selectByPage(begin, pageSize);
        PageBean<Student> pageBean = new PageBean<>();
        pageBean.setRows(list);
        int totalCount = studentMapper.selectCount();
        pageBean.setTotalCount(totalCount);

        return pageBean;
    }

    @Override
    public PageBean<Student> selectByPageAndCondition(int currentPage, int pageSize, String studentNumber, String studentName, String gender) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        int begin = (currentPage - 1) * pageSize;
        List<Student> list = studentMapper.selectByPageAndCondition(begin, pageSize, studentNumber, studentName, gender);
        PageBean<Student> pageBean = new PageBean<>();
        pageBean.setRows(list);
        int totalCount = studentMapper.selectCountByPageAndCondition(studentNumber, studentName, gender);
        pageBean.setTotalCount(totalCount);
        return pageBean;
    }
}
