package com.example.demo.Control;

import com.example.demo.Model.Auditoria;
import com.example.demo.Model.User;
import com.example.demo.Service.AuditoriaService;
import com.example.demo.Service.RelatorioService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

public class AuditoriaController {
    private int id;
    private User userId;
    private String acao;
    private LocalDateTime dataHora;
    private List<Auditoria> auditorias;



    @Autowired
    private RelatorioService relatorioService;
    @Autowired
    private UserService userService;
    @Autowired
    private User user;
    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    public AuditoriaController(UserService userService, AuditoriaService auditoriaService) {
        this.userService = userService;
        this.auditoriaService = auditoriaService;
    }

    public AuditoriaController() {
    }
    @PostConstruct
    public void init() {
        listarAuditorias();
    }

    public List<Auditoria> listarAuditorias() {
        this.auditorias = auditoriaService.buscarTodas();
        return this.auditorias;
    }

    public List<Auditoria> getAuditorias() {
        return auditorias;
    }
    public void gerarRelatorioPDF() {
        RelatorioService relatorioService = new RelatorioService();
        relatorioService.gerarRelatorioPDF(auditorias);
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public void setAuditoriaService(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }


    public void setRelatorioService(Object relatorioService) {
    }

    public RelatorioService getRelatorioService() {
        return relatorioService;
    }

    public void setRelatorioService(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    public AuditoriaService getAuditoriaService() {
        return auditoriaService;
    }
}
