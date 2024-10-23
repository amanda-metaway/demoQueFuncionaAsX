package com.example.demo.Model;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;

public class ServicosValida implements TypeHandlerCallback {

    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter instanceof Servicos) {
            Servicos servico = (Servicos) parameter;
        }
    }

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        return null;
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
