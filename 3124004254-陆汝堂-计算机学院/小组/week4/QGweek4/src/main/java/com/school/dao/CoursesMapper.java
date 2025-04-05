package com.school.dao;

import com.school.pojo.Courses;
import com.school.util.JDBC.curd;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CoursesMapper {


    /**
     *
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<Courses> selectAllCourse() throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from courses";
        List<Courses> list = curd.Query(Courses.class, sql);
        return list;
    }

    /**
     *
     * @param begin
     * @param pageSize
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<Courses> selectByPage(int begin, int pageSize) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from courses limit ?,? ";
        List<Courses> list = new ArrayList<Courses>();
        list = curd.Query(Courses.class,sql,begin,pageSize);

        return list;
    }

    /**
     *
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public int selectCount() throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from courses ";
        List<Courses> list = curd.Query(Courses.class,sql);
        return list.size();
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
    public List<Courses> selectByStudentId(int studentId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        String sql ="SELECT c.course_id as courseId, c.course_code as courseCode, c.course_name as courseName, c.credit, c.teacher_name as teacherName, c.start_date as startDate FROM student s INNER JOIN student_courses sc ON s.student_id = sc.student_id INNER JOIN courses c ON sc.course_id = c.course_id WHERE s.student_id = ?";

        List<Courses> list = curd.Query(Courses.class, sql, studentId);

        return list;
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
    public List<Courses> selectAllCourseByTime(int studentId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select c.* from courses c where c.start_date > curdate() and not exists (select 1 from student_courses sc where sc.course_id = c.course_id and sc.student_id = ?)";
        List<Courses> list = curd.Query(Courses.class,sql,studentId);
        return list;
    }

    /**
     *
     * @param courseId
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void deleteCourse(int courseId) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "delete from courses where course_id = ?";
        curd.UpdateData(sql,courseId);
    }

    public void editCourse(Courses course) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "update courses set course_code = ?,course_name = ?, credit = ?, start_date = ? where course_id = ?";
        String CourseCode = course.getCourseCode();
        String CourseName = course.getCourseName();
        int credit = course.getCredit();
        Date startDate = course.getStartDate();
        int courseId = course.getCourseId();
        curd.UpdateData(sql,CourseCode,CourseName,credit,startDate,courseId);
    }


    public List<Courses> selectByPageAndCondition(int begin, int pageSize, String CourseName, String teacherName, String status) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        StringBuilder condition = new StringBuilder("select * from courses where 1=1 ");
        List<Object> params = new ArrayList<>();

        if (CourseName != null && !"".equals(CourseName)) {
            condition.append(" and course_name like ?");
            params.add("%" + CourseName + "%");
        }
        if (teacherName != null && !"".equals(teacherName)) {
            condition.append(" and teacher_name like ?");
            params.add("%" + teacherName + "%");
        }
        if (status != null && !"".equals(status)) {
            String date = ">";
            if ("1".equals(status)) {
                date = "<=";
            }
            condition.append(" and start_date ").append(date).append(" curdate() ");
        }
        condition.append(" limit ?,?");
        params.add(begin);
        params.add(pageSize);
        List<Courses> list = curd.Query(Courses.class,condition.toString(),params.toArray());

        return list;
    }
    public int selectCountByPageAndCondition(String CourseName, String teacherName, String status) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        StringBuilder condition = new StringBuilder("select * from courses where 1=1 ");
        List<Object> params = new ArrayList<>();

        if (CourseName != null && !"".equals(CourseName)) {
            condition.append(" and course_name like ?");
            params.add("%" + CourseName + "%");
        }
        if (teacherName != null && !"".equals(teacherName)) {
            condition.append(" and teacher_name like ?");
            params.add("%" + teacherName + "%");
        }
        if (status != null && !"".equals(status)) {
            String date = ">";
            if ("1".equals(status)) {
                date = "<=";
            }
            condition.append(" and start_date ").append(date).append(" curdate() ");
        }

        List<Courses> list = curd.Query(Courses.class,condition.toString(),params.toArray());

        return list.size();
    }
}
