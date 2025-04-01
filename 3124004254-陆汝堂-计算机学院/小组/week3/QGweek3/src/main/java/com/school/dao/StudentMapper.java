package com.school.dao;

import com.school.pojo.Student;
import com.school.util.JDBC.curd;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentMapper {

    /**
     * 按userId查询
     * @param userId
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Student getStudentByUserId(int userId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Student> list = new ArrayList<Student>();
        String sql = "select * from student where user_id = ?";
        list = curd.Query(Student.class, sql, userId);

        if (list.isEmpty() || list == null) {
            return null;
        }
        return list.get(0);
    }


    /**
     *
     * @param student
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void add(Student student) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "insert into student (student_id, user_id, student_number, name, gender, phone, enrollment_year)values(null,?,?,?,?,?,?)";
        int userId = student.getUserId();
        String name = student.getName();
        String studentNumber = student.getStudentNumber();
        String gender = student.getGender();
        String phone = student.getPhone();
        int enrollment = student.getEnrollmentYear();

        curd.UpdateData(sql, userId, studentNumber, name, gender, phone, enrollment);

    }

    /**
     *
     * @param studentId
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Student queryTheStudent(int studentId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from student where student_id = ?";
        List<Student> list = curd.Query(Student.class, sql, studentId);
        if (list.isEmpty() || list == null) {
            return null;
        }
        return list.get(0);
    }

    public List<Student> queryAllStudent() throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from student";
        List<Student> list = curd.Query(Student.class, sql);
        if (list.isEmpty() || list == null) {
            return null;
        }
        return list;
    }

    public void changePhone(int studentId, String phone) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "update student set phone = ? where student_id = ?";
        curd.UpdateData(sql, phone, studentId);
    }

    public List<Student> studentsOfCourse(int courseId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "SELECT s.student_id as studentId, s.user_id as userId, s.student_number as studentNumber, s.name, s.gender, s.phone, s.enrollment_year as enrollmentYear FROM student s INNER JOIN student_courses sc ON s.student_id = sc.student_id INNER JOIN courses c ON sc.course_id = c.course_id WHERE c.course_id = ?";
        List<Student> list = curd.Query(Student.class, sql, courseId);
        if (list.isEmpty() || list == null) {
            return null;
        }
        return list;
    }
}
