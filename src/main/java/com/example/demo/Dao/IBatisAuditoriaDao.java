package com.example.demo.Dao;




import com.example.demo.Model.Auditoria;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.util.List;


public class IBatisAuditoriaDao extends SqlMapClientDaoSupport implements AuditoriaDao {
    public void inserirAuditoria(Auditoria auditoria) {
        getSqlMapClientTemplate().insert("inserirAuditoria", auditoria);
    }

    @Override
    public List<Auditoria> buscarTodas() {
        return getSqlMapClientTemplate().queryForList("buscarTodas");
    }


}







