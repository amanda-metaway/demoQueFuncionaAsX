package com.example.demo.Dao;

import com.example.demo.Model.Servicos;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;

public class IBatisServicosDao extends SqlMapClientDaoSupport implements ServicosDao {

    @Override
    public void saveServico(Servicos servico) {
        getSqlMapClientTemplate().insert("saveServico", servico);
    }

    @Override
    public Servicos getServicoById(int id) {
        return (Servicos) getSqlMapClientTemplate().queryForObject("getServicoById", id);
    }

    @Override
    public void updateServico(Servicos servico) {
        getSqlMapClientTemplate().update("updateServico", servico);
    }

    @Override
    public void deleteCancelaServico(int id) {
        getSqlMapClientTemplate().delete("deleteCancelaServico", id);
    }

    @Override
    public List<Servicos> listarServicos() {
        return getSqlMapClientTemplate().queryForList("listarServicos");
    }
}
