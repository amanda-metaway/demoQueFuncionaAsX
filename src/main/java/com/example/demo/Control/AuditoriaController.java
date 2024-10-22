package com.example.demo.Control;

import com.example.demo.Model.Auditoria;
import com.example.demo.Model.User;
import com.example.demo.Service.AuditoriaService;
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
    private UserService userService;
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

    public void listarAuditorias() {
        this.auditorias = auditoriaService.buscarTodas();
    }

    public List<Auditoria> getAuditorias() {
        return auditorias;
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


}
