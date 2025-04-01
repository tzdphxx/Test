package com.school.service.impl;

import com.school.dao.UserMapper;
import com.school.pojo.User;
import com.school.service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class UserImpl implements UserService {
    private static UserMapper userMapper = new UserMapper();

    @Override
    public User selectByUserName(String userName) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        User user = userMapper.selectByUsername(userName);
        if (user == null) {
            throw new NoSuchFieldException("用户不存在");
        }

       return user;
    }

    @Override
    public List<User> selectAll() throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public void add(User user) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        userMapper.add(user);
    }


}
