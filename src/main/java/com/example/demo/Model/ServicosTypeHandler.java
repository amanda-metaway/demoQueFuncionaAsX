package com.example.demo.Model;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

import java.sql.SQLException;
import java.time.Duration;

public class ServicosTypeHandler implements TypeHandlerCallback {

    @Override
    public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
        if (parameter instanceof Servicos) {
            Servicos servico = (Servicos) parameter;
            setter.setInt(servico.getId());
            setter.setInt(servico.getTipo().getTipoServico());
            setter.setString(servico.getDescricao());
            setter.setDouble(servico.getValor());
            setter.setString(servico.getDuracaoEstimado().toString());
            setter.setInt(servico.getUserId().getId());
        } else {
            throw new SQLException("Erro: esperado Servicos, mas recebeu " + parameter.getClass().getName());
        }
    }

    @Override
    public Object getResult(ResultGetter getter) throws SQLException {
        Servicos servico = new Servicos();
        servico.setId(getter.getInt());
        servico.setTipo(TipoServico.fromTiposervico(getter.getInt()));
        servico.setDescricao(getter.getString());
        servico.setValor(getter.getDouble());
        servico.setDuracaoEstimado(Duration.parse(getter.getString()));
        return servico;
    }

    @Override
    public Object valueOf(String s) {
        return null;
    }
}
