package com.example.demo.Model;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;
import java.util.Optional;

public class PerfilValida implements TypeHandlerCallback {

    @Override
    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        if (o instanceof UserProfile) {
            UserProfile perfil = (UserProfile) o;
            parameterSetter.setString(perfil.name());
        } else {
            throw new SQLException("Erro: " + o.getClass().getName());
        }
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        String value = resultGetter.getString();
//        Optional.ofNullable(resultGetter.getString()).map(UserProfile::valueOf).orElse(null);
        return value != null ? UserProfile.valueOf(value) : null;
    }

    @Override
    public Object valueOf(String s) {
        return UserProfile.valueOf(s);
    }
}
