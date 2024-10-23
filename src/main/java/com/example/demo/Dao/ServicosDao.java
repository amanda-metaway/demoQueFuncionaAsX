package com.example.demo.Dao;

import com.example.demo.Model.Servicos;

import java.util.List;

public interface ServicosDao {

    void saveServico(Servicos servico);

    Servicos getServicoById(int id);

    void updateServico(Servicos servico);

    void deleteCancelaServico(int id);

    List<Servicos> listarServicos();
}
