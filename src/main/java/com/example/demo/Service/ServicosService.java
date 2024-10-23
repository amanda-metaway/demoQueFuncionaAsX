package com.example.demo.Service;

import com.example.demo.Dao.IBatisServicosDao;
import com.example.demo.Model.Servicos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicosService {

    @Autowired
    private IBatisServicosDao servicosDao;

    public void salvarServico(Servicos servico) {
        if (servico.getId() == 0) {
            servicosDao.saveServico(servico);
        } else {
            servicosDao.updateServico(servico);
        }
    }

    public Servicos buscarServicoPorId(int id) {
        return servicosDao.getServicoById(id);
    }

    public List<Servicos> listarServicos() {
        return servicosDao.listarServicos();
    }

    public void deleteCancelaServico(int id) {
        servicosDao.deleteCancelaServico(id);
    }

    public void setServicosDao(IBatisServicosDao servicosDao) {

    }
}
