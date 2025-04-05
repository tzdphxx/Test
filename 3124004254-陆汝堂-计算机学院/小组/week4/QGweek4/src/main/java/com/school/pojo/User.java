package com.school.pojo;

public class User {
    private int userId;
    private String userName;
    private String passWord;
    private String role;

    public User() {
    }

    public User(int userId, String userName, String passWord, String role) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.role = role;
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
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取
     * @return passWord
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * 设置
     * @param passWord
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * 获取
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    public String toString() {
        return "User{userId = " + userId + ", userName = " + userName + ", passWord = " + passWord + ", role = " + role + "}";
    }
}