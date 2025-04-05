package com.school.pojo;

public class Student {
    private int studentId;
    private int userId;
    private String studentNumber;
    private String name;
    private String gender;
    private String phone;
    private int enrollmentYear;

    public Student() {
    }

    public Student(int studentId, int userId, String studentNumber, String name, String gender, String phone, int enrollmentYear) {
        this.studentId = studentId;
        this.userId = userId;
        this.studentNumber = studentNumber;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.enrollmentYear = enrollmentYear;
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
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 获取
     * @return studentNumber
     */
    public String getStudentNumber() {
        return studentNumber;
    }

    /**
     * 设置
     * @param studentNumber
     */
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * 设置
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * 获取
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取
     * @return enrollmentYear
     */
    public int getEnrollmentYear() {
        return enrollmentYear;
    }

    /**
     * 设置
     * @param enrollmentYear
     */
    public void setEnrollmentYear(int enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public String toString() {
        return "Student{studentId = " + studentId + ", userId = " + userId + ", studentNumber = " + studentNumber + ", name = " + name + ", gender = " + gender + ", phone = " + phone + ", enrollmentYear = " + enrollmentYear + "}";
    }
}