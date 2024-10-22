package com.example.demo.Dao;


import com.example.demo.Model.Auditoria;

import java.util.List;


public interface AuditoriaDao {

    void inserirAuditoria(Auditoria auditoria);

    List<Auditoria> buscarTodas();
}
