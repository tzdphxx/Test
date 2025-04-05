package com.school.dao;

import com.school.pojo.User;
import com.school.util.JDBC.Jdbc;
import com.school.util.JDBC.curd;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {


    public UserMapper(){}

    /**
     * 查询所有
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<User> selectAll() throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<User> users = new ArrayList<User>();
        String sql = "select * from users";
        users = curd.Query(User.class,sql);

        return users;
    }


    /**
     * 按名字查询
     * @param userName
     * @return
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public User selectByUsername(String userName) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from users where user_name = ?";
        List<User> users = curd.Query(User.class,sql,userName);
        if (users.isEmpty()){
            return null;
        }
        return users.get(0);
    }


    /**
     *
     * @param user
     * @throws SQLException
     * @throws NoSuchFieldException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void add(User user) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "insert into users (user_id, user_name, pass_word, role) VALUES (null, ?, ?, ?)";
        String userName = user.getUserName();
        String passWord = user.getPassWord();
        String role = user.getRole();

        curd.UpdateData(sql,userName,passWord,role);
    }



}
