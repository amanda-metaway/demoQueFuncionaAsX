package com.example.demo.Model;

import com.example.demo.Service.UserService;
import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.sql.Types;

public class AuditoriatypeHandler implements TypeHandlerCallback {

    @Autowired
    private User user;

    @Autowired
    private UserService userService;


    @Override
    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        if (o == null) {
            parameterSetter.setNull(Types.INTEGER);
        } else if (o instanceof Integer) {
            parameterSetter.setInt((Integer) o);
        } else {
            throw new SQLException("Esperado Integer mas obteve: " + o.getClass().getName());
        }
    }




    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        return resultGetter.getInt();
    }

    @Override
    public Object valueOf(String s) {
        return Integer.valueOf(s);
    }


    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


