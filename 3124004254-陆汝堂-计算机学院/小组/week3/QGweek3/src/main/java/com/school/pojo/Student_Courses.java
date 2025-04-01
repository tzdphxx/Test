package com.school.pojo;

import java.sql.Date;
import java.sql.Timestamp;

public class Student_Courses {
    private int enrollmentId;
    private int studentId;
    private int courseId;
    private Timestamp selectAt;

    public Student_Courses() {
    }

    public Student_Courses(int enrollmentId, int studentId, int courseId, Timestamp selectAt) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.selectAt = selectAt;
    }

    /**
     * 获取
     * @return enrollmentId
     */
    public int getEnrollmentId() {
        return enrollmentId;
    }

    /**
     * 设置
     * @param enrollmentId
     */
    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    /**
     * 获取
     * @return studentId
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * 设置
     * @param studentId
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * 获取
     * @return courseId
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * 设置
     * @param courseId
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * 获取
     * @return selectAt
     */
    public Timestamp getSelectAt() {
        return selectAt;
    }

    /**
     * 设置
     * @param selectAt
     */
    public void setSelectAt(Timestamp selectAt) {
        this.selectAt = selectAt;
    }

    public String toString() {
        return "Student_Courses{enrollmentId = " + enrollmentId + ", studentId = " + studentId + ", courseId = " + courseId + ", selectAt = " + selectAt + "}";
    }
}
