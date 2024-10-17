package com.example.demo.Control;

import com.example.demo.Model.Auditoria;
import com.example.demo.Model.User;
import com.example.demo.Service.AuditoriaService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class AuditoriaController {
    private User userId;
    private String acao;
    private LocalDateTime dataHora;

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
