package com.example.demo.Model;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LocalDateTimeTypeHandler implements TypeHandlerCallback {
    @Override
    public void setParameter(ParameterSetter parameterSetter, Object o) throws SQLException {
        if (o instanceof LocalDateTime) {
            parameterSetter.setTimestamp(Timestamp.valueOf((LocalDateTime) o));
        } else {
            throw new SQLException("Erro: esperado LocalDateTime, mas recebeu " + o.getClass().getName());
        }
    }

    @Override
    public Object getResult(ResultGetter resultGetter) throws SQLException {
        Timestamp timestamp = resultGetter.getTimestamp();
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    @Override
    public Object valueOf(String s) {
        return LocalDateTime.parse(s);
    }
}
