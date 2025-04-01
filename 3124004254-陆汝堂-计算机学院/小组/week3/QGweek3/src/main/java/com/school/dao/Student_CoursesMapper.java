package com.school.dao;

import com.school.pojo.Student;
import com.school.pojo.Student_Courses;
import com.school.util.JDBC.curd;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class Student_CoursesMapper {
    /**
     *
     * @param courseId
     * @throws SQLException
     */
    public void deleteByCourseIdAndStudentId(int courseId,int studentId) throws SQLException {
        String sql = "delete from student_courses where course_id=? and student_id=?";
        curd.UpdateData(sql,courseId,studentId);
    }

    /**
     *
     * @param selectIds
     * @param studentId
     * @throws SQLException
     */
    public void deleteByCourseIds(int[] selectIds, int studentId) throws SQLException {
        if (selectIds == null || selectIds.length == 0) {
            return;
        }


        StringBuilder placeholders = new StringBuilder();
        placeholders.append("?");
        for (int i = 1; i < selectIds.length; i++) {
            placeholders.append(",?");
        }

        String sql = "DELETE FROM student_courses WHERE student_id = ? AND course_id IN (" + placeholders + ")";


        Object[] params = new Object[selectIds.length + 1];
        params[0] = studentId;
        for (int i = 0; i < selectIds.length; i++) {
            params[i + 1] = selectIds[i];
        }

        curd.UpdateData(sql, params);
    }

    /**
     *
     * @param studentId
     * @param courseId
     * @throws SQLException
     */
    public void addCourse(int studentId, int courseId) throws SQLException {
        String sql = "insert into student_courses(student_id, course_id) values(?,?)";
        curd.UpdateData(sql, studentId, courseId);
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
    public int HowChoose(int studentId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from student_courses where student_id=?";
        List<Student_Courses>list = curd.Query(Student_Courses.class,sql,studentId);

        return list.size();
    }

    /**
     *
     * @param courseId
     * @throws SQLException
     */
    public void deleteCourse(int courseId) throws SQLException {
        String sql = "delete from student_courses where course_id=?";
        curd.UpdateData(sql,courseId);
    }


}
