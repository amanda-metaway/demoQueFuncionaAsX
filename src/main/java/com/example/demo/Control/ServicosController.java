package com.example.demo.Control;

import com.example.demo.Model.Servicos;
import com.example.demo.Service.ServicosService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.List;


public class ServicosController {

    @Autowired
    private ServicosService servicosService;

    private Servicos servicoSelecionado;
    private List<Servicos> servicosListaController;

    @PostConstruct
    public void init() {
        listarServicos();
    }

    public void listarServicos() {
        servicosListaController = servicosService.listarServicos();
    }

    public void buscarServico(int id) {
        servicoSelecionado = servicosService.buscarServicoPorId(id);
        if (servicoSelecionado != null) {
            System.out.println("Serviço encontrado: " + servicoSelecionado.getDescricao());
        } else {
            System.out.println("Serviço não encontrado para ID: " + id);
        }
    }

    public void agendarServico(Servicos servico) {
        if (servico != null) {
            System.out.println("Agendamento do serviço " + servico.getDescricao() + " realizado com sucesso!");
        } else {
            System.out.println("Não foi possível agendar o serviço.");
        }
    }



    public Servicos getServicoSelecionado() {
        return servicoSelecionado;
    }

    public void setServicoSelecionado(Servicos servicoSelecionado) {
        this.servicoSelecionado = servicoSelecionado;
    }

    public List<Servicos> getServicosListaController() {
        return servicosListaController;
    }

    public void setServicosListaController(List<Servicos> servicosListaController) {
        this.servicosListaController = servicosListaController;
    }

    public ServicosService getServicosService() {
        return servicosService;
    }

    public void setServicosService(ServicosService servicosService) {
        this.servicosService = servicosService;
    }
}
