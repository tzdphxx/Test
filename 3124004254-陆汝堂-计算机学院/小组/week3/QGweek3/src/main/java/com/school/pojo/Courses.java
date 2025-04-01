package com.school.pojo;

import java.sql.Date;



public class Courses {
    private int courseId;
    private String courseCode;
    private String courseName;
    private int credit;
    private int teacherId;
    private Date startDate;
    private String teacherName;

    public Courses() {
    }

    public Courses(int courseId, String courseCode, String courseName, int credit, int teacherId, Date startDate, String teacherName) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credit = credit;
        this.teacherId = teacherId;
        this.startDate = startDate;
        this.teacherName = teacherName;
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
     * @return courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * 设置
     * @param courseCode
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * 获取
     * @return courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * 设置
     * @param courseName
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * 获取
     * @return credit
     */
    public int getCredit() {
        return credit;
    }

    /**
     * 设置
     * @param credit
     */
    public void setCredit(int credit) {
        this.credit = credit;
    }

    /**
     * 获取
     * @return teacherId
     */
    public int getTeacherId() {
        return teacherId;
    }

    /**
     * 设置
     * @param teacherId
     */
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 获取
     * @return startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取
     * @return teacherName
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * 设置
     * @param teacherName
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String toString() {
        return "Courses{courseId = " + courseId + ", courseCode = " + courseCode + ", courseName = " + courseName + ", credit = " + credit + ", teacherId = " + teacherId + ", startDate = " + startDate + ", teacherName = " + teacherName + "}";
    }
}