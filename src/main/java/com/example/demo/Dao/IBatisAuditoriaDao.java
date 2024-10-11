package com.example.demo.Dao;

import com.example.demo.Control.AuditoriaController;
import org.apache.tomcat.jni.User;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import java.time.LocalDateTime;

public class IBatisAuditoriaDao extends SqlMapClientDaoSupport implements AuditoriaDao {

    @Override
    public void saveAuditoria(AuditoriaController auditoriaController) {
        getSqlMapClientTemplate().insert("inserirAuditoria", auditoriaController);
    }

}



